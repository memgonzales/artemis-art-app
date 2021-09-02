package com.mobdeve.gonzales.lee.ong.artemis

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import java.util.*

class UnregisteredCommentsAdapter(private val dataComments: ArrayList<Comment>) :
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
        holder.setItemCommentDate(currentComment.getDateCommented())
        holder.setItemCommentComment(currentComment.getCommentBody())
        holder.setItemCommentEdit(currentComment.getEditable())

        holder.setItemCommentProfileOnClickListener { view ->
            val intent = Intent(view.context, ViewUserUnregisteredActivity::class.java)
            intent.putExtra(
                Keys.KEY_PROFILE_PICTURE.name,
                currentComment.getProfilePicture()
            )
            intent.putExtra(
                Keys.KEY_USERNAME.name,
                currentComment.getUsername()
            )
            intent.putExtra(
                Keys.KEY_BIO.name,
                "Dummy bio"
            )
            view.context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return dataComments.size
    }
}