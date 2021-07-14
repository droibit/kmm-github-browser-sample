package com.example.android.app.ui.repo.detail

import com.example.shared.data.source.local.db.Contributor
import com.example.shared.data.source.local.db.Repo

data class RepoUiModel(
    val repo: Repo,
    val contributors: List<Contributor>
) {
    val hasContributors: Boolean get() = contributors.isNotEmpty()
}

data class GetRepoUiModel(
    val inProgress: Boolean = false,
    val repoUiModel: RepoUiModel? = null,
    val error: String? = null
) {
    val visibleContributors: Boolean
        get() = repoUiModel?.hasContributors == true

    val visibleEmptyContributor: Boolean
        get() = repoUiModel?.hasContributors == false
}