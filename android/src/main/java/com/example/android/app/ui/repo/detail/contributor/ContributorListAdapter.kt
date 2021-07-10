package com.example.android.app.ui.repo.detail.contributor

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.android.app.databinding.ListItemContributorBinding
import com.example.shared.data.source.local.db.Contributor

class ContributorListAdapter(
    private val clickListener: ContributorClickListener
) : ListAdapter<Contributor, ContributorListAdapter.ViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(parent).apply {
            itemView.setOnClickListener {
                clickListener.onContributorClick(getItem(bindingAdapterPosition))
            }
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindTo(contributor = getItem(position))
    }

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Contributor>() {
            override fun areItemsTheSame(oldItem: Contributor, newItem: Contributor): Boolean {
                return (oldItem.login == newItem.login)
                    && (oldItem.repoName == newItem.repoName)
                    && (oldItem.repoOwner == newItem.repoOwner)
            }

            override fun areContentsTheSame(oldItem: Contributor, newItem: Contributor): Boolean {
                return oldItem == newItem
            }
        }
    }

    fun interface ContributorClickListener {
        fun onContributorClick(contributor: Contributor)
    }

    class ViewHolder(
        private val binding: ListItemContributorBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        constructor(parent: ViewGroup) : this(
            binding = ListItemContributorBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )

        fun bindTo(contributor: Contributor) {
            binding.contributor = contributor
        }
    }
}