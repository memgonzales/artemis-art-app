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
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import de.hdodenhof.circleimageview.CircleImageView
import java.io.File

class SearchResultsActivity : AppCompatActivity() {
    private lateinit var dataPosts: ArrayList<Post>
    private lateinit var dataUsers: ArrayList<User>
    private lateinit var rvSearch: RecyclerView
    private lateinit var civSearchResultUser1: CircleImageView
    private lateinit var civSearchResultUser2: CircleImageView
    private lateinit var civSearchResultUser3: CircleImageView
    private lateinit var civSearchResultUser4: CircleImageView
    private lateinit var tvSearchResultsArtworks: TextView

    private lateinit var ivNone: ImageView
    private lateinit var tvNone: TextView
    private lateinit var tvSubNone: TextView

    private lateinit var searchAdapter: SearchResultsAdapter
    private lateinit var sflSearch: ShimmerFrameLayout
    private lateinit var bnvSearchBottom: BottomNavigationView

    private lateinit var btmAddPost: BottomSheetDialog
    private lateinit var fabAddPost: FloatingActionButton
    private lateinit var clDialogPostArtworkGallery: ConstraintLayout
    private lateinit var clDialogPostArtworkPhoto: ConstraintLayout

    private lateinit var srlSearchResults: SwipeRefreshLayout

    private lateinit var etSearchBar: EditText

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
    private lateinit var cameraLauncher: ActivityResultLauncher<Intent>

    /**
     * Activity result launcher related to choosing photos from the Gallery
     */
    private lateinit var galleryLauncher: ActivityResultLauncher<Intent>

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
            val intent = Intent(this@SearchResultsActivity, BrokenLinkActivity::class.java)
            startActivity(intent)
        }
    }

    private fun initComponents() {
        setSupportActionBar(findViewById(R.id.toolbar_search_results))
        initShimmer()
        initBottom()
        addPost()
        initActionBar()

        initSwipeRefresh()
    }

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

    private fun onRefresh() {
        Handler(Looper.getMainLooper()).postDelayed({
            srlSearchResults.isRefreshing = false
        }, AnimationDuration.REFRESH_TIMEOUT.toLong())
    }

    private fun initBottom() {
        this.bnvSearchBottom = findViewById(R.id.nv_search_results_bottom)

        BottomMenuUtil.setBottomMenuListeners(bnvSearchBottom, this,
            this@SearchResultsActivity)
    }

    private fun initContents() {
        this.dataPosts = DataHelper.loadPostData();
        //this.dataUsers = DataHelper.loadSearchUserData();
        this.dataUsers = arrayListOf<User>()

        this.civSearchResultUser1 = findViewById(R.id.civ_search_result_user1)
        this.civSearchResultUser2 = findViewById(R.id.civ_search_result_user2)
        this.civSearchResultUser3 = findViewById(R.id.civ_search_result_user3)
        this.civSearchResultUser4 = findViewById(R.id.civ_search_result_user4)

        this.tvSearchResultsArtworks = findViewById(R.id.tv_search_results_artworks)
        this.ivNone = findViewById(R.id.iv_search_results_none)
        this.tvNone = findViewById(R.id.tv_search_results_none)
        this.tvSubNone = findViewById(R.id.tv_search_results_subtitle_none)

        val intent: Intent = intent

        val search = intent.getStringExtra(Keys.KEY_SEARCH.name).toString()

        //this.civSearchResultUser1.setImageResource(dataUsers[0].getUserImg())
        //this.civSearchResultUser2.setImageResource(dataUsers[1].getUserImg())
        //this.civSearchResultUser3.setImageResource(dataUsers[2].getUserImg())
        //this.civSearchResultUser4.setImageResource(dataUsers[3].getUserImg())

        getUserSearchResults(search)
        //getSearchPostResults(search)

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

        this.searchAdapter = SearchResultsAdapter(this.dataPosts)

        this.rvSearch.adapter = searchAdapter

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

    private fun getUserSearchResults(search: String){
        val userDB = this.db.child(Keys.KEY_DB_USERS.name)

        userDB.addListenerForSingleValueEvent(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                dataUsers.clear()
                if (snapshot.exists()){
                    for (u in snapshot.children){
                        var userSnap = u.getValue(User::class.java)

                        if (userSnap != null && userSnap.getUsername().contains(search, ignoreCase = true)){
                            dataUsers.add(userSnap)
                        }
                    }

                    setSearchUserResults(dataUsers)
                    getSearchPostResults(search, dataUsers)

                }
            }

            override fun onCancelled(error: DatabaseError) {
                val intent = Intent(this@SearchResultsActivity, BrokenLinkActivity::class.java)
                startActivity(intent)
            }

        })
    }

    private fun setSearchUserResults(data: ArrayList<User>){
        var i = 0

        while (i >= 0  && i < dataUsers.size && i < 4){

            when (i){
                0 -> {
                    civSearchResultUser1.visibility = View.VISIBLE

                    Glide.with(this@SearchResultsActivity)
                        .load(dataUsers[0].getUserImg())
                        .placeholder(R.drawable.placeholder)
                        .error(R.drawable.placeholder)
                        .into(civSearchResultUser1)
                }


                1 -> {
                    civSearchResultUser2.visibility = View.VISIBLE

                    Glide.with(this@SearchResultsActivity)
                        .load(dataUsers[1].getUserImg())
                        .placeholder(R.drawable.placeholder)
                        .error(R.drawable.placeholder)
                        .into(civSearchResultUser2)
                }


                2 -> {
                    civSearchResultUser3.visibility = View.VISIBLE

                    Glide.with(this@SearchResultsActivity)
                        .load(dataUsers[2].getUserImg())
                        .placeholder(R.drawable.placeholder)
                        .error(R.drawable.placeholder)
                        .into(civSearchResultUser3)
                }


                3 -> {
                    civSearchResultUser4.visibility = View.VISIBLE

                    Glide.with(this@SearchResultsActivity)
                        .load(dataUsers[3].getUserImg())
                        .placeholder(R.drawable.placeholder)
                        .error(R.drawable.placeholder)
                        .into(civSearchResultUser4)
                }
            }
            i++
        }
    }


    private fun getSearchPostResults(searchPost: String, dataUsers: ArrayList<User>){
        val postDB = this.db.child(Keys.KEY_DB_POSTS.name)

        postDB.addListenerForSingleValueEvent(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                dataPosts.clear()
                if (snapshot.exists()){
                    for (p in snapshot.children){
                        var postSnap = p.getValue(Post::class.java)

                        if (postSnap != null && !postSnap.getTags().isNullOrEmpty()) {

                            var check = postSnap.getTags().filter { it.contains(searchPost, ignoreCase = true) }

                            if (check.size > 0){
                                dataPosts.add(postSnap)
                            }
                        }
                    }
                    searchAdapter.notifyDataSetChanged()
                    setSearchPostResults(dataPosts, dataUsers)

                }
            }

            override fun onCancelled(error: DatabaseError) {
                val intent = Intent(this@SearchResultsActivity, BrokenLinkActivity::class.java)
                startActivity(intent)
            }

        })
    }

    private fun setSearchPostResults(dataPosts: ArrayList<Post>, dataUsers: ArrayList<User>){
        if (dataPosts.isNullOrEmpty() && dataUsers.isNullOrEmpty()){
            this.tvSearchResultsArtworks.visibility = View.GONE
            this.ivNone.visibility = View.VISIBLE
            this.tvNone.visibility = View.VISIBLE
            this.tvSubNone.visibility = View.VISIBLE
        }

        else if (!dataPosts.isNullOrEmpty()){
            this.tvSearchResultsArtworks.visibility = View.VISIBLE
            this.ivNone.visibility = View.GONE
            this.tvNone.visibility = View.GONE
            this.tvSubNone.visibility = View.GONE
        }
    }

    private fun initActionBar() {
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
    }

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
     *     ActivityCompat.requestPermissions(android.app.Activity, String[], int)</code>
     * @param permissions The requested permissions. Never null
     * @param grantResults The grant results for the corresponding permissions which is either <code>
     *     PackageManager.PERMISSION_GRANTED</code> or <code>PackageManager.PERMISSION_DENIED</code>.
     *     Never null
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