package com.mobdeve.gonzales.lee.ong.artemis

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.MenuItem
import android.view.View
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.bumptech.glide.Glide
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import de.hdodenhof.circleimageview.CircleImageView

class ViewPostUnregisteredActivity : AppCompatActivity() {
    private lateinit var civItemViewPostUnregisteredProfilePic: CircleImageView
    private lateinit var tvItemViewPostUnregisteredUsername: TextView
    private lateinit var ivItemViewPostUnregisteredPost: ImageView
    private lateinit var tvItemViewPostUnregisteredTitle: TextView
    private lateinit var tvItemViewPostUnregisteredUpvoteCounter: TextView
    private lateinit var tvItemViewPostUnregisteredComments: TextView
    private lateinit var tvItemViewPostUnregisteredDatePosted: TextView
    private lateinit var tvItemViewPostUnregisteredMedium: TextView
    private lateinit var tvItemViewPostUnregisteredDimensions: TextView
    private lateinit var tvItemViewPostUnregisteredDescription: TextView
    private lateinit var tvItemViewPostUnregisteredTags: TextView

    private lateinit var ibItemViewPostUnregisteredBookmark: ImageButton
    private lateinit var clItemViewPostUnregisteredUpvote: ConstraintLayout
    private lateinit var clItemViewPostUnregisteredComment: ConstraintLayout
    private lateinit var clItemViewPostUnregisteredShare: ConstraintLayout
    private lateinit var bnvViewPostUnregisteredBottom: BottomNavigationView

    private lateinit var fabAddPost: FloatingActionButton

    private lateinit var srlViewPostUnregisterd: SwipeRefreshLayout

    private lateinit var firebaseHelper: FirebaseHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_post_unregistered)

        civItemViewPostUnregisteredProfilePic = findViewById(R.id.civ_item_view_post_unregistered_profile_pic)
        tvItemViewPostUnregisteredUsername = findViewById(R.id.tv_item_view_post_unregistered_username)
        ivItemViewPostUnregisteredPost = findViewById(R.id.iv_item_view_post_unregistered_post)
        tvItemViewPostUnregisteredTitle = findViewById(R.id.tv_item_view_post_unregistered_title)
        tvItemViewPostUnregisteredUpvoteCounter = findViewById(R.id.tv_item_view_post_unregistered_upvote_counter)
        tvItemViewPostUnregisteredComments = findViewById(R.id.tv_item_view_post_unregistered_comments)
        tvItemViewPostUnregisteredDatePosted = findViewById(R.id.tv_item_view_post_unregistered_date)
        tvItemViewPostUnregisteredMedium = findViewById(R.id.tv_item_view_post_unregistered_medium)
        tvItemViewPostUnregisteredDimensions = findViewById(R.id.tv_item_view_post_unregistered_dimen)
        tvItemViewPostUnregisteredDescription = findViewById(R.id.tv_item_view_post_unregistered_desc)
        tvItemViewPostUnregisteredTags = findViewById(R.id.tv_item_view_post_unregistered_tags)
        ibItemViewPostUnregisteredBookmark = findViewById(R.id.ib_item_view_post_unregistered_bookmark)
        clItemViewPostUnregisteredUpvote = findViewById(R.id.cl_item_view_post_unregistered_upvote)
        clItemViewPostUnregisteredComment = findViewById(R.id.cl_item_view_post_unregistered_comment)
        clItemViewPostUnregisteredShare = findViewById(R.id.cl_item_view_post_unregistered_share)

        initIntent()
        initComponents()
        initSwipeRefresh()
    }

    private fun initSwipeRefresh() {
        this.srlViewPostUnregisterd = findViewById(R.id.srl_view_post_unregistered)
        srlViewPostUnregisterd.setOnRefreshListener {
            onRefresh()
        }

        srlViewPostUnregisterd.setColorSchemeResources(R.color.purple_main,
            R.color.pinkish_purple,
            R.color.purple_pics_lighter,
            R.color.pinkish_purple_lighter)
    }

    private fun onRefresh() {
        Handler(Looper.getMainLooper()).postDelayed({
            srlViewPostUnregisterd.isRefreshing = false
        }, AnimationDuration.REFRESH_TIMEOUT.toLong())
    }

    private fun initIntent() {
        val intent: Intent = intent

        val postId = intent.getStringExtra(Keys.KEY_POSTID.name)
        val userIdPost = intent.getStringExtra(Keys.KEY_USERID.name)

        this.firebaseHelper = FirebaseHelper(this@ViewPostUnregisteredActivity, postId, userIdPost)

        val profilePicture = intent.getStringExtra(Keys.KEY_PROFILE_PICTURE.name)
        val username = intent.getStringExtra(Keys.KEY_USERNAME.name)

        val postImg = intent.getStringExtra(Keys.KEY_POST.name)
        val title = intent.getStringExtra(Keys.KEY_TITLE.name)

        val upvoteCounter = intent.getIntExtra(Keys.KEY_NUM_UPVOTES.name, 0)
        val comments = intent.getIntExtra(Keys.KEY_NUM_COMMENTS.name, 0)

        val datePosted = intent.getStringExtra(Keys.KEY_DATE_POSTED.name)
        val medium = intent.getStringExtra(Keys.KEY_MEDIUM.name)
        val dimensions = intent.getStringExtra(Keys.KEY_DIMENSIONS.name)
        val description = intent.getStringExtra(Keys.KEY_DESCRIPTION.name)
        val tags = intent.getStringArrayListExtra(Keys.KEY_TAGS.name)
        var bookmark = intent.getBooleanExtra(Keys.KEY_BOOKMARK.name, false)
        var upvote = intent.getBooleanExtra(Keys.KEY_UPVOTE.name, false)

        var upvoteString = ""
        var commentString = ""

        if (upvoteCounter == 1) {
            upvoteString = "$upvoteCounter upvote"
        } else {
            upvoteString = "$upvoteCounter upvotes"
        }

        if (comments == 1) {
            commentString = "$comments comment"
        } else {
            commentString = "$comments comments"
        }

        val tagsString = tags?.joinToString(", ")

        //this.civItemViewPostUnregisteredProfilePic.setImageResource(profilePicture)
        Glide.with(this)
            .load(profilePicture)
            .placeholder(R.drawable.chibi_artemis_hd)
            .error(R.drawable.chibi_artemis_hd)
            .into(this.civItemViewPostUnregisteredProfilePic)

        this.tvItemViewPostUnregisteredUsername.text = username
        //this.ivItemViewPostUnregisteredPost.setImageResource(post)
        Glide.with(this)
            .load(postImg)
            .placeholder(R.drawable.placeholder)
            .error(R.drawable.placeholder)
            .into(this.ivItemViewPostUnregisteredPost)

        if (!title.isNullOrEmpty()){
            this.tvItemViewPostUnregisteredTitle.visibility = View.VISIBLE
            this.tvItemViewPostUnregisteredTitle.text = title
        }
        else{
            this.tvItemViewPostUnregisteredTitle.visibility = View.INVISIBLE
        }

        this.tvItemViewPostUnregisteredUpvoteCounter.text = upvoteString
        this.tvItemViewPostUnregisteredComments.text = commentString
        this.tvItemViewPostUnregisteredDatePosted.text = datePosted

        if(!medium.isNullOrEmpty()){
            this.tvItemViewPostUnregisteredMedium.visibility = View.VISIBLE
            this.tvItemViewPostUnregisteredMedium.text = medium
        }
        else{
            this.tvItemViewPostUnregisteredMedium.visibility = View.GONE
        }

        if(!dimensions.isNullOrEmpty()){
            this.tvItemViewPostUnregisteredDimensions.visibility = View.VISIBLE
            this.tvItemViewPostUnregisteredDimensions.text = dimensions
        }
        else{
            this.tvItemViewPostUnregisteredDimensions.visibility = View.GONE
        }

        if(!description.isNullOrEmpty()){
            this.tvItemViewPostUnregisteredDescription.visibility = View.VISIBLE
            this.tvItemViewPostUnregisteredDescription.text = description
        }
        else{
            this.tvItemViewPostUnregisteredDescription.visibility = View.GONE
        }

        this.tvItemViewPostUnregisteredTags.text = tagsString

        ibItemViewPostUnregisteredBookmark.setOnClickListener {
            Toast.makeText(
                this@ViewPostUnregisteredActivity,
                "Log in or create an account to use this feature",
                Toast.LENGTH_SHORT
            ).show()
        }

        clItemViewPostUnregisteredUpvote.setOnClickListener {
            Toast.makeText(
                this@ViewPostUnregisteredActivity,
                "Log in or create an account to use this feature",
                Toast.LENGTH_SHORT
            ).show()
        }

        clItemViewPostUnregisteredComment.setOnClickListener {
            val intent = Intent(this, ViewCommentsUnregisteredActivity::class.java)
            startActivity(intent)
        }

        clItemViewPostUnregisteredShare.setOnClickListener {
            Toast.makeText(
                this@ViewPostUnregisteredActivity,
                "Log in or create an account to use this feature",
                Toast.LENGTH_SHORT
            ).show()
        }

        civItemViewPostUnregisteredProfilePic.setOnClickListener {
            val intent = Intent(this, ViewUserUnregisteredActivity::class.java)

            intent.putExtra(
                Keys.KEY_USERID.name,
                userIdPost
            )

            startActivity(intent)
        }

        tvItemViewPostUnregisteredUsername.setOnClickListener {
            val intent = Intent(this, ViewUserUnregisteredActivity::class.java)

            intent.putExtra(
                Keys.KEY_USERID.name,
                userIdPost
            )

            startActivity(intent)
        }
    }

    private fun initComponents() {
        setSupportActionBar(findViewById(R.id.toolbar_view_post_unregistered))
        initActionBar()
        initBottom()
    }

    private fun initBottom() {
        this.bnvViewPostUnregisteredBottom = findViewById(R.id.nv_view_post_unregistered_bottom)
        this.fabAddPost = findViewById(R.id.fab_view_post_unregistered_add)

        BottomMenuUtil.setFinishBottomMenuListenersUnregistered(bnvViewPostUnregisteredBottom, this,
            this@ViewPostUnregisteredActivity)

        fabAddPost.setOnClickListener {
            Toast.makeText(
                this@ViewPostUnregisteredActivity,
                "Log in or create an account to use this feature",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    private fun initActionBar() {
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
    }

    /**
     * This hook is called whenever an item in your options menu is selected.
     *
     * @param item The menu item that was selected. This value cannot be <code>null</code>.
     * @return Return false to allow normal menu processing to proceed, true to consume it here.
     */
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId) {
            android.R.id.home -> {
                onBackPressed()
                true
            } else -> {
                super.onOptionsItemSelected(item)
            }
        }
    }
}