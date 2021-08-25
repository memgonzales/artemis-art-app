package com.mobdeve.gonzales.lee.ong.artemis

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
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
    }

    private fun initIntent() {
        val intent: Intent = intent

        val profilePicture = intent.getIntExtra(Keys.KEY_PROFILE_PICTURE.name, 0)
        val username = intent.getStringExtra(Keys.KEY_USERNAME.name)
        val post = intent.getIntExtra(Keys.KEY_POST.name, 0)
        val title = intent.getStringExtra(Keys.KEY_TITLE.name)
        var upvoteCounter = intent.getIntExtra(Keys.KEY_NUM_UPVOTES.name, 0)
        val comments = intent.getIntExtra(Keys.KEY_NUM_COMMENTS.name, 0)
        val datePosted = intent.getStringExtra(Keys.KEY_DATE_POSTED.name)
        val medium = intent.getStringExtra(Keys.KEY_MEDIUM.name)
        val dimensions = intent.getStringExtra(Keys.KEY_DIMENSIONS.name)
        val description = intent.getStringExtra(Keys.KEY_DESCRIPTION.name)
        val tags = intent.getStringArrayListExtra(Keys.KEY_TAGS.name)
        var bookmark = intent.getBooleanExtra(Keys.KEY_BOOKMARK.name, false)
        var upvote = intent.getBooleanExtra(Keys.KEY_UPVOTE.name, false)

        var upvoteString = "$upvoteCounter upvotes"
        val commentString = "$comments comments"
        val tagsString = tags?.joinToString(", ")

        this.civItemViewPostUnregisteredProfilePic.setImageResource(profilePicture)
        this.tvItemViewPostUnregisteredUsername.text = username
        this.ivItemViewPostUnregisteredPost.setImageResource(post)
        this.tvItemViewPostUnregisteredTitle.text = title
        this.tvItemViewPostUnregisteredUpvoteCounter.text = upvoteString
        this.tvItemViewPostUnregisteredComments.text = commentString
        this.tvItemViewPostUnregisteredDatePosted.text = datePosted
        this.tvItemViewPostUnregisteredMedium.text = medium
        this.tvItemViewPostUnregisteredDimensions.text = dimensions
        this.tvItemViewPostUnregisteredDescription.text = description
        this.tvItemViewPostUnregisteredTags.text = tagsString

        ibItemViewPostUnregisteredBookmark.setOnClickListener(View.OnClickListener {
            Toast.makeText(this@ViewPostUnregisteredActivity,"Log in or create an account to use this feature", Toast.LENGTH_SHORT).show()
        })

        clItemViewPostUnregisteredUpvote.setOnClickListener(View.OnClickListener {
            Toast.makeText(this@ViewPostUnregisteredActivity,"Log in or create an account to use this feature", Toast.LENGTH_SHORT).show()
        })

        clItemViewPostUnregisteredComment.setOnClickListener(View.OnClickListener {
            val intent = Intent(this, ViewCommentsUnregisteredActivity::class.java)
            startActivity(intent)
        })

        clItemViewPostUnregisteredShare.setOnClickListener(View.OnClickListener {
            Toast.makeText(this@ViewPostUnregisteredActivity,"Log in or create an account to use this feature", Toast.LENGTH_SHORT).show()
        })

        civItemViewPostUnregisteredProfilePic.setOnClickListener(View.OnClickListener {
            val intent = Intent(this, ViewUserUnregisteredActivity::class.java)

            intent.putExtra(
                Keys.KEY_PROFILE_PICTURE.name,
                profilePicture
            )
            intent.putExtra(
                Keys.KEY_USERNAME.name,
                username
            )
            intent.putExtra(
                Keys.KEY_BIO.name,
                "Dummy bio"
            )

            startActivity(intent)
        })

        tvItemViewPostUnregisteredUsername.setOnClickListener(View.OnClickListener {
            val intent = Intent(this, ViewUserUnregisteredActivity::class.java)

            intent.putExtra(
                Keys.KEY_PROFILE_PICTURE.name,
                profilePicture
            )
            intent.putExtra(
                Keys.KEY_USERNAME.name,
                username
            )
            intent.putExtra(
                Keys.KEY_BIO.name,
                "Dummy bio"
            )

            startActivity(intent)
        })
    }

    private fun initComponents() {
        setSupportActionBar(findViewById(R.id.toolbar_view_post))
        initActionBar()
        initBottom()
    }

    private fun initBottom() {
        this.bnvViewPostUnregisteredBottom = findViewById(R.id.nv_view_post_unregistered_bottom)

        bnvViewPostUnregisteredBottom.setOnItemSelectedListener{ item ->
            when (item.itemId) {
                R.id.icon_home_feed -> {
                    val intent = Intent(this@ViewPostUnregisteredActivity, BrowseFeedUnregisteredActivity::class.java)
                    startActivity(intent)
                    finish()
                    return@setOnItemSelectedListener true
                }
                R.id.icon_follow_feed -> {
                    Toast.makeText(this@ViewPostUnregisteredActivity,"Log in or create an account to use this feature", Toast.LENGTH_SHORT).show()
                }
                R.id.icon_bookmark_feed -> {
                    Toast.makeText(this@ViewPostUnregisteredActivity,"Log in or create an account to use this feature", Toast.LENGTH_SHORT).show()
                }
                R.id.icon_user_feed -> {
                    Toast.makeText(this@ViewPostUnregisteredActivity,"Log in or create an account to use this feature", Toast.LENGTH_SHORT).show()
                }
            }
            false
        }
    }

    private fun initActionBar() {
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId

        when(id) {
            android.R.id.home -> {
                onBackPressed()
                return true
            } else -> {
            return super.onOptionsItemSelected(item)
            }
        }

        return super.onOptionsItemSelected(item)
    }
}