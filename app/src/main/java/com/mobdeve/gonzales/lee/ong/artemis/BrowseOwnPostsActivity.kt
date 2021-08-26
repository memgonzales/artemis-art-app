package com.mobdeve.gonzales.lee.ong.artemis

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import androidx.core.widget.NestedScrollView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.facebook.shimmer.ShimmerFrameLayout
import com.google.android.material.bottomnavigation.BottomNavigationView

class BrowseOwnPostsActivity : AppCompatActivity() {
    private lateinit var dataPosts: ArrayList<Post>
    private lateinit var rvBrowseOwnPosts: RecyclerView
    private lateinit var ownPostsAdapter: OwnPostsAdapter
    private lateinit var sflBrowseOwnPosts: ShimmerFrameLayout
    private lateinit var bnvBrowseOwnPostsBottom: BottomNavigationView

    private lateinit var srlBrowseOwnPosts: SwipeRefreshLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_browse_own_posts)

        initComponents()
    }

    private fun initComponents() {
        setSupportActionBar(findViewById(R.id.toolbar_browse_own_posts))
        initActionBar()
        initShimmer()
        initBottom()
        initSwipeRefresh()
    }

    private fun initShimmer() {
        this.sflBrowseOwnPosts = findViewById(R.id.sfl_browse_own_posts)

        sflBrowseOwnPosts.startShimmer()

        Handler(Looper.getMainLooper()).postDelayed({
            initRecyclerView()
            sflBrowseOwnPosts.visibility = View.GONE
            rvBrowseOwnPosts.visibility = View.VISIBLE
        }, AnimationDuration.SHIMMER_TIMEOUT.toLong())
    }

    private fun initSwipeRefresh() {
        this.srlBrowseOwnPosts = findViewById(R.id.srl_browse_own_posts)
        srlBrowseOwnPosts.setOnRefreshListener {
            onRefresh();
        }

        srlBrowseOwnPosts.setColorSchemeResources(R.color.purple_main,
            R.color.pinkish_purple,
            R.color.purple_pics_lighter,
            R.color.pinkish_purple_lighter);
    }

    private fun onRefresh() {
        Handler(Looper.getMainLooper()).postDelayed({
            srlBrowseOwnPosts.isRefreshing = false
        }, AnimationDuration.REFRESH_TIMEOUT.toLong())
    }

    private fun initBottom() {
        this.bnvBrowseOwnPostsBottom = findViewById(R.id.nv_browse_own_posts_bottom)

        bnvBrowseOwnPostsBottom.setOnItemSelectedListener{ item ->
            when (item.itemId) {
                R.id.icon_home_profile -> {
                    val intent = Intent(this@BrowseOwnPostsActivity, BrowseFeedActivity::class.java)
                    startActivity(intent)
                    return@setOnItemSelectedListener true
                }
                R.id.icon_follow_profile -> {
                    val intent = Intent(this@BrowseOwnPostsActivity, BrowseFeedFollowedActivity::class.java)
                    startActivity(intent)
                    return@setOnItemSelectedListener true
                }
                R.id.icon_bookmark_profile -> {
                    val intent = Intent(this@BrowseOwnPostsActivity, BrowseBookmarksActivity::class.java)
                    startActivity(intent)
                    return@setOnItemSelectedListener true
                }
                R.id.icon_user_profile -> {
                    val intent = Intent(this@BrowseOwnPostsActivity, ViewProfileActivity::class.java)
                    startActivity(intent)
                    return@setOnItemSelectedListener true
                }
            }
            false
        }
    }

    private fun initRecyclerView() {
        this.dataPosts = DataHelper.loadOwnPostsData();

        this.rvBrowseOwnPosts = findViewById(R.id.rv_browse_own_posts);
        this.rvBrowseOwnPosts.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);

        this.ownPostsAdapter = OwnPostsAdapter(this.dataPosts);


        this.rvBrowseOwnPosts.adapter = ownPostsAdapter;
    }

    private fun initActionBar() {
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
    }
}