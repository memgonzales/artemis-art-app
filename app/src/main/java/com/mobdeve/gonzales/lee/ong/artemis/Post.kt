package com.mobdeve.gonzales.lee.ong.artemis

import com.mobdeve.s14.lee.hylene.androidchallenge1.CustomDate

class Post(
    val profilePicture: Int,
    val username: String,
    val post: Int,
    val title: String,
    val numUpvotes: Int,
    val numComments: Int,
    val datePosted: CustomDate,
    val type: String,
    val dimHeight: Int,
    val dimWidth: Int,
    val description: String,
    val tags: Array<String>

)