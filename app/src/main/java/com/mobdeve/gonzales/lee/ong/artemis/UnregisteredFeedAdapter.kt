package com.mobdeve.gonzales.lee.ong.artemis

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import java.util.*

class UnregisteredFeedAdapter(private val dataPosts: ArrayList<Post>) :
    RecyclerView.Adapter<FeedViewHolder>() {

    /**
     * Context tied to the activity calling this adapter.
     */
    private lateinit var context: Context

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
            val intent = Intent(view.context, ViewCommentsUnregisteredActivity::class.java)
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
        val currentPost = dataPosts[position]

        // holder.setItemFeedProfilePic(currentPost.getProfilePicture()!!)
        Glide.with(context)
            .load(currentPost.getProfilePicture())
            .placeholder(R.drawable.chibi_artemis_hd)
            .error(R.drawable.chibi_artemis_hd)
            .into(holder.getItemFeedProfilePic())

        holder.setItemFeedUsername(currentPost.getUsername())
       // holder.setItemFeedPost(currentPost.getPostImg())
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
        return dataPosts.size
    }
}