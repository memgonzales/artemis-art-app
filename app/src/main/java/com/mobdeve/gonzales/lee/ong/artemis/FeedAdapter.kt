package com.mobdeve.gonzales.lee.ong.artemis

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import java.util.*

class FeedAdapter(private val dataPosts: ArrayList<Post>) :
    RecyclerView.Adapter<FeedViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FeedViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val itemView = inflater.inflate(R.layout.item_feed, parent, false)
        return FeedViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: FeedViewHolder, position: Int) {
        val currentPost = dataPosts[position]
        holder.setItemFeedProfilePic(currentPost.profilePicture)
        holder.setItemFeedUsername(currentPost.username)
        holder.setItemFeedPost(currentPost.post)
        holder.setItemFeedTitle(currentPost.title)
        holder.setItemFeedUpvoteCounter(currentPost.numUpvotes.toString() + " upvotes")
        holder.setItemFeedComments(currentPost.numComments.toString() + " comments")
    }

    override fun getItemCount(): Int {
        return dataPosts.size
    }
}