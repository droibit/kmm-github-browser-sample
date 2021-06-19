package com.example.shared.data.repository.repo

import com.example.shared.data.source.local.db.AppDatabase
import com.example.shared.data.source.local.db.Repo
import com.example.shared.data.source.remote.api.GitHubService
import com.example.shared.utils.CoroutinesDispatcherProvider
import com.example.shared.utils.SuspendWrapper
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob

class RepoRepositoryIos(
    private val repository: RepoRepository,
    private val dispatcher: CoroutineDispatcher
) {
    private val supervisorJob = SupervisorJob()
    private val scope: CoroutineScope = CoroutineScope(supervisorJob + dispatcher)

    fun loadRepos(owner: String, force: Boolean) =
        SuspendWrapper(scope) { repository.loadRepos(owner, force) }

    companion object Provider {
        fun get(
            gitHubService: GitHubService,
            appDatabase: AppDatabase
        ): RepoRepositoryIos {
            val dispatchers = CoroutinesDispatcherProvider.sharedInstance
            val repository = RepoRepository(
                gitHubService,
                appDatabase,
                dispatchers
            )
            return RepoRepositoryIos(repository, dispatchers.io)
        }
    }
}