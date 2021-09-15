package com.mobdeve.gonzales.lee.ong.artemis

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
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

class ViewCommentsFollowedActivity : AppCompatActivity() {
    private lateinit var dataCommentsFollowed: ArrayList<Comment>
    private lateinit var rvCommentsFollowed: RecyclerView
    private lateinit var commentsAdapter: CommentsAdapter
    private lateinit var llViewCommentsFollowedShimmer: LinearLayout
    private lateinit var sflViewCommentsFollowed: ShimmerFrameLayout
    private lateinit var bnvViewCommentsFollowedBottom: BottomNavigationView

    private lateinit var btmAddPost: BottomSheetDialog
    private lateinit var fabAddPost: FloatingActionButton
    private lateinit var clDialogPostArtworkGallery: ConstraintLayout
    private lateinit var clDialogPostArtworkPhoto: ConstraintLayout

    private lateinit var ibAddComment: ImageButton
    private lateinit var etComment: EditText

    private lateinit var pbComment: ProgressBar

    /**
     * First (main) text view displayed when the feed does not have any post to display.
     */
    private lateinit var ivNone: ImageView

    /**
     * Second text view displayed displayed when the feed does not have any post to display.
     */
    private lateinit var tvNone: TextView


    private lateinit var srlViewCommentsFollowed: SwipeRefreshLayout

    //Firebase
    private lateinit var mAuth: FirebaseAuth
    private lateinit var user: FirebaseUser
    private lateinit var userId: String
    private lateinit var db: DatabaseReference

    private lateinit var postId: String
    private var numComment: Int = 0

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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_comments_followed)

        initFirebase()
        initComponents()
        initIntent()
        initGalleryLauncher(this@ViewCommentsFollowedActivity)
        initCameraLauncher(this@ViewCommentsFollowedActivity)
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

    private fun initFirebase() {
        this.mAuth = Firebase.auth
        this.user = this.mAuth.currentUser!!
        this.userId = this.user.uid
        this.db = Firebase.database.reference
    }

    private fun initIntent(){
        val intent: Intent = intent
        this.postId = intent.getStringExtra(Keys.KEY_POSTID.name).toString()
        this.numComment = intent.getIntExtra(Keys.KEY_NUM_COMMENTS.name, 0)

        if (postId.isNullOrEmpty()){
            val intent = Intent(this@ViewCommentsFollowedActivity, BrokenLinkActivity::class.java)
            startActivity(intent)
        }
    }

    private fun initComponents() {
        setSupportActionBar(findViewById(R.id.toolbar_view_comments_followed))
        initShimmer()
        initActionBar()
        initBottom()
        addPost()
        initSwipeRefresh()

        addComment()
    }

    private fun initShimmer() {
        this.sflViewCommentsFollowed = findViewById(R.id.sfl_view_comments_followed)

        sflViewCommentsFollowed.startShimmer()

        Handler(Looper.getMainLooper()).postDelayed({
            initRecyclerView()
            sflViewCommentsFollowed.visibility = View.GONE
            rvCommentsFollowed.visibility = View.VISIBLE
        }, AnimationDuration.SHIMMER_TIMEOUT.toLong())
    }

    private fun initSwipeRefresh() {
        this.srlViewCommentsFollowed = findViewById(R.id.srl_view_comments_followed)
        srlViewCommentsFollowed.setOnRefreshListener {
            onRefresh()
        }

        srlViewCommentsFollowed.setColorSchemeResources(R.color.purple_main,
            R.color.pinkish_purple,
            R.color.purple_pics_lighter,
            R.color.pinkish_purple_lighter)
    }

    private fun onRefresh() {
        Handler(Looper.getMainLooper()).postDelayed({
            srlViewCommentsFollowed.isRefreshing = false
        }, AnimationDuration.REFRESH_TIMEOUT.toLong())
    }

    private fun initBottom() {
        this.bnvViewCommentsFollowedBottom = findViewById(R.id.nv_view_comments_followed_bottom)

        BottomMenuUtil.setFinishBottomMenuListeners(bnvViewCommentsFollowedBottom, this,
            this@ViewCommentsFollowedActivity)
    }

    private fun initActionBar() {
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
    }

    private fun initRecyclerView() {
        this.dataCommentsFollowed = arrayListOf<Comment>()

        this.rvCommentsFollowed = findViewById(R.id.rv_view_comments_followed)
        this.llViewCommentsFollowedShimmer = findViewById(R.id.ll_view_comments_followed_shimmer)

        this.rvCommentsFollowed.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)

        this.commentsAdapter = CommentsAdapter()

        this.rvCommentsFollowed.adapter = commentsAdapter

        this.rvCommentsFollowed.itemAnimator = null

        initContents()
    }

    private fun initContents(){
        this.ivNone = findViewById(R.id.iv_view_comments_followed_no_comment)
        this.tvNone = findViewById(R.id.tv_view_comments_followed_no_comment)

        val commentDB = this.db.child(Keys.KEY_DB_COMMENTS.name)

        commentDB.addChildEventListener(object : ChildEventListener {
            override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
                val commentSnap = snapshot.getValue(Comment::class.java)

                if (commentSnap != null &&
                    !commentSnap.getUserId().isNullOrEmpty() &&
                    !commentSnap.getCommentId().isNullOrEmpty() &&
                    !commentSnap.getPostId().isNullOrEmpty() &&
                    commentSnap.getPostId()!!.equals(postId)){

                    if (commentSnap.getUserId().equals(userId)){
                        commentSnap.setEditable(true)
                    }

                    dataCommentsFollowed.add(commentSnap)
                    commentsAdapter.updateComments(dataCommentsFollowed)
                }
            }

            override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {
                val commentSnap = snapshot.getValue(Comment::class.java)

                if (commentSnap != null &&
                    !commentSnap.getUserId().isNullOrEmpty() &&
                    !commentSnap.getCommentId().isNullOrEmpty() &&
                    !commentSnap.getPostId().isNullOrEmpty() &&
                    commentSnap.getPostId()!!.equals(postId)){

                    if (commentSnap.getUserId().equals(userId)){
                        commentSnap.setEditable(true)
                    }


                    val list = ArrayList<Comment>(dataCommentsFollowed)
                    val index = list.indexOfFirst { it.getCommentId() == commentSnap.getCommentId() }

                    if (index != -1){
                        list.set(index, commentSnap)

                        dataCommentsFollowed = list
                        commentsAdapter.updateComments(list)
                    }

                }
            }

            override fun onChildRemoved(snapshot: DataSnapshot) {
                val commentSnap = snapshot.getValue(Comment::class.java)

                if (commentSnap != null &&
                    !commentSnap.getUserId().isNullOrEmpty() &&
                    !commentSnap.getCommentId().isNullOrEmpty() &&
                    !commentSnap.getPostId().isNullOrEmpty()){

                    val list = ArrayList<Comment>(dataCommentsFollowed)

                    val index = list.indexOfFirst { it.getCommentId() == commentSnap.getCommentId() }

                    if (index != -1){
                        list.removeAt(index)

                        dataCommentsFollowed = list
                        commentsAdapter.updateComments(list)
                    }
                }
            }

            override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {
                /* This is intentionally left blank */
            }

            override fun onCancelled(error: DatabaseError) {
                val intent = Intent(this@ViewCommentsFollowedActivity, BrokenLinkActivity::class.java)
                startActivity(intent)
            }

        })
    }

    private fun addComment() {
        this.ibAddComment = findViewById(R.id.ib_add_comment_followed)
        this.etComment = findViewById(R.id.et_add_comment_followed)
        this.pbComment = findViewById(R.id.pb_view_comments_followed)


        this.ibAddComment.setOnClickListener {
            val commentText: String = etComment.text.toString().trim()

            if (commentText.isNotEmpty()) {
                this.pbComment.visibility = View.VISIBLE


                db.child(Keys.KEY_DB_USERS.name).child(userId)
                    .addListenerForSingleValueEvent(object: ValueEventListener {
                        override fun onDataChange(snapshot: DataSnapshot) {
                            val userImg: String = snapshot.child(Keys.userImg.name).getValue().toString()
                            val username: String = snapshot.child(Keys.username.name).getValue().toString()

                            val comment = Comment(userId, postId, userImg, username, commentText)

                            val commentDB = db.child(Keys.KEY_DB_COMMENTS.name)
                            val commentKey = commentDB.push().key!!

                            comment.setCommentId(commentKey)

                            addCommentDB(comment, commentKey)

                        }

                        override fun onCancelled(error: DatabaseError) {
                            val intent = Intent(this@ViewCommentsFollowedActivity, BrokenLinkActivity::class.java)
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

        val updates = hashMapOf<String, Any>(
            "/${Keys.KEY_DB_COMMENTS.name}/$commentKey" to comment,
            "/${Keys.KEY_DB_POSTS.name}/$postId/${Keys.comments.name}/$commentKey" to commentKey,
            "/${Keys.KEY_DB_POSTS.name}/$postId/${Keys.numComments.name}" to numComment,
        )

        db.updateChildren(updates)
            .addOnCompleteListener { task ->
                if (task.isSuccessful){
                    pbComment.visibility = View.GONE
                    Toast.makeText(this@ViewCommentsFollowedActivity, "Commented successfully", Toast.LENGTH_LONG).show()
                    etComment.text.clear()
                }

                else{
                    pbComment.visibility = View.VISIBLE
                    Toast.makeText(this@ViewCommentsFollowedActivity, "Failed to comment", Toast.LENGTH_LONG).show()
                }
            }
    }

    private fun addPost() {
        this.btmAddPost = BottomSheetDialog(this@ViewCommentsFollowedActivity)
        this.fabAddPost = findViewById(R.id.fab_view_comments_followed_add)

        val view = LayoutInflater.from(this@ViewCommentsFollowedActivity).inflate(R.layout.dialog_post_artwork, null)

        this.fabAddPost.setOnClickListener {
            btmAddPost.setContentView(view)

            this.clDialogPostArtworkGallery = btmAddPost.findViewById(R.id.cl_dialog_post_artwork_gallery)!!
            this.clDialogPostArtworkPhoto = btmAddPost.findViewById(R.id.cl_dialog_post_artwork_photo)!!

            clDialogPostArtworkGallery.setOnClickListener {
                PostArtworkUtil.chooseFromGallery(this, galleryLauncher)
            }

            clDialogPostArtworkPhoto.setOnClickListener {
                photoFile = PostArtworkUtil.takeFromCamera(this, this@ViewCommentsFollowedActivity, cameraLauncher)
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
        permissionsResult(requestCode, grantResults, this@ViewCommentsFollowedActivity, this)
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