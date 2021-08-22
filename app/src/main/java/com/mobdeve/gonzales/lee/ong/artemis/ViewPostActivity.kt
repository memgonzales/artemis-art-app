package com.mobdeve.gonzales.lee.ong.artemis

import android.content.Intent
import android.content.res.ColorStateList
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import de.hdodenhof.circleimageview.CircleImageView

class ViewPostActivity : AppCompatActivity() {
    private lateinit var civItemViewPostProfilePic: CircleImageView
    private lateinit var tvItemViewPostUsername: TextView
    private lateinit var ivItemViewPostPost: ImageView
    private lateinit var tvItemViewPostTitle: TextView
    private lateinit var tvItemViewPostUpvoteCounter: TextView
    private lateinit var tvItemViewPostComments: TextView
    private lateinit var tvItemViewPostDatePosted: TextView
    private lateinit var tvItemViewPostType: TextView
    private lateinit var tvItemViewPostDimensions: TextView
    private lateinit var tvItemViewPostDescription: TextView
    private lateinit var tvItemViewPostTags: TextView
    private lateinit var ibItemViewPostBookmark: ImageButton
    private lateinit var ivItemViewPostUpvote: ImageView
    private lateinit var tvItemViewPostUpvote: TextView
    private lateinit var clItemViewPostUpvote: ConstraintLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_post)

        civItemViewPostProfilePic = findViewById(R.id.civ_item_view_post_profile_pic)
        tvItemViewPostUsername = findViewById(R.id.tv_item_view_post_username)
        ivItemViewPostPost = findViewById(R.id.iv_item_view_post_post)
        tvItemViewPostTitle = findViewById(R.id.tv_item_view_post_title)
        tvItemViewPostUpvoteCounter = findViewById(R.id.tv_item_view_post_upvote_counter)
        tvItemViewPostComments = findViewById(R.id.tv_item_view_post_comments)
        tvItemViewPostDatePosted = findViewById(R.id.tv_item_view_post_date)
        tvItemViewPostType = findViewById(R.id.tv_item_view_post_medium)
        tvItemViewPostDimensions = findViewById(R.id.tv_item_view_post_dimen)
        tvItemViewPostDescription = findViewById(R.id.tv_item_view_post_desc)
        tvItemViewPostTags = findViewById(R.id.tv_item_view_post_tags)
        ibItemViewPostBookmark = findViewById(R.id.ib_item_view_post_bookmark)
        ivItemViewPostUpvote = findViewById(R.id.iv_item_view_post_upvote)
        tvItemViewPostUpvote = findViewById(R.id.tv_item_view_post_upvote)
        clItemViewPostUpvote = findViewById(R.id.cl_item_view_post_upvote)

        initIntent()
        initComponents()
    }

    private fun initIntent() {
        val intent: Intent = intent

        val profilePicture = intent.getIntExtra(Keys.KEY_PROFILE_PICTURE.name, 0)
        val username = intent.getStringExtra(Keys.KEY_USERNAME.name)
        val post = intent.getIntExtra(Keys.KEY_POST.name, 0)
        val title = intent.getStringExtra(Keys.KEY_TITLE.name)
        var upvoteCounter = intent.getIntExtra(Keys.KEY_UPVOTES.name, 0)
        val comments = intent.getIntExtra(Keys.KEY_COMMENTS.name, 0)
        val datePosted = intent.getStringExtra(Keys.KEY_DATE_POSTED.name)
        val type = intent.getStringExtra(Keys.KEY_TYPE.name)
        val dimHeight = intent.getIntExtra(Keys.KEY_DIM_HEIGHT.name, 0)
        val dimWidth = intent.getIntExtra(Keys.KEY_DIM_WIDTH.name, 0)
        val description = intent.getStringExtra(Keys.KEY_DESCRIPTION.name)
        val tags = intent.getStringArrayExtra(Keys.KEY_TAGS.name)
        var bookmark = intent.getBooleanExtra(Keys.KEY_BOOKMARK.name, false)
        var upvote = intent.getBooleanExtra(Keys.KEY_UPVOTE.name, false)

        var upvoteString = "$upvoteCounter upvotes"
        val commentString = "$comments comments"
        val dimensions = "$dimHeight x $dimWidth"
        val tagsString = tags?.joinToString(", ")

        this.civItemViewPostProfilePic.setImageResource(profilePicture)
        this.tvItemViewPostUsername.text = username
        this.ivItemViewPostPost.setImageResource(post)
        this.tvItemViewPostTitle.text = title
        this.tvItemViewPostUpvoteCounter.text = upvoteString
        this.tvItemViewPostComments.text = commentString
        this.tvItemViewPostDatePosted.text = datePosted
        this.tvItemViewPostType.text = type
        this.tvItemViewPostDimensions.text = dimensions
        this.tvItemViewPostDescription.text = description
        this.tvItemViewPostTags.text = tagsString

        updateBookmark(bookmark)
        updateUpvote(upvote)

        ibItemViewPostBookmark.setOnClickListener(View.OnClickListener {
            bookmark = !bookmark
            updateBookmark(bookmark)
        })

        clItemViewPostUpvote.setOnClickListener(View.OnClickListener {
            if (upvote) {
                upvote = false
                upvoteCounter -= 1
                upvoteString = "$upvoteCounter upvotes"
                this.tvItemViewPostUpvoteCounter.text = upvoteString
                updateUpvote(upvote)
            } else {
                upvote = true
                upvoteCounter += 1
                upvoteString = "$upvoteCounter upvotes"
                this.tvItemViewPostUpvoteCounter.text = upvoteString
                updateUpvote(upvote)
            }
        })
    }

    private fun initComponents() {
        setSupportActionBar(findViewById(R.id.toolbar_view_post))
        initActionBar()
    }

    private fun initActionBar() {
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
    }

    private fun updateBookmark(bookmark: Boolean) {
        if(bookmark) {
            this.ibItemViewPostBookmark.setImageResource(R.drawable.outline_bookmark_24)
            this.ibItemViewPostBookmark.imageTintList = ColorStateList.valueOf(
                ContextCompat.getColor(this.ibItemViewPostBookmark.context, R.color.pinkish_purple)
            )
        } else {
            this.ibItemViewPostBookmark.setImageResource(R.drawable.outline_bookmark_border_24)
            this.ibItemViewPostBookmark.imageTintList = ColorStateList.valueOf(
                ContextCompat.getColor(this.ibItemViewPostBookmark.context, R.color.default_gray)
            )
        }
    }

    private fun updateUpvote(upvote: Boolean) {
        if (upvote) {
            ivItemViewPostUpvote.setImageResource(R.drawable.upvote_colored)
            ivItemViewPostUpvote.imageTintList = ColorStateList.valueOf(
                ContextCompat.getColor(ivItemViewPostUpvote.context, R.color.pinkish_purple)
            )
            tvItemViewPostUpvote.setTextColor(ColorStateList.valueOf(
                ContextCompat.getColor(tvItemViewPostUpvote.context, R.color.pinkish_purple)))
        } else {
            ivItemViewPostUpvote.setImageResource(R.drawable.upvote_v2)
            ivItemViewPostUpvote.imageTintList = ColorStateList.valueOf(
                ContextCompat.getColor(ivItemViewPostUpvote.context, R.color.default_gray)
            )
            tvItemViewPostUpvote.setTextColor(ColorStateList.valueOf(
                ContextCompat.getColor(tvItemViewPostUpvote.context, R.color.default_gray)))
        }
    }
}