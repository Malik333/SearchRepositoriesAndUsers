package com.malikbisic.searchrepositoriesandusers.ui.landing.search

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.malikbisic.searchrepositoriesandusers.R

class RepositoryAdapter: RecyclerView.Adapter<RepositoryAdapter.RepositoryViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RepositoryViewHolder {
        TODO("Not yet implemented")
    }

    override fun onBindViewHolder(holder: RepositoryViewHolder, position: Int) {
        TODO("Not yet implemented")
    }

    override fun getItemCount(): Int {
        TODO("Not yet implemented")
    }


    class RepositoryViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        companion object {
            fun from(parent: ViewGroup): RepositoryViewHolder {
                val inflater = LayoutInflater.from(parent.context)
                val itemView = inflater.inflate(R.layout.repository_item, parent, false)
                return RepositoryViewHolder(itemView)
            }
        }
    }

}