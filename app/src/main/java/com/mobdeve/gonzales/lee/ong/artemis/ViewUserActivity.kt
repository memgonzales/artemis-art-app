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
import de.hdodenhof.circleimageview.CircleImageView
import java.io.File

class ViewUserActivity : AppCompatActivity() {
    private lateinit var civViewUserProfilePicture: CircleImageView
    private lateinit var tvViewUserUsername: TextView
    private lateinit var tvViewUserBio: TextView
    private lateinit var btnViewUserFollow: Button
    private lateinit var bnvViewUserBottom: BottomNavigationView
    private lateinit var nsvViewUser: NestedScrollView
    private lateinit var dataHighlights: ArrayList<Post>
    private lateinit var rvViewUser: RecyclerView
    private lateinit var highlightAdapter: OthersHighlightAdapter

    private lateinit var btmAddPost: BottomSheetDialog
    private lateinit var fabAddPost: FloatingActionButton
    private lateinit var clDialogPostArtworkGallery: ConstraintLayout
    private lateinit var clDialogPostArtworkPhoto: ConstraintLayout

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
     * Activity result launcher related to choosing photos from the Gallery
     */
    private lateinit var galleryLauncher: ActivityResultLauncher<Intent>


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

    private fun initContent() {
        this.civViewUserProfilePicture = findViewById(R.id.civ_view_user_logo)
        this.tvViewUserUsername = findViewById(R.id.tv_view_user_unregistered_username)
        this.tvViewUserBio = findViewById(R.id.tv_view_user_unregistered_bio)
        this.btnViewUserFollow = findViewById(R.id.btn_view_user_follow)

        this.dataHighlights = DataHelper.loadOthersHighlightData()

        val intent: Intent = intent
        val userIdPost = intent.getStringExtra(Keys.KEY_USERID.name).toString()
        /*
        val profilePicture = intent.getIntExtra(Keys.KEY_PROFILE_PICTURE.name, 0)
        val username = intent.getStringExtra(Keys.KEY_USERNAME.name)
        val bio = intent.getStringExtra(Keys.KEY_BIO.name)

         */

        val userDB = this.db.child(Keys.KEY_DB_USERS.name)

        if(!userIdPost.isEmpty()){
            userDB.child(userIdPost).addListenerForSingleValueEvent(object : ValueEventListener{
                override fun onDataChange(snapshot: DataSnapshot) {
                    val userInfoPost = snapshot.getValue(User::class.java)

                    if(userInfoPost != null){
                        Glide.with(this@ViewUserActivity)
                            .load(userInfoPost.getUserImg())
                            .error(R.drawable.chibi_artemis_hd)
                            .into(civViewUserProfilePicture)

                        tvViewUserUsername.text = userInfoPost.getUsername()
                        tvViewUserBio.text = userInfoPost.getBio()

                        val highlights = userInfoPost.getHighlights().keys
                        getPosts(highlights)
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    val intent = Intent(this@ViewUserActivity, BrokenLinkActivity::class.java)
                    startActivity(intent)
                }

            })
        }

        //this.civViewUserProfilePicture.setImageResource(profilePicture)
        //this.tvViewUserUsername.text = username
        //this.tvViewUserBio.text = bio

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
                updateUsersFF(userIdPost, userIdPost)
                btnViewUserFollow.setText("UNFOLLOW USER")
            }

            else{
                updateUsersFF(userIdPost, null)
                btnViewUserFollow.setText("FOLLOW USER")
            }
        }
    }

    private fun updateUsersFF(userKey: String?, userVal: String?){
        val updates = hashMapOf<String, Any?>(
            "/${Keys.KEY_DB_USERS.name}/$userId/${Keys.usersFollowed.name}/$userKey" to userVal
        )

        db.updateChildren(updates)
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

                    highlightAdapter = OthersHighlightAdapter(dataHighlights)
                    rvViewUser.adapter = highlightAdapter

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
        this.rvViewUser = findViewById(R.id.rv_view_user)
        this.rvViewUser.layoutManager = GridLayoutManager(this, 2)

      //  this.highlightAdapter = OthersHighlightAdapter(this.dataHighlights)

       // this.rvViewUser.adapter = highlightAdapter
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
     * Re-fetches data from the database and reshuffles the display of existing data when the screen
     * is swiped to refresh.
     */
    private fun onRefresh() {
        Handler(Looper.getMainLooper()).postDelayed({
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
     *     ActivityCompat.requestPermissions(android.app.Activity, String[], int)</code>
     * @param permissions The requested permissions. Never null
     * @param grantResults The grant results for the corresponding permissions which is either <code>
     *     PackageManager.PERMISSION_GRANTED</code> or <code>PackageManager.PERMISSION_DENIED</code>.
     *     Never null
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
     * @param requestCode the request code passed in <code>
     *     ActivityCompat.requestPermissions(android.app.Activity, String[], int)</code>
     * @param grantResults the grant results for the corresponding permissions which is either <code>
     *     PackageManager.PERMISSION_GRANTED</code> or <code>PackageManager.PERMISSION_DENIED</code>.
     *     Never null
     * @param context context tied to this activity
     * @param activity this activity
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