package com.example.shared.data.source.remote.api.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RepositoryResponse(
    val id: Int,
    val name: String,
    val description: String?,
    @SerialName("stargazers_count") val stargazersCount: Int,
    @SerialName("full_name") val fullName: String,
    val url: String,
    val owner: RepositoryOwnerResponse,
)

@Serializable
data class RepositoryOwnerResponse(
    val login: String,
    val id: Int,
    val url: String,
    @SerialName("avatar_url") val avatarUrl: String,
)