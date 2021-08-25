package com.mobdeve.gonzales.lee.ong.artemis

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import android.widget.ScrollView
import android.widget.TextView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.facebook.shimmer.ShimmerFrameLayout
import com.google.android.material.bottomnavigation.BottomNavigationView
import de.hdodenhof.circleimageview.CircleImageView

class SearchResultsActivity : AppCompatActivity() {
    private lateinit var dataPosts: ArrayList<Post>
    private lateinit var dataUsers: ArrayList<User>
    private lateinit var rvSearch: RecyclerView
    private lateinit var civSearchResultUser1: CircleImageView
    private lateinit var civSearchResultUser2: CircleImageView
    private lateinit var civSearchResultUser3: CircleImageView
    private lateinit var civSearchResultUser4: CircleImageView
    private lateinit var civSearchResultUser5: CircleImageView
    private lateinit var searchAdapter: SearchResultsAdapter
    private lateinit var sflSearch: ShimmerFrameLayout
    private lateinit var bnvSearchBottom: BottomNavigationView

    private lateinit var etSearchBar: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search_results)

        initComponents()
    }

    private fun initComponents() {
        setSupportActionBar(findViewById(R.id.toolbar_search_results))
        initShimmer()
        initBottom()
        initActionBar()

        this.etSearchBar = findViewById(R.id.et_search_results_bar)

        etSearchBar.setOnEditorActionListener(TextView.OnEditorActionListener { v, actionId, event ->
            if ((event != null && (event.keyCode == KeyEvent.KEYCODE_ENTER)) || (actionId == EditorInfo.IME_ACTION_DONE)) {
                val intent = Intent(this@SearchResultsActivity, SearchResultsActivity::class.java)
                startActivity(intent)
            }
            return@OnEditorActionListener false
        });
    }

    private fun initShimmer() {
        this.sflSearch = findViewById(R.id.sfl_search_results)

        sflSearch.startShimmer()

        Handler(Looper.getMainLooper()).postDelayed({
            initContents()
            sflSearch.visibility = View.GONE
            rvSearch.visibility = View.VISIBLE
        //    civSearchResultUser1.visibility = View.VISIBLE
        //    civSearchResultUser2.visibility = View.VISIBLE
        //    civSearchResultUser3.visibility = View.VISIBLE
        //    civSearchResultUser4.visibility = View.VISIBLE
        //    civSearchResultUser5.visibility = View.VISIBLE
        }, AnimationDuration.SHIMMER_TIMEOUT.toLong())
    }

    private fun initBottom() {
        this.bnvSearchBottom= findViewById(R.id.nv_search_results_bottom)

        bnvSearchBottom.setOnItemSelectedListener{ item ->
            when (item.itemId) {
                R.id.icon_home_search_bottom -> {
                    val intent = Intent(this@SearchResultsActivity, BrowseFeedActivity::class.java)
                    startActivity(intent)
                    return@setOnItemSelectedListener true
                }
                R.id.icon_follow_search_bottom -> {
                    val intent = Intent(this@SearchResultsActivity, BrowseFeedFollowedActivity::class.java)
                    startActivity(intent)
                    return@setOnItemSelectedListener true
                }
                R.id.icon_bookmark_search_bottom -> {
                    val intent = Intent(this@SearchResultsActivity, BrowseBookmarksActivity::class.java)
                    startActivity(intent)
                    return@setOnItemSelectedListener true
                }
                R.id.icon_user_search_bottom -> {
                    val intent = Intent(this@SearchResultsActivity, ViewProfileActivity::class.java)
                    startActivity(intent)
                    return@setOnItemSelectedListener true
                }
            }
            false
        }
    }

    private fun initContents() {
        this.dataPosts = DataHelper.loadPostData();
        this.dataUsers = DataHelper.loadSearchUserData();

        this.rvSearch = findViewById(R.id.rv_feed);
        this.rvSearch.layoutManager = GridLayoutManager(this, 2);

        this.searchAdapter = SearchResultsAdapter(this.dataPosts);


        this.rvSearch.adapter = searchAdapter;
    }

    private fun initActionBar() {
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
    }

}