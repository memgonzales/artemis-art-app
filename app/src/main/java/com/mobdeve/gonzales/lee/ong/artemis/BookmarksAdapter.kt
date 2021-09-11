package com.mobdeve.gonzales.lee.ong.artemis

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import java.util.*

/**
 * Adapter for the recycler view that handles the posts bookmarked by the user.
 *
 * @constructor Creates an adapter for the recycler view that handles the posts bookmarked
 * by the user.
 * @param dataPosts Posts bookmarked by the user.
 */
class BookmarksAdapter(private val dataPosts: ArrayList<Post>) :
    RecyclerView.Adapter<BookmarksViewHolder>() {

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
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookmarksViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val itemView = inflater.inflate(R.layout.item_rectangular_pic_grid, parent, false)
        this.context = parent.context

        val bookmarksViewHolder = BookmarksViewHolder(itemView)

        itemView.setOnClickListener { view ->
            val intent = Intent(view.context, ViewBookmarkActivity::class.java)

            intent.putExtra(
                Keys.KEY_USERID.name,
                dataPosts[bookmarksViewHolder.bindingAdapterPosition].getUserId()
            )
            intent.putExtra(
                Keys.KEY_POSTID.name,
                dataPosts[bookmarksViewHolder.bindingAdapterPosition].getPostId()
            )
            intent.putExtra(
                Keys.KEY_PROFILE_PICTURE.name,
                dataPosts[bookmarksViewHolder.bindingAdapterPosition].getProfilePicture()
            )
            intent.putExtra(
                Keys.KEY_USERNAME.name,
                dataPosts[bookmarksViewHolder.bindingAdapterPosition].getUsername()
            )
            intent.putExtra(
                Keys.KEY_POST.name,
                dataPosts[bookmarksViewHolder.bindingAdapterPosition].getPostImg()
            )
            intent.putExtra(
                Keys.KEY_TITLE.name,
                dataPosts[bookmarksViewHolder.bindingAdapterPosition].getTitle()
            )
            intent.putExtra(
                Keys.KEY_DATE_POSTED.name,
                dataPosts[bookmarksViewHolder.bindingAdapterPosition].getDatePosted()
            )
            intent.putExtra(
                Keys.KEY_MEDIUM.name,
                dataPosts[bookmarksViewHolder.bindingAdapterPosition].getMedium()
            )
            intent.putExtra(
                Keys.KEY_DIMENSIONS.name,
                dataPosts[bookmarksViewHolder.bindingAdapterPosition].getDimensions()
            )
            intent.putExtra(
                Keys.KEY_DESCRIPTION.name,
                dataPosts[bookmarksViewHolder.bindingAdapterPosition].getDescription()
            )
            intent.putExtra(
                Keys.KEY_BOOKMARK.name,
                true
            )

            view.context.startActivity(intent)
        }

        return bookmarksViewHolder
    }

    /**
     * Called by RecyclerView to display the data at the specified position.
     *
     * @param holder The ViewHolder which should be updated to represent the contents of the item
     * at the given position in the data set.
     * @param position The position of the item within the adapter's data set.
     */
    override fun onBindViewHolder(holder: BookmarksViewHolder, position: Int) {
        val currentPost = dataPosts[position]
        //holder.setItemSearchResults(currentPost.getPostImg())

        Glide.with(context)
            .load(currentPost.getPostImg())
            .placeholder(R.drawable.placeholder)
            .error(R.drawable.placeholder)
            .into(holder.getItemSearchResults())
    }

    /**
     * Returns the total number of items in the data set held by the adapter.
     *
     * @return The total number of items in this adapter.
     */
    override fun getItemCount(): Int {
        return dataPosts.size
    }
}