package com.mobdeve.gonzales.lee.ong.artemis

import android.content.Intent
import android.content.res.ColorStateList
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.widget.NestedScrollView
import com.google.android.material.bottomnavigation.BottomNavigationView
import de.hdodenhof.circleimageview.CircleImageView

class ViewOwnHighlightActivity : AppCompatActivity() {
    private lateinit var civItemViewOwnHighlightProfilePic: CircleImageView
    private lateinit var tvItemViewOwnHighlightUsername: TextView
    private lateinit var ivItemViewOwnHighlightPost: ImageView
    private lateinit var tvItemViewOwnHighlightTitle: TextView
    private lateinit var tvItemViewOwnHighlightDatePosted: TextView
    private lateinit var tvItemViewOwnHighlightMedium: TextView
    private lateinit var tvItemViewOwnHighlightDimensions: TextView
    private lateinit var tvItemViewOwnHighlightDescription: TextView
    private lateinit var ibItemViewOwnHighlightHighlight: ImageButton
    private lateinit var bnvViewOwnHighlightBottom: BottomNavigationView
    private lateinit var nsvViewOwnHighlight: NestedScrollView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_own_highlight)

        civItemViewOwnHighlightProfilePic = findViewById(R.id.civ_item_view_own_highlight_profile_pic)
        tvItemViewOwnHighlightUsername = findViewById(R.id.tv_item_view_own_highlight_username)
        ivItemViewOwnHighlightPost = findViewById(R.id.iv_item_view_own_highlight_post)
        tvItemViewOwnHighlightTitle = findViewById(R.id.tv_item_view_own_highlight_title)
        tvItemViewOwnHighlightDatePosted = findViewById(R.id.tv_item_view_own_highlight_date)
        tvItemViewOwnHighlightMedium = findViewById(R.id.tv_item_view_own_highlight_medium)
        tvItemViewOwnHighlightDimensions = findViewById(R.id.tv_item_view_own_highlight_dimen)
        tvItemViewOwnHighlightDescription = findViewById(R.id.tv_item_view_own_highlight_desc)
        ibItemViewOwnHighlightHighlight = findViewById(R.id.ib_item_view_own_highlight_highlight)

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
        val dimHeight = intent.getIntExtra(Keys.KEY_DIM_HEIGHT.name, 0)
        val dimWidth = intent.getIntExtra(Keys.KEY_DIM_WIDTH.name, 0)
        val description = intent.getStringExtra(Keys.KEY_DESCRIPTION.name)
        var highlight = intent.getBooleanExtra(Keys.KEY_HIGHLIGHT.name, false)

        val dimensions = "$dimHeight x $dimWidth"

        this.civItemViewOwnHighlightProfilePic.setImageResource(profilePicture)
        this.tvItemViewOwnHighlightUsername.text = username
        this.ivItemViewOwnHighlightPost.setImageResource(post)
        this.tvItemViewOwnHighlightTitle.text = title
        this.tvItemViewOwnHighlightDatePosted.text = datePosted
        this.tvItemViewOwnHighlightMedium.text = type
        this.tvItemViewOwnHighlightDimensions.text = dimensions
        this.tvItemViewOwnHighlightDescription.text = description

        updateHighlight(highlight)

        ibItemViewOwnHighlightHighlight.setOnClickListener(View.OnClickListener {
            highlight = !highlight
            updateHighlight(highlight)
        })

        civItemViewOwnHighlightProfilePic.setOnClickListener(View.OnClickListener {
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

        tvItemViewOwnHighlightUsername.setOnClickListener(View.OnClickListener {
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
        setSupportActionBar(findViewById(R.id.toolbar_view_own_highlight))
        initActionBar()
    }

    private fun initBottom() {
        this.bnvViewOwnHighlightBottom = findViewById(R.id.nv_view_own_highlight_bottom)
        this.nsvViewOwnHighlight = findViewById(R.id.nsv_view_own_highlight)

        bnvViewOwnHighlightBottom.setOnItemSelectedListener{ item ->
            when (item.itemId) {
                R.id.icon_home_profile -> {
                    val intent = Intent(this@ViewOwnHighlightActivity, BrowseFeedActivity::class.java)
                    startActivity(intent)
                    finish()
                    return@setOnItemSelectedListener true
                }
                R.id.icon_follow_profile -> {
                    val intent = Intent(this@ViewOwnHighlightActivity, BrowseFeedFollowedActivity::class.java)
                    startActivity(intent)
                    finish()
                    return@setOnItemSelectedListener true
                }
                R.id.icon_bookmark_profile -> {
                    val intent = Intent(this@ViewOwnHighlightActivity, BrowseBookmarksActivity::class.java)
                    startActivity(intent)
                    finish()
                    return@setOnItemSelectedListener true
                }
                R.id.icon_user_profile -> {
                    val intent = Intent(this@ViewOwnHighlightActivity, ViewProfileActivity::class.java)
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

    private fun updateHighlight(highlight: Boolean) {
        if(highlight) {
            this.ibItemViewOwnHighlightHighlight.setImageResource(R.drawable.baseline_star_24)
            this.ibItemViewOwnHighlightHighlight.imageTintList = ColorStateList.valueOf(
                ContextCompat.getColor(this.ibItemViewOwnHighlightHighlight.context, R.color.pinkish_purple)
            )
        } else {
            this.ibItemViewOwnHighlightHighlight.setImageResource(R.drawable.outline_star_border_24)
            this.ibItemViewOwnHighlightHighlight.imageTintList = ColorStateList.valueOf(
                ContextCompat.getColor(this.ibItemViewOwnHighlightHighlight.context, R.color.default_gray)
            )
        }
    }
}