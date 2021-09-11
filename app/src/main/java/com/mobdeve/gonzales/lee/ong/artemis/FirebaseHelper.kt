package com.mobdeve.gonzales.lee.ong.artemis

import android.content.Context
import android.content.Intent
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import java.security.Key

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

        if(postId.isNullOrEmpty() || userIdPost.isNullOrEmpty()){
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

    constructor(context: Context, commentId: String){
        this.mAuth = Firebase.auth
        this.db = Firebase.database.reference

        if(commentId.isNullOrEmpty()){
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

    fun updateUpvoteDB(userVal: String?, postKey: String?, postVal: String?, numUpvotes: Int){
        if (!postKey.isNullOrEmpty()){
            val updates = hashMapOf<String, Any?>(
                "/${Keys.KEY_DB_POSTS.name}/$postKey/${Keys.upvoteUsers.name}/$userId" to userVal,
                "/${Keys.KEY_DB_POSTS.name}/$postKey/${Keys.numUpvotes.name}" to numUpvotes,
                "/${Keys.KEY_DB_USERS.name}/$userId/${Keys.upvotedPosts.name}/$postKey" to postVal
            )

            db.updateChildren(updates)
        }
    }

    fun updateBookmarkDB(userVal: String?, postKey: String?, postVal: String?){
        if (!postKey.isNullOrEmpty()){
            val updates = hashMapOf<String, Any?>(
                "/${Keys.KEY_DB_POSTS.name}/$postKey/${Keys.bookmarkUsers.name}/$userId" to userVal,
                "/${Keys.KEY_DB_USERS.name}/$userId/${Keys.bookmarks.name}/$postKey" to postVal
            )

            db.updateChildren(updates)
        }
    }

    fun updateHighlightDB(postKey: String?, postVal: String?){
        if(!postKey.isNullOrEmpty()){
            val updates = hashMapOf<String, Any?>(
                "/${Keys.KEY_DB_USERS.name}/$userId/${Keys.highlights.name}/$postKey" to postVal
            )

            db.updateChildren(updates)
        }
    }

    fun updateUserPostDB(postKey: String, postVal: String?){
        val updates = hashMapOf<String, Any?>(
            "/${Keys.KEY_DB_USERS.name}/$userId/${Keys.userPosts.name}/$postKey" to postVal
        )

        db.updateChildren(updates)
    }






    fun editComment(commentId: String, commentBody: String){
        this.db.child(Keys.KEY_DB_COMMENTS.name).child(commentId).child(Keys.commentBody.name).setValue(commentBody)
            .addOnSuccessListener {
                Toast.makeText(context, "Your comment has been updated", Toast.LENGTH_SHORT).show()
            }
            .addOnFailureListener {
                Toast.makeText(context, "Your comment failed to be updated", Toast.LENGTH_SHORT).show()
            }
    }

    fun editPost(postId: String, title: String, medium: String, dimensions: String, desc: String, tags: ArrayList<String>){
        val updates = hashMapOf<String, Any?>(
            "/${Keys.KEY_DB_POSTS.name}/$postId/${Keys.title.name}" to title,
            "/${Keys.KEY_DB_POSTS.name}/$postId/${Keys.medium.name}" to medium,
            "/${Keys.KEY_DB_POSTS.name}/$postId/${Keys.dimensions.name}" to dimensions,
            "/${Keys.KEY_DB_POSTS.name}/$postId/${Keys.description.name}" to desc,
            "/${Keys.KEY_DB_POSTS.name}/$postId/${Keys.tags.name}" to tags,
        )

        db.updateChildren(updates)
            .addOnSuccessListener {
                Toast.makeText(context, "Your post details have been updated", Toast.LENGTH_SHORT).show()

            }
            .addOnFailureListener {
                Toast.makeText(context, "Your post details failed to be updated", Toast.LENGTH_SHORT).show()
            }
    }






    fun deletePostDB(postKey: String){
        val postDB = db.child(Keys.KEY_DB_POSTS.name).child(postKey)

        postDB.removeValue()
            .addOnSuccessListener {
                deletePostFromUsersDB(postKey)
                Toast.makeText(context, "Successfully deleted post", Toast.LENGTH_SHORT).show()
            }
    }

    fun deletePostFromUsersDB(postId: String){
        val userDB = db.child(Keys.KEY_DB_USERS.name)

        userDB.addListenerForSingleValueEvent(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {

                if (snapshot.exists()){
                    for (u in snapshot.children){
                        var userSnap = u.getValue(User::class.java)

                        if (userSnap != null){
                            var bookmarks = userSnap.getBookmarks().keys
                            var highlights = userSnap.getHighlights().keys
                            var upvotedPosts = userSnap.getUpvotedPosts().keys
                            var userPosts = userSnap.getUserPosts().keys

                            if(!bookmarks.isNullOrEmpty() && bookmarks.contains(postId)){
                                updateBookmarkDB(null, postId, null)
                            }

                            if(!highlights.isNullOrEmpty() && highlights.contains(postId)){
                                updateHighlightDB(postId, null)
                            }

                            if (!upvotedPosts.isNullOrEmpty() && upvotedPosts.contains(postId)){
                                updateUpvoteDB(null, postId, null, 0)
                            }

                            if (!userPosts.isNullOrEmpty() && userPosts.contains(postId)){
                                updateUserPostDB(postId, null)
                            }
                        }
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {
                val intent = Intent(context, BrokenLinkActivity::class.java)
                context.startActivity(intent)
            }
        })
    }

    fun deleteUserDB(){
        val userDB = db.child(Keys.KEY_DB_USERS.name).child(userId)

        userDB.addListenerForSingleValueEvent(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                val userSnap = snapshot.getValue(User::class.java)

                if (userSnap != null){
                    val userPosts = userSnap.getUserPosts().keys
                }
            }

            override fun onCancelled(error: DatabaseError) {
                val intent = Intent(context, BrokenLinkActivity::class.java)
                context.startActivity(intent)
            }

        })


        //db.child(Keys.KEY_DB_USERS.name).child(userId).removeValue()

    }
    /*
    fun getPosts(){
        val postDB = db.child(Keys.KEY_DB_POSTS.name)

        postDB.addListenerForSingleValueEvent(object : ValueEventListener{

        })
    }

     */
}