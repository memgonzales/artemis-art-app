package com.mobdeve.gonzales.lee.ong.artemis

import android.content.res.ColorStateList
import android.graphics.BitmapFactory
import android.graphics.drawable.BitmapDrawable
import android.view.View
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.facebook.CallbackManager
import com.facebook.FacebookCallback
import com.facebook.FacebookException
import com.facebook.share.Sharer
import com.facebook.share.model.SharePhoto
import com.facebook.share.model.SharePhotoContent
import com.facebook.share.widget.ShareDialog
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.ktx.storage
import de.hdodenhof.circleimageview.CircleImageView
import java.io.File

class FeedViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    private val civItemFeedProfilePic: CircleImageView
    private val tvItemFeedUsername: TextView
    private val ivItemFeedPost: ImageView
    private val tvItemFeedTitle: TextView
    private val tvItemFeedUpvoteCounter: TextView
    private val tvItemFeedComments: TextView
    private val ibItemFeedBookmark: ImageButton
    private val ivItemFeedUpvote: ImageView
    private val tvItemFeedUpvote: TextView
    private val clItemFeedUpvote: ConstraintLayout
    private val clItemFeedComment: ConstraintLayout
    private val clItemFeedShare: ConstraintLayout

    private var storage: FirebaseStorage
    private var storageRef: StorageReference

    fun getItemFeedProfilePic(): CircleImageView {
        return civItemFeedProfilePic
    }

    fun setItemFeedProfilePic(picture: String) {
        val localFile = File.createTempFile("images", "jpg")
        storageRef = storage.getReferenceFromUrl(picture)

        storageRef.getFile(localFile)
            .addOnSuccessListener {
                var bitmap = BitmapFactory.decodeFile(localFile.absolutePath)
                civItemFeedProfilePic.setImageBitmap(bitmap)
            }

        //civItemFeedProfilePic.setImageResource(picture)
    }

    fun getItemFeedUsername(): TextView {
        return tvItemFeedUsername
    }

    fun setItemFeedUsername(name: String?) {
        tvItemFeedUsername.text = name
    }

    fun getItemFeedPost(): ImageView {
        return ivItemFeedPost
    }

    fun setItemFeedPost(post: String) {
        val localFile = File.createTempFile("images", "jpg")
        storageRef = storage.getReferenceFromUrl(post)

        storageRef.getFile(localFile)
            .addOnSuccessListener {
                var bitmap = BitmapFactory.decodeFile(localFile.absolutePath)
                ivItemFeedPost.setImageBitmap(bitmap)
            }

        //ivItemFeedPost.setImageResource(post)
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

    fun setItemFeedUpvoteOnClickListener(onClickListener: View.OnClickListener) {
        clItemFeedUpvote.setOnClickListener(onClickListener)
    }

    fun setItemFeedUpvote(upvote: Boolean) {
        if (upvote) {
            ivItemFeedUpvote.setImageResource(R.drawable.upvote_colored)
            ivItemFeedUpvote.imageTintList = ColorStateList.valueOf(
                ContextCompat.getColor(ivItemFeedUpvote.context, R.color.pinkish_purple)
            )
            tvItemFeedUpvote.setTextColor(ColorStateList.valueOf(
                ContextCompat.getColor(tvItemFeedUpvote.context, R.color.pinkish_purple)))
        } else {
            ivItemFeedUpvote.setImageResource(R.drawable.upvote_v2)
            ivItemFeedUpvote.imageTintList = ColorStateList.valueOf(
                ContextCompat.getColor(ivItemFeedUpvote.context, R.color.default_gray)
            )
            tvItemFeedUpvote.setTextColor(ColorStateList.valueOf(
                ContextCompat.getColor(tvItemFeedUpvote.context, R.color.default_gray)))
        }
    }

    fun setItemFeedCommentOnClickListener(onClickListener: View.OnClickListener) {
        clItemFeedComment.setOnClickListener(onClickListener)
    }

    fun setItemFeedShareOnClickListener(onClickListener: View.OnClickListener) {
        clItemFeedShare.setOnClickListener(onClickListener)
    }

    fun setItemFeedProfileOnClickListener(onClickListener: View.OnClickListener) {
        civItemFeedProfilePic.setOnClickListener(onClickListener)
        tvItemFeedUsername.setOnClickListener(onClickListener)
    }

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
        clItemFeedShare = itemView.findViewById(R.id.cl_item_feed_share)

        this.storage = Firebase.storage
        this.storageRef = this.storage.reference
    }
}