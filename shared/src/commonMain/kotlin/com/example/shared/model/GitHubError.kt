package com.example.shared.model

import com.example.shared.data.source.remote.api.GitHubApiError
import com.example.shared.data.source.remote.api.response.header.GitHubApiRateLimit
import io.ktor.client.statement.HttpResponse
import io.ktor.http.HttpStatusCode
import io.ktor.utils.io.errors.IOException
import kotlinx.serialization.SerializationException

sealed class GitHubError(
    message: String? = null,
    cause: Throwable? = null
) : Exception(message, cause) {
    class Network(cause: Throwable) : GitHubError(cause = cause)
    class LateLimit(val limit: GitHubApiRateLimit) : GitHubError() {
        override fun toString(): String = limit.toString()
    }

    class Unexpected(message: String? = null, cause: Throwable? = null) : GitHubError(cause = cause)

    companion object {
        internal operator fun invoke(rawResponse: HttpResponse): GitHubError {
            return Unexpected(message = rawResponse.status.toString())
        }

        internal operator fun invoke(error: GitHubApiError): GitHubError {
            return when {
                error.cause is IOException -> Network(error.cause)
                error.cause is SerializationException -> Unexpected(cause = error.cause)
                error.status == HttpStatusCode.NotFound -> {
                    val lateLimit = error.rateLimit
                    if (lateLimit == null) {
                        Unexpected(error.status.toString(), error.cause)
                    } else {
                        LateLimit(lateLimit)
                    }
                }
                else -> Unexpected(error.status.toString(), error.cause)
            }
        }
    }
}