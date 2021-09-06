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

class FeedAdapter(private val dataPosts: ArrayList<Post>, private val parentActivity: Activity) :
    RecyclerView.Adapter<FeedViewHolder>() {
    private lateinit var context: Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FeedViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val itemView = inflater.inflate(R.layout.item_feed, parent, false)
        context = parent.context
        val feedViewHolder = FeedViewHolder(itemView)

        itemView.setOnClickListener { view ->
            val intent = Intent(view.context, ViewPostActivity::class.java)

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
            val intent = Intent(view.context, ViewCommentsActivity::class.java)
            view.context.startActivity(intent)
        }

        return feedViewHolder
    }

    override fun onBindViewHolder(holder: FeedViewHolder, position: Int) {
        val currentPost = dataPosts[position]

        // placeholder for sample image
        //val currentPicture = "https://firebasestorage.googleapis.com/v0/b/artemis-77e4e.appspot.com/o/shoobs.jpg?alt=media&token=759445bd-d3b6-4384-8d8e-0fe5f5f45ba5"
        Glide.with(context).load(currentPost.getProfilePicture()).into(holder.getItemFeedProfilePic())
    //    Glide.with(context).load(currentPicture).into(holder.getItemFeedProfilePic())
        holder.setItemFeedProfilePic(currentPost.getProfilePicture())
        holder.setItemFeedUsername(currentPost.getUsername())
        Glide.with(context).load(currentPost.getPostImg()).into(holder.getItemFeedPost())
    //    Glide.with(context).load(currentPicture).into(holder.getItemFeedPost())
    //    holder.setItemFeedPost(currentPost.getPostImg())
        holder.setItemFeedTitle(currentPost.getTitle())
        holder.setItemFeedUpvoteCounter(currentPost.getNumUpvotes().toString() + " upvotes")
        holder.setItemFeedComments(currentPost.getNumComments().toString() + " comments")
        holder.setItemFeedBookmark(currentPost.getBookmark())
        holder.setItemFeedUpvote(currentPost.getUpvote())

        holder.setItemFeedBookmarkOnClickListener {
            currentPost.setBookmark(!currentPost.getBookmark())
            holder.setItemFeedBookmark(currentPost.getBookmark())
        }

        holder.setItemFeedUpvoteOnClickListener {
            if (currentPost.getUpvote()) {
                currentPost.setUpvote(false)
                currentPost.setNumUpvotes(currentPost.getNumUpvotes() - 1)
                holder.setItemFeedUpvoteCounter(currentPost.getNumUpvotes().toString() + " upvotes")
                holder.setItemFeedUpvote(currentPost.getUpvote())
            } else {
                currentPost.setUpvote(true)
                currentPost.setNumUpvotes(currentPost.getNumUpvotes() + 1)
                holder.setItemFeedUpvoteCounter(currentPost.getNumUpvotes().toString() + " upvotes")
                holder.setItemFeedUpvote(currentPost.getUpvote())
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
                Keys.KEY_PROFILE_PICTURE.name,
                currentPost.getProfilePicture()
            )
            intent.putExtra(
                Keys.KEY_USERNAME.name,
                currentPost.getUsername()
            )
            intent.putExtra(
                Keys.KEY_BIO.name,
                "Dummy bio"
            )

            view.context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return dataPosts.size
    }
}
