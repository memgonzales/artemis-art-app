package com.mobdeve.gonzales.lee.ong.artemis

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.ScrollView
import androidx.core.widget.NestedScrollView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.facebook.shimmer.ShimmerFrameLayout
import com.google.android.material.bottomnavigation.BottomNavigationView

class BrowseOwnHighlightsActivity : AppCompatActivity() {
    private lateinit var dataPosts: ArrayList<Post>
    private lateinit var rvHighlights: RecyclerView
    private lateinit var highlightsAdapter: HighlightsAdapter
    private lateinit var sflHighlights: ShimmerFrameLayout
    private lateinit var bnvHighlightsBottom: BottomNavigationView
    private lateinit var nsvHighlights: NestedScrollView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_highlights)

        initComponents()
    }

    private fun initComponents() {
        setSupportActionBar(findViewById(R.id.toolbar_highlights))
        initActionBar()
        initShimmer()
        initBottom()
    }

    private fun initShimmer() {
        this.sflHighlights = findViewById(R.id.sfl_highlights)

        sflHighlights.startShimmer()

        Handler(Looper.getMainLooper()).postDelayed({
            initRecyclerView()
            sflHighlights.visibility = View.GONE
            rvHighlights.visibility = View.VISIBLE
        }, AnimationDuration.SHIMMER_TIMEOUT.toLong())
    }

    private fun initBottom() {
        this.bnvHighlightsBottom = findViewById(R.id.nv_highlights_bottom)
        this.nsvHighlights = findViewById(R.id.nsv_highlights)

        bnvHighlightsBottom.setOnItemSelectedListener{ item ->
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
        this.dataPosts = DataHelper.loadHighlightsData();

        this.rvHighlights = findViewById(R.id.rv_highlights);
        this.rvHighlights.layoutManager = GridLayoutManager(this, 2);

        this.highlightsAdapter = HighlightsAdapter(this.dataPosts);


        this.rvHighlights.adapter = highlightsAdapter;
    }

    private fun initActionBar() {
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
    }
}