package com.mobdeve.gonzales.lee.ong.artemis

class Post {
    private lateinit var postId: String
    private lateinit var profilePicture: String
    private lateinit var username: String

    private lateinit var postImg: String
    private lateinit var title: String
    private lateinit var datePosted: String

    private lateinit var medium: String
    private lateinit var dimensions: String
    private lateinit var description: String
    private lateinit var tags: ArrayList<String>

    private var bookmark: Boolean
    private var upvote: Boolean
    private var highlight: Boolean

    private lateinit var upvoteUsers: HashMap<String, Any?>
    private lateinit var bookmarkUsers: HashMap<String, Any?>
    private lateinit var comments: HashMap<String, Any?>

    private var numUpvotes: Int
    private var numComments: Int

    constructor(){
        bookmark = false
        upvote = false
        highlight = false

        numUpvotes = 0
        numComments = 0
    }

    constructor(postId: String, title: String, postImg: String,
                medium: String, dimensions: String, description: String, tags: ArrayList<String>) {

        this.postId = postId
        this.profilePicture = "https://firebasestorage.googleapis.com/v0/b/artemis-77e4e.appspot.com/o/chibi_artemis_hd.png?alt=media&token=53dfd292-76a2-4abb-849c-c5fcbb7932d2"
        this.username = ""

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

        this.upvoteUsers = HashMap<String, Any?>()
        this.bookmarkUsers = HashMap<String, Any?>()
        this.comments = HashMap<String, Any?>()

        this.numUpvotes = 0
        this.numComments = 0
    }

    /*
    constructor(postId: String, profilePicture: String, username: String, title: String, postImg: String,
                medium: String, dimensions: String, description: String, tags: ArrayList<String>) {

        this.postId = postId
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

        this.upvoteUsers = HashMap<String, Any?>()
        this.comments = HashMap<String, Any?>()

        this.numUpvotes = 0
        this.numComments = 0
    }

     */

    /*
    constructor(profilePicture: String, username: String, postImg: String, title: String, numUpvotes: Int,
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


     */
    fun getPostId(): String{
        return this.postId
    }

    fun getProfilePicture(): String {
        return this.profilePicture
    }

    fun getUsername(): String {
        return this.username
    }

    fun getPostImg(): String {
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

    fun getUpvoteUsers(): HashMap<String, Any?>{
        return this.upvoteUsers
    }

    fun getBookmarkUsers(): HashMap<String, Any?>{
        return this.bookmarkUsers
    }

    fun getComments(): HashMap<String, Any?> {
        return this.comments
    }

    fun getNumUpvotes(): Int {
        return this.numUpvotes
    }

    fun getNumComments(): Int {
        return this.numComments
    }





    fun setPostId(postId: String){
        this.postId = postId
    }

    fun setProfilePicture(picture: String) {
        this.profilePicture = picture
    }

    fun setUsername(name: String) {
        this.username = name
    }

    fun setPostImg(postImg: String) {
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

    fun setUpvoteUsers(upvoteUsers: HashMap<String, Any?>) {
        this.upvoteUsers = upvoteUsers
    }

    fun setBookmarkUsers(bookmarkUsers: HashMap<String, Any?>) {
        this.bookmarkUsers = bookmarkUsers
    }

    fun setComments(comments: HashMap<String, Any?>) {
        this.comments = comments
    }

    fun setNumUpvotes(numUpvotes: Int) {
        this.numUpvotes = numUpvotes
    }

    fun setNumComments(numComments: Int) {
        this.numComments = numComments
    }
}