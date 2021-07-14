package com.example.android.app.ui.user

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.DividerItemDecoration
import com.example.android.app.databinding.FragmentUserBinding
import com.example.android.app.ui.common.RetryView
import com.example.android.app.ui.repo.list.RepoListAdapter
import com.example.android.app.ui.repo.list.RepoListAdapter.RepoClickListener
import com.example.android.app.ui.user.UserFragmentDirections.Companion.toRepoFragment
import com.example.android.app.utils.navigateSafely
import com.example.shared.data.source.local.db.Repo
import com.github.droibit.komol.Komol
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class UserFragment : Fragment(), RepoClickListener, RetryView.Callback {
    private val args by navArgs<UserFragmentArgs>()

    private val viewModel: UserViewModel by viewModels()

    private var _binding: FragmentUserBinding? = null
    private val binding get() = requireNotNull(_binding)

    private lateinit var repoListAdapter: RepoListAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentUserBinding.inflate(inflater, container, false).also {
            it.lifecycleOwner = viewLifecycleOwner
            it.viewModel = viewModel
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRepoRecyclerView()

        subscribeGetUserUiModel()
    }

    private fun initRepoRecyclerView() {
        repoListAdapter = RepoListAdapter(this, visibleRepoOwner = false)
        binding.repoList.apply {
            adapter = repoListAdapter
            addItemDecoration(
                DividerItemDecoration(
                    requireContext(),
                    DividerItemDecoration.VERTICAL
                )
            )
        }
    }

    private fun subscribeGetUserUiModel() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.getUserUiModel
                .flowWithLifecycle(viewLifecycleOwner.lifecycle)
                .collect { getUserUiModel ->
                    getUserUiModel.userUiModel?.let {
                        binding.user = it.user
                        repoListAdapter.submitList(it.repos)
                    }
                }
        }
        viewModel.getUser(args.login)
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

    override fun onRepoClick(repo: Repo) {
        Komol.d("repo: ${repo.fullName}")
        findNavController()
            .navigateSafely(toRepoFragment(repo.ownerLogin, repo.name))
    }

    override fun retry() {
        viewModel.getUser(args.login)
    }
}