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
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
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

class ViewPostFollowedActivity : AppCompatActivity() {
    private lateinit var civItemViewPostFollowedProfilePic: CircleImageView
    private lateinit var tvItemViewPostFollowedUsername: TextView
    private lateinit var ivItemViewPostFollowedPost: ImageView
    private lateinit var tvItemViewPostFollowedTitle: TextView
    private lateinit var tvItemViewPostFollowedUpvoteCounter: TextView
    private lateinit var tvItemViewPostFollowedComments: TextView
    private lateinit var tvItemViewPostFollowedDatePosted: TextView
    private lateinit var tvItemViewPostFollowedMedium: TextView
    private lateinit var tvItemViewPostFollowedDimensions: TextView
    private lateinit var tvItemViewPostFollowedDescription: TextView
    private lateinit var tvItemViewPostFollowedTags: TextView

    private lateinit var ibItemViewPostFollowedBookmark: ImageButton
    private lateinit var ivItemViewPostFollowedUpvote: ImageView
    private lateinit var tvItemViewPostFollowedUpvote: TextView
    private lateinit var clItemViewPostFollowedUpvote: ConstraintLayout
    private lateinit var clItemViewPostFollowedComment: ConstraintLayout
    private lateinit var clItemViewPostFollowedShare: ConstraintLayout
    private lateinit var bnvViewPostFollowedBottom: BottomNavigationView

    private lateinit var btmAddPost: BottomSheetDialog
    private lateinit var fabAddPost: FloatingActionButton
    private lateinit var clDialogPostArtworkGallery: ConstraintLayout
    private lateinit var clDialogPostArtworkPhoto: ConstraintLayout

    private lateinit var srlViewPostFollowed: SwipeRefreshLayout

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

    private lateinit var firebaseHelper: FirebaseHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_post_followed)

        civItemViewPostFollowedProfilePic = findViewById(R.id.civ_item_view_post_followed_profile_pic)
        tvItemViewPostFollowedUsername = findViewById(R.id.tv_item_view_post_followed_username)
        ivItemViewPostFollowedPost = findViewById(R.id.iv_item_view_post_followed_post)
        tvItemViewPostFollowedTitle = findViewById(R.id.tv_item_view_post_followed_title)
        tvItemViewPostFollowedUpvoteCounter = findViewById(R.id.tv_item_view_post_followed_upvote_counter)
        tvItemViewPostFollowedComments = findViewById(R.id.tv_item_view_post_followed_comments)
        tvItemViewPostFollowedDatePosted = findViewById(R.id.tv_item_view_post_followed_date)
        tvItemViewPostFollowedMedium = findViewById(R.id.tv_item_view_post_followed_medium)
        tvItemViewPostFollowedDimensions = findViewById(R.id.tv_item_view_post_followed_dimen)
        tvItemViewPostFollowedDescription = findViewById(R.id.tv_item_view_post_followed_desc)
        tvItemViewPostFollowedTags = findViewById(R.id.tv_item_view_post_followed_tags)
        ibItemViewPostFollowedBookmark = findViewById(R.id.ib_item_view_post_followed_bookmark)
        ivItemViewPostFollowedUpvote = findViewById(R.id.iv_item_view_post_followed_upvote)
        tvItemViewPostFollowedUpvote = findViewById(R.id.tv_item_view_post_followed_upvote)
        clItemViewPostFollowedUpvote = findViewById(R.id.cl_item_view_post_followed_upvote)
        clItemViewPostFollowedComment = findViewById(R.id.cl_item_view_post_followed_comment)
        clItemViewPostFollowedShare = findViewById(R.id.cl_item_view_post_followed_share)

        initIntent()
        initComponents()

        initGalleryLauncher(this@ViewPostFollowedActivity)
        initCameraLauncher(this@ViewPostFollowedActivity)
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

        val postId = intent.getStringExtra(Keys.KEY_POSTID.name)
        val userIdPost = intent.getStringExtra(Keys.KEY_USERID.name)

        this.firebaseHelper = FirebaseHelper(this@ViewPostFollowedActivity, postId, userIdPost)

        val profilePicture = intent.getStringExtra(Keys.KEY_PROFILE_PICTURE.name)
        val username = intent.getStringExtra(Keys.KEY_USERNAME.name)

        val postImg = intent.getStringExtra(Keys.KEY_POST.name)

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

        var upvoteString = "$upvoteCounter upvotes"
        val commentString = "$comments comments"
        val tagsString = tags?.joinToString(", ")

        Glide.with(this)
            .load(profilePicture)
            .placeholder(R.drawable.chibi_artemis_hd)
            .error(R.drawable.chibi_artemis_hd)
            .into(this.civItemViewPostFollowedProfilePic)

        this.tvItemViewPostFollowedUsername.text = username

        Glide.with(this)
            .load(postImg)
            .placeholder(R.drawable.placeholder)
            .error(R.drawable.placeholder)
            .into(this.ivItemViewPostFollowedPost)

        this.tvItemViewPostFollowedTitle.text = title
        this.tvItemViewPostFollowedUpvoteCounter.text = upvoteString
        this.tvItemViewPostFollowedComments.text = commentString
        this.tvItemViewPostFollowedDatePosted.text = datePosted
        this.tvItemViewPostFollowedMedium.text = medium
        this.tvItemViewPostFollowedDimensions.text = dimensions
        this.tvItemViewPostFollowedDescription.text = description
        this.tvItemViewPostFollowedTags.text = tagsString

        updateBookmark(bookmark)
        updateUpvote(upvote)

        ibItemViewPostFollowedBookmark.setOnClickListener {
            bookmark = !bookmark
            updateBookmark(bookmark)

            if(bookmark){
                firebaseHelper.updateBookmarkDB("1", postId!!, "1")
            }

            else{
                firebaseHelper.updateBookmarkDB(null, postId!!, null)
            }
        }

        clItemViewPostFollowedUpvote.setOnClickListener {
            if (upvote) {
                upvote = false
                upvoteCounter -= 1
                upvoteString = "$upvoteCounter upvotes"
                this.tvItemViewPostFollowedUpvoteCounter.text = upvoteString
                updateUpvote(upvote)

                firebaseHelper.updateUpvoteDB(null, postId!!, null, upvoteCounter)

            } else {
                upvote = true
                upvoteCounter += 1
                upvoteString = "$upvoteCounter upvotes"
                this.tvItemViewPostFollowedUpvoteCounter.text = upvoteString
                updateUpvote(upvote)

                firebaseHelper.updateUpvoteDB( "1", postId!!, "1", upvoteCounter)
            }
        }

        clItemViewPostFollowedComment.setOnClickListener {
            val intent = Intent(this, ViewCommentsFollowedActivity::class.java)
            startActivity(intent)
        }

        clItemViewPostFollowedShare.setOnClickListener {
            shareOnFacebook()
        }

        civItemViewPostFollowedProfilePic.setOnClickListener {
            val intent = Intent(this, ViewUserActivity::class.java)

            intent.putExtra(
                Keys.KEY_USERID.name,
                userIdPost
            )

            startActivity(intent)
        }

        tvItemViewPostFollowedUsername.setOnClickListener {
            val intent = Intent(this, ViewUserActivity::class.java)

            intent.putExtra(
                Keys.KEY_USERID.name,
                userIdPost
            )

            startActivity(intent)
        }
    }

    private fun initComponents() {
        setSupportActionBar(findViewById(R.id.toolbar_view_post_followed))
        initActionBar()
        initSwipeRefresh()
        initBottom()
        addPost()
    }

    private fun initSwipeRefresh() {
        this.srlViewPostFollowed = findViewById(R.id.srl_view_post_followed)
        srlViewPostFollowed.setOnRefreshListener {
            onRefresh()
        }

        srlViewPostFollowed.setColorSchemeResources(R.color.purple_main,
            R.color.pinkish_purple,
            R.color.purple_pics_lighter,
            R.color.pinkish_purple_lighter)
    }

    private fun onRefresh() {
        Handler(Looper.getMainLooper()).postDelayed({
            srlViewPostFollowed.isRefreshing = false
        }, AnimationDuration.REFRESH_TIMEOUT.toLong())
    }

    private fun initActionBar() {
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
    }

    private fun initBottom() {
        this.bnvViewPostFollowedBottom = findViewById(R.id.nv_view_post_followed_bottom)

        BottomMenuUtil.setFinishBottomMenuListeners(bnvViewPostFollowedBottom, this,
            this@ViewPostFollowedActivity)
    }

    private fun updateBookmark(bookmark: Boolean) {
        if(bookmark) {
            this.ibItemViewPostFollowedBookmark.setImageResource(R.drawable.outline_bookmark_24)
            this.ibItemViewPostFollowedBookmark.imageTintList = ColorStateList.valueOf(
                ContextCompat.getColor(this.ibItemViewPostFollowedBookmark.context, R.color.pinkish_purple)
            )
        } else {
            this.ibItemViewPostFollowedBookmark.setImageResource(R.drawable.outline_bookmark_border_24)
            this.ibItemViewPostFollowedBookmark.imageTintList = ColorStateList.valueOf(
                ContextCompat.getColor(this.ibItemViewPostFollowedBookmark.context, R.color.default_gray)
            )
        }
    }

    private fun updateUpvote(upvote: Boolean) {
        if (upvote) {
            ivItemViewPostFollowedUpvote.setImageResource(R.drawable.upvote_colored)
            ivItemViewPostFollowedUpvote.imageTintList = ColorStateList.valueOf(
                ContextCompat.getColor(ivItemViewPostFollowedUpvote.context, R.color.pinkish_purple)
            )
            tvItemViewPostFollowedUpvote.setTextColor(ColorStateList.valueOf(
                ContextCompat.getColor(tvItemViewPostFollowedUpvote.context, R.color.pinkish_purple)))
        } else {
            ivItemViewPostFollowedUpvote.setImageResource(R.drawable.upvote_v2)
            ivItemViewPostFollowedUpvote.imageTintList = ColorStateList.valueOf(
                ContextCompat.getColor(ivItemViewPostFollowedUpvote.context, R.color.default_gray)
            )
            tvItemViewPostFollowedUpvote.setTextColor(ColorStateList.valueOf(
                ContextCompat.getColor(tvItemViewPostFollowedUpvote.context, R.color.default_gray)))
        }
    }

    /**
     * This hook is called whenever an item in your options menu is selected.
     *
     * @param item The menu item that was selected. This value cannot be <code>null</code>.
     * @return Return false to allow normal menu processing to proceed, true to consume it here.
     */
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
        this.btmAddPost = BottomSheetDialog(this@ViewPostFollowedActivity)
        this.fabAddPost = findViewById(R.id.fab_view_post_followed_add)

        val view = LayoutInflater.from(this@ViewPostFollowedActivity).inflate(R.layout.dialog_post_artwork, null)

        this.fabAddPost.setOnClickListener {
            btmAddPost.setContentView(view)

            this.clDialogPostArtworkGallery = btmAddPost.findViewById(R.id.cl_dialog_post_artwork_gallery)!!
            this.clDialogPostArtworkPhoto = btmAddPost.findViewById(R.id.cl_dialog_post_artwork_photo)!!

            clDialogPostArtworkGallery.setOnClickListener {
                PostArtworkUtil.chooseFromGallery(this, galleryLauncher)
            }

            clDialogPostArtworkPhoto.setOnClickListener {
                photoFile = PostArtworkUtil.takeFromCamera(this, this@ViewPostFollowedActivity, cameraLauncher)
            }

            btmAddPost.show()
        }
    }

    /**
     * Shares the posted artwork on the user's Facebook account.
     */
    private fun shareOnFacebook() {
        cmFacebook = CallbackManager.Factory.create()
        sdFacebook = ShareDialog(this@ViewPostFollowedActivity)

        sdFacebook.registerCallback(cmFacebook, object : FacebookCallback<Sharer.Result?> {
            override fun onSuccess(result: Sharer.Result?) {
                Toast.makeText(this@ViewPostFollowedActivity, "Shared on Facebook", Toast.LENGTH_SHORT).show()
            }

            override fun onCancel() {
                Toast.makeText(this@ViewPostFollowedActivity, "Sharing cancelled", Toast.LENGTH_SHORT).show()
            }

            override fun onError(error: FacebookException) {
                Toast.makeText(this@ViewPostFollowedActivity, "Sharing error occurred", Toast.LENGTH_SHORT).show()
            }
        })

        if (ShareDialog.canShow(SharePhotoContent::class.java)) {
            val bitmapDrawable = ivItemViewPostFollowedPost.drawable as BitmapDrawable
            val bitmap = bitmapDrawable.bitmap
            val username = "@" + tvItemViewPostFollowedUsername.text.toString()
            val captionedImage = CaptionPlacer.placeCaption(bitmap, username, "Posted on Artemis")
            val sharePhoto = SharePhoto.Builder()
                .setBitmap(captionedImage)
                .build()
            val sharePhotoContent = SharePhotoContent.Builder()
                .addPhoto(sharePhoto)
                .build()
            sdFacebook.show(sharePhotoContent)

            Toast.makeText(this@ViewPostFollowedActivity, "Opening Facebook", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(this@ViewPostFollowedActivity, "Unable to share post", Toast.LENGTH_SHORT).show()
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
        permissionsResult(requestCode, grantResults, this@ViewPostFollowedActivity, this)
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