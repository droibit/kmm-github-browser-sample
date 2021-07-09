package com.example.android.app.ui.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import androidx.fragment.app.Fragment
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

@AndroidEntryPoint
class SearchFragment : Fragment(), RepoListAdapter.RepoClickListener {

    private var _binding: FragmentSearchBinding? = null
    private val binding get() = requireNotNull(_binding)

    private lateinit var repoListAdapter: RepoListAdapter
    private lateinit var moreLoadingAdapter: MoreLoadingAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSearchBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRepoRecyclerView()
        initSearchEditText()

        repoListAdapter.submitList(
            List(10) {
                Repo(
                    id = it.toLong(),
                    name = "kmm-github-browser-sample",
                    fullName = "droibit/kmm-github-browser-sample",
                    description = "Github Browser sample using Kotlin Multiplatform Mobile.",
                    stars = it.toLong(),
                    ownerLogin = "droibit",
                    ownerUrl = ""
                )
            }
        )
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
                        // TODO: Paging
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
    }

    private fun search(view: View) {
        val query = binding.searchTextEdit.text.toString()
        Komol.d("search: $query")

        view.toggleSofInputVisibility(false)
        // view.clearFocus()
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

    override fun onRepoClick(repo: Repo) {
        Komol.d("onRepoClick(repo: ${repo.fullName})")
    }
}