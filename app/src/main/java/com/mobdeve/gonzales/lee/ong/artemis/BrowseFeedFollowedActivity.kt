package com.mobdeve.gonzales.lee.ong.artemis

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.widget.NestedScrollView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.facebook.shimmer.ShimmerFrameLayout
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.floatingactionbutton.FloatingActionButton

class BrowseFeedFollowedActivity : AppCompatActivity() {
    private lateinit var dataPosts: ArrayList<Post>
    private lateinit var rvFollowed: RecyclerView
    private lateinit var feedFollowedAdapter: FeedFollowedAdapter
    private lateinit var sflFollowed: ShimmerFrameLayout
    private lateinit var bnvFollowedBottom: BottomNavigationView
    private lateinit var nsvFollowed: NestedScrollView

    private lateinit var srlFeedFollowed: SwipeRefreshLayout

    private lateinit var btmAddPost: BottomSheetDialog
    private lateinit var fabAddPost: FloatingActionButton
    private lateinit var clDialogPostArtworkGallery: ConstraintLayout
    private lateinit var clDialogPostArtworkPhoto: ConstraintLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_browse_feed_followed)

        initComponents()
    }

    private fun initComponents() {
        setSupportActionBar(findViewById(R.id.toolbar_feed_followed))
        initShimmer()
        initBottom()
        addPost()
        initSwipeRefresh()
    }

    private fun initShimmer() {
        this.sflFollowed = findViewById(R.id.sfl_feed_followed)

        sflFollowed.startShimmer()

        Handler(Looper.getMainLooper()).postDelayed({
            initRecyclerView()
            sflFollowed.visibility = View.GONE
            rvFollowed.visibility = View.VISIBLE
        }, AnimationDuration.SHIMMER_TIMEOUT.toLong())
    }

    private fun initSwipeRefresh() {
        this.srlFeedFollowed = findViewById(R.id.srl_feed_followed)
        srlFeedFollowed.setOnRefreshListener {
            onRefresh();
        }

        srlFeedFollowed.setColorSchemeResources(R.color.purple_main,
            R.color.pinkish_purple,
            R.color.purple_pics_lighter,
            R.color.pinkish_purple_lighter);
    }

    private fun onRefresh() {
        Handler(Looper.getMainLooper()).postDelayed({
            srlFeedFollowed.isRefreshing = false
        }, AnimationDuration.REFRESH_TIMEOUT.toLong())
    }

    private fun initBottom() {
        this.bnvFollowedBottom = findViewById(R.id.nv_feed_followed_bottom)
        this.nsvFollowed = findViewById(R.id.nsv_feed_followed)

        BottomMenuUtil.setScrollBottomMenuListeners(bnvFollowedBottom, nsvFollowed,
            BottomMenuUtil.FOLLOW, this, this@BrowseFeedFollowedActivity)
    }

    private fun initRecyclerView() {
        this.dataPosts = DataHelper.loadFollowedData();

        this.rvFollowed = findViewById(R.id.rv_feed_followed);
        this.rvFollowed.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);

        this.feedFollowedAdapter = FeedFollowedAdapter(dataPosts, this@BrowseFeedFollowedActivity);


        this.rvFollowed.adapter = feedFollowedAdapter;
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
        val intent = Intent(this@BrowseFeedFollowedActivity, SearchActivity::class.java)
        startActivity(intent)
    }

    private fun addPost() {
        this.btmAddPost = BottomSheetDialog(this@BrowseFeedFollowedActivity)
        this.fabAddPost = findViewById(R.id.fab_feed_followed_add)

        val view = LayoutInflater.from(this@BrowseFeedFollowedActivity).inflate(R.layout.dialog_post_artwork, null)

        this.fabAddPost.setOnClickListener {
            btmAddPost.setContentView(view)

            this.clDialogPostArtworkGallery = btmAddPost.findViewById(R.id.cl_dialog_post_artwork_gallery)!!
            this.clDialogPostArtworkPhoto = btmAddPost.findViewById(R.id.cl_dialog_post_artwork_photo)!!

            clDialogPostArtworkGallery.setOnClickListener(View.OnClickListener {
                Toast.makeText(this@BrowseFeedFollowedActivity, "Photo chosen from the gallery", Toast.LENGTH_SHORT).show()
                btmAddPost.dismiss()
                val intent = Intent(this@BrowseFeedFollowedActivity, PostArtworkActivity::class.java)
                startActivity(intent)
            })

            clDialogPostArtworkPhoto.setOnClickListener(View.OnClickListener {
                Toast.makeText(this@BrowseFeedFollowedActivity, "Photo taken with the device camera", Toast.LENGTH_SHORT).show()
                btmAddPost.dismiss()
                val intent = Intent(this@BrowseFeedFollowedActivity, PostArtworkActivity::class.java)
                startActivity(intent)
            })

            btmAddPost.show()
        }
    }
}