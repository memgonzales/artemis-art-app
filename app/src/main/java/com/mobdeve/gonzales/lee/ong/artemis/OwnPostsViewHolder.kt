package com.mobdeve.gonzales.lee.ong.artemis

import android.content.res.ColorStateList
import android.view.View
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import de.hdodenhof.circleimageview.CircleImageView

class OwnPostsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    private val civOwnPostProfilePic: CircleImageView
    private val tvOwnPostUsername: TextView

    private val ivOwnPostPost: ImageView
    private val tvOwnPostTitle: TextView
    private val tvOwnPostUpvoteCounter: TextView
    private val tvOwnPostComments: TextView

    private val ivOwnPostHighlight: ImageView
    private val tvOwnPostHighlight: TextView
    private val clOwnPostHighlight: ConstraintLayout
    private val clOwnPostComment: ConstraintLayout

    init {
        civItemFeedProfilePic = itemView.findViewById(R.id.civ_item_feed_profile_pic)
        tvItemFeedUsername = itemView.findViewById(R.id.tv_item_feed_username)
        ivItemFeedPost = itemView.findViewById(R.id.iv_item_feed_post)
        tvItemFeedTitle = itemView.findViewById(R.id.tv_item_feed_title)
        tvItemFeedUpvoteCounter = itemView.findViewById(R.id.tv_item_feed_upvote_counter)
        tvItemFeedComments = itemView.findViewById(R.id.tv_item_feed_comments)
        ibItemFeedBookmark = itemView.findViewById(R.id.ib_item_feed_bookmark)
        ivItemFeedUpvote = itemView.findViewById(R.id.iv_item_feed_upvote)
        tvItemFeedUpvote = itemView.findViewById(R.id.tv_item_feed_upvote)
        clItemFeedUpvote = itemView.findViewById(R.id.cl_item_feed_upvote)
        clItemFeedComment = itemView.findViewById(R.id.cl_item_feed_comment)
    }

    fun setOwnPostProfilePic(picture: Int) {
        civOwnPostProfilePic.setImageResource(picture)
    }

    fun setOwnPostUsername(name: String?) {
        tvOwnPostUsername.text = name
    }

    fun setOwnPostPost(post: Int) {
        ivOwnPostPost.setImageResource(post)
    }

    fun setOwnPostTitle(title: String?) {
        tvOwnPostTitle.text = title
    }

    fun setOwnPostUpvoteCounter(upvotes: String?) {
        tvOwnPostUpvoteCounter.text = upvotes
    }

    fun setOwnPostComments(comments: String?) {
        tvOwnPostComments.text = comments
    }

    fun setOwnPostHighlightOnClickListener(onClickListener: View.OnClickListener) {
        clOwnPostHighlight.setOnClickListener(onClickListener)
    }

    fun setOwnPostHighlight(highlight: Boolean) {
        if (highlight) {
            ivOwnPostHighlight.setImageResource(R.drawable.baseline_star_24)
            ivOwnPostHighlight.imageTintList = ColorStateList.valueOf(
                ContextCompat.getColor(ivOwnPostHighlight.context, R.color.pinkish_purple)
            )
            tvOwnPostHighlight.setTextColor(
                ColorStateList.valueOf(
                ContextCompat.getColor(tvOwnPostHighlight.context, R.color.pinkish_purple)))
        } else {
            ivOwnPostHighlight.setImageResource(R.drawable.outline_star_border_24)
            ivOwnPostHighlight.imageTintList = ColorStateList.valueOf(
                ContextCompat.getColor(ivOwnPostHighlight.context, R.color.default_gray)
            )
            tvOwnPostHighlight.setTextColor(
                ColorStateList.valueOf(
                ContextCompat.getColor(tvOwnPostHighlight.context, R.color.default_gray)))
        }
    }

    fun setOwnPostCommentOnClickListener(onClickListener: View.OnClickListener) {
        clOwnPostComment.setOnClickListener(onClickListener)
    }

    fun setOwnPostProfileOnClickListener(onClickListener: View.OnClickListener) {
        civOwnPostProfilePic.setOnClickListener(onClickListener)
        tvOwnPostUsername.setOnClickListener(onClickListener)
    }
}