package com.mobdeve.gonzales.lee.ong.artemis

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import java.util.*

class UnregisteredCommentsAdapter(private val dataComments: ArrayList<Comment>) :
    RecyclerView.Adapter<CommentsViewHolder>() {

    /**
     * Called when RecyclerView needs a new <code>RecyclerView.ViewHolder</code> of the given type
     * to represent an item.
     *
     * @param parent The ViewGroup into which the new View will be added after it is bound to an
     * adapter position.
     * @param viewType The view type of the new View.
     *
     * @return A new ViewHolder that holds a View of the given view type.
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommentsViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val itemView = inflater.inflate(R.layout.item_comment, parent, false)

        val commentsViewHolder = CommentsViewHolder(itemView)

        return commentsViewHolder
    }

    /**
     * Called by RecyclerView to display the data at the specified position.
     *
     * @param holder The ViewHolder which should be updated to represent the contents of the item
     * at the given position in the data set.
     * @param position The position of the item within the adapter's data set.
     */
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

    /**
     * Returns the total number of items in the data set held by the adapter.
     *
     * @return The total number of items in this adapter.
     */
    override fun getItemCount(): Int {
        return dataComments.size
    }
}