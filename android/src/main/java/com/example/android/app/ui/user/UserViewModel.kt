package com.example.android.app.ui.user

import androidx.annotation.MainThread
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.chrynan.inject.Inject
import com.example.android.app.R
import com.example.android.app.ui.common.Message
import com.example.shared.data.repository.repo.RepoRepository
import com.example.shared.data.repository.user.UserRepository
import com.example.shared.model.GitHubError
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.supervisorScope

@HiltViewModel
class UserViewModel(
    private val userRepository: UserRepository,
    private val repoRepository: RepoRepository,
    private val getUserUiModelSink: MutableStateFlow<GetUserUiModel>
) : ViewModel() {
    val getUserUiModel: StateFlow<GetUserUiModel> get() = getUserUiModelSink

    @Inject
    constructor(
        userRepository: UserRepository,
        repoRepository: RepoRepository
    ) : this(
        userRepository,
        repoRepository,
        getUserUiModelSink = MutableStateFlow(GetUserUiModel())
    )

    @MainThread
    fun getUser(login: String) {
        if (getUserUiModelSink.value.inProgress) {
            return
        }
        getUserUiModelSink.value = GetUserUiModel(inProgress = true)

        viewModelScope.launch {
            getUserUiModelSink.value = try {
                val (user, repos) = supervisorScope {
                    val user = async { userRepository.loadUser(login) }
                    val repos = async { repoRepository.loadRepos(login, force = false) }
                    user.await() to repos.await()
                }

                if (user == null) {
                    GetUserUiModel(error = Message(R.string.error_unknown_user, login))
                } else {
                    GetUserUiModel(userUiModel = UserUiModel(user, repos))
                }
            } catch (e: GitHubError) {
                GetUserUiModel(error = Message(requireNotNull(e.message)))
            }
        }
    }
}