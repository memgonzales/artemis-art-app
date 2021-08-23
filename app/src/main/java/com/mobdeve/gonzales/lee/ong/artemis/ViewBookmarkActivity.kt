package com.mobdeve.gonzales.lee.ong.artemis

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import de.hdodenhof.circleimageview.CircleImageView

class ViewBookmarkActivity : AppCompatActivity() {
    private lateinit var civItemViewBookmarkProfilePic: CircleImageView
    private lateinit var tvItemViewBookmarkUsername: TextView
    private lateinit var ivItemViewBookmarkPost: ImageView
    private lateinit var tvItemViewBookmarkTitle: TextView
    private lateinit var tvItemViewBookmarkDatePosted: TextView
    private lateinit var tvItemViewBookmarkMedium: TextView
    private lateinit var tvItemViewBookmarkDimensions: TextView
    private lateinit var tvItemViewBookmarkDescription: TextView
    private lateinit var ibItemViewBookmarkBookmark: ImageButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_bookmark)

        civItemViewBookmarkProfilePic = findViewById(R.id.civ_item_view_bookmark_profile_pic)
        tvItemViewBookmarkUsername = findViewById(R.id.tv_item_view_bookmark_username)
        ivItemViewBookmarkPost = findViewById(R.id.iv_item_view_bookmark_post)
        tvItemViewBookmarkTitle = findViewById(R.id.tv_item_view_bookmark_title)
        tvItemViewBookmarkDatePosted = findViewById(R.id.tv_item_view_bookmark_date)
        tvItemViewBookmarkMedium = findViewById(R.id.tv_item_view_bookmark_medium)
        tvItemViewBookmarkDimensions = findViewById(R.id.tv_item_view_bookmark_dimen)
        tvItemViewBookmarkDescription = findViewById(R.id.tv_item_view_bookmark_desc)
        ibItemViewBookmarkBookmark = findViewById(R.id.ib_item_view_bookmark_bookmark)

        initIntent()
        initComponents()
    }

    private fun initIntent() {
        val intent: Intent = intent

        val profilePicture = intent.getIntExtra(Keys.KEY_PROFILE_PICTURE.name, 0)
        val username = intent.getStringExtra(Keys.KEY_USERNAME.name)
        val post = intent.getIntExtra(Keys.KEY_POST.name, 0)
        val title = intent.getStringExtra(Keys.KEY_TITLE.name)
        val datePosted = intent.getStringExtra(Keys.KEY_DATE_POSTED.name)
        val type = intent.getStringExtra(Keys.KEY_MEDIUM.name)
        val dimHeight = intent.getIntExtra(Keys.KEY_DIM_HEIGHT.name, 0)
        val dimWidth = intent.getIntExtra(Keys.KEY_DIM_WIDTH.name, 0)
        val description = intent.getStringExtra(Keys.KEY_DESCRIPTION.name)

        val dimensions = "$dimHeight x $dimWidth"

        this.civItemViewBookmarkProfilePic.setImageResource(profilePicture)
        this.tvItemViewBookmarkUsername.text = username
        this.ivItemViewBookmarkPost.setImageResource(post)
        this.tvItemViewBookmarkTitle.text = title
        this.tvItemViewBookmarkDatePosted.text = datePosted
        this.tvItemViewBookmarkMedium.text = type
        this.tvItemViewBookmarkDimensions.text = dimensions
        this.tvItemViewBookmarkDescription.text = description
    }

    private fun initComponents() {
        setSupportActionBar(findViewById(R.id.toolbar_view_bookmark))
        initActionBar()
    }

    private fun initActionBar() {
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
    }
}