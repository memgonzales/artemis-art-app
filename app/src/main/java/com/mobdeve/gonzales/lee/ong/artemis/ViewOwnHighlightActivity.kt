package com.mobdeve.gonzales.lee.ong.artemis

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.res.ColorStateList
import android.os.Bundle
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
import com.bumptech.glide.Glide
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.floatingactionbutton.FloatingActionButton
import de.hdodenhof.circleimageview.CircleImageView
import java.io.File

/**
 * Class handling the functionalities related to viewing a user's own highlight.
 *
 * @constructor Creates a class that handles the functionalities related to viewing a user's own
 * highlight.
 */
class ViewOwnHighlightActivity : AppCompatActivity() {
    /**
     * Profile picture of the user whose highlight is being viewed.
     */
    private lateinit var civItemViewOwnHighlightProfilePic: CircleImageView

    /**
     * Username of the user whose highlight is being viewed.
     */
    private lateinit var tvItemViewOwnHighlightUsername: TextView

    /**
     * Artwork of the highlight being viewed.
     */
    private lateinit var ivItemViewOwnHighlightPost: ImageView

    /**
     * Title of the highlight being viewed.
     */
    private lateinit var tvItemViewOwnHighlightTitle: TextView

    /**
     * Date posted of the highlight being viewed.
     */
    private lateinit var tvItemViewOwnHighlightDatePosted: TextView

    /**
     * Medium of the highlight being viewed.
     */
    private lateinit var tvItemViewOwnHighlightMedium: TextView

    /**
     * Dimensions of the highlight being viewed.
     */
    private lateinit var tvItemViewOwnHighlightDimensions: TextView

    /**
     * Description of the highlight being viewed.
     */
    private lateinit var tvItemViewOwnHighlightDescription: TextView

    /**
     * Image button for toggling the highlight status of the highlight being viewed.
     */
    private lateinit var ibItemViewOwnHighlightHighlight: ImageButton

    /**
     * Bottom navigation view containing the menu items for Home, Followed, Bookmarks, and Profile.
     */
    private lateinit var bnvViewOwnHighlightBottom: BottomNavigationView

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
     * Object for accessing the Firebase helper methods.
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
        setContentView(R.layout.activity_view_own_highlight)

        civItemViewOwnHighlightProfilePic = findViewById(R.id.civ_item_view_own_highlight_profile_pic)
        tvItemViewOwnHighlightUsername = findViewById(R.id.tv_item_view_own_highlight_username)
        ivItemViewOwnHighlightPost = findViewById(R.id.iv_item_view_own_highlight_post)
        tvItemViewOwnHighlightTitle = findViewById(R.id.tv_item_view_own_highlight_title)
        tvItemViewOwnHighlightDatePosted = findViewById(R.id.tv_item_view_own_highlight_date)
        tvItemViewOwnHighlightMedium = findViewById(R.id.tv_item_view_own_highlight_medium)
        tvItemViewOwnHighlightDimensions = findViewById(R.id.tv_item_view_own_highlight_dimen)
        tvItemViewOwnHighlightDescription = findViewById(R.id.tv_item_view_own_highlight_desc)
        ibItemViewOwnHighlightHighlight = findViewById(R.id.ib_item_view_own_highlight_highlight)

        initIntent()
        initComponents()
        initBottom()
        addPost()

        initGalleryLauncher(this@ViewOwnHighlightActivity)
        initCameraLauncher(this@ViewOwnHighlightActivity)
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

        this.firebaseHelper = FirebaseHelper(this@ViewOwnHighlightActivity, postId, userIdPost)

        val profilePicture = intent.getStringExtra(Keys.KEY_PROFILE_PICTURE.name)

        val username = intent.getStringExtra(Keys.KEY_USERNAME.name)

        val postImg = intent.getStringExtra(Keys.KEY_POST.name)
        val title = intent.getStringExtra(Keys.KEY_TITLE.name)
        val datePosted = intent.getStringExtra(Keys.KEY_DATE_POSTED.name)
        val medium = intent.getStringExtra(Keys.KEY_MEDIUM.name)
        val dimensions = intent.getStringExtra(Keys.KEY_DIMENSIONS.name)
        val description = intent.getStringExtra(Keys.KEY_DESCRIPTION.name)
        var highlight = intent.getBooleanExtra(Keys.KEY_HIGHLIGHT.name, false)

        if (!(this@ViewOwnHighlightActivity as Activity).isFinishing) {
            Glide.with(this@ViewOwnHighlightActivity)
                .load(profilePicture)
                .placeholder(R.drawable.chibi_artemis_hd)
                .error(R.drawable.chibi_artemis_hd)
                .into(this.civItemViewOwnHighlightProfilePic)
        }

        this.tvItemViewOwnHighlightUsername.text = username

        if (!(this@ViewOwnHighlightActivity as Activity).isFinishing) {
            Glide.with(this@ViewOwnHighlightActivity)
                .load(postImg)
                .placeholder(R.drawable.placeholder)
                .error(R.drawable.placeholder)
                .into(this.ivItemViewOwnHighlightPost)
        }

        if (!title.isNullOrEmpty()) {
            this.tvItemViewOwnHighlightTitle.visibility = View.VISIBLE
            this.tvItemViewOwnHighlightTitle.text = title
        } else {
            this.tvItemViewOwnHighlightTitle.visibility = View.INVISIBLE
        }

        this.tvItemViewOwnHighlightDatePosted.text = datePosted

        if(!medium.isNullOrEmpty()) {
            this.tvItemViewOwnHighlightMedium.visibility = View.VISIBLE
            this.tvItemViewOwnHighlightMedium.text = medium
        } else {
            this.tvItemViewOwnHighlightMedium.visibility = View.GONE
        }

        if(!dimensions.isNullOrEmpty()) {
            this.tvItemViewOwnHighlightDimensions.visibility = View.VISIBLE
            this.tvItemViewOwnHighlightDimensions.text = dimensions
        } else {
            this.tvItemViewOwnHighlightDimensions.visibility = View.GONE
        }

        if(!description.isNullOrEmpty()) {
            this.tvItemViewOwnHighlightDescription.visibility = View.VISIBLE
            this.tvItemViewOwnHighlightDescription.text = description
        } else {
            this.tvItemViewOwnHighlightDescription.visibility = View.GONE
        }

        updateHighlight(highlight)

        ibItemViewOwnHighlightHighlight.setOnClickListener {
            if (highlight) {
                highlight = false
                updateHighlight(highlight)

                this.firebaseHelper.updateHighlightDB(postId!!, null)

            } else {
                highlight = true
                updateHighlight(highlight)
                Toast.makeText(this@ViewOwnHighlightActivity, "Added to your Highlights", Toast.LENGTH_SHORT).show()

                this.firebaseHelper.updateHighlightDB(postId!!, "1")
            }
        }

        civItemViewOwnHighlightProfilePic.setOnClickListener {
            val intent = Intent(this, ViewProfileActivity::class.java)
            startActivity(intent)
        }

        tvItemViewOwnHighlightUsername.setOnClickListener {
            val intent = Intent(this, ViewProfileActivity::class.java)
            startActivity(intent)
        }
    }

    /**
     * Initializes the components of the activity.
     */
    private fun initComponents() {
        setSupportActionBar(findViewById(R.id.toolbar_view_own_highlight))
        initActionBar()
    }

    /**
     * Sets the listeners for the menu selection found in the bottom navigation view.
     */
    private fun initBottom() {
        this.bnvViewOwnHighlightBottom = findViewById(R.id.nv_view_own_highlight_bottom)

        BottomMenuUtil.setFinishBottomMenuListeners(bnvViewOwnHighlightBottom, this,
            this@ViewOwnHighlightActivity)
    }

    /**
     * Adds a back button to the action bar.
     */
    private fun initActionBar() {
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
    }

    /**
     * Updates the highlight status of the highlight being viewed.
     *
     * @param highlight <code>true</code> if the user chooses to highlight the activity,
     * <code>false</code> if the user chooses to remove the highlight status of the activity
     */
    private fun updateHighlight(highlight: Boolean) {
        if(highlight) {
            this.ibItemViewOwnHighlightHighlight.setImageResource(R.drawable.baseline_star_24)
            this.ibItemViewOwnHighlightHighlight.imageTintList = ColorStateList.valueOf(
                ContextCompat.getColor(this.ibItemViewOwnHighlightHighlight.context, R.color.pinkish_purple)
            )
        } else {
            this.ibItemViewOwnHighlightHighlight.setImageResource(R.drawable.outline_star_border_24)
            this.ibItemViewOwnHighlightHighlight.imageTintList = ColorStateList.valueOf(
                ContextCompat.getColor(this.ibItemViewOwnHighlightHighlight.context, R.color.default_gray)
            )
        }
    }

    /**
     * Sets the listeners in relation to adding an artwork (that is, by either choosing an image
     * from the gallery or taking a photo using the device camera) to be posted on Artemis.
     */
    private fun addPost() {
        this.btmAddPost = BottomSheetDialog(this@ViewOwnHighlightActivity)
        this.fabAddPost = findViewById(R.id.fab_view_own_highlight_add)

        val view = LayoutInflater.from(this@ViewOwnHighlightActivity).inflate(R.layout.dialog_post_artwork, null)

        this.fabAddPost.setOnClickListener {
            btmAddPost.setContentView(view)

            this.clDialogPostArtworkGallery = btmAddPost.findViewById(R.id.cl_dialog_post_artwork_gallery)!!
            this.clDialogPostArtworkPhoto = btmAddPost.findViewById(R.id.cl_dialog_post_artwork_photo)!!

            clDialogPostArtworkGallery.setOnClickListener {
                PostArtworkUtil.chooseFromGallery(this, galleryLauncher)
            }

            clDialogPostArtworkPhoto.setOnClickListener {
                photoFile = PostArtworkUtil.takeFromCamera(this, this@ViewOwnHighlightActivity, cameraLauncher)
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
        permissionsResult(requestCode, grantResults, this@ViewOwnHighlightActivity, this)
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