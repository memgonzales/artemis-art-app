package com.mobdeve.gonzales.lee.ong.artemis

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import java.util.ArrayList

class SearchResultsAdapter(private val dataPosts: ArrayList<Post>) :
    RecyclerView.Adapter<SearchViewHolder>() {

    private lateinit var context: Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val itemView = inflater.inflate(R.layout.item_rectangular_pic_grid, parent, false)
        context = parent.context

        val searchViewHolder = SearchViewHolder(itemView)

        itemView.setOnClickListener { view ->
            val intent = Intent(view.context, ViewPostActivity::class.java)

            intent.putExtra(
                Keys.KEY_USERID.name,
                dataPosts[searchViewHolder.bindingAdapterPosition].getUserId()
            )
            intent.putExtra(
                Keys.KEY_POSTID.name,
                dataPosts[searchViewHolder.bindingAdapterPosition].getPostId()
            )
            intent.putExtra(
                Keys.KEY_PROFILE_PICTURE.name,
                dataPosts[searchViewHolder.bindingAdapterPosition].getProfilePicture()
            )
            intent.putExtra(
                Keys.KEY_USERNAME.name,
                dataPosts[searchViewHolder.bindingAdapterPosition].getUsername()
            )
            intent.putExtra(
                Keys.KEY_POST.name,
                dataPosts[searchViewHolder.bindingAdapterPosition].getPostImg()
            )
            intent.putExtra(
                Keys.KEY_TITLE.name,
                dataPosts[searchViewHolder.bindingAdapterPosition].getTitle()
            )
            intent.putExtra(
                Keys.KEY_NUM_UPVOTES.name,
                dataPosts[searchViewHolder.bindingAdapterPosition].getNumUpvotes()
            )
            intent.putExtra(
                Keys.KEY_NUM_COMMENTS.name,
                dataPosts[searchViewHolder.bindingAdapterPosition].getNumComments()
            )
            intent.putExtra(
                Keys.KEY_DATE_POSTED.name,
                dataPosts[searchViewHolder.bindingAdapterPosition].getDatePosted()
            )
            intent.putExtra(
                Keys.KEY_MEDIUM.name,
                dataPosts[searchViewHolder.bindingAdapterPosition].getMedium()
            )
            intent.putExtra(
                Keys.KEY_DIMENSIONS.name,
                dataPosts[searchViewHolder.bindingAdapterPosition].getDimensions()
            )
            intent.putExtra(
                Keys.KEY_DESCRIPTION.name,
                dataPosts[searchViewHolder.bindingAdapterPosition].getDescription()
            )
            intent.putExtra(
                Keys.KEY_TAGS.name,
                dataPosts[searchViewHolder.bindingAdapterPosition].getTags()
            )
            intent.putExtra(
                Keys.KEY_BOOKMARK.name,
                dataPosts[searchViewHolder.bindingAdapterPosition].getBookmark()
            )
            intent.putExtra(
                Keys.KEY_UPVOTE.name,
                dataPosts[searchViewHolder.bindingAdapterPosition].getUpvote()
            )

            view.context.startActivity(intent)
        }

        return searchViewHolder
    }

    override fun onBindViewHolder(holder: SearchViewHolder, position: Int) {
        val currentPost = dataPosts[position]
        holder.setItemSearchResults(currentPost.getPostImg())
        /*
        Glide.with(context)
            .load(dataUsers[0].getUserImg())
            .placeholder(R.drawable.placeholder)
            .error(R.drawable.placeholder)
            .into(civSearchResultUser1)

         */
    }

    override fun getItemCount(): Int {
        return dataPosts.size
    }
}