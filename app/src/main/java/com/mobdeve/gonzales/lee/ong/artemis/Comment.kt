package com.mobdeve.gonzales.lee.ong.artemis

import com.google.firebase.database.Exclude

class Comment {
    private var userId: String? = null
    private var postId: String? = null
    private var commentId: String? = null
    private var profilePicture: String? = null
    private var username: String? = null
    private var dateCommented: String? = null
    private var commentBody: String? = null
    @get:Exclude private var editable: Boolean = false

    constructor() {

    }

    constructor(userId: String, postId: String, profilePicture: String, username: String, commentBody: String) {
        this.userId = userId
        this.postId = postId
        this.profilePicture = profilePicture
        this.username = username

        this.dateCommented = CustomDate().toStringFull()
        this.commentBody = commentBody
        //this.editable = editable
    }

    fun getUserId(): String? {
        return this.userId
    }

    fun getPostId(): String? {
        return this.postId
    }

    fun getCommentId(): String? {
        return this.commentId
    }

    fun getProfilePicture(): String? {
        return this.profilePicture
    }

    fun getUsername(): String? {
        return this.username
    }

    fun getDateCommented(): String? {
        return this.dateCommented
    }

    fun getCommentBody(): String? {
        return this.commentBody
    }

    fun getEditable(): Boolean {
        return this.editable
    }


    fun setUserId(userId: String?){
        this.userId = userId
    }

    fun setPostId(postId: String?){
        this.postId = postId
    }

    fun setCommentId(commentId: String?){
        this.commentId = commentId
    }

    fun setProfilePicture(profPic: String?){
        this.profilePicture = profPic
    }

    fun setUsername(username: String?){
        this.username = username
    }

    fun setDateCommented(dateCommented: String?){
        this.dateCommented = dateCommented
    }

    fun setCommentBody(commentBody: String?){
        this.commentBody = commentBody
    }

    fun setEditable(editable: Boolean){
        this.editable = editable
    }
}