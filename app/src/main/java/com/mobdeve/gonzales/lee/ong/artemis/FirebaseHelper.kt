package com.mobdeve.gonzales.lee.ong.artemis

import android.content.Context
import android.content.Intent
import android.widget.Adapter
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.EmailAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class FirebaseHelper {

    private var context: Context

    private var mAuth: FirebaseAuth
    private var db: DatabaseReference

    private lateinit var user: FirebaseUser
    private lateinit var userId: String

    private lateinit var credential: AuthCredential

    private lateinit var adapter: Adapter

    constructor(context: Context, adapter: Adapter){
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

        this.adapter = adapter
    }


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

    constructor(context: Context, Id: String?){
        this.mAuth = Firebase.auth
        this.db = Firebase.database.reference

        if(Id.isNullOrEmpty()){
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

    fun updateUsersFollowedDB(userKey: String?, userVal: String?){
        val updates = hashMapOf<String, Any?>(
            "/${Keys.KEY_DB_USERS.name}/$userId/${Keys.usersFollowed.name}/$userKey" to userVal
        )

        db.updateChildren(updates)
    }




    fun editComment(commentId: String, commentBody: String){
        if (!commentId.isNullOrEmpty()){
            this.db.child(Keys.KEY_DB_COMMENTS.name).child(commentId).child(Keys.commentBody.name).setValue(commentBody)
                .addOnSuccessListener {
                    Toast.makeText(context, "Your comment has been updated", Toast.LENGTH_SHORT).show()
                }
                .addOnFailureListener {
                    Toast.makeText(context, "Your comment failed to be updated", Toast.LENGTH_SHORT).show()
                }
        }
    }

    fun editPost(postId: String, title: String, medium: String, dimensions: String, desc: String, tags: ArrayList<String>){
        val updates = hashMapOf<String, Any?>(
            "/${Keys.KEY_DB_POSTS.name}/$postId/${Keys.title.name}" to title,
            "/${Keys.KEY_DB_POSTS.name}/$postId/${Keys.medium.name}" to medium,
            "/${Keys.KEY_DB_POSTS.name}/$postId/${Keys.dimensions.name}" to dimensions,
            "/${Keys.KEY_DB_POSTS.name}/$postId/${Keys.description.name}" to desc,
            "/${Keys.KEY_DB_POSTS.name}/$postId/${Keys.tags.name}" to tags
        )

        db.updateChildren(updates)
            .addOnSuccessListener {
          //      Toast.makeText(context, "Your post details have been updated", Toast.LENGTH_SHORT).show()

            }
            .addOnFailureListener {
                     Toast.makeText(context, "Your post details failed to be updated", Toast.LENGTH_SHORT).show()
            }
    }





    fun deleteCommentDB(commentId: String, postId: String){
        if (!commentId.isNullOrEmpty() && !postId.isNullOrEmpty()){
            val commentDB = db.child(Keys.KEY_DB_COMMENTS.name).child(commentId)
            val postDB = db.child(Keys.KEY_DB_POSTS.name).child(postId)

            commentDB.removeValue()
                .addOnSuccessListener {
                    Toast.makeText(context, "Your comment has been deleted", Toast.LENGTH_SHORT).show()

                    postDB.child(Keys.numComments.name)
                        .addListenerForSingleValueEvent(object : ValueEventListener{
                            override fun onDataChange(snapshot: DataSnapshot) {
                                val numComments = snapshot.getValue().toString().toInt() - 1
                                updateCommentFromPostDB(postId, commentId, null, numComments)
                            }

                            override fun onCancelled(error: DatabaseError) {
                                val intent = Intent(context, BrokenLinkActivity::class.java)
                                context.startActivity(intent)
                            }

                        })
                }
                .addOnFailureListener {
                    Toast.makeText(context, "Unable to delete your comment", Toast.LENGTH_SHORT).show()
                }
        }

    }

    fun updateCommentFromPostDB(postId: String, commentKey: String?, commentVal: String?, numComments: Int){
        val updates = hashMapOf<String, Any?>(
            "/${Keys.KEY_DB_POSTS.name}/$postId/${Keys.comments.name}/$commentKey" to commentVal,
            "/${Keys.KEY_DB_POSTS.name}/$postId/${Keys.numComments.name}" to numComments,
        )

        db.updateChildren(updates)
    }




    fun deletePostDB(postKey: String, delUserFF: Boolean){
        val postDB = db.child(Keys.KEY_DB_POSTS.name).child(postKey)

        postDB.removeValue()
            .addOnSuccessListener {
                deleteCommentFromPostDB(postKey)
                deletePostANDUserFollowedFromUsersDB(postKey, delUserFF)

                if (!delUserFF){
                    Toast.makeText(context, "Successfully deleted post", Toast.LENGTH_SHORT).show()
                }
            }
            .addOnFailureListener {
                Toast.makeText(context, "Unable to delete post", Toast.LENGTH_SHORT).show()
            }
    }

    fun deletePostANDUserFollowedFromUsersDB(postId: String, delUserFF: Boolean){
        val userDB = db.child(Keys.KEY_DB_USERS.name)

        userDB.addListenerForSingleValueEvent(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {

                if (snapshot.exists()){
                    for (u in snapshot.children){
                        val userSnap = u.getValue(User::class.java)

                        if (userSnap != null){
                            val bookmarks = userSnap.getBookmarks().keys
                            val highlights = userSnap.getHighlights().keys
                            val upvotedPosts = userSnap.getUpvotedPosts().keys
                            val userPosts = userSnap.getUserPosts().keys
                            val userFFs = userSnap.getUsersFollowed().keys

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

                            if (delUserFF && !userFFs.isNullOrEmpty() && userFFs.contains(userId)){
                                updateUsersFollowedDB(userId, null)
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

    fun deleteCommentFromPostDB(postId: String){
        val commentDB = db.child(Keys.KEY_DB_COMMENTS.name)

        commentDB.addListenerForSingleValueEvent(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()){
                    for (c in snapshot.children){
                        var comment = c.getValue(Comment::class.java)

                        if (comment != null){
                            if (!comment.getPostId().isNullOrEmpty() && comment.getPostId().equals(postId)){
                                commentDB.child(comment.getCommentId()!!).removeValue()
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
        /*
        commentDB.orderByChild(Keys.postId.name).equalTo(postId)
            .addListenerForSingleValueEvent(object : ValueEventListener{
                override fun onDataChange(snapshot: DataSnapshot) {
                    if (snapshot.exists()){
                        for (c in snapshot.children){
                            var commentKey = c.key!!

                            commentDB.child(commentKey).removeValue()
                        }
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    val intent = Intent(context, BrokenLinkActivity::class.java)
                    context.startActivity(intent)
                }

            })

         */
    }




    fun deleteUserDB(){
        val userDB = db.child(Keys.KEY_DB_USERS.name).child(userId)

        userDB.addListenerForSingleValueEvent(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                val userSnap = snapshot.getValue(User::class.java)

                if (userSnap != null && !userSnap.getEmail().isNullOrEmpty() && !userSnap.getPassword().isNullOrEmpty()){
                    credential = EmailAuthProvider.getCredential(userSnap.getEmail()!!, userSnap.getPassword()!!)

                    user.reauthenticate(credential).addOnCompleteListener {
                        if (it.isSuccessful){
                            deleteUser()

                            user.delete().addOnCompleteListener {
                                if (it.isSuccessful){

                                    Toast.makeText(context.applicationContext, "Account deleted", Toast.LENGTH_SHORT).show()
                                    val intent = Intent(context, LogInActivity::class.java)
                                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP
                                    context.startActivity(intent)
                                }

                                else{
                                    Toast.makeText(context, "Failed to delete account", Toast.LENGTH_SHORT).show()
                                }
                            }


                        }

                        else{
                            Toast.makeText(context, "Failed to delete account", Toast.LENGTH_SHORT).show()
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

    fun deleteUser(){
        val userDB = db.child(Keys.KEY_DB_USERS.name).child(userId)

        userDB.addListenerForSingleValueEvent(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                val userSnap = snapshot.getValue(User::class.java)

                if (userSnap != null){
                    val userPosts = userSnap.getUserPosts().keys

                    if (!userPosts.isNullOrEmpty()){
                        for (postId in userPosts){
                            deletePostDB(postId, true)
                        }
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {
                val intent = Intent(context, BrokenLinkActivity::class.java)
                context.startActivity(intent)
            }
        })

        userDB.removeValue()
    }

}