package com.mobdeve.gonzales.lee.ong.artemis

import android.view.View
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView

class BookmarksViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    private val ivItemSearchResults: ImageView

    fun setItemSearchResults(post: Int) {
        ivItemSearchResults.setImageResource(post)
    }

    init {
        ivItemSearchResults = itemView.findViewById(R.id.iv_item_search_results)

    }
}