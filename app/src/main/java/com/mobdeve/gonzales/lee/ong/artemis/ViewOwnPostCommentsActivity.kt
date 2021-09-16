package com.mobdeve.gonzales.lee.ong.artemis

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.widget.*
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.facebook.shimmer.ShimmerFrameLayout
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.*
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import java.io.File

class ViewOwnPostCommentsActivity : AppCompatActivity() {
    private lateinit var dataComments: ArrayList<Comment>
    private lateinit var rvComments: RecyclerView
    private lateinit var commentsAdapter: CommentsAdapter
    private lateinit var llViewCommentsShimmer: LinearLayout
    private lateinit var sflViewComments: ShimmerFrameLayout
    private lateinit var bnvViewCommentsBottom: BottomNavigationView

    private lateinit var btmAddPost: BottomSheetDialog
    private lateinit var fabAddPost: FloatingActionButton
    private lateinit var clDialogPostArtworkGallery: ConstraintLayout
    private lateinit var clDialogPostArtworkPhoto: ConstraintLayout

    private lateinit var ibAddComment: ImageButton
    private lateinit var etComment: EditText

    private lateinit var pbComment: ProgressBar

    private lateinit var srlViewComments: SwipeRefreshLayout

    /**
     * First (main) text view displayed when the feed does not have any post to display.
     */
    private lateinit var ivNone: ImageView

    /**
     * Second text view displayed displayed when the feed does not have any post to display.
     */
    private lateinit var tvNone: TextView

    //Firebase
    private lateinit var mAuth: FirebaseAuth
    private lateinit var user: FirebaseUser
    private lateinit var userId: String
    private lateinit var db: DatabaseReference

    private lateinit var postId: String
    private var numComment: Int = 0

    // data for onbackpressed() intent
    private lateinit var userIdPost: String
    private lateinit var profilePicture: String
    private lateinit var username: String
    private lateinit var postImg: String
    private lateinit var title: String
    private var upvoteCounter: Int = 0
    private lateinit var datePosted: String
    private lateinit var medium: String
    private lateinit var dimensions: String
    private lateinit var description: String
    private lateinit var tags: String
    private var bookmark: Boolean = false
    private var upvote: Boolean = false

    /**
     * Photo of the artwork for posting.
     */
    private lateinit var photoFile: File

    /**
     * Activity result launcher related to taking photos using the device camera.
     */
    private lateinit var cameraLauncher: ActivityResultLauncher<Intent>

    /**
     * Activity result launcher related to choosing photos from the Gallery.
     */
    private lateinit var galleryLauncher: ActivityResultLauncher<Intent>

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
        setContentView(R.layout.activity_view_own_post_comments)

        initFirebase()
        initIntent()
        initComponents()
        initGalleryLauncher(this@ViewOwnPostCommentsActivity)
        initCameraLauncher(this@ViewOwnPostCommentsActivity)
    }

    private fun initFirebase() {
        this.mAuth = Firebase.auth
        this.db = Firebase.database.reference

        if (this.mAuth.currentUser != null){
            this.user = this.mAuth.currentUser!!
            this.userId = this.user.uid
        }

        else{
            val intent = Intent(this@ViewOwnPostCommentsActivity, BrokenLinkActivity::class.java)
            startActivity(intent)
        }
    }

    private fun initIntent(){
        val intent: Intent = intent
        this.postId = intent.getStringExtra(Keys.KEY_POSTID.name).toString()
        this.numComment = intent.getIntExtra(Keys.KEY_NUM_COMMENTS.name, 0)

        this.userIdPost = intent.getStringExtra(Keys.KEY_USERID.name).toString()
        this.profilePicture = intent.getStringExtra(Keys.KEY_PROFILE_PICTURE.name).toString()
        this.username = intent.getStringExtra(Keys.KEY_USERNAME.name).toString()
        this.postImg = intent.getStringExtra(Keys.KEY_POST.name).toString()
        this.title = intent.getStringExtra(Keys.KEY_TITLE.name).toString()
        this.upvoteCounter = intent.getIntExtra(Keys.KEY_NUM_UPVOTES.name, 0)
        this.datePosted = intent.getStringExtra(Keys.KEY_DATE_POSTED.name).toString()
        this.medium = intent.getStringExtra(Keys.KEY_MEDIUM.name).toString()
        this.dimensions = intent.getStringExtra(Keys.KEY_DIMENSIONS.name).toString()
        this.description = intent.getStringExtra(Keys.KEY_DESCRIPTION.name).toString()
        this.tags = intent.getStringArrayListExtra(Keys.KEY_TAGS.name).toString()
        this.bookmark = intent.getBooleanExtra(Keys.KEY_BOOKMARK.name, false)
        this.upvote = intent.getBooleanExtra(Keys.KEY_UPVOTE.name, false)

        if (postId.isNullOrEmpty()){
            val intent = Intent(this@ViewOwnPostCommentsActivity, BrokenLinkActivity::class.java)
            startActivity(intent)
        }
    }

    /**
     * Initializes the activity result launcher related to choosing photos from the Gallery.
     *
     * @param packageContext Context tied to this activity.
     */
    private fun initGalleryLauncher(packageContext: Context) {
        galleryLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
                result: ActivityResult ->
            if (result.resultCode == Activity.RESULT_OK) {
                val intent = Intent(packageContext, PostArtworkActivity::class.java)
                intent.putExtra(
                    Keys.KEY_POST_ARTWORK.name,
                    result.data?.data.toString()
                )

                intent.putExtra(
                    Keys.KEY_POST_FROM.name,
                    PostArtworkUtil.FROM_GALLERY
                )

                startActivity(intent)
            }
        }
    }

    /**
     * Initializes the activity result launcher related to taking photos using the device camera.
     *
     * @param packageContext Context tied to this activity.
     */
    private fun initCameraLauncher(packageContext: Context) {
        cameraLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
            if (result.resultCode == Activity.RESULT_OK) {
                val intent = Intent(packageContext, PostArtworkActivity::class.java)
                intent.putExtra(
                    Keys.KEY_POST_ARTWORK.name,
                    photoFile.absolutePath
                )

                intent.putExtra(
                    Keys.KEY_POST_FROM.name,
                    PostArtworkUtil.FROM_CAMERA
                )

                startActivity(intent)
            }
        }
    }

    private fun initComponents() {
        setSupportActionBar(findViewById(R.id.toolbar_view_comments))
        initShimmer()
        initActionBar()
        initBottom()
        addPost()
        initSwipeRefresh()

        addComment()
    }

    private fun initShimmer() {
        this.sflViewComments = findViewById(R.id.sfl_view_comments)

        sflViewComments.startShimmer()

        Handler(Looper.getMainLooper()).postDelayed({
            initRecyclerView()
            sflViewComments.visibility = View.GONE
            rvComments.visibility = View.VISIBLE
        }, AnimationDuration.SHIMMER_TIMEOUT.toLong())
    }

    private fun initSwipeRefresh() {
        this.srlViewComments = findViewById(R.id.srl_view_comments)
        srlViewComments.setOnRefreshListener {
            onRefresh()
        }

        srlViewComments.setColorSchemeResources(R.color.purple_main,
            R.color.pinkish_purple,
            R.color.purple_pics_lighter,
            R.color.pinkish_purple_lighter)
    }

    private fun onRefresh() {
        Handler(Looper.getMainLooper()).postDelayed({
            srlViewComments.isRefreshing = false
        }, AnimationDuration.REFRESH_TIMEOUT.toLong())
    }

    private fun initBottom() {
        this.bnvViewCommentsBottom = findViewById(R.id.nv_view_comments_bottom)

        BottomMenuUtil.setFinishBottomMenuListeners(bnvViewCommentsBottom, this,
            this@ViewOwnPostCommentsActivity)
    }

    private fun initActionBar() {
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
    }

    private fun initRecyclerView() {

        this.dataComments = arrayListOf()

        this.rvComments = findViewById(R.id.rv_view_comments)
        this.llViewCommentsShimmer = findViewById(R.id.ll_view_comments_shimmer)

        this.rvComments.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)

        this.commentsAdapter = CommentsAdapter()

        this.rvComments.adapter = commentsAdapter

        this.rvComments.itemAnimator = null


        initContents()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId) {
            android.R.id.home -> {
                onBackPressed()
                true
            } else -> {
                super.onOptionsItemSelected(item)
            }
        }
    }

    override fun onBackPressed() {
        val intent = Intent(this, ViewOwnPostActivity::class.java)

        intent.putExtra(
            Keys.KEY_USERID.name,
            this.userIdPost
        )
        intent.putExtra(
            Keys.KEY_PROFILE_PICTURE.name,
            this.profilePicture
        )
        intent.putExtra(
            Keys.KEY_USERNAME.name,
            this.username
        )
        intent.putExtra(
            Keys.KEY_POST.name,
            this.postImg
        )
        intent.putExtra(
            Keys.KEY_TITLE.name,
            this.title
        )
        intent.putExtra(
            Keys.KEY_POSTID.name,
            this.postId
        )
        intent.putExtra(
            Keys.KEY_NUM_UPVOTES.name,
            this.upvoteCounter
        )
        intent.putExtra(
            Keys.KEY_NUM_COMMENTS.name,
            this.numComment
        )
        intent.putExtra(
            Keys.KEY_DATE_POSTED.name,
            this.datePosted
        )
        intent.putExtra(
            Keys.KEY_MEDIUM.name,
            this.medium
        )
        intent.putExtra(
            Keys.KEY_DIMENSIONS.name,
            this.dimensions
        )
        intent.putExtra(
            Keys.KEY_DESCRIPTION.name,
            this.description
        )
        intent.putExtra(
            Keys.KEY_TAGS.name,
            this.tags
        )
        intent.putExtra(
            Keys.KEY_BOOKMARK.name,
            this.bookmark
        )
        intent.putExtra(
            Keys.KEY_UPVOTE.name,
            this.upvote
        )
        startActivity(intent)
        finish()
    }

    private fun initContents(){
        this.ivNone = findViewById(R.id.iv_view_comments_no_comment)
        this.tvNone = findViewById(R.id.tv_view_comments_no_comment)

        val commentDB = this.db.child(Keys.KEY_DB_COMMENTS.name)

        commentDB.addChildEventListener(object : ChildEventListener{
            override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
                val commentSnap = snapshot.getValue(Comment::class.java)

                if (commentSnap != null &&
                    !commentSnap.getUserId().isNullOrEmpty() &&
                    !commentSnap.getCommentId().isNullOrEmpty() &&
                    !commentSnap.getPostId().isNullOrEmpty() &&
                    commentSnap.getPostId()!! == postId
                ){

                    if (commentSnap.getUserId().equals(userId)){
                        commentSnap.setEditable(true)
                    }

                    dataComments.add(commentSnap)
                    commentsAdapter.updateComments(dataComments)
                }
            }

            override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {
                val commentSnap = snapshot.getValue(Comment::class.java)

                if (commentSnap != null &&
                    !commentSnap.getUserId().isNullOrEmpty() &&
                    !commentSnap.getCommentId().isNullOrEmpty() &&
                    !commentSnap.getPostId().isNullOrEmpty() &&
                    commentSnap.getPostId()!! == postId
                ){

                    if (commentSnap.getUserId().equals(userId)){
                        commentSnap.setEditable(true)
                    }


                    val list = ArrayList<Comment>(dataComments)
                    val index = list.indexOfFirst { it.getCommentId() == commentSnap.getCommentId() }

                    if (index != -1){
                        list[index] = commentSnap

                        dataComments = list
                        commentsAdapter.updateComments(list)
                    }

                }
            }

            override fun onChildRemoved(snapshot: DataSnapshot) {
                val commentSnap = snapshot.getValue(Comment::class.java)

                numComment--

                if (commentSnap != null &&
                    !commentSnap.getUserId().isNullOrEmpty() &&
                    !commentSnap.getCommentId().isNullOrEmpty() &&
                    !commentSnap.getPostId().isNullOrEmpty()){

                    val list = ArrayList<Comment>(dataComments)

                    val index = list.indexOfFirst { it.getCommentId() == commentSnap.getCommentId() }

                    if (index != -1){
                        list.removeAt(index)

                        dataComments = list
                        commentsAdapter.updateComments(list)
                    }
                }
            }

            override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {
                /* This is intentionally left blank */
            }

            override fun onCancelled(error: DatabaseError) {
                val intent = Intent(this@ViewOwnPostCommentsActivity, BrokenLinkActivity::class.java)
                startActivity(intent)
            }

        })
    }

    private fun addComment() {
        this.ibAddComment = findViewById(R.id.ib_add_comment)
        this.etComment = findViewById(R.id.et_add_comment)
        this.pbComment = findViewById(R.id.pb_view_comments)


        this.ibAddComment.setOnClickListener {
            val commentText: String = etComment.text.toString().trim()

            if (commentText.isNotEmpty()) {
                this.pbComment.visibility = View.VISIBLE


                db.child(Keys.KEY_DB_USERS.name).child(userId)
                    .addListenerForSingleValueEvent(object: ValueEventListener{
                        override fun onDataChange(snapshot: DataSnapshot) {
                            val userImg: String = snapshot.child(Keys.userImg.name).value.toString()
                            val username: String = snapshot.child(Keys.username.name).value.toString()

                            val comment = Comment(userId, postId, userImg, username, commentText)

                            val commentDB = db.child(Keys.KEY_DB_COMMENTS.name)
                            val commentKey = commentDB.push().key!!

                            comment.setCommentId(commentKey)

                            addCommentDB(comment, commentKey)

                        }

                        override fun onCancelled(error: DatabaseError) {
                            val intent = Intent(this@ViewOwnPostCommentsActivity, BrokenLinkActivity::class.java)
                            startActivity(intent)
                        }

                    })

            } else {
                Toast.makeText(this, "Comments should not be blank", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun addCommentDB(comment: Comment, commentKey: String) {
        this.numComment++

        val updates = hashMapOf(
            "/${Keys.KEY_DB_COMMENTS.name}/$commentKey" to comment,
            "/${Keys.KEY_DB_POSTS.name}/$postId/${Keys.comments.name}/$commentKey" to commentKey,
            "/${Keys.KEY_DB_POSTS.name}/$postId/${Keys.numComments.name}" to numComment,
        )

        db.updateChildren(updates)
            .addOnCompleteListener { task ->
                if (task.isSuccessful){
                    pbComment.visibility = View.GONE
                    Toast.makeText(this@ViewOwnPostCommentsActivity, "Commented successfully", Toast.LENGTH_LONG).show()
                    etComment.text.clear()
                }

                else{
                    pbComment.visibility = View.VISIBLE
                    Toast.makeText(this@ViewOwnPostCommentsActivity, "Failed to comment", Toast.LENGTH_LONG).show()
                }
            }
    }

    private fun addPost() {
        this.btmAddPost = BottomSheetDialog(this@ViewOwnPostCommentsActivity)
        this.fabAddPost = findViewById(R.id.fab_view_comments_add)

        val view = LayoutInflater.from(this@ViewOwnPostCommentsActivity).inflate(R.layout.dialog_post_artwork, null)

        this.fabAddPost.setOnClickListener {
            btmAddPost.setContentView(view)

            this.clDialogPostArtworkGallery = btmAddPost.findViewById(R.id.cl_dialog_post_artwork_gallery)!!
            this.clDialogPostArtworkPhoto = btmAddPost.findViewById(R.id.cl_dialog_post_artwork_photo)!!

            clDialogPostArtworkGallery.setOnClickListener {
                PostArtworkUtil.chooseFromGallery(this, galleryLauncher)
            }

            clDialogPostArtworkPhoto.setOnClickListener {
                photoFile = PostArtworkUtil.takeFromCamera(this, this@ViewOwnPostCommentsActivity, cameraLauncher)
            }

            btmAddPost.show()
        }
    }

    /**
     * Callback for the result from requesting permissions.
     *
     * @param requestCode The request code passed in <code>
     *     ActivityCompat.requestPermissions(android.app.Activity, String[], int)</code>.
     * @param permissions The requested permissions. Never null.
     * @param grantResults The grant results for the corresponding permissions which is either <code>
     *     PackageManager.PERMISSION_GRANTED</code> or <code>PackageManager.PERMISSION_DENIED</code>.
     *     Never null.
     */
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>,
                                            grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        permissionsResult(requestCode, grantResults, this@ViewOwnPostCommentsActivity, this)
    }

    /**
     * Defines the behavior related to choosing a photo from the Gallery or taking a photo using
     * the device camera based on the permissions granted by the user.
     *
     * @param requestCode The request code passed in <code>
     *     ActivityCompat.requestPermissions(android.app.Activity, String[], int)</code>.
     * @param grantResults The grant results for the corresponding permissions which is either <code>
     *     PackageManager.PERMISSION_GRANTED</code> or <code>PackageManager.PERMISSION_DENIED</code>.
     *     Never null.
     * @param context Context tied to this activity.
     * @param activity This activity.
     */
    private fun permissionsResult(requestCode: Int, grantResults: IntArray, context: Context,
                                  activity: Activity) {
        when (requestCode) {
            RequestCodes.REQUEST_CODE_POST_CAMERA.ordinal -> {
                val temp: File? = PostArtworkUtil.permissionsResultCamera(
                    grantResults, activity,
                    context, cameraLauncher
                )

                if (temp != null) {
                    photoFile = temp
                }
            }

            RequestCodes.REQUEST_CODE_POST_GALLERY.ordinal -> {
                PostArtworkUtil.permissionsResultGallery(grantResults, context, galleryLauncher)
            }
        }
    }
}