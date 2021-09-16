package com.mobdeve.gonzales.lee.ong.artemis

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.ImageView
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
import com.google.firebase.database.*
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import de.hdodenhof.circleimageview.CircleImageView

/**
 * Class handling the functionalities related to viewing another user's profile for unregistered users.
 *
 * @constructor Creates a class that handles the functionalities related to viewing another
 * user's profile for unregistered users.
 */
class ViewUserUnregisteredActivity : AppCompatActivity() {
    /**
     * Profile picture of the user whose profile is being viewed.
     */
    private lateinit var civViewUserUnregisteredProfilePicture: CircleImageView

    /**
     * Username of the user whose profile is being viewed.
     */
    private lateinit var tvViewUserUnregisteredUsername: TextView

    /**
     * Bio of the user whose profile is being viewed.
     */
    private lateinit var tvViewUserUnregisteredBio: TextView

    /**
     * Button for following a user.
     */
    private lateinit var btnViewUserUnregisteredFollow: Button

    /**
     * Bottom navigation view containing the menu items for Home, Followed, Bookmarks, and Profile.
     */
    private lateinit var bnvViewUserUnregisteredBottom: BottomNavigationView

    /**
     * List of highlights of the user whose profile is being viewed.
     */
    private lateinit var dataHighlights: ArrayList<Post>

    /**
     * Recycler view holding the highlights of the user whose profile is being viewed.
     */
    private lateinit var rvViewUnregisteredUser: RecyclerView

    /**
     * Adapter for displaying the highlights of the user whose profile is being viewed.
     */
    private lateinit var unregisteredHighlightAdapter: OthersHighlightAdapterUnregistered

    /**
     * Image view displayed when the user's highlights does not have any post to display.
     */
    private lateinit var ivNone: ImageView

    /**
     * First (main) text view displayed when the user's highlights does not have any post to display.
     */
    private lateinit var tvNone: TextView

    /**
     * Layout for registering a swipe gesture as a request to refresh this activity.
     */
    private lateinit var srlViewUserUnregistered: SwipeRefreshLayout

    /**
     * Floating action button for posting an artwork.
     */
    private lateinit var fabAddPost: FloatingActionButton

    /**
     * Starting point for Firebase authentication SDK.
     */
    private lateinit var mAuth: FirebaseAuth

    /**
     * Represents a user profile's information in the Firebase user database.
     */
    private lateinit var user: FirebaseUser

    /**
     * Unique identifier of the user.
     */
    private lateinit var userId: String

    /**
     * Starting point for all database-related operations.
     */
    private lateinit var db: DatabaseReference

    /**
     * Object for accessing the Firebase helper methods.
     */
    private lateinit var firebaseHelper: FirebaseHelper

    /**
     * Unique identifier of the post posted by the user.
     */
    private lateinit var userIdPost: String

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

    /**
     * Child event listener for handling the viewing of a user's highlights.
     */
    private var childEventListenerUserPost = object : ChildEventListener {
        override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
            val postId = snapshot.key.toString()

            if (postId != null){
                getPost(postId)
            }
        }

        override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {
            /* This is intentionally left blank */
        }

        override fun onChildRemoved(snapshot: DataSnapshot) {
            val postId = snapshot.key.toString()

            if (postId != null){

                val list = java.util.ArrayList<Post>(dataHighlights)

                val index = list.indexOfFirst { it.getPostId() == postId }

                if (index != -1){
                    list.removeAt(index)

                    dataHighlights = list
                    unregisteredHighlightAdapter.updatePosts(list)

                    if (dataHighlights.isNullOrEmpty()){
                        ivNone.visibility = View.VISIBLE
                        tvNone.visibility = View.VISIBLE
                    }
                }
            }
        }

        override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {
            /* This is intentionally left blank */
        }

        override fun onCancelled(error: DatabaseError) {
            val intent = Intent(this@ViewUserUnregisteredActivity, BrokenLinkActivity::class.java)
            startActivity(intent)
        }
    }

    /**
     * Initializes the contents of the activity.
     */
    private fun initContent() {
        this.ivNone = findViewById(R.id.iv_user_highlights_unregistered_none)
        this.tvNone = findViewById(R.id.tv_user_highlights_unregistered_none)

        this.civViewUserUnregisteredProfilePicture = findViewById(R.id.civ_view_user_unregistered_logo)
        this.tvViewUserUnregisteredUsername = findViewById(R.id.tv_view_user_unregistered_username)
        this.tvViewUserUnregisteredBio = findViewById(R.id.tv_view_user_unregistered_bio)
        this.btnViewUserUnregisteredFollow = findViewById(R.id.btn_view_user_unregistered_follow)
        this.dataHighlights = arrayListOf<Post>()

        val intent: Intent = intent
        this.userIdPost = intent.getStringExtra(Keys.KEY_USERID.name).toString()

        this.firebaseHelper = FirebaseHelper(this@ViewUserUnregisteredActivity, userIdPost)

        val userDB = this.db.child(Keys.KEY_DB_USERS.name)

        if(!userIdPost.isEmpty()){
            userDB.child(userIdPost).addListenerForSingleValueEvent(object : ValueEventListener{
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

                        if (userInfoPost.getHighlights().isNullOrEmpty()){
                            ivNone.visibility = View.VISIBLE
                            tvNone.visibility = View.VISIBLE
                        }
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    val intent = Intent(this@ViewUserUnregisteredActivity, BrokenLinkActivity::class.java)
                    startActivity(intent)
                }

            })
        }

        userDB.child(userIdPost).child(Keys.highlights.name).addChildEventListener(childEventListenerUserPost)

        btnViewUserUnregisteredFollow.setOnClickListener {
            Toast.makeText(
                this@ViewUserUnregisteredActivity,
                "Log in or create an account to use this feature",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    /**
     * Retrieves the post selected from the user's highlights.
     *
     * @param postId Unique identifier of the post to be displayed.
     */
    private fun getPost(postId: String){
        val postDB = this.db.child(Keys.KEY_DB_POSTS.name).child(postId)

        postDB.addListenerForSingleValueEvent(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()){
                    val post = snapshot.getValue(Post::class.java)
                    post?.setHighlight(true)

                    if (post != null){
                        dataHighlights.add(post)
                        unregisteredHighlightAdapter.updatePosts(dataHighlights)

                        ivNone.visibility = View.GONE
                        tvNone.visibility = View.GONE
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {
                val intent = Intent(this@ViewUserUnregisteredActivity, BrokenLinkActivity::class.java)
                startActivity(intent)
            }

        })
    }

    /**
     * Defines the behavior of the activity when it is paused.
     */
    override fun onPause() {
        val userDB = this.db.child(userIdPost).child(Keys.highlights.name)
        userDB.removeEventListener(childEventListenerUserPost)

        super.onPause()
    }

    /**
     * Defines the behavior of the activity when it is resumed.
     */
    override fun onResume() {
        super.onResume()

        initRecyclerView()
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
        this.dataHighlights = arrayListOf()
        this.rvViewUnregisteredUser = findViewById(R.id.rv_view_user_unregistered)
        this.rvViewUnregisteredUser.layoutManager = GridLayoutManager(this, 2)

        this.unregisteredHighlightAdapter = OthersHighlightAdapterUnregistered()

        this.rvViewUnregisteredUser.adapter = unregisteredHighlightAdapter
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
     * Refetches data from the database and reshuffles the display of existing data when the screen
     * is swiped to refresh.
     */
    private fun onRefresh() {
        Handler(Looper.getMainLooper()).postDelayed({
            initRecyclerView()
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