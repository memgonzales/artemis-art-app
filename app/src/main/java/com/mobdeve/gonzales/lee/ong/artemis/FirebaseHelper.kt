package com.mobdeve.gonzales.lee.ong.artemis

import android.content.Context
import android.content.Intent
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class FirebaseHelper {

    private lateinit var context: Context

    private lateinit var mAuth: FirebaseAuth
    private lateinit var db: DatabaseReference

    private lateinit var user: FirebaseUser
    private lateinit var userId: String

    constructor(context: Context){
        this.mAuth = Firebase.auth
        this.db = Firebase.database.reference

        if (this.mAuth.currentUser != null){
            this.user = this.mAuth.currentUser!!
            this.userId = this.user.uid
        }

        else{
            val intent = Intent(context, BrokenLinkActivity::class.java)
            context.startActivity(intent)
        }

        this.context = context
    }

    constructor(context: Context, postId: String?, userIdPost: String?){
        this.mAuth = Firebase.auth
        this.db = Firebase.database.reference

        if(postId == null || userIdPost == null){
            val intent = Intent(context, BrokenLinkActivity::class.java)
            context.startActivity(intent)
        }

        if (this.mAuth.currentUser != null){
            this.user = this.mAuth.currentUser!!
            this.userId = this.user.uid
        }

        else{
            val intent = Intent(context, BrokenLinkActivity::class.java)
            context.startActivity(intent)
        }

        this.context = context
    }

    fun updateUpvoteDB(userVal: String?, postKey: String, postVal: String?, numUpvotes: Int){
        val updates = hashMapOf<String, Any?>(
            "/${Keys.KEY_DB_POSTS.name}/$postKey/${Keys.upvoteUsers.name}/$userId" to userVal,
            "/${Keys.KEY_DB_POSTS.name}/$postKey/${Keys.numUpvotes.name}" to numUpvotes,
            "/${Keys.KEY_DB_USERS.name}/$userId/${Keys.upvotedPosts.name}/$postKey" to postVal
        )

        db.updateChildren(updates)
    }

    fun updateBookmarkDB(userVal: String?, postKey: String, postVal: String?){
        val updates = hashMapOf<String, Any?>(
            "/${Keys.KEY_DB_POSTS.name}/$postKey/${Keys.bookmarkUsers.name}/$userId" to userVal,
            "/${Keys.KEY_DB_USERS.name}/$userId/${Keys.bookmarks.name}/$postKey" to postVal
        )

        db.updateChildren(updates)
    }

    fun updateHighlightDB(postKey: String, postVal: String?){
        val updates = hashMapOf<String, Any?>(
            "/${Keys.KEY_DB_USERS.name}/$userId/${Keys.highlights.name}/$postKey" to postVal
        )

        db.updateChildren(updates)
    }
}