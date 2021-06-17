package com.example.shared.data.source.remote.api

import com.chrynan.inject.Inject
import com.chrynan.inject.Named
import com.chrynan.inject.Singleton
import com.example.shared.data.source.remote.api.response.UserResponse
import com.github.droibit.komol.Komol
import io.ktor.client.HttpClient
import io.ktor.client.call.receive
import io.ktor.client.request.accept
import io.ktor.client.request.get
import io.ktor.client.statement.HttpResponse
import io.ktor.http.ContentType
import io.ktor.http.isSuccess
import kotlin.coroutines.cancellation.CancellationException

@Singleton
class GitHubService @Inject constructor(
    @Named("githubApiBaseURL") private val baseURL: String,
    private val httpClient: HttpClient
) {
    private val contentType = ContentType("application", "vnd.github.v3+json")

    @Throws(ApiError::class, CancellationException::class)
    suspend fun getUser(login: String): UserResponse {
        val response: HttpResponse = httpClient.get("$baseURL/users/$login") {
            accept(contentType)
        }
        return response.receiveIfSuccess()
    }

    //suspend fun getRepos(login: String)
}

private suspend inline fun <reified T> HttpResponse.receiveIfSuccess(): T {
    try {
        if (!status.isSuccess()) {
            throw ApiError(this)
        }
        return receive()
    } catch (e: Exception) {
        throw ApiError(this, cause = e)
    }
}