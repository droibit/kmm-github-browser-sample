package com.example.android.app.ui.repo.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.DividerItemDecoration
import com.example.android.app.databinding.FragmentRepoBinding
import com.example.android.app.ui.common.RetryView
import com.example.android.app.ui.repo.detail.contributor.ContributorListAdapter
import com.example.android.app.ui.repo.detail.contributor.ContributorListAdapter.ContributorClickListener
import com.example.shared.data.source.local.db.Contributor
import com.example.shared.data.source.local.db.Repo
import com.github.droibit.komol.Komol
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class RepoFragment : Fragment(), ContributorClickListener, RetryView.Callback {
    private val viewModel: RepoViewModel by viewModels()

    private val args: RepoFragmentArgs by navArgs()

    private var _binding: FragmentRepoBinding? = null
    private val binding get() = requireNotNull(_binding)

    private lateinit var contributorListAdapter: ContributorListAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentRepoBinding.inflate(inflater, container, false).also {
            it.lifecycleOwner = viewLifecycleOwner
            it.viewModel = viewModel
            it.retryCallback = this
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initContributorRecyclerView()

        subscribeGetRepoUiModel()
    }

    private fun initContributorRecyclerView() {
        contributorListAdapter = ContributorListAdapter(this)
        binding.contributorList.apply {
            adapter = contributorListAdapter
            addItemDecoration(
                DividerItemDecoration(
                    requireContext(),
                    DividerItemDecoration.VERTICAL
                )
            )
        }
    }

    private fun subscribeGetRepoUiModel() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.getRepoUiModel
                .flowWithLifecycle(viewLifecycleOwner.lifecycle)
                .collect { getRepoUiModel ->
                    getRepoUiModel.repoUiModel?.let {
                        binding.repo = it.repo
                        contributorListAdapter.submitList(it.contributors)
                    }
                }
        }
        viewModel.getRepo(args.owner, args.name)
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

    override fun onContributorClick(contributor: Contributor) {
        Komol.d("contributor: ${contributor.login}")
    }

    override fun retry() {
        viewModel.getRepo(args.owner, args.name)
    }
}