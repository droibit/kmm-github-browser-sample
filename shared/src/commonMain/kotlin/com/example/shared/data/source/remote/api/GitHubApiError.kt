package com.example.shared.data.source.remote.api

import com.example.shared.data.source.remote.api.response.header.GitHubApiRateLimit
import io.ktor.client.statement.HttpResponse

class GitHubApiError(
    val rawResponse: HttpResponse,
    cause: Throwable? = null
) : Exception(cause) {

    val rateLimit: GitHubApiRateLimit? by lazy {
        GitHubApiRateLimit(rawResponse.headers)
    }

    override fun toString(): String {
        if (cause == null) {
            return rawResponse.status.toString()
        }
        return super.toString()
    }
}
