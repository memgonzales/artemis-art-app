package com.mobdeve.gonzales.lee.ong.artemis

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import java.util.ArrayList

class OwnPostsAdapter(private val dataPosts: ArrayList<Post>) :
    RecyclerView.Adapter<OwnPostsViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OwnPostsViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val itemView = inflater.inflate(R.layout.item_own_post, parent, false)

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

            view.context.startActivity(intent)
        }

        ownPostsViewHolder.setOwnPostCommentOnClickListener { view ->
            val intent = Intent(view.context, ViewCommentsActivity::class.java)
            view.context.startActivity(intent)
        }

        return ownPostsViewHolder
    }

    override fun onBindViewHolder(holder: OwnPostsViewHolder, position: Int) {
        val currentPost = dataPosts[position]

        holder.setOwnPostProfilePic(currentPost.getProfilePicture())
        holder.setOwnPostUsername(currentPost.getUsername())
        holder.setOwnPostPost(currentPost.getPostImg())
        holder.setOwnPostTitle(currentPost.getTitle())
        holder.setOwnPostUpvoteCounter(currentPost.getNumUpvotes().toString() + " upvotes")
        holder.setOwnPostComments(currentPost.getNumComments().toString() + " comments")
        holder.setOwnPostHighlight(currentPost.getHighlight())

        holder.setOwnPostHighlightOnClickListener { view ->
            if (currentPost.getHighlight()) {
                currentPost.setHighlight(false)
                holder.setOwnPostHighlight(currentPost.getHighlight())
            } else {
                currentPost.setHighlight(true)
                holder.setOwnPostHighlight(currentPost.getHighlight())
                Toast.makeText(view.context, "Added to your Highlights", Toast.LENGTH_SHORT).show()
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
                    currentPost.getTags()
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
}