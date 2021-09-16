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

/**
 * Class handling the functionalities related to viewing a post.
 *
 * @constructor Creates a class that handles the functionalities related to viewing a post.
 */
class ViewPostActivity : AppCompatActivity() {
    /**
     * Profile picture of the user whose post is being viewed.
     */
    private lateinit var civItemViewPostProfilePic: CircleImageView

    /**
     * Username of the user whose post is being viewed.
     */
    private lateinit var tvItemViewPostUsername: TextView

    /**
     * Artwork of the post being viewed.
     */
    private lateinit var ivItemViewPostPost: ImageView

    /**
     * Title of the post being viewed.
     */
    private lateinit var tvItemViewPostTitle: TextView

    /**
     * Number of upvotes of the post being viewed.
     */
    private lateinit var tvItemViewPostUpvoteCounter: TextView

    /**
     * Number of comments of the post being viewed.
     */
    private lateinit var tvItemViewPostComments: TextView

    /**
     * Date posted of the post being viewed.
     */
    private lateinit var tvItemViewPostDatePosted: TextView

    /**
     * Medium of the artwork being viewed.
     */
    private lateinit var tvItemViewPostMedium: TextView

    /**
     * Dimensions of the artwork being viewed.
     */
    private lateinit var tvItemViewPostDimensions: TextView

    /**
     * Description of the post being viewed.
     */
    private lateinit var tvItemViewPostDescription: TextView

    /**
     * Tags of the post being viewed.
     */
    private lateinit var tvItemViewPostTags: TextView

    /**
     * Image button holding the bookmark icon.
     */
    private lateinit var ibItemViewPostBookmark: ImageButton

    /**
     * Image view holding the upvote icon.
     */
    private lateinit var ivItemViewPostUpvote: ImageView

    /**
     * Text view holding the "Upvote" label.
     */
    private lateinit var tvItemViewPostUpvote: TextView

    /**
     * Constraint layout holding the upvote option.
     */
    private lateinit var clItemViewPostUpvote: ConstraintLayout

    /**
     * Constraint layout holding the comment option.
     */
    private lateinit var clItemViewPostComment: ConstraintLayout

    /**
     * Constraint layout holding the share option.
     */
    private lateinit var clItemViewPostShare: ConstraintLayout

    /**
     * Bottom navigation view containing the menu items for Home, Followed, Bookmarks, and Profile.
     */
    private lateinit var bnvViewPostBottom: BottomNavigationView

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
    private lateinit var srlViewPost: SwipeRefreshLayout

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

        this.firebaseHelper = FirebaseHelper(this@ViewPostActivity, postId, userIdPost)

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
            .into(this.civItemViewPostProfilePic)

        this.tvItemViewPostUsername.text = username

        Glide.with(this)
            .load(postImg)
            .placeholder(R.drawable.placeholder)
            .error(R.drawable.placeholder)
            .into(this.ivItemViewPostPost)

        if (!title.isNullOrEmpty()){
            this.tvItemViewPostTitle.visibility = View.VISIBLE
            this.tvItemViewPostTitle.text = title
        }
        else{
            this.tvItemViewPostTitle.visibility = View.INVISIBLE
        }

        this.tvItemViewPostUpvoteCounter.text = upvoteString
        this.tvItemViewPostComments.text = commentString
        this.tvItemViewPostDatePosted.text = datePosted

        if(!medium.isNullOrEmpty()){
            this.tvItemViewPostMedium.visibility = View.VISIBLE
            this.tvItemViewPostMedium.text = medium
        }
        else{
            this.tvItemViewPostMedium.visibility = View.GONE
        }

        if(!dimensions.isNullOrEmpty()){
            this.tvItemViewPostDimensions.visibility = View.VISIBLE
            this.tvItemViewPostDimensions.text = dimensions
        }
        else{
            this.tvItemViewPostDimensions.visibility = View.GONE
        }

        if(!description.isNullOrEmpty()){
            this.tvItemViewPostDescription.visibility = View.VISIBLE
            this.tvItemViewPostDescription.text = description
        }
        else{
            this.tvItemViewPostDescription.visibility = View.GONE
        }

        this.tvItemViewPostTags.text = tagsString

        updateBookmark(bookmark)
        updateUpvote(upvote)

        ibItemViewPostBookmark.setOnClickListener {
            bookmark = !bookmark
            updateBookmark(bookmark)

            if(bookmark){
                this.firebaseHelper.updateBookmarkDB("1", postId!!, "1")
            }

            else{
                this.firebaseHelper.updateBookmarkDB(null, postId!!, null)
            }
        }

        clItemViewPostUpvote.setOnClickListener {
            if (upvote) {
                upvote = false
                upvoteCounter -= 1

                if (upvoteCounter == 1) {
                    upvoteString = "$upvoteCounter upvote"
                } else {
                    upvoteString = "$upvoteCounter upvotes"
                }

                this.tvItemViewPostUpvoteCounter.text = upvoteString
                updateUpvote(upvote)

                this.firebaseHelper.updateUpvoteDB(null, postId!!, null, upvoteCounter)

            } else {
                upvote = true
                upvoteCounter += 1

                if (upvoteCounter == 1) {
                    upvoteString = "$upvoteCounter upvote"
                } else {
                    upvoteString = "$upvoteCounter upvotes"
                }

                this.tvItemViewPostUpvoteCounter.text = upvoteString
                updateUpvote(upvote)

                this.firebaseHelper.updateUpvoteDB( "1", postId!!, "1", upvoteCounter)
            }
        }

        clItemViewPostComment.setOnClickListener {
            val intent = Intent(this, ViewCommentsActivity::class.java)

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

        clItemViewPostShare.setOnClickListener {
            shareOnFacebook()
        }

        civItemViewPostProfilePic.setOnClickListener {
            val intent = Intent(this, ViewUserActivity::class.java)

            intent.putExtra(
                Keys.KEY_USERID.name,
                userIdPost
            )

            startActivity(intent)
        }

        tvItemViewPostUsername.setOnClickListener {
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
        setSupportActionBar(findViewById(R.id.toolbar_view_post))
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
        this.srlViewPost = findViewById(R.id.srl_view_post)
        srlViewPost.setOnRefreshListener {
            onRefresh()
        }

        srlViewPost.setColorSchemeResources(R.color.purple_main,
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
            srlViewPost.isRefreshing = false
        }, AnimationDuration.REFRESH_TIMEOUT.toLong())
    }

    /**
     * Sets the listeners for the menu selection found in the bottom navigation view.
     */
    private fun initBottom() {
        this.bnvViewPostBottom = findViewById(R.id.nv_view_post_bottom)

        BottomMenuUtil.setFinishBottomMenuListeners(bnvViewPostBottom, this,
            this@ViewPostActivity)
    }

    /**
     * Adds a back button to the action bar.
     */
    private fun initActionBar() {
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
    }

    /**
     * Updates the bookmark status of a post.
     *
     * @param bookmark <code>true</code> if the user chooses to bookmark the post; <code>false</code>
     * if the user chooses to remove the bookmark status of the post
     */
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

    /**
     * Updates the upvote status of a post.
     *
     * @param upvote <code>true</code> if the user chooses to upvote the post; <code>false</code>
     * if the user chooses to remove their upvote from the post
     */
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
     *     ActivityCompat.requestPermissions(android.app.Activity, String[], int)</code>.
     * @param permissions The requested permissions. Never null.
     * @param grantResults The grant results for the corresponding permissions which is either <code>
     *     PackageManager.PERMISSION_GRANTED</code> or <code>PackageManager.PERMISSION_DENIED</code>.
     *     Never null.
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