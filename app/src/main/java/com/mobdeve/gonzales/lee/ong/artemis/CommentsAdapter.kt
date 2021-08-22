package com.mobdeve.gonzales.lee.ong.artemis

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import java.util.ArrayList

class CommentsAdapter(private val dataComments: ArrayList<Comment>) :
    RecyclerView.Adapter<CommentsViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommentsViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val itemView = inflater.inflate(R.layout.item_comment, parent, false)

        val commentsViewHolder = CommentsViewHolder(itemView)

        return commentsViewHolder
    }

    override fun onBindViewHolder(holder: CommentsViewHolder, position: Int) {
        val currentComment = dataComments[position]
        holder.setItemCommentProfilePic(currentComment.profilePicture)
        holder.setItemCommentUsername(currentComment.username)
        holder.setItemCommentDate(currentComment.dateCommented.toStringFull())
        holder.setItemCommentComment(currentComment.commentBody)
    }

    override fun getItemCount(): Int {
        return dataComments.size
    }
}