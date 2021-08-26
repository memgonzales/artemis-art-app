package com.mobdeve.gonzales.lee.ong.artemis

class Comment {
    private var postId: String
    private var profilePicture: Int
    private var username: String
    private var dateCommented: String
    private var commentBody: String
    private var editable: Boolean

    constructor(postId: String, profilePicture: Int, username: String, commentBody: String,
                editable: Boolean) {
        this.postId = postId
        this.profilePicture = profilePicture
        this.username = username

        this.dateCommented = CustomDate().toStringFull()
        this.commentBody = commentBody
        this.editable = editable
    }

    fun getPostId(): String {
        return this.postId
    }

    fun getProfilePicture(): Int {
        return this.profilePicture
    }

    fun getUsername(): String {
        return this.username
    }

    fun getDateCommented(): String {
        return this.dateCommented
    }

    fun getCommentBody(): String {
        return this.commentBody
    }

    fun getEditable(): Boolean {
        return this.editable
    }
}