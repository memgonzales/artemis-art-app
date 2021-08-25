package com.mobdeve.gonzales.lee.ong.artemis

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.core.widget.NestedScrollView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomnavigation.BottomNavigationView
import de.hdodenhof.circleimageview.CircleImageView

class ViewUserActivity : AppCompatActivity() {
    private lateinit var civViewUserProfilePicture: CircleImageView
    private lateinit var tvViewUserUsername: TextView
    private lateinit var tvViewUserBio: TextView
    private lateinit var btnViewUserFollow: Button
    private lateinit var bnvViewUserBottom: BottomNavigationView
    private lateinit var nsvViewUser: NestedScrollView
    private lateinit var dataHighlights: ArrayList<Post>
    private lateinit var rvViewUser: RecyclerView
    private lateinit var highlightAdapter: OthersHighlightAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_user)

        initContent()
        initComponents()
    }

    private fun initContent() {
        this.civViewUserProfilePicture = findViewById(R.id.civ_view_user_logo)
        this.tvViewUserUsername = findViewById(R.id.tv_view_profile_username)
        this.tvViewUserBio = findViewById(R.id.tv_view_profile_bio)
        this.btnViewUserFollow = findViewById(R.id.btn_view_user_follow)
        this.bnvViewUserBottom = findViewById(R.id.nv_view_user_bottom)
        this.nsvViewUser = findViewById(R.id.nsv_view_user)
        this.dataHighlights = DataHelper.loadOthersHighlightData()

        val intent: Intent = intent
        val profilePicture = intent.getIntExtra(Keys.KEY_PROFILE_PICTURE.name, 0)
        val username = intent.getStringExtra(Keys.KEY_USERNAME.name)
        val bio = intent.getStringExtra(Keys.KEY_BIO.name)

        this.civViewUserProfilePicture.setImageResource(profilePicture)
        this.tvViewUserUsername.text = username
        this.tvViewUserBio.text = bio

        btnViewUserFollow.setOnClickListener(View.OnClickListener {
            Toast.makeText(this@ViewUserActivity, "User followed.", Toast.LENGTH_SHORT).show()
        })
    }

    private fun initComponents() {
        setSupportActionBar(findViewById(R.id.toolbar_view_user))
        initActionBar()
        initRecyclerView()
        initBottom()
    }

    private fun initRecyclerView() {
        this.rvViewUser = findViewById(R.id.rv_view_user);
        this.rvViewUser.layoutManager = GridLayoutManager(this, 2);

        this.highlightAdapter = OthersHighlightAdapter(this.dataHighlights);

        this.rvViewUser.adapter = highlightAdapter;
    }

    private fun initBottom() {
        bnvViewUserBottom.setOnItemSelectedListener{ item ->
            when (item.itemId) {
                R.id.icon_home_search_bottom -> {
                    val intent = Intent(this@ViewUserActivity, BrowseFeedActivity::class.java)
                    startActivity(intent)
                    return@setOnItemSelectedListener true
                }
                R.id.icon_follow_search_bottom -> {
                    val intent = Intent(this@ViewUserActivity, BrowseFeedFollowedActivity::class.java)
                    startActivity(intent)
                    return@setOnItemSelectedListener true
                }
                R.id.icon_bookmark_search_bottom -> {
                    val intent = Intent(this@ViewUserActivity, BrowseBookmarksActivity::class.java)
                    startActivity(intent)
                    return@setOnItemSelectedListener true
                }
                R.id.icon_user_search_bottom -> {
                    val intent = Intent(this@ViewUserActivity, ViewProfileActivity::class.java)
                    startActivity(intent)
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