package com.mobdeve.gonzales.lee.ong.artemis

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.ScrollView
import android.widget.Toast
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

class BrowseBookmarksActivity : AppCompatActivity() {
    private lateinit var dataPosts: ArrayList<Post>
    private lateinit var rvBookmarks: RecyclerView
    private lateinit var bookmarksAdapter: BookmarksAdapter
    private lateinit var sflBookmarks: ShimmerFrameLayout
    private lateinit var bnvBookmarksBottom: BottomNavigationView
    private lateinit var nsvBookmarks: NestedScrollView

    private lateinit var srlBookmarks: SwipeRefreshLayout

    private lateinit var btmAddPost: BottomSheetDialog
    private lateinit var fabAddPost: FloatingActionButton
    private lateinit var clDialogPostArtworkGallery: ConstraintLayout
    private lateinit var clDialogPostArtworkPhoto: ConstraintLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_browse_bookmarks)

        initComponents()
    }

    private fun initComponents() {
        setSupportActionBar(findViewById(R.id.toolbar_bookmarks))
        initShimmer()
        initBottom()
        addPost()
        initSwipeRefresh()
    }

    private fun initShimmer() {
        this.sflBookmarks = findViewById(R.id.sfl_bookmarks)

        sflBookmarks.startShimmer()

        Handler(Looper.getMainLooper()).postDelayed({
            initRecyclerView()
            sflBookmarks.visibility = View.GONE
            rvBookmarks.visibility = View.VISIBLE
        }, AnimationDuration.SHIMMER_TIMEOUT.toLong())
    }

    private fun initBottom() {
        this.bnvBookmarksBottom = findViewById(R.id.nv_bookmarks_bottom)
        this.nsvBookmarks = findViewById(R.id.nsv_bookmarks)

        BottomMenuUtil.setScrollBottomBottomMenuListeners(bnvBookmarksBottom, nsvBookmarks,
            BottomMenuUtil.BOOKMARK, this, this@BrowseBookmarksActivity)
    }

    private fun initSwipeRefresh() {
        this.srlBookmarks = findViewById(R.id.srl_bookmarks)
        srlBookmarks.setOnRefreshListener {
            onRefresh();
        }

        srlBookmarks.setColorSchemeResources(R.color.purple_main,
            R.color.pinkish_purple,
            R.color.purple_pics_lighter,
            R.color.pinkish_purple_lighter);
    }

    private fun onRefresh() {
        Handler(Looper.getMainLooper()).postDelayed({
            srlBookmarks.isRefreshing = false
        }, AnimationDuration.REFRESH_TIMEOUT.toLong())
    }

    private fun initRecyclerView() {
        this.dataPosts = DataHelper.loadBookmarkData();

        this.rvBookmarks = findViewById(R.id.rv_bookmarks);
        this.rvBookmarks.layoutManager = GridLayoutManager(this, 2);

        this.bookmarksAdapter = BookmarksAdapter(this.dataPosts);


        this.rvBookmarks.adapter = bookmarksAdapter;
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.menu_top_with_search, menu)

        return true
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
        val intent = Intent(this@BrowseBookmarksActivity, SearchActivity::class.java)
        startActivity(intent)
    }

    private fun addPost() {
        this.btmAddPost = BottomSheetDialog(this@BrowseBookmarksActivity)
        this.fabAddPost = findViewById(R.id.fab_bookmarks_add)

        val view = LayoutInflater.from(this@BrowseBookmarksActivity).inflate(R.layout.dialog_post_artwork, null)

        this.fabAddPost.setOnClickListener {
            btmAddPost.setContentView(view)

            this.clDialogPostArtworkGallery = btmAddPost.findViewById(R.id.cl_dialog_post_artwork_gallery)!!
            this.clDialogPostArtworkPhoto = btmAddPost.findViewById(R.id.cl_dialog_post_artwork_photo)!!

            clDialogPostArtworkGallery.setOnClickListener(View.OnClickListener {
                Toast.makeText(this@BrowseBookmarksActivity, "Photo chosen from the gallery", Toast.LENGTH_SHORT).show()
                btmAddPost.dismiss()
                val intent = Intent(this@BrowseBookmarksActivity, PostArtworkActivity::class.java)
                startActivity(intent)
            })

            clDialogPostArtworkPhoto.setOnClickListener(View.OnClickListener {
                Toast.makeText(this@BrowseBookmarksActivity, "Photo taken with the device camera", Toast.LENGTH_SHORT).show()
                btmAddPost.dismiss()
                val intent = Intent(this@BrowseBookmarksActivity, PostArtworkActivity::class.java)
                startActivity(intent)
            })

            btmAddPost.show()
        }
    }
}