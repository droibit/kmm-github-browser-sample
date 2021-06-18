package com.example.shared.data.source.remote.api

import com.example.shared.data.source.remote.api.response.header.GitHubApiRateLimit
import io.ktor.client.statement.HttpResponse
import io.ktor.http.HttpStatusCode
import kotlin.LazyThreadSafetyMode.NONE

internal class GitHubApiError(
    val rawResponse: HttpResponse,
    cause: Throwable? = null
) : Exception(cause) {

    val rateLimit: GitHubApiRateLimit? by lazy(NONE) {
        GitHubApiRateLimit(rawResponse.headers)
    }

    val status: HttpStatusCode get() = rawResponse.status

    override fun toString(): String {
        if (cause == null) {
            return rawResponse.status.toString()
        }
        return super.toString()
    }
}
