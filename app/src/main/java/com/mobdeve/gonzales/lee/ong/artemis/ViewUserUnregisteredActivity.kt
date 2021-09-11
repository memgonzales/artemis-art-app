package com.mobdeve.gonzales.lee.ong.artemis

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.MenuItem
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.bumptech.glide.Glide
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
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

    private lateinit var srlViewUserUnregistered: SwipeRefreshLayout

    private lateinit var fabAddPost: FloatingActionButton

    private lateinit var mAuth: FirebaseAuth
    private lateinit var db: DatabaseReference

    private lateinit var user: FirebaseUser
    private lateinit var userId: String

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
        setContentView(R.layout.activity_view_user_unregistered)

        initFirebase()
        initContent()
        initComponents()
    }

    /**
     * Initializes the Firebase-related components.
     */
    private fun initFirebase(){
        this.mAuth = Firebase.auth
        this.db = Firebase.database.reference

        if (this.mAuth.currentUser != null){
            this.user = this.mAuth.currentUser!!
            this.userId = this.user.uid
        }

        else{
            val intent = Intent(this@ViewUserUnregisteredActivity, BrokenLinkActivity::class.java)
            startActivity(intent)
        }
    }

    private fun initContent() {
        this.civViewUserUnregisteredProfilePicture = findViewById(R.id.civ_view_user_unregistered_logo)
        this.tvViewUserUnregisteredUsername = findViewById(R.id.tv_view_user_unregistered_username)
        this.tvViewUserUnregisteredBio = findViewById(R.id.tv_view_user_unregistered_bio)
        this.btnViewUserUnregisteredFollow = findViewById(R.id.btn_view_user_unregistered_follow)
        this.dataHighlights = DataHelper.loadOthersHighlightData()

        val intent: Intent = intent
        val userIdPost = intent.getStringExtra(Keys.KEY_USERID.name).toString()

        /*
        val intent: Intent = intent
        val profilePicture = intent.getIntExtra(Keys.KEY_PROFILE_PICTURE.name, 0)
        val username = intent.getStringExtra(Keys.KEY_USERNAME.name)
        val bio = intent.getStringExtra(Keys.KEY_BIO.name)

         */

        this.firebaseHelper = FirebaseHelper(this@ViewUserUnregisteredActivity, userIdPost)

        val userDB = this.db.child(Keys.KEY_DB_USERS.name)

        if(!userIdPost.isEmpty()){
            userDB.child(userIdPost).addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val userInfoPost = snapshot.getValue(User::class.java)

                    if(userInfoPost != null){
                        Glide.with(this@ViewUserUnregisteredActivity)
                            .load(userInfoPost.getUserImg())
                            .placeholder(R.drawable.chibi_artemis_hd)
                            .error(R.drawable.chibi_artemis_hd)
                            .into(civViewUserUnregisteredProfilePicture)

                        tvViewUserUnregisteredUsername.text = userInfoPost.getUsername()
                        tvViewUserUnregisteredBio.text = userInfoPost.getBio()

                        val highlights = userInfoPost.getHighlights().keys
                        getPosts(highlights)
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    val intent = Intent(this@ViewUserUnregisteredActivity, BrokenLinkActivity::class.java)
                    startActivity(intent)
                }

            })
        }

        //this.civViewUserUnregisteredProfilePicture.setImageResource(profilePicture)
        //this.tvViewUserUnregisteredUsername.text = username
        //this.tvViewUserUnregisteredBio.text = bio

        btnViewUserUnregisteredFollow.setOnClickListener {
            Toast.makeText(
                this@ViewUserUnregisteredActivity,
                "Log in or create an account to use this feature",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    private fun getPosts(highlights: Set<String?>){

        this.dataHighlights = arrayListOf<Post>()
        val postDB = this.db.child(Keys.KEY_DB_POSTS.name)

        postDB.addListenerForSingleValueEvent(object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {

                if (snapshot.exists()){
                    for (postSnap in snapshot.children){
                        if (postSnap.key != null && highlights.contains(postSnap.key)){
                            val post = postSnap.getValue(Post::class.java)

                            if(post != null){
                                post.setBookmark(true)
                                dataHighlights.add(post)
                            }

                        }
                    }

                    //highlightAdapter.notifyDataSetChanged()
                    unregisteredHighlightAdapter = OthersHighlightAdapterUnregistered(dataHighlights)
                    rvViewUnregisteredUser.adapter = unregisteredHighlightAdapter

                }
            }

            override fun onCancelled(error: DatabaseError) {
                val intent = Intent(this@ViewUserUnregisteredActivity, BrokenLinkActivity::class.java)
                startActivity(intent)
            }

        })
    }

    /**
     * Initializes the components of the activity.
     */
    private fun initComponents() {
        setSupportActionBar(findViewById(R.id.toolbar_view_user_unregistered))
        initActionBar()
        initRecyclerView()
        initBottom()
        initSwipeRefresh()
    }

    /**
     * Initializes the recycler view of the activity.
     */
    private fun initRecyclerView() {
        this.rvViewUnregisteredUser = findViewById(R.id.rv_view_user_unregistered)
        this.rvViewUnregisteredUser.layoutManager = GridLayoutManager(this, 2)

       // this.unregisteredHighlightAdapter = OthersHighlightAdapterUnregistered(this.dataHighlights)

       // this.rvViewUnregisteredUser.adapter = unregisteredHighlightAdapter
    }

    /**
     * Initializes the swipe refresh layout and defines the behavior when the screen is swiped
     * to refresh.
     */
    private fun initSwipeRefresh() {
        this.srlViewUserUnregistered = findViewById(R.id.srl_view_user_unregistered)
        srlViewUserUnregistered.setOnRefreshListener {
            onRefresh()
        }

        srlViewUserUnregistered.setColorSchemeResources(R.color.purple_main,
            R.color.pinkish_purple,
            R.color.purple_pics_lighter,
            R.color.pinkish_purple_lighter)
    }

    /**
     * Re-fetches data from the database and reshuffles the display of existing data when the screen
     * is swiped to refresh.
     */
    private fun onRefresh() {
        Handler(Looper.getMainLooper()).postDelayed({
            srlViewUserUnregistered.isRefreshing = false
        }, AnimationDuration.REFRESH_TIMEOUT.toLong())
    }

    /**
     * Sets the listeners for the menu selection found in the bottom navigation view.
     */
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

        fabAddPost.setOnClickListener {
            Toast.makeText(
                this@ViewUserUnregisteredActivity,
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