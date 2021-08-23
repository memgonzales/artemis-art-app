package com.mobdeve.gonzales.lee.ong.artemis

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import java.util.*

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
        holder.setItemCommentProfilePic(currentComment.getProfilePicture())
        holder.setItemCommentUsername(currentComment.getUsername())
        holder.setItemCommentDate(currentComment.getDateCommented().toStringFull())
        holder.setItemCommentComment(currentComment.getCommentBody())
    }

    override fun getItemCount(): Int {
        return dataComments.size
    }
}