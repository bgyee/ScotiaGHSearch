package com.interview.scotiaghsearch.ui.list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.interview.scotiaghsearch.data.model.UserRepo
import com.interview.scotiaghsearch.databinding.ItemRepoListBinding

class RepoListAdapter(private val onItemClick: (userRepo: UserRepo) -> Unit) :
    ListAdapter<UserRepo, RepoListAdapter.RepoViewHolder>(RepoDiffCallback) {

    class RepoViewHolder(val binding: ItemRepoListBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RepoViewHolder {
        return RepoViewHolder(
            ItemRepoListBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: RepoViewHolder, position: Int) {
        with(holder.binding) {
            val repo = getItem(position)
            repoNameView.text = repo.name
            repoDescriptionView.text = repo.description
            root.setOnClickListener {
                onItemClick(repo)
            }
        }
    }
}

object RepoDiffCallback : DiffUtil.ItemCallback<UserRepo>() {
    override fun areItemsTheSame(oldItem: UserRepo, newItem: UserRepo): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: UserRepo, newItem: UserRepo): Boolean {
        return oldItem.name == newItem.name && oldItem.updatedAt == newItem.updatedAt
    }
}