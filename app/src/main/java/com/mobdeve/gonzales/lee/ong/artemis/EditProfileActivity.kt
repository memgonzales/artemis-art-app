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
import com.google.android.material.textfield.TextInputLayout
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

class EditProfileActivity : AppCompatActivity() {
    private lateinit var fabEditProfilePicEdit: FloatingActionButton
    private lateinit var btmProfilePicture: BottomSheetDialog
    private lateinit var clDialogProfilePictureEdit: ConstraintLayout
    private lateinit var clDialogProfilePictureCamera: ConstraintLayout
    private lateinit var clDialogProfilePictureDelete: ConstraintLayout
    private lateinit var btnEditProfileSave: Button

    private lateinit var civEditProfilePic: CircleImageView
    private lateinit var tietEditProfileUsername: TextInputEditText
    private lateinit var tietEditProfileEmail: TextInputEditText
    private lateinit var tietEditProfilePassword: TextInputEditText
    private lateinit var tietEditProfileBio: TextInputEditText

    private lateinit var tilEditProfileEmail: TextInputLayout
    private lateinit var tilEditProfilePassword: TextInputLayout

    private lateinit var clEditProfileEmail: ConstraintLayout
    private lateinit var clEditProfilePassword: ConstraintLayout

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

    private var cameraTaken: Boolean = false
    private lateinit var photoByte: ByteArray
    private lateinit var photoUri: String

    /**
     * Activity result launcher related to taking photos using the device camera.
     */
    private lateinit var cameraLauncher: ActivityResultLauncher<Intent>

    /**
     * Activity result launcher related to choosing photos from the Gallery
     */
    private lateinit var galleryLauncher: ActivityResultLauncher<Intent>



    //Firebase
    private lateinit var mAuth: FirebaseAuth
    private lateinit var db: DatabaseReference

    private lateinit var user: FirebaseUser
    private lateinit var userId: String

    private lateinit var storage: FirebaseStorage
    private lateinit var storageRef: StorageReference

    private lateinit var credentials: AuthCredential


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
     *
     * @param packageContext context tied to this activity
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
     * Initializes the activity result launcher related to taking photos using the device camera
     *
     * @param packageContext context tied to this activity
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

        /* Compress the photo */
        rotatedBitmap.compress(Bitmap.CompressFormat.JPEG, 60, FileOutputStream(photoPath))


        civEditProfilePic.setImageBitmap(rotatedBitmap)


        val outputStream = ByteArrayOutputStream()
        rotatedBitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream)
        this.photoByte = outputStream.toByteArray()
    }


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

    private fun initComponents() {
        setSupportActionBar(findViewById(R.id.toolbar_edit_profile))
        initActionBar()

        this.civEditProfilePic = findViewById(R.id.civ_edit_profile_pic)
        this.tietEditProfileUsername = findViewById(R.id.tiet_edit_profile_username)
        this.tietEditProfileBio = findViewById(R.id.tiet_edit_profile_bio)

        this.tietEditProfileUsername.isFocusable = false

        this.fabEditProfilePicEdit = findViewById(R.id.fab_edit_profile_pic_edit)
        this.btmProfilePicture = BottomSheetDialog(this@EditProfileActivity)

        this.btnEditProfileSave = findViewById(R.id.btn_edit_profile_save)
        this.clEditProfileEmail = findViewById(R.id.cl_edit_profile_edit_email)
        this.clEditProfilePassword = findViewById(R.id.cl_edit_profile_edit_password)

        this.pbEditProfile = findViewById(R.id.pb_edit_profile)

        btnEditProfileSave.setOnClickListener {
//            var username: String = tietEditProfileUsername.text.toString().trim()
//            var profPic: Int = civEditProfilePic.id
//            var email: String = tietEditProfileEmail.text.toString().trim()
//            var password: String = tietEditProfilePassword.text.toString().trim()

            pbEditProfile.visibility = View.VISIBLE
            val bio: String = tietEditProfileBio.text.toString().trim()
            //this.updateBio(bio)
            updateImgBio(bio)

            //val intent: Intent = intent
            //Toast.makeText(applicationContext, "check: " + intent.getStringExtra("Check").toString(), Toast.LENGTH_LONG).show()
//            if(!checkEmpty(email, password)){
//                pbEditProfile.visibility = View.VISIBLE
//                updateProfile(profPic, email, password, bio)
//            }
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

    private fun updateImgBio(bio: String){
        if (isProfilePictureUploaded){
            val url = this.storageRef.child(this.userId)

            if(cameraTaken){
                url.putBytes(this.photoByte)
                    .addOnSuccessListener {
                        url.downloadUrl
                            .addOnSuccessListener {
                                uploadSuccessfully()
                                updateDB(it.toString(), bio)
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
                                uploadSuccessfully()
                                updateDB(it.toString(), bio)
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
            updateDB("https://firebasestorage.googleapis.com/v0/b/artemis-77e4e.appspot.com/o/chibi_artemis_hd.png?alt=media&token=53dfd292-76a2-4abb-849c-c5fcbb7932d2", bio)
        }
    }


    private fun uploadSuccessfully(){
        pbEditProfile.visibility = View.GONE
        Toast.makeText(this@EditProfileActivity, "Successfully uploaded your image", Toast.LENGTH_SHORT).show()
    }

    private fun uploadFailed(){
        pbEditProfile.visibility = View.GONE
        Toast.makeText(this@EditProfileActivity, "Unable to process your actions right now. Please try again later", Toast.LENGTH_SHORT).show()
    }

    private fun initContent(){
        this.pbEditProfile.visibility = View.VISIBLE

        this.db.child(Keys.KEY_DB_USERS.name).child(this.userId).addValueEventListener(object: ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                pbEditProfile.visibility = View.GONE

                val profPic: String = snapshot.child(Keys.userImg.name).getValue().toString()
                val username: String = snapshot.child(Keys.username.name).getValue().toString()
//                var email: String = snapshot.child(Keys.email.name).getValue().toString()
//                var pw: String = snapshot.child(Keys.password.name).getValue().toString()


                Glide.with(this@EditProfileActivity)
                    .load(profPic)
                    .placeholder(R.drawable.placeholder)
                    .error(R.drawable.placeholder)
                    .into(civEditProfilePic)

                val bio: String = snapshot.child(Keys.bio.name).getValue().toString()



                tietEditProfileUsername.setText(username)
//                tietEditProfileEmail.setText(email)
//                tietEditProfilePassword.setText(pw)
                tietEditProfileBio.setText(bio)
            }

            override fun onCancelled(error: DatabaseError) {
                pbEditProfile.visibility = View.GONE
                Toast.makeText(applicationContext, "Failed to Access User", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun launchDialog() {
        val view = LayoutInflater.from(this@EditProfileActivity).inflate(R.layout.dialog_profile_picture, null)

        this.fabEditProfilePicEdit.setOnClickListener {
            btmProfilePicture.setContentView(view)

            this.clDialogProfilePictureEdit = btmProfilePicture.findViewById(R.id.cl_dialog_profile_picture_edit)!!
            this.clDialogProfilePictureCamera = btmProfilePicture.findViewById(R.id.cl_dialog_profile_picture_camera)!!
            this.clDialogProfilePictureDelete = btmProfilePicture.findViewById(R.id.cl_dialog_profile_picture_delete)!!

            clDialogProfilePictureEdit.setOnClickListener {
                PostArtworkUtil.chooseFromGallery(this, galleryLauncher)
            }

            clDialogProfilePictureCamera.setOnClickListener {
                photoFile = PostArtworkUtil.takeFromCamera(this, this@EditProfileActivity, cameraLauncher)
            }

            clDialogProfilePictureDelete.setOnClickListener {
                civEditProfilePic.setImageResource(R.drawable.painter)
                isProfilePictureUploaded = false
                cameraTaken = false
            }

            btmProfilePicture.show()
        }
    }

    private fun initActionBar() {
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
    }

    private fun updateSuccessfully(){
        pbEditProfile.visibility = View.GONE
        Toast.makeText(this@EditProfileActivity, "Your profile details have been updated", Toast.LENGTH_SHORT).show()

        val intent = Intent(this@EditProfileActivity, ViewProfileActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun updateFailed(){
        pbEditProfile.visibility = View.GONE
        Toast.makeText(this@EditProfileActivity, "Failed to update your profile details", Toast.LENGTH_SHORT).show()
    }


    private fun updateDB(userImg: String, bio: String){
       // val userDB = this.db.child(Keys.KEY_DB_USERS.name).child(this.userId)

        val updates = hashMapOf<String, Any>(
            "/${Keys.KEY_DB_USERS.name}/$userId/${Keys.userImg.name}" to userImg,
            "/${Keys.KEY_DB_USERS.name}/$userId/${Keys.bio.name}" to bio
        )

        db.updateChildren(updates)
            .addOnCompleteListener { task ->
                if (task.isSuccessful){
                    updateSuccessfully()
                }

                else{
                    updateFailed()
                }
            }

        /*
        userDB.child(Keys.bio.name).setValue(bio)
            .addOnSuccessListener {
                updateSuccessfully()
            }
            .addOnFailureListener{
                updateFailed()
            }


        this.db.child(Keys.KEY_DB_USERS.name).child(this.userId).child(Keys.userImg.name).setValue(userImg)
            .addOnSuccessListener {
                uploadSuccessfully()
            }
            .addOnFailureListener{
                uploadFailed()
            }

         */
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
        permissionsResult(requestCode, grantResults, this@EditProfileActivity, this)
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