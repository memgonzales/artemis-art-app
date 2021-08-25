package com.mobdeve.gonzales.lee.ong.artemis

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.ScrollView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.NestedScrollView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.facebook.shimmer.ShimmerFrameLayout
import com.google.android.material.bottomnavigation.BottomNavigationView

class BrowseBookmarksActivity : AppCompatActivity() {
    private lateinit var dataPosts: ArrayList<Post>
    private lateinit var rvBookmarks: RecyclerView
    private lateinit var bookmarksAdapter: BookmarksAdapter
    private lateinit var sflBookmarks: ShimmerFrameLayout
    private lateinit var bnvBookmarksBottom: BottomNavigationView
    private lateinit var nsvBookmarks: NestedScrollView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_browse_bookmarks)

        initComponents()
    }

    private fun initComponents() {
        setSupportActionBar(findViewById(R.id.toolbar_bookmarks))
        initShimmer()
        initBottom()
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

        bnvBookmarksBottom.setOnItemSelectedListener{ item ->
            when (item.itemId) {
                R.id.icon_home_bookmarks -> {
                    val intent = Intent(this@BrowseBookmarksActivity, BrowseFeedActivity::class.java)
                    startActivity(intent)
                    return@setOnItemSelectedListener true
                }
                R.id.icon_follow_bookmarks -> {
                    val intent = Intent(this@BrowseBookmarksActivity, BrowseFeedFollowedActivity::class.java)
                    startActivity(intent)
                    return@setOnItemSelectedListener true
                }
                R.id.icon_bookmark_bookmarks -> {
                    nsvBookmarks.fullScroll(ScrollView.FOCUS_UP)
                    return@setOnItemSelectedListener true
                }
                R.id.icon_user_bookmarks -> {
                    val intent = Intent(this@BrowseBookmarksActivity, ViewProfileActivity::class.java)
                    startActivity(intent)
                    return@setOnItemSelectedListener true
                }
            }
            false
        }
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
}