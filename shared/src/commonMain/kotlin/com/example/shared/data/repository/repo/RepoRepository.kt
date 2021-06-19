package com.example.shared.data.repository.repo

import com.chrynan.inject.Inject
import com.chrynan.inject.Singleton
import com.example.shared.data.source.local.db.AppDatabase
import com.example.shared.data.source.local.db.Repo
import com.example.shared.data.source.remote.api.GitHubApiError
import com.example.shared.data.source.remote.api.GitHubService
import com.example.shared.model.GitHubError
import com.example.shared.utils.CoroutinesDispatcherProvider
import com.github.droibit.komol.Komol
import kotlinx.coroutines.withContext

@Singleton
class RepoRepository @Inject constructor(
    private val gitHubService: GitHubService,
    private val appDatabase: AppDatabase,
    private val dispatcherProvider: CoroutinesDispatcherProvider
) {
    suspend fun loadRepos(owner: String, force: Boolean): List<Repo> =
        withContext(dispatcherProvider.io) {
            val repos = appDatabase.repoQueries.loadRepositories(owner)
                .executeAsList()
            if (!force && repos.isNotEmpty()) {
                return@withContext repos
            }

            try {
                val response = gitHubService.getRepos(owner)
                val body = response.body ?: return@withContext emptyList()
                val newRepos = body.map {
                    Repo(
                        it.id,
                        it.name,
                        it.fullName,
                        it.description,
                        it.stargazersCount.toLong(),
                        it.owner.login,
                        it.owner.url
                    )
                }

                if (newRepos.isNotEmpty()) {
                    appDatabase.transaction {
                        newRepos.forEach { appDatabase.repoQueries.insert(it) }
                    }
                }
                newRepos
            } catch (e: GitHubApiError) {
                Komol.e(e)
                throw GitHubError(e)
            }
        }
}