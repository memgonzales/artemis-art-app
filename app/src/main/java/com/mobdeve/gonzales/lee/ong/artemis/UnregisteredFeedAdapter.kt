package com.mobdeve.gonzales.lee.ong.artemis

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import java.security.Key
import java.util.*

/**
 * Adapter for the recycler view that handles the posts displayed on the feed for unregistered users.
 *
 * @constructor Creates an adapter for the recycler view that handles the posts displayed on the feed
 * for unregistered users.
 */
class UnregisteredFeedAdapter() : RecyclerView.Adapter<FeedViewHolder>() {

    /**
     * Context tied to the activity calling this adapter.
     */
    private lateinit var context: Context

    /**
     * Callback that informs <code>ArrayObjectAdapter</code> how to compute list updates when using
     * <code>DiffUtil</code> in <code>ArrayObjectAdapter.setItems(List, DiffCallback)</code> method.
     */
    private val diffCallbacks = object : DiffUtil.ItemCallback<Post>(){
        override fun areItemsTheSame(oldItem: Post, newItem: Post): Boolean {
            return oldItem.getPostId().equals(newItem.getPostId())
        }

        override fun areContentsTheSame(oldItem: Post, newItem: Post): Boolean {
            return oldItem.equals(newItem)
        }

    }

    /**
     * Helper for computing the difference between two lists via <code>DiffUtil</code> on a background thread.
     */
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
            val intent = Intent(view.context, ViewPostUnregisteredActivity::class.java)
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
            val intent = Intent(view.context, ViewCommentsUnregisteredActivity::class.java)
            val curPost = differ.currentList[feedViewHolder.bindingAdapterPosition]
            intent.putExtra(
                Keys.KEY_POSTID.name,
                curPost.getPostId()
            )
            intent.putExtra(
                Keys.KEY_NUM_COMMENTS.name,
                curPost.getNumComments()
            )
            view.context.startActivity(intent)
        }

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

        holder.setItemFeedBookmarkOnClickListener { view ->
            Toast.makeText(view.context,"Log in or create an account to use this feature", Toast.LENGTH_SHORT).show()
        }

        holder.setItemFeedUpvoteOnClickListener { view ->
            Toast.makeText(view.context,"Log in or create an account to use this feature", Toast.LENGTH_SHORT).show()
        }

        holder.setItemFeedShareOnClickListener { view ->
            Toast.makeText(view.context,"Log in or create an account to use this feature", Toast.LENGTH_SHORT).show()
        }

        holder.setItemFeedProfileOnClickListener { view ->
            val intent = Intent(view.context, ViewUserUnregisteredActivity::class.java)

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

    fun updatePosts(newPosts: List<Post>){
        differ.submitList(newPosts)
    }
}