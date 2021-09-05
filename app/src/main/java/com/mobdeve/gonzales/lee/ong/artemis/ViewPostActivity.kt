package com.mobdeve.gonzales.lee.ong.artemis

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.res.ColorStateList
import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.MenuItem
import android.widget.*
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.bumptech.glide.Glide
import com.facebook.CallbackManager
import com.facebook.FacebookCallback
import com.facebook.FacebookException
import com.facebook.share.Sharer
import com.facebook.share.model.SharePhoto
import com.facebook.share.model.SharePhotoContent
import com.facebook.share.widget.ShareDialog
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.floatingactionbutton.FloatingActionButton
import de.hdodenhof.circleimageview.CircleImageView
import java.io.File


class ViewPostActivity : AppCompatActivity() {
    private lateinit var civItemViewPostProfilePic: CircleImageView
    private lateinit var tvItemViewPostUsername: TextView
    private lateinit var ivItemViewPostPost: ImageView
    private lateinit var tvItemViewPostTitle: TextView
    private lateinit var tvItemViewPostUpvoteCounter: TextView
    private lateinit var tvItemViewPostComments: TextView
    private lateinit var tvItemViewPostDatePosted: TextView
    private lateinit var tvItemViewPostMedium: TextView
    private lateinit var tvItemViewPostDimensions: TextView
    private lateinit var tvItemViewPostDescription: TextView
    private lateinit var tvItemViewPostTags: TextView

    private lateinit var ibItemViewPostBookmark: ImageButton
    private lateinit var ivItemViewPostUpvote: ImageView
    private lateinit var tvItemViewPostUpvote: TextView
    private lateinit var clItemViewPostUpvote: ConstraintLayout
    private lateinit var clItemViewPostComment: ConstraintLayout
    private lateinit var clItemViewPostShare: ConstraintLayout
    private lateinit var bnvViewPostBottom: BottomNavigationView

    private lateinit var btmAddPost: BottomSheetDialog
    private lateinit var fabAddPost: FloatingActionButton
    private lateinit var clDialogPostArtworkGallery: ConstraintLayout
    private lateinit var clDialogPostArtworkPhoto: ConstraintLayout

    private lateinit var srlViewPost: SwipeRefreshLayout

    private lateinit var cmFacebook: CallbackManager
    private lateinit var sdFacebook: ShareDialog

    /**
     * Photo of the artwork for posting.
     */
    private lateinit var photoFile: File

    /**
     * Activity result launcher related to taking photos using the device camera.
     */
    private lateinit var cameraLauncher: ActivityResultLauncher<Intent>

    /**
     * Activity result launcher related to choosing photos from the Gallery
     */
    private lateinit var galleryLauncher: ActivityResultLauncher<Intent>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_post)

        civItemViewPostProfilePic = findViewById(R.id.civ_item_view_post_profile_pic)
        tvItemViewPostUsername = findViewById(R.id.tv_item_view_post_username)
        ivItemViewPostPost = findViewById(R.id.iv_item_view_post_post)
        tvItemViewPostTitle = findViewById(R.id.tv_item_view_post_title)
        tvItemViewPostUpvoteCounter = findViewById(R.id.tv_item_view_post_upvote_counter)
        tvItemViewPostComments = findViewById(R.id.tv_item_view_post_comments)
        tvItemViewPostDatePosted = findViewById(R.id.tv_item_view_post_date)
        tvItemViewPostMedium = findViewById(R.id.tv_item_view_post_medium)
        tvItemViewPostDimensions = findViewById(R.id.tv_item_view_post_dimen)
        tvItemViewPostDescription = findViewById(R.id.tv_item_view_post_desc)
        tvItemViewPostTags = findViewById(R.id.tv_item_view_post_tags)
        ibItemViewPostBookmark = findViewById(R.id.ib_item_view_post_bookmark)
        ivItemViewPostUpvote = findViewById(R.id.iv_item_view_post_upvote)
        tvItemViewPostUpvote = findViewById(R.id.tv_item_view_post_upvote)
        clItemViewPostUpvote = findViewById(R.id.cl_item_view_post_upvote)
        clItemViewPostComment = findViewById(R.id.cl_item_view_post_comment)
        clItemViewPostShare = findViewById(R.id.cl_item_view_post_share)

        initIntent()
        initComponents()

        initGalleryLauncher(this@ViewPostActivity)
        initCameraLauncher(this@ViewPostActivity)
    }

    /**
     * Initializes the activity result launcher related to choosing photos from the Gallery.
     *
     * @packageContext context tied to this activity
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
     * Initializes the activity result launcher related to taking photos using the device camera
     *
     * @packageContext context tied to this activity
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

    private fun initIntent() {
        val intent: Intent = intent

        val profilePicture = intent.getIntExtra(Keys.KEY_PROFILE_PICTURE.name, 0)
        val username = intent.getStringExtra(Keys.KEY_USERNAME.name)
        val post = intent.getIntExtra(Keys.KEY_POST.name, 0)
        val title = intent.getStringExtra(Keys.KEY_TITLE.name)
        var upvoteCounter = intent.getIntExtra(Keys.KEY_NUM_UPVOTES.name, 0)
        val comments = intent.getIntExtra(Keys.KEY_NUM_COMMENTS.name, 0)
        val datePosted = intent.getStringExtra(Keys.KEY_DATE_POSTED.name)
        val medium = intent.getStringExtra(Keys.KEY_MEDIUM.name)
        val dimensions = intent.getStringExtra(Keys.KEY_DIMENSIONS.name)
        val description = intent.getStringExtra(Keys.KEY_DESCRIPTION.name)
        val tags = intent.getStringArrayListExtra(Keys.KEY_TAGS.name)
        var bookmark = intent.getBooleanExtra(Keys.KEY_BOOKMARK.name, false)
        var upvote = intent.getBooleanExtra(Keys.KEY_UPVOTE.name, false)
        val tempPost = "https://firebasestorage.googleapis.com/v0/b/artemis-77e4e.appspot.com/o/shoobs.jpg?alt=media&token=759445bd-d3b6-4384-8d8e-0fe5f5f45ba5"
        var upvoteString = "$upvoteCounter upvotes"
        val commentString = "$comments comments"
        val tagsString = tags?.joinToString(", ")

        this.civItemViewPostProfilePic.setImageResource(profilePicture)
        this.tvItemViewPostUsername.text = username
    //    this.ivItemViewPostPost.setImageResource(post)
        Glide.with(this).load(tempPost).into(this.ivItemViewPostPost)
        this.tvItemViewPostTitle.text = title
        this.tvItemViewPostUpvoteCounter.text = upvoteString
        this.tvItemViewPostComments.text = commentString
        this.tvItemViewPostDatePosted.text = datePosted
        this.tvItemViewPostMedium.text = medium
        this.tvItemViewPostDimensions.text = dimensions
        this.tvItemViewPostDescription.text = description
        this.tvItemViewPostTags.text = tagsString

        updateBookmark(bookmark)
        updateUpvote(upvote)

        ibItemViewPostBookmark.setOnClickListener {
            bookmark = !bookmark
            updateBookmark(bookmark)
        }

        clItemViewPostUpvote.setOnClickListener {
            if (upvote) {
                upvote = false
                upvoteCounter -= 1
                upvoteString = "$upvoteCounter upvotes"
                this.tvItemViewPostUpvoteCounter.text = upvoteString
                updateUpvote(upvote)
            } else {
                upvote = true
                upvoteCounter += 1
                upvoteString = "$upvoteCounter upvotes"
                this.tvItemViewPostUpvoteCounter.text = upvoteString
                updateUpvote(upvote)
            }
        }

        clItemViewPostComment.setOnClickListener {
            val intent = Intent(this, ViewCommentsActivity::class.java)
            startActivity(intent)
        }

        clItemViewPostShare.setOnClickListener {
            shareOnFacebook()
        }

        civItemViewPostProfilePic.setOnClickListener {
            val intent = Intent(this, ViewUserActivity::class.java)

            intent.putExtra(
                Keys.KEY_PROFILE_PICTURE.name,
                profilePicture
            )
            intent.putExtra(
                Keys.KEY_USERNAME.name,
                username
            )
            intent.putExtra(
                Keys.KEY_BIO.name,
                "Dummy bio"
            )

            startActivity(intent)
        }

        tvItemViewPostUsername.setOnClickListener {
            val intent = Intent(this, ViewUserActivity::class.java)

            intent.putExtra(
                Keys.KEY_PROFILE_PICTURE.name,
                profilePicture
            )
            intent.putExtra(
                Keys.KEY_USERNAME.name,
                username
            )
            intent.putExtra(
                Keys.KEY_BIO.name,
                "Dummy bio"
            )

            startActivity(intent)
        }
    }

    private fun initComponents() {
        setSupportActionBar(findViewById(R.id.toolbar_view_post))
        initActionBar()
        initSwipeRefresh()
        initBottom()
        addPost()
    }

    private fun initSwipeRefresh() {
        this.srlViewPost = findViewById(R.id.srl_view_post)
        srlViewPost.setOnRefreshListener {
            onRefresh()
        }

        srlViewPost.setColorSchemeResources(R.color.purple_main,
            R.color.pinkish_purple,
            R.color.purple_pics_lighter,
            R.color.pinkish_purple_lighter)
    }

    private fun onRefresh() {
        Handler(Looper.getMainLooper()).postDelayed({
            srlViewPost.isRefreshing = false
        }, AnimationDuration.REFRESH_TIMEOUT.toLong())
    }

    private fun initBottom() {
        this.bnvViewPostBottom = findViewById(R.id.nv_view_post_bottom)

        BottomMenuUtil.setFinishBottomMenuListeners(bnvViewPostBottom, this,
            this@ViewPostActivity)
    }

    private fun initActionBar() {
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
    }

    private fun updateBookmark(bookmark: Boolean) {
        if(bookmark) {
            this.ibItemViewPostBookmark.setImageResource(R.drawable.outline_bookmark_24)
            this.ibItemViewPostBookmark.imageTintList = ColorStateList.valueOf(
                ContextCompat.getColor(this.ibItemViewPostBookmark.context, R.color.pinkish_purple)
            )
        } else {
            this.ibItemViewPostBookmark.setImageResource(R.drawable.outline_bookmark_border_24)
            this.ibItemViewPostBookmark.imageTintList = ColorStateList.valueOf(
                ContextCompat.getColor(this.ibItemViewPostBookmark.context, R.color.default_gray)
            )
        }
    }

    private fun updateUpvote(upvote: Boolean) {
        if (upvote) {
            ivItemViewPostUpvote.setImageResource(R.drawable.upvote_colored)
            ivItemViewPostUpvote.imageTintList = ColorStateList.valueOf(
                ContextCompat.getColor(ivItemViewPostUpvote.context, R.color.pinkish_purple)
            )
            tvItemViewPostUpvote.setTextColor(ColorStateList.valueOf(
                ContextCompat.getColor(tvItemViewPostUpvote.context, R.color.pinkish_purple)))
        } else {
            ivItemViewPostUpvote.setImageResource(R.drawable.upvote_v2)
            ivItemViewPostUpvote.imageTintList = ColorStateList.valueOf(
                ContextCompat.getColor(ivItemViewPostUpvote.context, R.color.default_gray)
            )
            tvItemViewPostUpvote.setTextColor(ColorStateList.valueOf(
                ContextCompat.getColor(tvItemViewPostUpvote.context, R.color.default_gray)))
        }
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

    private fun addPost() {
        this.btmAddPost = BottomSheetDialog(this@ViewPostActivity)
        this.fabAddPost = findViewById(R.id.fab_view_post_add)

        val view = LayoutInflater.from(this@ViewPostActivity).inflate(R.layout.dialog_post_artwork, null)

        this.fabAddPost.setOnClickListener {
            btmAddPost.setContentView(view)

            this.clDialogPostArtworkGallery = btmAddPost.findViewById(R.id.cl_dialog_post_artwork_gallery)!!
            this.clDialogPostArtworkPhoto = btmAddPost.findViewById(R.id.cl_dialog_post_artwork_photo)!!

            clDialogPostArtworkGallery.setOnClickListener {
                PostArtworkUtil.chooseFromGallery(this, galleryLauncher)
            }

            clDialogPostArtworkPhoto.setOnClickListener {
                photoFile = PostArtworkUtil.takeFromCamera(this, this@ViewPostActivity, cameraLauncher)
            }

            btmAddPost.show()
        }
    }

    /**
     * Shares the posted artwork on the user's Facebook account.
     */
    private fun shareOnFacebook() {
        cmFacebook = CallbackManager.Factory.create()
        sdFacebook = ShareDialog(this@ViewPostActivity)

        sdFacebook.registerCallback(cmFacebook, object : FacebookCallback<Sharer.Result?> {
            override fun onSuccess(result: Sharer.Result?) {
                Toast.makeText(this@ViewPostActivity, "Shared on Facebook", Toast.LENGTH_SHORT).show()
            }

            override fun onCancel() {
                Toast.makeText(this@ViewPostActivity, "Sharing cancelled", Toast.LENGTH_SHORT).show()
            }

            override fun onError(error: FacebookException) {
                Toast.makeText(this@ViewPostActivity, "Sharing error occurred", Toast.LENGTH_SHORT).show()
            }
        })

        if (ShareDialog.canShow(SharePhotoContent::class.java)) {
            val bitmapDrawable = ivItemViewPostPost.drawable as BitmapDrawable
            val bitmap = bitmapDrawable.bitmap
            val username = "@" + tvItemViewPostUsername.text.toString()
            val captionedImage = CaptionPlacer.placeCaption(bitmap, username, "Posted on Artemis")
            val sharePhoto = SharePhoto.Builder()
                .setBitmap(captionedImage)
                .build()
            val sharePhotoContent = SharePhotoContent.Builder()
                .addPhoto(sharePhoto)
                .build()
            sdFacebook.show(sharePhotoContent)

            Toast.makeText(this@ViewPostActivity, "Opening Facebook", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(this@ViewPostActivity, "Unable to share post", Toast.LENGTH_SHORT).show()
        }
    }

    /**
     * Callback for the result from requesting permissions.
     *
     * @param requestCode The request code passed in <code>
     *     ActivityCompat.requestPermissions(android.app.Activity, String[], int)</code>
     * @param permissions The requested permissions. Never null
     * @param grantResults The grant results for the corresponding permissions which is either <code>
     *     PackageManager.PERMISSION_GRANTED</code> or <code>PackageManager.PERMISSION_DENIED</code>.
     *     Never null
     */
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>,
                                            grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        permissionsResult(requestCode, grantResults, this@ViewPostActivity, this)
    }

    /**
     * Defines the behavior related to choosing a photo from the Gallery or taking a photo using
     * the device camera based on the permissions granted by the user.
     *
     * @param requestCode the request code passed in <code>
     *     ActivityCompat.requestPermissions(android.app.Activity, String[], int)</code>
     * @param grantResults the grant results for the corresponding permissions which is either <code>
     *     PackageManager.PERMISSION_GRANTED</code> or <code>PackageManager.PERMISSION_DENIED</code>.
     *     Never null
     * @param context context tied to this activity
     * @param activity this activity
     */
    private fun permissionsResult(requestCode: Int, grantResults: IntArray, context: Context,
                                  activity: Activity
    ) {
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