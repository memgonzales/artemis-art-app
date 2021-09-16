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
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import de.hdodenhof.circleimageview.CircleImageView

/**
 * Class handling the functionalities related to viewing a post for unregistered users.
 *
 * @constructor Creates a class that handles the functionalities related to viewing a post
 * for unregistered users.
 */
class ViewPostUnregisteredActivity : AppCompatActivity() {
    /**
     * Profile picture of the user whose post is being viewed.
     */
    private lateinit var civItemViewPostUnregisteredProfilePic: CircleImageView

    /**
     * Username of the user whose post is being viewed.
     */
    private lateinit var tvItemViewPostUnregisteredUsername: TextView

    /**
     * Artwork of the post being viewed.
     */
    private lateinit var ivItemViewPostUnregisteredPost: ImageView

    /**
     * Title of the post being viewed.
     */
    private lateinit var tvItemViewPostUnregisteredTitle: TextView

    /**
     * Number of upvotes of the post being viewed.
     */
    private lateinit var tvItemViewPostUnregisteredUpvoteCounter: TextView

    /**
     * Number of comments of the post being viewed.
     */
    private lateinit var tvItemViewPostUnregisteredComments: TextView

    /**
     * Date posted of the post being viewed.
     */
    private lateinit var tvItemViewPostUnregisteredDatePosted: TextView

    /**
     * Medium of the artwork being viewed.
     */
    private lateinit var tvItemViewPostUnregisteredMedium: TextView

    /**
     * Dimensions of the artwork being viewed.
     */
    private lateinit var tvItemViewPostUnregisteredDimensions: TextView

    /**
     * Description of the post being viewed.
     */
    private lateinit var tvItemViewPostUnregisteredDescription: TextView

    /**
     * Tags of the post being viewed.
     */
    private lateinit var tvItemViewPostUnregisteredTags: TextView

    /**
     * Image button holding the bookmark icon.
     */
    private lateinit var ibItemViewPostUnregisteredBookmark: ImageButton

    /**
     * Constraint layout holding the upvote option.
     */
    private lateinit var clItemViewPostUnregisteredUpvote: ConstraintLayout

    /**
     * Constraint layout holding the comment option.
     */
    private lateinit var clItemViewPostUnregisteredComment: ConstraintLayout

    /**
     * Constraint layout holding the share option.
     */
    private lateinit var clItemViewPostUnregisteredShare: ConstraintLayout

    /**
     * Bottom navigation view containing the menu items for Home, Followed, Bookmarks, and Profile.
     */
    private lateinit var bnvViewPostUnregisteredBottom: BottomNavigationView

    /**
     * Floating action button for posting an artwork.
     */
    private lateinit var fabAddPost: FloatingActionButton

    /**
     * Layout for registering a swipe gesture as a request to refresh this activity.
     */
    private lateinit var srlViewPostUnregistered: SwipeRefreshLayout

    /**
     * Object for accessing the Firebase helper methods.
     */
    private lateinit var firebaseHelper: FirebaseHelper

    /**
     * Called when the activity is starting.
     *
     * @param savedInstanceState  If the activity is being re-initialized after previously being
     * shut down then this Bundle contains the data it most recently supplied in
     * <code>onSaveInstanceState(Bundle)</code>. Note: Otherwise it is <code>null</code>.
     * This value may be <code>null</code>.
     */
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

    /**
     * Initializes the swipe refresh layout and defines the behavior when the screen is swiped
     * to refresh.
     */
    private fun initSwipeRefresh() {
        this.srlViewPostUnregistered = findViewById(R.id.srl_view_post_unregistered)
        srlViewPostUnregistered.setOnRefreshListener {
            onRefresh()
        }

        srlViewPostUnregistered.setColorSchemeResources(R.color.purple_main,
            R.color.pinkish_purple,
            R.color.purple_pics_lighter,
            R.color.pinkish_purple_lighter)
    }

    /**
     * Refetches data from the database and reshuffles the display of existing data when the screen
     * is swiped to refresh.
     */
    private fun onRefresh() {
        Handler(Looper.getMainLooper()).postDelayed({
            initIntent()
            srlViewPostUnregistered.isRefreshing = false
        }, AnimationDuration.REFRESH_TIMEOUT.toLong())
    }

    /**
     * Initializes the intent passed to the activity.
     */
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
            .diskCacheStrategy(DiskCacheStrategy.NONE)
            .placeholder(R.drawable.chibi_artemis_hd)
            .error(R.drawable.chibi_artemis_hd)
            .into(this.civItemViewPostUnregisteredProfilePic)

        this.tvItemViewPostUnregisteredUsername.text = username
        //this.ivItemViewPostUnregisteredPost.setImageResource(post)
        Glide.with(this)
            .load(postImg)
            .diskCacheStrategy(DiskCacheStrategy.NONE)
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
            intent.putExtra(
                Keys.KEY_POSTID.name,
                postId
            )
            intent.putExtra(
                Keys.KEY_NUM_COMMENTS.name,
                comments
            )
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

    /**
     * Initializes the components of the activity.
     */
    private fun initComponents() {
        setSupportActionBar(findViewById(R.id.toolbar_view_post_unregistered))
        initActionBar()
        initBottom()
    }

    /**
     * Sets the listeners for the menu selection found in the bottom navigation view.
     */
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

    /**
     * Adds a back button to the action bar.
     */
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