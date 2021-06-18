package com.example.shared.data.source.remote.api.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * ref. https://docs.github.com/rest/reference/users#get-a-user
 */
@Serializable
data class UserResponse(
    val login: String,
    val id: Int,
    @SerialName("html_url") val url: String,
    @SerialName("avatar_url") val avatarUrl: String,
    @SerialName("blog") val blogUrl: String?,
    val name: String,
    val company: String?,
    val email: String?,
    val followers: Int,
    val following: Int
)