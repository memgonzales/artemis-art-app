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
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.widget.NestedScrollView
import androidx.recyclerview.widget.GridLayoutManager
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
 * Class handling the functionalities related to browsing the posts bookmarked by the user.
 *
 * @constructor Creates a class that handles the functionalities related to browsing the posts
 * bookmarked by the user.
 */
class BrowseBookmarksActivity : AppCompatActivity() {
    /**
     * Posts bookmarked by the user.
     */
    private lateinit var dataPosts: ArrayList<Post>

    /**
     * Recycler view for the posts bookmarked by the user.
     */
    private lateinit var rvBookmarks: RecyclerView

    /**
     * Adapter for the recycler view handling the posts bookmarked by the user.
     */
    private lateinit var bookmarksAdapter: BookmarksAdapter

    /**
     * Shimmer layout displayed while data regarding the posts bookmarked by the user
     * are being fetched from the remote database.
     */
    private lateinit var sflBookmarks: ShimmerFrameLayout

    /**
     * Bottom navigation view containing the menu items for Home, Followed, Bookmarks, and Profile.
     */
    private lateinit var bnvBookmarksBottom: BottomNavigationView

    /**
     * Nested scroll view for the main layout of this activity.
     */
    private lateinit var nsvBookmarks: NestedScrollView

    /**
     * Layout for registering a swipe gesture as a request to refresh this activity.
     */
    private lateinit var srlBookmarks: SwipeRefreshLayout

    /**
     * Image view displayed when the user has no bookmarked posts.
     */
    private lateinit var ivNone: ImageView

    /**
     * Text view displayed when the user has no bookmarked posts.
     */
    private lateinit var tvNone: TextView

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
    private lateinit var cameraLauncher:  ActivityResultLauncher<Intent>

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
        setContentView(R.layout.activity_browse_bookmarks)

        initFirebase()
        initComponents()
        initGalleryLauncher(this@BrowseBookmarksActivity)
        initCameraLauncher(this@BrowseBookmarksActivity)
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
            val intent = Intent(this@BrowseBookmarksActivity, BrokenLinkActivity::class.java)
            startActivity(intent)
        }
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
     * Initializes the components of the activity.
     */
    private fun initComponents() {
        setSupportActionBar(findViewById(R.id.toolbar_bookmarks))
        initShimmer()
        initBottom()
        addPost()
        initSwipeRefresh()
    }

    /**
     * Initializes the shimmer layout animated while the data are being fetched from the remote server.
     */
    private fun initShimmer() {
        this.sflBookmarks = findViewById(R.id.sfl_bookmarks)

        sflBookmarks.startShimmer()

        Handler(Looper.getMainLooper()).postDelayed({
            initRecyclerView()
            sflBookmarks.visibility = View.GONE
            rvBookmarks.visibility = View.VISIBLE
        }, AnimationDuration.SHIMMER_TIMEOUT.toLong())
    }

    /**
     * Sets the listeners for the menu selection found in the bottom navigation view.
     */
    private fun initBottom() {
        this.bnvBookmarksBottom = findViewById(R.id.nv_bookmarks_bottom)
        this.nsvBookmarks = findViewById(R.id.nsv_bookmarks)

        BottomMenuUtil.setScrollBottomMenuListeners(bnvBookmarksBottom, nsvBookmarks,
            BottomMenuUtil.BOOKMARK, this, this@BrowseBookmarksActivity)
    }

    /**
     * Initializes the swipe refresh layout and defines the behavior when the screen is swiped
     * to refresh.
     */
    private fun initSwipeRefresh() {
        this.srlBookmarks = findViewById(R.id.srl_bookmarks)
        srlBookmarks.setOnRefreshListener {
            onRefresh()
        }

        srlBookmarks.setColorSchemeResources(R.color.purple_main,
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
            initRecyclerView()

            srlBookmarks.isRefreshing = false
        }, AnimationDuration.REFRESH_TIMEOUT.toLong())
    }

    /**
     * Initializes the recycler view of the activity.
     */
    private fun initRecyclerView() {
        this.dataPosts = arrayListOf<Post>()

        this.rvBookmarks = findViewById(R.id.rv_bookmarks)
        this.rvBookmarks.layoutManager = GridLayoutManager(this, 2)

        this.bookmarksAdapter = BookmarksAdapter()

        this.rvBookmarks.adapter = bookmarksAdapter

        this.rvBookmarks.itemAnimator = null
        getRealtimeUpdates()
    }

    private var childEventListener = object : ChildEventListener{
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

                val list = ArrayList<Post>(dataPosts)

                val index = list.indexOfFirst { it.getPostId() == postId }

                if (index != -1){
                    list.removeAt(index)

                    dataPosts = list
                    bookmarksAdapter.updatePosts(list)
                }
            }
        }

        override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {
            /* This is intentionally left blank */
        }

        override fun onCancelled(error: DatabaseError) {
            val intent = Intent(this@BrowseBookmarksActivity, BrokenLinkActivity::class.java)
            startActivity(intent)
        }
    }
    /**
     * Fetches realtime updates from the remote database to prevent the entire activity from reloading
     * in case data change as a result of some user activity.
     */
    private fun getRealtimeUpdates(){
        this.ivNone = findViewById(R.id.iv_browse_bookmarks_none)
        this.tvNone = findViewById(R.id.tv_browse_bookmarks_none)

        Handler(Looper.getMainLooper()).postDelayed({

            if (dataPosts.isNullOrEmpty()){
                ivNone.visibility = View.VISIBLE
                tvNone.visibility = View.VISIBLE
            }

        }, AnimationDuration.NO_POST_TIMEOUT.toLong())


        val userDB = this.db.child(Keys.KEY_DB_USERS.name).child(userId).child(Keys.bookmarks.name)

        userDB.addChildEventListener(childEventListener)
    }


    private fun getPost(postId: String){
        val postDB = this.db.child(Keys.KEY_DB_POSTS.name).child(postId)

        postDB.addListenerForSingleValueEvent(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()){
                    val post = snapshot.getValue(Post::class.java)
                    post?.setBookmark(true)

                    if (post != null){
                        dataPosts.add(post)
                        bookmarksAdapter.updatePosts(dataPosts)

                        ivNone.visibility = View.GONE
                        tvNone.visibility = View.GONE
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {
                val intent = Intent(this@BrowseBookmarksActivity, BrokenLinkActivity::class.java)
                startActivity(intent)
            }

        })
    }

    /*
    override fun onPause() {
        val userDB = this.db.child(Keys.KEY_DB_USERS.name).child(userId).child(Keys.bookmarks.name)
        userDB.removeEventListener(childEventListener)

        super.onPause()
    }

    override fun onResume() {
        super.onResume()

        initRecyclerView()
    }

     */

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
        val intent = Intent(this@BrowseBookmarksActivity, SearchActivity::class.java)
        startActivity(intent)
    }

    /**
     * Sets the listeners in relation to adding an artwork (that is, by either choosing an image
     * from the gallery or taking a photo using the device camera) to be posted on Artemis.
     */
    private fun addPost() {
        this.btmAddPost = BottomSheetDialog(this@BrowseBookmarksActivity)
        this.fabAddPost = findViewById(R.id.fab_bookmarks_add)

        val view = LayoutInflater.from(this@BrowseBookmarksActivity).inflate(R.layout.dialog_post_artwork, null)

        this.fabAddPost.setOnClickListener {
            btmAddPost.setContentView(view)

            this.clDialogPostArtworkGallery = btmAddPost.findViewById(R.id.cl_dialog_post_artwork_gallery)!!
            this.clDialogPostArtworkPhoto = btmAddPost.findViewById(R.id.cl_dialog_post_artwork_photo)!!

            clDialogPostArtworkGallery.setOnClickListener {
                PostArtworkUtil.chooseFromGallery(this, galleryLauncher)
            }

            clDialogPostArtworkPhoto.setOnClickListener {
                photoFile = PostArtworkUtil.takeFromCamera(this, this@BrowseBookmarksActivity, cameraLauncher)
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
        permissionsResult(requestCode, grantResults, this@BrowseBookmarksActivity, this)
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