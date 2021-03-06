package com.mobdeve.gonzales.lee.ong.artemis

import com.google.firebase.database.Exclude

/**
 * Class representing a comment and specifying the attributes associated with it.
 */
class Comment {
    /**
     * Unique identifier of the user who posted the comment.
     */
    private var userId: String? = null

    /**
     * Unique identifier of the post to which the comment is tied.
     */
    private var postId: String? = null

    /**
     * Unique identifier of the comment.
     */
    private var commentId: String? = null

    /**
     * URI of the profile picture of the user who posted the comment.
     */
    private var profilePicture: String? = null

    /**
     * Username of the user who posted the comment.
     */
    private var username: String? = null

    /**
     * Date when the comment was posted.
     */
    private var dateCommented: String? = null

    /**
     * Comment per se.
     */
    private var commentBody: String? = null

    /**
     * <code>true</code> if the comment can be edited by the user; <code>false</code>, otherwise
     */
    @get:Exclude private var editable: Boolean = false

    /**
     * Creates a comment.
     *
     * @constructor Creates a comment.
     */
    constructor() {
        /*
         * Intentionally left empty to prevent the app from crashing when performing Firebase
         * CRUD operations involving comments
         */
    }

    /**
     * Creates a comment given the unique identifier of the post to which the comment is tied,
     * the comment itself, and the unique identifier, profile picture, and username of the user
     * who posted it.
     *
     * @constructor Creates a comment given the unique identifier of the post to which the comment
     * is tied, the comment itself, and the unique identifier, profile picture, and username of the
     * user who posted it.
     * @param userId Unique identifier of the user who posted the comment.
     * @param postId Unique identifier of the post to which the comment is tied.
     * @param profilePicture URI of the profile picture of the user who posted the comment.
     * @param username Username of the user who posted the comment.
     * @param commentBody Comment per se.
     */
    constructor(userId: String, postId: String, profilePicture: String, username: String, commentBody: String) {
        this.userId = userId
        this.postId = postId
        this.profilePicture = profilePicture
        this.username = username

        this.dateCommented = CustomDate().toStringFull()
        this.commentBody = commentBody
        //this.editable = editable
    }

    /**
     * Returns the unique identifier of the user who posted the comment.
     *
     * @return Unique identifier of the user who posted the comment.
     */
    fun getUserId(): String? {
        return this.userId
    }

    /**
     * Returns the unique identifier of the post to which the comment is tied.
     *
     * @return Unique identifier of the post to which the comment is tied.
     */
    fun getPostId(): String? {
        return this.postId
    }

    /**
     * Returns the unique identifier of the comment.
     *
     * @return Unique identifier of the comment.
     */
    fun getCommentId(): String? {
        return this.commentId
    }

    /**
     * Returns the profile picture of the user who posted the comment.
     *
     * @return Profile picture of the user who posted the comment.
     */
    fun getProfilePicture(): String? {
        return this.profilePicture
    }

    /**
     * Returns the username of the user who posted the comment.
     *
     * @return Username of the user who posted the comment.
     */
    fun getUsername(): String? {
        return this.username
    }

    /**
     * Returns the date when the comment was posted.
     *
     * @return Date when the comment was posted.
     */
    fun getDateCommented(): String? {
        return this.dateCommented
    }

    /**
     * Returns the comment per se.
     *
     * @return Comment per se.
     */
    fun getCommentBody(): String? {
        return this.commentBody
    }

    /**
     * Returns <code>true</code> if the user is allowed to edit the comment; <code>false</code>,
     * otherwise.
     *
     * @return <code>true</code> if the user is allowed to edit the comment; <code>false</code>,
     * otherwise.
     */
    fun getEditable(): Boolean {
        return this.editable
    }

    /**
     * Sets the unique identifier of the user who posted the comment to the specified string.
     *
     * @param userId String to which the unique identifier of the user is to be set.
     */
    fun setUserId(userId: String?){
        this.userId = userId
    }

    /**
     * Sets the unique identifier of the post to which the comment is tied to the specified string.
     *
     * @param postId String to which the unique identifier of the post to which the comment is tied
     * is to be set.
     */
    fun setPostId(postId: String?){
        this.postId = postId
    }

    /**
     * Sets the unique identifier of the comment to the specified string.
     *
     * @param commentId String to which the unique identifier of the comment is to be set.
     */
    fun setCommentId(commentId: String?){
        this.commentId = commentId
    }

    /**
     * Sets the profile picture of the user who posted the comment to the photo specified
     * by the given URI.
     *
     * @param profPic URI of the photo to which the profile picture of the user who posted
     * the comment to the photo specified is to be set.
     */
    fun setProfilePicture(profPic: String?){
        this.profilePicture = profPic
    }

    /**
     * Sets the username of the user who posted the comment to the specified string.
     *
     * @param username String to which the username of the user who posted the comment is to be set.
     */
    fun setUsername(username: String?){
        this.username = username
    }

    /**
     * Sets the date when the comment was posted to the specified string.
     *
     * @param dateCommented String to which the date when the comment was posted is to be set.
     */
    fun setDateCommented(dateCommented: String?){
        this.dateCommented = dateCommented
    }

    /**
     * Sets the comment per se to the specified string.
     *
     * @param commentBody String to which the comment per se is to be set.
     */
    fun setCommentBody(commentBody: String?){
        this.commentBody = commentBody
    }

    /**
     * Allows the user to edit a comment posted by them but disallows them to edit a comment
     * created by another user.
     *
     * @param editable <code>true</code> if the user is allowed to edit a comment; <code>false</code>,
     * otherwise.
     */
    fun setEditable(editable: Boolean){
        this.editable = editable
    }
}