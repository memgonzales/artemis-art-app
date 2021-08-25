package com.mobdeve.gonzales.lee.ong.artemis

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.View
import android.widget.*
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.widget.NestedScrollView
import com.google.android.material.bottomnavigation.BottomNavigationView
import de.hdodenhof.circleimageview.CircleImageView

class ViewProfileActivity : AppCompatActivity() {
    private lateinit var civViewProfileProfilePicture: CircleImageView
    private lateinit var tvViewProfileUsername: TextView
    private lateinit var tvViewProfileBio: TextView
    private lateinit var clViewProfileEdit: ConstraintLayout
    private lateinit var clViewProfileViewPosts: ConstraintLayout
    private lateinit var clViewProfileDelete: ConstraintLayout
    private lateinit var btnViewProfileHighlights: Button
    private lateinit var bnvViewProfileBottom: BottomNavigationView
    private lateinit var nsvViewProfile: NestedScrollView
    private lateinit var dataUser: User

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_profile)

        initContent()
        initComponents()
    }

    private fun initContent() {
        this.civViewProfileProfilePicture = findViewById(R.id.civ_view_profile_logo)
        this.tvViewProfileUsername = findViewById(R.id.tv_view_user_unregistered_username)
        this.tvViewProfileBio = findViewById(R.id.tv_view_user_unregistered_bio)
        this.clViewProfileEdit = findViewById(R.id.cl_view_profile_edit)
        this.clViewProfileViewPosts = findViewById(R.id.cl_view_profile_view_posts)
        this.clViewProfileDelete = findViewById(R.id.cl_view_profile_delete)
        this.btnViewProfileHighlights = findViewById(R.id.btn_view_profile_highlights)
        this.bnvViewProfileBottom = findViewById(R.id.nv_view_profile_bottom)
        this.nsvViewProfile = findViewById(R.id.nsv_view_profile)
        this.dataUser = DataHelper.loadProfileData()

        this.civViewProfileProfilePicture.setImageResource(dataUser.getUserImg())
        this.tvViewProfileUsername.text = dataUser.getUsername()
        this.tvViewProfileBio.text = dataUser.getBio()

        clViewProfileEdit.setOnClickListener(View.OnClickListener {
            val intent = Intent(this@ViewProfileActivity, EditProfileActivity::class.java)
            startActivity(intent)
        })

        clViewProfileViewPosts.setOnClickListener(View.OnClickListener {
            val intent = Intent(this@ViewProfileActivity, BrowseOwnPostsActivity::class.java)
            startActivity(intent)
        })

        clViewProfileDelete.setOnClickListener(View.OnClickListener {
            val intent = Intent(this@ViewProfileActivity, AccountManagementActivity::class.java)
            startActivity(intent)
        })

        btnViewProfileHighlights.setOnClickListener(View.OnClickListener {
            val intent = Intent(this@ViewProfileActivity, BrowseOwnHighlightsActivity::class.java)
            startActivity(intent)
        })
    }

    private fun initComponents() {
        setSupportActionBar(findViewById(R.id.toolbar_view_profile))
        initBottom()
    }

    private fun initBottom() {
        bnvViewProfileBottom.setOnItemSelectedListener{ item ->
            when (item.itemId) {
                R.id.icon_home_profile -> {
                    val intent = Intent(this@ViewProfileActivity, BrowseFeedActivity::class.java)
                    startActivity(intent)
                    return@setOnItemSelectedListener true
                }
                R.id.icon_follow_profile -> {
                    val intent = Intent(this@ViewProfileActivity, BrowseFeedFollowedActivity::class.java)
                    startActivity(intent)
                    return@setOnItemSelectedListener true
                }
                R.id.icon_bookmark_profile -> {
                    val intent = Intent(this@ViewProfileActivity, BrowseBookmarksActivity::class.java)
                    startActivity(intent)
                    return@setOnItemSelectedListener true
                }
                R.id.icon_user_profile -> {
                    nsvViewProfile.fullScroll(ScrollView.FOCUS_UP)
                    return@setOnItemSelectedListener true
                }
            }
            false
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.menu_top_with_search, menu)

        return true
    }
}