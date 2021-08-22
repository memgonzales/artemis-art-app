package com.mobdeve.gonzales.lee.ong.artemis

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import java.util.*

class FeedAdapter(private val dataPosts: ArrayList<Post>) :
    RecyclerView.Adapter<FeedViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FeedViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val itemView = inflater.inflate(R.layout.item_feed, parent, false)

        val feedViewHolder = FeedViewHolder(itemView)

        itemView.setOnClickListener { view ->
            val intent = Intent(view.context, ViewPostActivity::class.java)

            intent.putExtra(
                Keys.KEY_PROFILE_PICTURE.name,
                dataPosts[feedViewHolder.bindingAdapterPosition].profilePicture
            )
            intent.putExtra(
                Keys.KEY_USERNAME.name,
                dataPosts[feedViewHolder.bindingAdapterPosition].username
            )
            intent.putExtra(
                Keys.KEY_POST.name,
                dataPosts[feedViewHolder.bindingAdapterPosition].post
            )
            intent.putExtra(
                Keys.KEY_TITLE.name,
                dataPosts[feedViewHolder.bindingAdapterPosition].title
            )
            intent.putExtra(
                Keys.KEY_UPVOTES.name,
                dataPosts[feedViewHolder.bindingAdapterPosition].numUpvotes
            )
            intent.putExtra(
                Keys.KEY_COMMENTS.name,
                dataPosts[feedViewHolder.bindingAdapterPosition].numComments
            )
            intent.putExtra(
                Keys.KEY_DATE_POSTED.name,
                dataPosts[feedViewHolder.bindingAdapterPosition].datePosted.toStringFull()
            )
            intent.putExtra(
                Keys.KEY_TYPE.name,
                dataPosts[feedViewHolder.bindingAdapterPosition].type
            )
            intent.putExtra(
                Keys.KEY_DIM_HEIGHT.name,
                dataPosts[feedViewHolder.bindingAdapterPosition].dimHeight
            )
            intent.putExtra(
                Keys.KEY_DIM_WIDTH.name,
                dataPosts[feedViewHolder.bindingAdapterPosition].dimWidth
            )
            intent.putExtra(
                Keys.KEY_DESCRIPTION.name,
                dataPosts[feedViewHolder.bindingAdapterPosition].description
            )
            intent.putExtra(
                Keys.KEY_TAGS.name,
                dataPosts[feedViewHolder.bindingAdapterPosition].tags
            )
            intent.putExtra(
                Keys.KEY_BOOKMARK.name,
                dataPosts[feedViewHolder.bindingAdapterPosition].bookmark
            )

            view.context.startActivity(intent)
        }

        return feedViewHolder
    }

    override fun onBindViewHolder(holder: FeedViewHolder, position: Int) {
        val currentPost = dataPosts[position]
        holder.setItemFeedProfilePic(currentPost.profilePicture)
        holder.setItemFeedUsername(currentPost.username)
        holder.setItemFeedPost(currentPost.post)
        holder.setItemFeedTitle(currentPost.title)
        holder.setItemFeedUpvoteCounter(currentPost.numUpvotes.toString() + " upvotes")
        holder.setItemFeedComments(currentPost.numComments.toString() + " comments")
        holder.setItemFeedBookmark(currentPost.bookmark)

        holder.setItemFeedBookmarkOnClickListener {
            currentPost.bookmark = !currentPost.bookmark
            holder.setItemFeedBookmark(currentPost.bookmark)
        }
    }

    override fun getItemCount(): Int {
        return dataPosts.size
    }
}
