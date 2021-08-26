package com.mobdeve.gonzales.lee.ong.artemis

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.ScrollView
import android.widget.Toast
import androidx.core.widget.NestedScrollView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.facebook.shimmer.ShimmerFrameLayout
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.floatingactionbutton.FloatingActionButton

class BrowseFeedUnregisteredActivity : AppCompatActivity() {
    private lateinit var dataPosts: ArrayList<Post>
    private lateinit var rvFeed: RecyclerView
    private lateinit var unregisteredFeedAdapter: UnregisteredFeedAdapter
    private lateinit var sflFeed: ShimmerFrameLayout
    private lateinit var bnvFeedBottom: BottomNavigationView
    private lateinit var nsvFeed: NestedScrollView

    private lateinit var srlFeedUnregistered: SwipeRefreshLayout

    private lateinit var fabAddPost: FloatingActionButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_browse_feed_unregistered)

        initComponents()
    }

    private fun initComponents() {
        setSupportActionBar(findViewById(R.id.toolbar_feed_unregistered))
        initShimmer()
        initBottom()
        initSwipeRefresh()

        addPost()
    }

    private fun initShimmer() {
        this.sflFeed = findViewById(R.id.sfl_feed_unregistered)

        sflFeed.startShimmer()

        Handler(Looper.getMainLooper()).postDelayed({
            initRecyclerView()
            sflFeed.visibility = View.GONE
            rvFeed.visibility = View.VISIBLE
        }, AnimationDuration.SHIMMER_TIMEOUT.toLong())
    }

    private fun initSwipeRefresh() {
        this.srlFeedUnregistered = findViewById(R.id.srl_feed_unregistered)
        srlFeedUnregistered.setOnRefreshListener {
            onRefresh();
        }

        srlFeedUnregistered.setColorSchemeResources(R.color.purple_main,
            R.color.pinkish_purple,
            R.color.purple_pics_lighter,
            R.color.pinkish_purple_lighter);
    }

    private fun onRefresh() {
        Handler(Looper.getMainLooper()).postDelayed({
            srlFeedUnregistered.isRefreshing = false
        }, AnimationDuration.REFRESH_TIMEOUT.toLong())
    }

    private fun initBottom() {
        this.bnvFeedBottom = findViewById(R.id.nv_feed_unregistered_bottom)
        this.nsvFeed = findViewById(R.id.nsv_feed_unregistered)

        bnvFeedBottom.setOnItemSelectedListener{ item ->
            when (item.itemId) {
                R.id.icon_home_feed -> {
                    nsvFeed.fullScroll(ScrollView.FOCUS_UP)
                    return@setOnItemSelectedListener true
                }
                R.id.icon_follow_feed -> {
                    Toast.makeText(this@BrowseFeedUnregisteredActivity,"Log in or create an account to use this feature", Toast.LENGTH_SHORT).show()
                }
                R.id.icon_bookmark_feed -> {
                    Toast.makeText(this@BrowseFeedUnregisteredActivity,"Log in or create an account to use this feature", Toast.LENGTH_SHORT).show()
                }
                R.id.icon_user_feed -> {
                    Toast.makeText(this@BrowseFeedUnregisteredActivity,"Log in or create an account to use this feature", Toast.LENGTH_SHORT).show()
                }
            }
            false
        }
    }

    private fun initRecyclerView() {
        this.dataPosts = DataHelper.loadPostDataUnregistered();

        this.rvFeed = findViewById(R.id.rv_feed_unregistered);
        this.rvFeed.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);

        this.unregisteredFeedAdapter = UnregisteredFeedAdapter(this.dataPosts);


        this.rvFeed.adapter = unregisteredFeedAdapter;
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
        val intent = Intent(this@BrowseFeedUnregisteredActivity, SearchUnregisteredActivity::class.java)
        startActivity(intent)
    }

    private fun addPost(){
        this.fabAddPost = findViewById(R.id.fab_feed_unregistered_add)

        this.fabAddPost.setOnClickListener {
            Toast.makeText(this@BrowseFeedUnregisteredActivity,"Log in or create an account to use this feature", Toast.LENGTH_SHORT).show()
        }
    }
}