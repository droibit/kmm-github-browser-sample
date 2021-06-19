package com.example.shared.data.repository.repo

import com.chrynan.inject.Inject
import com.chrynan.inject.Singleton
import com.example.shared.data.source.local.db.AppDatabase
import com.example.shared.data.source.local.db.Contributor
import com.example.shared.data.source.local.db.Repo
import com.example.shared.data.source.remote.api.GitHubApiError
import com.example.shared.data.source.remote.api.GitHubService
import com.example.shared.data.source.remote.api.response.body.ContributorResponseBody
import com.example.shared.data.source.remote.api.response.body.RepositoryResponseBody
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
                val body = response.body ?: emptyList()
                return@withContext body.map(RepositoryResponseBody::toRepo)
                    .also { newRepos ->
                        if (newRepos.isNotEmpty()) {
                            appDatabase.transaction {
                                newRepos.forEach { appDatabase.repoQueries.insert(it) }
                            }
                        }
                    }
            } catch (e: GitHubApiError) {
                Komol.e(e)
                throw GitHubError(e)
            }
        }

    suspend fun loadRepo(owner: String, name: String): Repo? = withContext(dispatcherProvider.io) {
        val repo = appDatabase.repoQueries.loadRepo(owner, name)
            .executeAsOneOrNull()
        if (repo != null) {
            return@withContext repo
        }

        try {
            val response = gitHubService.getRepo(owner, name)
            val body = response.body ?: return@withContext null
            return@withContext body.toRepo()
                .also {
                    appDatabase.repoQueries.insert(it)
                }
        } catch (e: GitHubApiError) {
            Komol.e(e)
            throw GitHubError(e)
        }
    }

    suspend fun loadContributors(owner: String, name: String): List<Contributor> =
        withContext(dispatcherProvider.io) {
            val contributors = appDatabase.contributorQueries.loadContributors(owner, name)
                .executeAsList()
            if (contributors.isNotEmpty()) {
                return@withContext contributors
            }

            try {
                val response = gitHubService.getContributors(owner, name)
                val body = response.body ?: emptyList()
                return@withContext body.map {
                    it.toContributor(owner, name)
                }.also { newContributors ->
                    if (newContributors.isNotEmpty()) {
                        appDatabase.transaction {
                            appDatabase.repoQueries.createRepoIfNotExists(
                                Repo(
                                    id = -1L,
                                    name = name,
                                    fullName = "$owner/$name",
                                    description = "",
                                    stars = 0,
                                    ownerLogin = owner,
                                    ownerUrl = null
                                )
                            )
                            newContributors.forEach {
                                appDatabase.contributorQueries.insert(it)
                            }
                        }
                    }
                }
            } catch (e: GitHubApiError) {
                Komol.e(e)
                throw GitHubError(e)
            }
        }
}

private fun RepositoryResponseBody.toRepo() = Repo(
    id,
    name,
    fullName,
    description,
    stargazersCount.toLong(),
    owner.login,
    owner.url
)

private fun ContributorResponseBody.toContributor(
    repoName: String,
    repoOwner: String
) = Contributor(
    repoName,
    repoOwner,
    login,
    contributions.toLong(),
    avatarUrl
)