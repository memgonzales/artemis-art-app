package com.mobdeve.gonzales.lee.ong.artemis

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.drawable.BitmapDrawable
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
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

/**
 * Adapter for the recycler view that handles the posts of followed users.
 *
 * @constructor Creates an adapter for the recycler view that handles the posts of followed users.
 * @param dataPosts Posts of followed users.
 * @param parentActivity Activity calling this adapter.
 */
class FeedFollowedAdapter(private val parentActivity: Activity) :
    RecyclerView.Adapter<FeedViewHolder>() {

    /**
     * Context tied to the activity calling this adapter.
     */
    private lateinit var context: Context

    /**
     * Wrapper over Firebase's realtime database.
     */
    private lateinit var firebaseHelper: FirebaseHelper

    private val diffCallbacks = object : DiffUtil.ItemCallback<Post>(){
        override fun areItemsTheSame(oldItem: Post, newItem: Post): Boolean {
            return oldItem.getPostId().equals(newItem.getPostId())
        }

        override fun areContentsTheSame(oldItem: Post, newItem: Post): Boolean {
            return oldItem.equals(newItem)
        }

    }

    private val differ: AsyncListDiffer<Post> = AsyncListDiffer(this, diffCallbacks)

    /**
     * Called when RecyclerView needs a new <code>RecyclerView.ViewHolder</code> of the given type
     * to represent an item.
     *
     * @param parent The ViewGroup into which the new View will be added after it is bound to an
     * adapter position.
     * @param viewType The view type of the new View.
     *
     * @return A new ViewHolder that holds a View of the given view type.
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FeedViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val itemView = inflater.inflate(R.layout.item_feed, parent, false)
        this.context = parent.context
        val feedViewHolder = FeedViewHolder(itemView)

        itemView.setOnClickListener { view ->
            val intent = Intent(view.context, ViewPostFollowedActivity::class.java)
            val curPost = differ.currentList[feedViewHolder.bindingAdapterPosition]

            intent.putExtra(
                Keys.KEY_USERID.name,
                curPost.getUserId()
            )
            intent.putExtra(
                Keys.KEY_POSTID.name,
                curPost.getPostId()
            )
            intent.putExtra(
                Keys.KEY_PROFILE_PICTURE.name,
                curPost.getProfilePicture()
            )
            intent.putExtra(
                Keys.KEY_USERNAME.name,
                curPost.getUsername()
            )
            intent.putExtra(
                Keys.KEY_POST.name,
                curPost.getPostImg()
            )
            intent.putExtra(
                Keys.KEY_TITLE.name,
                curPost.getTitle()
            )
            intent.putExtra(
                Keys.KEY_NUM_UPVOTES.name,
                curPost.getNumUpvotes()
            )
            intent.putExtra(
                Keys.KEY_NUM_COMMENTS.name,
                curPost.getNumComments()
            )
            intent.putExtra(
                Keys.KEY_DATE_POSTED.name,
                curPost.getDatePosted()
            )
            intent.putExtra(
                Keys.KEY_MEDIUM.name,
                curPost.getMedium()
            )
            intent.putExtra(
                Keys.KEY_DIMENSIONS.name,
                curPost.getDimensions()
            )
            intent.putExtra(
                Keys.KEY_DESCRIPTION.name,
                curPost.getDescription()
            )
            intent.putExtra(
                Keys.KEY_TAGS.name,
                curPost.getTags()
            )
            intent.putExtra(
                Keys.KEY_BOOKMARK.name,
                curPost.getBookmark()
            )
            intent.putExtra(
                Keys.KEY_UPVOTE.name,
                curPost.getUpvote()
            )

            view.context.startActivity(intent)
        }

        feedViewHolder.setItemFeedCommentOnClickListener { view ->
            val intent = Intent(view.context, ViewCommentsFollowedActivity::class.java)
            val curPost = differ.currentList[feedViewHolder.bindingAdapterPosition]

            intent.putExtra(
                Keys.KEY_USERID.name,
                curPost.getUserId()
            )
            intent.putExtra(
                Keys.KEY_POSTID.name,
                curPost.getPostId()
            )
            intent.putExtra(
                Keys.KEY_PROFILE_PICTURE.name,
                curPost.getProfilePicture()
            )
            intent.putExtra(
                Keys.KEY_USERNAME.name,
                curPost.getUsername()
            )
            intent.putExtra(
                Keys.KEY_POST.name,
                curPost.getPostImg()
            )
            intent.putExtra(
                Keys.KEY_TITLE.name,
                curPost.getTitle()
            )
            intent.putExtra(
                Keys.KEY_NUM_UPVOTES.name,
                curPost.getNumUpvotes()
            )
            intent.putExtra(
                Keys.KEY_NUM_COMMENTS.name,
                curPost.getNumComments()
            )
            intent.putExtra(
                Keys.KEY_DATE_POSTED.name,
                curPost.getDatePosted()
            )
            intent.putExtra(
                Keys.KEY_MEDIUM.name,
                curPost.getMedium()
            )
            intent.putExtra(
                Keys.KEY_DIMENSIONS.name,
                curPost.getDimensions()
            )
            intent.putExtra(
                Keys.KEY_DESCRIPTION.name,
                curPost.getDescription()
            )
            intent.putExtra(
                Keys.KEY_TAGS.name,
                curPost.getTags()
            )
            intent.putExtra(
                Keys.KEY_BOOKMARK.name,
                curPost.getBookmark()
            )
            intent.putExtra(
                Keys.KEY_UPVOTE.name,
                curPost.getUpvote()
            )

            view.context.startActivity(intent)
        }

        this.firebaseHelper = FirebaseHelper(context)

        return feedViewHolder
    }

    /**
     * Called by RecyclerView to display the data at the specified position.
     *
     * @param holder The ViewHolder which should be updated to represent the contents of the item
     * at the given position in the data set.
     * @param position The position of the item within the adapter's data set.
     */
    override fun onBindViewHolder(holder: FeedViewHolder, position: Int) {
        val currentPost = differ.currentList[position]

        if (!(context as Activity).isFinishing) {
            Glide.with(context)
                .load(currentPost.getProfilePicture())
                .placeholder(R.drawable.chibi_artemis_hd)
                .error(R.drawable.chibi_artemis_hd)
                .into(holder.getItemFeedProfilePic())
        }

        holder.setItemFeedUsername(currentPost.getUsername())

        if (!(context as Activity).isFinishing) {
            Glide.with(context)
                .load(currentPost.getPostImg())
                .placeholder(R.drawable.placeholder)
                .error(R.drawable.placeholder)
                .into(holder.getItemFeedPost())
        }

        holder.setItemFeedTitle(currentPost.getTitle())

        if (currentPost.getNumUpvotes() == 1) {
            holder.setItemFeedUpvoteCounter(currentPost.getNumUpvotes().toString() + " upvote")
        } else {
            holder.setItemFeedUpvoteCounter(currentPost.getNumUpvotes().toString() + " upvotes")
        }

        if (currentPost.getNumComments() == 1) {
            holder.setItemFeedCommentCounter(currentPost.getNumComments().toString() + " comment")
        } else {
            holder.setItemFeedCommentCounter(currentPost.getNumComments().toString() + " comments")
        }

        holder.setItemFeedBookmark(currentPost.getBookmark())
        holder.setItemFeedUpvote(currentPost.getUpvote())

        holder.setItemFeedBookmarkOnClickListener {
            currentPost.setBookmark(!currentPost.getBookmark())
            holder.setItemFeedBookmark(currentPost.getBookmark())

            if(currentPost.getBookmark()){
                firebaseHelper.updateBookmarkDB(firebaseHelper.getUserId(), "1", currentPost.getPostId(),"1")
            }

            else{
                firebaseHelper.updateBookmarkDB( firebaseHelper.getUserId(), null, currentPost.getPostId(), null)
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

        holder.setItemFeedShareOnClickListener {
            val cmFacebook = CallbackManager.Factory.create()
            val sdFacebook = ShareDialog(parentActivity)

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

    /**
     * Returns the total number of items in the data set held by the adapter.
     *
     * @return The total number of items in this adapter.
     */
    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    /**
     * Updates the posts displayed through the adapter.
     *
     * @param newPosts List of new posts to be displayed through the adapter.
     */
    fun updatePosts(newPosts: List<Post>){
        differ.submitList(newPosts)
    }
}