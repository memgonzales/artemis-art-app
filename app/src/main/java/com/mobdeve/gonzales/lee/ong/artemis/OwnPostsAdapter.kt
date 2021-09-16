package com.mobdeve.gonzales.lee.ong.artemis

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
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

/**
 * Adapter for the recycler view that handles the user's own posts.
 *
 * @constructor Creates an adapter for the recycler view that handles the user's own posts.
 */
class OwnPostsAdapter(private val parentActivity: Activity) :
    RecyclerView.Adapter<OwnPostsViewHolder>() {

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
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OwnPostsViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val itemView = inflater.inflate(R.layout.item_own_post, parent, false)
        this.context = parent.context

        val ownPostsViewHolder = OwnPostsViewHolder(itemView)

        itemView.setOnClickListener { view ->
            val intent = Intent(view.context, ViewOwnPostActivity::class.java)
            val curPost = differ.currentList[ownPostsViewHolder.bindingAdapterPosition]

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
                Keys.KEY_NUM_UPVOTES.name,
                curPost.getNumUpvotes()
            )
            intent.putExtra(
                Keys.KEY_NUM_COMMENTS.name,
                curPost.getNumComments()
            )
            intent.putExtra(
                Keys.KEY_HIGHLIGHT.name,
                curPost.getHighlight()
            )

            view.context.startActivity(intent)
        }

        ownPostsViewHolder.setOwnPostCommentOnClickListener { view ->
            val intent = Intent(view.context, ViewOwnPostCommentsActivity::class.java)
            val curPost = differ.currentList[ownPostsViewHolder.bindingAdapterPosition]

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

        return ownPostsViewHolder
    }

    /**
     * Called by RecyclerView to display the data at the specified position.
     *
     * @param holder The ViewHolder which should be updated to represent the contents of the item
     * at the given position in the data set.
     * @param position The position of the item within the adapter's data set.
     */
    override fun onBindViewHolder(holder: OwnPostsViewHolder, position: Int) {
        val currentPost = differ.currentList[position]

        holder.setOwnPostProfilePic(currentPost.getProfilePicture()!!)
        /*
        Glide.with(context)
            .load(currentPost.getProfilePicture())
            .placeholder(R.drawable.chibi_artemis_hd)
            .error(R.drawable.chibi_artemis_hd)
            .into(holder.getOwnPostProfilePic())


         */
        holder.setOwnPostUsername(currentPost.getUsername())

        holder.setOwnPostPost(currentPost.getPostImg()!!)
        /*
        Glide.with(context)
            .load(currentPost.getPostImg())
            .placeholder(R.drawable.placeholder)
            .error(R.drawable.placeholder)
            .into(holder.getOwnPostPost())
         */
        holder.setOwnPostTitle(currentPost.getTitle())

        if (currentPost.getNumUpvotes() == 1) {
            holder.setOwnPostUpvoteCounter(currentPost.getNumUpvotes().toString() + " upvote")
        } else {
            holder.setOwnPostUpvoteCounter(currentPost.getNumUpvotes().toString() + " upvotes")
        }

        if (currentPost.getNumComments() == 1) {
            holder.setOwnPostComments(currentPost.getNumComments().toString() + " comment")
        } else {
            holder.setOwnPostComments(currentPost.getNumComments().toString() + " comments")
        }

        holder.setOwnPostHighlight(currentPost.getHighlight())

        holder.setOwnPostHighlightOnClickListener { view ->
            if (currentPost.getHighlight()) {
                currentPost.setHighlight(false)
                holder.setOwnPostHighlight(currentPost.getHighlight())

                this.firebaseHelper.updateHighlightDB(currentPost.getPostId(), null)

            } else {
                currentPost.setHighlight(true)
                holder.setOwnPostHighlight(currentPost.getHighlight())
                Toast.makeText(view.context, "Added to your Highlights", Toast.LENGTH_SHORT).show()

                this.firebaseHelper.updateHighlightDB(currentPost.getPostId(), currentPost.getPostId())
            }
        }

        holder.setOwnPostShareOnClickListener {
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
                val bitmapDrawable = holder.getOwnPostPost().drawable as BitmapDrawable
                val bitmap = bitmapDrawable.bitmap
                val username = "@" + holder.getOwnPostUsername().text.toString()
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

        holder.setOwnPostProfileOnClickListener { view ->
            val intent = Intent(view.context, ViewProfileActivity::class.java)
            view.context.startActivity(intent)
        }

        holder.setOwnPostOptionsOnClickListener { view ->
            val dialogView = LayoutInflater.from(view.context).inflate(R.layout.dialog_own_post, null)
            val dialog = holder.getOwnPostOptions()
            dialog.setContentView(dialogView)

            val edit: ConstraintLayout = dialogView.findViewById(R.id.cl_dialog_own_post_edit)
            val delete: ConstraintLayout = dialogView.findViewById(R.id.cl_dialog_own_post_delete)

            edit.setOnClickListener{ view ->
                dialog.dismiss()
                val intent = Intent(view.context, EditPostActivity::class.java)
                val tags = currentPost.getTags()
                val tagsString = tags?.joinToString(", ")

                intent.putExtra(
                    Keys.KEY_USERID.name,
                    currentPost.getUserId()
                )
                intent.putExtra(
                    Keys.KEY_POSTID.name,
                    currentPost.getPostId()
                )
                intent.putExtra(
                    Keys.KEY_TITLE.name,
                    currentPost.getTitle()
                )
                intent.putExtra(
                    Keys.KEY_MEDIUM.name,
                    currentPost.getMedium()
                )
                intent.putExtra(
                    Keys.KEY_DIMENSIONS.name,
                    currentPost.getDimensions()
                )
                intent.putExtra(
                    Keys.KEY_DESCRIPTION.name,
                    currentPost.getDescription()
                )
                intent.putExtra(
                    Keys.KEY_TAGS.name,
                    tagsString
                )
                intent.putExtra(
                    Keys.KEY_POST.name,
                    currentPost.getPostImg()
                )
                view.context.startActivity(intent)
                notifyItemChanged(position)
            }

            delete.setOnClickListener {
                this.firebaseHelper.deletePostDB(currentPost.getPostId()!!, false)
                //Toast.makeText(view.context, "Your post has been deleted", Toast.LENGTH_SHORT).show()
                dialog.dismiss()

            }


            dialog.show()
        }
    }

    override fun onBindViewHolder(holder: OwnPostsViewHolder, position: Int, payloads: MutableList<Any>) {
        super.onBindViewHolder(holder, position, payloads)

        if (payloads.isEmpty()){
            super.onBindViewHolder(holder, position, payloads)
        }

        else{
            val bundle = payloads.get(0) as? Bundle

            if (bundle != null){
                for (key in bundle!!.keySet()){
                    if (key.equals(Keys.title.name)){
                        holder.setOwnPostTitle(bundle.getString(Keys.title.name))
                    }
                }
            }

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

    fun updatePosts(newPosts: ArrayList<Post>){
        differ.submitList(newPosts)
    }
}