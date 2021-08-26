package com.mobdeve.gonzales.lee.ong.artemis

import android.opengl.Visibility
import android.view.View
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomsheet.BottomSheetDialog
import de.hdodenhof.circleimageview.CircleImageView

class CommentsViewHolder (itemView: View) : RecyclerView.ViewHolder(itemView) {
    private val civItemCommentProfilePic: CircleImageView
    private val tvItemCommentUsername: TextView
    private val tvItemCommentDate: TextView
    private val tvItemCommentComment: TextView

    private val ibItemCommentEdit: ImageButton
    private val btmItemCommentOptions: BottomSheetDialog

    fun setItemCommentProfilePic(picture: Int) {
        civItemCommentProfilePic.setImageResource(picture)
    }

    fun setItemCommentUsername(name: String?) {
        tvItemCommentUsername.text = name
    }

    fun setItemCommentDate(date: String?) {
        tvItemCommentDate.text = date
    }

    fun setItemCommentComment(comment: String?) {
        tvItemCommentComment.text = comment
    }

    fun setItemCommentEdit(editable: Boolean) {
        if (editable)
            ibItemCommentEdit.visibility = View.VISIBLE
        else
            ibItemCommentEdit.visibility = View.GONE
    }

    fun setItemCommentProfileOnClickListener(onClickListener: View.OnClickListener) {
        civItemCommentProfilePic.setOnClickListener(onClickListener)
        tvItemCommentUsername.setOnClickListener(onClickListener)
    }

    fun setItemCommentOptionsOnClickListener(onClickListener: View.OnClickListener) {
        ibItemCommentEdit.setOnClickListener(onClickListener)
    }

    fun getItemCommentOptions(): BottomSheetDialog {
        return this.btmItemCommentOptions
    }

    init {
        civItemCommentProfilePic = itemView.findViewById(R.id.civ_item_comment_profile_pic)
        tvItemCommentUsername = itemView.findViewById(R.id.tv_item_comment_username)
        tvItemCommentDate = itemView.findViewById(R.id.tv_item_comment_date)
        tvItemCommentComment = itemView.findViewById(R.id.tv_item_comment_comment)
        ibItemCommentEdit = itemView.findViewById(R.id.ib_item_comment_edit)
        btmItemCommentOptions = BottomSheetDialog(itemView.context)
    }
}