package com.mobdeve.gonzales.lee.ong.artemis

import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.os.Handler
import android.os.Looper
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.ImageView
import android.widget.ScrollView
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.core.widget.NestedScrollView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.facebook.shimmer.ShimmerFrameLayout
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.floatingactionbutton.FloatingActionButton
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

    private lateinit var photoFile: File

    private var cameraLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            result: ActivityResult ->
        if (result.resultCode == Activity.RESULT_OK) {
            val intent = Intent(this@BrowseFeedActivity, PostArtworkActivity::class.java)
            intent.putExtra(
                Keys.KEY_POST_ARTWORK.name,
                photoFile?.absolutePath
            )

             startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_browse_feed)

        initComponents()
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

        bnvFeedBottom.setOnItemSelectedListener{ item ->
            when (item.itemId) {
                R.id.icon_home_feed -> {
                    nsvFeed.fullScroll(ScrollView.FOCUS_UP)
                    return@setOnItemSelectedListener true
                }
                R.id.icon_follow_feed -> {
                    val intent = Intent(this@BrowseFeedActivity, BrowseFeedFollowedActivity::class.java)
                    startActivity(intent)
                    return@setOnItemSelectedListener true
                }
                R.id.icon_bookmark_feed -> {
                    val intent = Intent(this@BrowseFeedActivity, BrowseBookmarksActivity::class.java)
                    startActivity(intent)
                    return@setOnItemSelectedListener true
                }
                R.id.icon_user_feed -> {
                    val intent = Intent(this@BrowseFeedActivity, ViewProfileActivity::class.java)
                    startActivity(intent)
                    return@setOnItemSelectedListener true
                }
            }
            false
        }
    }

    private fun initRecyclerView() {
        this.dataPosts = DataHelper.loadPostData();

        this.rvFeed = findViewById(R.id.rv_feed);
        this.rvFeed.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);

        this.feedAdapter = FeedAdapter(this.dataPosts);


        this.rvFeed.adapter = feedAdapter;
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
        val id = item.itemId

        when(id) {
            R.id.menu_feed_search -> {
                launchSearch()
                return true
            } else -> {
            return super.onOptionsItemSelected(item)
        }
        }

        return super.onOptionsItemSelected(item)
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

            clDialogPostArtworkGallery.setOnClickListener(View.OnClickListener {
                Toast.makeText(this@BrowseFeedActivity, "Photo chosen from the gallery", Toast.LENGTH_SHORT).show()
                btmAddPost.dismiss()
                val intent = Intent(this@BrowseFeedActivity, PostArtworkActivity::class.java)
                startActivity(intent)
            })

            clDialogPostArtworkPhoto.setOnClickListener(View.OnClickListener {
                val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                val permissions = arrayOf(android.Manifest.permission.CAMERA,
                                            android.Manifest.permission.READ_EXTERNAL_STORAGE,
                                            android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                photoFile = getPhotoFile(DefaultStrings.PHOTO_DEFAULT_FILE_NAME)

                val fileProvider = FileProvider.getUriForFile(this, DefaultStrings.PACKAGE_NAME, photoFile!!)
                intent.putExtra(MediaStore.EXTRA_OUTPUT, fileProvider)

                if (ContextCompat.checkSelfPermission(this.applicationContext, permissions[0]) != PackageManager.PERMISSION_GRANTED
                    || ContextCompat.checkSelfPermission(this.applicationContext, permissions[1]) != PackageManager.PERMISSION_GRANTED
                    || ContextCompat.checkSelfPermission(this.applicationContext, permissions[2]) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(this@BrowseFeedActivity, permissions, RequestCodes.REQUEST_CODE_POST_CAMERA.ordinal)

                } else {
                    if (intent.resolveActivity(this@BrowseFeedActivity.packageManager) != null) {
                        cameraLauncher.launch(intent)
                    } else {
                        Toast.makeText(
                            this@BrowseFeedActivity,
                            "Camera app cannot be found",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            })

            btmAddPost.show()
        }
    }

    private fun getPhotoFile(name: String): File {
        val storageDirectory = getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        return File.createTempFile(name, ".jpg", storageDirectory)
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        when (requestCode) {
            RequestCodes.REQUEST_CODE_POST_CAMERA.ordinal -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED
                    && grantResults[1] == PackageManager.PERMISSION_GRANTED
                    && grantResults[2] == PackageManager.PERMISSION_GRANTED) {
                    val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE);

                    if (intent.resolveActivity(this@BrowseFeedActivity.packageManager) != null) {
                        cameraLauncher.launch(intent)
                    } else {
                        Toast.makeText(
                            this@BrowseFeedActivity,
                            "Camera app cannot be found",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                } else {
                    Toast.makeText(
                        this@BrowseFeedActivity,
                        "Insufficient permissions to take a photo",
                        Toast.LENGTH_LONG
                    ).show()
                }
                return
            }
        }
    }
}