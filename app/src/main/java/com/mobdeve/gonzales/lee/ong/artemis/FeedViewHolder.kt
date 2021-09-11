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
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.ktx.storage
import de.hdodenhof.circleimageview.CircleImageView
import java.io.File
import java.io.IOException

/**
 * View holder for the recycler view that handles the posts displayed on the feed.
 *
 * @constructor Creates a view holder for the recycler view that handles the posts displayed
 * on the feed.
 * @param itemView layout for a single item in the recycler view
 */
class FeedViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    /**
     * Image view for the profile picture of the user who posted.
     */
    private val civItemFeedProfilePic: CircleImageView

    /**
     * Text view for the username of the user who posted.
     */
    private val tvItemFeedUsername: TextView

    /**
     * Image view for the artwork posted.
     */
    private val ivItemFeedPost: ImageView

    /**
     * Text view for the title of the artwork posted.
     */
    private val tvItemFeedTitle: TextView

    /**
     * Text view for the number of upvotes.
     */
    private val tvItemFeedUpvoteCounter: TextView

    /**
     * Text view for the number of comments.
     */
    private val tvItemFeedCommentsCounter: TextView

    /**
     * Text view for the comments.
     */
    private val tvItemFeedComments: TextView

    /**
     * Button for bookmarking the post.
     */
    private val ibItemFeedBookmark: ImageButton

    /**
     * Button for upvoting the post.
     */
    private val ivItemFeedUpvote: ImageView

    /**
     * Text view for upvotes.
     */
    private val tvItemFeedUpvote: TextView

    /**
     * Clickable layout for upvoting the post.
     */
    private val clItemFeedUpvote: ConstraintLayout

    /**
     * Clckable layout for commenting on the post.
     */
    private val clItemFeedComment: ConstraintLayout

    /**
     * Clickable layout for sharing the post.
     */
    private val clItemFeedShare: ConstraintLayout

    /**
     * Service that supports uploading and downloading large objects to Google Cloud Storage.
     */
    private var storage: FirebaseStorage

    /**
     * Represents a reference to a Google Cloud Storage object.
     */
    private var storageRef: StorageReference

    /**
     * Returns the image view for the profile picture of the user who posted.
     *
     * @return image view for the profile picture of the user who posted
     */
    fun getItemFeedProfilePic(): CircleImageView {
        return civItemFeedProfilePic
    }

    /**
     * Sets the profile picture of the user who posted to the photo specified by the given URI.
     *
     * @return URI of the photo to which the profile picture of the user is to be set
     */
    fun setItemFeedProfilePic(picture: String) {

        try{
            val localFile = File.createTempFile("images", "jpg")
            storageRef = storage.getReferenceFromUrl(picture)

            storageRef.getFile(localFile)
                .addOnSuccessListener {
                    val bitmap = BitmapFactory.decodeFile(localFile.absolutePath)
                    civItemFeedProfilePic.setImageBitmap(bitmap)
                }
        }
        catch(err: IOException){
            civItemFeedProfilePic.setImageResource(R.drawable.chibi_artemis_hd)
        }


        //civItemFeedProfilePic.setImageResource(picture)
    }

    /**
     * Returns the username of the user who posed.
     *
     * @return username of the user who posted
     */
    fun getItemFeedUsername(): TextView {
        return tvItemFeedUsername
    }

    /**
     * Sets the username of the user who posted to the specified string.
     *
     * @param name string to which the username of the user who posted is to be set
     */
    fun setItemFeedUsername(name: String?) {
        tvItemFeedUsername.text = name
    }

    /**
     * Returns the image view for the posted artwork.
     *
     * @return image view for the posted artwork
     */
    fun getItemFeedPost(): ImageView {
        return ivItemFeedPost
    }

    /**
     * Sets the posted artwork to photo specified by the given URI.
     *
     * @param post URI of the photo to which the posted artwork is to be set
     */
    fun setItemFeedPost(post: String) {

        try{
            val localFile = File.createTempFile("images", "jpg")
            storageRef = storage.getReferenceFromUrl(post)

            storageRef.getFile(localFile)
                .addOnSuccessListener {
                    val bitmap = BitmapFactory.decodeFile(localFile.absolutePath)
                    ivItemFeedPost.setImageBitmap(bitmap)
                }
        }
        catch(err: IOException){
            ivItemFeedPost.setImageResource(R.drawable.chibi_artemis_hd)
        }


        //ivItemFeedPost.setImageResource(post)
    }

    /**
     * Sets the title of the posted artwork to the given string.
     *
     * @param title string to which the title of the posted artwork is to be set
     */
    fun setItemFeedTitle(title: String?) {
        tvItemFeedTitle.text = title
    }

    /**
     * Sets the number of upvotes of the posted artwork to the given string.
     *
     * @param upvotes string to which the number of upvotes of the posted artwork is to be set
     */
    fun setItemFeedUpvoteCounter(upvotes: String?) {
        tvItemFeedUpvoteCounter.text = upvotes
    }

    /**
     * Sets the number of comments on the posted artwork to the given string.
     *
     * @param numComments string to which the number of comments on the posted artwork is to be set
     */
    fun setItemFeedCommentCounter(numComments: String?) {
        tvItemFeedCommentsCounter.text = numComments
    }

    /**
     * Sets the text inside the text view related to the comments on the posted artwork
     * to the given string.
     *
     * @param comments string to which the text inside the text view related to the comments
     * on the posted artwork is to be set
     */
    fun setItemFeedComments(comments: String?) {
        tvItemFeedComments.text = comments
    }

    /**
     * Sets the onclick listener for bookmarking the posted artwork.
     *
     * @param onClickListener onclick listener for upvoting the posted artwork.
     */
    fun setItemFeedBookmarkOnClickListener(onClickListener: View.OnClickListener) {
        ibItemFeedBookmark.setOnClickListener(onClickListener)
    }

    /**
     * Updates the view depending on whether the posted artwork is bookmarked
     *
     * @param bookmark <code>true</code> fi the posted artwork is bookmarked; <code>false</code>,
     * otherwise
     */
    fun setItemFeedBookmark(bookmark: Boolean) {
        if (bookmark) {
            ibItemFeedBookmark.setImageResource(R.drawable.outline_bookmark_24)
            ibItemFeedBookmark.imageTintList = ColorStateList.valueOf(
                ContextCompat.getColor(ibItemFeedBookmark.context, R.color.pinkish_purple)
            )
        } else {
            ibItemFeedBookmark.setImageResource(R.drawable.outline_bookmark_border_24)
            ibItemFeedBookmark.imageTintList = ColorStateList.valueOf(
                ContextCompat.getColor(ibItemFeedBookmark.context, R.color.default_gray)
            )
        }
    }

    /**
     * Sets the onclick listener for upvoting the posted artwork.
     *
     * @param onClickListener onclick listener for upvoting the posted artwork.
     */
    fun setItemFeedUpvoteOnClickListener(onClickListener: View.OnClickListener) {
        clItemFeedUpvote.setOnClickListener(onClickListener)
    }

    /**
     * Updates the view depending on whether the posted artwork is upvoted.
     *
     * @param upvote <code>true</code> fi the posted artwork is upvoted; <code>false</code>,
     * otherwise
     */
    fun setItemFeedUpvote(upvote: Boolean) {
        if (upvote) {
            ivItemFeedUpvote.setImageResource(R.drawable.upvote_colored)
            ivItemFeedUpvote.imageTintList = ColorStateList.valueOf(
                ContextCompat.getColor(ivItemFeedUpvote.context, R.color.pinkish_purple)
            )
            tvItemFeedUpvote.setTextColor(ColorStateList.valueOf(
                ContextCompat.getColor(tvItemFeedUpvote.context, R.color.pinkish_purple)))

        } else {
            ivItemFeedUpvote.setImageResource(R.drawable.upvote_v2)
            ivItemFeedUpvote.imageTintList = ColorStateList.valueOf(
                ContextCompat.getColor(ivItemFeedUpvote.context, R.color.default_gray)
            )
            tvItemFeedUpvote.setTextColor(ColorStateList.valueOf(
                ContextCompat.getColor(tvItemFeedUpvote.context, R.color.default_gray)))

        }

    }

    /**
     * Sets the onclick listener for commenting on the posted artwork.
     *
     * @param onClickListener onclick listener for commenting on the posted artwork.
     */
    fun setItemFeedCommentOnClickListener(onClickListener: View.OnClickListener) {
        clItemFeedComment.setOnClickListener(onClickListener)
    }

    /**
     * Sets the onclick listener for sharing the posted artwork.
     *
     * @param onClickListener onclick listener for sharing the posted artwork.
     */
    fun setItemFeedShareOnClickListener(onClickListener: View.OnClickListener) {
        clItemFeedShare.setOnClickListener(onClickListener)
    }

    /**
     * Sets the onclick listener for the profile picture and username of the user who posted
     * the artwork.
     *
     * @param onClickListener onclick listener for the profile picture and username of the user
     * who posted the artwork
     */
    fun setItemFeedProfileOnClickListener(onClickListener: View.OnClickListener) {
        civItemFeedProfilePic.setOnClickListener(onClickListener)
        tvItemFeedUsername.setOnClickListener(onClickListener)
    }

    /**
     * Initializes the components of the view holder.
     */
    init {
        civItemFeedProfilePic = itemView.findViewById(R.id.civ_item_feed_profile_pic)
        tvItemFeedUsername = itemView.findViewById(R.id.tv_item_feed_username)
        ivItemFeedPost = itemView.findViewById(R.id.iv_item_feed_post)
        tvItemFeedTitle = itemView.findViewById(R.id.tv_item_feed_title)
        tvItemFeedUpvoteCounter = itemView.findViewById(R.id.tv_item_feed_upvote_counter)
        tvItemFeedCommentsCounter = itemView.findViewById(R.id.tv_item_feed_comments)
        tvItemFeedComments = itemView.findViewById(R.id.tv_item_feed_comment)
        ibItemFeedBookmark = itemView.findViewById(R.id.ib_item_feed_bookmark)
        ivItemFeedUpvote = itemView.findViewById(R.id.iv_item_feed_upvote)
        tvItemFeedUpvote = itemView.findViewById(R.id.tv_item_feed_upvote)
        clItemFeedUpvote = itemView.findViewById(R.id.cl_item_feed_upvote)
        clItemFeedComment = itemView.findViewById(R.id.cl_item_feed_comment)
        clItemFeedShare = itemView.findViewById(R.id.cl_item_feed_share)

        this.storage = Firebase.storage
        this.storageRef = this.storage.reference

    }
}