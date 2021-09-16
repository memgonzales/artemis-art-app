package com.mobdeve.gonzales.lee.ong.artemis

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.bumptech.glide.Glide
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
import de.hdodenhof.circleimageview.CircleImageView
import java.io.File

/**
 * Class for handling functionalities related to displaying the search results.
 *
 * @constructor Creates an activity for displaying the search results.
 */
class SearchResultsActivity : AppCompatActivity() {
    /**
     * List of posts returned by the search query.
     */
    private lateinit var dataPosts: ArrayList<Post>

    /**
     * List of users returned by the search query.
     */
    private lateinit var dataUsers: ArrayList<User>

    /**
     * Recycler view for the posts to be displayed on the search results.
     */
    private lateinit var rvSearch: RecyclerView

    /**
     * Image view holding the profile picture of the first user returned by the search query.
     */
    private lateinit var civSearchResultUser1: CircleImageView

    /**
     * Image view holding the profile picture of the second user returned by the search query.
     */
    private lateinit var civSearchResultUser2: CircleImageView

    /**
     * Image view holding the profile picture of the third user returned by the search query.
     */
    private lateinit var civSearchResultUser3: CircleImageView

    /**
     * Image view holding the profile picture of the fourth user returned by the search query.
     */
    private lateinit var civSearchResultUser4: CircleImageView

    /**
     * Text view holding the "Matching Artworks" label.
     */
    private lateinit var tvSearchResultsArtworks: TextView
    private lateinit var tvSearchResultsUsers: TextView

    /**
     * Image view displayed when the search result does not have matching users or posts to display.
     */
    private lateinit var ivNone: ImageView

    /**
     * First (main) text view displayed when the search result does not have matching users or
     * posts to display.
     */
    private lateinit var tvNone: TextView

    /**
     * Second text view displayed when the search result does not have matching users or
     * posts to display.
     */
    private lateinit var tvSubNone: TextView

    /**
     * Adapter for the recycler view handling the posts to be displayed on the search result.
     */
    private lateinit var searchAdapter: SearchResultsAdapter

    /**
     * Shimmer layout displayed while data regarding the posts are being fetched
     * from the remote database.
     */
    private lateinit var sflSearch: ShimmerFrameLayout

    /**
     * Bottom navigation view containing the menu items for Home, Followed, Bookmarks, and Profile.
     */
    private lateinit var bnvSearchBottom: BottomNavigationView

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
    private lateinit var srlSearchResults: SwipeRefreshLayout

    /**
     * Input field for the user's search query.
     */
    private lateinit var etSearchBar: EditText

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
        setContentView(R.layout.activity_search_results)

        initFirebase()
        initComponents()
        initGalleryLauncher(this@SearchResultsActivity)
        initCameraLauncher(this@SearchResultsActivity)
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
            val intent = Intent(this@SearchResultsActivity, BrokenLinkActivity::class.java)
            startActivity(intent)
        }
    }

    /**
     * Initializes the components of the activity.
     */
    private fun initComponents() {
        setSupportActionBar(findViewById(R.id.toolbar_search_results))
        initShimmer()
        initBottom()
        addPost()
        initActionBar()

        initSwipeRefresh()
    }

    /**
     * Initializes the shimmer layout animated while the data are being fetched from the remote server.
     */
    private fun initShimmer() {
        this.sflSearch = findViewById(R.id.sfl_search_results)

        sflSearch.startShimmer()

        Handler(Looper.getMainLooper()).postDelayed({
            initContents()
            sflSearch.visibility = View.GONE
            //tvSearchResultsArtworks.visibility = View.VISIBLE
            rvSearch.visibility = View.VISIBLE

            civSearchResultUser1.visibility = View.GONE
            civSearchResultUser2.visibility = View.GONE
            civSearchResultUser3.visibility = View.GONE
            civSearchResultUser4.visibility = View.GONE
        }, AnimationDuration.SHIMMER_TIMEOUT.toLong())
    }

    /**
     * Initializes the swipe refresh layout and defines the behavior when the screen is swiped
     * to refresh.
     */
    private fun initSwipeRefresh() {
        this.srlSearchResults = findViewById(R.id.srl_search_results)
        srlSearchResults.setOnRefreshListener {
            onRefresh();
        }

        srlSearchResults.setColorSchemeResources(R.color.purple_main,
            R.color.pinkish_purple,
            R.color.purple_pics_lighter,
            R.color.pinkish_purple_lighter);
    }

    /**
     * Refetches data from the database and reshuffles the display of existing data when the screen
     * is swiped to refresh.
     */
    private fun onRefresh() {
        Handler(Looper.getMainLooper()).postDelayed({
            srlSearchResults.isRefreshing = false
        }, AnimationDuration.REFRESH_TIMEOUT.toLong())
    }

    /**
     * Sets the listeners for the menu selection found in the bottom navigation view.
     */
    private fun initBottom() {
        this.bnvSearchBottom = findViewById(R.id.nv_search_results_bottom)

        BottomMenuUtil.setBottomMenuListeners(bnvSearchBottom, this,
            this@SearchResultsActivity)
    }

    /**
     * Initializes the contents (i.e., the list of resulting users and posts) of the activity.
     */
    private fun initContents() {
        this.dataPosts = arrayListOf<Post>()
        this.dataUsers = arrayListOf<User>()

        this.civSearchResultUser1 = findViewById(R.id.civ_search_result_user1)
        this.civSearchResultUser2 = findViewById(R.id.civ_search_result_user2)
        this.civSearchResultUser3 = findViewById(R.id.civ_search_result_user3)
        this.civSearchResultUser4 = findViewById(R.id.civ_search_result_user4)

        this.tvSearchResultsArtworks = findViewById(R.id.tv_search_results_artworks)
        this.tvSearchResultsUsers = findViewById(R.id.tv_search_results_users)

        this.ivNone = findViewById(R.id.iv_search_results_none)
        this.tvNone = findViewById(R.id.tv_search_results_none)
        this.tvSubNone = findViewById(R.id.tv_search_results_subtitle_none)

        val intent: Intent = intent

        val search = intent.getStringExtra(Keys.KEY_SEARCH.name).toString()

        getUserSearchResults(search)

        civSearchResultUser1.setOnClickListener(View.OnClickListener {
            val intent = Intent(this@SearchResultsActivity, ViewUserActivity::class.java)
            intent.putExtra(
                Keys.KEY_USERID.name,
                dataUsers[0].getUserId()
            )

            startActivity(intent)
        })

        civSearchResultUser2.setOnClickListener(View.OnClickListener {
            val intent = Intent(this@SearchResultsActivity, ViewUserActivity::class.java)
            intent.putExtra(
                Keys.KEY_USERID.name,
                dataUsers[1].getUserId()
            )
            startActivity(intent)
        })

        civSearchResultUser3.setOnClickListener(View.OnClickListener {
            val intent = Intent(this@SearchResultsActivity, ViewUserActivity::class.java)
            intent.putExtra(
                Keys.KEY_USERID.name,
                dataUsers[2].getUserId()
            )
            startActivity(intent)
        })

        civSearchResultUser4.setOnClickListener(View.OnClickListener {
            val intent = Intent(this@SearchResultsActivity, ViewUserActivity::class.java)
            intent.putExtra(
                Keys.KEY_USERID.name,
                dataUsers[3].getUserId()
            )
            startActivity(intent)
        })


        this.rvSearch = findViewById(R.id.rv_search_results)
        this.rvSearch.layoutManager = GridLayoutManager(this, 2)

        this.searchAdapter = SearchResultsAdapter()

        this.rvSearch.adapter = searchAdapter

        this.rvSearch.itemAnimator = null

        this.etSearchBar = findViewById(R.id.et_search_results_bar)

        etSearchBar.setOnEditorActionListener(TextView.OnEditorActionListener { v, actionId, event ->
            if ((event != null && (event.keyCode == KeyEvent.KEYCODE_ENTER)) || (actionId == EditorInfo.IME_ACTION_DONE)) {
                val intent = Intent(this@SearchResultsActivity, SearchResultsActivity::class.java)
                intent.putExtra(
                    Keys.KEY_SEARCH.name,
                    etSearchBar.text.toString().trim()
                )
                startActivity(intent)
            }
            return@OnEditorActionListener false
        });
    }

    /**
     * Retrieves the list of matching users based on the user's search query.
     *
     * @param search The user's search query.
     */
    private fun getUserSearchResults(search: String){
        val userDB = this.db.child(Keys.KEY_DB_USERS.name)

        userDB.addListenerForSingleValueEvent(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                dataUsers.clear()
                if (snapshot.exists()){
                    for (u in snapshot.children){

                        var userSnap = u.getValue(User::class.java)

                        if (userSnap != null && userSnap.getUsername()!!.contains(search, ignoreCase = true)){
                            dataUsers.add(userSnap)
                        }

                    }

                    if (!dataUsers.isNullOrEmpty()){
                        tvSearchResultsUsers.visibility = View.VISIBLE
                        ivNone.visibility = View.GONE
                        tvNone.visibility = View.GONE
                        tvSubNone.visibility = View.GONE

                        setSearchUserResults(dataUsers)
                    }

                    getSearchPostResults(search, dataUsers)

                }
            }

            override fun onCancelled(error: DatabaseError) {
                val intent = Intent(this@SearchResultsActivity, BrokenLinkActivity::class.java)
                startActivity(intent)
            }

        })
    }

    /**
     * Sets the list of matching users based on the user's search query onto the activity layout.
     *
     * @param data Array list of users matching the user's search query.
     */
    private fun setSearchUserResults(data: ArrayList<User>){
        var i = 0

        while (i >= 0  && i < dataUsers.size && i < 4){

            when (i){
                0 -> {
                    civSearchResultUser1.visibility = View.VISIBLE

                    Glide.with(this)
                        .load(dataUsers[0].getUserImg())
                        .placeholder(R.drawable.placeholder)
                        .error(R.drawable.placeholder)
                        .into(civSearchResultUser1)
                }


                1 -> {
                    civSearchResultUser2.visibility = View.VISIBLE

                    Glide.with(this)
                        .load(dataUsers[1].getUserImg())
                        .placeholder(R.drawable.placeholder)
                        .error(R.drawable.placeholder)
                        .into(civSearchResultUser2)
                }


                2 -> {
                    civSearchResultUser3.visibility = View.VISIBLE

                    Glide.with(this)
                        .load(dataUsers[2].getUserImg())
                        .placeholder(R.drawable.placeholder)
                        .error(R.drawable.placeholder)
                        .into(civSearchResultUser3)
                }


                3 -> {
                    civSearchResultUser4.visibility = View.VISIBLE

                    Glide.with(this)
                        .load(dataUsers[3].getUserImg())
                        .placeholder(R.drawable.placeholder)
                        .error(R.drawable.placeholder)
                        .into(civSearchResultUser4)
                }
            }
            i++
        }
    }

    /**
     * Retrieves the list of matching posts based on the user's search query.
     *
     * @param searchPost The user's search query.
     * @param dataUsers Array list of user's matching the user's search query.
     */
    private fun getSearchPostResults(searchPost: String, dataUsers: ArrayList<User>){
        setSearchPostResults(dataPosts, dataUsers)

        val postDB = this.db.child(Keys.KEY_DB_POSTS.name)

        postDB.addChildEventListener(object : ChildEventListener{
            override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
                val post = snapshot.getValue(Post::class.java)

                if (post != null && !post.getPostId().isNullOrEmpty()
                    && !post.getTags()?.filter { it.contains(searchPost, ignoreCase = true)}.isNullOrEmpty()){

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

                    tvSearchResultsArtworks.visibility = View.VISIBLE
                    ivNone.visibility = View.GONE
                    tvNone.visibility = View.GONE
                    tvSubNone.visibility = View.GONE

                    dataPosts.add(post)
                    searchAdapter.updatePosts(dataPosts)


                }
            }

            override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {
                val post = snapshot.getValue(Post::class.java)

                if (post != null && !post.getPostId().isNullOrEmpty()
                    && !post.getTags()?.filter { it.contains(searchPost, ignoreCase = true)}.isNullOrEmpty()){

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

                    if (index != -1){
                        list[index] = post

                        dataPosts = list
                        searchAdapter.updatePosts(list)
                    }
                }
            }

            override fun onChildRemoved(snapshot: DataSnapshot) {
                val post = snapshot.getValue(Post::class.java)

                if (post != null && !post.getPostId().isNullOrEmpty()
                    && !post.getTags()?.filter { it.contains(searchPost, ignoreCase = true)}.isNullOrEmpty()){

                    val list = ArrayList<Post>(dataPosts)

                    val index = list.indexOfFirst { it.getPostId() == post.getPostId() }

                    if (index != -1){
                        list.removeAt(index)

                        dataPosts = list
                        searchAdapter.updatePosts(list)
                    }

                }
            }

            override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {
                /* This is intentionally left blank */
            }

            override fun onCancelled(error: DatabaseError) {
                val intent = Intent(this@SearchResultsActivity, BrokenLinkActivity::class.java)
                startActivity(intent)
            }
        })
    }

    /**
     * Sets the list of matching posts based on the user's search query onto the activity layout.
     *
     * @param dataPosts Array list of posts matching the user's search query.
     * @param dataUsers Array list of users matching the user's search query.
     */
    private fun setSearchPostResults(dataPosts: ArrayList<Post>, dataUsers: ArrayList<User>){
        if (dataPosts.isNullOrEmpty() && dataUsers.isNullOrEmpty()){
            this.tvSearchResultsUsers.visibility = View.GONE
            this.tvSearchResultsArtworks.visibility = View.GONE
            this.ivNone.visibility = View.VISIBLE
            this.tvNone.visibility = View.VISIBLE
            this.tvSubNone.visibility = View.VISIBLE
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
     * Sets the listeners in relation to adding an artwork (that is, by either choosing an image
     * from the gallery or taking a photo using the device camera) to be posted on Artemis.
     */
    private fun addPost() {
        this.btmAddPost = BottomSheetDialog(this@SearchResultsActivity)
        this.fabAddPost = findViewById(R.id.fab_search_results_add)

        val view = LayoutInflater.from(this@SearchResultsActivity).inflate(R.layout.dialog_post_artwork, null)

        this.fabAddPost.setOnClickListener {
            btmAddPost.setContentView(view)

            this.clDialogPostArtworkGallery = btmAddPost.findViewById(R.id.cl_dialog_post_artwork_gallery)!!
            this.clDialogPostArtworkPhoto = btmAddPost.findViewById(R.id.cl_dialog_post_artwork_photo)!!

            clDialogPostArtworkGallery.setOnClickListener {
                PostArtworkUtil.chooseFromGallery(this, galleryLauncher)
            }

            clDialogPostArtworkPhoto.setOnClickListener {
                photoFile = PostArtworkUtil.takeFromCamera(this, this@SearchResultsActivity, cameraLauncher)
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
        permissionsResult(requestCode, grantResults, this@SearchResultsActivity, this)
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