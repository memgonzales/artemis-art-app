package com.mobdeve.gonzales.lee.ong.artemis

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.MenuItem
import android.view.View
import androidx.core.widget.NestedScrollView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.facebook.shimmer.ShimmerFrameLayout
import com.google.android.material.bottomnavigation.BottomNavigationView

class BrowseOwnHighlightsActivity : AppCompatActivity() {
    private lateinit var dataPosts: ArrayList<Post>
    private lateinit var rvHighlights: RecyclerView
    private lateinit var highlightsAdapter: HighlightsAdapter
    private lateinit var sflHighlights: ShimmerFrameLayout
    private lateinit var bnvHighlightsBottom: BottomNavigationView
    private lateinit var nsvHighlights: NestedScrollView

    private lateinit var srlHighlights: SwipeRefreshLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_browse_highlights)

        initComponents()
    }

    private fun initComponents() {
        setSupportActionBar(findViewById(R.id.toolbar_highlights))
        initActionBar()
        initShimmer()
        initBottom()
        initSwipeRefresh()
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

    private fun initSwipeRefresh() {
        this.srlHighlights = findViewById(R.id.srl_highlights)
        srlHighlights.setOnRefreshListener {
            onRefresh();
        }

        srlHighlights.setColorSchemeResources(R.color.purple_main,
            R.color.pinkish_purple,
            R.color.purple_pics_lighter,
            R.color.pinkish_purple_lighter);
    }

    private fun onRefresh() {
        Handler(Looper.getMainLooper()).postDelayed({
            srlHighlights.isRefreshing = false
        }, AnimationDuration.REFRESH_TIMEOUT.toLong())
    }

    private fun initBottom() {
        this.bnvHighlightsBottom = findViewById(R.id.nv_highlights_bottom)
        this.nsvHighlights = findViewById(R.id.nsv_highlights)

        bnvHighlightsBottom.setOnItemSelectedListener{ item ->
            when (item.itemId) {
                R.id.icon_home_profile -> {
                    val intent = Intent(this@BrowseOwnHighlightsActivity, BrowseFeedActivity::class.java)
                    startActivity(intent)
                    return@setOnItemSelectedListener true
                }
                R.id.icon_follow_profile -> {
                    val intent = Intent(this@BrowseOwnHighlightsActivity, BrowseFeedFollowedActivity::class.java)
                    startActivity(intent)
                    return@setOnItemSelectedListener true
                }
                R.id.icon_bookmark_profile -> {
                    val intent = Intent(this@BrowseOwnHighlightsActivity, BrowseBookmarksActivity::class.java)
                    startActivity(intent)
                    return@setOnItemSelectedListener true
                }
                R.id.icon_user_profile -> {
                    val intent = Intent(this@BrowseOwnHighlightsActivity, ViewProfileActivity::class.java)
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