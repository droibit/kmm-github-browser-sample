package com.example.android.app.ui.repo.list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.android.app.databinding.ListItemRepoBinding
import com.example.shared.data.source.local.db.Repo

class RepoListAdapter : ListAdapter<Repo, RepoListAdapter.ViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(parent)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindTo(repo = getItem(position))
    }

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Repo>() {
            override fun areItemsTheSame(oldItem: Repo, newItem: Repo): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Repo, newItem: Repo): Boolean {
                return oldItem == newItem
            }
        }
    }

    class ViewHolder(
        private val binding: ListItemRepoBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        constructor(parent: ViewGroup) : this(
            binding = ListItemRepoBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )

        fun bindTo(repo: Repo) {
            binding.repo = repo
        }
    }
}