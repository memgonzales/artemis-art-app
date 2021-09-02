package com.mobdeve.gonzales.lee.ong.artemis

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.facebook.shimmer.ShimmerFrameLayout
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.floatingactionbutton.FloatingActionButton

class BrowseOwnHighlightsActivity : AppCompatActivity() {
    private lateinit var dataPosts: ArrayList<Post>
    private lateinit var rvHighlights: RecyclerView
    private lateinit var highlightsAdapter: HighlightsAdapter
    private lateinit var sflHighlights: ShimmerFrameLayout
    private lateinit var bnvHighlightsBottom: BottomNavigationView

    private lateinit var btmAddPost: BottomSheetDialog
    private lateinit var fabAddPost: FloatingActionButton
    private lateinit var clDialogPostArtworkGallery: ConstraintLayout
    private lateinit var clDialogPostArtworkPhoto: ConstraintLayout

    private lateinit var srlHighlights: SwipeRefreshLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_browse_own_highlights)

        initComponents()
    }

    private fun initComponents() {
        setSupportActionBar(findViewById(R.id.toolbar_highlights))
        initActionBar()
        initShimmer()
        initBottom()
        addPost()
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

        BottomMenuUtil.setBottomMenuListeners(bnvHighlightsBottom, this,
            this@BrowseOwnHighlightsActivity)
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

    private fun addPost() {
        this.btmAddPost = BottomSheetDialog(this@BrowseOwnHighlightsActivity)
        this.fabAddPost = findViewById(R.id.fab_browse_highlights_add)

        val view = LayoutInflater.from(this@BrowseOwnHighlightsActivity).inflate(R.layout.dialog_post_artwork, null)

        this.fabAddPost.setOnClickListener {
            btmAddPost.setContentView(view)

            this.clDialogPostArtworkGallery = btmAddPost.findViewById(R.id.cl_dialog_post_artwork_gallery)!!
            this.clDialogPostArtworkPhoto = btmAddPost.findViewById(R.id.cl_dialog_post_artwork_photo)!!

            clDialogPostArtworkGallery.setOnClickListener(View.OnClickListener {
                Toast.makeText(this@BrowseOwnHighlightsActivity, "Photo chosen from the gallery", Toast.LENGTH_SHORT).show()
                btmAddPost.dismiss()
                val intent = Intent(this@BrowseOwnHighlightsActivity, PostArtworkActivity::class.java)
                startActivity(intent)
            })

            clDialogPostArtworkPhoto.setOnClickListener(View.OnClickListener {
                Toast.makeText(this@BrowseOwnHighlightsActivity, "Photo taken with the device camera", Toast.LENGTH_SHORT).show()
                btmAddPost.dismiss()
                val intent = Intent(this@BrowseOwnHighlightsActivity, PostArtworkActivity::class.java)
                startActivity(intent)
            })

            btmAddPost.show()
        }
    }
}