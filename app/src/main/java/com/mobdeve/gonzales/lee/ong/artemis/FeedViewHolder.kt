package com.mobdeve.gonzales.lee.ong.artemis

import android.content.res.ColorStateList
import android.view.View
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import de.hdodenhof.circleimageview.CircleImageView

class FeedViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    private val civItemFeedProfilePic: CircleImageView
    private val tvItemFeedUsername: TextView
    private val ivItemFeedPost: ImageView
    private val tvItemFeedTitle: TextView
    private val tvItemFeedUpvoteCounter: TextView
    private val tvItemFeedComments: TextView
    private val ibItemFeedBookmark: ImageButton

    fun setItemFeedProfilePic(picture: Int) {
        civItemFeedProfilePic.setImageResource(picture)
    }

    fun setItemFeedUsername(name: String?) {
        tvItemFeedUsername.text = name
    }

    fun setItemFeedPost(post: Int) {
        ivItemFeedPost.setImageResource(post)
    }

    fun setItemFeedTitle(title: String?) {
        tvItemFeedTitle.text = title
    }

    fun setItemFeedUpvoteCounter(upvotes: String?) {
        tvItemFeedUpvoteCounter.text = upvotes
    }

    fun setItemFeedComments(comments: String?) {
        tvItemFeedComments.text = comments
    }

    fun setItemFeedBookmarkOnClickListener(onClickListener: View.OnClickListener) {
        ibItemFeedBookmark.setOnClickListener(onClickListener)
    }

    fun setItemFeedBookmark(bookmark: Boolean) {
        if (bookmark) {
            ibItemFeedBookmark.setImageResource(R.drawable.outline_bookmark_24)
            ibItemFeedBookmark.imageTintList = ColorStateList.valueOf(
                ContextCompat.getColor(ibItemFeedBookmark.context, R.color.pinkish_purple)
            )
        } else {
            ibItemFeedBookmark.setImageResource(R.drawable.outline_bookmark_border_24)
            ibItemFeedBookmark.imageTintList = ColorStateList.valueOf(
                ContextCompat.getColor(ibItemFeedBookmark.context, R.color.default_gray)
            )
        }
    }

    init {
        civItemFeedProfilePic = itemView.findViewById(R.id.civ_item_feed_profile_pic)
        tvItemFeedUsername = itemView.findViewById(R.id.tv_item_feed_username)
        ivItemFeedPost = itemView.findViewById(R.id.iv_item_feed_post)
        tvItemFeedTitle = itemView.findViewById(R.id.tv_item_feed_title)
        tvItemFeedUpvoteCounter = itemView.findViewById(R.id.tv_item_feed_upvote_counter)
        tvItemFeedComments = itemView.findViewById(R.id.tv_item_feed_comments)
        ibItemFeedBookmark = itemView.findViewById(R.id.ib_item_feed_bookmark)
    }
}