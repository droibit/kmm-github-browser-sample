package com.example.shared.data.source.remote.api.response.body

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ContributorResponseBody(
    val login: String,
    val id: Int,
    @SerialName("avatar_url") val avatarUrl: String,
    @SerialName("html_url") val url: String,
    val contributions: Int
)
