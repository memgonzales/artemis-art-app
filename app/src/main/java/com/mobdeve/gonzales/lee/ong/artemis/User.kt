package com.mobdeve.gonzales.lee.ong.artemis

class User {
    private var username: String
    private var email: String
    private var password: String
    private var userImg: Int
    private var desc: String

    constructor(username: String, email: String, password: String){
        this.username = username
        this.email = email
        this.password = password
        this.userImg = R.drawable.chibi_circle
        this.desc = ""
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

    fun getDesc(): String {
        return this.desc
    }
}



