package com.example.shared.data.repository.user

import com.chrynan.inject.Inject
import com.chrynan.inject.Singleton
import com.example.shared.data.source.local.db.AppDatabase
import com.example.shared.data.source.local.db.User
import com.example.shared.data.source.remote.api.GitHubApiError
import com.example.shared.data.source.remote.api.GitHubService
import com.example.shared.model.GitHubError
import com.example.shared.utils.CoroutinesDispatcherProvider
import com.github.droibit.komol.Komol
import kotlinx.coroutines.withContext
import kotlin.coroutines.cancellation.CancellationException

@Singleton
class UserRepository @Inject constructor(
    private val gitHubService: GitHubService,
    private val appDatabase: AppDatabase,
    private val dispatcherProvider: CoroutinesDispatcherProvider
) {
    @Throws(GitHubError::class, CancellationException::class)
    suspend fun loadUser(login: String): User = withContext(dispatcherProvider.io) {
        val user = appDatabase.userQueries.findByLogin(login)
            .executeAsOneOrNull()
        if (user != null) {
            return@withContext user
        }

        try {
            val response = gitHubService.getUser(login)
            if (!response.isSuccessful || response.body == null) {
                throw GitHubError(response.raw)
            }
            val body = requireNotNull(response.body)
            return@withContext User(
                body.login,
                body.avatarUrl,
                body.name,
                body.company,
                body.reposUrl,
                body.blogUrl
            ).also {
                appDatabase.userQueries.insert(
                    it.login,
                    it.avatarUrl,
                    it.name,
                    it.company,
                    it.reposUrl,
                    it.blog
                )
            }
        } catch (e: GitHubApiError) {
            Komol.e(e)
            throw GitHubError(e)
        }
    }
}