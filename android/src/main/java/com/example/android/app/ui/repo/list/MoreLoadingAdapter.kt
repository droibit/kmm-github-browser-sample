package com.example.android.app.ui.repo.list

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.android.app.databinding.ListItemMoreLoadingBinding

class MoreLoadingAdapter : RecyclerView.Adapter<MoreLoadingAdapter.ViewHolder>() {
    var visibleIndicator: Boolean = false
        @SuppressLint("NotifyDataSetChanged")
        set(value) {
            if (field != value) {
                field = value
                notifyDataSetChanged()
            }
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(parent)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = Unit

    override fun getItemCount(): Int {
        return if (visibleIndicator) 1 else 0
    }

    class ViewHolder(
        private val binding: ListItemMoreLoadingBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        constructor(parent: ViewGroup) : this(
            binding = ListItemMoreLoadingBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }
}