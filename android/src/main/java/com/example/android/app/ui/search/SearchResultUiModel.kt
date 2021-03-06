package com.example.android.app.ui.search

import com.example.android.app.ui.common.Message
import com.example.shared.model.PagedRepoSearchResult

data class SearchResultUiModel(
    val inProgress: Boolean = false,
    val searchResult: PagedRepoSearchResult? = null,
    val error: Message? = null
) {
    val hasNoState: Boolean
        get() = !inProgress && searchResult == null && error == null

    val firstInProgress: Boolean
        get() = inProgress && searchResult == null

    val pagingInProgress: Boolean
        get() = inProgress && searchResult != null

    val visibleSearchResult: Boolean
        get() = searchResult?.hasRepos == true

    val visibleEmptySearchResult: Boolean
        get() = searchResult?.hasRepos == false
}