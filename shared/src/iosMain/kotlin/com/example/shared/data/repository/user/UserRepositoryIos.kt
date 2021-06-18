package com.example.shared.data.repository.user

import com.example.shared.data.source.local.db.AppDatabase
import com.example.shared.data.source.remote.api.GitHubService
import com.example.shared.utils.CoroutinesDispatcherProvider
import com.example.shared.utils.NullableSuspendWrapper
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob

class UserRepositoryIos(
    private val repository: UserRepository,
    private val dispatcher: CoroutineDispatcher
) {
    private val supervisorJob = SupervisorJob()
    private val scope: CoroutineScope = CoroutineScope(supervisorJob + dispatcher)

    fun loadUserWrapper(login: String) =
        NullableSuspendWrapper(scope) { repository.loadUser(login) }

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