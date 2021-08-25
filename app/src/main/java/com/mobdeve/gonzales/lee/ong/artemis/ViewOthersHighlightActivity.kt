package com.mobdeve.gonzales.lee.ong.artemis

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.core.widget.NestedScrollView
import com.google.android.material.bottomnavigation.BottomNavigationView
import de.hdodenhof.circleimageview.CircleImageView

class ViewOthersHighlightActivity : AppCompatActivity() {
    private lateinit var civItemViewOthersHighlightProfilePic: CircleImageView
    private lateinit var tvItemViewOthersHighlightUsername: TextView
    private lateinit var ivItemViewOthersHighlightPost: ImageView
    private lateinit var tvItemViewOthersHighlightTitle: TextView
    private lateinit var tvItemViewOthersHighlightDatePosted: TextView
    private lateinit var tvItemViewOthersHighlightMedium: TextView
    private lateinit var tvItemViewOthersHighlightDimensions: TextView
    private lateinit var tvItemViewOthersHighlightDescription: TextView
    private lateinit var bnvViewOthersHighlightBottom: BottomNavigationView
    private lateinit var nsvViewOthersHighlight: NestedScrollView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_others_highlight)

        civItemViewOthersHighlightProfilePic = findViewById(R.id.civ_item_view_others_highlight_profile_pic)
        tvItemViewOthersHighlightUsername = findViewById(R.id.tv_item_view_others_highlight_username)
        ivItemViewOthersHighlightPost = findViewById(R.id.iv_item_view_others_highlight_post)
        tvItemViewOthersHighlightTitle = findViewById(R.id.tv_item_view_others_highlight_title)
        tvItemViewOthersHighlightDatePosted = findViewById(R.id.tv_item_view_others_highlight_date)
        tvItemViewOthersHighlightMedium = findViewById(R.id.tv_item_view_others_highlight_medium)
        tvItemViewOthersHighlightDimensions = findViewById(R.id.tv_item_view_others_highlight_dimen)
        tvItemViewOthersHighlightDescription = findViewById(R.id.tv_item_view_others_highlight_desc)

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

        this.civItemViewOthersHighlightProfilePic.setImageResource(profilePicture)
        this.tvItemViewOthersHighlightUsername.text = username
        this.ivItemViewOthersHighlightPost.setImageResource(post)
        this.tvItemViewOthersHighlightTitle.text = title
        this.tvItemViewOthersHighlightDatePosted.text = datePosted
        this.tvItemViewOthersHighlightMedium.text = type
        this.tvItemViewOthersHighlightDimensions.text = dimensions
        this.tvItemViewOthersHighlightDescription.text = description

        civItemViewOthersHighlightProfilePic.setOnClickListener(View.OnClickListener {
            val intent = Intent(this, ViewUserActivity::class.java)

            intent.putExtra(
                Keys.KEY_PROFILE_PICTURE.name,
                profilePicture
            )
            intent.putExtra(
                Keys.KEY_USERNAME.name,
                username
            )

            startActivity(intent)
        })

        tvItemViewOthersHighlightUsername.setOnClickListener(View.OnClickListener {
            val intent = Intent(this, ViewUserActivity::class.java)

            intent.putExtra(
                Keys.KEY_PROFILE_PICTURE.name,
                profilePicture
            )
            intent.putExtra(
                Keys.KEY_USERNAME.name,
                username
            )

            startActivity(intent)
        })
    }

    private fun initComponents() {
        setSupportActionBar(findViewById(R.id.toolbar_view_others_highlight))
        initActionBar()
    }

    private fun initBottom() {
        this.bnvViewOthersHighlightBottom = findViewById(R.id.nv_view_others_highlight_bottom)
        this.nsvViewOthersHighlight = findViewById(R.id.nsv_view_others_highlight)

        bnvViewOthersHighlightBottom.setOnItemSelectedListener{ item ->
            when (item.itemId) {
                R.id.icon_home_search_bottom -> {
                    val intent = Intent(this@ViewOthersHighlightActivity, BrowseFeedActivity::class.java)
                    startActivity(intent)
                    finish()
                    return@setOnItemSelectedListener true
                }
                R.id.icon_follow_search_bottom -> {
                    val intent = Intent(this@ViewOthersHighlightActivity, BrowseFeedFollowedActivity::class.java)
                    startActivity(intent)
                    finish()
                    return@setOnItemSelectedListener true
                }
                R.id.icon_bookmark_search_bottom -> {
                    val intent = Intent(this@ViewOthersHighlightActivity, BrowseBookmarksActivity::class.java)
                    startActivity(intent)
                    finish()
                    return@setOnItemSelectedListener true
                }
                R.id.icon_user_search_bottom -> {
                    val intent = Intent(this@ViewOthersHighlightActivity, ViewProfileActivity::class.java)
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
}