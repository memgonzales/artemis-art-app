package com.mobdeve.gonzales.lee.ong.artemis

import android.content.res.ColorStateList
import android.graphics.BitmapFactory
import android.view.View
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.ktx.storage
import de.hdodenhof.circleimageview.CircleImageView
import java.io.File
import java.io.IOException

/**
 * View holder for the recycler view that handles the user's own posts.
 *
 * @constructor Creates a view holder for the recycler view that handles the user's own posts.
 * @param itemView Layout for a single item in the recycler view.
 */
class OwnPostsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    /**
     * Image view for the user's profile picture.
     */
    private val civOwnPostProfilePic: CircleImageView

    /**
     * Text view for the user's username.
     */
    private val tvOwnPostUsername: TextView

    /**
     * Image view for the artwork posted.
     */
    private val ivOwnPostPost: ImageView

    /**
     * Text view for the title of the artwork posted.
     */
    private val tvOwnPostTitle: TextView

    /**
     * Text view for the number of upvotes.
     */
    private val tvOwnPostUpvoteCounter: TextView

    /**
     * Text view for the comments.
     */
    private val tvOwnPostComments: TextView

    /**
     * Image view for highlighting the posted artwork.
     */
    private val ivOwnPostHighlight: ImageView

    /**
     * Text view for highlighting the posted artwork.
     */
    private val tvOwnPostHighlight: TextView

    /**
     * Clickable layout for highlighting the posted artwork.
     */
    private val clOwnPostHighlight: ConstraintLayout

    /**
     * Clickable layout for commenting on the post.
     */
    private val clOwnPostComment: ConstraintLayout

    /**
     * Clickable layout for sharing the post on the user's Facebook account.
     */
    private val clOwnPostShare: ConstraintLayout

    /**
     * Image button for displaying the bottom sheet dialog related to the actions that the user
     * can perform on their own post.
     */
    private val ibItemOwnPostOptions: ImageButton

    /**
     * Bottom sheet dialog showing the actions that the user can perform on their own post:
     * editing its details and deleting it.
     */
    private val btmItemOwnPostOptions: BottomSheetDialog

    /**
     * Service that supports uploading and downloading large objects to Google Cloud Storage.
     */
    private var storage: FirebaseStorage

    /**
     * Represents a reference to a Google Cloud Storage object.
     */
    private var storageRef: StorageReference

    /**
     * Returns the image view for the user's profile picture.
     *
     * @return image view for the user's profile picture
     */
    fun getOwnPostProfilePic(): CircleImageView {
        return this.civOwnPostProfilePic
    }

    /**
     * Sets the profile picture to the photo specified by the given URI.
     *
     * In the case of a caught input/output exception, this function sets the profile picture
     * to the launcher icon (currently, this is the chibi version of the Artemis logo).
     *
     * @param picture URI of the photo to which the profile picture is to be set.
     */
    fun setOwnPostProfilePic(picture: String) {
        try{
            val localFile = File.createTempFile("images", "jpg")
            storageRef = storage.getReferenceFromUrl(picture)

            storageRef.getFile(localFile)
                .addOnSuccessListener {
                    val bitmap = BitmapFactory.decodeFile(localFile.absolutePath)
                    civOwnPostProfilePic.setImageBitmap(bitmap)
                }
        }
        catch(err: IOException){
            civOwnPostProfilePic.setImageResource(R.drawable.chibi_artemis_hd)
        }
    }

    /**
     * Returns the text view for the user's username.
     *
     * @return Text view for the user's username.
     */
    fun getOwnPostUsername(): TextView {
        return this.tvOwnPostUsername
    }

    /**
     * Sets the username to the specified string.
     *
     * @param name String to which the username is to be set.
     */
    fun setOwnPostUsername(name: String?) {
        tvOwnPostUsername.text = name
    }

    /**
     * Returns the image view for the artwork posted.
     *
     * @return Image view for the artwork posted.
     */
    fun getOwnPostPost(): ImageView {
        return ivOwnPostPost
    }

    /**
     * Sets the posted artwork to photo specified by the given URI.
     *
     * In the case of a caught input/output exception, this function sets the photo
     * to the launcher icon (currently, this is the chibi version of the Artemis logo).
     *
     * @param post URI of the photo to which the posted artwork is to be set.
     */
    fun setOwnPostPost(post: String) {
        try{
            val localFile = File.createTempFile("images", "jpg")
            storageRef = storage.getReferenceFromUrl(post)

            storageRef.getFile(localFile)
                .addOnSuccessListener {
                    val bitmap = BitmapFactory.decodeFile(localFile.absolutePath)
                    civOwnPostProfilePic.setImageBitmap(bitmap)
                }
        }
        catch(err: IOException){
            ivOwnPostPost.setImageResource(R.drawable.chibi_artemis_hd)
        }
    }

    /**
     * Sets the title of the posted artwork to the given string.
     *
     * @param title String to which the title of the posted artwork is to be set.
     */
    fun setOwnPostTitle(title: String?) {
        tvOwnPostTitle.text = title
    }

    /**
     * Sets the number of upvotes of the posted artwork to the given string.
     *
     * @param upvotes String to which the number of upvotes of the posted artwork is to be set.
     */
    fun setOwnPostUpvoteCounter(upvotes: String?) {
        tvOwnPostUpvoteCounter.text = upvotes
    }

    /**
     * Sets the text inside the text view related to the comments on the posted artwork
     * to the given string.
     *
     * @param comments String to which the text inside the text view related to the comments
     * on the posted artwork is to be set.
     */
    fun setOwnPostComments(comments: String?) {
        tvOwnPostComments.text = comments
    }

    /**
     * Sets the onclick listener for highlighting the posted artwork.
     *
     * @param onClickListener onclick listener for highlighting the posted artwork.
     */
    fun setOwnPostHighlightOnClickListener(onClickListener: View.OnClickListener) {
        clOwnPostHighlight.setOnClickListener(onClickListener)
    }

    /**
     * Updates the view depending on whether the posted artwork is highlighted
     *
     * @param highlight <code>true</code> fi the posted artwork is highlighted; <code>false</code>,
     * otherwise.
     */
    fun setOwnPostHighlight(highlight: Boolean) {
        if (highlight) {
            ivOwnPostHighlight.setImageResource(R.drawable.baseline_star_24)
            ivOwnPostHighlight.imageTintList = ColorStateList.valueOf(
                ContextCompat.getColor(ivOwnPostHighlight.context, R.color.pinkish_purple)
            )
            tvOwnPostHighlight.setTextColor(
                ColorStateList.valueOf(
                    ContextCompat.getColor(tvOwnPostHighlight.context, R.color.pinkish_purple)))
        } else {
            ivOwnPostHighlight.setImageResource(R.drawable.outline_star_border_24)
            ivOwnPostHighlight.imageTintList = ColorStateList.valueOf(
                ContextCompat.getColor(ivOwnPostHighlight.context, R.color.default_gray)
            )
            tvOwnPostHighlight.setTextColor(
                ColorStateList.valueOf(
                    ContextCompat.getColor(tvOwnPostHighlight.context, R.color.default_gray)))
        }
    }

    /**
     * Sets the onclick listener for commenting on the posted artwork.
     *
     * @param onClickListener onclick listener for commenting on the posted artwork.
     */
    fun setOwnPostCommentOnClickListener(onClickListener: View.OnClickListener) {
        clOwnPostComment.setOnClickListener(onClickListener)
    }

    /**
     * Sets the onclick listener for sharing the posted artwork on Facebook.
     *
     * @param onClickListener onclick listener for sharing the posted artwork on Facebook.
     */
    fun setOwnPostShareOnClickListener(onClickListener: View.OnClickListener) {
        clOwnPostShare.setOnClickListener(onClickListener)
    }

    /**
     * Sets the onclick listener for the profile picture and username of the user who posted
     * the artwork.
     *
     * @param onClickListener onclick listener for the profile picture and username of the user
     * who posted the artwork.
     */
    fun setOwnPostProfileOnClickListener(onClickListener: View.OnClickListener) {
        civOwnPostProfilePic.setOnClickListener(onClickListener)
        tvOwnPostUsername.setOnClickListener(onClickListener)
    }

    /**
     * Sets the onclick listener for the button used to display the actions that the user can
     * perform on their own posted artwork.
     *
     * @param onClickListener onclick listener for the button used to display the actions that the
     * user can perform on their own posted artwork.
     */
    fun setOwnPostOptionsOnClickListener(onClickListener: View.OnClickListener) {
        ibItemOwnPostOptions.setOnClickListener(onClickListener)
    }

    /**
     * Returns the bottom sheet dialog showing the actions that the user can perform on their own post:
     * editing its details and deleting it.
     *
     * @return Bottom sheet dialog showing the actions that the user can perform on their own post:
     * editing its details and deleting it.
     */
    fun getOwnPostOptions(): BottomSheetDialog {
        return this.btmItemOwnPostOptions
    }

    /**
     * Initializes the components of the view holder.
     */
    init {
        civOwnPostProfilePic = itemView.findViewById(R.id.civ_item_own_post_profile_pic)
        tvOwnPostUsername = itemView.findViewById(R.id.tv_item_own_post_username)
        ivOwnPostPost = itemView.findViewById(R.id.iv_item_own_post_post)
        tvOwnPostTitle = itemView.findViewById(R.id.tv_item_own_post_title)
        tvOwnPostUpvoteCounter = itemView.findViewById(R.id.tv_item_own_post_upvote_counter)
        tvOwnPostComments = itemView.findViewById(R.id.tv_item_own_post_comments)
        ivOwnPostHighlight = itemView.findViewById(R.id.iv_item_own_post_highlight)
        tvOwnPostHighlight = itemView.findViewById(R.id.tv_item_own_post_highlight)
        clOwnPostHighlight = itemView.findViewById(R.id.cl_item_own_post_highlight)
        clOwnPostComment = itemView.findViewById(R.id.cl_item_own_post_comment)
        clOwnPostShare = itemView.findViewById(R.id.cl_item_own_post_share)
        ibItemOwnPostOptions = itemView.findViewById(R.id.ib_item_own_post_options)
        btmItemOwnPostOptions = BottomSheetDialog(itemView.context)

        this.storage = Firebase.storage
        this.storageRef = this.storage.reference
    }
}