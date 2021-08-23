package com.mobdeve.gonzales.lee.ong.artemis

class Comment {
    private var profilePicture: Int
    private var username: String
    private var dateCommented: CustomDate
    private var commentBody: String

    constructor(profilePicture: Int, username: String, dateCommented: CustomDate, commentBody: String) {
        this.profilePicture = profilePicture
        this.username = username
        this.dateCommented = dateCommented
        this.commentBody = commentBody
    }

    fun getProfilePicture(): Int {
        return this.profilePicture
    }

    fun getUsername(): String {
        return this.username
    }

    fun getDateCommented(): CustomDate {
        return this.dateCommented
    }

    fun getCommentBody(): String {
        return this.commentBody
    }

    fun setProfilePicture(picture: Int) {
        this.profilePicture = profilePicture
    }

    fun setUsername(name: String) {
        this.username = name
    }

    fun setDateCommented(dateCommented: CustomDate) {
        this.dateCommented = dateCommented
    }

    fun setCommentBody(comment: String) {
        this.commentBody = comment
    }
}