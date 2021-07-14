package com.example.shared.model

import com.example.shared.data.source.local.db.Repo

data class PagedRepoSearchResult(
    val repos: List<Repo>,
    val nextPage: Int?
) {
    val hasRepos get() = repos.isNotEmpty()

    constructor() : this(emptyList(), null)

    fun merge(existingRepos: List<Repo>) = copy(repos = existingRepos + repos)
}
