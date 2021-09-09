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

class BookmarksViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    private val ivItemSearchResults: ImageView

    private var storage: FirebaseStorage
    private var storageRef: StorageReference

    fun getItemSearchResults(): ImageView {
        return ivItemSearchResults
    }

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

    init {
        ivItemSearchResults = itemView.findViewById(R.id.iv_item_rectangular_pic_grid)

        this.storage = Firebase.storage
        this.storageRef = this.storage.reference

    }
}