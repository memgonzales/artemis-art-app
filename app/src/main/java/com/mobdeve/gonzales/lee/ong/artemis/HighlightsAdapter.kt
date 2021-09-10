package com.mobdeve.gonzales.lee.ong.artemis

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import java.util.ArrayList

class HighlightsAdapter(private val dataPosts: ArrayList<Post>) :
    RecyclerView.Adapter<HighlightsViewHolder>() {

    private lateinit var context: Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HighlightsViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val itemView = inflater.inflate(R.layout.item_rectangular_pic_grid, parent, false)
        context = parent.context

        val highlightsViewHolder = HighlightsViewHolder(itemView)

        itemView.setOnClickListener { view ->
            val intent = Intent(view.context, ViewOwnHighlightActivity::class.java)

            intent.putExtra(
                Keys.KEY_USERID.name,
                dataPosts[highlightsViewHolder.bindingAdapterPosition].getUserId()
            )
            intent.putExtra(
                Keys.KEY_POSTID.name,
                dataPosts[highlightsViewHolder.bindingAdapterPosition].getPostId()
            )
            intent.putExtra(
                Keys.KEY_PROFILE_PICTURE.name,
                dataPosts[highlightsViewHolder.bindingAdapterPosition].getProfilePicture()
            )
            intent.putExtra(
                Keys.KEY_USERNAME.name,
                dataPosts[highlightsViewHolder.bindingAdapterPosition].getUsername()
            )
            intent.putExtra(
                Keys.KEY_POST.name,
                dataPosts[highlightsViewHolder.bindingAdapterPosition].getPostImg()
            )
            intent.putExtra(
                Keys.KEY_TITLE.name,
                dataPosts[highlightsViewHolder.bindingAdapterPosition].getTitle()
            )
            intent.putExtra(
                Keys.KEY_DATE_POSTED.name,
                dataPosts[highlightsViewHolder.bindingAdapterPosition].getDatePosted()
            )
            intent.putExtra(
                Keys.KEY_MEDIUM.name,
                dataPosts[highlightsViewHolder.bindingAdapterPosition].getMedium()
            )
            intent.putExtra(
                Keys.KEY_DIMENSIONS.name,
                dataPosts[highlightsViewHolder.bindingAdapterPosition].getDimensions()
            )
            intent.putExtra(
                Keys.KEY_DESCRIPTION.name,
                dataPosts[highlightsViewHolder.bindingAdapterPosition].getDescription()
            )
            intent.putExtra(
                Keys.KEY_HIGHLIGHT.name,
                dataPosts[highlightsViewHolder.bindingAdapterPosition].getHighlight()
            )

            view.context.startActivity(intent)
        }

        return highlightsViewHolder
    }

    override fun onBindViewHolder(holder: HighlightsViewHolder, position: Int) {
        val currentPost = dataPosts[position]

        Glide.with(context)
            .load(currentPost.getPostImg())
            .error(R.drawable.placeholder)
            .into(holder.getItemSearchResults())
    }

    override fun getItemCount(): Int {
        return dataPosts.size
    }
}