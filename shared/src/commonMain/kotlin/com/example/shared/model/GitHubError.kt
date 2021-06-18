package com.example.shared.model

import com.example.shared.data.source.remote.api.GitHubApiError
import com.example.shared.data.source.remote.api.response.header.GitHubApiRateLimit
import io.ktor.client.features.ServerResponseException
import io.ktor.client.statement.HttpResponse
import io.ktor.http.HttpStatusCode
import io.ktor.utils.io.errors.IOException
import kotlinx.serialization.SerializationException

sealed class GitHubError(
    message: String? = null,
    cause: Throwable? = null
) : Exception(message, cause) {
    class NetworkError(cause: Throwable) : GitHubError(cause = cause)
    class LateLimitError(val limit: GitHubApiRateLimit) : GitHubError() {
        override fun toString(): String = limit.toString()
    }

    class SystemError(message: String?, cause: Throwable?) : GitHubError(message, cause)
    class UnknownError(message: String?, cause: Throwable?) : GitHubError(message, cause)

    companion object {
        internal operator fun invoke(rawResponse: HttpResponse): GitHubError {
            return UnknownError(
                message = rawResponse.status.toString(),
                cause = null
            )
        }

        internal operator fun invoke(error: GitHubApiError): GitHubError {
            return when {
                error.cause is IOException -> NetworkError(error.cause)
                error.cause is SerializationException ->
                    UnknownError("Response parse error", cause = error.cause)
                error.cause is ServerResponseException ->
                    SystemError(error.status.toString(), error.cause)
                error.status == HttpStatusCode.NotFound -> {
                    val lateLimit = error.rateLimit
                    if (lateLimit == null) {
                        UnknownError(error.status.toString(), error.cause)
                    } else {
                        LateLimitError(lateLimit)
                    }
                }
                else -> UnknownError(error.status.toString(), error.cause)
            }
        }
    }
}