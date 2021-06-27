package com.example.shared.data.repository.repo

import com.example.shared.data.source.local.db.AppDatabase
import com.example.shared.data.source.remote.api.GitHubService
import com.example.shared.utils.CoroutinesDispatcherProvider
import com.example.shared.utils.NullableSuspendWrapper
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

    fun loadReposWrapper(owner: String, force: Boolean) =
        SuspendWrapper(scope) { repository.loadRepos(owner, force) }

    fun loadRepoWrapper(owner: String, name: String) =
        NullableSuspendWrapper(scope) { repository.loadRepo(owner, name) }

    fun loadContributorsWrapper(owner: String, name: String) =
        SuspendWrapper(scope) { repository.loadContributors(owner, name) }

    fun searchWrapper(query: String, page: Int?) =
        SuspendWrapper(scope) { repository.search(query, page) }

    companion object Provider {
        fun get(
            gitHubService: GitHubService,
            appDatabase: AppDatabase
        ): RepoRepositoryIos {
            val dispatchers = CoroutinesDispatcherProvider.sharedInstance
            val repository = RepoRepository(
                gitHubService,
                appDatabase,
                dispatchers.default
            )
            return RepoRepositoryIos(repository, dispatchers.default)
        }
    }
}