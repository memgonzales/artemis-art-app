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

class CommentsViewHolder (itemView: View) : RecyclerView.ViewHolder(itemView) {
    private val civItemCommentProfilePic: CircleImageView
    private val tvItemCommentUsername: TextView
    private val tvItemCommentDate: TextView
    private val tvItemCommentComment: TextView

    fun setItemCommentProfilePic(picture: Int) {
        civItemCommentProfilePic.setImageResource(picture)
    }

    fun setItemCommentUsername(name: String?) {
        tvItemCommentUsername.text = name
    }

    fun setItemCommentDate(date: String?) {
        tvItemCommentDate.text = date
    }

    fun setItemCommentComment(comment: String?) {
        tvItemCommentComment.text = comment
    }

    init {
        civItemCommentProfilePic = itemView.findViewById(R.id.civ_item_comment_profile_pic)
        tvItemCommentUsername = itemView.findViewById(R.id.tv_item_comment_username)
        tvItemCommentDate = itemView.findViewById(R.id.tv_item_comment_date)
        tvItemCommentComment = itemView.findViewById(R.id.tv_item_comment_comment)
    }
}