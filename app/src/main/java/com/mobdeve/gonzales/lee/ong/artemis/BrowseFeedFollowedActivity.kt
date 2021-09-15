package com.mobdeve.gonzales.lee.ong.artemis

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.widget.NestedScrollView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.facebook.shimmer.ShimmerFrameLayout
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.*
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import java.io.File

/**
 * Class handling the functionalities related to browsing the posts of followed users.
 *
 * @constructor Creates a class that handles the functionalities related to browsing the posts
 * of followed users.
 */
class BrowseFeedFollowedActivity : AppCompatActivity() {
    /**
     * Posts of followed users.
     */
    private lateinit var dataPosts: ArrayList<Post>

    /**
     * Recycler view for the posts of followed users.
     */
    private lateinit var rvFollowed: RecyclerView

    /**
     * Adapter for the recycler view handling the posts of followed users.
     */
    private lateinit var feedFollowedAdapter: FeedFollowedAdapter

    /**
     * Shimmer layout displayed while data regarding the posts are being fetched
     * from the remote database.
     */
    private lateinit var sflFollowed: ShimmerFrameLayout

    /**
     * Bottom navigation view containing the menu items for Home, Followed, Bookmarks, and Profile.
     */
    private lateinit var bnvFollowedBottom: BottomNavigationView

    /**
     * Nested scroll view for the main layout of this activity.
     */
    private lateinit var nsvFollowed: NestedScrollView

    /**
     * Layout for registering a swipe gesture as a request to refresh this activity.
     */
    private lateinit var srlFeedFollowed: SwipeRefreshLayout

    /**
     * Bottom sheet dialog displayed when the user clicks the floating action button
     * for posting an artwork.
     */
    private lateinit var btmAddPost: BottomSheetDialog

    /**
     * Floating action button for posting an artwork.
     */
    private lateinit var fabAddPost: FloatingActionButton

    /**
     * Clickable constraint layout (part of the bottom sheet dialog) related to the option
     * of the user uploading a photo of their artwork from the Gallery.
     */
    private lateinit var clDialogPostArtworkGallery: ConstraintLayout

    /**
     * Clickable constraint layout (part of the bottom sheet dialog) related to the option
     * of the user taking a photo of their artwork using the device camera.
     */
    private lateinit var clDialogPostArtworkPhoto: ConstraintLayout

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
     * Photo of the artwork for posting.
     */
    private lateinit var photoFile: File

    /**
     * Activity result launcher related to taking photos using the device camera.
     */
    private lateinit var cameraLauncher: ActivityResultLauncher<Intent>

    /**
     * Activity result launcher related to choosing photos from the Gallery.
     */
    private lateinit var galleryLauncher: ActivityResultLauncher<Intent>

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
        setContentView(R.layout.activity_browse_feed_followed)

        initFirebase()
        initComponents()
        initGalleryLauncher(this@BrowseFeedFollowedActivity)
        initCameraLauncher(this@BrowseFeedFollowedActivity)
    }

    /**
     * Initializes the activity result launcher related to choosing photos from the Gallery.
     *
     * @param packageContext Context tied to this activity.
     */
    private fun initGalleryLauncher(packageContext: Context) {
        galleryLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
                result: ActivityResult ->
            if (result.resultCode == Activity.RESULT_OK) {
                val intent = Intent(packageContext, PostArtworkActivity::class.java)
                intent.putExtra(
                    Keys.KEY_POST_ARTWORK.name,
                    result.data?.data.toString()
                )

                intent.putExtra(
                    Keys.KEY_POST_FROM.name,
                    PostArtworkUtil.FROM_GALLERY
                )

                startActivity(intent)
            }
        }
    }

    /**
     * Initializes the activity result launcher related to taking photos using the device camera.
     *
     * @param packageContext Context tied to this activity.
     */
    private fun initCameraLauncher(packageContext: Context) {
        cameraLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
            if (result.resultCode == Activity.RESULT_OK) {
                val intent = Intent(packageContext, PostArtworkActivity::class.java)
                intent.putExtra(
                    Keys.KEY_POST_ARTWORK.name,
                    photoFile.absolutePath
                )

                intent.putExtra(
                    Keys.KEY_POST_FROM.name,
                    PostArtworkUtil.FROM_CAMERA
                )

                startActivity(intent)
            }
        }
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
            val intent = Intent(this@BrowseFeedFollowedActivity, BrokenLinkActivity::class.java)
            startActivity(intent)
        }
    }

    /**
     * Initializes the components of the activity.
     */
    private fun initComponents() {
        setSupportActionBar(findViewById(R.id.toolbar_feed_followed))
        initShimmer()
        initBottom()
        addPost()
        initSwipeRefresh()
    }

    /**
     * Initializes the shimmer layout animated while the data are being fetched from the remote server.
     */
    private fun initShimmer() {
        this.sflFollowed = findViewById(R.id.sfl_feed_followed)

        sflFollowed.startShimmer()

        Handler(Looper.getMainLooper()).postDelayed({
            initRecyclerView()
            sflFollowed.visibility = View.GONE
            rvFollowed.visibility = View.VISIBLE
        }, AnimationDuration.SHIMMER_TIMEOUT.toLong())
    }

    /**
     * Initializes the swipe refresh layout and defines the behavior when the screen is swiped
     * to refresh.
     */
    private fun initSwipeRefresh() {
        this.srlFeedFollowed = findViewById(R.id.srl_feed_followed)
        srlFeedFollowed.setOnRefreshListener {
            onRefresh()
        }

        srlFeedFollowed.setColorSchemeResources(R.color.purple_main,
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
            srlFeedFollowed.isRefreshing = false
        }, AnimationDuration.REFRESH_TIMEOUT.toLong())
    }

    /**
     * Sets the listeners for the menu selection found in the bottom navigation view.
     */
    private fun initBottom() {
        this.bnvFollowedBottom = findViewById(R.id.nv_feed_followed_bottom)
        this.nsvFollowed = findViewById(R.id.nsv_feed_followed)

        BottomMenuUtil.setScrollBottomMenuListeners(bnvFollowedBottom, nsvFollowed,
            BottomMenuUtil.FOLLOW, this, this@BrowseFeedFollowedActivity)
    }

    /**
     * Initializes the recycler view of the activity.
     */
    private fun initRecyclerView() {
        //this.dataPosts = DataHelper.loadFollowedData();

        this.dataPosts = arrayListOf()
        this.rvFollowed = findViewById(R.id.rv_feed_followed)
        this.rvFollowed.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)

        //this.feedFollowedAdapter = FeedFollowedAdapter(this@BrowseFeedFollowedActivity)
        this.feedFollowedAdapter = FeedFollowedAdapter(this.dataPosts,this@BrowseFeedFollowedActivity)


        this.rvFollowed.adapter = feedFollowedAdapter

       initContent()
        //getRealtimeUpdates()
    }


    /**
     * Fetches the keys related to the posts of followed users from the remote database.
     */
    private fun initContent(){
        this.ivNone = findViewById(R.id.iv_feed_followed_none)
        this.tvNone = findViewById(R.id.tv_feed_followed_none)
        this.tvSubNone = findViewById(R.id.tv_feed_followed_subtitle_none)

        val userDB = this.db.child(Keys.KEY_DB_USERS.name)

        userDB.child(userId).addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {

                val userSnap = snapshot.getValue(User::class.java)

                if (userSnap != null){
                    val usersFF = userSnap.getUsersFollowed().keys
                    getUserPosts(userDB, usersFF)
                }
            }

            override fun onCancelled(error: DatabaseError) {
                val intent = Intent(this@BrowseFeedFollowedActivity, BrokenLinkActivity::class.java)
                startActivity(intent)
            }

        })
    }

    /**
     * Fetches the posts of followed users and updates the visibility of text and image views.
     *
     * @param ref Particular location in the Firebase database.
     * @param userKeys Keys of the followed users.
     */
    private fun getUserPosts(ref: DatabaseReference, userKeys: Set<String?>) {

        ref.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                dataPosts.clear()
                if (snapshot.exists()){
                    for (userSnap in snapshot.children){
                        if (userSnap.key != null && userKeys.contains(userSnap.key)){
                            val userFF = userSnap.getValue(User::class.java)

                            if(userFF != null){
                                val userPosts = userFF.getUserPosts().keys

                                getPosts(userPosts)
                            }
                        }
                    }

                    if (dataPosts.isNotEmpty()){
                        ivNone.visibility = View.GONE
                        tvNone.visibility = View.GONE
                        tvSubNone.visibility = View.GONE
                    }
                    /*
                    else{
                        ivNone.visibility = View.VISIBLE
                        tvNone.visibility = View.VISIBLE
                        tvSubNone.visibility = View.VISIBLE
                    }

                     */
                }

                else{
                    ivNone.visibility = View.VISIBLE
                    tvNone.visibility = View.VISIBLE
                    tvSubNone.visibility = View.VISIBLE
                }

            }

            override fun onCancelled(error: DatabaseError) {
                val intent = Intent(this@BrowseFeedFollowedActivity, BrokenLinkActivity::class.java)
                startActivity(intent)
            }

        })
    }

    /**
     * Fetches the posts of followed users, handles the status of each post (that is, whether it
     * is bookmarked or upvoted by the current user), and updates the view, alongside the adapter
     * and the view holder.
     *
     * @param userPosts Posts of followed users.
     */
    private fun getPosts(userPosts: Set<String?>){
        val postDB = this.db.child(Keys.KEY_DB_POSTS.name)

        postDB.addValueEventListener(object: ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {

                if (snapshot.exists()) {
                    for (postSnap in snapshot.children) {
                        if (postSnap.key != null && userPosts.contains(postSnap.key)) {
                            val post = postSnap.getValue(Post::class.java)

                            if (post != null) {

                                if (!post.getUpvoteUsers().isNullOrEmpty() && post.getUpvoteUsers().containsKey(userId)) {
                                    post.setUpvote(true)
                                }

                                if (!post.getBookmarkUsers().isNullOrEmpty() && post.getBookmarkUsers().containsKey(userId)) {
                                    post.setBookmark(true)
                                }

                                dataPosts.add(post)
                            }
                        }
                    }

                    feedFollowedAdapter.notifyDataSetChanged()
                }
            }

            override fun onCancelled(error: DatabaseError) {
                //Toast.makeText(this@BrowseOwnPostsActivity, "Unable to load data", Toast.LENGTH_SHORT).show()
                val intent = Intent(this@BrowseFeedFollowedActivity, BrokenLinkActivity::class.java)
                startActivity(intent)
            }

        })
    }



    /**
     * Fetches realtime updates from the remote database to prevent the entire activity from reloading
     * in case data change as a result of some user activity.
     */
    private fun getRealtimeUpdates(){
        this.ivNone = findViewById(R.id.iv_feed_followed_none)
        this.tvNone = findViewById(R.id.tv_feed_followed_none)
        this.tvSubNone = findViewById(R.id.tv_feed_followed_subtitle_none)

        val userDB = this.db.child(Keys.KEY_DB_USERS.name).child(userId).child(Keys.usersFollowed.name)

        userDB.addChildEventListener(object: ChildEventListener {
            override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
                val postId = snapshot.key.toString()
                if (!postId.isNullOrEmpty()){
                    getUserPosts(userId)
                }

            }

            override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {
                /* This is intentionally left blank */
            }

            override fun onChildRemoved(snapshot: DataSnapshot) {
                val postId = snapshot.key.toString()

                if (!postId.isNullOrEmpty()){

                    val list = ArrayList<Post>(dataPosts)

                    val index = list.indexOfFirst { it.getPostId() == postId }

                    if (index != -1){
                        list.removeAt(index)

                        dataPosts = list
                        feedFollowedAdapter.updatePosts(list)

                        if (dataPosts.isNullOrEmpty()){
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
                val intent = Intent(this@BrowseFeedFollowedActivity, BrokenLinkActivity::class.java)
                startActivity(intent)
            }

        })

        if (dataPosts.isNotEmpty()){
            ivNone.visibility = View.GONE
            tvNone.visibility = View.GONE
            tvSubNone.visibility = View.GONE
        }

        else{
            ivNone.visibility = View.VISIBLE
            tvNone.visibility = View.VISIBLE
            tvSubNone.visibility = View.VISIBLE
        }
    }

    private fun getUserPosts(userId: String){
        val userDB = this.db.child(Keys.KEY_DB_USERS.name).child(userId)

        userDB.addListenerForSingleValueEvent(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()){
                    val userSnap = snapshot.getValue(User::class.java)

                    if (userSnap != null){
                        val usersFFPosts = userSnap.getUpvotedPosts().keys

                        if (usersFFPosts != null){
                            getRealtimePostUpdates(usersFFPosts)
                        }
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {
                val intent = Intent(this@BrowseFeedFollowedActivity, BrokenLinkActivity::class.java)
                startActivity(intent)
            }

        })
    }

    private fun getRealtimePostUpdates(userPosts: Set<String?>){
        val postDB = this.db.child(Keys.KEY_DB_POSTS.name)

        postDB.addChildEventListener(object: ChildEventListener{
            override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
                val post = snapshot.getValue(Post::class.java)

                if (post != null && !post.getPostId().isNullOrEmpty() && userPosts.contains(post.getPostId())){
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
                    feedFollowedAdapter.updatePosts(dataPosts)
                }

            }

            override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {
                val post = snapshot.getValue(Post::class.java)

                if (post != null && !post.getPostId().isNullOrEmpty() && userPosts.contains(post.getPostId())){

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


                    val list = ArrayList<Post>(dataPosts)
                    val index = list.indexOfFirst { it.getPostId() == post.getPostId() }

                    Toast.makeText(applicationContext, "ch: " + index, Toast.LENGTH_SHORT).show()
                    if (index != -1){
                        list.set(index, post)

                        dataPosts = list
                        feedFollowedAdapter.updatePosts(list)
                    }

                }
            }

            override fun onChildRemoved(snapshot: DataSnapshot) {
                val post = snapshot.getValue(Post::class.java)

                if (post != null && !post.getPostId().isNullOrEmpty()){

                    val list = ArrayList<Post>(dataPosts)

                    val index = list.indexOfFirst { it.getPostId() == post.getPostId() }

                    if (index != -1){
                        list.removeAt(index)

                        dataPosts = list
                        feedFollowedAdapter.updatePosts(list)

                        if (dataPosts.isNullOrEmpty()){
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
                val intent = Intent(this@BrowseFeedFollowedActivity, BrokenLinkActivity::class.java)
                startActivity(intent)
            }
        })

        if (dataPosts.isNotEmpty()){
            ivNone.visibility = View.GONE
            tvNone.visibility = View.GONE
        }

        else{
            Handler(Looper.getMainLooper()).postDelayed({

                ivNone.visibility = View.VISIBLE
                tvNone.visibility = View.VISIBLE

            }, AnimationDuration.NO_POST_TIMEOUT.toLong())

        }
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
        val intent = Intent(this@BrowseFeedFollowedActivity, SearchActivity::class.java)
        startActivity(intent)
    }

    /**
     * Sets the listeners in relation to adding an artwork (that is, by either choosing an image
     * from the gallery or taking a photo using the device camera) to be posted on Artemis.
     */
    private fun addPost() {
        this.btmAddPost = BottomSheetDialog(this@BrowseFeedFollowedActivity)
        this.fabAddPost = findViewById(R.id.fab_feed_followed_add)

        val view = LayoutInflater.from(this@BrowseFeedFollowedActivity).inflate(R.layout.dialog_post_artwork, null)

        this.fabAddPost.setOnClickListener {
            btmAddPost.setContentView(view)

            this.clDialogPostArtworkGallery = btmAddPost.findViewById(R.id.cl_dialog_post_artwork_gallery)!!
            this.clDialogPostArtworkPhoto = btmAddPost.findViewById(R.id.cl_dialog_post_artwork_photo)!!

            clDialogPostArtworkGallery.setOnClickListener {
                PostArtworkUtil.chooseFromGallery(this, galleryLauncher)
            }

            clDialogPostArtworkPhoto.setOnClickListener {
                photoFile = PostArtworkUtil.takeFromCamera(this, this@BrowseFeedFollowedActivity, cameraLauncher)
            }

            btmAddPost.show()
        }
    }

    /**
     * Callback for the result from requesting permissions.
     *
     * @param requestCode The request code passed in <code>
     *     ActivityCompat.requestPermissions(android.app.Activity, String[], int)</code>.
     * @param permissions The requested permissions. Never null.
     * @param grantResults The grant results for the corresponding permissions which is either <code>
     *     PackageManager.PERMISSION_GRANTED</code> or <code>PackageManager.PERMISSION_DENIED</code>.
     *     Never null.
     */
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>,
                                            grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        permissionsResult(requestCode, grantResults, this@BrowseFeedFollowedActivity, this)
    }

    /**
     * Defines the behavior related to choosing a photo from the Gallery or taking a photo using
     * the device camera based on the permissions granted by the user.
     *
     * @param requestCode The request code passed in <code>
     *     ActivityCompat.requestPermissions(android.app.Activity, String[], int)</code>.
     * @param grantResults The grant results for the corresponding permissions which is either <code>
     *     PackageManager.PERMISSION_GRANTED</code> or <code>PackageManager.PERMISSION_DENIED</code>.
     *     Never null.
     * @param context Context tied to this activity.
     * @param activity This activity.
     */
    private fun permissionsResult(requestCode: Int, grantResults: IntArray, context: Context,
                                  activity: Activity) {
        when (requestCode) {
            RequestCodes.REQUEST_CODE_POST_CAMERA.ordinal -> {
                val temp: File? = PostArtworkUtil.permissionsResultCamera(grantResults, activity,
                    context, cameraLauncher)

                if (temp != null) {
                    photoFile = temp
                }
            }

            RequestCodes.REQUEST_CODE_POST_GALLERY.ordinal -> {
                PostArtworkUtil.permissionsResultGallery(grantResults, context, galleryLauncher)
            }
        }
    }
}