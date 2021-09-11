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
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import de.hdodenhof.circleimageview.CircleImageView
import java.io.File

class ViewBookmarkActivity : AppCompatActivity() {
    private lateinit var civItemViewBookmarkProfilePic: CircleImageView
    private lateinit var tvItemViewBookmarkUsername: TextView
    private lateinit var ivItemViewBookmarkPost: ImageView
    private lateinit var tvItemViewBookmarkTitle: TextView
    private lateinit var tvItemViewBookmarkDatePosted: TextView
    private lateinit var tvItemViewBookmarkMedium: TextView
    private lateinit var tvItemViewBookmarkDimensions: TextView
    private lateinit var tvItemViewBookmarkDescription: TextView
    private lateinit var ibItemViewBookmarkBookmark: ImageButton
    private lateinit var bnvViewBookmarkBottom: BottomNavigationView
    private lateinit var nsvViewBookmark: NestedScrollView

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

        Glide.with(this@ViewBookmarkActivity)
            .load(profilePicture)
            .placeholder(R.drawable.chibi_artemis_hd)
            .error(R.drawable.chibi_artemis_hd)
            .into(civItemViewBookmarkProfilePic)

        this.tvItemViewBookmarkUsername.text = username

        Glide.with(this@ViewBookmarkActivity)
            .load(postImg)
            .placeholder(R.drawable.placeholder)
            .error(R.drawable.placeholder)
            .into(ivItemViewBookmarkPost)

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

    private fun initComponents() {
        setSupportActionBar(findViewById(R.id.toolbar_view_bookmark))
        initActionBar()
        initBottom()
        addPost()
    }

    private fun initBottom() {
        this.bnvViewBookmarkBottom = findViewById(R.id.nv_view_bookmark_bottom)
        this.nsvViewBookmark = findViewById(R.id.nsv_view_bookmark)

        BottomMenuUtil.setFinishBottomMenuListeners(bnvViewBookmarkBottom, this,
            this@ViewBookmarkActivity)
    }

    private fun initActionBar() {
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
    }

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
     *     ActivityCompat.requestPermissions(android.app.Activity, String[], int)</code>
     * @param permissions The requested permissions. Never null
     * @param grantResults The grant results for the corresponding permissions which is either <code>
     *     PackageManager.PERMISSION_GRANTED</code> or <code>PackageManager.PERMISSION_DENIED</code>.
     *     Never null
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