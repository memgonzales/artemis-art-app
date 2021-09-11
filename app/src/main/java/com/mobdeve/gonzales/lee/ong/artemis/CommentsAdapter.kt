package com.mobdeve.gonzales.lee.ong.artemis

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import java.util.*

/**
 * Adapter for the recycler view that handles the comments.
 *
 * @constructor Creates an adapter for the recycler view that handles the comments.
 * @param dataComments comments on the posts
 */
class CommentsAdapter(private val dataComments: ArrayList<Comment>) :
    RecyclerView.Adapter<CommentsViewHolder>() {

    /**
     * Context tied to the activity calling this adapter.
     */
    private lateinit var context: Context

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

        context = parent.context

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

        Glide.with(context)
            .load(currentComment.getProfilePicture())
            .placeholder(R.drawable.chibi_artemis_hd)
            .error(R.drawable.chibi_artemis_hd)
            .into(holder.getItemCommentProfilePic())

        holder.setItemCommentUsername(currentComment.getUsername())
        holder.setItemCommentDate(currentComment.getDateCommented())
        holder.setItemCommentComment(currentComment.getCommentBody())
        holder.setItemCommentEdit(currentComment.getEditable())

        holder.setItemCommentProfileOnClickListener { view ->
            val intent = Intent(view.context, ViewUserActivity::class.java)
            intent.putExtra(
                Keys.KEY_USERID.name,
                currentComment.getUserId()
            )

            view.context.startActivity(intent)
        }

        holder.setItemCommentOptionsOnClickListener { view ->
            val dialogView = LayoutInflater.from(view.context).inflate(R.layout.dialog_comment, null)
            val dialog = holder.getItemCommentOptions()
            dialog.setContentView(dialogView)

            val edit: ConstraintLayout = dialogView.findViewById(R.id.cl_dialog_comment_gallery)
            val delete: ConstraintLayout = dialogView.findViewById(R.id.cl_dialog_comment_photo)

            edit.setOnClickListener{ view ->
                dialog.dismiss()
                val intent = Intent(view.context, EditCommentActivity::class.java)
                intent.putExtra(
                    Keys.KEY_COMMENTID.name,
                    currentComment.getCommentId()
                )
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

    /**
     * Returns the total number of items in the data set held by the adapter.
     *
     * @return The total number of items in this adapter.
     */
    override fun getItemCount(): Int {
        return dataComments.size
    }
}