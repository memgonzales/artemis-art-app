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
import com.google.firebase.database.*
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import java.io.File

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

    private lateinit var ref: DatabaseReference

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

    private fun initFirebase(){
        this.ref = Firebase.database.reference
    }

    private fun initComponents() {
        setSupportActionBar(findViewById(R.id.toolbar_feed))
        initShimmer()
        initBottom()
        initSwipeRefresh()

        addPost()
    }

    private fun initShimmer() {
        this.sflFeed = findViewById(R.id.sfl_feed)

        sflFeed.startShimmer()

        Handler(Looper.getMainLooper()).postDelayed({
            initRecyclerView()
            //initContent()
            sflFeed.visibility = View.GONE
            rvFeed.visibility = View.VISIBLE
        }, AnimationDuration.SHIMMER_TIMEOUT.toLong())
    }

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

    private fun onRefresh() {
        Handler(Looper.getMainLooper()).postDelayed({
            srlFeed.isRefreshing = false
        }, AnimationDuration.REFRESH_TIMEOUT.toLong())
    }

    private fun initBottom() {
        this.bnvFeedBottom = findViewById(R.id.nv_feed_bottom)
        this.nsvFeed = findViewById(R.id.nsv_feed)

        BottomMenuUtil.setScrollBottomMenuListeners(bnvFeedBottom, nsvFeed,
            BottomMenuUtil.HOME, this, this@BrowseFeedActivity)
    }

    private fun initRecyclerView() {
        //this.dataPosts = DataHelper.loadPostData();


        this.rvFeed = findViewById(R.id.rv_feed)
        this.rvFeed.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)

        //this.feedAdapter = FeedAdapter(dataPosts);


        //this.rvFeed.adapter = feedAdapter;

        initContent()

    }

    private fun initContent(){
        this.dataPosts = arrayListOf<Post>()

        val postDB = this.ref.child(Keys.KEY_DB_POSTS.name)

        postDB.addListenerForSingleValueEvent(object: ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {

                if(snapshot.exists()){

                    for(postSnap in snapshot.children){

                        val userImg = postSnap.child(Keys.profilePicture.name).getValue().toString()
                        val username = postSnap.child(Keys.username.name).getValue().toString()

                        val postImg = postSnap.child(Keys.postImg.name).getValue().toString()
                        val title = postSnap.child(Keys.title.name).getValue().toString()
                        val datePosted = postSnap.child(Keys.datePosted.name).getValue().toString()

                        val medium = postSnap.child(Keys.medium.name).getValue().toString()
                        val dimensions = postSnap.child(Keys.dimensions.name).getValue().toString()
                        val desc = postSnap.child(Keys.description.name).getValue().toString()
                        val tags = getList(postSnap.child(Keys.tags.name).getValue().toString())

                        val bookmark = postSnap.child(Keys.bookmarks.name).getValue().toString().toBoolean()
                        val upvote = postSnap.child(Keys.upvote.name).getValue().toString().toBoolean()
                        val highlights = postSnap.child(Keys.highlights.name).getValue().toString().toBoolean()

                        val upvoteUsers = postSnap.child(Keys.upvoteUsers.name).getValue().toString()
                        val comments = postSnap.child(Keys.comments.name).getValue().toString()

                        val numUpvotes = postSnap.child(Keys.numUpvotes.name).getValue().toString().toInt()
                        val numComment = postSnap.child(Keys.numComments.name).getValue().toString().toInt()

                        val post = Post(userImg, username, postImg, title, numUpvotes, numComment,
                            datePosted, medium, dimensions, desc, tags, bookmark, upvote, highlights)

                        dataPosts.add(post)

                    }


                    feedAdapter = FeedAdapter(dataPosts, this@BrowseFeedActivity)
                    rvFeed.adapter = feedAdapter

                    //Toast.makeText(applicationContext, "ch: " + title, Toast.LENGTH_SHORT).show()
                    //initRecyclerView(dataPosts)
                }

                //initRecyclerView(dataPosts)
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(applicationContext, "Unable to load data", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun getRealtimeUpdates(){
        val postDB = this.ref.child(Keys.KEY_DB_POSTS.name)

        postDB.addChildEventListener(object: ChildEventListener{
            override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
                var post = snapshot.getValue(Post::class.java)
               // post?.setPostId(snapshot.key!!)


            }

            override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {
                TODO("Not yet implemented")
            }

            override fun onChildRemoved(snapshot: DataSnapshot) {
                TODO("Not yet implemented")
            }

            override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {
                TODO("Not yet implemented")
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
    }
    private fun getList(str: String): ArrayList<String>{
        return str.substring(1, str.length-1).split(",").toCollection(ArrayList<String>())
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.menu_top_with_search, menu)

        return true
    }

    override fun onBackPressed() {
        val i = Intent(Intent.ACTION_MAIN)
        i.addCategory(Intent.CATEGORY_HOME)
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        startActivity(i)
    }

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

    private fun launchSearch() {
        val intent = Intent(this@BrowseFeedActivity, SearchActivity::class.java)
        startActivity(intent)
    }

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