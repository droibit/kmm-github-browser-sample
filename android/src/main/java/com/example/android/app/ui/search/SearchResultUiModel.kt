package com.example.android.app.ui.search

import com.example.shared.data.source.local.db.Repo
import com.example.shared.model.PagedRepoSearchResult

data class SearchResultUiModel(
    val inProgress: Boolean = false,
    val searchResult: PagedRepoSearchResult? = null,
    val error: String? = null
) {
    val hasNoState: Boolean
        get() = !inProgress && searchResult == null && error == null

    val firstInProgress: Boolean
        get() = inProgress && searchResult == null

    val pagingInProgress: Boolean
        get() = inProgress && searchResult != null

    val visibleSearchResult: Boolean
        get() = !searchResult?.repos.isNullOrEmpty()

    val visibleEmptySearchResult: Boolean
        get() = hasNoState || searchResult?.repos?.isEmpty() == true
}

fun PagedRepoSearchResult.merge(existingRepos: List<Repo>) =
    copy(existingRepos + repos)