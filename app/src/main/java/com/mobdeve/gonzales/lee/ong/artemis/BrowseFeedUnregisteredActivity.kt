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
import kotlin.collections.ArrayList

class BrowseFeedUnregisteredActivity : AppCompatActivity() {
    private lateinit var dataPosts: ArrayList<Post>
    private lateinit var rvFeed: RecyclerView
    private lateinit var unregisteredFeedAdapter: UnregisteredFeedAdapter
    private lateinit var sflFeed: ShimmerFrameLayout
    private lateinit var bnvFeedBottom: BottomNavigationView
    private lateinit var nsvFeed: NestedScrollView

    private lateinit var srlFeedUnregistered: SwipeRefreshLayout

    private lateinit var fabAddPost: FloatingActionButton

    private lateinit var ivNone: ImageView
    private lateinit var tvNone: TextView
    private lateinit var tvSubNone: TextView

    private lateinit var mAuth: FirebaseAuth
    private lateinit var db: DatabaseReference

    private lateinit var user: FirebaseUser
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
        //this.dataPosts = DataHelper.loadPostDataUnregistered()

        this.dataPosts = arrayListOf<Post>()
        this.rvFeed = findViewById(R.id.rv_feed_unregistered)
        this.rvFeed.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)

        this.unregisteredFeedAdapter = UnregisteredFeedAdapter(this.dataPosts)


        this.rvFeed.adapter = unregisteredFeedAdapter

        initContent(false)
        getRealtimeUpdates()
    }

    private fun initContent(shuffle: Boolean) {
        this.ivNone = findViewById(R.id.iv_feed_unregistered_none)
        this.tvNone = findViewById(R.id.tv_feed_unregistered_none)
        this.tvSubNone = findViewById(R.id.tv_feed_unregistered_subtitle_none)

        val postDB = this.db.child(Keys.KEY_DB_POSTS.name)

        postDB.addListenerForSingleValueEvent(object: ValueEventListener {

            override fun onDataChange(snapshot: DataSnapshot) {
                dataPosts.clear()
                if(snapshot.exists()){

                    for(postSnap in snapshot.children){
                        var post = postSnap.getValue(Post::class.java)

                        if(post != null){
                            if (!post.getUpvoteUsers().isNullOrEmpty() && post.getUpvoteUsers().containsKey(userId)){
                                post.setUpvote(true)
                            }

                            if(!post.getBookmarkUsers().isNullOrEmpty() && post.getBookmarkUsers().containsKey(userId)){
                                post.setBookmark(true)
                            }

                            dataPosts.add(post)
                        }
                    }

                    if (shuffle) {
                        Collections.shuffle(dataPosts)
                    }

                    if (!dataPosts.isEmpty()){
                        ivNone.visibility = View.GONE
                        tvNone.visibility = View.GONE
                        tvSubNone.visibility = View.GONE
                    }

                    else{
                        ivNone.visibility = View.VISIBLE
                        tvNone.visibility = View.VISIBLE
                        tvSubNone.visibility = View.VISIBLE
                    }

                    unregisteredFeedAdapter.notifyDataSetChanged()
                }

                else{
                    ivNone.visibility = View.VISIBLE
                    tvNone.visibility = View.VISIBLE
                    tvSubNone.visibility = View.VISIBLE
                }
            }

            override fun onCancelled(error: DatabaseError) {
                val intent = Intent(this@BrowseFeedUnregisteredActivity, BrokenLinkActivity::class.java)
                startActivity(intent)
            }
        })
    }

    private fun getRealtimeUpdates(){
        val postDB = this.db.child(Keys.KEY_DB_POSTS.name)

        postDB.addChildEventListener(object: ChildEventListener {
            override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
                val post = snapshot.getValue(Post::class.java)

                if (post != null && !post.getPostId().isNullOrEmpty()){
                    if (!post.getUpvoteUsers().isNullOrEmpty() && post.getUpvoteUsers().containsKey(userId)){
                        post.setUpvote(true)
                    }

                    if(!post.getBookmarkUsers().isNullOrEmpty() && post.getBookmarkUsers().containsKey(userId)){
                        post.setBookmark(true)
                    }

                    dataPosts.add(post)
                }

                unregisteredFeedAdapter.notifyDataSetChanged()
            }

            override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {
                val post = snapshot.getValue(Post::class.java)

                if (post != null && !post.getPostId().isNullOrEmpty()){
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
                }
            }

            override fun onChildRemoved(snapshot: DataSnapshot) {
                val post = snapshot.getValue(Post::class.java)
                dataPosts.remove(post)
                unregisteredFeedAdapter.notifyDataSetChanged()
            }

            override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {
                /* This is intentionally left blank */

            }

            override fun onCancelled(error: DatabaseError) {
                val intent = Intent(this@BrowseFeedUnregisteredActivity, BrokenLinkActivity::class.java)
                startActivity(intent)
            }

        })
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