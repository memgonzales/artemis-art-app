package com.mobdeve.gonzales.lee.ong.artemis

import android.content.Context
import android.content.Intent
import android.widget.Toast
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.EmailAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.*
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

/**
 * Class containing helper attributes and methods for accessing Firebase.
 */
class FirebaseHelper {
    /**
     * Context with which the database is being accessed.
     */
    private var context: Context

    /**
     * Attribute for performing Firebase authentications.
     */
    private var mAuth: FirebaseAuth

    /**
     * Reference to the database being accessed.
     */
    private var db: DatabaseReference

    /**
     * Attribute storing a user's profile information obtained from the user database.
     */
    private lateinit var user: FirebaseUser

    /**
     * Unique identifier of the user currently accessing the app.
     */
    private lateinit var userId: String

    /**
     * Credential used by Firebase to authenticate a user.
     */
    private lateinit var credential: AuthCredential


    /**
     * Creates a FirebaseHelper object given the context with which the database is accessed.
     *
     * @param context Context with which the database is accessed.
     */
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

    /**
     * Creates a FirebaseHelper object given the context with which the database is accessed, the
     * unique identifier of a post, and the unique identifier of the user who posted it.
     *
     * @param context Context with which the database is accessed.
     * @param postId Unique identifier of a post.
     * @param userIdPost Unique identifier of the user who posted the post.
     */
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

    /**
     * Creates a FirebaseHelper object given the context with which the database is accessed and a
     * unique identifier of a database document.
     *
     * @param context Context with which the database is accessed.
     * @param Id Unique identifier of a database document.
     */
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

    /**
     * Updates the upvotes of a post on the database.
     *
     * @param userVal Username of the user who upvoted or removed their upvote on a post.
     * @param postKey Unique identifier of the post whose upvotes are being toggled.
     * @param postVal Unique identifier of the post to be added or removed from the user's list of
     * upvoted posts.
     * @param numUpvotes Updated number of upvotes of the post.
     */
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

    /**
     * Updates the bookmark status of a post on the database.
     *
     * @param userVal Username of the user who bookmarked or removed the bookmark on a post.
     * @param postKey Unique identifier of the post whose bookmark status is being toggled.
     * @param postVal Unique identifier of the post to be added or removed from the user's list of
     * bookmarked posts.
     */
    fun updateBookmarkDB(userVal: String?, postKey: String?, postVal: String?){
        if (!postKey.isNullOrEmpty()){

            val updates = hashMapOf<String, Any?>(
                "/${Keys.KEY_DB_POSTS.name}/$postKey/${Keys.bookmarkUsers.name}/$userId" to userVal,
                "/${Keys.KEY_DB_USERS.name}/$userId/${Keys.bookmarks.name}/$postKey" to postVal
            )

            db.updateChildren(updates)
        }
    }

    /**
     * Updates the highlight status of a post on the database.
     *
     * @param postKey Unique identifier of the post whose highlight status is being toggled.
     * @param postVal Unique identifier of the post to be added or removed from the user's list of
     * highlighted posts.
     */
    fun updateHighlightDB(postKey: String?, postVal: String?){
        if(!postKey.isNullOrEmpty()){

            var userVal: String? = null

            if (!postVal.isNullOrEmpty()){
                userVal = userId
            }

            val updates = hashMapOf<String, Any?>(
                "/${Keys.KEY_DB_USERS.name}/$userId/${Keys.highlights.name}/$postKey" to postVal,
                "/${Keys.KEY_DB_POSTS.name}/$postKey/${Keys.highlightUser.name}" to userVal
            )

            db.updateChildren(updates)
        }
    }

    /**
     * Updates the user's list of posts on the database.
     *
     * @param postKey Unique identifier of the post.
     * @param postVal Unique identifier of the post to be added or removed from the user's list of
     * posts.
     */
    fun updateUserPostDB(postKey: String, postVal: String?){
        val updates = hashMapOf<String, Any?>(
            "/${Keys.KEY_DB_USERS.name}/$userId/${Keys.userPosts.name}/$postKey" to postVal
        )

        db.updateChildren(updates)
    }

    /**
     * Updates the user's list of followed users on the database.
     *
     * @param userKey Unique identifier of a user.
     * @param userVal Unique identifier of the user to be added or removed from the current user's
     * list of followed users.
     */
    fun updateUsersFollowedDB(userKey: String?, userVal: String?){
        val updates = hashMapOf<String, Any?>(
            "/${Keys.KEY_DB_USERS.name}/$userId/${Keys.usersFollowed.name}/$userKey" to userVal
        )

        db.updateChildren(updates)
    }

    /**
     * Edits the body of a comment.
     *
     * @param commentId Unique identifier of the comment being edited.
     * @param commentBody Updated comment body.
     */
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

    /**
     * Edits the details of a post.
     *
     * @param postId Unique identifier of the posts whose details are being edited.
     * @param title Updated title of the post.
     * @param medium Updated medium of the post.
     * @param dimensions Updated dimensions of the post.
     * @param desc Updated description of the post.
     * @param tags Updated tags of the post.
     */
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
                Toast.makeText(context, "Your post details have been updated", Toast.LENGTH_SHORT).show()

            }
            .addOnFailureListener {
                Toast.makeText(context, "Your post details failed to be updated", Toast.LENGTH_SHORT).show()
            }
    }

    /**
     * Deletes a comment from the database.
     *
     * @param commentId Unique identifier of the comment to be deleted.
     * @param postId Unique identifier of the post on which the comment was added.
     */
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

    /**
     * Updates a comment on the database.
     *
     * @param postId Unique identifier of the post on which the comment was added.
     * @param commentKey Unique identifier of the comment being updated.
     * @param commentVal Unique identifier of the comment being updated to be added ore removed from
     * the list of comments of a post.
     * @param numComments Updated number of comments on the post.
     */
    fun updateCommentFromPostDB(postId: String, commentKey: String?, commentVal: String?, numComments: Int){
        val updates = hashMapOf<String, Any?>(
            "/${Keys.KEY_DB_POSTS.name}/$postId/${Keys.comments.name}/$commentKey" to commentVal,
            "/${Keys.KEY_DB_POSTS.name}/$postId/${Keys.numComments.name}" to numComments,
        )

        db.updateChildren(updates)
    }

    /**
     * Deletes a post from the database.
     *
     * @param postKey Unique identifier of the post being deleted.
     * @param delUserFF <code>true</code> if the post was successfully deleted; <code>false</code>,
     * otherwise.
     */
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

    /**
     * Deletes a post and its related data (i.e., bookmark, highlight, and upvote status) from the
     * database.
     *
     * @param postId Unique identifier of the post being deleted.
     * @param delUserFF <code>true</code> if the post was and its related data was successfully
     * deleted; <code>false</code>, otherwise.
     */
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

    /**
     * Deletes a comment from the database.
     *
     * @param postId Unique identifier of the post where the comment to be deleted was added.
     */
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
    }
    
    /**
     * Deletes the account details of the current user.
     */
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