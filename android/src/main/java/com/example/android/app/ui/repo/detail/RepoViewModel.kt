package com.example.android.app.ui.repo.detail

import androidx.annotation.MainThread
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.chrynan.inject.Inject
import com.example.shared.data.repository.repo.RepoRepository
import com.example.shared.model.GitHubError
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.supervisorScope

@HiltViewModel
class RepoViewModel(
    private val repoRepository: RepoRepository,
    private val getRepoUiModelSink: MutableStateFlow<GetRepoUiModel>
) : ViewModel() {
    val getRepoUiModel: StateFlow<GetRepoUiModel>
        get() = getRepoUiModelSink

    @Inject
    constructor(
        repoRepository: RepoRepository
    ) : this(
        repoRepository,
        getRepoUiModelSink = MutableStateFlow(GetRepoUiModel())
    )

    @MainThread
    fun getRepo(owner: String, name: String) {
        if (getRepoUiModelSink.value.inProgress) {
            return
        }
        getRepoUiModelSink.value = GetRepoUiModel(inProgress = true)

        viewModelScope.launch {
            getRepoUiModelSink.value = try {
                val (repo, contributors) = supervisorScope {
                    val repo = async { repoRepository.loadRepo(owner, name) }
                    val contributors = async { repoRepository.loadContributors(owner, name) }
                    repo.await() to contributors.await()
                }

                if (repo == null) {
                    GetRepoUiModel(error = "Unknown repo: $owner/$name")
                } else {
                    GetRepoUiModel(
                        repoUiModel = RepoUiModel(repo, contributors)
                    )
                }
            } catch (e: GitHubError) {
                GetRepoUiModel(error = requireNotNull(e.message))
            }
        }
    }
}