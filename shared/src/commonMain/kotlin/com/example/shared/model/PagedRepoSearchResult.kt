package com.example.shared.model

import com.example.shared.data.source.local.db.Repo

data class PagedRepoSearchResult(
    val repos: List<Repo>,
    val nextPage: Int?
) {
    constructor() : this(emptyList(), null)
}
