package com.mobdeve.gonzales.lee.ong.artemis

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.facebook.shimmer.ShimmerFrameLayout
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

/**
 * Class handling the functionalities related to viewing the comments on a post for unregistered users.
 *
 * @constructor Creates a class that handles the functionalities related to viewing the comments
 * on a post for unregistered users.
 */
class ViewCommentsUnregisteredActivity : AppCompatActivity() {
    /**
     * Comments to be displayed.
     */
    private lateinit var dataComments: ArrayList<Comment>

    /**
     * Recycler view for the comments to be displayed.
     */
    private lateinit var rvComments: RecyclerView

    /**
     * Adapter for the recycler view handling the comments to be displayed.
     */
    private lateinit var unregisteredCommentsAdapter: UnregisteredCommentsAdapter

    /**
     * Linear layout used to hold the shimmer layout.
     */
    private lateinit var llViewCommentsShimmer: LinearLayout

    /**
     * Shimmer layout displayed while data regarding the comments are being fetched
     * from the remote database.
     */
    private lateinit var sflViewComments: ShimmerFrameLayout

    /**
     * Bottom navigation view containing the menu items for Home, Followed, Bookmarks, and Profile.
     */
    private lateinit var bnvViewCommentsUnregisteredBottom: BottomNavigationView

    /**
     * Edit text field for entering a comment.
     */
    private lateinit var etAddCommentUnregistered: EditText

    /**
     * Image button for adding a comment.
     */
    private lateinit var ibAddComment: ImageButton

    /**
     * Floating action button for posting an artwork.
     */
    private lateinit var fabAddPost: FloatingActionButton

    /**
     * Layout for registering a swipe gesture as a request to refresh this activity.
     */
    private lateinit var srlViewCommentsUnregisterd: SwipeRefreshLayout

    /**
     * First (main) text view displayed when the feed does not have any post to display.
     */
    private lateinit var ivNone: ImageView

    /**
     * Second text view displayed displayed when the feed does not have any post to display.
     */
    private lateinit var tvNone: TextView

    /**
     * Starting point for Firebase authentication SDK.
     */
    private lateinit var mAuth: FirebaseAuth

    /**
     * Represents a user profile's information in the Firebase user database.
     */
    private lateinit var user: FirebaseUser

    /**
     * Unique identifier of the user.
     */
    private lateinit var userId: String

    /**
     * Starting point for all database-related operations.
     */
    private lateinit var db: DatabaseReference

    /**
     * Unique identifier of the post.
     */
    private lateinit var postId: String

    /**
     * Number of comments on the post.
     */
    private var numComment: Int = 0


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
        setContentView(R.layout.activity_view_comments_unregistered)

        initFirebase()
        initIntent()
        initComponents()
    }

    /**
     * Initializes the Firebase-related components.
     */
    private fun initFirebase() {
        this.mAuth = Firebase.auth
        this.db = Firebase.database.reference

        if (this.mAuth.currentUser != null){
            this.user = this.mAuth.currentUser!!
            this.userId = this.user.uid
        }

        else{
            val intent = Intent(this@ViewCommentsUnregisteredActivity, BrokenLinkActivity::class.java)
            startActivity(intent)
        }
    }

    /**
     * Initializes the intent passed to the activity.
     */
    private fun initIntent(){
        val intent: Intent = intent
        this.postId = intent.getStringExtra(Keys.KEY_POSTID.name).toString()
        this.numComment = intent.getIntExtra(Keys.KEY_NUM_COMMENTS.name, 0)

        if (postId.isNullOrEmpty()){
            val intent = Intent(this@ViewCommentsUnregisteredActivity, BrokenLinkActivity::class.java)
            startActivity(intent)
        }
    }

    /**
     * Initializes the components of the activity.
     */
    private fun initComponents() {
        setSupportActionBar(findViewById(R.id.toolbar_view_comments_unregistered))
        initShimmer()
        initActionBar()
        initBottom()
        initSwipeRefresh()

        disableEditText()
    }

    /**
     * Initializes the shimmer layout animated while the data are being fetched from the remote server.
     */
    private fun initShimmer() {
        this.sflViewComments = findViewById(R.id.sfl_view_comments_unregistered)

        sflViewComments.startShimmer()

        Handler(Looper.getMainLooper()).postDelayed({
            initRecyclerView()
            sflViewComments.visibility = View.GONE
            rvComments.visibility = View.VISIBLE
        }, AnimationDuration.SHIMMER_TIMEOUT.toLong())
    }

    /**
     * Initializes the swipe refresh layout and defines the behavior when the screen is swiped
     * to refresh.
     */
    private fun initSwipeRefresh() {
        this.srlViewCommentsUnregisterd = findViewById(R.id.srl_view_comments_unregistered)
        srlViewCommentsUnregisterd.setOnRefreshListener {
            onRefresh()
        }

        srlViewCommentsUnregisterd.setColorSchemeResources(R.color.purple_main,
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
            srlViewCommentsUnregisterd.isRefreshing = false
        }, AnimationDuration.REFRESH_TIMEOUT.toLong())
    }

    /**
     * Sets the listeners for the menu selection found in the bottom navigation view.
     */
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

        fabAddPost.setOnClickListener {
            Toast.makeText(
                this@ViewCommentsUnregisteredActivity,
                "Log in or create an account to use this feature",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    /**
     * Adds a back button to the action bar.
     */
    private fun initActionBar() {
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
    }

    /**
     * Initializes the recycler view of the activity.
     */
    private fun initRecyclerView() {
        this.dataComments = arrayListOf<Comment>()

        this.rvComments = findViewById(R.id.rv_view_comments_unregistered)
        this.llViewCommentsShimmer = findViewById(R.id.ll_view_comments_unregistered_shimmer)

        this.rvComments.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)

        this.unregisteredCommentsAdapter = UnregisteredCommentsAdapter()

        this.rvComments.adapter = unregisteredCommentsAdapter

        this.rvComments.itemAnimator = null

        initContents()
    }

    /**
     * Initializes the contents of the activity.
     */
    private fun initContents(){
        this.ivNone = findViewById(R.id.iv_view_comments_unregistered_no_comment)
        this.tvNone = findViewById(R.id.tv_view_comments_unregistered_no_comment)

        val commentDB = this.db.child(Keys.KEY_DB_COMMENTS.name)

        commentDB.addChildEventListener(object : ChildEventListener {
            override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
                val commentSnap = snapshot.getValue(Comment::class.java)

                if (commentSnap != null &&
                    !commentSnap.getUserId().isNullOrEmpty() &&
                    !commentSnap.getCommentId().isNullOrEmpty() &&
                    !commentSnap.getPostId().isNullOrEmpty() &&
                    commentSnap.getPostId()!!.equals(postId)){

                    dataComments.add(commentSnap)
                    unregisteredCommentsAdapter.updateComments(dataComments)
                }
            }

            override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {
                val commentSnap = snapshot.getValue(Comment::class.java)

                if (commentSnap != null &&
                    !commentSnap.getUserId().isNullOrEmpty() &&
                    !commentSnap.getCommentId().isNullOrEmpty() &&
                    !commentSnap.getPostId().isNullOrEmpty() &&
                    commentSnap.getPostId()!!.equals(postId)){

                    val list = ArrayList<Comment>(dataComments)
                    val index = list.indexOfFirst { it.getCommentId() == commentSnap.getCommentId() }

                    if (index != -1){
                        list.set(index, commentSnap)

                        dataComments = list
                        unregisteredCommentsAdapter.updateComments(list)
                    }

                }
            }

            override fun onChildRemoved(snapshot: DataSnapshot) {
                val commentSnap = snapshot.getValue(Comment::class.java)

                if (commentSnap != null &&
                    !commentSnap.getUserId().isNullOrEmpty() &&
                    !commentSnap.getCommentId().isNullOrEmpty() &&
                    !commentSnap.getPostId().isNullOrEmpty()){

                    val list = ArrayList<Comment>(dataComments)

                    val index = list.indexOfFirst { it.getCommentId() == commentSnap.getCommentId() }

                    if (index != -1){
                        list.removeAt(index)

                        dataComments = list
                        unregisteredCommentsAdapter.updateComments(list)
                    }
                }
            }

            override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {
                /* This is intentionally left blank */
            }

            override fun onCancelled(error: DatabaseError) {
                val intent = Intent(this@ViewCommentsUnregisteredActivity, BrokenLinkActivity::class.java)
                startActivity(intent)
            }

        })
    }

    /**
     * Disables the adding of comments when a user is unregistered.
     */
    private fun disableEditText() {
        this.etAddCommentUnregistered = findViewById(R.id.et_add_comment_unregistered)
        this.ibAddComment = findViewById(R.id.ib_add_comment_unregistered)

        this.etAddCommentUnregistered.isEnabled = false
        this.ibAddComment.setOnClickListener {
            Toast.makeText(
                this@ViewCommentsUnregisteredActivity,
                "Log in or create an account to use this feature",
                Toast.LENGTH_SHORT
            ).show()
        }
    }
}