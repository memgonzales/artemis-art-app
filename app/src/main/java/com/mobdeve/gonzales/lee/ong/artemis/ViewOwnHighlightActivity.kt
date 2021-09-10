package com.mobdeve.gonzales.lee.ong.artemis

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.res.ColorStateList
import android.os.Bundle
import android.view.LayoutInflater
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
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import de.hdodenhof.circleimageview.CircleImageView
import java.io.File

class ViewOwnHighlightActivity : AppCompatActivity() {
    private lateinit var civItemViewOwnHighlightProfilePic: CircleImageView
    private lateinit var tvItemViewOwnHighlightUsername: TextView
    private lateinit var ivItemViewOwnHighlightPost: ImageView
    private lateinit var tvItemViewOwnHighlightTitle: TextView
    private lateinit var tvItemViewOwnHighlightDatePosted: TextView
    private lateinit var tvItemViewOwnHighlightMedium: TextView
    private lateinit var tvItemViewOwnHighlightDimensions: TextView
    private lateinit var tvItemViewOwnHighlightDescription: TextView
    private lateinit var ibItemViewOwnHighlightHighlight: ImageButton
    private lateinit var bnvViewOwnHighlightBottom: BottomNavigationView

    private lateinit var btmAddPost: BottomSheetDialog
    private lateinit var fabAddPost: FloatingActionButton
    private lateinit var clDialogPostArtworkGallery: ConstraintLayout
    private lateinit var clDialogPostArtworkPhoto: ConstraintLayout

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
     * Activity result launcher related to choosing photos from the Gallery
     */
    private lateinit var galleryLauncher: ActivityResultLauncher<Intent>

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

        this.firebaseHelper = FirebaseHelper(this@ViewOwnHighlightActivity, postId, userIdPost)

        val profilePicture = intent.getStringExtra(Keys.KEY_PROFILE_PICTURE.name)

        val username = intent.getStringExtra(Keys.KEY_USERNAME.name)

        val postImg = intent.getStringExtra(Keys.KEY_POST.name)

        val title = intent.getStringExtra(Keys.KEY_TITLE.name)
        val datePosted = intent.getStringExtra(Keys.KEY_DATE_POSTED.name)
        val type = intent.getStringExtra(Keys.KEY_MEDIUM.name)
        val dimensions = intent.getStringExtra(Keys.KEY_DIMENSIONS.name)
        val description = intent.getStringExtra(Keys.KEY_DESCRIPTION.name)
        var highlight = intent.getBooleanExtra(Keys.KEY_HIGHLIGHT.name, false)

        Glide.with(this@ViewOwnHighlightActivity)
            .load(profilePicture)
            .error(R.drawable.chibi_artemis_hd)
            .into(civItemViewOwnHighlightProfilePic)

        this.tvItemViewOwnHighlightUsername.text = username

        Glide.with(this@ViewOwnHighlightActivity)
            .load(postImg)
            .error(R.drawable.placeholder)
            .into(ivItemViewOwnHighlightPost)

        this.tvItemViewOwnHighlightTitle.text = title
        this.tvItemViewOwnHighlightDatePosted.text = datePosted
        this.tvItemViewOwnHighlightMedium.text = type
        this.tvItemViewOwnHighlightDimensions.text = dimensions
        this.tvItemViewOwnHighlightDescription.text = description

        updateHighlight(highlight)

        ibItemViewOwnHighlightHighlight.setOnClickListener {
            if (highlight) {
                highlight = false
                updateHighlight(highlight)

                firebaseHelper.updateHighlightDB(postId!!, null)

            } else {
                highlight = true
                updateHighlight(highlight)
                Toast.makeText(this@ViewOwnHighlightActivity, "Added to your Highlights", Toast.LENGTH_SHORT).show()

                firebaseHelper.updateHighlightDB(postId!!, "1")
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

    private fun initComponents() {
        setSupportActionBar(findViewById(R.id.toolbar_view_own_highlight))
        initActionBar()
    }

    private fun initBottom() {
        this.bnvViewOwnHighlightBottom = findViewById(R.id.nv_view_own_highlight_bottom)

        BottomMenuUtil.setFinishBottomMenuListeners(bnvViewOwnHighlightBottom, this,
            this@ViewOwnHighlightActivity)
    }

    private fun initActionBar() {
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
    }

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
     *     ActivityCompat.requestPermissions(android.app.Activity, String[], int)</code>
     * @param permissions The requested permissions. Never null
     * @param grantResults The grant results for the corresponding permissions which is either <code>
     *     PackageManager.PERMISSION_GRANTED</code> or <code>PackageManager.PERMISSION_DENIED</code>.
     *     Never null
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