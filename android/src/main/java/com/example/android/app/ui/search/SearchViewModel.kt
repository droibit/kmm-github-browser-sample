package com.example.android.app.ui.search

import androidx.annotation.MainThread
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.chrynan.inject.Inject
import com.example.android.app.ui.common.Message
import com.example.shared.data.repository.repo.RepoRepository
import com.example.shared.data.source.local.db.Repo
import com.example.shared.model.GitHubError
import com.example.shared.model.PagedRepoSearchResult
import com.github.droibit.komol.Komol
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

@HiltViewModel
class SearchViewModel(
    val query: MutableStateFlow<String>,
    private var lastQuery: String,
    private val repoRepository: RepoRepository,
    private val searchResultUiModelSink: MutableStateFlow<SearchResultUiModel>
) : ViewModel() {

    val searchResultUiModel: StateFlow<SearchResultUiModel> get() = searchResultUiModelSink

    @Inject
    constructor(
        repoRepository: RepoRepository,
    ) : this(
        query = MutableStateFlow(""),
        lastQuery = "",
        repoRepository = repoRepository,
        searchResultUiModelSink = MutableStateFlow(SearchResultUiModel())
    )

    fun searchWithNewQuery() {
        if (query.value == lastQuery) {
            return
        }
        search()
    }

    @MainThread
    fun search() {
        if (searchResultUiModelSink.value.inProgress) {
            return
        }

        val query = this.query.value.lowercase().filter { !it.isWhitespace() }
        if (query.isEmpty()) {
            lastQuery = query
            emit(SearchResultUiModel())
            return
        }

        val mayPaging = query == lastQuery
        val currentSearchResult: PagedRepoSearchResult? = if (mayPaging) {
            searchResultUiModelSink.value
                .searchResult?.takeIf { it.nextPage != null } ?: return
        } else {
            null
        }
        emit(SearchResultUiModel(inProgress = true, searchResult = currentSearchResult))

        val page = currentSearchResult?.nextPage
        Komol.d("query=$query, page=${page ?: "null"}")

        viewModelScope.launch {
            val result = try {
                val newSearchResult = repoRepository.search(query, page)
                val currentRepos: List<Repo> = if (mayPaging) {
                    currentSearchResult?.repos ?: emptyList()
                } else {
                    emptyList()
                }
                lastQuery = query
                SearchResultUiModel(searchResult = newSearchResult.merge(currentRepos))
            } catch (e: GitHubError) {
                SearchResultUiModel(error = Message(requireNotNull(e.message)))
            }
            emit(result)
        }
    }

    @MainThread
    private fun emit(uiModel: SearchResultUiModel) {
        searchResultUiModelSink.value = uiModel
    }
}