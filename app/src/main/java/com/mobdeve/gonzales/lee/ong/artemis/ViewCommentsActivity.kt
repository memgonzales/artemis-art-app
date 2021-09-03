package com.mobdeve.gonzales.lee.ong.artemis

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.facebook.shimmer.ShimmerFrameLayout
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class ViewCommentsActivity : AppCompatActivity() {
    private lateinit var dataComments: ArrayList<Comment>
    private lateinit var rvComments: RecyclerView
    private lateinit var commentsAdapter: CommentsAdapter
    private lateinit var llViewCommentsShimmer: LinearLayout
    private lateinit var sflViewComments: ShimmerFrameLayout
    private lateinit var bnvViewCommentsBottom: BottomNavigationView

    private lateinit var btmAddPost: BottomSheetDialog
    private lateinit var fabAddPost: FloatingActionButton
    private lateinit var clDialogPostArtworkGallery: ConstraintLayout
    private lateinit var clDialogPostArtworkPhoto: ConstraintLayout

    private lateinit var ibAddComment: ImageButton
    private lateinit var etComment: EditText

    private lateinit var pbComment: ProgressBar

    private lateinit var srlViewComments: SwipeRefreshLayout

    //Firebase
    private lateinit var mAuth: FirebaseAuth
    private lateinit var user: FirebaseUser
    private lateinit var userId: String
    private lateinit var db: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_comments)

        initComponents()
        initFirebase()
    }

    private fun initFirebase() {
        this.mAuth = Firebase.auth
        this.user = this.mAuth.currentUser!!
        this.userId = this.user.uid
        this.db = Firebase.database.reference
    }

    private fun initComponents() {
        setSupportActionBar(findViewById(R.id.toolbar_view_comments))
        initShimmer()
        initActionBar()
        initBottom()
        addPost()
        initSwipeRefresh()

        addComment()
    }

    private fun initShimmer() {
        this.sflViewComments = findViewById(R.id.sfl_view_comments)

        sflViewComments.startShimmer()

        Handler(Looper.getMainLooper()).postDelayed({
            initRecyclerView()
            sflViewComments.visibility = View.GONE
            rvComments.visibility = View.VISIBLE
        }, AnimationDuration.SHIMMER_TIMEOUT.toLong())
    }

    private fun initSwipeRefresh() {
        this.srlViewComments = findViewById(R.id.srl_view_comments)
        srlViewComments.setOnRefreshListener {
            onRefresh();
        }

        srlViewComments.setColorSchemeResources(R.color.purple_main,
            R.color.pinkish_purple,
            R.color.purple_pics_lighter,
            R.color.pinkish_purple_lighter);
    }

    private fun onRefresh() {
        Handler(Looper.getMainLooper()).postDelayed({
            srlViewComments.isRefreshing = false
        }, AnimationDuration.REFRESH_TIMEOUT.toLong())
    }

    private fun initBottom() {
        this.bnvViewCommentsBottom = findViewById(R.id.nv_view_comments_bottom)

        bnvViewCommentsBottom.setOnItemSelectedListener{ item ->
            when (item.itemId) {
                R.id.icon_home_feed -> {
                    val intent = Intent(this@ViewCommentsActivity, BrowseFeedActivity::class.java)
                    startActivity(intent)
                    finish()
                    return@setOnItemSelectedListener true
                }
                R.id.icon_follow_feed -> {
                    val intent = Intent(this@ViewCommentsActivity, BrowseFeedFollowedActivity::class.java)
                    startActivity(intent)
                    finish()
                    return@setOnItemSelectedListener true
                }
                R.id.icon_bookmark_feed -> {
                    val intent = Intent(this@ViewCommentsActivity, BrowseBookmarksActivity::class.java)
                    startActivity(intent)
                    finish()
                    return@setOnItemSelectedListener true
                }
                R.id.icon_user_feed -> {
                    val intent = Intent(this@ViewCommentsActivity, ViewProfileActivity::class.java)
                    startActivity(intent)
                    finish()
                    return@setOnItemSelectedListener true
                }
            }
            false
        }
    }

    private fun initActionBar() {
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
    }

    private fun initRecyclerView() {
        this.dataComments = DataHelper.loadCommentData()

        this.rvComments = findViewById(R.id.rv_view_comments)
        this.llViewCommentsShimmer = findViewById(R.id.ll_view_comments_shimmer)

        this.rvComments.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)

        this.commentsAdapter = CommentsAdapter(this.dataComments)

        this.rvComments.adapter = commentsAdapter
    }

    private fun addCommentDB(comment: Comment) {
        val commentDB = this.db.child(Keys.KEY_DB_COMMENTS.name)
        val commentKey = commentDB.push().key!!

        val userDB = this.db.child(Keys.KEY_DB_USERS.name)

        userDB.child(this.userId).addValueEventListener(object: ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                var comments = snapshot.child(Keys.comments.name).getValue().toString()
                var commentIds: ArrayList<String> = ArrayList<String>()

                if(comments != null){
                    commentIds = getList(comments)
                }



                commentIds.add(commentKey)

                //Toast.makeText(applicationContext, "cj: " + commentIds, Toast.LENGTH_SHORT).show()

                val updates = hashMapOf<String, Any>(
                    "/${Keys.KEY_DB_COMMENTS}/$commentKey" to comment,
                    "/${Keys.KEY_DB_USERS}/$userId/${Keys.comments.name}" to commentIds
                )

                db.updateChildren(updates)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful){
                            pbComment.visibility = View.GONE
                            Toast.makeText(this@ViewCommentsActivity, "Commented successfully", Toast.LENGTH_LONG).show()
                            etComment.text.clear()
                        }

                        else{
                            pbComment.visibility = View.VISIBLE
                            Toast.makeText(this@ViewCommentsActivity, "Failed to comment", Toast.LENGTH_LONG).show()
                        }
                    }




            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
        /*
        commentDB.child(commentKey).setValue(comment)
            .addOnCompleteListener { task ->
                if (task.isSuccessful){
                    this.pbComment.visibility = View.GONE
                    Toast.makeText(this, "Commented successfully", Toast.LENGTH_LONG).show()
                    this.etComment.text.clear()
                }

                else{
                    this.pbComment.visibility = View.VISIBLE
                    Toast.makeText(this, "Failed to comment", Toast.LENGTH_LONG).show()
                }
            }


         */
        /*
        val updates = hashMapOf<String, Any>(
            "/${Keys.KEY_DB_COMMENTS}/$commentKey" to comment,
            "/${Keys.KEY_DB_USERS}/$userId/${Keys.comments.name}" to commentKey
        )

        this.db.updateChildren(updates)
            .addOnCompleteListener { task ->
                if (task.isSuccessful){
                    this.pbComment.visibility = View.GONE
                    Toast.makeText(this, "Commented successfully", Toast.LENGTH_LONG).show()
                    this.etComment.text.clear()
                }

                else{
                    this.pbComment.visibility = View.VISIBLE
                    Toast.makeText(this, "Failed to comment", Toast.LENGTH_LONG).show()
                }
            }

         */
    }

    private fun getList(str: String): ArrayList<String>{
        return str.substring(1, str.length-1).split(",").toCollection(ArrayList<String>())
    }

    /*
    private fun addCommentToUserDB(commentId: String){
        val userDB = this.db.child(Keys.KEY_DB_USERS.name)
        val userKey = "kWI0ojJ9RaPoJZbkPAR9dQGvVPf2"

        userDB.child(userKey).child(Keys.comments.name).set
    }

     */

    private fun addComment() {
        this.ibAddComment = findViewById(R.id.ib_add_comment_followed)
        this.etComment = findViewById(R.id.et_add_comment_followed)
        this.pbComment = findViewById(R.id.pb_view_comments)

        this.ibAddComment.setOnClickListener {
            val commentText: String = etComment.text.toString().trim()

            if (!commentText.isEmpty()) {
                this.pbComment.visibility = View.VISIBLE

                val comment: Comment = Comment("1", R.drawable.tofu_chan, "Tobe", commentText, true)
                addCommentDB(comment)

            } else {
                Toast.makeText(this, "Comments should not be blank", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun addPost() {
        this.btmAddPost = BottomSheetDialog(this@ViewCommentsActivity)
        this.fabAddPost = findViewById(R.id.fab_view_comments_add)

        val view = LayoutInflater.from(this@ViewCommentsActivity).inflate(R.layout.dialog_post_artwork, null)

        this.fabAddPost.setOnClickListener {
            btmAddPost.setContentView(view)

            this.clDialogPostArtworkGallery = btmAddPost.findViewById(R.id.cl_dialog_post_artwork_gallery)!!
            this.clDialogPostArtworkPhoto = btmAddPost.findViewById(R.id.cl_dialog_post_artwork_photo)!!

            clDialogPostArtworkGallery.setOnClickListener(View.OnClickListener {
                Toast.makeText(this@ViewCommentsActivity, "Photo chosen from the gallery", Toast.LENGTH_SHORT).show()
                btmAddPost.dismiss()
                val intent = Intent(this@ViewCommentsActivity, PostArtworkActivity::class.java)
                startActivity(intent)
            })

            clDialogPostArtworkPhoto.setOnClickListener(View.OnClickListener {
                Toast.makeText(this@ViewCommentsActivity, "Photo taken with the device camera", Toast.LENGTH_SHORT).show()
                btmAddPost.dismiss()
                val intent = Intent(this@ViewCommentsActivity, PostArtworkActivity::class.java)
                startActivity(intent)
            })

            btmAddPost.show()
        }
    }
}