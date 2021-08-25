package com.mobdeve.gonzales.lee.ong.artemis

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.Gravity
import android.view.View
import android.widget.EditText
import android.widget.ImageButton
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.NestedScrollView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.facebook.shimmer.ShimmerFrameLayout
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class ViewCommentsActivity : AppCompatActivity() {
    private lateinit var dataComments: ArrayList<Comment>
    private lateinit var rvComments: RecyclerView
    private lateinit var commentsAdapter: CommentsAdapter
    private lateinit var llViewCommentsShimmer: LinearLayout
    private lateinit var sflViewComments: ShimmerFrameLayout
    private lateinit var bnvViewCommentsBottom: BottomNavigationView
    private lateinit var nsvViewComments: NestedScrollView

    private lateinit var ibAddComment: ImageButton
    private lateinit var etComment: EditText

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

    private fun initBottom() {
        this.bnvViewCommentsBottom = findViewById(R.id.nv_view_comments_bottom)
        this.nsvViewComments = findViewById(R.id.nsv_view_comments)

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

    private fun addComment(comment: Comment){
        val commentDB = this.db.child(Keys.KEY_DB_COMMENTS.name)
        val postKey = commentDB.push().key!!

        commentDB.child(postKey).setValue(comment)
            .addOnCompleteListener { task ->
                if (task.isSuccessful){
                    Toast.makeText(this, "Commented Successfully", Toast.LENGTH_LONG).show()
                }

                else{
                    Toast.makeText(this, "Failed to Comment", Toast.LENGTH_LONG).show()

                }
            }

    }

    fun onClick(v: View) {
        this.ibAddComment = findViewById(R.id.ib_add_comment)
        this.etComment = findViewById(R.id.et_add_comment)

        if (v.id == R.id.ib_add_comment) {
            val commentText: String = etComment.text.toString().trim()

            if (!commentText.isEmpty()){
                val comment: Comment = Comment("1", R.drawable.chibi_circle, "yey", commentText)
                addComment(comment)
            }

            else{
                Toast.makeText(this, "Kindly input a comment", Toast.LENGTH_SHORT).show()
            }
        }
    }


}