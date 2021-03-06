package com.mobdeve.gonzales.lee.ong.artemis

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.NestedScrollView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.facebook.shimmer.ShimmerFrameLayout
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.*
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import java.util.*

/**
 * Class handling the functionalities related to browsing the posts (for unregistered users).
 *
 * @constructor Creates a class that handles the functionalities related to browsing the posts
 * (for unregistered users).
 */
class BrowseFeedUnregisteredActivity : AppCompatActivity() {
    /**
     * Posts to be displayed on the feed.
     */
    private lateinit var dataPosts: ArrayList<Post>

    /**
     * Recycler view for the posts to be displayed on the feed.
     */
    private lateinit var rvFeed: RecyclerView

    /**
     * Adapter for the recycler view handling the posts to be displayed on the feed.
     */
    private lateinit var unregisteredFeedAdapter: UnregisteredFeedAdapter

    /**
     * Shimmer layout displayed while data regarding the posts are being fetched
     * from the remote database.
     */
    private lateinit var sflFeed: ShimmerFrameLayout

    /**
     * Bottom navigation view containing the menu items for Home, Followed, Bookmarks, and Profile.
     */
    private lateinit var bnvFeedBottom: BottomNavigationView

    /**
     * Nested scroll view for the main layout of this activity.
     */
    private lateinit var nsvFeed: NestedScrollView

    /**
     * Layout for registering a swipe gesture as a request to refresh this activity.
     */
    private lateinit var srlFeedUnregistered: SwipeRefreshLayout

    /**
     * Floating action button for posting an artwork.
     */
    private lateinit var fabAddPost: FloatingActionButton

    /**
     * Image view displayed when the feed does not have any post to display.
     */
    private lateinit var ivNone: ImageView

    /**
     * First (main) text view displayed when the feed does not have any post to display.
     */
    private lateinit var tvNone: TextView

    /**
     * Second text view displayed displayed when the feed does not have any post to display.
     */
    private lateinit var tvSubNone: TextView

    /**
     * Starting point for Firebase authentication SDK.
     */
    private lateinit var mAuth: FirebaseAuth

    /**
     * Starting point for all database-related operations.
     */
    private lateinit var db: DatabaseReference

    /**
     * Represents a user profile's information in the Firebase user database.
     */
    private lateinit var user: FirebaseUser

     /**
     * Unique identifier of the user.
     */
    private lateinit var userId: String

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
        setContentView(R.layout.activity_browse_feed_unregistered)

        initFirebase()
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
            val intent = Intent(this@BrowseFeedUnregisteredActivity, BrokenLinkActivity::class.java)
            startActivity(intent)
        }
    }

    /**
     * Initializes the components of the activity.
     */
    private fun initComponents() {
        setSupportActionBar(findViewById(R.id.toolbar_feed_unregistered))
        initShimmer()
        initBottom()
        initActionBar()
        initSwipeRefresh()

        addPost()
    }

    /**
     * Initializes the shimmer layout animated while the data are being fetched from the remote server.
     */
    private fun initShimmer() {
        this.sflFeed = findViewById(R.id.sfl_feed_unregistered)

        sflFeed.startShimmer()

        Handler(Looper.getMainLooper()).postDelayed({
            initRecyclerView()
            sflFeed.visibility = View.GONE
            rvFeed.visibility = View.VISIBLE
        }, AnimationDuration.SHIMMER_TIMEOUT.toLong())
    }

    /**
     * Initializes the swipe refresh layout and defines the behavior when the screen is swiped
     * to refresh.
     */
    private fun initSwipeRefresh() {
        this.srlFeedUnregistered = findViewById(R.id.srl_feed_unregistered)
        srlFeedUnregistered.setOnRefreshListener {
            onRefresh()
        }

        srlFeedUnregistered.setColorSchemeResources(R.color.purple_main,
            R.color.pinkish_purple,
            R.color.purple_pics_lighter,
            R.color.pinkish_purple_lighter)
    }

    /**
     * Initializes the swipe refresh layout and defines the behavior when the screen is swiped
     * to refresh.
     */
    private fun onRefresh() {
        Handler(Looper.getMainLooper()).postDelayed({
            initRecyclerView()

            srlFeedUnregistered.isRefreshing = false
        }, AnimationDuration.REFRESH_TIMEOUT.toLong())
    }

    /**
     * Adds a back button to the action bar.
     */
    private fun initActionBar() {
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
    }

    /**
     * Sets the listeners for the menu selection found in the bottom navigation view.
     */
    private fun initBottom() {
        this.bnvFeedBottom = findViewById(R.id.nv_feed_unregistered_bottom)
        this.nsvFeed = findViewById(R.id.nsv_feed_unregistered)

        BottomMenuUtil.setScrollBottomMenuListenersUnregistered(bnvFeedBottom,
            nsvFeed, this@BrowseFeedUnregisteredActivity)
    }

    /**
     * Initializes the recycler view of the activity.
     */
    private fun initRecyclerView() {
        this.dataPosts = arrayListOf()
        this.rvFeed = findViewById(R.id.rv_feed_unregistered)
        this.rvFeed.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, true)

        this.unregisteredFeedAdapter = UnregisteredFeedAdapter()


        this.rvFeed.adapter = unregisteredFeedAdapter

        this.rvFeed.itemAnimator = null

        getRealtimeUpdates()
    }

    private var childEventListener = object : ChildEventListener{
        override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
            val post = snapshot.getValue(Post::class.java)

            if (post != null && !post.getPostId().isNullOrEmpty() && !post.getUserId().isNullOrEmpty()){
                if (!post.getUpvoteUsers().isNullOrEmpty() && post.getUpvoteUsers().containsKey(userId)){
                    post.setUpvote(true)
                }
                else{
                    post.setUpvote(false)
                }

                if(!post.getBookmarkUsers().isNullOrEmpty() && post.getBookmarkUsers().containsKey(userId)){
                    post.setBookmark(true)
                }
                else{
                    post.setBookmark(false)
                }

                dataPosts.add(post)
                unregisteredFeedAdapter.updatePosts(dataPosts)

                ivNone.visibility = View.GONE
                tvNone.visibility = View.GONE
                tvSubNone.visibility = View.GONE
            }
        }

        override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {
            val post = snapshot.getValue(Post::class.java)

            if (post != null && !post.getPostId().isNullOrEmpty() && !post.getUserId().isNullOrEmpty()){

                if (!post.getUpvoteUsers().isNullOrEmpty()){
                    if(post.getUpvoteUsers().containsKey(userId)){
                        post.setUpvote(true)
                    }
                    else{
                        post.setUpvote(false)
                    }
                }

                if(!post.getBookmarkUsers().isNullOrEmpty()) {
                    if (post.getBookmarkUsers().containsKey(userId)){
                        post.setBookmark(true)
                    }
                    else{
                        post.setBookmark(false)
                    }
                }


                val list = ArrayList<Post>(dataPosts)
                val index = list.indexOfFirst { it.getPostId() == post.getPostId() }

                if (index != -1){
                    list.set(index, post)

                    dataPosts = list
                    unregisteredFeedAdapter.updatePosts(list)
                }

            }
        }

        override fun onChildRemoved(snapshot: DataSnapshot) {
            val post = snapshot.getValue(Post::class.java)

            if (post != null && !post.getPostId().isNullOrEmpty() && !post.getUserId().isNullOrEmpty()){

                val list = ArrayList<Post>(dataPosts)

                val index = list.indexOfFirst { it.getPostId() == post.getPostId() }

                if (index != -1){
                    list.removeAt(index)

                    dataPosts = list
                    unregisteredFeedAdapter.updatePosts(list)

                    if (list.isNullOrEmpty()){
                        ivNone.visibility = View.VISIBLE
                        tvNone.visibility = View.VISIBLE
                        tvSubNone.visibility = View.VISIBLE
                    }
                }

            }
        }

        override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {
            /* This is intentionally left blank */

        }

        override fun onCancelled(error: DatabaseError) {
            val intent = Intent(this@BrowseFeedUnregisteredActivity, BrokenLinkActivity::class.java)
            startActivity(intent)
        }
    }
    /**
     * Fetches realtime updates from the remote database to prevent the entire activity from reloading
     * in case data change as a result of some user activity.
     */
    private fun getRealtimeUpdates(){
        this.ivNone = findViewById(R.id.iv_feed_unregistered_none)
        this.tvNone = findViewById(R.id.tv_feed_unregistered_none)
        this.tvSubNone = findViewById(R.id.tv_feed_unregistered_subtitle_none)

        Handler(Looper.getMainLooper()).postDelayed({

            if (dataPosts.isNullOrEmpty()){
                ivNone.visibility = View.VISIBLE
                tvNone.visibility = View.VISIBLE
                tvSubNone.visibility = View.VISIBLE
            }

        }, AnimationDuration.NO_POST_TIMEOUT.toLong())

        val postDB = this.db.child(Keys.KEY_DB_POSTS.name)

        postDB.addChildEventListener(childEventListener)
    }


    /**
     * Initialize the contents of the Activity's standard options menu.
     *
     * @param menu The options menu in which you place your items.
     * @return You must return true for the menu to be displayed; if you return false
     * it will not be shown.
     */
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.menu_top_with_search, menu)

        return true
    }

    /**
     * This hook is called whenever an item in your options menu is selected.
     *
     * @param item The menu item that was selected. This value cannot be <code>null</code>.
     * @return Return false to allow normal menu processing to proceed, true to consume it here.
     */
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId) {
            R.id.menu_feed_search -> {
                launchSearch()
                true
            } else -> {
                super.onOptionsItemSelected(item)
            }
        }
    }

    /**
     * Directs the user to the search activity.
     */
    private fun launchSearch() {
        val intent = Intent(this@BrowseFeedUnregisteredActivity, SearchUnregisteredActivity::class.java)
        startActivity(intent)
    }

    /**
     * Sets the listeners in relation to adding an artwork (that is, by either choosing an image
     * from the gallery or taking a photo using the device camera) to be posted on Artemis.
     */
    private fun addPost(){
        this.fabAddPost = findViewById(R.id.fab_feed_unregistered_add)

        this.fabAddPost.setOnClickListener {
            Toast.makeText(this@BrowseFeedUnregisteredActivity,"Log in or create an account to use this feature", Toast.LENGTH_SHORT).show()
        }
    }
}