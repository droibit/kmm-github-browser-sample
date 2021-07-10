package com.example.android.app.ui.repo.detail

import com.example.shared.data.source.local.db.Contributor
import com.example.shared.data.source.local.db.Repo

data class RepoUiModel(
    val repo: Repo,
    val contributors: List<Contributor>
)

data class GetRepoUiModel(
    val inProgress: Boolean = false,
    val repoUiModel: RepoUiModel? = null,
    val error: String? = null
) {
    val visibleContributors: Boolean
        get() = !repoUiModel?.contributors.isNullOrEmpty()

    val visibleEmptyContributor: Boolean
        get() = repoUiModel?.contributors?.isEmpty() == true
}