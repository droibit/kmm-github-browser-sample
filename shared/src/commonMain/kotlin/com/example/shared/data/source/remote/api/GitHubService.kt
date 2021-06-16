package com.example.shared.data.source.remote.api

import com.chrynan.inject.Inject
import com.chrynan.inject.Named
import com.chrynan.inject.Singleton
import io.ktor.client.HttpClient

@Singleton
class GitHubService @Inject constructor(
    @Named("githubApiBaseURL") private val baseURL: String,
    private val httpClient: HttpClient
) {
}