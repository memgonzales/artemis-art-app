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
 * View holder for the recycler view that handles the posts displayed on the search result.
 *
 * @constructor Creates a view holder for the recycler view that handles the posts displayed
 * on the search result.
 * @param itemView Layout for a single item in the recycler view.
 */
class SearchViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    /**
     * Image view for the artwork returned as part of the search result
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
     * Returns the image view for the artwork returned as part of the search result.
     *
     * @return Image view for the artwork returned as part of the search result.
     */
    fun getItemSearchResults(): ImageView{
        return ivItemSearchResults
    }

    /**
     * Sets the artwork returned as part of the search result specified by the given URI.
     *
     * @param post URI of the photo to which the image of the artwork to be set.
     */
    fun setItemSearchResults(post: String) {
        val localFile = File.createTempFile("images", "jpg")
        storageRef = storage.getReferenceFromUrl(post)

        storageRef.getFile(localFile)
            .addOnSuccessListener {
                var bitmap = BitmapFactory.decodeFile(localFile.absolutePath)
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