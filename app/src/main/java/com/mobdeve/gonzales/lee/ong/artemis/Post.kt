package com.mobdeve.gonzales.lee.ong.artemis

class Post {
    private lateinit var postId: String
    private var profilePicture: Int
    private var username: String

    private var postImg: Int
    private var title: String
    private var datePosted: String

    private var medium: String
    private var dimensions: String
    private var description: String
    private var tags: ArrayList<String>

    private var bookmark: Boolean
    private var upvote: Boolean
    private var highlight: Boolean

    private var upvoteUsers: ArrayList<String>
    private var comments: ArrayList<String>

    private var numUpvotes: Int
    private var numComments: Int

    constructor(profilePicture: Int, username: String, title: String, postImg: Int,
                medium: String, dimensions: String, description: String, tags: ArrayList<String>) {

        this.profilePicture = profilePicture
        this.username = username

        this.postImg = postImg
        this.title = title
        this.datePosted = CustomDate().toStringFull()

        this.medium = medium
        this.dimensions = dimensions
        this.description = description
        this.tags = tags

        this.bookmark = false
        this.upvote = false
        this.highlight = false

        this.upvoteUsers = ArrayList<String>()
        this.comments = ArrayList<String>()

        this.numUpvotes = 0
        this.numComments = 0
    }

    constructor(profilePicture: Int, username: String, postImg: Int, title: String, numUpvotes: Int,
                numComments: Int, datePosted: String, medium: String, dimensions: String,
                description: String, tags: ArrayList<String>, bookmark: Boolean, upvote: Boolean, highlight: Boolean) {

        this.profilePicture = profilePicture
        this.username = username
        this.postImg = postImg
        this.title = title
        this.numUpvotes = numUpvotes
        this.numComments = numComments
        this.datePosted = datePosted
        this.medium = medium
        this.dimensions = dimensions
        this.description = description
        this.tags = tags
        this.bookmark = bookmark
        this.upvote = upvote
        this.highlight = highlight

        this.upvoteUsers = ArrayList<String>()
        this.comments = ArrayList<String>()
    }

    fun getPostId(): String{
        return this.postId
    }

    fun getProfilePicture(): Int {
        return this.profilePicture
    }

    fun getUsername(): String {
        return this.username
    }

    fun getPostImg(): Int {
        return this.postImg
    }

    fun getTitle(): String {
        return this.title
    }

    fun getDatePosted(): String {
        return this.datePosted
    }

    fun getMedium(): String {
        return this.medium
    }

    fun getDimensions(): String {
        return this.dimensions
    }

    fun getDescription(): String {
        return this.description
    }

    fun getTags(): ArrayList<String> {
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

    fun getUpvoteUsers(): ArrayList<String>{
        return this.upvoteUsers
    }

    fun getComments(): ArrayList<String> {
        return this.comments
    }

    fun getNumUpvotes(): Int {
        return this.numUpvotes
    }

    fun getNumComments(): Int {
        return this.numComments
    }


    fun setPostId(id: String){
        this.postId = id
    }

    fun setProfilePicture(picture: Int) {
        this.profilePicture = profilePicture
    }

    fun setUsername(name: String) {
        this.username = name
    }

    fun setPostImg(postImg: Int) {
        this.postImg = postImg
    }

    fun setTitle(title: String) {
        this.title = title
    }

    fun setDatePosted(datePosted: String) {
        this.datePosted = datePosted
    }

    fun setMedium(medium: String) {
        this.medium = medium
    }

    fun setDimensions(dimensions: String) {
        this.dimensions = dimensions
    }

    fun setDescription(description: String) {
        this.description = description
    }

    fun setTags(tags: ArrayList<String>) {
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

    fun setUpvoteUsers(upvoteUsers: ArrayList<String>) {
        this.upvoteUsers = upvoteUsers
    }

    fun setComments(comments: ArrayList<String>  ) {
        this.comments = comments
    }

    fun setNumUpvotes(numUpvotes: Int) {
        this.numUpvotes = numUpvotes
    }

    fun setNumComments(numComments: Int) {
        this.numComments = numComments
    }
}