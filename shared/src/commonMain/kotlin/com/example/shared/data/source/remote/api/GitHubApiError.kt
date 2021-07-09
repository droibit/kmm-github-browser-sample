package com.example.shared.data.source.remote.api

import com.example.shared.data.source.remote.api.response.header.GitHubApiRateLimit
import io.ktor.client.statement.HttpResponse
import io.ktor.http.HttpStatusCode
import kotlin.LazyThreadSafetyMode.NONE

internal class GitHubApiError(
    val rawResponse: HttpResponse?,
    cause: Throwable
) : Exception(cause) {

    val rateLimit: GitHubApiRateLimit? by lazy(NONE) {
        if (rawResponse != null) {
            GitHubApiRateLimit(rawResponse.headers)
        } else {
            null
        }
    }

    val status: HttpStatusCode? get() = rawResponse?.status

    override fun toString(): String {
        return cause.toString()
    }
}
