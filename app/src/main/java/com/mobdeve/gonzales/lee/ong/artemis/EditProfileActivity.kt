package com.mobdeve.gonzales.lee.ong.artemis

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Matrix
import android.media.ExifInterface
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.ProgressBar
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import com.bumptech.glide.Glide
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.*
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.ktx.storage
import de.hdodenhof.circleimageview.CircleImageView
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream

/**
 * Class handling the functionalities related to editing the user's profile.
 *
 * @constructor Creates a class that handles the functionalities related to editing the user's
 * profile.
 */
class EditProfileActivity : AppCompatActivity() {
    /**
     * Floating action button for editing the user's profile picture.
     */
    private lateinit var fabEditProfilePicEdit: FloatingActionButton

    /**
     * Dialog showing the options related to editing the profile picture (either by choosing
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
     * Button for saving the new attributes of the user's profile.
     */
    private lateinit var btnEditProfileSave: Button

    /**
     * Image view for the user's profile picture.
     */
    private lateinit var civEditProfilePic: CircleImageView

    /**
     * Input field for the user's username.
     */
    private lateinit var tietEditProfileUsername: TextInputEditText

    /**
     * Input field for the user's short bio.
     */
   // private lateinit var tietEditProfileBio: TextInputEditText
    private lateinit var clEditProfileBio : ConstraintLayout

    /**
     * Clickable layout for editing the user's email address.
     */
    private lateinit var clEditProfileEmail: ConstraintLayout

    /**
     * Clickable layout for editing the user's password.
     */
    private lateinit var clEditProfilePassword: ConstraintLayout

    /**
     * Progress bar to signal that data are being saved into the database.
     */
    private lateinit var pbEditProfile: ProgressBar

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
     * <code>true</code> if the profile picture was taken using the device camera;
     * <code>false</code>, otherwise.
     */
    private var cameraTaken: Boolean = false

    /**
     * Byte array pertaining to the profile picture uploaded by the user.
     */
    private lateinit var photoByte: ByteArray

    /**
     * URI of the profile picture uploaded by the user.
     */
    private lateinit var photoUri: String

    /**
     * Activity result launcher related to taking photos using the device camera.
     */
    private lateinit var cameraLauncher: ActivityResultLauncher<Intent>

    /**
     * Activity result launcher related to choosing photos from the Gallery.
     */
    private lateinit var galleryLauncher: ActivityResultLauncher<Intent>

    /**
     * Starting point for Firebase authentication SDK.
     */
    private lateinit var mAuth: FirebaseAuth

    /**
     * Starting point for all database-related operations.
     */
    private lateinit var db: DatabaseReference

    /**
     * Represents a user profile's information in the Firebase user database.
     */
    private lateinit var user: FirebaseUser

    /**
     * Unique identifier of the user.
     */
    private lateinit var userId: String

    /**
     * Service that supports uploading and downloading large objects to Google Cloud Storage.
     */
    private lateinit var storage: FirebaseStorage

    /**
     * Represents a reference to a Google Cloud Storage object.
     */
    private lateinit var storageRef: StorageReference

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
        setContentView(R.layout.activity_edit_profile)

        initFirebase()
        initComponents()
        initGalleryLauncher()
        initCameraLauncher()
    }

    /**
     * Initializes the activity result launcher related to choosing photos from the Gallery.
     */
    private fun initGalleryLauncher() {
        galleryLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
                result: ActivityResult ->
            if (result.resultCode == Activity.RESULT_OK) {
                val path = result.data?.data
                civEditProfilePic.setImageURI(path)
                isProfilePictureUploaded = true

                this.photoUri = path.toString()
            }
        }
    }

    /**
     * Initializes the activity result launcher related to taking photos using the device camera.
     */
    private fun initCameraLauncher() {
        cameraLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
                if (result.resultCode == Activity.RESULT_OK) {
                    val path = photoFile.absolutePath
                    fetchFromCamera(path)
                    isProfilePictureUploaded = true
                    cameraTaken = true
                }
            }
    }

    /**
     * Displays the photo taken using the device camera in the proper orientation.
     *
     * This method addresses the issue of most device cameras setting the orientation of a captured
     * image to landscape. The code for rotation is a direct translation of the one found in
     * [https://www.py4u.net/discuss/611150](https://www.py4u.net/discuss/611150).
     *
     * @param photoPath Path to the photo to be rotated.
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

        /* Compress the photo */
        rotatedBitmap.compress(Bitmap.CompressFormat.JPEG, 60, FileOutputStream(photoPath))


        civEditProfilePic.setImageBitmap(rotatedBitmap)


        val outputStream = ByteArrayOutputStream()
        rotatedBitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream)
        this.photoByte = outputStream.toByteArray()
    }

    /**
     * Initializes the Firebase-related components.
     */
    private fun initFirebase(){
        this.mAuth = Firebase.auth
        this.db = Firebase.database.reference

        if (this.mAuth.currentUser != null){
            this.user = this.mAuth.currentUser!!
            this.userId = this.user.uid
        }

        else{
            val intent = Intent(this@EditProfileActivity, BrokenLinkActivity::class.java)
            startActivity(intent)
        }

        this.storage = Firebase.storage
        this.storageRef = this.storage.reference
    }

    /**
     * Initializes the components of the activity.
     */
    private fun initComponents() {
        setSupportActionBar(findViewById(R.id.toolbar_edit_profile))
        initActionBar()

        this.civEditProfilePic = findViewById(R.id.civ_edit_profile_pic)
        this.tietEditProfileUsername = findViewById(R.id.tiet_edit_profile_username)

        this.tietEditProfileUsername.isFocusable = false

        this.fabEditProfilePicEdit = findViewById(R.id.fab_edit_profile_pic_edit)
        this.btmProfilePicture = BottomSheetDialog(this@EditProfileActivity)

        this.btnEditProfileSave = findViewById(R.id.btn_edit_profile_save)

        this.clEditProfileBio = findViewById(R.id.cl_edit_profile_edit_bio)

        this.clEditProfileEmail = findViewById(R.id.cl_edit_profile_edit_email)
        this.clEditProfilePassword = findViewById(R.id.cl_edit_profile_edit_password)

        this.pbEditProfile = findViewById(R.id.pb_edit_profile)

        btnEditProfileSave.setOnClickListener {

            pbEditProfile.visibility = View.VISIBLE
            updateImg()

        }

        clEditProfileBio.setOnClickListener {
            val intent = Intent(this@EditProfileActivity, EditBioActivity::class.java)
            startActivity(intent)
        }

        clEditProfileEmail.setOnClickListener {
            val intent = Intent(this@EditProfileActivity, EditEmailActivity::class.java)
            startActivity(intent)
        }

        clEditProfilePassword.setOnClickListener {
            val intent = Intent(this@EditProfileActivity, EditPasswordActivity::class.java)
            startActivity(intent)
        }


        initContent()
        launchDialog()

    }

    /**
     * Saves the new profile picture and short bio in the remote database, effectively replacing
     * the old profile picture and short bio of the user.
     *
     * If the user choose to remove their current profile picture, a placeholder photo is saved
     * instead (currently, this placeholder is the launcher icon of the app, that is, a chibi
     * version of the Artemis logo).
     *
     * @param bio New short bio of the user.
     */
    private fun updateImg(){
        if (isProfilePictureUploaded){
            val url = this.storageRef.child(this.userId)

            if(cameraTaken){
                url.putBytes(this.photoByte)
                    .addOnSuccessListener {
                        url.downloadUrl
                            .addOnSuccessListener {
                                updateDB(it.toString())
                            }

                            .addOnFailureListener {
                                uploadFailed()
                            }
                    }
                    .addOnFailureListener{
                        uploadFailed()
                    }
            }

            else{
                url.putFile(Uri.parse(this.photoUri))
                    .addOnSuccessListener {
                        url.downloadUrl
                            .addOnSuccessListener {
                                updateDB(it.toString())
                            }

                            .addOnFailureListener {
                                uploadFailed()
                            }
                    }
                    .addOnFailureListener{
                        uploadFailed()
                    }
            }
        }
        else{
            updateDB("https://firebasestorage.googleapis.com/v0/b/artemis-77e4e.appspot.com/o/chibi_artemis_hd.png?alt=media&token=53dfd292-76a2-4abb-849c-c5fcbb7932d2")
        }
    }

    /**
     * Defines the behavior when the new profile picture is successfully uploaded.
     */
    private fun uploadSuccessfully(){
        pbEditProfile.visibility = View.GONE
        Toast.makeText(this@EditProfileActivity, "Update in progress", Toast.LENGTH_SHORT).show()
    }

    /**
     * Defines the behavior when the new profile picture is not successfully uploaded.
     */
    private fun uploadFailed(){
        pbEditProfile.visibility = View.GONE
        Toast.makeText(this@EditProfileActivity, "Unable to process your actions right now. Please try again later", Toast.LENGTH_SHORT).show()
    }

    /**
     * Fetches the attributes comprising the user's profile and updates the view.
     */
    private fun initContent(){
        this.pbEditProfile.visibility = View.VISIBLE

        this.db.child(Keys.KEY_DB_USERS.name).child(this.userId).addValueEventListener(object: ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                pbEditProfile.visibility = View.GONE

                val profPic: String = snapshot.child(Keys.userImg.name).value.toString()
                val username: String = snapshot.child(Keys.username.name).value.toString()

                if (!(this@EditProfileActivity as Activity).isFinishing) {
                    Glide.with(this@EditProfileActivity)
                        .load(profPic)
                        .placeholder(R.drawable.chibi_artemis_hd)
                        .error(R.drawable.chibi_artemis_hd)
                        .into(civEditProfilePic)
                }

                tietEditProfileUsername.setText(username)

            }

            override fun onCancelled(error: DatabaseError) {
                pbEditProfile.visibility = View.GONE
                Toast.makeText(applicationContext, "Failed to access user", Toast.LENGTH_SHORT).show()
            }
        })
    }

    /**
     * Launches the bottom sheet dialog showing the options for editing the profile picture:
     * removing the current profile picture, selecting a photo from the Gallery, or taking a
     * photo using the device camera.
     */
    private fun launchDialog() {
        val view = LayoutInflater.from(this@EditProfileActivity).inflate(R.layout.dialog_profile_picture, null)

        this.fabEditProfilePicEdit.setOnClickListener {
            btmProfilePicture.setContentView(view)

            this.clDialogProfilePictureEdit = btmProfilePicture.findViewById(R.id.cl_dialog_profile_picture_edit)!!
            this.clDialogProfilePictureCamera = btmProfilePicture.findViewById(R.id.cl_dialog_profile_picture_camera)!!
            this.clDialogProfilePictureDelete = btmProfilePicture.findViewById(R.id.cl_dialog_profile_picture_delete)!!

            clDialogProfilePictureEdit.setOnClickListener {
                PostArtworkUtil.chooseFromGallery(this, galleryLauncher)
                btmProfilePicture.dismiss()
            }

            clDialogProfilePictureCamera.setOnClickListener {
                photoFile = PostArtworkUtil.takeFromCamera(this, this@EditProfileActivity, cameraLauncher)
                btmProfilePicture.dismiss()
            }

            clDialogProfilePictureDelete.setOnClickListener {
                civEditProfilePic.setImageResource(R.drawable.chibi_artemis_hd)
                isProfilePictureUploaded = false
                cameraTaken = false
                btmProfilePicture.dismiss()
            }

            btmProfilePicture.show()
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
     * Defines the behavior when the attribute in the user's profile are successfully edited.
     */
    private fun updateProfileSuccessfully(){
        pbEditProfile.visibility = View.GONE
        Toast.makeText(this@EditProfileActivity, "Your profile picture has been updated", Toast.LENGTH_SHORT).show()
    }

    /**
     * Defines the behavior when the attribute in the user's profile are not successfully edited.
     */
    private fun updateProfileFailed(){
        pbEditProfile.visibility = View.GONE
        Toast.makeText(this@EditProfileActivity, "Failed to update your profile picture", Toast.LENGTH_SHORT).show()
    }

    /**
     * Updates the database entries pertaining to the profile picture and short bio of the user.
     *
     * @param userImg URI of the profile picture of the user.
     */
    private fun updateDB(userImg: String){
        val updates = hashMapOf<String, Any>(
            "/${Keys.KEY_DB_USERS.name}/$userId/${Keys.userImg.name}" to userImg
        )

        db.updateChildren(updates)
            .addOnCompleteListener { task ->
                if (task.isSuccessful){
                    updateProfileSuccessfully()
                }

                else{
                    updateProfileFailed()
                }
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
        permissionsResult(requestCode, grantResults, this@EditProfileActivity, this)
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