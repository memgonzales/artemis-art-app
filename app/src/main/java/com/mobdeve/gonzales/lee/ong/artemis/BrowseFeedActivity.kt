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
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap

class BrowseFeedActivity : AppCompatActivity() {
    private lateinit var dataPosts: ArrayList<Post>

    private lateinit var rvFeed: RecyclerView
    private lateinit var feedAdapter: FeedAdapter
    private lateinit var sflFeed: ShimmerFrameLayout
    private lateinit var bnvFeedBottom: BottomNavigationView
    private lateinit var nsvFeed: NestedScrollView

    private lateinit var srlFeed: SwipeRefreshLayout

    private lateinit var btmAddPost: BottomSheetDialog
    private lateinit var fabAddPost: FloatingActionButton
    private lateinit var clDialogPostArtworkGallery: ConstraintLayout
    private lateinit var clDialogPostArtworkPhoto: ConstraintLayout

    private lateinit var ivNone: ImageView
    private lateinit var tvNone: TextView
    private lateinit var tvSubNone: TextView

    private lateinit var mAuth: FirebaseAuth
    private lateinit var db: DatabaseReference

    private lateinit var user: FirebaseUser
    private lateinit var userId: String

    /**
     * Photo of the artwork for posting.
     */
    private lateinit var photoFile: File

    /**
     * Activity result launcher related to taking photos using the device camera.
     */
    private lateinit var cameraLauncher:  ActivityResultLauncher<Intent>

    /**
     * Activity result launcher related to choosing photos from the Gallery
     */
    private lateinit var galleryLauncher: ActivityResultLauncher<Intent>

    /*
    private val childEventListener = object : ChildEventListener{
        override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
            val post = snapshot.getValue(Post::class.java)

            if (post != null){
                post.setPostId(snapshot.key!!)
            }
        }
    }

     */
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
        setContentView(R.layout.activity_browse_feed)

        initFirebase()
        initComponents()
        initGalleryLauncher(this@BrowseFeedActivity)
        initCameraLauncher(this@BrowseFeedActivity)

    }

    /**
     * Initializes the activity result launcher related to choosing photos from the Gallery.
     *
     * @packageContext context tied to this activity
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
     * Initializes the activity result launcher related to taking photos using the device camera
     *
     * @packageContext context tied to this activity
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
            val intent = Intent(this@BrowseFeedActivity, BrokenLinkActivity::class.java)
            startActivity(intent)
        }
    }

    /**
     * Initializes the components of the activity.
     */
    private fun initComponents() {
        setSupportActionBar(findViewById(R.id.toolbar_feed))
        initShimmer()
        initBottom()
        initSwipeRefresh()

        addPost()
    }

    /**
     * Initializes the shimmer layout animated while the data are being fetched from the remote server.
     */
    private fun initShimmer() {
        this.sflFeed = findViewById(R.id.sfl_feed)

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
        this.srlFeed = findViewById(R.id.srl_feed)
        srlFeed.setOnRefreshListener {
            onRefresh()
        }

        srlFeed.setColorSchemeResources(R.color.purple_main,
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
            initContent(true)
            srlFeed.isRefreshing = false
        }, AnimationDuration.REFRESH_TIMEOUT.toLong())
    }

    /**
     * Sets the listeners for the menu selection found in the bottom navigation view.
     */
    private fun initBottom() {
        this.bnvFeedBottom = findViewById(R.id.nv_feed_bottom)
        this.nsvFeed = findViewById(R.id.nsv_feed)

        BottomMenuUtil.setScrollBottomMenuListeners(bnvFeedBottom, nsvFeed,
            BottomMenuUtil.HOME, this, this@BrowseFeedActivity)
    }

    /**
     * Initializes the recycler view of the activity.
     */
    private fun initRecyclerView() {
        //this.dataPosts = DataHelper.loadPostData();
        this.dataPosts = arrayListOf<Post>()

        this.rvFeed = findViewById(R.id.rv_feed)
        this.rvFeed.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, true)

        this.feedAdapter = FeedAdapter(dataPosts, this@BrowseFeedActivity);


        this.rvFeed.adapter = feedAdapter;

        initContent(false)
        //getRealtimeUpdates()

    }

    private fun initContent(shuffle: Boolean) {
        this.ivNone = findViewById(R.id.iv_feed_none)
        this.tvNone = findViewById(R.id.tv_feed_none)
        this.tvSubNone = findViewById(R.id.tv_feed_subtitle_none)

        val postDB = this.db.child(Keys.KEY_DB_POSTS.name)

        postDB.addListenerForSingleValueEvent(object: ValueEventListener{

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

                    feedAdapter.notifyDataSetChanged()
                }

                else{
                    ivNone.visibility = View.VISIBLE
                    tvNone.visibility = View.VISIBLE
                    tvSubNone.visibility = View.VISIBLE
                }
            }

            override fun onCancelled(error: DatabaseError) {
                val intent = Intent(this@BrowseFeedActivity, BrokenLinkActivity::class.java)
                startActivity(intent)
            }
        })
    }

    private fun getRealtimeUpdates(){
        val postDB = this.db.child(Keys.KEY_DB_POSTS.name)

        postDB.addChildEventListener(object: ChildEventListener{
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

                feedAdapter.notifyDataSetChanged()
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
                feedAdapter.notifyDataSetChanged()
            }

            override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {
                /* This is intentionally left blank */

            }

            override fun onCancelled(error: DatabaseError) {
                val intent = Intent(this@BrowseFeedActivity, BrokenLinkActivity::class.java)
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
     * Called when the activity has detected the user's press of the back key.
     */
    override fun onBackPressed() {
        val i = Intent(Intent.ACTION_MAIN)
        i.addCategory(Intent.CATEGORY_HOME)
        i.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        startActivity(i)
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
        val intent = Intent(this@BrowseFeedActivity, SearchActivity::class.java)
        startActivity(intent)
    }

    /**
     * Sets the listeners in relation to adding an artwork (that is, by either choosing an image
     * from the gallery or taking a photo using the device camera) to be posted on Artemis.
     */
    private fun addPost() {
        this.btmAddPost = BottomSheetDialog(this@BrowseFeedActivity)
        this.fabAddPost = findViewById(R.id.fab_feed_add)

        val view = LayoutInflater.from(this@BrowseFeedActivity).inflate(R.layout.dialog_post_artwork, null)

        this.fabAddPost.setOnClickListener {
            btmAddPost.setContentView(view)

            this.clDialogPostArtworkGallery = btmAddPost.findViewById(R.id.cl_dialog_post_artwork_gallery)!!
            this.clDialogPostArtworkPhoto = btmAddPost.findViewById(R.id.cl_dialog_post_artwork_photo)!!

            clDialogPostArtworkGallery.setOnClickListener {
                PostArtworkUtil.chooseFromGallery(this, galleryLauncher)
            }

            clDialogPostArtworkPhoto.setOnClickListener {
                photoFile = PostArtworkUtil.takeFromCamera(this, this@BrowseFeedActivity, cameraLauncher)
            }

            btmAddPost.show()
        }
    }

    /**
     * Callback for the result from requesting permissions.
     *
     * @param requestCode the request code passed in <code>
     *     ActivityCompat.requestPermissions(android.app.Activity, String[], int)</code>
     * @param permissions the requested permissions. Never null
     * @param grantResults the grant results for the corresponding permissions which is either <code>
     *     PackageManager.PERMISSION_GRANTED</code> or <code>PackageManager.PERMISSION_DENIED</code>.
     *     Never null
     */
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>,
                                            grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        permissionsResult(requestCode, grantResults, this@BrowseFeedActivity, this)
    }

    /**
     * Defines the behavior related to choosing a photo from the Gallery or taking a photo using
     * the device camera based on the permissions granted by the user.
     *
     * @param requestCode the request code passed in <code>
     *     ActivityCompat.requestPermissions(android.app.Activity, String[], int)</code>
     * @param grantResults the grant results for the corresponding permissions which is either <code>
     *     PackageManager.PERMISSION_GRANTED</code> or <code>PackageManager.PERMISSION_DENIED</code>.
     *     Never null
     * @param context context tied to this activity
     * @param activity this activity
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
