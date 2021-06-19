package com.example.shared.data.source.remote.api.response.body

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RepositoryResponseBody(
    val id: Long,
    val name: String,
    val description: String?,
    @SerialName("stargazers_count") val stargazersCount: Int,
    @SerialName("full_name") val fullName: String,
    @SerialName("html_url") val url: String,
    val owner: RepositoryOwnerResponseBody,
)

@Serializable
data class RepositoryOwnerResponseBody(
    val login: String,
    val id: Long,
    @SerialName("html_url") val url: String,
    @SerialName("avatar_url") val avatarUrl: String,
)