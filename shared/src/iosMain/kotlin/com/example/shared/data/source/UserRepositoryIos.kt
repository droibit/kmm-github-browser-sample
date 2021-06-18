package com.example.shared.data.source

import com.example.shared.data.repository.user.UserRepository
import com.example.shared.data.source.local.db.AppDatabase
import com.example.shared.data.source.local.db.User
import com.example.shared.data.source.remote.api.GitHubService
import com.example.shared.utils.CoroutinesDispatcherProvider
import com.example.shared.utils.SuspendWrapper
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import kotlin.native.concurrent.freeze

class UserRepositoryIos(
    private val repository: UserRepository,
    private val dispatcher: CoroutineDispatcher
) {
    private val supervisorJob = SupervisorJob()
    private val scope: CoroutineScope = CoroutineScope(supervisorJob + dispatcher)

    init {
        freeze()
    }

    fun loadUserWrapper(login: String): SuspendWrapper<User> {
        return SuspendWrapper(scope) { repository.loadUser(login) }
    }

    companion object Provider {
        fun get(
            gitHubService: GitHubService,
            appDatabase: AppDatabase
        ): UserRepositoryIos {
            val dispatchers = CoroutinesDispatcherProvider.sharedInstance
            val repository = UserRepository(
                gitHubService,
                appDatabase,
                dispatchers
            )
            return UserRepositoryIos(repository, dispatchers.io)
        }
    }
}