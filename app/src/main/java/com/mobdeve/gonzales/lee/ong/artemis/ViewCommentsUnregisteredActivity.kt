package com.mobdeve.gonzales.lee.ong.artemis

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.MenuItem
import android.view.View
import android.widget.EditText
import android.widget.ImageButton
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.NestedScrollView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.facebook.shimmer.ShimmerFrameLayout
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.floatingactionbutton.FloatingActionButton

class ViewCommentsUnregisteredActivity : AppCompatActivity() {
    private lateinit var dataComments: ArrayList<Comment>
    private lateinit var rvComments: RecyclerView
    private lateinit var unregisteredCommentsAdapter: UnregisteredCommentsAdapter
    private lateinit var llViewCommentsShimmer: LinearLayout
    private lateinit var sflViewComments: ShimmerFrameLayout
    private lateinit var bnvViewCommentsUnregisteredBottom: BottomNavigationView
    private lateinit var etAddCommentUnregistered: EditText

    private lateinit var ibAddComment: ImageButton

    private lateinit var fabAddPost: FloatingActionButton

    private lateinit var srlViewCommentsUnregisterd: SwipeRefreshLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_comments_unregistered)

        initComponents()
    }

    private fun initComponents() {
        setSupportActionBar(findViewById(R.id.toolbar_view_comments_unregistered))
        initShimmer()
        initActionBar()
        initBottom()
        initSwipeRefresh()

        disableEditText()
    }

    private fun initShimmer() {
        this.sflViewComments = findViewById(R.id.sfl_view_comments_unregistered)

        sflViewComments.startShimmer()

        Handler(Looper.getMainLooper()).postDelayed({
            initRecyclerView()
            sflViewComments.visibility = View.GONE
            rvComments.visibility = View.VISIBLE
        }, AnimationDuration.SHIMMER_TIMEOUT.toLong())
    }

    private fun initSwipeRefresh() {
        this.srlViewCommentsUnregisterd = findViewById(R.id.srl_view_comments_unregistered)
        srlViewCommentsUnregisterd.setOnRefreshListener {
            onRefresh();
        }

        srlViewCommentsUnregisterd.setColorSchemeResources(R.color.purple_main,
            R.color.pinkish_purple,
            R.color.purple_pics_lighter,
            R.color.pinkish_purple_lighter);
    }

    private fun onRefresh() {
        Handler(Looper.getMainLooper()).postDelayed({
            srlViewCommentsUnregisterd.isRefreshing = false
        }, AnimationDuration.REFRESH_TIMEOUT.toLong())
    }

    private fun initBottom() {
        this.bnvViewCommentsUnregisteredBottom = findViewById(R.id.nv_view_comments_unregistered_bottom)
        this.fabAddPost = findViewById(R.id.fab_view_comments_unregistered_add)

        bnvViewCommentsUnregisteredBottom.setOnItemSelectedListener{ item ->
            when (item.itemId) {
                R.id.icon_home_feed -> {
                    val intent = Intent(this@ViewCommentsUnregisteredActivity, BrowseFeedUnregisteredActivity::class.java)
                    startActivity(intent)
                    finish()
                    return@setOnItemSelectedListener true
                }
                R.id.icon_follow_feed -> {
                    Toast.makeText(this@ViewCommentsUnregisteredActivity,"Log in or create an account to use this feature", Toast.LENGTH_SHORT).show()
                }
                R.id.icon_bookmark_feed -> {
                    Toast.makeText(this@ViewCommentsUnregisteredActivity,"Log in or create an account to use this feature", Toast.LENGTH_SHORT).show()
                }
                R.id.icon_user_feed -> {
                    Toast.makeText(this@ViewCommentsUnregisteredActivity,"Log in or create an account to use this feature", Toast.LENGTH_SHORT).show()
                }
            }
            false
        }

        fabAddPost.setOnClickListener(View.OnClickListener {
            Toast.makeText(this@ViewCommentsUnregisteredActivity,"Log in or create an account to use this feature", Toast.LENGTH_SHORT).show()
        })
    }

    private fun initActionBar() {
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
    }

    private fun initRecyclerView() {
        this.dataComments = DataHelper.loadCommentDataUnregistered()

        this.rvComments = findViewById(R.id.rv_view_comments_unregistered)
        this.llViewCommentsShimmer = findViewById(R.id.ll_view_comments_unregistered_shimmer)

        this.rvComments.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)

        this.unregisteredCommentsAdapter = UnregisteredCommentsAdapter(this.dataComments)

        this.rvComments.adapter = unregisteredCommentsAdapter
    }

    private fun disableEditText() {
        this.etAddCommentUnregistered = findViewById(R.id.et_add_comment_unregistered)
        this.ibAddComment = findViewById(R.id.ib_add_comment)

        this.etAddCommentUnregistered.isEnabled = false
        this.ibAddComment.setOnClickListener(View.OnClickListener {
            Toast.makeText(this@ViewCommentsUnregisteredActivity,"Log in or create an account to use this feature", Toast.LENGTH_SHORT).show()
        })
    }
}