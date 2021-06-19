package com.example.shared.data.source.remote.api.response.body

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UserResponseBody(
    val login: String,
    val id: Long,
    @SerialName("html_url") val url: String,
    @SerialName("avatar_url") val avatarUrl: String,
    @SerialName("repos_url") val reposUrl: String,
    @SerialName("blog") val blogUrl: String?,
    val name: String,
    val company: String?,
    val email: String?,
    val followers: Int,
    val following: Int
)