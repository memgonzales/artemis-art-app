package com.mobdeve.gonzales.lee.ong.artemis

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import com.bumptech.glide.Glide
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.floatingactionbutton.FloatingActionButton
import de.hdodenhof.circleimageview.CircleImageView
import java.io.File

class ViewOthersHighlightActivity : AppCompatActivity() {
    private lateinit var civItemViewOthersHighlightProfilePic: CircleImageView
    private lateinit var tvItemViewOthersHighlightUsername: TextView
    private lateinit var ivItemViewOthersHighlightPost: ImageView
    private lateinit var tvItemViewOthersHighlightTitle: TextView
    private lateinit var tvItemViewOthersHighlightDatePosted: TextView
    private lateinit var tvItemViewOthersHighlightMedium: TextView
    private lateinit var tvItemViewOthersHighlightDimensions: TextView
    private lateinit var tvItemViewOthersHighlightDescription: TextView
    private lateinit var bnvViewOthersHighlightBottom: BottomNavigationView

    private lateinit var btmAddPost: BottomSheetDialog
    private lateinit var fabAddPost: FloatingActionButton
    private lateinit var clDialogPostArtworkGallery: ConstraintLayout
    private lateinit var clDialogPostArtworkPhoto: ConstraintLayout

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
        setContentView(R.layout.activity_view_others_highlight)

        civItemViewOthersHighlightProfilePic = findViewById(R.id.civ_item_view_others_highlight_profile_pic)
        tvItemViewOthersHighlightUsername = findViewById(R.id.tv_item_view_others_highlight_username)
        ivItemViewOthersHighlightPost = findViewById(R.id.iv_item_view_others_highlight_post)
        tvItemViewOthersHighlightTitle = findViewById(R.id.tv_item_view_others_highlight_title)
        tvItemViewOthersHighlightDatePosted = findViewById(R.id.tv_item_view_others_highlight_date)
        tvItemViewOthersHighlightMedium = findViewById(R.id.tv_item_view_others_highlight_medium)
        tvItemViewOthersHighlightDimensions = findViewById(R.id.tv_item_view_others_highlight_dimen)
        tvItemViewOthersHighlightDescription = findViewById(R.id.tv_item_view_others_highlight_desc)

        initIntent()
        initComponents()
        initBottom()
        addPost()
        initGalleryLauncher(this@ViewOthersHighlightActivity)
        initCameraLauncher(this@ViewOthersHighlightActivity)
    }

    /**
     * Initializes the activity result launcher related to choosing photos from the Gallery.
     *
     * @param packageContext context tied to this activity
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
     * @param packageContext context tied to this activity
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

        val userIdPost = intent.getStringExtra(Keys.KEY_USERID.name)

        this.firebaseHelper = FirebaseHelper(this@ViewOthersHighlightActivity, userIdPost)

        val profilePicture = intent.getStringExtra(Keys.KEY_PROFILE_PICTURE.name)
        val username = intent.getStringExtra(Keys.KEY_USERNAME.name)

        val postImg = intent.getStringExtra(Keys.KEY_POST.name)
        val title = intent.getStringExtra(Keys.KEY_TITLE.name)
        val datePosted = intent.getStringExtra(Keys.KEY_DATE_POSTED.name)
        val medium = intent.getStringExtra(Keys.KEY_MEDIUM.name)
        val dimensions = intent.getStringExtra(Keys.KEY_DIMENSIONS.name)
        val description = intent.getStringExtra(Keys.KEY_DESCRIPTION.name)


        //this.civItemViewOthersHighlightProfilePic.setImageResource(profilePicture)

        Glide.with(this@ViewOthersHighlightActivity)
            .load(profilePicture)
            .placeholder(R.drawable.chibi_artemis_hd)
            .error(R.drawable.chibi_artemis_hd)
            .into(this.civItemViewOthersHighlightProfilePic)

        this.tvItemViewOthersHighlightUsername.text = username
        //this.ivItemViewOthersHighlightPost.setImageResource(post)
        Glide.with(this@ViewOthersHighlightActivity)
            .load(postImg)
            .placeholder(R.drawable.placeholder)
            .error(R.drawable.placeholder)
            .into(this.ivItemViewOthersHighlightPost)

        if (!title.isNullOrEmpty()){
            this.tvItemViewOthersHighlightTitle.visibility = View.VISIBLE
            this.tvItemViewOthersHighlightTitle.text = title
        }
        else{
            this.tvItemViewOthersHighlightTitle.visibility = View.INVISIBLE
        }

        this.tvItemViewOthersHighlightDatePosted.text = datePosted

        if(!medium.isNullOrEmpty()){
            this.tvItemViewOthersHighlightMedium.visibility = View.VISIBLE
            this.tvItemViewOthersHighlightMedium.text = medium
        }
        else{
            this.tvItemViewOthersHighlightMedium.visibility = View.GONE
        }

        if(!dimensions.isNullOrEmpty()){
            this.tvItemViewOthersHighlightDimensions.visibility = View.VISIBLE
            this.tvItemViewOthersHighlightDimensions.text = dimensions
        }
        else{
            this.tvItemViewOthersHighlightDimensions.visibility = View.GONE
        }

        if(!description.isNullOrEmpty()){
            this.tvItemViewOthersHighlightDescription.visibility = View.VISIBLE
            this.tvItemViewOthersHighlightDescription.text = description
        }
        else{
            this.tvItemViewOthersHighlightDescription.visibility = View.GONE
        }

        civItemViewOthersHighlightProfilePic.setOnClickListener {
            val intent = Intent(this, ViewUserActivity::class.java)

            intent.putExtra(
                Keys.KEY_USERID.name,
                userIdPost
            )

            startActivity(intent)
        }

        tvItemViewOthersHighlightUsername.setOnClickListener {
            val intent = Intent(this, ViewUserActivity::class.java)

            intent.putExtra(
                Keys.KEY_USERID.name,
                userIdPost
            )

            startActivity(intent)
        }
    }

    private fun initComponents() {
        setSupportActionBar(findViewById(R.id.toolbar_view_others_highlight))
        initActionBar()
    }

    private fun initBottom() {
        this.bnvViewOthersHighlightBottom = findViewById(R.id.nv_view_others_highlight_bottom)

        BottomMenuUtil.setFinishBottomMenuListeners(bnvViewOthersHighlightBottom, this,
            this@ViewOthersHighlightActivity)
    }

    private fun initActionBar() {
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
    }

    private fun addPost() {
        this.btmAddPost = BottomSheetDialog(this@ViewOthersHighlightActivity)
        this.fabAddPost = findViewById(R.id.fab_view_others_highlight_add)

        val view = LayoutInflater.from(this@ViewOthersHighlightActivity).inflate(R.layout.dialog_post_artwork, null)

        this.fabAddPost.setOnClickListener {
            btmAddPost.setContentView(view)

            this.clDialogPostArtworkGallery = btmAddPost.findViewById(R.id.cl_dialog_post_artwork_gallery)!!
            this.clDialogPostArtworkPhoto = btmAddPost.findViewById(R.id.cl_dialog_post_artwork_photo)!!

            clDialogPostArtworkGallery.setOnClickListener {
                PostArtworkUtil.chooseFromGallery(this, galleryLauncher)
            }

            clDialogPostArtworkPhoto.setOnClickListener {
                photoFile = PostArtworkUtil.takeFromCamera(this, this@ViewOthersHighlightActivity, cameraLauncher)
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
        permissionsResult(requestCode, grantResults, this@ViewOthersHighlightActivity, this)
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