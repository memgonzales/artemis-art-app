package com.mobdeve.gonzales.lee.ong.artemis

import android.content.res.ColorStateList
import android.view.View
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomsheet.BottomSheetDialog
import de.hdodenhof.circleimageview.CircleImageView

class OwnPostsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    private val civOwnPostProfilePic: CircleImageView
    private val tvOwnPostUsername: TextView

    private val ivOwnPostPost: ImageView
    private val tvOwnPostTitle: TextView
    private val tvOwnPostUpvoteCounter: TextView
    private val tvOwnPostComments: TextView

    private val ivOwnPostHighlight: ImageView
    private val tvOwnPostHighlight: TextView
    private val clOwnPostHighlight: ConstraintLayout
    private val clOwnPostComment: ConstraintLayout
    private val clOwnPostShare: ConstraintLayout

    private val ibItemOwnPostOptions: ImageButton
    private val btmItemOwnPostOptions: BottomSheetDialog

    fun setOwnPostProfilePic(picture: Int) {
        civOwnPostProfilePic.setImageResource(picture)
    }

    fun setOwnPostUsername(name: String?) {
        tvOwnPostUsername.text = name
    }

    fun setOwnPostPost(post: Int) {
        ivOwnPostPost.setImageResource(post)
    }

    fun setOwnPostTitle(title: String?) {
        tvOwnPostTitle.text = title
    }

    fun setOwnPostUpvoteCounter(upvotes: String?) {
        tvOwnPostUpvoteCounter.text = upvotes
    }

    fun setOwnPostComments(comments: String?) {
        tvOwnPostComments.text = comments
    }

    fun setOwnPostHighlightOnClickListener(onClickListener: View.OnClickListener) {
        clOwnPostHighlight.setOnClickListener(onClickListener)
    }

    fun setOwnPostHighlight(highlight: Boolean) {
        if (highlight) {
            ivOwnPostHighlight.setImageResource(R.drawable.baseline_star_24)
            ivOwnPostHighlight.imageTintList = ColorStateList.valueOf(
                ContextCompat.getColor(ivOwnPostHighlight.context, R.color.pinkish_purple)
            )
            tvOwnPostHighlight.setTextColor(
                ColorStateList.valueOf(
                    ContextCompat.getColor(tvOwnPostHighlight.context, R.color.pinkish_purple)))
        } else {
            ivOwnPostHighlight.setImageResource(R.drawable.outline_star_border_24)
            ivOwnPostHighlight.imageTintList = ColorStateList.valueOf(
                ContextCompat.getColor(ivOwnPostHighlight.context, R.color.default_gray)
            )
            tvOwnPostHighlight.setTextColor(
                ColorStateList.valueOf(
                    ContextCompat.getColor(tvOwnPostHighlight.context, R.color.default_gray)))
        }
    }

    fun setOwnPostCommentOnClickListener(onClickListener: View.OnClickListener) {
        clOwnPostComment.setOnClickListener(onClickListener)
    }

    fun setOwnPostShareOnClickListener(onClickListener: View.OnClickListener) {
        clOwnPostShare.setOnClickListener(onClickListener)
    }

    fun setOwnPostProfileOnClickListener(onClickListener: View.OnClickListener) {
        civOwnPostProfilePic.setOnClickListener(onClickListener)
        tvOwnPostUsername.setOnClickListener(onClickListener)
    }

    fun setOwnPostOptionsOnClickListener(onClickListener: View.OnClickListener) {
        ibItemOwnPostOptions.setOnClickListener(onClickListener)
    }

    fun getOwnPostOptions(): BottomSheetDialog {
        return this.btmItemOwnPostOptions
    }

    init {
        civOwnPostProfilePic = itemView.findViewById(R.id.civ_item_own_post_profile_pic)
        tvOwnPostUsername = itemView.findViewById(R.id.tv_item_own_post_username)
        ivOwnPostPost = itemView.findViewById(R.id.iv_item_own_post_post)
        tvOwnPostTitle = itemView.findViewById(R.id.tv_item_own_post_title)
        tvOwnPostUpvoteCounter = itemView.findViewById(R.id.tv_item_own_post_upvote_counter)
        tvOwnPostComments = itemView.findViewById(R.id.tv_item_own_post_comments)
        ivOwnPostHighlight = itemView.findViewById(R.id.iv_item_own_post_highlight)
        tvOwnPostHighlight = itemView.findViewById(R.id.tv_item_own_post_highlight)
        clOwnPostHighlight = itemView.findViewById(R.id.cl_item_own_post_highlight)
        clOwnPostComment = itemView.findViewById(R.id.cl_item_own_post_comment)
        clOwnPostShare = itemView.findViewById(R.id.cl_item_own_post_share)
        ibItemOwnPostOptions = itemView.findViewById(R.id.ib_item_own_post_options)
        btmItemOwnPostOptions = BottomSheetDialog(itemView.context)
    }
}