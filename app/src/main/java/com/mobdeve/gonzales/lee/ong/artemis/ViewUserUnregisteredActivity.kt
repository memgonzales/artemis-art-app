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
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import de.hdodenhof.circleimageview.CircleImageView

class ViewUserUnregisteredActivity : AppCompatActivity() {
    private lateinit var civViewUserUnregisteredProfilePicture: CircleImageView
    private lateinit var tvViewUserUnregisteredUsername: TextView
    private lateinit var tvViewUserUnregisteredBio: TextView
    private lateinit var btnViewUserUnregisteredFollow: Button
    private lateinit var bnvViewUserUnregisteredBottom: BottomNavigationView
    private lateinit var dataHighlights: ArrayList<Post>
    private lateinit var rvViewUnregisteredUser: RecyclerView
    private lateinit var unregisteredHighlightAdapter: OthersHighlightAdapterUnregistered

    private lateinit var fabAddPost: FloatingActionButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_user_unregistered)

        initContent()
        initComponents()
    }

    private fun initContent() {
        this.civViewUserUnregisteredProfilePicture = findViewById(R.id.civ_view_user_unregistered_logo)
        this.tvViewUserUnregisteredUsername = findViewById(R.id.tv_view_user_unregistered_username)
        this.tvViewUserUnregisteredBio = findViewById(R.id.tv_view_user_unregistered_bio)
        this.btnViewUserUnregisteredFollow = findViewById(R.id.btn_view_user_unregistered_follow)
        this.dataHighlights = DataHelper.loadOthersHighlightData()

        val intent: Intent = intent
        val profilePicture = intent.getIntExtra(Keys.KEY_PROFILE_PICTURE.name, 0)
        val username = intent.getStringExtra(Keys.KEY_USERNAME.name)
        val bio = intent.getStringExtra(Keys.KEY_BIO.name)

        this.civViewUserUnregisteredProfilePicture.setImageResource(profilePicture)
        this.tvViewUserUnregisteredUsername.text = username
        this.tvViewUserUnregisteredBio.text = bio

        btnViewUserUnregisteredFollow.setOnClickListener(View.OnClickListener {
            Toast.makeText(this@ViewUserUnregisteredActivity, "Log in or create an account to use this feature", Toast.LENGTH_SHORT).show()
        })
    }

    private fun initComponents() {
        setSupportActionBar(findViewById(R.id.toolbar_view_user_unregistered))
        initActionBar()
        initRecyclerView()
        initBottom()
    }

    private fun initRecyclerView() {
        this.rvViewUnregisteredUser = findViewById(R.id.rv_view_user_unregistered);
        this.rvViewUnregisteredUser.layoutManager = GridLayoutManager(this, 2);

        this.unregisteredHighlightAdapter = OthersHighlightAdapterUnregistered(this.dataHighlights);

        this.rvViewUnregisteredUser.adapter = unregisteredHighlightAdapter;
    }

    private fun initBottom() {
        this.bnvViewUserUnregisteredBottom = findViewById(R.id.nv_view_user_unregistered_bottom)
        this.fabAddPost = findViewById(R.id.fab_view_user_unregistered_add)

        bnvViewUserUnregisteredBottom.setOnItemSelectedListener{ item ->
            when (item.itemId) {
                R.id.icon_home_feed -> {
                    val intent = Intent(this@ViewUserUnregisteredActivity, BrowseFeedUnregisteredActivity::class.java)
                    startActivity(intent)
                    return@setOnItemSelectedListener true
                }
                R.id.icon_follow_feed -> {
                    Toast.makeText(this@ViewUserUnregisteredActivity, "Log in or create an account to use this feature", Toast.LENGTH_SHORT).show()
                }
                R.id.icon_bookmark_feed -> {
                    Toast.makeText(this@ViewUserUnregisteredActivity, "Log in or create an account to use this feature", Toast.LENGTH_SHORT).show()
                }
                R.id.icon_user_feed -> {
                    Toast.makeText(this@ViewUserUnregisteredActivity, "Log in or create an account to use this feature", Toast.LENGTH_SHORT).show()
                }
            }
            false
        }

        fabAddPost.setOnClickListener(View.OnClickListener {
            Toast.makeText(this@ViewUserUnregisteredActivity,"Log in or create an account to use this feature", Toast.LENGTH_SHORT).show()
        })
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