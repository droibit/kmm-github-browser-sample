package com.example.android.app.ui.user

import com.example.android.app.ui.common.Message
import com.example.shared.data.source.local.db.Repo
import com.example.shared.data.source.local.db.User

data class UserUiModel(
    val user: User,
    val repos: List<Repo>
) {
    val hasRepos: Boolean get() = repos.isNotEmpty()
}

data class GetUserUiModel(
    val inProgress: Boolean = false,
    val userUiModel: UserUiModel? = null,
    val error: Message? = null
) {
    val visibleRepos: Boolean
        get() = userUiModel?.hasRepos == true

    val visibleEmptyRepo: Boolean
        get() = userUiModel?.hasRepos == false
}