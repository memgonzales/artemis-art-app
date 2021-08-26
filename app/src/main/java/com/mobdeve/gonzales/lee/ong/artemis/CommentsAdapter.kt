package com.mobdeve.gonzales.lee.ong.artemis

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
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
        holder.setItemCommentDate(currentComment.getDateCommented())
        holder.setItemCommentComment(currentComment.getCommentBody())
        holder.setItemCommentEdit(currentComment.getEditable())

        holder.setItemCommentProfileOnClickListener { view ->
            val intent = Intent(view.context, ViewUserActivity::class.java)
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

        holder.setItemCommentOptionsOnClickListener { view ->
            val dialogView = LayoutInflater.from(view.context).inflate(R.layout.dialog_comment, null)
            val dialog = holder.getItemCommentOptions()
            dialog.setContentView(dialogView)

            val edit: ConstraintLayout = dialogView.findViewById(R.id.cl_dialog_post_artwork_gallery)
            val delete: ConstraintLayout = dialogView.findViewById(R.id.cl_dialog_post_artwork_photo)

            edit.setOnClickListener{ view ->
                dialog.dismiss()
                val intent = Intent(view.context, EditCommentActivity::class.java)
                intent.putExtra(
                    Keys.KEY_PROFILE_PICTURE.name,
                    currentComment.getProfilePicture()
                )
                intent.putExtra(
                    Keys.KEY_USERNAME.name,
                    currentComment.getUsername()
                )
                intent.putExtra(
                    Keys.KEY_COMMENT_BODY.name,
                    currentComment.getCommentBody()
                )
                view.context.startActivity(intent)
            }

            delete.setOnClickListener { view ->
                Toast.makeText(view.context, "Your comment has been deleted", Toast.LENGTH_SHORT).show()
                dialog.dismiss()
            }

            dialog.show()
        }
    }

    override fun getItemCount(): Int {
        return dataComments.size
    }
}