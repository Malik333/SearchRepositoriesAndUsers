package com.malikbisic.searchrepositoriesandusers.ui.landing.search

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.malikbisic.searchrepositoriesandusers.R
import com.malikbisic.searchrepositoriesandusers.glide.GlideApp
import com.malikbisic.searchrepositoriesandusers.model.Author
import kotlinx.android.synthetic.main.user_item.view.*

class UserAdapter: RecyclerView.Adapter<UserAdapter.UserViewHolder>() {

    private var users: List<Author> = listOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        return UserViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        val item = users[position]
        holder.bind(item)
    }

    override fun getItemCount(): Int {
        return users.size
    }

    fun setUserItems(users: List<Author>) {
        this.users = users
        notifyDataSetChanged()
    }

    class UserViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {

        fun bind(item: Author) {
            itemView.apply {
                GlideApp.with(userAvatar)
                    .load(item.avatarUrl)
                    .into(userAvatar)

                userName.text = item.authorName

            }
        }

        companion object {
            fun from(parent: ViewGroup): UserViewHolder {
                val inflater = LayoutInflater.from(parent.context)
                val itemView = inflater.inflate(R.layout.user_item, parent, false)
                return UserViewHolder(itemView)
            }
        }
    }

}