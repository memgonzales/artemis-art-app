package com.mobdeve.gonzales.lee.ong.artemis

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.widget.NestedScrollView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.*
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import de.hdodenhof.circleimageview.CircleImageView
import java.io.File

/**
 * Class handling the functionalities related to viewing another user's profile.
 *
 * @constructor Creates a class that handles the functionalities related to viewing another
 * user's profile.
 */
class ViewUserActivity : AppCompatActivity() {
    /**
     * Profile picture of the user whose profile is being viewed.
     */
    private lateinit var civViewUserProfilePicture: CircleImageView

    /**
     * Username of the user whose profile is being viewed.
     */
    private lateinit var tvViewUserUsername: TextView

    /**
     * Bio of the user whose profile is being viewed.
     */
    private lateinit var tvViewUserBio: TextView

    /**
     * Button for following a user.
     */
    private lateinit var btnViewUserFollow: Button

    /**
     * Bottom navigation view containing the menu items for Home, Followed, Bookmarks, and Profile.
     */
    private lateinit var bnvViewUserBottom: BottomNavigationView

    /**
     * Nested scroll view holding the contents of the activity excluding the bottom navigation view.
     */
    private lateinit var nsvViewUser: NestedScrollView

    /**
     * List of highlights of the user whose profile is being viewed.
     */
    private lateinit var dataHighlights: ArrayList<Post>

    /**
     * Recycler view holding the highlights of the user whose profile is being viewed.
     */
    private lateinit var rvViewUser: RecyclerView

    /**
     * Adapter for displaying the highlights of the user whose profile is being viewed.
     */
    private lateinit var highlightAdapter: OthersHighlightAdapter

    /**
     * Image view displayed when the user's highlights does not have any post to display.
     */
    private lateinit var ivNone: ImageView

    /**
     * First (main) text view displayed when the user's highlights does not have any post to display.
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
     * Layout for registering a swipe gesture as a request to refresh this activity.
     */
    private lateinit var srlViewUser: SwipeRefreshLayout

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
        setContentView(R.layout.activity_view_user)

        initFirebase()
        initComponents()
        initContent()
        addPost()

        initGalleryLauncher(this@ViewUserActivity)
        initCameraLauncher(this@ViewUserActivity)
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
            val intent = Intent(this@ViewUserActivity, BrokenLinkActivity::class.java)
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
     * Child event listener for handling the viewing of a user's highlights.
     */
    private var childEventListenerUserPost = object : ChildEventListener{
        override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
            val postId = snapshot.key.toString()

            if (postId != null) {
                getPost(postId)
            }
        }

        override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {
            /* This is intentionally left blank */
        }

        override fun onChildRemoved(snapshot: DataSnapshot) {
            val postId = snapshot.key.toString()

            if (postId != null) {

                val list = java.util.ArrayList<Post>(dataHighlights)

                val index = list.indexOfFirst { it.getPostId() == postId }

                if (index != -1){
                    list.removeAt(index)

                    dataHighlights = list
                    highlightAdapter.updatePosts(list)

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
            val intent = Intent(this@ViewUserActivity, BrokenLinkActivity::class.java)
            startActivity(intent)
        }
    }

    /**
     * Initializes the contents of the activity.
     */
    private fun initContent() {
        this.ivNone = findViewById(R.id.iv_user_highlights_none)
        this.tvNone = findViewById(R.id.tv_user_highlights_none)

        this.civViewUserProfilePicture = findViewById(R.id.civ_view_user_logo)
        this.tvViewUserUsername = findViewById(R.id.tv_view_user_unregistered_username)
        this.tvViewUserBio = findViewById(R.id.tv_view_user_unregistered_bio)
        this.btnViewUserFollow = findViewById(R.id.btn_view_user_follow)

        this.dataHighlights = arrayListOf<Post>()

        val intent: Intent = intent
        this.userIdPost = intent.getStringExtra(Keys.KEY_USERID.name).toString()

        this.firebaseHelper = FirebaseHelper(this@ViewUserActivity, userIdPost)

        val userDB = this.db.child(Keys.KEY_DB_USERS.name)

        if (!userIdPost.isNullOrEmpty()){

            userDB.child(userIdPost).addListenerForSingleValueEvent(object : ValueEventListener{
                override fun onDataChange(snapshot: DataSnapshot) {
                    val userInfoPost = snapshot.getValue(User::class.java)

                    if(userInfoPost != null){
                        Glide.with(this@ViewUserActivity)
                            .load(userInfoPost.getUserImg())
                                        .placeholder(R.drawable.chibi_artemis_hd)
                            .error(R.drawable.chibi_artemis_hd)
                            .into(civViewUserProfilePicture)

                        tvViewUserUsername.text = userInfoPost.getUsername()
                        tvViewUserBio.text = userInfoPost.getBio()

                        if (userInfoPost.getHighlights().isNullOrEmpty()){
                            ivNone.visibility = View.VISIBLE
                            tvNone.visibility = View.VISIBLE
                        }
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    val intent = Intent(this@ViewUserActivity, BrokenLinkActivity::class.java)
                    startActivity(intent)
                }

            })

            userDB.child(userIdPost).child(Keys.highlights.name).addChildEventListener(childEventListenerUserPost)

            if (userIdPost.equals(userId)){
                btnViewUserFollow.visibility = View.GONE
            }

            else{
                userDB.child(userId).addListenerForSingleValueEvent(object: ValueEventListener{
                    override fun onDataChange(snapshot: DataSnapshot) {
                        val userSnap = snapshot.getValue(User::class.java)

                        if (userSnap != null){
                            val usersFF = userSnap.getUsersFollowed().keys

                            if (usersFF.contains(userIdPost)){
                                btnViewUserFollow.setText("UNFOLLOW USER")
                            }

                            else{
                                btnViewUserFollow.setText("FOLLOW USER")
                            }
                        }
                    }

                    override fun onCancelled(error: DatabaseError) {
                        val intent = Intent(this@ViewUserActivity, BrokenLinkActivity::class.java)
                        startActivity(intent)
                    }

                })
            }

            btnViewUserFollow.setOnClickListener {
                if(btnViewUserFollow.text.equals("FOLLOW USER")){
                    this.firebaseHelper.updateUsersFollowedDB(userIdPost, userIdPost)
                    btnViewUserFollow.setText("UNFOLLOW USER")
                }

                else{
                    this.firebaseHelper.updateUsersFollowedDB(userIdPost, null)
                    btnViewUserFollow.setText("FOLLOW USER")
                }
            }

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
                        highlightAdapter.updatePosts(dataHighlights)

                        ivNone.visibility = View.GONE
                        tvNone.visibility = View.GONE
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {
                val intent = Intent(this@ViewUserActivity, BrokenLinkActivity::class.java)
                startActivity(intent)
            }

        })
    }

    /**
     * Initializes the components of the activity.
     */
    private fun initComponents() {
        setSupportActionBar(findViewById(R.id.toolbar_view_user))
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

        this.rvViewUser = findViewById(R.id.rv_view_user)
        this.rvViewUser.layoutManager = GridLayoutManager(this, 2)

        this.highlightAdapter = OthersHighlightAdapter()

        this.rvViewUser.adapter = highlightAdapter
    }

    /**
     * Initializes the swipe refresh layout and defines the behavior when the screen is swiped
     * to refresh.
     */
    private fun initSwipeRefresh() {
        this.srlViewUser = findViewById(R.id.srl_view_user)
        srlViewUser.setOnRefreshListener {
            onRefresh()
        }

        srlViewUser.setColorSchemeResources(R.color.purple_main,
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
            srlViewUser.isRefreshing = false
        }, AnimationDuration.REFRESH_TIMEOUT.toLong())
    }

    /**
     * Sets the listeners for the menu selection found in the bottom navigation view.
     */
    private fun initBottom() {
        this.bnvViewUserBottom = findViewById(R.id.nv_view_user_bottom)
        this.nsvViewUser = findViewById(R.id.nsv_view_user)

        BottomMenuUtil.setBottomMenuListeners(bnvViewUserBottom, this,
            this@ViewUserActivity)
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

    /**
     * Sets the listeners in relation to adding an artwork (that is, by either choosing an image
     * from the gallery or taking a photo using the device camera) to be posted on Artemis.
     */
    private fun addPost() {
        this.btmAddPost = BottomSheetDialog(this@ViewUserActivity)
        this.fabAddPost = findViewById(R.id.fab_view_user_add)

        val view = LayoutInflater.from(this@ViewUserActivity).inflate(R.layout.dialog_post_artwork, null)

        this.fabAddPost.setOnClickListener {
            btmAddPost.setContentView(view)

            this.clDialogPostArtworkGallery = btmAddPost.findViewById(R.id.cl_dialog_post_artwork_gallery)!!
            this.clDialogPostArtworkPhoto = btmAddPost.findViewById(R.id.cl_dialog_post_artwork_photo)!!

            clDialogPostArtworkGallery.setOnClickListener {
                PostArtworkUtil.chooseFromGallery(this, galleryLauncher)
            }

            clDialogPostArtworkPhoto.setOnClickListener {
                photoFile = PostArtworkUtil.takeFromCamera(this, this@ViewUserActivity, cameraLauncher)
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
        permissionsResult(requestCode, grantResults, this@ViewUserActivity, this)
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
                                  activity: Activity
    ) {
        when (requestCode) {
            RequestCodes.REQUEST_CODE_POST_CAMERA.ordinal -> {
                val temp: File? = PostArtworkUtil.permissionsResultCamera(
                    grantResults, activity,
                    context, cameraLauncher
                )

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