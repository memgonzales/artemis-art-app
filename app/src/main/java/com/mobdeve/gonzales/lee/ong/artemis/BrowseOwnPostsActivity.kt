package com.mobdeve.gonzales.lee.ong.artemis

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
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
 * Class handling the functionalities related to browsing the user's own posts.
 *
 * @constructor Creates a class that handles the functionalities related to browsing the user's
 * own posts.
 */
class BrowseOwnPostsActivity : AppCompatActivity() {
    /**
     * User's own posts.
     */
    private lateinit var dataPosts: ArrayList<Post>

    private lateinit var postKeys: ArrayList<String>

    /**
     * Recycler view for the user's own posts.
     */
    private lateinit var rvBrowseOwnPosts: RecyclerView

    /**
     * Adapter for the recycler view handling the user's own posts.
     */
    private lateinit var ownPostsAdapter: OwnPostsAdapter

    /**
     * Shimmer layout displayed while data regarding the user's own posts are being
     * fetched from the remote database.
     */
    private lateinit var sflBrowseOwnPosts: ShimmerFrameLayout

    /**
     * Bottom navigation view containing the menu items for Home, Followed, Bookmarks, and Profile.
     */
    private lateinit var bnvBrowseOwnPostsBottom: BottomNavigationView

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
     * Layout for registering a swipe gesture as a request to refresh this activity.
     */
    private lateinit var srlBrowseOwnPosts: SwipeRefreshLayout

    /**
     * Image view displayed when the feed does not have any post to display.
     */
    private lateinit var ivNone: ImageView

    /**
     * Text view displayed when the feed does not have any post to display.
     */
    private lateinit var tvNone: TextView

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

   // private lateinit var childEventListener: ChildEventListener

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
        setContentView(R.layout.activity_browse_own_posts)

        initFirebase()
        initComponents()
        initGalleryLauncher(this@BrowseOwnPostsActivity)
        initCameraLauncher(this@BrowseOwnPostsActivity)
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
            val intent = Intent(this@BrowseOwnPostsActivity, BrokenLinkActivity::class.java)
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
        setSupportActionBar(findViewById(R.id.toolbar_browse_own_posts))
        initActionBar()
        initShimmer()
        initBottom()
        addPost()
        initSwipeRefresh()
    }

    /**
     * Initializes the shimmer layout animated while the data are being fetched from the remote server.
     */
    private fun initShimmer() {
        this.sflBrowseOwnPosts = findViewById(R.id.sfl_browse_own_posts)

        sflBrowseOwnPosts.startShimmer()

        Handler(Looper.getMainLooper()).postDelayed({
            initRecyclerView()
            sflBrowseOwnPosts.visibility = View.GONE
            rvBrowseOwnPosts.visibility = View.VISIBLE
        }, AnimationDuration.SHIMMER_TIMEOUT.toLong())
    }

    /**
     * Initializes the swipe refresh layout and defines the behavior when the screen is swiped
     * to refresh.
     */
    private fun initSwipeRefresh() {
        this.srlBrowseOwnPosts = findViewById(R.id.srl_browse_own_posts)
        srlBrowseOwnPosts.setOnRefreshListener {
            onRefresh()
        }

        srlBrowseOwnPosts.setColorSchemeResources(R.color.purple_main,
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
            srlBrowseOwnPosts.isRefreshing = false
        }, AnimationDuration.REFRESH_TIMEOUT.toLong())
    }

    /**
     * Sets the listeners for the menu selection found in the bottom navigation view.
     */
    private fun initBottom() {
        this.bnvBrowseOwnPostsBottom = findViewById(R.id.nv_browse_own_posts_bottom)

        BottomMenuUtil.setBottomMenuListeners(bnvBrowseOwnPostsBottom, this,
            this@BrowseOwnPostsActivity)
    }

    /**
     * Initializes the recycler view of the activity.
     */
    private fun initRecyclerView() {
        //this.dataPosts = DataHelper.loadOwnPostsData();

        this.dataPosts = arrayListOf()
        this.postKeys = arrayListOf()

        this.rvBrowseOwnPosts = findViewById(R.id.rv_browse_own_posts)
        this.rvBrowseOwnPosts.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, true)

        //this.ownPostsAdapter = OwnPostsAdapter(this.dataPosts, this@BrowseOwnPostsActivity)
        this.ownPostsAdapter = OwnPostsAdapter(this@BrowseOwnPostsActivity)


        this.rvBrowseOwnPosts.adapter = ownPostsAdapter

        //initContent()
        getRealtimeUpdates()

    }

    /**
     * Fetches the keys related to the user's own posts from the remote database.
     */
    private fun initContent(){
        this.ivNone = findViewById(R.id.iv_browse_own_posts_none)
        this.tvNone = findViewById(R.id.tv_browse_own_posts_none)

        val userDB = this.db.child(Keys.KEY_DB_USERS.name).child(this.userId)

        userDB.addListenerForSingleValueEvent(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                val userPost = snapshot.getValue(User::class.java)

                if(userPost != null){
                    val postKeys = userPost.getUserPosts().keys
                    val userHighlights = userPost.getHighlights().keys

                    if (!userHighlights.isNullOrEmpty()){
                        getPosts(userHighlights, postKeys)
                    }

                    else{
                        ivNone.visibility = View.VISIBLE
                        tvNone.visibility = View.VISIBLE
                    }

                }

            }

            override fun onCancelled(error: DatabaseError) {
                val intent = Intent(this@BrowseOwnPostsActivity, BrokenLinkActivity::class.java)
                startActivity(intent)
            }
        })
    }


    /**
     * Fetches the user's own posts and updates the visibility of text and image views.
     *
     * @param highlights Posts highlighted by the user.
     * @param postKeys Keys of the user's own posts.
     */
    private fun getPosts(highlights: Set<String?>, postKeys: Set<String?>){

        val postDB = this.db.child(Keys.KEY_DB_POSTS.name)

        postDB.addListenerForSingleValueEvent(object: ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                dataPosts.clear()
                if (snapshot.exists()){
                    for (postSnap in snapshot.children){
                        if (postSnap.key != null && postKeys.contains(postSnap.key)){
                            val post = postSnap.getValue(Post::class.java)

                            if(post != null){
                                if (!highlights.isNullOrEmpty() && highlights.contains(post.getPostId())){
                                    post.setHighlight(true)
                                }

                                dataPosts.add(post)
                            }
                        }
                    }

                    if (dataPosts.isNotEmpty()){
                        ivNone.visibility = View.GONE
                        tvNone.visibility = View.GONE
                    }

                    else{
                        ivNone.visibility = View.VISIBLE
                        tvNone.visibility = View.VISIBLE
                    }

                    ownPostsAdapter.notifyDataSetChanged()
                }

                else{
                    ivNone.visibility = View.VISIBLE
                    tvNone.visibility = View.VISIBLE
                }

            }

            override fun onCancelled(error: DatabaseError) {
                val intent = Intent(this@BrowseOwnPostsActivity, BrokenLinkActivity::class.java)
                startActivity(intent)
            }

        })
    }

    /**
     * Fetches realtime updates from the remote database to prevent the entire activity from reloading
     * in case data change as a result of some user activity.
     */
    private fun getRealtimeUpdates(){
        val userDB = this.db.child(Keys.KEY_DB_USERS.name).child(userId) //.child(Keys.highlights.name)


        userDB.child(Keys.userPosts.name).addChildEventListener(object : ChildEventListener{
            override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
                val postId = snapshot.key.toString()
                setUserHighlight(postId)
            }

            override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {
                //val userSnap = snapshot.getValue(User::class.java)

                /*
                if (userSnap != null && !userSnap.getUserId().isNullOrEmpty()){
                    if(!userSnap.getHighlights().isNullOrEmpty()){

                    }
                }

                 */

                val postId = snapshot.key.toString()

                Toast.makeText(applicationContext, "ch: " + postId, Toast.LENGTH_LONG).show()
                setUserHighlight(postId)


            }

            override fun onChildRemoved(snapshot: DataSnapshot) {
                val postId = snapshot.key.toString()

                if (!postId.isNullOrEmpty()){
                    val list = ArrayList<Post>(dataPosts)

                    //val index = postKeys.indexOf(postId)
                    val index = list.indexOfFirst { it.getPostId() == postId }
                    list.removeAt(index)
                    //postKeys.removeAt(index)
                   // ownPostsAdapter.notifyItemRemoved(index)

                    dataPosts = list
                    ownPostsAdapter.updatePosts(list)
                }
            }

            override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {
                /* This is intentionally left blank */
            }

            override fun onCancelled(error: DatabaseError) {
                val intent = Intent(this@BrowseOwnPostsActivity, BrokenLinkActivity::class.java)
                startActivity(intent)
            }

        })
    }

    private fun setUserHighlight(postId: String){
        val userDB = this.db.child(Keys.KEY_DB_USERS.name).child(userId).child(Keys.highlights.name).child(postId)
        //val userDB = this.db.child(Keys.KEY_DB_USERS.name).child(userId)

        userDB.addListenerForSingleValueEvent(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                if(snapshot.exists()){
                    getPost(postId, true)
                    /*
                    val userSnap = snapshot.getValue(User::class.java)

                    if (userSnap != null){
                        val highlights = userSnap.getHighlights().keys
                        getRealtimePostUpdates(postId, highlights)
                    }

                     */
                }

                else{
                    getPost(postId, false)
                   // getRealtimePostUpdates(postId, false)
                }



            }

            override fun onCancelled(error: DatabaseError) {
                val intent = Intent(this@BrowseOwnPostsActivity, BrokenLinkActivity::class.java)
                startActivity(intent)
            }

        })
    }

    private fun getPost(postId: String, highlights: Boolean){
        val postDB = this.db.child(Keys.KEY_DB_POSTS.name)

        postDB.child(postId).addListenerForSingleValueEvent(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                if(snapshot.exists()){
                    val post = snapshot.getValue(Post::class.java)

                    if (post != null && !post.getPostId().isNullOrEmpty()){
                        post.setHighlight(highlights)
                        dataPosts.add(post)
                        postKeys.add(post.getPostId()!!)
                    }
                }

                //ownPostsAdapter.notifyDataSetChanged()
                ownPostsAdapter.updatePosts(dataPosts)

            }

            override fun onCancelled(error: DatabaseError) {
                val intent = Intent(this@BrowseOwnPostsActivity, BrokenLinkActivity::class.java)
                startActivity(intent)
            }

        })
    }




    /*
    override fun onStop() {
        super.onStop()

        this.db.child(Keys.KEY_DB_USERS.name).removeEventListener(this.childEventListener)
    }

     */

    /**
     * Adds a back button to the action bar.
     */
    private fun initActionBar() {
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
    }

    /**
     * Sets the listeners in relation to adding an artwork (that is, by either choosing an image
     * from the gallery or taking a photo using the device camera) to be posted on Artemis.
     */
    private fun addPost() {
        this.btmAddPost = BottomSheetDialog(this@BrowseOwnPostsActivity)
        this.fabAddPost = findViewById(R.id.fab_browse_own_posts_add)

        val view = LayoutInflater.from(this@BrowseOwnPostsActivity).inflate(R.layout.dialog_post_artwork, null)

        this.fabAddPost.setOnClickListener {
            btmAddPost.setContentView(view)

            this.clDialogPostArtworkGallery = btmAddPost.findViewById(R.id.cl_dialog_post_artwork_gallery)!!
            this.clDialogPostArtworkPhoto = btmAddPost.findViewById(R.id.cl_dialog_post_artwork_photo)!!

            clDialogPostArtworkGallery.setOnClickListener {
                PostArtworkUtil.chooseFromGallery(this, galleryLauncher)
            }

            clDialogPostArtworkPhoto.setOnClickListener {
                photoFile = PostArtworkUtil.takeFromCamera(this, this@BrowseOwnPostsActivity, cameraLauncher)
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
        permissionsResult(requestCode, grantResults, this@BrowseOwnPostsActivity, this)
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