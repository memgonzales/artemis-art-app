package com.mobdeve.gonzales.lee.ong.artemis

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.bumptech.glide.Glide
import com.facebook.shimmer.ShimmerFrameLayout
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import de.hdodenhof.circleimageview.CircleImageView

/**
 * Class for handling functionalities related to displaying the search results for unregistered users.
 *
 * @constructor Creates an activity for displaying the search results for unregistered users.
 */
class SearchResultsUnregisteredActivity : AppCompatActivity() {
    /**
     * List of posts returned by the search query.
     */
    private lateinit var dataPosts: ArrayList<Post>

    /**
     * List of users returned by the search query.
     */
    private lateinit var dataUsers: ArrayList<User>

    /**
     * Recycler view for the posts to be displayed on the search results.
     */
    private lateinit var rvSearch: RecyclerView

    /**
     * Image view holding the profile picture of the first user returned by the search query.
     */
    private lateinit var civSearchResultUser1: CircleImageView

    /**
     * Image view holding the profile picture of the second user returned by the search query.
     */
    private lateinit var civSearchResultUser2: CircleImageView

    /**
     * Image view holding the profile picture of the third user returned by the search query.
     */
    private lateinit var civSearchResultUser3: CircleImageView

    /**
     * Image view holding the profile picture of the fourth user returned by the search query.
     */
    private lateinit var civSearchResultUser4: CircleImageView

    /**
     * Text view holding the "Matching Artworks" label.
     */
    private lateinit var tvSearchResultsArtworks: TextView

    /**
     * Adapter for the recycler view handling the posts to be displayed on the search result.
     */
    private lateinit var searchAdapter: SearchResultsUnregisteredAdapter

    /**
     * Shimmer layout displayed while data regarding the posts are being fetched
     * from the remote database.
     */
    private lateinit var sflSearch: ShimmerFrameLayout

    /**
     * Image view displayed when the search result does not have matching users or posts to display.
     */
    private lateinit var ivNone: ImageView

    /**
     * First (main) text view displayed when the search result does not have matching users or
     * posts to display.
     */
    private lateinit var tvNone: TextView

    /**
     * Second text view displayed when the search result does not have matching users or
     * posts to display.
     */
    private lateinit var tvSubNone: TextView

    /**
     * Bottom navigation view containing the menu items for Home, Followed, Bookmarks, and Profile.
     */
    private lateinit var bnvSearchBottom: BottomNavigationView

    /**
     * Floating action button for posting an artwork.
     */
    private lateinit var fabAddPost: FloatingActionButton

    /**
     * Layout for registering a swipe gesture as a request to refresh this activity.
     */
    private lateinit var srlSearchResults: SwipeRefreshLayout

    /**
     * Input field for the user's search query.
     */
    private lateinit var etSearchBar: EditText

    /**
     * Starting point for Firebase authentication SDK.
     */
    private lateinit var mAuth: FirebaseAuth

    /**
     * Starting point for all database-related operations.
     */
    private lateinit var db: DatabaseReference

    /**
     * Represents a user profile's information in the Firebase user database.
     */
    private lateinit var user: FirebaseUser

    /**
     * Unique identifier of the user.
     */
    private lateinit var userId: String

    /**
     * Called when the activity is starting.
     *
     * @param savedInstanceState  If the activity is being re-initialized after previously being
     * shut down then this Bundle contains the data it most recently supplied in
     * <code>onSaveInstanceState(Bundle)</code>. Note: Otherwise it is <code>null</code>.
     * This value may be <code>null</code>.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search_results_unregistered)

        initFirebase()
        initComponents()
    }

    /**
     * Initializes the Firebase-related components.
     */
    private fun initFirebase(){
        this.mAuth = Firebase.auth
        this.db = Firebase.database.reference

        if (this.mAuth.currentUser != null){
            this.user = this.mAuth.currentUser!!
            this.userId = this.user.uid
        }

        else{
            val intent = Intent(this@SearchResultsUnregisteredActivity, BrokenLinkActivity::class.java)
            startActivity(intent)
        }
    }

    /**
     * Initializes the components of the activity.
     */
    private fun initComponents() {
        setSupportActionBar(findViewById(R.id.toolbar_search_unregistered_results))
        initShimmer()
        initBottom()
        initActionBar()

        initSwipeRefresh()
    }

    /**
     * Initializes the shimmer layout animated while the data are being fetched from the remote server.
     */
    private fun initShimmer() {
        this.sflSearch = findViewById(R.id.sfl_search_unregistered_results)

        sflSearch.startShimmer()

        Handler(Looper.getMainLooper()).postDelayed({
            initContents()
            sflSearch.visibility = View.GONE
            //tvSearchResultsArtworks.visibility = View.VISIBLE
            rvSearch.visibility = View.VISIBLE

            civSearchResultUser1.visibility = View.GONE
            civSearchResultUser2.visibility = View.GONE
            civSearchResultUser3.visibility = View.GONE
            civSearchResultUser4.visibility = View.GONE
        }, AnimationDuration.SHIMMER_TIMEOUT.toLong())
    }

    /**
     * Initializes the swipe refresh layout and defines the behavior when the screen is swiped
     * to refresh.
     */
    private fun initSwipeRefresh() {
        this.srlSearchResults = findViewById(R.id.srl_search_unregistered_results)
        srlSearchResults.setOnRefreshListener {
            onRefresh()
        }

        srlSearchResults.setColorSchemeResources(R.color.purple_main,
            R.color.pinkish_purple,
            R.color.purple_pics_lighter,
            R.color.pinkish_purple_lighter)
    }

    /**
     * Refetches data from the database and reshuffles the display of existing data when the screen
     * is swiped to refresh.
     */
    private fun onRefresh() {
        Handler(Looper.getMainLooper()).postDelayed({
            srlSearchResults.isRefreshing = false
        }, AnimationDuration.REFRESH_TIMEOUT.toLong())
    }

    /**
     * Sets the listeners for the menu selection found in the bottom navigation view.
     */
    private fun initBottom() {
        this.bnvSearchBottom = findViewById(R.id.nv_search_unregistered_results_bottom)
        this.fabAddPost = findViewById(R.id.fab_search_unregistered_results_add)

        BottomMenuUtil.setBottomMenuListenersUnregistered(bnvSearchBottom,
            this,this@SearchResultsUnregisteredActivity)

        fabAddPost.setOnClickListener {
            Toast.makeText(this@SearchResultsUnregisteredActivity,"Log in or create an account to use this feature", Toast.LENGTH_SHORT).show()
        }
    }

    /**
     * Initializes the contents (i.e., the list of resulting users and posts) of the activity.
     */
    private fun initContents() {
        this.dataPosts = arrayListOf<Post>()
        this.dataUsers = arrayListOf<User>()

        this.civSearchResultUser1 = findViewById(R.id.civ_search_unregistered_result_user1)
        this.civSearchResultUser2 = findViewById(R.id.civ_search_unregistered_result_user2)
        this.civSearchResultUser3 = findViewById(R.id.civ_search_unregistered_result_user3)
        this.civSearchResultUser4 = findViewById(R.id.civ_search_unregistered_result_user4)

        this.tvSearchResultsArtworks = findViewById(R.id.tv_search_unregistered_results_artworks)
        this.ivNone = findViewById(R.id.iv_search_results_unregistered_none)
        this.tvNone = findViewById(R.id.tv_search_results_unregistered_none)
        this.tvSubNone = findViewById(R.id.tv_search_results_subtitle_unregistered_none)

        val intent: Intent = intent

        val search = intent.getStringExtra(Keys.KEY_SEARCH.name).toString()

        getUserSearchResults(search)

        civSearchResultUser1.setOnClickListener {
            val intent = Intent(
                this@SearchResultsUnregisteredActivity,
                ViewUserUnregisteredActivity::class.java
            )
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
        }

        civSearchResultUser2.setOnClickListener {
            val intent = Intent(
                this@SearchResultsUnregisteredActivity,
                ViewUserUnregisteredActivity::class.java
            )
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
        }

        civSearchResultUser3.setOnClickListener {
            val intent = Intent(
                this@SearchResultsUnregisteredActivity,
                ViewUserUnregisteredActivity::class.java
            )
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
        }

        civSearchResultUser4.setOnClickListener {
            val intent = Intent(
                this@SearchResultsUnregisteredActivity,
                ViewUserUnregisteredActivity::class.java
            )
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
        }

        this.rvSearch = findViewById(R.id.rv_search_unregistered_results)
        this.rvSearch.layoutManager = GridLayoutManager(this, 2)

        this.searchAdapter = SearchResultsUnregisteredAdapter(this.dataPosts)

        this.rvSearch.adapter = searchAdapter

        this.etSearchBar = findViewById(R.id.et_search_unregistered_results_bar)

        etSearchBar.setOnEditorActionListener(TextView.OnEditorActionListener { _, actionId, event ->
            if ((event != null && (event.keyCode == KeyEvent.KEYCODE_ENTER)) || (actionId == EditorInfo.IME_ACTION_DONE)) {
                val intent = Intent(this@SearchResultsUnregisteredActivity, SearchResultsUnregisteredActivity::class.java)
                startActivity(intent)
            }
            return@OnEditorActionListener false
        })
    }

    /**
     * Retrieves the list of matching users based on the user's search query.
     *
     * @param search The user's search query.
     */
    private fun getUserSearchResults(search: String){
        val userDB = this.db.child(Keys.KEY_DB_USERS.name)

        userDB.addListenerForSingleValueEvent(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                dataUsers.clear()
                if (snapshot.exists()){
                    for (u in snapshot.children){
                        var userSnap = u.getValue(User::class.java)

                        if (userSnap != null && userSnap.getUsername()!!.contains(search, ignoreCase = true)){
                            dataUsers.add(userSnap)
                        }
                    }

                    setSearchUserResults(dataUsers)
                    getSearchPostResults(search, dataUsers)

                }
            }

            override fun onCancelled(error: DatabaseError) {
                val intent = Intent(this@SearchResultsUnregisteredActivity, BrokenLinkActivity::class.java)
                startActivity(intent)
            }

        })
    }

    /**
     * Sets the list of matching users based on the user's search query onto the activity layout.
     *
     * @param data Array list of users matching the user's search query.
     */
    private fun setSearchUserResults(data: ArrayList<User>){
        var i = 0

        while (i >= 0  && i < dataUsers.size && i < 4){

            when (i){
                0 -> {
                    civSearchResultUser1.visibility = View.VISIBLE

                    Glide.with(this)
                        .load(dataUsers[0].getUserImg())
                        .placeholder(R.drawable.placeholder)
                        .error(R.drawable.placeholder)
                        .into(civSearchResultUser1)
                }


                1 -> {
                    civSearchResultUser2.visibility = View.VISIBLE

                    Glide.with(this)
                        .load(dataUsers[1].getUserImg())
                        .placeholder(R.drawable.placeholder)
                        .error(R.drawable.placeholder)
                        .into(civSearchResultUser2)
                }


                2 -> {
                    civSearchResultUser3.visibility = View.VISIBLE

                    Glide.with(this)
                        .load(dataUsers[2].getUserImg())
                        .placeholder(R.drawable.placeholder)
                        .error(R.drawable.placeholder)
                        .into(civSearchResultUser3)
                }


                3 -> {
                    civSearchResultUser4.visibility = View.VISIBLE

                    Glide.with(this)
                        .load(dataUsers[3].getUserImg())
                        .placeholder(R.drawable.placeholder)
                        .error(R.drawable.placeholder)
                        .into(civSearchResultUser4)
                }
            }
            i++
        }
    }

    /**
     * Retrieves the list of matching posts based on the user's search query.
     *
     * @param searchPost The user's search query.
     * @param dataUsers Array list of user's matching the user's search query.
     */
    private fun getSearchPostResults(searchPost: String, dataUsers: ArrayList<User>){
        val postDB = this.db.child(Keys.KEY_DB_POSTS.name)

        postDB.addListenerForSingleValueEvent(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                dataPosts.clear()
                if (snapshot.exists()){
                    for (p in snapshot.children){
                        var postSnap = p.getValue(Post::class.java)

                        if (postSnap != null && !postSnap.getTags().isNullOrEmpty()) {

                            var check = postSnap.getTags()?.filter { it.contains(searchPost, ignoreCase = true) }

                            if (check!!.size > 0){
                                dataPosts.add(postSnap)
                            }
                        }
                    }
                    searchAdapter.notifyDataSetChanged()
                    setSearchPostResults(dataPosts, dataUsers)

                }
            }

            override fun onCancelled(error: DatabaseError) {
                val intent = Intent(this@SearchResultsUnregisteredActivity, BrokenLinkActivity::class.java)
                startActivity(intent)
            }

        })
    }

    /**
     * Sets the list of matching posts based on the user's search query onto the activity layout.
     *
     * @param dataPosts Array list of posts matching the user's search query.
     * @param dataUsers Array list of users matching the user's search query.
     */
    private fun setSearchPostResults(dataPosts: ArrayList<Post>, dataUsers: ArrayList<User>){
        if (dataPosts.isNullOrEmpty() && dataUsers.isNullOrEmpty()){
            this.tvSearchResultsArtworks.visibility = View.GONE
            this.ivNone.visibility = View.VISIBLE
            this.tvNone.visibility = View.VISIBLE
            this.tvSubNone.visibility = View.VISIBLE
        }

        else if (!dataPosts.isNullOrEmpty()){
            this.tvSearchResultsArtworks.visibility = View.VISIBLE
            this.ivNone.visibility = View.GONE
            this.tvNone.visibility = View.GONE
            this.tvSubNone.visibility = View.GONE
        }
    }

    /**
     * Adds a back button to the action bar.
     */
    private fun initActionBar() {
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
    }
}