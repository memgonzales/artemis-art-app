package com.mobdeve.gonzales.lee.ong.artemis

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import java.util.*


/**
 * Adapter for the recycler view that handles the posts displayed on the search results for
 * unregistered users.
 *
 * @constructor Creates an adapter for the recycler view that handles the posts displayed on the
 * search results for unregistered users.
 * @param dataPosts Posts displayed on the search results.
 */
class SearchResultsUnregisteredAdapter() : RecyclerView.Adapter<SearchViewHolder>() {


    /**
     * Context tied to the activity calling this adapter.
     */
    private lateinit var context: Context

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
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val itemView = inflater.inflate(R.layout.item_rectangular_pic_grid, parent, false)

        this.context = parent.context

        val searchViewHolder = SearchViewHolder(itemView)

        itemView.setOnClickListener { view ->
            val intent = Intent(view.context, ViewPostUnregisteredActivity::class.java)
            val curPost = differ.currentList[searchViewHolder.bindingAdapterPosition]
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

        return searchViewHolder
    }

    /**
     * Called by RecyclerView to display the data at the specified position.
     *
     * @param holder The ViewHolder which should be updated to represent the contents of the item
     * at the given position in the data set.
     * @param position The position of the item within the adapter's data set.
     */
    override fun onBindViewHolder(holder: SearchViewHolder, position: Int) {
        val currentPost = differ.currentList[position]

        if (!(context as Activity).isFinishing) {
            Glide.with(context)
                .load(currentPost.getPostImg())
                .placeholder(R.drawable.placeholder)
                .error(R.drawable.placeholder)
                .into(holder.getItemSearchResults())
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