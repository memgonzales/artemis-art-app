package com.mobdeve.gonzales.lee.ong.artemis

import android.os.Bundle
import androidx.recyclerview.widget.DiffUtil

class PostUtilCallbacks(private val oldPosts: ArrayList<Post>, private  val newPosts: ArrayList<Post>) : DiffUtil.Callback(){

    /*
    private var oldPosts: ArrayList<Post> = ArrayList<Post>()
    private var newPosts: ArrayList<Post> = ArrayList<Post>()

    constructor(oldPosts: ArrayList<Post>, newPosts: ArrayList<Post>){
        this.oldPosts = oldPosts
        this.newPosts = newPosts
    }

     */


    override fun getOldListSize(): Int {

        if (oldPosts != null){
            return oldPosts.size
        }

        return oldPosts.size
    }

    override fun getNewListSize(): Int {
        if (newPosts != null){
            return newPosts.size
        }

        return 0
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return newPosts.get(newItemPosition).getPostId().equals(oldPosts.get(oldItemPosition).getPostId())
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return newPosts.get(newItemPosition).equals(oldPosts.get(oldItemPosition))
    }

    override fun getChangePayload(oldItemPosition: Int, newItemPosition: Int): Any? {

/*
        val newPosts = newPosts.get(newItemPosition)
        val oldPosts = oldPosts.get(oldItemPosition)

        val bundle = Bundle()

        if (!newPosts.getUpvote().equals(oldPosts.getUpvote())){
            bundle.putBoolean(Keys.upvote.name, newPosts.getUpvote())
        }

        if (!newPosts.getBookmark().equals(oldPosts.getBookmark())){
            bundle.putBoolean(Keys.bookmark.name, newPosts.getBookmark())
        }

        if (!newPosts.getTitle().equals(oldPosts.getTitle())){
            bundle.putString(Keys.title.name, newPosts.getTitle())
        }

        if (bundle.size() == 0){
            return null
        }

        return bundle

 */




       return super.getChangePayload(oldItemPosition, newItemPosition)
    }


}