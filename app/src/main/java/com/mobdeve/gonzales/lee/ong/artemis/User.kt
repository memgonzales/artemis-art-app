package com.mobdeve.gonzales.lee.ong.artemis

class User {
    private var username: String
    private var email: String
    private var password: String
    private var userImg: Int
    private var bio: String

    private var bookmarks: ArrayList<Int>
    private var userPosts: ArrayList<Int>
    private var highlights: ArrayList<Int>
    private var upvotedPosts: ArrayList<Int>
    private var usersFollowed: ArrayList<Int>
    private var comments: ArrayList<Int>

    constructor(username: String, email: String, password: String){
        this.username = username
        this.email = email
        this.password = password
        this.userImg = R.drawable.chibi_circle
        this.bio = ""

        this.bookmarks = ArrayList<Int>()
        this.userPosts = ArrayList<Int>()
        this.highlights = ArrayList<Int>()
        this.upvotedPosts = ArrayList<Int>()
        this.usersFollowed = ArrayList<Int>()
        this.comments = ArrayList<Int>()
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

    fun getBookmarks(): ArrayList<Int>{
        return this.bookmarks
    }

    fun getUserPosts(): ArrayList<Int>{
        return this.userPosts
    }

    fun getHighlights(): ArrayList<Int>{
        return this.highlights
    }

    fun getUpvotedPosts(): ArrayList<Int>{
        return this.upvotedPosts
    }

    fun getUsersFolloweded(): ArrayList<Int>{
        return this.usersFollowed
    }
}



