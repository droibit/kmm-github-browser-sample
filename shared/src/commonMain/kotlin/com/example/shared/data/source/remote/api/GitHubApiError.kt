package com.example.shared.data.source.remote.api

import com.example.shared.data.source.remote.api.response.GitHubApiRateLimit
import io.ktor.client.statement.HttpResponse

class GitHubApiError(
    val response: HttpResponse,
    cause: Throwable? = null
) : Exception(cause) {

    val rateLimit: GitHubApiRateLimit? by lazy {
        GitHubApiRateLimit(response.headers)
    }

    override fun toString(): String {
        if (cause == null) {
            return response.status.toString()
        }
        return super.toString()
    }
}
