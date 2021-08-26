package com.mobdeve.gonzales.lee.ong.artemis

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.widget.*
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
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class ViewCommentsFollowedActivity : AppCompatActivity() {
    private lateinit var dataCommentsFollowed: ArrayList<Comment>
    private lateinit var rvCommentsFollowed: RecyclerView
    private lateinit var commentsAdapter: CommentsAdapter
    private lateinit var llViewCommentsFollowedShimmer: LinearLayout
    private lateinit var sflViewCommentsFollowed: ShimmerFrameLayout
    private lateinit var bnvViewCommentsFollowedBottom: BottomNavigationView

    private lateinit var btmAddPost: BottomSheetDialog
    private lateinit var fabAddPost: FloatingActionButton
    private lateinit var clDialogPostArtworkGallery: ConstraintLayout
    private lateinit var clDialogPostArtworkPhoto: ConstraintLayout

    private lateinit var ibAddComment: ImageButton
    private lateinit var etComment: EditText

    private lateinit var pbComment: ProgressBar

    private lateinit var srlViewCommentsFollowed: SwipeRefreshLayout

    //Firebase
    private lateinit var mAuth: FirebaseAuth
    private lateinit var user: FirebaseUser
    private lateinit var userId: String
    private lateinit var db: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_comments_followed)

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
        setSupportActionBar(findViewById(R.id.toolbar_view_comments_followed))
        initShimmer()
        initActionBar()
        initBottom()
        addPost()
        initSwipeRefresh()

        addComment()
    }

    private fun initShimmer() {
        this.sflViewCommentsFollowed = findViewById(R.id.sfl_view_comments_followed)

        sflViewCommentsFollowed.startShimmer()

        Handler(Looper.getMainLooper()).postDelayed({
            initRecyclerView()
            sflViewCommentsFollowed.visibility = View.GONE
            rvCommentsFollowed.visibility = View.VISIBLE
        }, AnimationDuration.SHIMMER_TIMEOUT.toLong())
    }

    private fun initSwipeRefresh() {
        this.srlViewCommentsFollowed = findViewById(R.id.srl_view_comments_followed)
        srlViewCommentsFollowed.setOnRefreshListener {
            onRefresh();
        }

        srlViewCommentsFollowed.setColorSchemeResources(R.color.purple_main,
            R.color.pinkish_purple,
            R.color.purple_pics_lighter,
            R.color.pinkish_purple_lighter);
    }

    private fun onRefresh() {
        Handler(Looper.getMainLooper()).postDelayed({
            srlViewCommentsFollowed.isRefreshing = false
        }, AnimationDuration.REFRESH_TIMEOUT.toLong())
    }

    private fun initBottom() {
        this.bnvViewCommentsFollowedBottom = findViewById(R.id.nv_view_comments_followed_bottom)

        bnvViewCommentsFollowedBottom.setOnItemSelectedListener{ item ->
            when (item.itemId) {
                R.id.icon_home_feed_followed -> {
                    val intent = Intent(this@ViewCommentsFollowedActivity, BrowseFeedActivity::class.java)
                    startActivity(intent)
                    finish()
                    return@setOnItemSelectedListener true
                }
                R.id.icon_follow_feed_followed -> {
                    val intent = Intent(this@ViewCommentsFollowedActivity, BrowseFeedFollowedActivity::class.java)
                    startActivity(intent)
                    finish()
                    return@setOnItemSelectedListener true
                }
                R.id.icon_bookmark_feed_followed -> {
                    val intent = Intent(this@ViewCommentsFollowedActivity, BrowseBookmarksActivity::class.java)
                    startActivity(intent)
                    finish()
                    return@setOnItemSelectedListener true
                }
                R.id.icon_user_feed_followed -> {
                    val intent = Intent(this@ViewCommentsFollowedActivity, ViewProfileActivity::class.java)
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
        this.dataCommentsFollowed = DataHelper.loadCommentData()

        this.rvCommentsFollowed = findViewById(R.id.rv_view_comments_followed)
        this.llViewCommentsFollowedShimmer = findViewById(R.id.ll_view_comments_followed_shimmer)

        this.rvCommentsFollowed.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)

        this.commentsAdapter = CommentsAdapter(this.dataCommentsFollowed)

        this.rvCommentsFollowed.adapter = commentsAdapter
    }

    private fun addComment(comment: Comment) {
        val commentDB = this.db.child(Keys.KEY_DB_COMMENTS.name)
        val postKey = commentDB.push().key!!

        commentDB.child(postKey).setValue(comment)
            .addOnCompleteListener { task ->
                if (task.isSuccessful){
                    this.pbComment.visibility = View.GONE
                    Toast.makeText(this, "Commented successfully", Toast.LENGTH_LONG).show()
                }

                else{
                    this.pbComment.visibility = View.VISIBLE
                    Toast.makeText(this, "Failed to comment", Toast.LENGTH_LONG).show()
                }
            }
    }

    private fun addComment() {
        this.ibAddComment = findViewById(R.id.ib_add_comment_followed)
        this.etComment = findViewById(R.id.et_add_comment_followed)
        this.pbComment = findViewById(R.id.pb_view_comments_followed)

        this.ibAddComment.setOnClickListener {
            val commentText: String = etComment.text.toString().trim()

            if (!commentText.isEmpty()) {
                this.pbComment.visibility = View.VISIBLE

                val comment: Comment = Comment("1", R.drawable.chibi_circle, "yey", commentText, true)
                addComment(comment)
            } else {
                Toast.makeText(this, "Comments should not be blank", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun addPost() {
        this.btmAddPost = BottomSheetDialog(this@ViewCommentsFollowedActivity)
        this.fabAddPost = findViewById(R.id.fab_view_comments_followed_add)

        val view = LayoutInflater.from(this@ViewCommentsFollowedActivity).inflate(R.layout.dialog_post_artwork, null)

        this.fabAddPost.setOnClickListener {
            btmAddPost.setContentView(view)

            this.clDialogPostArtworkGallery = btmAddPost.findViewById(R.id.cl_dialog_post_artwork_gallery)!!
            this.clDialogPostArtworkPhoto = btmAddPost.findViewById(R.id.cl_dialog_post_artwork_photo)!!

            clDialogPostArtworkGallery.setOnClickListener(View.OnClickListener {
                Toast.makeText(this@ViewCommentsFollowedActivity, "Photo chosen from the gallery", Toast.LENGTH_SHORT).show()
                btmAddPost.dismiss()
                val intent = Intent(this@ViewCommentsFollowedActivity, PostArtworkActivity::class.java)
                startActivity(intent)
            })

            clDialogPostArtworkPhoto.setOnClickListener(View.OnClickListener {
                Toast.makeText(this@ViewCommentsFollowedActivity, "Photo taken with the device camera", Toast.LENGTH_SHORT).show()
                btmAddPost.dismiss()
                val intent = Intent(this@ViewCommentsFollowedActivity, PostArtworkActivity::class.java)
                startActivity(intent)
            })

            btmAddPost.show()
        }
    }
}