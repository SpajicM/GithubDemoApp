package com.marin.githubdemoapp.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.marin.githubdemoapp.databinding.ItemSearchBinding
import com.marin.githubdemoapp.entities.api.Repo

class SearchListAdapter(private val onSearchItemClick: OnSearchItemClick) : RecyclerView.Adapter<SearchListAdapter.ViewHolder>() {

    private val dataSet = mutableListOf<Repo>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemSearchBinding.inflate(inflater)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = dataSet.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bind(dataSet[position])

    inner class ViewHolder(val binding: ItemSearchBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Repo) {
            binding.tvAuthorName.text = item.owner.login
            binding.tvRepoName.text = item.name
            binding.tvForksIssues.text = "Forks: ${item.forks_count} Issues: ${item.open_issues_count}"

            binding.ivAuthorPhoto.setOnClickListener {
                onSearchItemClick.onAuthorClick(item)
            }

             binding.root.setOnClickListener {
                 onSearchItemClick.onItemClick(item)
             }

            Glide.with(binding.root.context).load(item.owner.avatar_url).into(binding.ivAuthorPhoto)

            binding.executePendingBindings()
        }
    }

    interface OnSearchItemClick {
        fun onItemClick(item: Repo)
        fun onAuthorClick(item: Repo)
    }

    fun updateDataset(items: List<Repo>) {
        dataSet.clear()
        dataSet.addAll(items)
        notifyDataSetChanged()
    }
}