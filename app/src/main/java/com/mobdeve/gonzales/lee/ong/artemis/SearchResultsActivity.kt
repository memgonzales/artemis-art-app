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
    private lateinit var searchAdapter: SearchResultsAdapter
    private lateinit var sflSearch: ShimmerFrameLayout
    private lateinit var bnvSearchBottom: BottomNavigationView

    private lateinit var btmAddPost: BottomSheetDialog
    private lateinit var fabAddPost: FloatingActionButton
    private lateinit var clDialogPostArtworkGallery: ConstraintLayout
    private lateinit var clDialogPostArtworkPhoto: ConstraintLayout

    private lateinit var srlSearchResults: SwipeRefreshLayout

    private lateinit var etSearchBar: EditText

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
            tvSearchResultsArtworks.visibility = View.VISIBLE
            rvSearch.visibility = View.VISIBLE
            civSearchResultUser1.visibility = View.VISIBLE
            civSearchResultUser2.visibility = View.VISIBLE
            civSearchResultUser3.visibility = View.VISIBLE
            civSearchResultUser4.visibility = View.VISIBLE
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
        this.dataUsers = DataHelper.loadSearchUserData();

        this.civSearchResultUser1 = findViewById(R.id.civ_search_result_user1)
        this.civSearchResultUser2 = findViewById(R.id.civ_search_result_user2)
        this.civSearchResultUser3 = findViewById(R.id.civ_search_result_user3)
        this.civSearchResultUser4 = findViewById(R.id.civ_search_result_user4)

        this.tvSearchResultsArtworks = findViewById(R.id.tv_search_results_artworks)

        //this.civSearchResultUser1.setImageResource(dataUsers[0].getUserImg())
        //this.civSearchResultUser2.setImageResource(dataUsers[1].getUserImg())
        //this.civSearchResultUser3.setImageResource(dataUsers[2].getUserImg())
        //this.civSearchResultUser4.setImageResource(dataUsers[3].getUserImg())

        civSearchResultUser1.setOnClickListener(View.OnClickListener {
            val intent = Intent(this@SearchResultsActivity, ViewUserActivity::class.java)
            intent.putExtra(
                Keys.KEY_PROFILE_PICTURE.name,
                dataUsers[0].getUserImg()
            )
            intent.putExtra(
                Keys.KEY_USERNAME.name,
                dataUsers[0].getUsername()
            )
            intent.putExtra(
                Keys.KEY_BIO.name,
                dataUsers[0].getBio()
            )
            startActivity(intent)
        })

        civSearchResultUser2.setOnClickListener(View.OnClickListener {
            val intent = Intent(this@SearchResultsActivity, ViewUserActivity::class.java)
            intent.putExtra(
                Keys.KEY_PROFILE_PICTURE.name,
                dataUsers[1].getUserImg()
            )
            intent.putExtra(
                Keys.KEY_USERNAME.name,
                dataUsers[1].getUsername()
            )
            intent.putExtra(
                Keys.KEY_BIO.name,
                dataUsers[1].getBio()
            )
            startActivity(intent)
        })

        civSearchResultUser3.setOnClickListener(View.OnClickListener {
            val intent = Intent(this@SearchResultsActivity, ViewUserActivity::class.java)
            intent.putExtra(
                Keys.KEY_PROFILE_PICTURE.name,
                dataUsers[2].getUserImg()
            )
            intent.putExtra(
                Keys.KEY_USERNAME.name,
                dataUsers[2].getUsername()
            )
            intent.putExtra(
                Keys.KEY_BIO.name,
                dataUsers[2].getBio()
            )
            startActivity(intent)
        })

        civSearchResultUser4.setOnClickListener(View.OnClickListener {
            val intent = Intent(this@SearchResultsActivity, ViewUserActivity::class.java)
            intent.putExtra(
                Keys.KEY_PROFILE_PICTURE.name,
                dataUsers[3].getUserImg()
            )
            intent.putExtra(
                Keys.KEY_USERNAME.name,
                dataUsers[3].getUsername()
            )
            intent.putExtra(
                Keys.KEY_BIO.name,
                dataUsers[3].getBio()
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
                startActivity(intent)
            }
            return@OnEditorActionListener false
        });
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