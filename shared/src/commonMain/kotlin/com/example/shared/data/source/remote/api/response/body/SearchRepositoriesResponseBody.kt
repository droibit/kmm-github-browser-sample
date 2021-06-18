package com.example.shared.data.source.remote.api.response.body

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SearchRepositoriesResponseBody(
    @SerialName("total_count") val totalCount: Int,
    @SerialName("incomplete_results") val incompleteResults: Boolean,
    val items: List<RepositoryResponseBody>
)
