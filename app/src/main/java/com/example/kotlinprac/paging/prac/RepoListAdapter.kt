package com.example.kotlinprac.paging.prac

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.kotlinprac.databinding.ItemRepoBinding

class RepoListAdapter: PagingDataAdapter<Repo, RepoListAdapter.RepoViewHolder>(diffUtil) {

    companion object {
        private val diffUtil = object : DiffUtil.ItemCallback<Repo>() {
            override fun areItemsTheSame(oldItem: Repo, newItem: Repo): Boolean =
                oldItem.id == newItem.id
            override fun areContentsTheSame(oldItem: Repo, newItem: Repo): Boolean =
                oldItem == newItem
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RepoListAdapter.RepoViewHolder {
        return RepoViewHolder(ItemRepoBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: RepoListAdapter.RepoViewHolder, position: Int) {
        getItem(position)?.let {
            holder.bind(it)
        }
    }

    inner class RepoViewHolder(
        private val binding: ItemRepoBinding
    ): RecyclerView.ViewHolder(binding.root) {
        fun bind(repo: Repo) {
            binding.apply {
                repository = repo
                executePendingBindings()
            }
        }
    }
}