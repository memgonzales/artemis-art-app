package com.mobdeve.gonzales.lee.ong.artemis

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.drawable.BitmapDrawable
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.facebook.CallbackManager
import com.facebook.FacebookCallback
import com.facebook.FacebookException
import com.facebook.share.Sharer
import com.facebook.share.model.SharePhoto
import com.facebook.share.model.SharePhotoContent
import com.facebook.share.widget.ShareDialog
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import java.util.*

class OwnPostsAdapter(private val dataPosts: ArrayList<Post>, private val parentActivity: Activity) :
    RecyclerView.Adapter<OwnPostsViewHolder>() {
    private lateinit var context: Context

    private var mAuth: FirebaseAuth = Firebase.auth
    private var db: DatabaseReference = Firebase.database.reference

    private var user: FirebaseUser = mAuth.currentUser!!
    private var userId: String = user.uid

    private fun initFirebase() {
        this.mAuth = Firebase.auth
        this.user = this.mAuth.currentUser!!
        this.userId = this.user.uid
        this.db = Firebase.database.reference
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OwnPostsViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val itemView = inflater.inflate(R.layout.item_own_post, parent, false)
        context = parent.context
        val ownPostsViewHolder = OwnPostsViewHolder(itemView)

        itemView.setOnClickListener { view ->
            val intent = Intent(view.context, ViewOwnPostActivity::class.java)

            intent.putExtra(
                Keys.KEY_PROFILE_PICTURE.name,
                dataPosts[ownPostsViewHolder.bindingAdapterPosition].getProfilePicture()
            )
            intent.putExtra(
                Keys.KEY_USERNAME.name,
                dataPosts[ownPostsViewHolder.bindingAdapterPosition].getUsername()
            )
            intent.putExtra(
                Keys.KEY_POST.name,
                dataPosts[ownPostsViewHolder.bindingAdapterPosition].getPostImg()
            )
            intent.putExtra(
                Keys.KEY_TITLE.name,
                dataPosts[ownPostsViewHolder.bindingAdapterPosition].getTitle()
            )
            intent.putExtra(
                Keys.KEY_DATE_POSTED.name,
                dataPosts[ownPostsViewHolder.bindingAdapterPosition].getDatePosted()
            )
            intent.putExtra(
                Keys.KEY_MEDIUM.name,
                dataPosts[ownPostsViewHolder.bindingAdapterPosition].getMedium()
            )
            intent.putExtra(
                Keys.KEY_DIMENSIONS.name,
                dataPosts[ownPostsViewHolder.bindingAdapterPosition].getDimensions()
            )
            intent.putExtra(
                Keys.KEY_DESCRIPTION.name,
                dataPosts[ownPostsViewHolder.bindingAdapterPosition].getDescription()
            )
            intent.putExtra(
                Keys.KEY_TAGS.name,
                dataPosts[ownPostsViewHolder.bindingAdapterPosition].getTags()
            )
            intent.putExtra(
                Keys.KEY_NUM_UPVOTES.name,
                dataPosts[ownPostsViewHolder.bindingAdapterPosition].getNumUpvotes()
            )
            intent.putExtra(
                Keys.KEY_NUM_COMMENTS.name,
                dataPosts[ownPostsViewHolder.bindingAdapterPosition].getNumComments()
            )
            intent.putExtra(
                Keys.KEY_HIGHLIGHT.name,
                dataPosts[ownPostsViewHolder.bindingAdapterPosition].getHighlight()
            )

            view.context.startActivity(intent)
        }

        ownPostsViewHolder.setOwnPostCommentOnClickListener { view ->
            val intent = Intent(view.context, ViewCommentsActivity::class.java)
            view.context.startActivity(intent)
        }

        initFirebase()
        return ownPostsViewHolder
    }

    override fun onBindViewHolder(holder: OwnPostsViewHolder, position: Int) {
        val currentPost = dataPosts[position]

        // placeholder for sample image
       // val currentPicture = "https://firebasestorage.googleapis.com/v0/b/artemis-77e4e.appspot.com/o/shoobs.jpg?alt=media&token=759445bd-d3b6-4384-8d8e-0fe5f5f45ba5"
        //Glide.with(context).load(currentPicture).into(holder.getOwnPostProfilePic())

        Glide.with(context)
            .load(currentPost.getProfilePicture())
            .error(R.drawable.chibi_artemis_hd)
            .into(holder.getOwnPostProfilePic())


    //    holder.setOwnPostProfilePic(currentPost.getProfilePicture())
        holder.setOwnPostUsername(currentPost.getUsername())
        //Glide.with(context).load(currentPicture).into(holder.getOwnPostPost())

        Glide.with(context)
            .load(currentPost.getPostImg())
            .error(R.drawable.placeholder)
            .into(holder.getOwnPostPost())


    //    holder.setOwnPostPost(currentPost.getPostImg())
        holder.setOwnPostTitle(currentPost.getTitle())
        holder.setOwnPostUpvoteCounter(currentPost.getNumUpvotes().toString() + " upvotes")
        holder.setOwnPostComments(currentPost.getNumComments().toString() + " comments")
        holder.setOwnPostHighlight(currentPost.getHighlight())

        holder.setOwnPostHighlightOnClickListener { view ->
            if (currentPost.getHighlight()) {
                currentPost.setHighlight(false)
                holder.setOwnPostHighlight(currentPost.getHighlight())

                updateHighlightDB(currentPost.getPostId(), null)

            } else {
                currentPost.setHighlight(true)
                holder.setOwnPostHighlight(currentPost.getHighlight())
                Toast.makeText(view.context, "Added to your Highlights", Toast.LENGTH_SHORT).show()

                updateHighlightDB(currentPost.getPostId(), currentPost.getPostId())
            }
        }

        holder.setOwnPostShareOnClickListener { view ->
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

            // val edit: ConstraintLayout = dialogView.findViewById(R.id.cl_dialog_post_artwork_gallery)
            // val delete: ConstraintLayout = dialogView.findViewById(R.id.cl_dialog_post_artwork_photo)

            val edit: ConstraintLayout = dialogView.findViewById(R.id.cl_dialog_own_post_edit)
            val delete: ConstraintLayout = dialogView.findViewById(R.id.cl_dialog_own_post_delete)

            edit.setOnClickListener{ view ->
                dialog.dismiss()
                val intent = Intent(view.context, EditPostActivity::class.java)
                val tags = currentPost.getTags()
                val tagsString = tags.joinToString(", ")

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
            }

            delete.setOnClickListener { view ->
                Toast.makeText(view.context, "Your post has been deleted", Toast.LENGTH_SHORT).show()
                dialog.dismiss()
            }


            dialog.show()
        }
    }

    override fun getItemCount(): Int {
        return dataPosts.size
    }

    fun updateHighlightDB(postKey: String, postVal: String?){
        val updates = hashMapOf<String, Any?>(
            "/${Keys.KEY_DB_USERS.name}/$userId/${Keys.highlights.name}/$postKey" to postVal
        )

        db.updateChildren(updates)
    }
}