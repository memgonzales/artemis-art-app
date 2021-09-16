package com.mobdeve.gonzales.lee.ong.artemis

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.res.ColorStateList
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
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
 * Class handling the functionalities related to viewing a user's own post.
 *
 * @constructor Creates a class that handles the functionalities related to viewing a user's
 * own post.
 */
class ViewOwnPostActivity : AppCompatActivity() {
    /**
     * Profile picture of the user whose post is being viewed.
     */
    private lateinit var civItemViewOwnPostProfilePic: CircleImageView

    /**
     * Username of the user whose post is being viewed.
     */
    private lateinit var tvItemViewOwnPostUsername: TextView

    /**
     * Artwork of the post being viewed.
     */
    private lateinit var ivItemViewOwnPostPostImg: ImageView

    /**
     * Title of the post being viewed.
     */
    private lateinit var tvItemViewOwnPostTitle: TextView

    /**
     * Number of upvotes of the post being viewed.
     */
    private lateinit var tvItemViewOwnPostUpvoteCounter: TextView

    /**
     * Number of comments of the post being viewed.
     */
    private lateinit var tvItemViewOwnPostComments: TextView

    /**
     * Date posted of the post being viewed.
     */
    private lateinit var tvItemViewOwnPostDatePosted: TextView

    /**
     * Medium of the artwork being viewed.
     */
    private lateinit var tvItemViewOwnPostMedium: TextView

    /**
     * Dimensions of the artwork being viewed.
     */
    private lateinit var tvItemViewOwnPostDimensions: TextView

    /**
     * Description of the post being viewed.
     */
    private lateinit var tvItemViewOwnPostDescription: TextView

    /**
     * Tags of the post being viewed.
     */
    private lateinit var tvItemViewOwnPostTags: TextView

    /**
     * Image view holding the highlight icon.
     */
    private lateinit var ivItemViewOwnPostHighlight: ImageView

    /**
     * Text view holding the "Highlight" label.
     */
    private lateinit var tvItemViewOwnPostHighlight: TextView

    /**
     * Constraint layout holding the highlight option.
     */
    private lateinit var clItemViewOwnPostHighlight: ConstraintLayout

    /**
     * Constraint layout holding the comment option.
     */
    private lateinit var clItemViewOwnPostComment: ConstraintLayout

    /**
     * Constraint layout holding the share option.
     */
    private lateinit var clItemViewOwnPostShare: ConstraintLayout

    /**
     * Bottom navigation view containing the menu items for Home, Followed, Bookmarks, and Profile.
     */
    private lateinit var bnvViewOwnPostBottom: BottomNavigationView

    /**
     * Image button for opening the own post options bottom dialog
     */
    private lateinit var ibItemViewOwnPostOptions: ImageButton

    /**
     * Bottom sheet dialog displayed when the user clicks the image button for viewing the own
     * post options.
     */
    private lateinit var btmViewOwnPost: BottomSheetDialog

    /**
     * Constraint layout holding the edit post option.
     */
    private lateinit var clDialogViewOwnPostEdit: ConstraintLayout

    /**
     * Constraint layout holding the delete post option.
     */
    private lateinit var clDialogViewOwnPostDelete: ConstraintLayout

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
    private lateinit var srlViewOwnPost: SwipeRefreshLayout

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
        setContentView(R.layout.activity_view_own_post)

        this.civItemViewOwnPostProfilePic = findViewById(R.id.civ_item_view_own_post_profile_pic)
        this.tvItemViewOwnPostUsername = findViewById(R.id.tv_item_view_own_post_username)
        this.ivItemViewOwnPostPostImg = findViewById(R.id.iv_item_view_own_post_post)
        this.tvItemViewOwnPostTitle = findViewById(R.id.tv_item_view_own_post_title)
        this.tvItemViewOwnPostUpvoteCounter = findViewById(R.id.tv_item_view_own_post_upvote_counter)
        this.tvItemViewOwnPostComments = findViewById(R.id.tv_item_view_own_post_comments)
        this.tvItemViewOwnPostDatePosted = findViewById(R.id.tv_item_view_own_post_date)
        this.tvItemViewOwnPostMedium = findViewById(R.id.tv_item_view_own_post_medium)
        this.tvItemViewOwnPostDimensions = findViewById(R.id.tv_item_view_own_post_dimen)
        this.tvItemViewOwnPostDescription = findViewById(R.id.tv_item_view_own_post_desc)
        this.tvItemViewOwnPostTags = findViewById(R.id.tv_item_view_own_post_tags)

        this.ivItemViewOwnPostHighlight = findViewById(R.id.iv_item_view_own_post_highlight)
        this.tvItemViewOwnPostHighlight = findViewById(R.id.tv_item_view_own_post_highlight)
        this.clItemViewOwnPostHighlight = findViewById(R.id.cl_item_view_own_post_highlight)
        this.clItemViewOwnPostComment = findViewById(R.id.cl_item_view_own_post_comment)
        this.clItemViewOwnPostShare = findViewById(R.id.cl_item_view_own_post_share)

        this.ibItemViewOwnPostOptions = findViewById(R.id.ib_item_view_own_post_options)
        this.btmViewOwnPost = BottomSheetDialog(this@ViewOwnPostActivity)

        initIntent()
        initComponents()
        initBottom()
        addPost()

        initGalleryLauncher(this@ViewOwnPostActivity)
        initCameraLauncher(this@ViewOwnPostActivity)
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
     * Initializes the components of the activity.
     */
    private fun initComponents() {
        setSupportActionBar(findViewById(R.id.toolbar_view_own_post))
        initActionBar()
        initSwipeRefresh()
    }

    /**
     * Initializes the swipe refresh layout and defines the behavior when the screen is swiped
     * to refresh.
     */
    private fun initSwipeRefresh() {
        this.srlViewOwnPost = findViewById(R.id.srl_view_own_post)
        srlViewOwnPost.setOnRefreshListener {
            onRefresh()
        }

        srlViewOwnPost.setColorSchemeResources(R.color.purple_main,
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
            srlViewOwnPost.isRefreshing = false
        }, AnimationDuration.REFRESH_TIMEOUT.toLong())
    }

    /**
     * Initializes the intent passed to the activity.
     */
    private fun initIntent() {
        val intent: Intent = intent

        val postId = intent.getStringExtra(Keys.KEY_POSTID.name)
        val userIdPost = intent.getStringExtra(Keys.KEY_USERID.name)

        this.firebaseHelper = FirebaseHelper(this@ViewOwnPostActivity, postId, userIdPost)

        val profilePicture = intent.getStringExtra(Keys.KEY_PROFILE_PICTURE.name)
        val username = intent.getStringExtra(Keys.KEY_USERNAME.name)

        val postImg = intent.getStringExtra(Keys.KEY_POST.name)

        val title = intent.getStringExtra(Keys.KEY_TITLE.name)
        val upvoteCounter = intent.getIntExtra(Keys.KEY_NUM_UPVOTES.name, 0)
        val comments = intent.getIntExtra(Keys.KEY_NUM_COMMENTS.name, 0)
        val datePosted = intent.getStringExtra(Keys.KEY_DATE_POSTED.name)
        val medium = intent.getStringExtra(Keys.KEY_MEDIUM.name)
        val dimensions = intent.getStringExtra(Keys.KEY_DIMENSIONS.name)
        val description = intent.getStringExtra(Keys.KEY_DESCRIPTION.name)
        val tags = intent.getStringArrayListExtra(Keys.KEY_TAGS.name)
        var highlight = intent.getBooleanExtra(Keys.KEY_HIGHLIGHT.name, false)

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

        //this.civItemViewOwnPostProfilePic.setImageURI(Uri.parse(profilePicture))

        Glide.with(this)
            .load(profilePicture)
            .diskCacheStrategy(DiskCacheStrategy.NONE)
            .placeholder(R.drawable.chibi_artemis_hd)
            .error(R.drawable.chibi_artemis_hd)
            .into(this.civItemViewOwnPostProfilePic)


        this.tvItemViewOwnPostUsername.text = username

        //this.ivItemViewOwnPostPostImg.setImageURI(Uri.parse(postImg))

        Glide.with(this)
            .load(postImg)
            .diskCacheStrategy(DiskCacheStrategy.NONE)
            .placeholder(R.drawable.placeholder)
            .error(R.drawable.placeholder)
            .into(this.ivItemViewOwnPostPostImg)


        if (!title.isNullOrEmpty()){
            this.tvItemViewOwnPostTitle.visibility = View.VISIBLE
            this.tvItemViewOwnPostTitle.text = title
        }
        else{
            this.tvItemViewOwnPostTitle.visibility = View.INVISIBLE
        }

        this.tvItemViewOwnPostUpvoteCounter.text = upvoteString
        this.tvItemViewOwnPostComments.text = commentString
        this.tvItemViewOwnPostDatePosted.text = datePosted

        if(!medium.isNullOrEmpty()){
            this.tvItemViewOwnPostMedium.visibility = View.VISIBLE
            this.tvItemViewOwnPostMedium.text = medium
        }
        else{
            this.tvItemViewOwnPostMedium.visibility = View.GONE
        }

        if(!dimensions.isNullOrEmpty()){
            this.tvItemViewOwnPostDimensions.visibility = View.VISIBLE
            this.tvItemViewOwnPostDimensions.text = dimensions
        }
        else{
            this.tvItemViewOwnPostDimensions.visibility = View.GONE
        }

        if(!description.isNullOrEmpty()){
            this.tvItemViewOwnPostDescription.visibility = View.VISIBLE
            this.tvItemViewOwnPostDescription.text = description
        }
        else{
            this.tvItemViewOwnPostDescription.visibility = View.GONE
        }

        this.tvItemViewOwnPostTags.text = tagsString

        updateHighlight(highlight)

        clItemViewOwnPostHighlight.setOnClickListener {
            if (highlight) {
                highlight = false
                updateHighlight(highlight)
                this.firebaseHelper.updateHighlightDB(postId!!, null)

            } else {
                highlight = true
                updateHighlight(highlight)
                Toast.makeText(this@ViewOwnPostActivity, "Added to your Highlights", Toast.LENGTH_SHORT).show()
                this.firebaseHelper.updateHighlightDB(postId!!, postId)
            }
        }

        clItemViewOwnPostComment.setOnClickListener {
            val intent = Intent(this, ViewOwnPostCommentsActivity::class.java)
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

        clItemViewOwnPostShare.setOnClickListener {
            shareOnFacebook()
        }

        civItemViewOwnPostProfilePic.setOnClickListener {
            val intent = Intent(this, ViewProfileActivity::class.java)
            startActivity(intent)
        }

        tvItemViewOwnPostUsername.setOnClickListener {
            val intent = Intent(this, ViewProfileActivity::class.java)
            startActivity(intent)
        }

        val editTitle: String = title.toString()
        val editMedium: String = medium.toString()
        val editDimensions: String = dimensions.toString()
        val editDescription: String = description.toString()
        val editPostImg: String = postImg.toString()
        val editTags: String = tagsString.toString()

        launchDialog(userIdPost.toString(), postId.toString(), editTitle, editMedium, editDimensions, editDescription, editPostImg, editTags)
    }

    /**
     * Launches the bottom sheet dialog for choosing the own post options.
     *
     * @param userIdPost Unique identifier of the user who posted the post.
     * @param postId Unique identifier of the post.
     * @param title Title of the post.
     * @param medium Medium of the artwork.
     * @param dimensions Dimensions of the artwork.
     * @param description Description of the post.
     * @param postImg The posted artwork.
     * @param tags Tags of the post.
     */
    private fun launchDialog(userIdPost: String, postId: String,
                             title: String, medium: String, dimensions: String, description: String,
                             postImg: String, tags: String) {
        val view = LayoutInflater.from(this@ViewOwnPostActivity).inflate(R.layout.dialog_own_post, null)

        this.ibItemViewOwnPostOptions.setOnClickListener {
            btmViewOwnPost.setContentView(view)

            this.clDialogViewOwnPostEdit = btmViewOwnPost.findViewById(R.id.cl_dialog_own_post_edit)!!
            this.clDialogViewOwnPostDelete = btmViewOwnPost.findViewById(R.id.cl_dialog_own_post_delete)!!

            clDialogViewOwnPostEdit.setOnClickListener {
                btmViewOwnPost.dismiss()
                val intent = Intent(this@ViewOwnPostActivity, EditPostActivity::class.java)

                intent.putExtra(
                    Keys.KEY_USERID.name,
                    userIdPost
                )
                intent.putExtra(
                    Keys.KEY_POSTID.name,
                    postId
                )
                intent.putExtra(
                    Keys.KEY_TITLE.name,
                    title
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
                    Keys.KEY_POST.name,
                    postImg
                )
                startActivity(intent)
                finish()
            }

            clDialogViewOwnPostDelete.setOnClickListener {
                firebaseHelper.deletePostDB(postId, false)
                val intent = Intent(this@ViewOwnPostActivity, BrowseOwnPostsActivity::class.java)
                startActivity(intent)
                finish()

                btmViewOwnPost.dismiss()
                //Toast.makeText(this@ViewOwnPostActivity, "Your post has been deleted", Toast.LENGTH_SHORT).show()

            }

            btmViewOwnPost.show()
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
     * Sets the listeners for the menu selection found in the bottom navigation view.
     */
    private fun initBottom() {
        this.bnvViewOwnPostBottom = findViewById(R.id.nv_view_own_post_bottom)

        BottomMenuUtil.setFinishBottomMenuListeners(bnvViewOwnPostBottom, this,
            this@ViewOwnPostActivity)
    }

    /**
     * Updates the highlight status of a post.
     *
     * @param highlight <code>true</code> if the user chooses to highlight the post; <code>false</code>
     * if the user chooses to remove the highlight status of the post
     */
    private fun updateHighlight(highlight: Boolean) {
        if(highlight) {
            this.ivItemViewOwnPostHighlight.setImageResource(R.drawable.baseline_star_24)
            this.ivItemViewOwnPostHighlight.imageTintList = ColorStateList.valueOf(
                ContextCompat.getColor(this.ivItemViewOwnPostHighlight.context, R.color.pinkish_purple)
            )
            this.tvItemViewOwnPostHighlight.setTextColor(ColorStateList.valueOf(
                ContextCompat.getColor(this.tvItemViewOwnPostHighlight.context, R.color.pinkish_purple))
            )
        } else {
            this.ivItemViewOwnPostHighlight.setImageResource(R.drawable.outline_star_border_24)
            this.ivItemViewOwnPostHighlight.imageTintList = ColorStateList.valueOf(
                ContextCompat.getColor(this.ivItemViewOwnPostHighlight.context, R.color.default_gray)
            )
            this.tvItemViewOwnPostHighlight.setTextColor(ColorStateList.valueOf(
                ContextCompat.getColor(this.tvItemViewOwnPostHighlight.context, R.color.default_gray))
            )
        }
    }

    /**
     * Sets the listeners in relation to adding an artwork (that is, by either choosing an image
     * from the gallery or taking a photo using the device camera) to be posted on Artemis.
     */
    private fun addPost() {
        this.btmAddPost = BottomSheetDialog(this@ViewOwnPostActivity)
        this.fabAddPost = findViewById(R.id.fab_view_own_post_add)

        val view = LayoutInflater.from(this@ViewOwnPostActivity).inflate(R.layout.dialog_post_artwork, null)

        this.fabAddPost.setOnClickListener {
            btmAddPost.setContentView(view)

            this.clDialogPostArtworkGallery = btmAddPost.findViewById(R.id.cl_dialog_post_artwork_gallery)!!
            this.clDialogPostArtworkPhoto = btmAddPost.findViewById(R.id.cl_dialog_post_artwork_photo)!!

            clDialogPostArtworkGallery.setOnClickListener {
                PostArtworkUtil.chooseFromGallery(this, galleryLauncher)
            }

            clDialogPostArtworkPhoto.setOnClickListener {
                photoFile = PostArtworkUtil.takeFromCamera(this, this@ViewOwnPostActivity, cameraLauncher)
            }

            btmAddPost.show()
        }
    }

    /**
     * Shares the posted artwork on the user's Facebook account.
     */
    private fun shareOnFacebook() {
        cmFacebook = CallbackManager.Factory.create()
        sdFacebook = ShareDialog(this@ViewOwnPostActivity)

        sdFacebook.registerCallback(cmFacebook, object : FacebookCallback<Sharer.Result?> {
            override fun onSuccess(result: Sharer.Result?) {
                Toast.makeText(this@ViewOwnPostActivity, "Shared on Facebook", Toast.LENGTH_SHORT).show()
            }

            override fun onCancel() {
                Toast.makeText(this@ViewOwnPostActivity, "Sharing cancelled", Toast.LENGTH_SHORT).show()
            }

            override fun onError(error: FacebookException) {
                Toast.makeText(this@ViewOwnPostActivity, "Sharing error occurred", Toast.LENGTH_SHORT).show()
            }
        })

        if (ShareDialog.canShow(SharePhotoContent::class.java)) {
            val bitmapDrawable = ivItemViewOwnPostPostImg.drawable as BitmapDrawable
            val bitmap = bitmapDrawable.bitmap
            val username = "@" + tvItemViewOwnPostUsername.text.toString()
            val captionedImage = CaptionPlacer.placeCaption(bitmap, username, "Posted on Artemis")
            val sharePhoto = SharePhoto.Builder()
                .setBitmap(captionedImage)
                .build()
            val sharePhotoContent = SharePhotoContent.Builder()
                .addPhoto(sharePhoto)
                .build()
            sdFacebook.show(sharePhotoContent)

            Toast.makeText(this@ViewOwnPostActivity, "Opening Facebook", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(this@ViewOwnPostActivity, "Unable to share post", Toast.LENGTH_SHORT).show()
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
        permissionsResult(requestCode, grantResults, this@ViewOwnPostActivity, this)
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