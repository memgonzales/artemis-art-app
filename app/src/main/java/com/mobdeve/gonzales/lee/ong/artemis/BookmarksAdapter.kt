package com.mobdeve.gonzales.lee.ong.artemis

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import java.util.ArrayList

class BookmarksAdapter(private val dataPosts: ArrayList<Post>) :
    RecyclerView.Adapter<BookmarksViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookmarksViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val itemView = inflater.inflate(R.layout.item_rectangular_pic_grid, parent, false)

        val bookmarksViewHolder = BookmarksViewHolder(itemView)

        itemView.setOnClickListener { view ->
            val intent = Intent(view.context, ViewBookmarkActivity::class.java)

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
                Keys.KEY_DIM_HEIGHT.name,
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

    override fun onBindViewHolder(holder: BookmarksViewHolder, position: Int) {
        val currentPost = dataPosts[position]
        holder.setItemSearchResults(currentPost.getPostImg())
    }

    override fun getItemCount(): Int {
        return dataPosts.size
    }
}