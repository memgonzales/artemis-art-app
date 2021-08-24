package com.mobdeve.gonzales.lee.ong.artemis

class Post {
    private var profilePicture: Int
    private var username: String
    private var post: Int
    private var title: String
    private var numUpvotes: Int
    private var numComments: Int
    private var datePosted: CustomDate
    private var medium: String
    private var dimHeight: Int
    private var dimWidth: Int
    private var description: String
    private var tags: Array<String>
    private var bookmark: Boolean
    private var upvote: Boolean
    private var highlight: Boolean

    constructor(profilePicture: Int, username: String, post: Int, title: String, numUpvotes: Int,
                numComments: Int, datePosted: CustomDate, medium: String, dimHeight: Int, dimWidth: Int,
                description: String, tags: Array<String>, bookmark: Boolean, upvote: Boolean, highlight: Boolean) {
        this.profilePicture = profilePicture
        this.username = username
        this.post = post
        this.title = title
        this.numUpvotes = numUpvotes
        this.numComments = numComments
        this.datePosted = datePosted
        this.medium = medium
        this.dimHeight = dimHeight
        this.dimWidth = dimWidth
        this.description = description
        this.tags = tags
        this.bookmark = bookmark
        this.upvote = upvote
        this.highlight = highlight
    }

    fun getProfilePicture(): Int {
        return this.profilePicture
    }

    fun getUsername(): String {
        return this.username
    }

    fun getPost(): Int {
        return this.post
    }

    fun getTitle(): String {
        return this.title
    }

    fun getNumUpvotes(): Int {
        return this.numUpvotes
    }

    fun getNumComments(): Int {
        return this.numComments
    }

    fun getDatePosted(): CustomDate {
        return this.datePosted
    }

    fun getMedium(): String {
        return this.medium
    }

    fun getDimHeight(): Int {
        return this.dimHeight
    }

    fun getDimWidth(): Int {
        return this.dimWidth
    }

    fun getDescription(): String {
        return this.description
    }

    fun getTags(): Array<String> {
        return this.tags
    }

    fun getBookmark(): Boolean {
        return this.bookmark
    }

    fun getUpvote(): Boolean {
        return this.upvote
    }

    fun getHighlight(): Boolean {
        return this.highlight
    }


    fun setProfilePicture(picture: Int) {
        this.profilePicture = profilePicture
    }

    fun setUsername(name: String) {
        this.username = name
    }

    fun setPost(post: Int) {
        this.post = post
    }

    fun setTitle(title: String) {
        this.title = title
    }

    fun setNumUpvotes(numUpvotes: Int) {
        this.numUpvotes = numUpvotes
    }

    fun setNumComments(numComments: Int) {
        this.numComments = numComments
    }

    fun setDatePosted(datePosted: CustomDate) {
        this.datePosted = datePosted
    }

    fun setMedium(medium: String) {
        this.medium = medium
    }

    fun setDimHeight(height: Int) {
        this.dimHeight = height
    }

    fun setDimWidth(width: Int) {
        this.dimWidth = width
    }

    fun setDescription(description: String) {
        this.description = description
    }

    fun setTags(tags: Array<String>) {
        this.tags = tags
    }

    fun setBookmark(bookmark: Boolean) {
        this.bookmark = bookmark
    }

    fun setUpvote(upvote: Boolean) {
        this.upvote = upvote
    }

    fun setHighlight(highlight: Boolean) {
        this.highlight = highlight
    }
}