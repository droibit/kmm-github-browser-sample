package com.example.shared.data.source.remote.api

import com.chrynan.inject.Inject
import com.chrynan.inject.Singleton
import com.example.shared.data.source.remote.api.response.Response
import com.example.shared.data.source.remote.api.response.body.ContributorResponseBody
import com.example.shared.data.source.remote.api.response.body.RepositoryResponseBody
import com.example.shared.data.source.remote.api.response.body.SearchRepositoriesResponseBody
import com.example.shared.data.source.remote.api.response.body.UserResponseBody
import io.ktor.client.HttpClient
import io.ktor.client.features.ResponseException
import io.ktor.client.request.accept
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.client.statement.HttpResponse
import io.ktor.http.ContentType
import io.ktor.http.HttpStatusCode
import kotlin.coroutines.cancellation.CancellationException

@Singleton
class GitHubService(
    private val baseURL: String,
    private val httpClient: HttpClient
) {
    @Inject
    constructor(httpClient: HttpClient) : this(
        baseURL = "https://api.github.com",
        httpClient
    )

    private val contentType = ContentType("application", "vnd.github.v3+json")

    /**
     * ref. https://docs.github.com/rest/reference/users#get-a-user
     */
    @Throws(GitHubApiError::class, CancellationException::class)
    suspend fun getUser(login: String): Response<UserResponseBody> {
        return try {
            val rawResponse: HttpResponse = httpClient.get("$baseURL/users/$login") {
                accept(contentType)
            }
            createGitHubResponse(rawResponse)
        } catch (e: ResponseException) {
            if (e.response.status != HttpStatusCode.NotFound) {
                throw GitHubApiError(e.response, e)
            }
            Response.error(e.response)
        }
    }

    /**
     * ref. https://docs.github.com/rest/reference/repos#list-repositories-for-a-user
     */
    @Throws(GitHubApiError::class, CancellationException::class)
    suspend fun getRepos(login: String): Response<List<RepositoryResponseBody>> {
        return try {
            val rawResponse: HttpResponse = httpClient.get("$baseURL/users/$login/repos") {
                accept(contentType)
            }
            createGitHubResponse(rawResponse)
        } catch (e: ResponseException) {
            if (e.response.status != HttpStatusCode.NotFound) {
                throw GitHubApiError(e.response, e)
            }
            Response.error(e.response)
        }
    }

    /**
     * ref. https://docs.github.com/rest/reference/repos#get-a-repository
     */
    @Throws(GitHubApiError::class, CancellationException::class)
    suspend fun getRepo(owner: String, name: String): Response<RepositoryResponseBody> {
        return try {
            val rawResponse: HttpResponse = httpClient.get("$baseURL/repos/$owner/$name") {
                accept(contentType)
            }
            createGitHubResponse(rawResponse)
        } catch (e: ResponseException) {
            if (e.response.status != HttpStatusCode.NotFound) {
                throw GitHubApiError(e.response, e)
            }
            Response.error(e.response)
        }
    }

    /**
     * ref. https://docs.github.com/en/rest/reference/repos#list-repository-contributors
     */
    @Throws(GitHubApiError::class, CancellationException::class)
    suspend fun getContributors(
        owner: String,
        name: String
    ): Response<List<ContributorResponseBody>> {
        return try {
            val rawResponse: HttpResponse =
                httpClient.get("$baseURL/repos/$owner/$name/contributors") {
                    accept(contentType)
                }
            createGitHubResponse(rawResponse)
        } catch (e: ResponseException) {
            if (e.response.status != HttpStatusCode.NotFound) {
                throw GitHubApiError(e.response, e)
            }
            Response.error(e.response)
        }
    }

    /**
     * ref. https://docs.github.com/en/rest/reference/search#search-repositories
     */
    @Throws(GitHubApiError::class, CancellationException::class)
    suspend fun searchRepos(
        query: String,
        page: Int? = null
    ): Response<SearchRepositoriesResponseBody> {
        try {
            val rawResponse: HttpResponse = httpClient.get("$baseURL/search/repositories") {
                accept(contentType)

                parameter("q", query)
                parameter("page", page)
            }
            return createGitHubResponse(rawResponse)
        } catch (e: ResponseException) {
            throw GitHubApiError(e.response, e)
        }
    }
}

private suspend inline fun <reified T> createGitHubResponse(rawResponse: HttpResponse): Response<T> {
    try {
        return Response.create(rawResponse)
    } catch (e: Exception) {
        throw GitHubApiError(rawResponse, cause = e)
    }
}