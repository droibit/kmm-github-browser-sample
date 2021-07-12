package com.example.android.app.ui.user

import com.example.shared.data.source.local.db.Repo
import com.example.shared.data.source.local.db.User

data class UserUiModel(
    val user: User,
    val repos: List<Repo>
)

data class GetUserUiModel(
    val inProgress: Boolean = false,
    val userUiModel: UserUiModel? = null,
    val error: String? = null
) {
    val visibleRepos: Boolean
        get() = !userUiModel?.repos.isNullOrEmpty()

    val visibleEmptyRepo: Boolean
        get() = userUiModel?.repos?.isEmpty() == true
}