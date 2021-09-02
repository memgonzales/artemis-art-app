package com.mobdeve.gonzales.lee.ong.artemis

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import java.util.*

class FeedFollowedAdapter(private val dataPosts: ArrayList<Post>) :
    RecyclerView.Adapter<FeedViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FeedViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val itemView = inflater.inflate(R.layout.item_feed, parent, false)

        val feedViewHolder = FeedViewHolder(itemView)

        itemView.setOnClickListener { view ->
            val intent = Intent(view.context, ViewPostFollowedActivity::class.java)

            intent.putExtra(
                Keys.KEY_PROFILE_PICTURE.name,
                dataPosts[feedViewHolder.bindingAdapterPosition].getProfilePicture()
            )
            intent.putExtra(
                Keys.KEY_USERNAME.name,
                dataPosts[feedViewHolder.bindingAdapterPosition].getUsername()
            )
            intent.putExtra(
                Keys.KEY_POST.name,
                dataPosts[feedViewHolder.bindingAdapterPosition].getPostImg()
            )
            intent.putExtra(
                Keys.KEY_TITLE.name,
                dataPosts[feedViewHolder.bindingAdapterPosition].getTitle()
            )
            intent.putExtra(
                Keys.KEY_NUM_UPVOTES.name,
                dataPosts[feedViewHolder.bindingAdapterPosition].getNumUpvotes()
            )
            intent.putExtra(
                Keys.KEY_NUM_COMMENTS.name,
                dataPosts[feedViewHolder.bindingAdapterPosition].getNumComments()
            )
            intent.putExtra(
                Keys.KEY_DATE_POSTED.name,
                dataPosts[feedViewHolder.bindingAdapterPosition].getDatePosted()
            )
            intent.putExtra(
                Keys.KEY_MEDIUM.name,
                dataPosts[feedViewHolder.bindingAdapterPosition].getMedium()
            )
            intent.putExtra(
                Keys.KEY_DIMENSIONS.name,
                dataPosts[feedViewHolder.bindingAdapterPosition].getDimensions()
            )
            intent.putExtra(
                Keys.KEY_DESCRIPTION.name,
                dataPosts[feedViewHolder.bindingAdapterPosition].getDescription()
            )
            intent.putExtra(
                Keys.KEY_TAGS.name,
                dataPosts[feedViewHolder.bindingAdapterPosition].getTags()
            )
            intent.putExtra(
                Keys.KEY_BOOKMARK.name,
                dataPosts[feedViewHolder.bindingAdapterPosition].getBookmark()
            )
            intent.putExtra(
                Keys.KEY_UPVOTE.name,
                dataPosts[feedViewHolder.bindingAdapterPosition].getUpvote()
            )

            view.context.startActivity(intent)
        }

        feedViewHolder.setItemFeedCommentOnClickListener { view ->
            val intent = Intent(view.context, ViewCommentsFollowedActivity::class.java)
            view.context.startActivity(intent)
        }

        return feedViewHolder
    }

    override fun onBindViewHolder(holder: FeedViewHolder, position: Int) {
        val currentPost = dataPosts[position]
        holder.setItemFeedProfilePic(currentPost.getProfilePicture())
        holder.setItemFeedUsername(currentPost.getUsername())
        holder.setItemFeedPost(currentPost.getPostImg())
        holder.setItemFeedTitle(currentPost.getTitle())
        holder.setItemFeedUpvoteCounter(currentPost.getNumUpvotes().toString() + " upvotes")
        holder.setItemFeedComments(currentPost.getNumComments().toString() + " comments")
        holder.setItemFeedBookmark(currentPost.getBookmark())
        holder.setItemFeedUpvote(currentPost.getUpvote())

        holder.setItemFeedBookmarkOnClickListener {
            currentPost.setBookmark(!currentPost.getBookmark())
            holder.setItemFeedBookmark(currentPost.getBookmark())
        }

        holder.setItemFeedUpvoteOnClickListener {
            if (currentPost.getUpvote()) {
                currentPost.setUpvote(false)
                currentPost.setNumUpvotes(currentPost.getNumUpvotes() - 1)
                holder.setItemFeedUpvoteCounter(currentPost.getNumUpvotes().toString() + " upvotes")
                holder.setItemFeedUpvote(currentPost.getUpvote())
            } else {
                currentPost.setUpvote(true)
                currentPost.setNumUpvotes(currentPost.getNumUpvotes() + 1)
                holder.setItemFeedUpvoteCounter(currentPost.getNumUpvotes().toString() + " upvotes")
                holder.setItemFeedUpvote(currentPost.getUpvote())
            }
        }

        holder.setItemFeedShareOnClickListener { view ->
            Toast.makeText(view.context,"Post shared on Facebook", Toast.LENGTH_SHORT).show()
        }

        holder.setItemFeedProfileOnClickListener { view ->
            val intent = Intent(view.context, ViewUserActivity::class.java)

            intent.putExtra(
                Keys.KEY_PROFILE_PICTURE.name,
                currentPost.getProfilePicture()
            )
            intent.putExtra(
                Keys.KEY_USERNAME.name,
                currentPost.getUsername()
            )
            intent.putExtra(
                Keys.KEY_BIO.name,
                "Dummy bio"
            )

            view.context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return dataPosts.size
    }
}