package com.mobdeve.gonzales.lee.ong.artemis

/**
 * Class representing a user and specifying the attributes associated with it.
 */
class User {

    /**
     * Username of the user.
     */
    private var username: String

    /**
     * Email address of the user.
     */
    private var email: String

    /**
     * Password of the user.
     */
    private var password: String

    /**
     * Profile picture of the user.
     */
    private var userImg: Int

    /**
     * Short bio of the user.
     */
    private var bio: String

    /**
     * Identifiers of the posts bookmarked by the user.
     */
    private var bookmarks: ArrayList<String>

    /**
     * Identifiers of the posts created by the user.
     */
    private var userPosts: ArrayList<String>

    /**
     * Identifiers of the highlights posted by the user.
     */
    private var highlights: ArrayList<String>

    /**
     * Identifiers of the posts upvoted by the user.
     */
    private var upvotedPosts: ArrayList<String>

    /**
     * Identifiers of the users followed by the user.
     */
    private var usersFollowed: ArrayList<String>

    /**
     * Identifiers of the comments written by the user.
     */
    private var userComments: ArrayList<String>

    /**
     * Creates a user given a username, email address, and password.
     *
     * @param username username of the user
     * @param email email address of the user
     * @param password password of the user
     */
    constructor(username: String, email: String, password: String){
        this.username = username
        this.email = email
        this.password = password
        this.userImg = R.drawable.chibi_artemis_hd
        this.bio = ""

        this.bookmarks = ArrayList<String>()
        this.userPosts = ArrayList<String>()
        this.highlights = ArrayList<String>()
        this.upvotedPosts = ArrayList<String>()
        this.usersFollowed = ArrayList<String>()
        this.userComments = ArrayList<String>()

    }

    /**
     * Creates a user given a username, email address, password, profile picture, and short bio.
     *
     * @param username username of the user
     * @param email email address of the user
     * @param password password of the user
     * @param userImg profile picture of the user
     * @param bio short bio of the user
     */
    constructor(username: String, email: String, password: String, userImg: Int, bio: String){
        this.username = username
        this.email = email
        this.password = password
        this.userImg = userImg
        this.bio = bio

        this.bookmarks = ArrayList<String>()
        this.userPosts = ArrayList<String>()
        this.highlights = ArrayList<String>()
        this.upvotedPosts = ArrayList<String>()
        this.usersFollowed = ArrayList<String>()
        this.userComments = ArrayList<String>()

    }


    /**
     * Returns the username of the user.
     *
     * @return username of the user
     */
    fun getUsername(): String {
        return this.username
    }

    /**
     * Returns the email address of the user.
     *
     * @return email address of the user
     */
    fun getEmail(): String {
        return this.email
    }

    /**
     * Returns the password of the user.
     *
     * @return password of the user
     */
    fun getPassword(): String {
        return this.password
    }

    /**
     * Returns the profile picture of the user.
     *
     * @return profile picture of the user
     */
    fun getUserImg(): Int {
        return this.userImg
    }

    /**
     * Returns the short bio of the user.
     *
     * @return short bio of the user
     */
    fun getBio(): String {
        return this.bio
    }

    /**
     * Returns the identifiers of the posts bookmarked by the user.
     *
     * @return identifiers of the posts bookmarked by the user
     */
    fun getBookmarks(): ArrayList<String>{
        return this.bookmarks
    }

    /**
     * Returns the identifiers of the posts created by the user.
     *
     * @return identifiers of the posts created by the user
     */
    fun getUserPosts(): ArrayList<String>{
        return this.userPosts
    }

    /**
     * Returns the identifiers of the highlights posted by the user.
     *
     * @return identifiers of the highlights posted by the user
     */
    fun getHighlights(): ArrayList<String>{
        return this.highlights
    }

    /**
     * Returns the identifiers of the posts upvoted by the user.
     *
     * @return identifiers of the posts upvoted by the user
     */
    fun getUpvotedPosts(): ArrayList<String>{
        return this.upvotedPosts
    }

    /**
     * Returns the identifiers of the users followed by the user.
     *
     * @return identifiers of the users followed by the user
     */
    fun getUsersFollowed(): ArrayList<String>{
        return this.usersFollowed
    }

    /**
     * Returns the identifiers of the comments written by the user.
     *
     * @return identifiers of the comments written by the user
     */
    fun getUserComments(): ArrayList<String>{
        return this.userComments
    }

    /**
     * Sets the username of the user to the specified string.
     *
     * @param username string to which the username is to be set
     */
    fun setUsername(username: String) {
        this.username = username
    }

    /**
     * Sets the email address of the user to the specified string.
     *
     * @param email string to which the email address is to be set
     */
    fun setEmail(email: String) {
        this.email = email
    }

    /**
     * Sets the password of the user to the specified string.
     *
     * @param password string to which the password is to be set
     */
    fun setPassword(password: String) {
        this.password = password
    }

    /**
     * Sets the profile picture of the user to the specified image URI.
     *
     * @param userImg image URI to which the profile picture of the user is to be set
     *
     */
    fun setUserImg(userImg: Int) {
        this.userImg = userImg
    }

    /**
     * Sets the short bio of the user to the specified string.
     *
     * @param bio string to which the short bio of the user is to be set
     */
    fun setBio(bio: String) {
        this.bio = bio
    }

    /**
     * Sets the identifiers of the posts bookmarked by the user to the specified strings.
     *
     * @param bookmarks list containing strings to which the identifiers of the posts bookmarked
     * by the user are to be set
     */
    fun setBookmarks(bookmarks: ArrayList<String>){
        this.bookmarks = bookmarks
    }

    /**
     * Sets the identifiers of the posts created by the user to the specified strings.
     *
     * @param userPosts list containing strings to which the identifiers of the posts created
     * by the user are to be set
     */
    fun setUserPosts(userPosts: ArrayList<String>){
        this.userPosts = userPosts
    }

    /**
     * Sets the identifiers of the posts highlighted by the user to the specified strings.
     *
     * @param highlights list containing strings to which the identifiers of the posts highlighted
     * by the user are to be set
     */
    fun setHighlights(highlights: ArrayList<String>){
        this.highlights = highlights
    }

    /**
     * Sets the identifiers of the posts upvoted by the user to the specified strings.
     *
     * @param upvotedPosts list containing strings to which the identifiers of the posts upvoted
     * by the user are to be set
     */
    fun setUpvotedPosts(upvotedPosts: ArrayList<String>){
        this.upvotedPosts = upvotedPosts
    }

    /**
     * Sets the identifiers of the users followed by the user to the specified strings.
     *
     * @param usersFollowed list containing strings to which the identifiers of the users followed
     * by the user are to be set
     */
    fun setUsersFollowed(usersFollowed: ArrayList<String>){
        this.usersFollowed = usersFollowed
    }

    /**
     * Sets the identifiers of the comments written by the user to the specified strings.
     *
     * @param userComments list containing strings to which the identifiers of the comments written
     * by the user are to be set
     */
    fun setUserComments(userComments: ArrayList<String>) {
        this.userComments = userComments
    }
}



