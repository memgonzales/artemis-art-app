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
import android.view.View
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
import com.bumptech.glide.load.engine.DiskCacheStrategy
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

/**
 * Class handling the functionalities related to viewing a post of a followed user.
 *
 * @constructor Creates a class that handles the functionalities related to viewing a post of a
 * followed user.
 */
class   ViewPostFollowedActivity : AppCompatActivity() {
    /**
     * Profile picture of the user whose post is being viewed.
     */
    private lateinit var civItemViewPostFollowedProfilePic: CircleImageView

    /**
     * Username of the user whose post is being viewed.
     */
    private lateinit var tvItemViewPostFollowedUsername: TextView

    /**
     * Artwork of the post being viewed.
     */
    private lateinit var ivItemViewPostFollowedPost: ImageView

    /**
     * Title of the post being viewed.
     */
    private lateinit var tvItemViewPostFollowedTitle: TextView

    /**
     * Number of upvotes of the post being viewed.
     */
    private lateinit var tvItemViewPostFollowedUpvoteCounter: TextView

    /**
     * Number of comments of the post being viewed.
     */
    private lateinit var tvItemViewPostFollowedComments: TextView

    /**
     * Date posted of the post being viewed.
     */
    private lateinit var tvItemViewPostFollowedDatePosted: TextView

    /**
     * Medium of the artwork being viewed.
     */
    private lateinit var tvItemViewPostFollowedMedium: TextView

    /**
     * Dimensions of the artwork being viewed.
     */
    private lateinit var tvItemViewPostFollowedDimensions: TextView

    /**
     * Description of the post being viewed.
     */
    private lateinit var tvItemViewPostFollowedDescription: TextView

    /**
     * Tags of the post being viewed.
     */
    private lateinit var tvItemViewPostFollowedTags: TextView

    /**
     * Image button holding the bookmark icon.
     */
    private lateinit var ibItemViewPostFollowedBookmark: ImageButton

    /**
     * Image view holding the upvote icon.
     */
    private lateinit var ivItemViewPostFollowedUpvote: ImageView

    /**
     * Text view holding the "Upvote" label.
     */
    private lateinit var tvItemViewPostFollowedUpvote: TextView

    /**
     * Constraint layout holding the upvote option.
     */
    private lateinit var clItemViewPostFollowedUpvote: ConstraintLayout

    /**
     * Constraint layout holding the comment option.
     */
    private lateinit var clItemViewPostFollowedComment: ConstraintLayout

    /**
     * Constraint layout holding the share option.
     */
    private lateinit var clItemViewPostFollowedShare: ConstraintLayout

    /**
     * Bottom navigation view containing the menu items for Home, Followed, Bookmarks, and Profile.
     */
    private lateinit var bnvViewPostFollowedBottom: BottomNavigationView

    /**
     * Bottom sheet dialog displayed when the user clicks the floating action button
     * for posting an artwork.
     */
    private lateinit var btmAddPost: BottomSheetDialog

    /**
     * Floating action button for posting an artwork.
     */
    private lateinit var fabAddPost: FloatingActionButton

    /**
     * Clickable constraint layout (part of the bottom sheet dialog) related to the option
     * of the user uploading a photo of their artwork from the Gallery.
     */
    private lateinit var clDialogPostArtworkGallery: ConstraintLayout

    /**
     * Clickable constraint layout (part of the bottom sheet dialog) related to the option
     * of the user taking a photo of their artwork using the device camera.
     */
    private lateinit var clDialogPostArtworkPhoto: ConstraintLayout

    /**
     * Layout for registering a swipe gesture as a request to refresh this activity.
     */
    private lateinit var srlViewPostFollowed: SwipeRefreshLayout

    /**
     * Callback manager for handling the share on Facebook feature.
     */
    private lateinit var cmFacebook: CallbackManager

    /**
     * Share dialog for sharing the artwork on Facebook.
     */
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
     * Activity result launcher related to choosing photos from the Gallery.
     */
    private lateinit var galleryLauncher: ActivityResultLauncher<Intent>

    /**
     * Object for accessing the Firebase helper methods.
     */
    private lateinit var firebaseHelper: FirebaseHelper

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

    /**
     * Initializes the intent passed to the activity.
     */
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

        var upvoteString = ""
        var commentString = ""

        if (upvoteCounter == 1) {
            upvoteString = "$upvoteCounter upvote"
        } else {
            upvoteString = "$upvoteCounter upvotes"
        }

        if (comments == 1) {
            commentString = "$comments comment"
        } else {
            commentString = "$comments comments"
        }

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

        if (!title.isNullOrEmpty()) {
            this.tvItemViewPostFollowedTitle.visibility = View.VISIBLE
            this.tvItemViewPostFollowedTitle.text = title
        } else {
            this.tvItemViewPostFollowedTitle.visibility = View.INVISIBLE
        }

        this.tvItemViewPostFollowedUpvoteCounter.text = upvoteString
        this.tvItemViewPostFollowedComments.text = commentString
        this.tvItemViewPostFollowedDatePosted.text = datePosted

        if(!medium.isNullOrEmpty()) {
            this.tvItemViewPostFollowedMedium.visibility = View.VISIBLE
            this.tvItemViewPostFollowedMedium.text = medium
        } else {
            this.tvItemViewPostFollowedMedium.visibility = View.GONE
        }

        if(!dimensions.isNullOrEmpty()) {
            this.tvItemViewPostFollowedDimensions.visibility = View.VISIBLE
            this.tvItemViewPostFollowedDimensions.text = dimensions
        } else {
            this.tvItemViewPostFollowedDimensions.visibility = View.GONE
        }

        if(!description.isNullOrEmpty()) {
            this.tvItemViewPostFollowedDescription.visibility = View.VISIBLE
            this.tvItemViewPostFollowedDescription.text = description
        } else {
            this.tvItemViewPostFollowedDescription.visibility = View.GONE
        }

        this.tvItemViewPostFollowedTags.text = tagsString

        updateBookmark(bookmark)
        updateUpvote(upvote)

        ibItemViewPostFollowedBookmark.setOnClickListener {
            bookmark = !bookmark
            updateBookmark(bookmark)

            if(bookmark) {
                firebaseHelper.updateBookmarkDB("1", postId!!, "1")
            } else {
                firebaseHelper.updateBookmarkDB(null, postId!!, null)
            }
        }

        clItemViewPostFollowedUpvote.setOnClickListener {
            if (upvote) {
                upvote = false
                upvoteCounter -= 1

                if (upvoteCounter == 1) {
                    upvoteString = "$upvoteCounter upvote"
                } else {
                    upvoteString = "$upvoteCounter upvotes"
                }

                this.tvItemViewPostFollowedUpvoteCounter.text = upvoteString
                updateUpvote(upvote)

                firebaseHelper.updateUpvoteDB(null, postId!!, null, upvoteCounter)

            } else {
                upvote = true
                upvoteCounter += 1

                if (upvoteCounter == 1) {
                    upvoteString = "$upvoteCounter upvote"
                } else {
                    upvoteString = "$upvoteCounter upvotes"
                }

                this.tvItemViewPostFollowedUpvoteCounter.text = upvoteString
                updateUpvote(upvote)

                firebaseHelper.updateUpvoteDB( "1", postId!!, "1", upvoteCounter)
            }
        }

        clItemViewPostFollowedComment.setOnClickListener {
            val intent = Intent(this, ViewCommentsFollowedActivity::class.java)

            intent.putExtra(
                Keys.KEY_USERID.name,
                userIdPost
            )
            intent.putExtra(
                Keys.KEY_PROFILE_PICTURE.name,
                profilePicture
            )
            intent.putExtra(
                Keys.KEY_USERNAME.name,
                username
            )
            intent.putExtra(
                Keys.KEY_POST.name,
                postImg
            )
            intent.putExtra(
                Keys.KEY_TITLE.name,
                title
            )
            intent.putExtra(
                Keys.KEY_POSTID.name,
                postId
            )
            intent.putExtra(
                Keys.KEY_NUM_UPVOTES.name,
                upvoteCounter
            )
            intent.putExtra(
                Keys.KEY_NUM_COMMENTS.name,
                comments
            )
            intent.putExtra(
                Keys.KEY_DATE_POSTED.name,
                datePosted
            )
            intent.putExtra(
                Keys.KEY_MEDIUM.name,
                medium
            )
            intent.putExtra(
                Keys.KEY_DIMENSIONS.name,
                dimensions
            )
            intent.putExtra(
                Keys.KEY_DESCRIPTION.name,
                description
            )
            intent.putExtra(
                Keys.KEY_TAGS.name,
                tags
            )
            intent.putExtra(
                Keys.KEY_BOOKMARK.name,
                bookmark
            )
            intent.putExtra(
                Keys.KEY_UPVOTE.name,
                upvote
            )
            startActivity(intent)
            finish()
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

    /**
     * Initializes the components of the activity.
     */
    private fun initComponents() {
        setSupportActionBar(findViewById(R.id.toolbar_view_post_followed))
        initActionBar()
        initSwipeRefresh()
        initBottom()
        addPost()
    }

    /**
     * Initializes the swipe refresh layout and defines the behavior when the screen is swiped
     * to refresh.
     */
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

    /**
     * Refetches data from the database and reshuffles the display of existing data when the screen
     * is swiped to refresh.
     */
    private fun onRefresh() {
        Handler(Looper.getMainLooper()).postDelayed({
            initIntent()
            srlViewPostFollowed.isRefreshing = false
        }, AnimationDuration.REFRESH_TIMEOUT.toLong())
    }

    /**
     * Adds a back button to the action bar.
     */
    private fun initActionBar() {
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
    }

    /**
     * Sets the listeners for the menu selection found in the bottom navigation view.
     */
    private fun initBottom() {
        this.bnvViewPostFollowedBottom = findViewById(R.id.nv_view_post_followed_bottom)

        BottomMenuUtil.setFinishBottomMenuListeners(bnvViewPostFollowedBottom, this,
            this@ViewPostFollowedActivity)
    }

    /**
     * Updates the bookmark status of a post.
     *
     * @param bookmark <code>true</code> if the user chooses to bookmark the post; <code>false</code>
     * if the user chooses to remove the bookmark status of the post
     */
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

    /**
     * Updates the upvote status of a post.
     *
     * @param upvote <code>true</code> if the user chooses to upvote the post; <code>false</code>
     * if the user chooses to remove their upvote from the post
     */
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

    /**
     * Sets the listeners in relation to adding an artwork (that is, by either choosing an image
     * from the gallery or taking a photo using the device camera) to be posted on Artemis.
     */
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
     *     ActivityCompat.requestPermissions(android.app.Activity, String[], int)</code>.
     * @param permissions The requested permissions. Never null.
     * @param grantResults The grant results for the corresponding permissions which is either <code>
     *     PackageManager.PERMISSION_GRANTED</code> or <code>PackageManager.PERMISSION_DENIED</code>.
     *     Never null.
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
     * @param requestCode The request code passed in <code>
     *     ActivityCompat.requestPermissions(android.app.Activity, String[], int)</code>.
     * @param grantResults The grant results for the corresponding permissions which is either <code>
     *     PackageManager.PERMISSION_GRANTED</code> or <code>PackageManager.PERMISSION_DENIED</code>.
     *     Never null.
     * @param context Context tied to this activity.
     * @param activity This activity.
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