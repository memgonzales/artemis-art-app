package com.mobdeve.gonzales.lee.ong.artemis

import com.google.firebase.database.Exclude

/**
 * Class representing a post and specifying the attributes associated with it.
 */
class Post {
    /**
     * Unique identifier of the user who posted the post.
     */
    private var userId: String? = null

    /**
     * Unique identifier of the post.
     */
    private var postId: String? = null

    /**
     * Profile picture of the user who posted the post.
     */
    private var profilePicture: String? = null

    /**
     * Username of the user who posted the post.
     */
    private var username: String? = null

    /**
     * Image of the post (i.e., the artwork itself).
     */
    private var postImg: String? = null

    /**
     * Title of the post.
     */
    private var title: String? = null

    /**
     * Date the post was posted.
     */
    private var datePosted: String? = null

    /**
     * Medium of the posted artwork.
     */
    private var medium: String? = null

    /**
     * Dimensions of the posted artwork.
     */
    private var dimensions: String? = null

    /**
     * Description of the post.
     */
    private var description: String? = null

    /**
     * List of tags of the post.
     */
    private var tags: ArrayList<String> = ArrayList()

    /**
     * <code>true</code> if the current user has bookmarked the post; <code>false</code>, otherwise.
     */
    @get:Exclude private var bookmark: Boolean = false

    /**
     * <code>true</code> if the current user has upvoted the post; <code>false</code>, otherwise.
     */
    @get:Exclude private var upvote: Boolean = false

    /**
     * <code>true</code> if the current user has highlighted the post; <code>false</code>, otherwise.
     */
    @get:Exclude private var highlight: Boolean = false

    /**
     * List of users that have upvoted the post.
     */
    private var upvoteUsers: HashMap<String, Any?> = HashMap()

    /**
     * List of users that have bookmarked the post.
     */
    private var bookmarkUsers: HashMap<String, Any?> = HashMap()

    /**
     * List of comments on the post.
     */
    private var comments: HashMap<String, Any?> = HashMap()

    /**
     * Unique identifier of the user who highlighted the post.
     */
    private var highlightUser : String? = null

    /**
     * Number of upvotes of the post.
     */
    private var numUpvotes: Int = 0

    /**
     * Number of comments of the post.
     */
    private var numComments: Int = 0

    /**
     * Creates a post.
     */
    constructor(){
        /*
         * Intentionally left empty to prevent the app from crashing when performing Firebase
         * CRUD operations involving posts
         */
    }

    /**
     * Creates a post given the unique identifier of the user who posted the post, the unique
     * identifier of the post itself, the title of the post, the artwork to be posted, the medium
     * of the artwork, the dimensions of the artwork, the description of the post, and the tags of
     * the post.
     *
     * @param userId Unique identifier of the user who posted the post.
     * @param postId Unique identifier of the post itself.
     * @param title Title of the post.
     * @param postImg Artwork to be posted.
     * @param medium Medium of the artwork.
     * @param dimensions Dimensions of the artwork.
     * @param description Description of the post.
     * @param tags Tags of the post.
     */
    constructor(userId: String, postId: String, title: String, postImg: String,
                medium: String, dimensions: String, description: String, tags: ArrayList<String>) {

        this.userId = userId
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
    }

    /**
     * Returns the unique identifier of the user who posted the post.
     *
     * @return Unique identifier of the user who posted the post.
     */
    fun getUserId(): String?{
        return this.userId
    }

    /**
     * Returns the unique identifier of the post.
     *
     * @return Unique identifier of the post.
     */
    fun getPostId(): String?{
        return this.postId
    }

    /**
     * Returns the profile picture of the user who posted the post.
     *
     * @return Profile picture of the user who posted the post.
     */
    fun getProfilePicture(): String? {
        return this.profilePicture
    }

    /**
     * Returns the username of the user who posted the post.
     *
     * @return Username of the user who posted the post.
     */
    fun getUsername(): String? {
        return this.username
    }

    /**
     * Returns the artwork associated with the post.
     *
     * @return Artwork associated with the post.
     */
    fun getPostImg(): String? {
        return this.postImg
    }

    /**
     * Returns the title of the post.
     *
     * @return Title of the post.
     */
    fun getTitle(): String? {
        return this.title
    }

    /**
     * Returns the date the post was posted.
     *
     * @return Date the post was posted.
     */
    fun getDatePosted(): String? {
        return this.datePosted
    }

    /**
     * Returns the medium of the artwork.
     *
     * @return Medium of the artwork.
     */
    fun getMedium(): String? {
        return this.medium
    }

    /**
     * Returns the dimensions of the artwork.
     *
     * @return Dimensions of the artwork.
     */
    fun getDimensions(): String? {
        return this.dimensions
    }

    /**
     * Returns the description of the post.
     *
     * @return Description of the post.
     */
    fun getDescription(): String? {
        return this.description
    }

    /**
     * Returns the tags of the post.
     *
     * @return Tags of the post.
     */
    fun getTags(): ArrayList<String>? {
        return this.tags
    }

    /**
     * Returns <code>true</code> if the user has bookmarked the post and <code>false</code> otherwise.
     *
     * @return <code>true</code> if the user has bookmarked the post; <code>false</code>, otherwise.
     */
    fun getBookmark(): Boolean {
        return this.bookmark
    }

    /**
     * Returns <code>true</code> if the user has upvoted the post and <code>false</code> otherwise.
     *
     * @return <code>true</code> if the user has upvoted the post; <code>false</code>, otherwise.
     */
    fun getUpvote(): Boolean {
        return this.upvote
    }

    /**
     * Returns <code>true</code> if the user has highlighted the post and <code>false</code> otherwise.
     *
     * @return <code>true</code> if the user has highlighted the post; <code>false</code>, otherwise.
     */
    fun getHighlight(): Boolean {
        return this.highlight
    }

    /**
     * Returns the list of users that have upvoted the post.
     *
     * @return List of users that have upvoted the post.
     */
    fun getUpvoteUsers(): HashMap<String, Any?>{
        return this.upvoteUsers
    }

    /**
     * Returns the list of users that have bookmarked the post.
     *
     * @return List of users that have bookmarked the post.
     */
    fun getBookmarkUsers(): HashMap<String, Any?>{
        return this.bookmarkUsers
    }


    /**
     * Returns the comments tied to the post.
     *
     * @return Commented tied to the post.
     */
    fun getComments(): HashMap<String, Any?> {
        return this.comments
    }

    /**
     * Returns the unique identifier of the user who highlighted the post, if any.
     *
     * @return Unique identifier of the user who highlighted the post, if any.
     */
    fun getHighlightUser(): String? {
        return this.highlightUser
    }

    /**
     * Returns the number of upvotes of the post.
     *
     * @return Number of upvotes of the post.
     */
    fun getNumUpvotes(): Int {
        return this.numUpvotes
    }

    /**
     * Returns the number of comments of the post.
     *
     * @return Number of comments of the post.
     */
    fun getNumComments(): Int {
        return this.numComments
    }

    /**
     * Sets the unique identifier of the user who posted the post to the specified string.
     *
     * @param userId String to which the unique identifier of the user is to be set.
     */
    fun setUserId(userId: String){
        this.userId = userId
    }

    /**
     * Sets the unique identifier of the post to the specified string.
     *
     * @param postId String to which the unique identifier of the post is to be set.
     */
    fun setPostId(postId: String?){
        this.postId = postId
    }

    /**
     * Sets the profile picture associated with the post to the specified string.
     *
     * @param picture String to which the profile picture associated with the post is to be set.
     */
    fun setProfilePicture(picture: String?) {
        this.profilePicture = picture
    }

    /**
     * Sets the username associated with the post to the specified string.
     *
     * @param name String to which the username associated with the post is to be set.
     */
    fun setUsername(name: String?) {
        this.username = name
    }

    /**
     * Sets the image associated with the post (i.e., the artwork) to the specified string.
     *
     * @param postImg String to which the image associated with the post is to be set.
     */
    fun setPostImg(postImg: String?) {
        this.postImg = postImg
    }

    /**
     * Sets the title of the post to the specified string.
     *
     * @param title String to which the title of the post is to be set.
     */
    fun setTitle(title: String?) {
        this.title = title
    }

    /**
     * Sets the date that the post was posted to the specified string.
     *
     * @param datePosted String to which the date that the post was posted is to be set.
     */
    fun setDatePosted(datePosted: String?) {
        this.datePosted = datePosted
    }

    /**
     * Sets the medium of the posted artwork to the specified string.
     *
     * @param medium String to which the medium of the artwork is to be set.
     */
    fun setMedium(medium: String?) {
        this.medium = medium
    }

    /**
     * Sets the dimensions of the posted artwork to the specified string.
     *
     * @param dimensions String to which the dimensions of the posted artwork is to be set.
     */
    fun setDimensions(dimensions: String?) {
        this.dimensions = dimensions
    }

    /**
     * Sets the description of the post to the specified string.
     *
     * @param description String to which the description of the post is to be set.
     */
    fun setDescription(description: String?) {
        this.description = description
    }

    /**
     * Sets the tags of the post to the specified list of strings.
     *
     * @param tags List of strings to which the tags of the post are to be set.
     */
    fun setTags(tags: ArrayList<String>) {
        this.tags = tags
    }

    /**
     * Sets the bookmark status of the post to the specified boolean value.
     *
     * @param bookmark Boolean value to which the bookmark status of the post is to be set.
     */
    fun setBookmark(bookmark: Boolean) {
        this.bookmark = bookmark
    }

    /**
     * Sets the upvote status of the post to the specified boolean value.
     *
     * @param upvote Boolean value to which the upvote status of the post is to be set.
     */
    fun setUpvote(upvote: Boolean) {
        this.upvote = upvote
    }

    /**
     * Sets the highlight status of the post to the specified boolean value.
     *
     * @param highlight Boolean value to which the highlight status of the post is to be set.
     */
    fun setHighlight(highlight: Boolean) {
        this.highlight = highlight
    }

    /**
     * Sets the list of users who upvoted the post to the specified hashmap.
     *
     * @param upvoteUsers Hashmap to which the list of users who upvoted the post is to be set.
     */
    fun setUpvoteUsers(upvoteUsers: HashMap<String, Any?>) {
        this.upvoteUsers = upvoteUsers
    }

    /**
     * Sets the list of users who bookmarked the post to the specified hashmap.
     *
     * @param bookmarkUsers Hashmap to which the list of users who bookmarked the post is to be set.
     */
    fun setBookmarkUsers(bookmarkUsers: HashMap<String, Any?>) {
        this.bookmarkUsers = bookmarkUsers
    }

    /**
     * Sets the list of comments tied to the post to the specified hashmap.
     *
     * @param comments Hashmap to which the list of comments tied to the post is to be set.
     */
    fun setComments(comments: HashMap<String, Any?>) {
        this.comments = comments
    }

    /**
     * Sets the unique identifier of the user who highlighted the post to the specified string.
     *
     * @param highlightUser String to which the unique identifier of the user who highlighted
     * the post is to be set.
     */
    fun setHighlightUser(highlightUser: String?) {
        this.highlightUser = highlightUser
    }

    /**
     * Sets the number of upvotes of the post to the specified value.
     *
     * @param numUpvotes Value to which the number of upvotes of the post is to be set.
     */
    fun setNumUpvotes(numUpvotes: Int) {
        this.numUpvotes = numUpvotes
    }

    /**
     * Sets the number of comments of the post to the specified value.
     *
     * @param numComments Value to which the number of comments of the post is to be set.
     */
    fun setNumComments(numComments: Int) {
        this.numComments = numComments
    }
}