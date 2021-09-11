package com.mobdeve.gonzales.lee.ong.artemis

import android.graphics.BitmapFactory
import android.view.View
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.ktx.storage
import java.io.File

/**
 * View holder for the recycler view that handles the posts bookmarked by the user.
 *
 * @constructor Creates a view holder for the recycler view that handles the posts bookmarked
 * by the user.
 * @param itemView layout for a single item in the recycler view
 */
class BookmarksViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    /**
     * Image view for an item in the search results.
     */
    private val ivItemSearchResults: ImageView

    /**
     * Service that supports uploading and downloading large objects to Google Cloud Storage.
     */
    private var storage: FirebaseStorage

    /**
     * Represents a reference to a Google Cloud Storage object.
     */
    private var storageRef: StorageReference

    /**
     * Returns the image view for an item in the search results.
     *
     * @return image view for an item in the search results
     */
    fun getItemSearchResults(): ImageView {
        return ivItemSearchResults
    }

    /**
     * Sets the item in the search results to the specified post.
     *
     * @param post post to which the item in the search results is set
     */
    fun setItemSearchResults(post: String) {
        val localFile = File.createTempFile("images", "jpg")
        storageRef = storage.getReferenceFromUrl(post)

        storageRef.getFile(localFile)
            .addOnSuccessListener {
                val bitmap = BitmapFactory.decodeFile(localFile.absolutePath)
                ivItemSearchResults.setImageBitmap(bitmap)
            }

        //ivItemSearchResults.setImageResource(post)
    }

    /**
     * Initializes the components of the view holder.
     */
    init {
        ivItemSearchResults = itemView.findViewById(R.id.iv_item_rectangular_pic_grid)

        this.storage = Firebase.storage
        this.storageRef = this.storage.reference

    }
}