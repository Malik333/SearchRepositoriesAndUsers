package com.malikbisic.searchrepositoriesandusers.ui.landing.search

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.malikbisic.searchrepositoriesandusers.R
import com.malikbisic.searchrepositoriesandusers.glide.GlideApp
import com.malikbisic.searchrepositoriesandusers.model.Repository
import kotlinx.android.synthetic.main.repository_item.view.*

class RepositoryAdapter: RecyclerView.Adapter<RepositoryAdapter.RepositoryViewHolder>() {

    private var repositories: List<Repository> = listOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RepositoryViewHolder {
        return RepositoryViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: RepositoryViewHolder, position: Int) {
        val item = repositories[position]
        holder.bind(item)
    }

    override fun getItemCount(): Int {
       return repositories.size
    }

    fun setRepositoryItems(repositories: List<Repository>) {
        this.repositories = repositories
        notifyDataSetChanged()
    }


    class RepositoryViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {

        fun bind(item: Repository) {

            itemView.setOnClickListener {
                val directions =
                    SearchScreenFragmentDirections.actionSearchFragmentToRepositoryDetailsFragment(item.author?.authorName!!, item.repositoryName!!)
                it.findNavController().navigate(directions)
            }

            itemView.apply {
                GlideApp.with(user_avatar)
                    .load(item.author?.avatarUrl)
                    .into(user_avatar)

                user_name.text = item.author?.authorName
                repo_name.text = item.repositoryName
                watchers_count.text = item.watchersCount.toString()
                forks_count.text = item.forksCount.toString()
                issues_count.text = item.issuesCount.toString()

            }
        }

        companion object {
            fun from(parent: ViewGroup): RepositoryViewHolder {
                val inflater = LayoutInflater.from(parent.context)
                val itemView = inflater.inflate(R.layout.repository_item, parent, false)
                return RepositoryViewHolder(itemView)
            }
        }
    }

}