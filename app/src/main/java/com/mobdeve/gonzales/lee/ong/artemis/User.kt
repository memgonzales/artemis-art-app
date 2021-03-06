package com.mobdeve.gonzales.lee.ong.artemis

/**
 * Class representing a user and specifying the attributes associated with it.
 */
class User {
    /**
     * Unique identifier of the user.
     */
    private var userId: String? = null
    /**
     * Username of the user.
     */
    private var username: String? = null

    /**
     * Email address of the user.
     */
    private var email: String? = null

    /**
     * Password of the user.
     */
    private var password: String? = null

    /**
     * Profile picture of the user.
     */
    private var userImg: String? = null

    /**
     * Short bio of the user.
     */
    private var bio: String? = null

    /**
     * Identifiers of the posts bookmarked by the user.
     */
    private var bookmarks: HashMap<String, Any?> = HashMap()

    /**
     * Identifiers of the posts created by the user.
     */
    private var userPosts: HashMap<String, Any?> = HashMap()

    /**
     * Identifiers of the highlights posted by the user.
     */
    private var highlights: HashMap<String, Any?> = HashMap()

    /**
     * Identifiers of the posts upvoted by the user.
     */
    private var upvotedPosts: HashMap<String, Any?> = HashMap()

    /**
     * Identifiers of the users followed by the user.
     */
    private var usersFollowed: HashMap<String, Any?> = HashMap()

    /**
     * Identifiers of the comments written by the user.
     */
    private var userComments: HashMap<String, Any?> = HashMap()

    /**
     * Creates a user.
     *
     * @constructor Creates a user.
     */
    constructor(){
        /*
         * Intentionally left empty to prevent the app from crashing when performing Firebase
         * CRUD operations involving comments
         */
    }

    /**
     * Creates a user given a username, email address, and password.
     *
     * @constructor Creates a user given a username, email address, and password.
     * @param username Username of the user.
     * @param email Email address of the user.
     * @param password Password of the user.
     */
    constructor(username: String, email: String, password: String){
         this.userId = ""
         this.username = username
         this.email = email
         this.password = password
         this.userImg = "https://firebasestorage.googleapis.com/v0/b/artemis-77e4e.appspot.com/o/chibi_artemis_hd.png?alt=media&token=53dfd292-76a2-4abb-849c-c5fcbb7932d2"
         this.bio = ""
    }

    /**
     * Creates a user given a username, email address, password, profile picture, and short bio.
     *
     * @constructor Creates a user given a username, email address, password, profile picture, and short bio.
     * @param username Username of the user.
     * @param email Email address of the user.
     * @param password Password of the user.
     * @param userImg Profile picture of the user.
     * @param bio Short bio of the user.
     */
    constructor(username: String, email: String, password: String, userImg: String, bio: String){
        this.userId = ""
        this.username = username
        this.email = email
        this.password = password
        this.userImg = userImg
        this.bio = bio
    }

    /**
     * Returns the unique identifier of the user.
     *
     * @return Unique identifier of the user.
     */
    fun getUserId(): String? {
        return this.userId
    }

    /**
     * Returns the username of the user.
     *
     * @return Username of the user.
     */
    fun getUsername(): String? {
        return this.username
    }

    /**
     * Returns the email address of the user.
     *
     * @return Email address of the user.
     */
    fun getEmail(): String? {
        return this.email
    }

    /**
     * Returns the password of the user.
     *
     * @return Password of the user.
     */
    fun getPassword(): String? {
        return this.password
    }

    /**
     * Returns the profile picture of the user.
     *
     * @return Profile picture of the user.
     */
    fun getUserImg(): String? {
        return this.userImg
    }

    /**
     * Returns the short bio of the user.
     *
     * @return Short bio of the user.
     */
    fun getBio(): String? {
        return this.bio
    }

    /**
     * Returns the identifiers of the posts bookmarked by the user.
     *
     * @return Identifiers of the posts bookmarked by the user.
     */
    fun getBookmarks(): HashMap<String, Any?>{
        return this.bookmarks
    }

    /**
     * Returns the identifiers of the posts created by the user.
     *
     * @return Identifiers of the posts created by the user.
     */
    fun getUserPosts(): HashMap<String, Any?>{
        return this.userPosts
    }

    /**
     * Returns the identifiers of the highlights posted by the user.
     *
     * @return Identifiers of the highlights posted by the user.
     */
    fun getHighlights(): HashMap<String, Any?>{
        return this.highlights
    }

    /**
     * Returns the identifiers of the posts upvoted by the user.
     *
     * @return Identifiers of the posts upvoted by the user.
     */
    fun getUpvotedPosts(): HashMap<String, Any?>{
        return this.upvotedPosts
    }

    /**
     * Returns the identifiers of the users followed by the user.
     *
     * @return Identifiers of the users followed by the user.
     */
    fun getUsersFollowed(): HashMap<String, Any?>{
        return this.usersFollowed
    }

    /**
     * Returns the identifiers of the comments written by the user.
     *
     * @return Identifiers of the comments written by the user.
     */
    fun getUserComments(): HashMap<String, Any?>{
        return this.userComments
    }

    /**
     * Sets the unique identifier of the user to the specified string.
     *
     * @param userId String to which the unique identifier of the user is to be set.
     */
    fun setUserId(userId: String?){
        this.userId = userId
    }

    /**
     * Sets the username of the user to the specified string.
     *
     * @param username String to which the username is to be set.
     */
    fun setUsername(username: String?) {
        this.username = username
    }

    /**
     * Sets the email address of the user to the specified string.
     *
     * @param email String to which the email address is to be set.
     */
    fun setEmail(email: String?) {
        this.email = email
    }

    /**
     * Sets the password of the user to the specified string.
     *
     * @param password String to which the password is to be set.
     */
    fun setPassword(password: String?) {
        this.password = password
    }

    /**
     * Sets the profile picture of the user to the photo specified by the given URI.
     *
     * @param userImg URI of the photo to which the profile picture of the user is to be set.
     *
     */
    fun setUserImg(userImg: String?) {
        this.userImg = userImg
    }

    /**
     * Sets the short bio of the user to the specified string.
     *
     * @param bio String to which the short bio of the user is to be set.
     */
    fun setBio(bio: String?) {
        this.bio = bio
    }

    /**
     * Sets the identifiers of the posts bookmarked by the user to the specified strings.
     *
     * @param bookmarks List containing strings to which the identifiers of the posts bookmarked
     * by the user are to be set.
     */
    fun setBookmarks(bookmarks: HashMap<String, Any?>){
        this.bookmarks = bookmarks
    }

    /**
     * Sets the identifiers of the posts created by the user to the specified strings.
     *
     * @param userPosts List containing strings to which the identifiers of the posts created
     * by the user are to be set.
     */
    fun setUserPosts(userPosts: HashMap<String, Any?>){
        this.userPosts = userPosts
    }

    /**
     * Sets the identifiers of the posts highlighted by the user to the specified strings.
     *
     * @param highlights List containing strings to which the identifiers of the posts highlighted
     * by the user are to be set.
     */
    fun setHighlights(highlights: HashMap<String, Any?>){
        this.highlights = highlights
    }

    /**
     * Sets the identifiers of the posts upvoted by the user to the specified strings.
     *
     * @param upvotedPosts List containing strings to which the identifiers of the posts upvoted
     * by the user are to be set.
     */
    fun setUpvotedPosts(upvotedPosts: HashMap<String, Any?>){
        this.upvotedPosts = upvotedPosts
    }

    /**
     * Sets the identifiers of the users followed by the user to the specified strings.
     *
     * @param usersFollowed List containing strings to which the identifiers of the users followed
     * by the user are to be set.
     */
    fun setUsersFollowed(usersFollowed: HashMap<String, Any?>){
        this.usersFollowed = usersFollowed
    }

    /**
     * Sets the identifiers of the comments written by the user to the specified strings.
     *
     * @param userComments List containing strings to which the identifiers of the comments written
     * by the user are to be set.
     */
    fun setUserComments(userComments: HashMap<String, Any?>) {
        this.userComments = userComments
    }
}



