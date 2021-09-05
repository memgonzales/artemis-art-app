package com.mobdeve.gonzales.lee.ong.artemis

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Matrix
import android.media.ExifInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.floatingactionbutton.FloatingActionButton
import de.hdodenhof.circleimageview.CircleImageView
import java.io.File

/**
 * Class handling the functionalities related to adding a profile picture as part of user profile
 * customization after signing up.
 *
 * @constructor Creates an activity for adding a profile picture as part of user profile customization
 * after signing up
 */
class AddProfilePictureActivity : AppCompatActivity() {
    /**
     * Button for adding the profile picture
     */
    private lateinit var btnAddProfilePic: Button

    /**
     * Button for editing the profile picture
     */
    private lateinit var fabAddProfilePicEdit: FloatingActionButton

    /**
     * Dialog showing the options related to adding a profile picture (either by choosing
     * from the Gallery or opening the camera) or removing it.
     */
    private lateinit var btmProfilePicture: BottomSheetDialog

    /**
     * Clickable layout for choosing a profile picture from the Gallery.
     */
    private lateinit var clDialogProfilePictureEdit: ConstraintLayout

    /**
     * Clickable layout for taking a photo using the device camera.
     */
    private lateinit var clDialogProfilePictureCamera: ConstraintLayout

    /**
     * Clickable layout for removing the uploaded profile picture.
     */
    private lateinit var clDialogProfilePictureDelete: ConstraintLayout

    /**
     * Profile picture uploaded the user (or the placeholder if the user has not yet uploaded).
     */
    private lateinit var civUploadImg: CircleImageView

    /**
     * Clickable  text view for skipping the addition of a profile picture.
     */
    private lateinit var tvSkipUpload: TextView

    /**
     * Photo of the artwork for posting.
     */
    private lateinit var photoFile: File

    /**
     * Set to <code>false</code> when the user did not upload any profile picture (or the user
     * decided to remove an uploaded profile picture); <code>true</code>, otherwise.
     */
    private var isProfilePictureUploaded: Boolean = false

    /**
     * Activity result launcher related to taking photos using the device camera.
     */
    private lateinit var cameraLauncher: ActivityResultLauncher<Intent>

    /**
     * Activity result launcher related to choosing photos from the Gallery
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
        setContentView(R.layout.activity_add_profile_picture)

        initComponents()
        initGalleryLauncher()
        initCameraLauncher()
    }

    /**
     * Initializes the activity result launcher related to choosing photos from the Gallery.
     *
     * @packageContext context tied to this activity
     */
    private fun initGalleryLauncher() {
        galleryLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
                result: ActivityResult ->
            if (result.resultCode == Activity.RESULT_OK) {
                val path = result.data?.data
                civUploadImg.setImageURI(path)
                isProfilePictureUploaded = true
            }
        }
    }

    /**
     * Initializes the activity result launcher related to taking photos using the device camera
     *
     * @packageContext context tied to this activity
     */
    private fun initCameraLauncher() {
        cameraLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
            if (result.resultCode == Activity.RESULT_OK) {
                val path = photoFile.absolutePath
                fetchFromCamera(path)
                isProfilePictureUploaded = true
            }
        }
    }

    /**
     * Displays the photo taken using the device camera in the proper orientation.
     *
     * This method addresses the issue of most device cameras setting the orientation of a captured
     * image to landscape. The code for rotation is a direct translation of the one found in
     * <a href = "https://www.py4u.net/discuss/611150">https://www.py4u.net/discuss/611150</a>
     *
     * @param photoPath path to the photo to be rotated
     */
    private fun fetchFromCamera(photoPath: String?) {
        val bounds = BitmapFactory.Options()
        bounds.inJustDecodeBounds = true
        BitmapFactory.decodeFile(photoPath, bounds)

        val opts = BitmapFactory.Options()
        val bm = BitmapFactory.decodeFile(photoPath, opts)
        val exif = ExifInterface(photoPath!!)
        val orientString: String? = exif.getAttribute(ExifInterface.TAG_ORIENTATION)
        val orientation = orientString?.toInt() ?: ExifInterface.ORIENTATION_NORMAL

        var rotationAngle = 0
        if (orientation == ExifInterface.ORIENTATION_ROTATE_90) rotationAngle = 90
        if (orientation == ExifInterface.ORIENTATION_ROTATE_180) rotationAngle = 180
        if (orientation == ExifInterface.ORIENTATION_ROTATE_270) rotationAngle = 270

        val matrix = Matrix()
        matrix.setRotate(rotationAngle.toFloat(), bm.width.toFloat() / 2, bm.height.toFloat() / 2)
        val rotatedBitmap =
            Bitmap.createBitmap(bm, 0, 0, bounds.outWidth, bounds.outHeight, matrix, true)

        civUploadImg.setImageBitmap(rotatedBitmap)
    }

    /**
     * Initializes the components of the activity.
     */
    private fun initComponents() {
        this.civUploadImg = findViewById(R.id.civ_add_profile_profile_pic)

        setSupportActionBar(findViewById(R.id.toolbar_add_profile_pic))

        this.btnAddProfilePic = findViewById(R.id.btn_add_profile_pic_add)
        launchAddBio()

        this.fabAddProfilePicEdit = findViewById(R.id.fab_add_profile_pic_edit)
        this.btmProfilePicture = BottomSheetDialog(this@AddProfilePictureActivity)
        launchDialog()

        this.tvSkipUpload = findViewById(R.id.tv_add_profile_pic_skip)
        onSkipUpload()
    }

    /**
     * Defines the behavior when the button for adding a profile picture is clicked, that is,
     * the profile picture has been successfully added and the user is directed towards
     * adding a short bio.
     */
    private fun launchAddBio() {
        this.btnAddProfilePic.setOnClickListener {
            val i = Intent(this@AddProfilePictureActivity, AddProfileBioActivity::class.java)
            startActivity(i)
        }
    }

    /**
     * Launches a dialog showing the options related to adding a profile picture (either by choosing
     * from the Gallery or opening the camera) or removing it.
     */
    private fun launchDialog() {
        val view = LayoutInflater.from(this@AddProfilePictureActivity).inflate(R.layout.dialog_profile_picture, null)

        this.fabAddProfilePicEdit.setOnClickListener {
            btmProfilePicture.setContentView(view)

            this.clDialogProfilePictureEdit = btmProfilePicture.findViewById(R.id.cl_dialog_profile_picture_edit)!!
            this.clDialogProfilePictureCamera = btmProfilePicture.findViewById(R.id.cl_dialog_profile_picture_camera)!!
            this.clDialogProfilePictureDelete = btmProfilePicture.findViewById(R.id.cl_dialog_profile_picture_delete)!!

            clDialogProfilePictureEdit.setOnClickListener {
                PostArtworkUtil.chooseFromGallery(this, galleryLauncher)
            }

            clDialogProfilePictureCamera.setOnClickListener {
                photoFile = PostArtworkUtil.takeFromCamera(this, this@AddProfilePictureActivity, cameraLauncher)
            }

            clDialogProfilePictureDelete.setOnClickListener {
                civUploadImg.setImageResource(R.drawable.painter)
                isProfilePictureUploaded = false
            }

            btmProfilePicture.show()
        }
    }

    /**
     * Called when the activity has detected the user's press of the back key.
     */
    override fun onBackPressed() {
        val i = Intent(this@AddProfilePictureActivity, BrowseFeedActivity::class.java)
        Toast.makeText(this@AddProfilePictureActivity, "You may update your profile details through the account tab",
            Toast.LENGTH_SHORT).show()
        startActivity(i)
        finish()
    }

    /**
     * Defines the behavior when the user chooses to skip adding a profile picture.
     */
    private fun onSkipUpload() {
        this.tvSkipUpload.setOnClickListener {
            val i = Intent(this@AddProfilePictureActivity, AddProfileBioActivity::class.java)
            Toast.makeText(this@AddProfilePictureActivity, "You may update your profile details through the account tab",
                Toast.LENGTH_SHORT).show()
            startActivity(i)
            finish()
        }
    }

    /**
     * Callback for the result from requesting permissions.
     *
     * @param requestCode the request code passed in <code>
     *     ActivityCompat.requestPermissions(android.app.Activity, String[], int)</code>
     * @param permissions the requested permissions. Never null
     * @param grantResults the grant results for the corresponding permissions which is either <code>
     *     PackageManager.PERMISSION_GRANTED</code> or <code>PackageManager.PERMISSION_DENIED</code>.
     *     Never null
     */
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>,
                                            grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        permissionsResult(requestCode, grantResults, this@AddProfilePictureActivity, this)
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
                val temp: File? = PostArtworkUtil.permissionsResultCamera(grantResults, activity,
                    context, cameraLauncher)

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