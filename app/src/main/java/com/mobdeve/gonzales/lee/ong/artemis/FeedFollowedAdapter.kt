package com.mobdeve.gonzales.lee.ong.artemis

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.drawable.BitmapDrawable
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.facebook.CallbackManager
import com.facebook.FacebookCallback
import com.facebook.FacebookException
import com.facebook.share.Sharer
import com.facebook.share.model.SharePhoto
import com.facebook.share.model.SharePhotoContent
import com.facebook.share.widget.ShareDialog
import java.util.*

class FeedFollowedAdapter(private val dataPosts: ArrayList<Post>, private val parentActivity: Activity) :
    RecyclerView.Adapter<FeedViewHolder>() {
    private lateinit var context: Context

    private lateinit var firebaseHelper: FirebaseHelper

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FeedViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val itemView = inflater.inflate(R.layout.item_feed, parent, false)
        context = parent.context
        val feedViewHolder = FeedViewHolder(itemView)

        itemView.setOnClickListener { view ->
            val intent = Intent(view.context, ViewPostFollowedActivity::class.java)

            intent.putExtra(
                Keys.KEY_USERID.name,
                dataPosts[feedViewHolder.bindingAdapterPosition].getUserId()
            )
            intent.putExtra(
                Keys.KEY_POSTID.name,
                dataPosts[feedViewHolder.bindingAdapterPosition].getPostId()
            )
            intent.putExtra(
                Keys.KEY_PROFILE_PICTURE.name,
                dataPosts[feedViewHolder.bindingAdapterPosition].getProfilePicture()
            )
            intent.putExtra(
                Keys.KEY_USERNAME.name,
                dataPosts[feedViewHolder.bindingAdapterPosition].getUsername()
            )
            intent.putExtra(
                Keys.KEY_POST.name,
                dataPosts[feedViewHolder.bindingAdapterPosition].getPostImg()
            )
            intent.putExtra(
                Keys.KEY_TITLE.name,
                dataPosts[feedViewHolder.bindingAdapterPosition].getTitle()
            )
            intent.putExtra(
                Keys.KEY_NUM_UPVOTES.name,
                dataPosts[feedViewHolder.bindingAdapterPosition].getNumUpvotes()
            )
            intent.putExtra(
                Keys.KEY_NUM_COMMENTS.name,
                dataPosts[feedViewHolder.bindingAdapterPosition].getNumComments()
            )
            intent.putExtra(
                Keys.KEY_DATE_POSTED.name,
                dataPosts[feedViewHolder.bindingAdapterPosition].getDatePosted()
            )
            intent.putExtra(
                Keys.KEY_MEDIUM.name,
                dataPosts[feedViewHolder.bindingAdapterPosition].getMedium()
            )
            intent.putExtra(
                Keys.KEY_DIMENSIONS.name,
                dataPosts[feedViewHolder.bindingAdapterPosition].getDimensions()
            )
            intent.putExtra(
                Keys.KEY_DESCRIPTION.name,
                dataPosts[feedViewHolder.bindingAdapterPosition].getDescription()
            )
            intent.putExtra(
                Keys.KEY_TAGS.name,
                dataPosts[feedViewHolder.bindingAdapterPosition].getTags()
            )
            intent.putExtra(
                Keys.KEY_BOOKMARK.name,
                dataPosts[feedViewHolder.bindingAdapterPosition].getBookmark()
            )
            intent.putExtra(
                Keys.KEY_UPVOTE.name,
                dataPosts[feedViewHolder.bindingAdapterPosition].getUpvote()
            )

            view.context.startActivity(intent)
        }

        feedViewHolder.setItemFeedCommentOnClickListener { view ->
            val intent = Intent(view.context, ViewCommentsFollowedActivity::class.java)
            view.context.startActivity(intent)
        }

        this.firebaseHelper = FirebaseHelper(context)

        return feedViewHolder
    }

    override fun onBindViewHolder(holder: FeedViewHolder, position: Int) {
        val currentPost = dataPosts[position]

       Glide.with(context)
            .load(currentPost.getProfilePicture())
            .placeholder(R.drawable.chibi_artemis_hd)
            .error(R.drawable.chibi_artemis_hd)
            .into(holder.getItemFeedProfilePic())

        holder.setItemFeedUsername(currentPost.getUsername())

        Glide.with(context)
            .load(currentPost.getPostImg())
            .placeholder(R.drawable.placeholder)
            .error(R.drawable.placeholder)
            .into(holder.getItemFeedPost())

        holder.setItemFeedTitle(currentPost.getTitle())
        holder.setItemFeedUpvoteCounter(currentPost.getNumUpvotes().toString() + " upvotes")
        holder.setItemFeedComments(currentPost.getNumComments().toString() + " comments")
        holder.setItemFeedBookmark(currentPost.getBookmark())
        holder.setItemFeedUpvote(currentPost.getUpvote())

        holder.setItemFeedBookmarkOnClickListener {
            currentPost.setBookmark(!currentPost.getBookmark())
            holder.setItemFeedBookmark(currentPost.getBookmark())

            if(currentPost.getBookmark()){
                firebaseHelper.updateBookmarkDB("1", currentPost.getPostId(),"1")
            }

            else{
                firebaseHelper.updateBookmarkDB( null, currentPost.getPostId(), null)
            }
        }

        holder.setItemFeedUpvoteOnClickListener {
            if (currentPost.getUpvote()) {
                currentPost.setUpvote(false)
                currentPost.setNumUpvotes(currentPost.getNumUpvotes() - 1)
                holder.setItemFeedUpvoteCounter(currentPost.getNumUpvotes().toString() + " upvotes")
                holder.setItemFeedUpvote(currentPost.getUpvote())

                firebaseHelper.updateUpvoteDB(null, currentPost.getPostId(), null, currentPost.getNumUpvotes())

            } else {
                currentPost.setUpvote(true)
                currentPost.setNumUpvotes(currentPost.getNumUpvotes() + 1)
                holder.setItemFeedUpvoteCounter(currentPost.getNumUpvotes().toString() + " upvotes")
                holder.setItemFeedUpvote(currentPost.getUpvote())

                firebaseHelper.updateUpvoteDB("1", currentPost.getPostId(), "1", currentPost.getNumUpvotes())
            }
        }

        holder.setItemFeedShareOnClickListener { view ->
            var cmFacebook = CallbackManager.Factory.create()
            var sdFacebook = ShareDialog(parentActivity)

            sdFacebook.registerCallback(cmFacebook, object : FacebookCallback<Sharer.Result?> {
                override fun onSuccess(result: Sharer.Result?) {
                    Toast.makeText(parentActivity, "Shared on Facebook", Toast.LENGTH_SHORT).show()
                }

                override fun onCancel() {
                    Toast.makeText(parentActivity, "Sharing cancelled", Toast.LENGTH_SHORT).show()
                }

                override fun onError(error: FacebookException) {
                    Toast.makeText(parentActivity, "Sharing error occurred", Toast.LENGTH_SHORT).show()
                }
            })

            if (ShareDialog.canShow(SharePhotoContent::class.java)) {
                val bitmapDrawable = holder.getItemFeedPost().drawable as BitmapDrawable
                val bitmap = bitmapDrawable.bitmap
                val username = "@" + holder.getItemFeedUsername().text.toString()
                val captionedImage = CaptionPlacer.placeCaption(bitmap, username, "Posted on Artemis")
                val sharePhoto = SharePhoto.Builder()
                    .setBitmap(captionedImage)
                    .build()
                val sharePhotoContent = SharePhotoContent.Builder()
                    .addPhoto(sharePhoto)
                    .build()
                sdFacebook.show(sharePhotoContent)

                Toast.makeText(parentActivity, "Opening Facebook", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(parentActivity, "Unable to share post", Toast.LENGTH_SHORT).show()
            }
        }

        holder.setItemFeedProfileOnClickListener { view ->
            val intent = Intent(view.context, ViewUserActivity::class.java)

            intent.putExtra(
                Keys.KEY_USERID.name,
                currentPost.getUserId()
            )

            view.context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return dataPosts.size
    }
}