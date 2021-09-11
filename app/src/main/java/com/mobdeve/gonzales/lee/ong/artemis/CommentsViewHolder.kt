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
     * Image view for the profile picture of the user who posted the comment.
     */
    private val civItemCommentProfilePic: CircleImageView

    /**
     * Text view for the username of the user who posted the comment.
     */
    private val tvItemCommentUsername: TextView

    /**
     * Text view for the date when the comment was posted.
     */
    private val tvItemCommentDate: TextView

    /**
     * Text view for the comment per se.
     */
    private val tvItemCommentComment: TextView

    /**
     * Button for editing the comment.
     */
    private val ibItemCommentEdit: ImageButton

    /**
     * Bottom sheet dialog for modifying a comment (that is, either editing or deleting it).
     */
    private val btmItemCommentOptions: BottomSheetDialog

    /**
     * Service that supports uploading and downloading large objects to Google Cloud Storage.
     */
    private var storage: FirebaseStorage

    /**
     * Represents a reference to a Google Cloud Storage object.
     */
    private var storageRef: StorageReference

    /**
     * Returns the image view for the profile picture of the user who posted the comment.
     *
     * @return image view for the profile picture of the user who posted the comment.
     */
    fun getItemCommentProfilePic() : CircleImageView {
        return civItemCommentProfilePic
    }

    /**
     * Sets the profile picture of the user who posted the comment to the photo specified
     * by the given URI.
     *
     * @param picture URI of the photo to which the profile picture of the user who posted
     * the comment to the photo specified is to be set
     */
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

    /**
     * Sets the username of the user who posted the comment to the specified string.
     *
     * @param name string to which the username of the user who posted the comment is to be set
     */
    fun setItemCommentUsername(name: String?) {
        tvItemCommentUsername.text = name
    }

    /**
     * Sets the date when the comment was posted to the specified string.
     *
     * @param date string to which the date when the comment was posted is to be set
     */
    fun setItemCommentDate(date: String?) {
        tvItemCommentDate.text = date
    }

    /**
     * Sets the comment to the specified string.
     *
     * @param comment string to which the comment is to be set
     */
    fun setItemCommentComment(comment: String?) {
        tvItemCommentComment.text = comment
    }

    /**
     * Allows the user to edit a comment posted by them but disallows them to edit a comment
     * created by another user.
     *
     * @param editable <code>true</code> if the user is allowed to edit a comment; <code>false</code>,
     * otherwise
     */
    fun setItemCommentEdit(editable: Boolean) {
        if (editable)
            ibItemCommentEdit.visibility = View.VISIBLE
        else
            ibItemCommentEdit.visibility = View.GONE
    }

    /**
     * Sets the onclick listener of the profile picture of the user who posted the comment.
     *
     * @param onClickListener onclick listener of the profile picture of the user who posted
     * the comment
     */
    fun setItemCommentProfileOnClickListener(onClickListener: View.OnClickListener) {
        civItemCommentProfilePic.setOnClickListener(onClickListener)
        tvItemCommentUsername.setOnClickListener(onClickListener)
    }

    /**
     * Sets the onclick listener of the options icon displayed alongside a comment.
     *
     * @param onClickListener onclick listener of the options icon displayed alongside a comment.
     */
    fun setItemCommentOptionsOnClickListener(onClickListener: View.OnClickListener) {
        ibItemCommentEdit.setOnClickListener(onClickListener)
    }

    /**
     * Returns the bottom sheet dialog for modifying a comment (that is, either editing or deleting it).
     *
     * @return bottom sheet dialog for modifying a comment (that is, either editing or deleting it)
     */
    fun getItemCommentOptions(): BottomSheetDialog {
        return this.btmItemCommentOptions
    }

    /**
     * Initializes the components of the view holder.
     */
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