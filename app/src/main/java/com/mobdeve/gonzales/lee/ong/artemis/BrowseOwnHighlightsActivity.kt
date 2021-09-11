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
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
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
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import java.io.File
import java.util.*

/**
 * Class handling the functionalities related to browsing the user's own highlights.
 *
 * @constructor Creates a class that handles the functionalities related to browsing the user's
 * own highlights.
 */
class BrowseOwnHighlightsActivity : AppCompatActivity() {
    /**
     * Posts highlighted by the user.
     */
    private lateinit var dataPosts: ArrayList<Post>

    /**
     * Recycler view for the posts highlighted by the user.
     */
    private lateinit var rvHighlights: RecyclerView

    /**
     * Adapter for the recycler view handling the posts highlighted by the user.
     */
    private lateinit var highlightsAdapter: HighlightsAdapter

    /**
     * Shimmer layout displayed while data regarding the posts highlighted by the user are being
     * fetched from the remote database.
     */
    private lateinit var sflHighlights: ShimmerFrameLayout

    /**
     * Bottom navigation view containing the menu items for Home, Followed, Bookmarks, and Profile.
     */
    private lateinit var bnvHighlightsBottom: BottomNavigationView

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
    private lateinit var srlHighlights: SwipeRefreshLayout

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
     * Activity result launcher related to choosing photos from the Gallery
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
        setContentView(R.layout.activity_browse_own_highlights)

        initFirebase()
        initComponents()
        initGalleryLauncher(this@BrowseOwnHighlightsActivity)
        initCameraLauncher(this@BrowseOwnHighlightsActivity)
    }

    /**
     * Initializes the activity result launcher related to choosing photos from the Gallery.
     *
     * @param packageContext context tied to this activity
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
     * @param packageContext context tied to this activity
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
            val intent = Intent(this@BrowseOwnHighlightsActivity, BrokenLinkActivity::class.java)
            startActivity(intent)
        }
    }

    /**
     * Initializes the components of the activity.
     */
    private fun initComponents() {
        setSupportActionBar(findViewById(R.id.toolbar_highlights))
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
        this.sflHighlights = findViewById(R.id.sfl_highlights)

        sflHighlights.startShimmer()

        Handler(Looper.getMainLooper()).postDelayed({
            initRecyclerView()
            sflHighlights.visibility = View.GONE
            rvHighlights.visibility = View.VISIBLE
        }, AnimationDuration.SHIMMER_TIMEOUT.toLong())
    }

    /**
     * Initializes the swipe refresh layout and defines the behavior when the screen is swiped
     * to refresh.
     */
    private fun initSwipeRefresh() {
        this.srlHighlights = findViewById(R.id.srl_highlights)
        srlHighlights.setOnRefreshListener {
            onRefresh()
        }

        srlHighlights.setColorSchemeResources(R.color.purple_main,
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
            srlHighlights.isRefreshing = false
        }, AnimationDuration.REFRESH_TIMEOUT.toLong())
    }

    /**
     * Sets the listeners for the menu selection found in the bottom navigation view.
     */
    private fun initBottom() {
        this.bnvHighlightsBottom = findViewById(R.id.nv_highlights_bottom)

        BottomMenuUtil.setBottomMenuListeners(bnvHighlightsBottom, this,
            this@BrowseOwnHighlightsActivity)
    }

    /**
     * Initializes the recycler view of the activity.
     */
    private fun initRecyclerView() {
       // this.dataPosts = DataHelper.loadHighlightsData()

        this.dataPosts = arrayListOf()

        this.rvHighlights = findViewById(R.id.rv_highlights)
        this.rvHighlights.layoutManager = GridLayoutManager(this, 2)

        this.highlightsAdapter = HighlightsAdapter(this.dataPosts)


        this.rvHighlights.adapter = highlightsAdapter

        initContent()
    }

    /**
     * Fetches the keys related to the posts highlighted by the user from the remote database.
     */
    private fun initContent(){
        val userDB = this.db.child(Keys.KEY_DB_USERS.name).child(this.userId)

        userDB.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                val userPost = snapshot.getValue(User::class.java)

                if(userPost != null){
                    val userHighlights = userPost.getHighlights().keys
                    getPosts(userHighlights)
                }

            }

            override fun onCancelled(error: DatabaseError) {
                val intent = Intent(this@BrowseOwnHighlightsActivity, BrokenLinkActivity::class.java)
                startActivity(intent)
            }
        })
    }

    /**
     * Fetches the posts highlighted by the user and updates the visibility of text and image views.
     *
     * @param highlights posts highlighted by the user
     */
    private fun getPosts(highlights: Set<String?>){
        this.ivNone = findViewById(R.id.iv_browse_highlights_none)
        this.tvNone = findViewById(R.id.tv_browse_highlights_none)

        //this.dataPosts = arrayListOf<Post>()
        val postDB = this.db.child(Keys.KEY_DB_POSTS.name)

        postDB.addValueEventListener(object: ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                dataPosts.clear()
                if (snapshot.exists()){
                    for (postSnap in snapshot.children){
                        if (postSnap.key != null && highlights.contains(postSnap.key)){
                            val post = postSnap.getValue(Post::class.java)

                            if(post != null){
                                post.setHighlight(true)
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

                    //highlightsAdapter = HighlightsAdapter(dataPosts)
                    //rvHighlights.adapter = highlightsAdapter
                    highlightsAdapter.notifyDataSetChanged()
                }

                else{
                    ivNone.visibility = View.VISIBLE
                    tvNone.visibility = View.VISIBLE
                }
            }

            override fun onCancelled(error: DatabaseError) {
                val intent = Intent(this@BrowseOwnHighlightsActivity, BrokenLinkActivity::class.java)
                startActivity(intent)
            }

        })

    }

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
        this.btmAddPost = BottomSheetDialog(this@BrowseOwnHighlightsActivity)
        this.fabAddPost = findViewById(R.id.fab_browse_highlights_add)

        val view = LayoutInflater.from(this@BrowseOwnHighlightsActivity).inflate(R.layout.dialog_post_artwork, null)

        this.fabAddPost.setOnClickListener {
            btmAddPost.setContentView(view)

            this.clDialogPostArtworkGallery = btmAddPost.findViewById(R.id.cl_dialog_post_artwork_gallery)!!
            this.clDialogPostArtworkPhoto = btmAddPost.findViewById(R.id.cl_dialog_post_artwork_photo)!!

            clDialogPostArtworkGallery.setOnClickListener {
                PostArtworkUtil.chooseFromGallery(this, galleryLauncher)
            }

            clDialogPostArtworkPhoto.setOnClickListener {
                photoFile = PostArtworkUtil.takeFromCamera(this, this@BrowseOwnHighlightsActivity, cameraLauncher)
            }

            btmAddPost.show()
        }
    }

    /**
     * Callback for the result from requesting permissions.
     *
     * @param requestCode The request code passed in <code>
     *     ActivityCompat.requestPermissions(android.app.Activity, String[], int)</code>
     * @param permissions The requested permissions. Never null
     * @param grantResults The grant results for the corresponding permissions which is either <code>
     *     PackageManager.PERMISSION_GRANTED</code> or <code>PackageManager.PERMISSION_DENIED</code>.
     *     Never null
     */
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>,
                                            grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        permissionsResult(requestCode, grantResults, this@BrowseOwnHighlightsActivity, this)
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