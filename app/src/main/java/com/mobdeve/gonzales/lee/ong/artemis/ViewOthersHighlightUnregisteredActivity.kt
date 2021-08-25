package com.mobdeve.gonzales.lee.ong.artemis

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.widget.NestedScrollView
import com.google.android.material.bottomnavigation.BottomNavigationView
import de.hdodenhof.circleimageview.CircleImageView

class ViewOthersHighlightUnregisteredActivity : AppCompatActivity() {
    private lateinit var civItemViewOthersHighlightUnregisteredProfilePic: CircleImageView
    private lateinit var tvItemViewOthersHighlightUnregisteredUsername: TextView
    private lateinit var ivItemViewOthersHighlightUnregisteredPost: ImageView
    private lateinit var tvItemViewOthersHighlightUnregisteredTitle: TextView
    private lateinit var tvItemViewOthersHighlightUnregisteredDatePosted: TextView
    private lateinit var tvItemViewOthersHighlightUnregisteredMedium: TextView
    private lateinit var tvItemViewOthersHighlightUnregisteredDimensions: TextView
    private lateinit var tvItemViewOthersHighlightUnregisteredDescription: TextView
    private lateinit var bnvViewOthersHighlightUnregisteredBottom: BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_others_highlight_unregistered)

        civItemViewOthersHighlightUnregisteredProfilePic = findViewById(R.id.civ_item_view_others_highlight_unregistered_profile_pic)
        tvItemViewOthersHighlightUnregisteredUsername = findViewById(R.id.tv_item_view_others_highlight_unregistered_username)
        ivItemViewOthersHighlightUnregisteredPost = findViewById(R.id.iv_item_view_others_highlight_unregistered_post)
        tvItemViewOthersHighlightUnregisteredTitle = findViewById(R.id.tv_item_view_others_highlight_unregistered_title)
        tvItemViewOthersHighlightUnregisteredDatePosted = findViewById(R.id.tv_item_view_others_highlight_unregistered_date)
        tvItemViewOthersHighlightUnregisteredMedium = findViewById(R.id.tv_item_view_others_highlight_unregistered_medium)
        tvItemViewOthersHighlightUnregisteredDimensions = findViewById(R.id.tv_item_view_others_highlight_unregistered_dimen)
        tvItemViewOthersHighlightUnregisteredDescription = findViewById(R.id.tv_item_view_others_highlight_unregistered_desc)

        initIntent()
        initComponents()
        initBottom()
    }

    private fun initIntent() {
        val intent: Intent = intent

        val profilePicture = intent.getIntExtra(Keys.KEY_PROFILE_PICTURE.name, 0)
        val username = intent.getStringExtra(Keys.KEY_USERNAME.name)
        val post = intent.getIntExtra(Keys.KEY_POST.name, 0)
        val title = intent.getStringExtra(Keys.KEY_TITLE.name)
        val datePosted = intent.getStringExtra(Keys.KEY_DATE_POSTED.name)
        val type = intent.getStringExtra(Keys.KEY_MEDIUM.name)
        val dimensions = intent.getStringExtra(Keys.KEY_DIMENSIONS.name)
        val description = intent.getStringExtra(Keys.KEY_DESCRIPTION.name)

        this.civItemViewOthersHighlightUnregisteredProfilePic.setImageResource(profilePicture)
        this.tvItemViewOthersHighlightUnregisteredUsername.text = username
        this.ivItemViewOthersHighlightUnregisteredPost.setImageResource(post)
        this.tvItemViewOthersHighlightUnregisteredTitle.text = title
        this.tvItemViewOthersHighlightUnregisteredDatePosted.text = datePosted
        this.tvItemViewOthersHighlightUnregisteredMedium.text = type
        this.tvItemViewOthersHighlightUnregisteredDimensions.text = dimensions
        this.tvItemViewOthersHighlightUnregisteredDescription.text = description

        civItemViewOthersHighlightUnregisteredProfilePic.setOnClickListener(View.OnClickListener {
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

        tvItemViewOthersHighlightUnregisteredUsername.setOnClickListener(View.OnClickListener {
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
        setSupportActionBar(findViewById(R.id.toolbar_view_others_highlight_unregistered))
        initActionBar()
    }

    private fun initBottom() {
        this.bnvViewOthersHighlightUnregisteredBottom = findViewById(R.id.nv_view_others_highlight_unregistered_bottom)

        bnvViewOthersHighlightUnregisteredBottom.setOnItemSelectedListener{ item ->
            when (item.itemId) {
                R.id.icon_home_feed -> {
                    val intent = Intent(this@ViewOthersHighlightUnregisteredActivity, BrowseFeedUnregisteredActivity::class.java)
                    startActivity(intent)
                    finish()
                    return@setOnItemSelectedListener true
                }
                R.id.icon_follow_feed -> {
                    Toast.makeText(this@ViewOthersHighlightUnregisteredActivity, "Log in or create an account to use this feature", Toast.LENGTH_SHORT).show()
                }
                R.id.icon_bookmark_feed -> {
                    Toast.makeText(this@ViewOthersHighlightUnregisteredActivity, "Log in or create an account to use this feature", Toast.LENGTH_SHORT).show()
                }
                R.id.icon_user_feed -> {
                    Toast.makeText(this@ViewOthersHighlightUnregisteredActivity, "Log in or create an account to use this feature", Toast.LENGTH_SHORT).show()
                }
            }
            false
        }
    }

    private fun initActionBar() {
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
    }
}