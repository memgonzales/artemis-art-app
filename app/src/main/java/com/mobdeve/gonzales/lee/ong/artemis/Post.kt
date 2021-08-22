package com.mobdeve.gonzales.lee.ong.artemis

import com.mobdeve.s14.lee.hylene.androidchallenge1.CustomDate

class Post(

    var profilePicture: Int,
    val username: String,
    val post: Int,
    var title: String,
    var numUpvotes: Int,
    var numComments: Int,
    var datePosted: CustomDate,
    var type: String,
    var dimHeight: Int,
    var dimWidth: Int,
    var description: String,
    var tags: Array<String>,
    var bookmark: Boolean
)