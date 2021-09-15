package com.mobdeve.gonzales.lee.ong.artemis

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import java.util.*

class UnregisteredCommentsAdapter() : RecyclerView.Adapter<CommentsViewHolder>() {

    /**
     * Context tied to the activity calling this adapter.
     */
    private lateinit var context: Context

    private val diffCallbacks = object : DiffUtil.ItemCallback<Comment>(){
        override fun areItemsTheSame(oldItem: Comment, newItem: Comment): Boolean {
            return oldItem.getCommentId().equals(newItem.getCommentId())
        }

        override fun areContentsTheSame(oldItem: Comment, newItem: Comment): Boolean {
            return oldItem.equals(newItem)
        }
    }

    private val differ: AsyncListDiffer<Comment> = AsyncListDiffer(this, diffCallbacks)

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
        this.context = parent.context

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
        val currentComment = differ.currentList[position]

        //holder.setItemCommentProfilePic(currentComment.getProfilePicture())
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
        return differ.currentList.size
    }

    fun updateComments(newComments: List<Comment>){
        differ.submitList(newComments)
    }
}