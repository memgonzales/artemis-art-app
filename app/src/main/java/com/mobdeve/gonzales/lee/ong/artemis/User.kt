package com.mobdeve.gonzales.lee.ong.artemis

class User {
    private lateinit var userId: String
    private var username: String
    private var email: String
    private var password: String
    private var userImg: Int
    private var bio: String

    private lateinit var bookmarks: ArrayList<String>
    private lateinit var userPosts: ArrayList<String>
    private lateinit var highlights: ArrayList<String>
    private lateinit var upvotedPosts: ArrayList<String>
    private lateinit var usersFollowed: ArrayList<String>
    private lateinit var userComments: ArrayList<String>

    constructor(username: String, email: String, password: String){
        this.username = username
        this.email = email
        this.password = password
        this.userImg = R.drawable.chibi_artemis_hd
        this.bio = ""

        /*
        this.bookmarks = ArrayList<String>()
        this.userPosts = ArrayList<String>()
        this.highlights = ArrayList<String>()
        this.upvotedPosts = ArrayList<String>()
        this.usersFollowed = ArrayList<String>()
        this.userComments = ArrayList<String>()

         */
    }

    constructor(username: String, email: String, password: String, userImg: Int, bio: String){
        this.username = username
        this.email = email
        this.password = password
        this.userImg = userImg
        this.bio = bio

        /*
        this.bookmarks = ArrayList<String>()
        this.userPosts = ArrayList<String>()
        this.highlights = ArrayList<String>()
        this.upvotedPosts = ArrayList<String>()
        this.usersFollowed = ArrayList<String>()
        this.userComments = ArrayList<String>()

         */
    }

    fun getUserId(): String{
        return this.userId
    }

    fun getUsername(): String {
        return this.username
    }

    fun getEmail(): String {
        return this.email
    }

    fun getPassword(): String {
        return this.password
    }

    fun getUserImg(): Int {
        return this.userImg
    }

    fun getBio(): String {
        return this.bio
    }

    fun getBookmarks(): ArrayList<String>{
        return this.bookmarks
    }

    fun getUserPosts(): ArrayList<String>{
        return this.userPosts
    }

    fun getHighlights(): ArrayList<String>{
        return this.highlights
    }

    fun getUpvotedPosts(): ArrayList<String>{
        return this.upvotedPosts
    }

    fun getUsersFollowed(): ArrayList<String>{
        return this.usersFollowed
    }

    fun getUserComments(): ArrayList<String>{
        return this.userComments
    }





    fun setUserId(userId: String){
        this.userId = userId
    }

    fun setUsername(username: String) {
        this.username = username
    }

    fun setEmail(email: String) {
        this.email = email
    }

    fun setPassword(password: String) {
        this.password = password
    }

    fun setUserImg(userImg: Int) {
        this.userImg = userImg
    }

    fun setBio(bio: String) {
        this.bio = bio
    }

    fun setBookmarks(bookmarks: ArrayList<String>){
        this.bookmarks = bookmarks
    }

    fun setUserPosts(userPosts: ArrayList<String>){
        this.userPosts = userPosts
    }

    fun setHighlights(highlights: ArrayList<String>){
        this.highlights = highlights
    }

    fun setUpvotedPosts(upvotedPosts: ArrayList<String>){
        this.upvotedPosts = upvotedPosts
    }

    fun setUsersFollowed(usersFollowed: ArrayList<String>){
        this.usersFollowed = usersFollowed
    }

    fun setUserComments(userComments: ArrayList<String>) {
        this.userComments = userComments
    }

}



