package com.mobdeve.gonzales.lee.ong.artemis

class User {
    private var username: String
    private var email: String
    private var password: String
    private var userImg: Int
    private var bio: String

    private var bookmarks: ArrayList<String>
    private var userPosts: ArrayList<String>
    private var highlights: ArrayList<String>
    private var upvotedPosts: ArrayList<String>
    private var usersFollowed: ArrayList<String>
    private var userComments: ArrayList<String>

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
}



