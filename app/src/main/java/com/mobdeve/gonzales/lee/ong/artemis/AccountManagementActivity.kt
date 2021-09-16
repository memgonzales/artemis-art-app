package com.mobdeve.gonzales.lee.ong.artemis

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.EmailAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import java.io.File

/**
 * Class handling the functionalities related to account management.
 *
 * @constructor Creates an activity for account management.
 */
class AccountManagementActivity : AppCompatActivity(), DialogWithInput.DialogWithInputListener {
    /**
     * Clickable layout for account deletion.
     */
    private lateinit var clAccountManagementDelete: ConstraintLayout

    /**
     * Bottom navigation view with menu selection for Home, Followed, Bookmarks, and Profile.
     */
    private lateinit var bnvAccountManagementBottom: BottomNavigationView

    /**
     * Bottom sheet dialog for posting an artwork.
     */
    private lateinit var btmAddPost: BottomSheetDialog

    /**
     * Floating action button for posting an artwork.
     */
    private lateinit var fabAddPost: FloatingActionButton

    /**
     * Layout in the bottom sheet dialog for choosing an artwork from the Gallery.
     */
    private lateinit var clDialogPostArtworkGallery: ConstraintLayout

    /**
     * Layout in the bottom sheet dialog for taking a photo of the artwork using the Camera.
     */
    private lateinit var clDialogPostArtworkPhoto: ConstraintLayout

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
     * Identifier of the user in the Firebase database.
     */
    private lateinit var userId: String

    /**
     * Email address of the user.
     */
    private lateinit var email: String

    /**
     * Credential used by Firebase to authenticate a user.
     */
    private lateinit var credential: AuthCredential

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
     * Text view for fetching the password entered by the user in the confirmatory dialog.
     */
    private lateinit var tvAccountManagementInputPassword: TextView

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
        setContentView(R.layout.activity_account_management)

        initFirebase()
        initDeleteDialog()
        initBottom()
        initComponents()

        initGalleryLauncher(this@AccountManagementActivity)
        initCameraLauncher(this@AccountManagementActivity)
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
     * Initializes the Firebase-related components.
     */
    private fun initFirebase(){
        this.mAuth = Firebase.auth
        this.db = Firebase.database.reference

        if (this.mAuth.currentUser != null){
            this.user = this.mAuth.currentUser!!
            this.userId = this.user.uid
            this.email = this.user.email!!
        }

        else{
            val intent = Intent(this@AccountManagementActivity, BrokenLinkActivity::class.java)
            startActivity(intent)
        }
    }

    /**
     * Launches a confirmation dialog when the Delete Account button is clicked.
     */
    private fun initDeleteDialog() {
        this.firebaseHelper = FirebaseHelper(this@AccountManagementActivity)

        this.clAccountManagementDelete = findViewById(R.id.cl_account_management_delete)

        clAccountManagementDelete.setOnClickListener {
            deleteDialog()
        }
    }

    /**
     * Creates the confirmation dialog when the Delete Account button is clicked.
     */
    private fun deleteDialog() {

        val passwordDialog = DialogWithInput()
        passwordDialog.show(supportFragmentManager, "Dialog");


//        val builder = AlertDialog.Builder(this)
//        builder.setTitle("Delete Profile")
//        builder.setMessage("Are you sure you want to delete your profile? This action cannot be reversed.")
//        builder.setPositiveButton(
//            "Delete"
//        ) { _, _ ->
//
//            this.firebaseHelper = FirebaseHelper(this@AccountManagementActivity)
//            this.firebaseHelper.deleteUserDB()
//
//            /*
//            this.user.delete()
//                .addOnCompleteListener { task ->
//                    if(task.isSuccessful){
//                        this.firebaseHelper.deleteUserDB()
//                        deleteSuccessfully()
//                    }
//
//                    else{
//                        deleteFailed()
//                    }
//                }
//
//             */
//        }
//
//        builder.setNegativeButton(
//            "Cancel"
//        ) { _, _ -> }
//        builder.create().show()
    }

    /**
     * Retrieves and returns the password entered by the user in the confirmation dialog.
     *
     * @param password Password entered by the user in the confirmation dialog.
     * @return Password entered by the user, casted as a string.
     */
    override fun fetchPassword(password: String): String {
        tvAccountManagementInputPassword = findViewById(R.id.tv_account_management_input_password)
        tvAccountManagementInputPassword.text = password

        if (!password.isNullOrEmpty()){
            credential = EmailAuthProvider.getCredential(email, password)

            user.reauthenticate(credential).addOnCompleteListener {
                if (it.isSuccessful){

                    firebaseHelper.deleteUser()

                    user.delete().addOnCompleteListener {
                        if (it.isSuccessful){
                            deleteSuccessfully()
                        }

                        else{
                            deleteFailed()
                        }
                    }
                }

                else{
                    deleteFailed()
                }
            }
        }

        else{
            Toast.makeText(applicationContext, "Please input your password", Toast.LENGTH_SHORT).show()
        }


        return tvAccountManagementInputPassword.text as String
    }


    /**
     * Defines the behavior when the account is successfully deleted.
     */
    private fun deleteSuccessfully(){
        Toast.makeText(applicationContext, "Account deleted", Toast.LENGTH_SHORT).show()
        val intent = Intent(this@AccountManagementActivity, LogInActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP
        startActivity(intent)
    }

    /**
     * Defines the behavior when the account deletion fails.
     */
    private fun deleteFailed(){
        Toast.makeText(applicationContext, "Invalid password", Toast.LENGTH_SHORT).show()
    }

    /**
     * Initializes the components of the activity.
     */
    private fun initComponents() {
        setSupportActionBar(findViewById(R.id.toolbar_account_management))
        initBottom()
        addPost()
        initActionBar()
    }

    /**
     * Sets the listeners for the menu selection found in the bottom navigation view.
     */
    private fun initBottom() {
        this.bnvAccountManagementBottom = findViewById(R.id.nv_account_management_bottom)
        BottomMenuUtil.setBottomMenuListeners(bnvAccountManagementBottom, this,
            this@AccountManagementActivity)
    }

    /**
     * Adds a back button to the action bar.
     */
    private fun initActionBar() {
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
    }

    /**
     * Sets the listeners in relation to adding an artwork (that is, by either choosing an image
     * from the gallery or taking a photo using the device camera) to be posted on Artemis.
     */
    private fun addPost() {
        this.btmAddPost = BottomSheetDialog(this@AccountManagementActivity)
        this.fabAddPost = findViewById(R.id.fab_account_management_add)

        val view = LayoutInflater.from(this@AccountManagementActivity).inflate(R.layout.dialog_post_artwork, null)

        this.fabAddPost.setOnClickListener {
            btmAddPost.setContentView(view)

            this.clDialogPostArtworkGallery = btmAddPost.findViewById(R.id.cl_dialog_post_artwork_gallery)!!
            this.clDialogPostArtworkPhoto = btmAddPost.findViewById(R.id.cl_dialog_post_artwork_photo)!!

            clDialogPostArtworkGallery.setOnClickListener {
                PostArtworkUtil.chooseFromGallery(this, galleryLauncher)
            }

            clDialogPostArtworkPhoto.setOnClickListener {
                photoFile = PostArtworkUtil.takeFromCamera(this, this@AccountManagementActivity, cameraLauncher)
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
        permissionsResult(requestCode, grantResults, this@AccountManagementActivity, this)
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