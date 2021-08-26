package com.mobdeve.gonzales.lee.ong.artemis

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import android.widget.ScrollView
import android.widget.TextView
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.facebook.shimmer.ShimmerFrameLayout
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.floatingactionbutton.FloatingActionButton
import de.hdodenhof.circleimageview.CircleImageView

class SearchResultsActivity : AppCompatActivity() {
    private lateinit var dataPosts: ArrayList<Post>
    private lateinit var dataUsers: ArrayList<User>
    private lateinit var rvSearch: RecyclerView
    private lateinit var civSearchResultUser1: CircleImageView
    private lateinit var civSearchResultUser2: CircleImageView
    private lateinit var civSearchResultUser3: CircleImageView
    private lateinit var civSearchResultUser4: CircleImageView
    private lateinit var tvSearchResultsArtworks: TextView
    private lateinit var searchAdapter: SearchResultsAdapter
    private lateinit var sflSearch: ShimmerFrameLayout
    private lateinit var bnvSearchBottom: BottomNavigationView

    private lateinit var btmAddPost: BottomSheetDialog
    private lateinit var fabAddPost: FloatingActionButton
    private lateinit var clDialogPostArtworkGallery: ConstraintLayout
    private lateinit var clDialogPostArtworkPhoto: ConstraintLayout

    private lateinit var srlSearchResults: SwipeRefreshLayout

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
        addPost()
        initActionBar()

        initSwipeRefresh()
    }

    private fun initShimmer() {
        this.sflSearch = findViewById(R.id.sfl_search_results)

        sflSearch.startShimmer()

        Handler(Looper.getMainLooper()).postDelayed({
            initContents()
            sflSearch.visibility = View.GONE
            tvSearchResultsArtworks.visibility = View.VISIBLE
            rvSearch.visibility = View.VISIBLE
            civSearchResultUser1.visibility = View.VISIBLE
            civSearchResultUser2.visibility = View.VISIBLE
            civSearchResultUser3.visibility = View.VISIBLE
            civSearchResultUser4.visibility = View.VISIBLE
        }, AnimationDuration.SHIMMER_TIMEOUT.toLong())
    }

    private fun initSwipeRefresh() {
        this.srlSearchResults = findViewById(R.id.srl_search_results)
        srlSearchResults.setOnRefreshListener {
            onRefresh();
        }

        srlSearchResults.setColorSchemeResources(R.color.purple_main,
            R.color.pinkish_purple,
            R.color.purple_pics_lighter,
            R.color.pinkish_purple_lighter);
    }

    private fun onRefresh() {
        Handler(Looper.getMainLooper()).postDelayed({
            srlSearchResults.isRefreshing = false
        }, AnimationDuration.REFRESH_TIMEOUT.toLong())
    }

    private fun initBottom() {
        this.bnvSearchBottom = findViewById(R.id.nv_search_results_bottom)

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

        this.civSearchResultUser1 = findViewById(R.id.civ_search_result_user1)
        this.civSearchResultUser2 = findViewById(R.id.civ_search_result_user2)
        this.civSearchResultUser3 = findViewById(R.id.civ_search_result_user3)
        this.civSearchResultUser4 = findViewById(R.id.civ_search_result_user4)

        this.tvSearchResultsArtworks = findViewById(R.id.tv_search_results_artworks)

        this.civSearchResultUser1.setImageResource(dataUsers[0].getUserImg())
        this.civSearchResultUser2.setImageResource(dataUsers[1].getUserImg())
        this.civSearchResultUser3.setImageResource(dataUsers[2].getUserImg())
        this.civSearchResultUser4.setImageResource(dataUsers[3].getUserImg())

        civSearchResultUser1.setOnClickListener(View.OnClickListener {
            val intent = Intent(this@SearchResultsActivity, ViewUserActivity::class.java)
            intent.putExtra(
                Keys.KEY_PROFILE_PICTURE.name,
                dataUsers[0].getUserImg()
            )
            intent.putExtra(
                Keys.KEY_USERNAME.name,
                dataUsers[0].getUsername()
            )
            intent.putExtra(
                Keys.KEY_BIO.name,
                dataUsers[0].getBio()
            )
            startActivity(intent)
        })

        civSearchResultUser2.setOnClickListener(View.OnClickListener {
            val intent = Intent(this@SearchResultsActivity, ViewUserActivity::class.java)
            intent.putExtra(
                Keys.KEY_PROFILE_PICTURE.name,
                dataUsers[1].getUserImg()
            )
            intent.putExtra(
                Keys.KEY_USERNAME.name,
                dataUsers[1].getUsername()
            )
            intent.putExtra(
                Keys.KEY_BIO.name,
                dataUsers[1].getBio()
            )
            startActivity(intent)
        })

        civSearchResultUser3.setOnClickListener(View.OnClickListener {
            val intent = Intent(this@SearchResultsActivity, ViewUserActivity::class.java)
            intent.putExtra(
                Keys.KEY_PROFILE_PICTURE.name,
                dataUsers[2].getUserImg()
            )
            intent.putExtra(
                Keys.KEY_USERNAME.name,
                dataUsers[2].getUsername()
            )
            intent.putExtra(
                Keys.KEY_BIO.name,
                dataUsers[2].getBio()
            )
            startActivity(intent)
        })

        civSearchResultUser4.setOnClickListener(View.OnClickListener {
            val intent = Intent(this@SearchResultsActivity, ViewUserActivity::class.java)
            intent.putExtra(
                Keys.KEY_PROFILE_PICTURE.name,
                dataUsers[3].getUserImg()
            )
            intent.putExtra(
                Keys.KEY_USERNAME.name,
                dataUsers[3].getUsername()
            )
            intent.putExtra(
                Keys.KEY_BIO.name,
                dataUsers[3].getBio()
            )
            startActivity(intent)
        })

        this.rvSearch = findViewById(R.id.rv_search_results)
        this.rvSearch.layoutManager = GridLayoutManager(this, 2)

        this.searchAdapter = SearchResultsAdapter(this.dataPosts)

        this.rvSearch.adapter = searchAdapter

        this.etSearchBar = findViewById(R.id.et_search_results_bar)

        etSearchBar.setOnEditorActionListener(TextView.OnEditorActionListener { v, actionId, event ->
            if ((event != null && (event.keyCode == KeyEvent.KEYCODE_ENTER)) || (actionId == EditorInfo.IME_ACTION_DONE)) {
                val intent = Intent(this@SearchResultsActivity, SearchResultsActivity::class.java)
                startActivity(intent)
            }
            return@OnEditorActionListener false
        });
    }

    private fun initActionBar() {
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
    }

    private fun addPost() {
        this.btmAddPost = BottomSheetDialog(this@SearchResultsActivity)
        this.fabAddPost = findViewById(R.id.fab_search_results_add)

        val view = LayoutInflater.from(this@SearchResultsActivity).inflate(R.layout.dialog_post_artwork, null)

        this.fabAddPost.setOnClickListener {
            btmAddPost.setContentView(view)

            this.clDialogPostArtworkGallery = btmAddPost.findViewById(R.id.cl_dialog_post_artwork_gallery)!!
            this.clDialogPostArtworkPhoto = btmAddPost.findViewById(R.id.cl_dialog_post_artwork_photo)!!

            clDialogPostArtworkGallery.setOnClickListener(View.OnClickListener {
                Toast.makeText(this@SearchResultsActivity, "Photo chosen from the gallery", Toast.LENGTH_SHORT).show()
                btmAddPost.dismiss()
                val intent = Intent(this@SearchResultsActivity, PostArtworkActivity::class.java)
                startActivity(intent)
            })

            clDialogPostArtworkPhoto.setOnClickListener(View.OnClickListener {
                Toast.makeText(this@SearchResultsActivity, "Photo taken with the device camera", Toast.LENGTH_SHORT).show()
                btmAddPost.dismiss()
                val intent = Intent(this@SearchResultsActivity, PostArtworkActivity::class.java)
                startActivity(intent)
            })

            btmAddPost.show()
        }
    }
}