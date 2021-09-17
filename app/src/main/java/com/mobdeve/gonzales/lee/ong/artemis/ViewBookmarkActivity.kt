package com.mobdeve.gonzales.lee.ong.artemis

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.res.ColorStateList
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.*
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.core.widget.NestedScrollView
import com.bumptech.glide.Glide
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.floatingactionbutton.FloatingActionButton
import de.hdodenhof.circleimageview.CircleImageView
import java.io.File

/**
 * Class handling the functionalities related to viewing the posts bookmarked by the user.
 *
 * @constructor Creates a class that handles the functionalities related to viewing the posts
 * bookmarked by the user.
 */
class ViewBookmarkActivity : AppCompatActivity() {
    /**
     * Image view for the profile picture of the user whose bookmarks are displayed.
     */
    private lateinit var civItemViewBookmarkProfilePic: CircleImageView

    /**
     * Text view for the username of the user whose bookmarks are displayed.
     */
    private lateinit var tvItemViewBookmarkUsername: TextView

    /**
     * Image view for the artwork bookmarked by the user.
     */
    private lateinit var ivItemViewBookmarkPost: ImageView

    /**
     * Text view for the title of the artwork bookmarked by the user.
     */
    private lateinit var tvItemViewBookmarkTitle: TextView

    /**
     * Text view for the date when the artwork bookmarked by the user was posted.
     */
    private lateinit var tvItemViewBookmarkDatePosted: TextView

    /**
     * Text view for the medium of the artwork bookmarked by the user.
     */
    private lateinit var tvItemViewBookmarkMedium: TextView

    /**
     * Text view for the dimensions of the artwork bookmarked by the user.
     */
    private lateinit var tvItemViewBookmarkDimensions: TextView

    /**
     * Text view for the description of the artwork bookmarked by the user.
     */
    private lateinit var tvItemViewBookmarkDescription: TextView

    /**
     * Button for bookmarking an artwork.
     */
    private lateinit var ibItemViewBookmarkBookmark: ImageButton

    /**
     * Bottom navigation view containing the menu items for Home, Followed, Bookmarks, and Profile.
     */
    private lateinit var bnvViewBookmarkBottom: BottomNavigationView

    /**
     * Nested scroll view for the main layout of this activity.
     */
    private lateinit var nsvViewBookmark: NestedScrollView

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
     * Object instantiating the class containing helper methods for Firebase CRUD operations.
     */
    private lateinit var firebaseHelper: FirebaseHelper

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
        setContentView(R.layout.activity_view_bookmark)

        civItemViewBookmarkProfilePic = findViewById(R.id.civ_item_view_bookmark_profile_pic)
        tvItemViewBookmarkUsername = findViewById(R.id.tv_item_view_bookmark_username)
        ivItemViewBookmarkPost = findViewById(R.id.iv_item_view_bookmark_post)
        tvItemViewBookmarkTitle = findViewById(R.id.tv_item_view_bookmark_title)
        tvItemViewBookmarkDatePosted = findViewById(R.id.tv_item_view_bookmark_date)
        tvItemViewBookmarkMedium = findViewById(R.id.tv_item_view_bookmark_medium)
        tvItemViewBookmarkDimensions = findViewById(R.id.tv_item_view_bookmark_dimen)
        tvItemViewBookmarkDescription = findViewById(R.id.tv_item_view_bookmark_desc)
        ibItemViewBookmarkBookmark = findViewById(R.id.ib_item_view_bookmark_bookmark)

        initIntent()
        initComponents()
        initGalleryLauncher(this@ViewBookmarkActivity)
        initCameraLauncher(this@ViewBookmarkActivity)
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
     * Retrieves the data passed via intents, initializes the remote database helpers
     * required for viewing the post, and sets the listeners for the clickable views
     * that redirect to the profile of the user who created the post.
     */
    private fun initIntent() {
        val intent: Intent = intent

        val postId = intent.getStringExtra(Keys.KEY_POSTID.name)
        val userIdPost = intent.getStringExtra(Keys.KEY_USERID.name)

        this.firebaseHelper = FirebaseHelper(this@ViewBookmarkActivity, postId, userIdPost)

        val profilePicture = intent.getStringExtra(Keys.KEY_PROFILE_PICTURE.name)
        val username = intent.getStringExtra(Keys.KEY_USERNAME.name)

        val postImg = intent.getStringExtra(Keys.KEY_POST.name)

        val title = intent.getStringExtra(Keys.KEY_TITLE.name)
        val datePosted = intent.getStringExtra(Keys.KEY_DATE_POSTED.name)
        val type = intent.getStringExtra(Keys.KEY_MEDIUM.name)
        val dimensions = intent.getStringExtra(Keys.KEY_DIMENSIONS.name)
        val description = intent.getStringExtra(Keys.KEY_DESCRIPTION.name)
        var bookmark = intent.getBooleanExtra(Keys.KEY_BOOKMARK.name, false)

        if (!(this@ViewBookmarkActivity as Activity).isFinishing) {
            Glide.with(this@ViewBookmarkActivity)
                .load(profilePicture)
                .placeholder(R.drawable.chibi_artemis_hd)
                .error(R.drawable.chibi_artemis_hd)
                .into(civItemViewBookmarkProfilePic)
        }

        this.tvItemViewBookmarkUsername.text = username

        if (!(this@ViewBookmarkActivity as Activity).isFinishing) {
            Glide.with(this@ViewBookmarkActivity)
                .load(postImg)
                .placeholder(R.drawable.placeholder)
                .error(R.drawable.placeholder)
                .into(ivItemViewBookmarkPost)
        }

        this.tvItemViewBookmarkTitle.text = title
        this.tvItemViewBookmarkDatePosted.text = datePosted
        this.tvItemViewBookmarkMedium.text = type
        this.tvItemViewBookmarkDimensions.text = dimensions
        this.tvItemViewBookmarkDescription.text = description

        updateBookmark(bookmark)

        ibItemViewBookmarkBookmark.setOnClickListener {
            bookmark = !bookmark
            updateBookmark(bookmark)

            if(bookmark){
                firebaseHelper.updateBookmarkDB("1", postId!!, "1")
            }

            else{
                firebaseHelper.updateBookmarkDB(null, postId!!, null)
            }
        }

        civItemViewBookmarkProfilePic.setOnClickListener {
            val intent = Intent(this, ViewUserActivity::class.java)

            intent.putExtra(
                Keys.KEY_USERID.name,
                userIdPost
            )

            startActivity(intent)
        }

        tvItemViewBookmarkUsername.setOnClickListener {
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
        setSupportActionBar(findViewById(R.id.toolbar_view_bookmark))
        initActionBar()
        initBottom()
        addPost()
    }

    /**
     * Sets the listeners for the menu selection found in the bottom navigation view.
     */
    private fun initBottom() {
        this.bnvViewBookmarkBottom = findViewById(R.id.nv_view_bookmark_bottom)
        this.nsvViewBookmark = findViewById(R.id.nsv_view_bookmark)

        BottomMenuUtil.setFinishBottomMenuListeners(bnvViewBookmarkBottom, this,
            this@ViewBookmarkActivity)
    }

    /**
     * Adds a back button to the action bar.
     */
    private fun initActionBar() {
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
    }

    /**
     * Updates the view depending on whether the post is bookmarked.
     *
     * @param bookmark <code>true</code> if the post is bookmarked; <code>false</code>, otherwise.
     */
    private fun updateBookmark(bookmark: Boolean) {
        if(bookmark) {
            this.ibItemViewBookmarkBookmark.setImageResource(R.drawable.outline_bookmark_24)
            this.ibItemViewBookmarkBookmark.imageTintList = ColorStateList.valueOf(
                ContextCompat.getColor(this.ibItemViewBookmarkBookmark.context, R.color.pinkish_purple)
            )
        } else {
            this.ibItemViewBookmarkBookmark.setImageResource(R.drawable.outline_bookmark_border_24)
            this.ibItemViewBookmarkBookmark.imageTintList = ColorStateList.valueOf(
                ContextCompat.getColor(this.ibItemViewBookmarkBookmark.context, R.color.default_gray)
            )
        }
    }

    /**
     * Sets the listeners in relation to adding an artwork (that is, by either choosing an image
     * from the gallery or taking a photo using the device camera) to be posted on Artemis.
     */
    private fun addPost() {
        this.btmAddPost = BottomSheetDialog(this@ViewBookmarkActivity)
        this.fabAddPost = findViewById(R.id.fab_view_bookmark_add)

        val view = LayoutInflater.from(this@ViewBookmarkActivity).inflate(R.layout.dialog_post_artwork, null)

        this.fabAddPost.setOnClickListener {
            btmAddPost.setContentView(view)

            this.clDialogPostArtworkGallery = btmAddPost.findViewById(R.id.cl_dialog_post_artwork_gallery)!!
            this.clDialogPostArtworkPhoto = btmAddPost.findViewById(R.id.cl_dialog_post_artwork_photo)!!

            clDialogPostArtworkGallery.setOnClickListener {
                PostArtworkUtil.chooseFromGallery(this, galleryLauncher)
            }

            clDialogPostArtworkPhoto.setOnClickListener {
                photoFile = PostArtworkUtil.takeFromCamera(this, this@ViewBookmarkActivity, cameraLauncher)
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
        permissionsResult(requestCode, grantResults, this@ViewBookmarkActivity, this)
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