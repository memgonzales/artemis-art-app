package com.mobdeve.gonzales.lee.ong.artemis

import android.graphics.BitmapFactory
import android.view.View
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.ktx.storage
import de.hdodenhof.circleimageview.CircleImageView
import java.io.File

/**
 * View holder for the recycler view that handles the comments on the posts.
 *
 * @constructor Creates a view holder for the recycler view that handles the comments on the posts.
 * @param itemView layout for a single item in the recycler view
 */
class CommentsViewHolder (itemView: View) : RecyclerView.ViewHolder(itemView) {
    /**
     *
     */
    private val civItemCommentProfilePic: CircleImageView
    private val tvItemCommentUsername: TextView
    private val tvItemCommentDate: TextView
    private val tvItemCommentComment: TextView

    private val ibItemCommentEdit: ImageButton
    private val btmItemCommentOptions: BottomSheetDialog

    private var storage: FirebaseStorage
    private var storageRef: StorageReference

    fun getItemCommentProfilePic() : CircleImageView {
        return civItemCommentProfilePic
    }

    fun setItemCommentProfilePic(picture: String) {

        val localFile = File.createTempFile("images", "jpg")
        storageRef = storage.getReferenceFromUrl(picture)

        storageRef.getFile(localFile)
            .addOnSuccessListener {
                val bitmap = BitmapFactory.decodeFile(localFile.absolutePath)
                civItemCommentProfilePic.setImageBitmap(bitmap)
            }

       // civItemCommentProfilePic.setImageResource(picture)
    }

    fun setItemCommentUsername(name: String?) {
        tvItemCommentUsername.text = name
    }

    fun setItemCommentDate(date: String?) {
        tvItemCommentDate.text = date
    }

    fun setItemCommentComment(comment: String?) {
        tvItemCommentComment.text = comment
    }

    fun setItemCommentEdit(editable: Boolean) {
        if (editable)
            ibItemCommentEdit.visibility = View.VISIBLE
        else
            ibItemCommentEdit.visibility = View.GONE
    }

    fun setItemCommentProfileOnClickListener(onClickListener: View.OnClickListener) {
        civItemCommentProfilePic.setOnClickListener(onClickListener)
        tvItemCommentUsername.setOnClickListener(onClickListener)
    }

    fun setItemCommentOptionsOnClickListener(onClickListener: View.OnClickListener) {
        ibItemCommentEdit.setOnClickListener(onClickListener)
    }

    fun getItemCommentOptions(): BottomSheetDialog {
        return this.btmItemCommentOptions
    }

    init {
        civItemCommentProfilePic = itemView.findViewById(R.id.civ_item_comment_profile_pic)
        tvItemCommentUsername = itemView.findViewById(R.id.tv_item_comment_username)
        tvItemCommentDate = itemView.findViewById(R.id.tv_item_comment_date)
        tvItemCommentComment = itemView.findViewById(R.id.tv_item_comment_comment)
        ibItemCommentEdit = itemView.findViewById(R.id.ib_item_comment_edit)
        btmItemCommentOptions = BottomSheetDialog(itemView.context)

        this.storage = Firebase.storage
        this.storageRef = this.storage.reference
    }
}