package com.example.android.app.ui.search

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.ConcatAdapter
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.android.app.databinding.FragmentSearchBinding
import com.example.android.app.ui.repo.list.MoreLoadingAdapter
import com.example.android.app.ui.repo.list.RepoListAdapter
import com.example.android.app.utils.toggleSofInputVisibility
import com.example.shared.data.source.local.db.Repo
import com.github.droibit.komol.Komol
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SearchFragment : Fragment(), RepoListAdapter.RepoClickListener {

    private val viewModel: SearchViewModel by viewModels()

    private var _binding: FragmentSearchBinding? = null
    private val binding get() = requireNotNull(_binding)

    private lateinit var repoListAdapter: RepoListAdapter
    private lateinit var moreLoadingAdapter: MoreLoadingAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSearchBinding.inflate(inflater, container, false).also {
            it.lifecycleOwner = viewLifecycleOwner
            it.viewModel = viewModel
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRepoRecyclerView()
        initSearchEditText()

        subscribeSearchResultUiModel()
    }

    private fun initRepoRecyclerView() {
        repoListAdapter = RepoListAdapter(this)
        moreLoadingAdapter = MoreLoadingAdapter()
        binding.repoList.apply {
            val concatAdapter = ConcatAdapter(repoListAdapter, moreLoadingAdapter)
            adapter = concatAdapter
            addItemDecoration(
                DividerItemDecoration(
                    requireContext(),
                    DividerItemDecoration.VERTICAL
                )
            )

            addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    val layoutManager = recyclerView.layoutManager as LinearLayoutManager
                    val lastPosition = layoutManager.findLastVisibleItemPosition()
                    if (lastPosition == concatAdapter.itemCount - 1) {
                        viewModel.search()
                    }
                }
            })
        }
    }

    private fun initSearchEditText() {
        binding.searchTextEdit.setOnEditorActionListener { v, actionId, _ ->
            Komol.d("actionId: $actionId")
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                search(v)
                true
            } else {
                false
            }
        }

        binding.searchTextEdit.setOnKeyListener { v, keyCode, event ->
            if (event.action == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_ENTER) {
                search(v)
                true
            } else {
                false
            }
        }
    }

    private fun search(view: View) {
        view.toggleSofInputVisibility(false)
        view.clearFocus()

        viewModel.searchWithNewQuery()
    }

    private fun subscribeSearchResultUiModel() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.searchResultUiModel
                .flowWithLifecycle(viewLifecycleOwner.lifecycle)
                .collect { searchResultUiModel ->
                    searchResultUiModel.searchResult?.let {
                        repoListAdapter.submitList(it.repos)
                    }
                    moreLoadingAdapter.visibleIndicator = searchResultUiModel.pagingInProgress
                }
        }
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

    override fun onRepoClick(repo: Repo) {
        Komol.d("onRepoClick(repo: ${repo.fullName})")
    }
}