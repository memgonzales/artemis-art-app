package com.mobdeve.gonzales.lee.ong.artemis

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import android.widget.*
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.widget.NestedScrollView
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.ktx.storage
import de.hdodenhof.circleimageview.CircleImageView
import java.io.File


class ViewProfileActivity : AppCompatActivity() {
    private lateinit var civViewProfileProfilePicture: CircleImageView
    private lateinit var tvViewProfileUsername: TextView
    private lateinit var tvViewProfileBio: TextView
    private lateinit var clViewProfileEdit: ConstraintLayout
    private lateinit var clViewProfileViewPosts: ConstraintLayout
    private lateinit var clViewProfileDelete: ConstraintLayout
    private lateinit var clViewProfileLogout: ConstraintLayout
    private lateinit var btnViewProfileHighlights: Button

    private lateinit var bnvViewProfileBottom: BottomNavigationView
    private lateinit var nsvViewProfile: NestedScrollView

    private lateinit var btmAddPost: BottomSheetDialog
    private lateinit var fabAddPost: FloatingActionButton
    private lateinit var clDialogPostArtworkGallery: ConstraintLayout
    private lateinit var clDialogPostArtworkPhoto: ConstraintLayout

    private lateinit var dataUser: User

    private lateinit var mAuth: FirebaseAuth
    private lateinit var db: DatabaseReference
    private lateinit var user: FirebaseUser
    private lateinit var userId: String

    private lateinit var storage: FirebaseStorage
    private lateinit var storageRef: StorageReference

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
        setContentView(R.layout.activity_view_profile)

        initFirebase()
        initContent()
        initComponents()
        addPost()

        initGalleryLauncher(this@ViewProfileActivity)
        initCameraLauncher(this@ViewProfileActivity)
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

    private fun initFirebase(){
        this.mAuth = Firebase.auth
        this.db = Firebase.database.reference

        this.user = this.mAuth.currentUser!!
        this.userId = this.user.uid

        this.storage = Firebase.storage
        this.storageRef = this.storage.reference
    }

    private fun initContent() {
        this.civViewProfileProfilePicture = findViewById(R.id.civ_view_profile_logo)
        this.tvViewProfileUsername = findViewById(R.id.tv_view_user_unregistered_username)
        this.tvViewProfileBio = findViewById(R.id.tv_view_user_unregistered_bio)
        this.clViewProfileEdit = findViewById(R.id.cl_view_profile_edit)
        this.clViewProfileViewPosts = findViewById(R.id.cl_view_profile_view_posts)
        this.clViewProfileDelete = findViewById(R.id.cl_view_profile_delete)
        this.clViewProfileLogout = findViewById(R.id.cl_view_profile_logout)
        this.btnViewProfileHighlights = findViewById(R.id.btn_view_profile_highlights)


        /*
        this.db.child(Keys.KEY_DB_USERS.name).child(this.userId)
            .addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                val profPic: String = snapshot.child(Keys.userImg.name).getValue().toString()
                val username: String = snapshot.child(Keys.username.name).getValue().toString()
                val bio: String = snapshot.child(Keys.bio.name).getValue().toString()

                val localFile = File.createTempFile("images", "jpg")
                storageRef = storage.getReferenceFromUrl(profPic)

                storageRef.getFile(localFile)
                    .addOnSuccessListener {
                        var bitmap = BitmapFactory.decodeFile(localFile.absolutePath)
                        civViewProfileProfilePicture.setImageBitmap(bitmap)
                    }

                tvViewProfileUsername.setText(username)
                tvViewProfileBio.setText(bio)

            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(applicationContext, "Unable to load data", Toast.LENGTH_SHORT).show()
            }

        })


         */


        this.dataUser = DataHelper.loadProfileData()

        //this.civViewProfileProfilePicture.setImageResource(dataUser.getUserImg())
        this.tvViewProfileUsername.text = dataUser.getUsername()
        this.tvViewProfileBio.text = dataUser.getBio()

        clViewProfileEdit.setOnClickListener {
            val intent = Intent(this@ViewProfileActivity, EditProfileActivity::class.java)
            startActivity(intent)
        }

        clViewProfileViewPosts.setOnClickListener {
            val intent = Intent(this@ViewProfileActivity, BrowseOwnPostsActivity::class.java)
            startActivity(intent)
        }

        clViewProfileDelete.setOnClickListener {
            val intent = Intent(this@ViewProfileActivity, AccountManagementActivity::class.java)
            startActivity(intent)
        }

        clViewProfileLogout.setOnClickListener {
            logoutDialog()
        }

        btnViewProfileHighlights.setOnClickListener {
            val intent = Intent(this@ViewProfileActivity, BrowseOwnHighlightsActivity::class.java)
            startActivity(intent)
        }
    }

    private fun initComponents() {
        setSupportActionBar(findViewById(R.id.toolbar_view_profile))
        initBottom()
    }

    private fun initBottom() {
        this.bnvViewProfileBottom = findViewById(R.id.nv_view_profile_bottom)
        this.nsvViewProfile = findViewById(R.id.nsv_view_profile)

        BottomMenuUtil.setScrollBottomMenuListeners(bnvViewProfileBottom, nsvViewProfile,
            BottomMenuUtil.USER, this, this@ViewProfileActivity)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.menu_top_with_search, menu)

        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId) {
            R.id.menu_feed_search -> {
                launchSearch()
                true
            } else -> {
                super.onOptionsItemSelected(item)
            }
        }
    }

    private fun launchSearch() {
        val intent = Intent(this@ViewProfileActivity, SearchActivity::class.java)
        startActivity(intent)
    }

    private fun addPost() {
        this.btmAddPost = BottomSheetDialog(this@ViewProfileActivity)
        this.fabAddPost = findViewById(R.id.fab_view_profile_add)

        val view = LayoutInflater.from(this@ViewProfileActivity).inflate(R.layout.dialog_post_artwork, null)

        this.fabAddPost.setOnClickListener {
            btmAddPost.setContentView(view)

            this.clDialogPostArtworkGallery = btmAddPost.findViewById(R.id.cl_dialog_post_artwork_gallery)!!
            this.clDialogPostArtworkPhoto = btmAddPost.findViewById(R.id.cl_dialog_post_artwork_photo)!!

            clDialogPostArtworkGallery.setOnClickListener {
                PostArtworkUtil.chooseFromGallery(this, galleryLauncher)
            }

            clDialogPostArtworkPhoto.setOnClickListener {
                photoFile = PostArtworkUtil.takeFromCamera(this, this@ViewProfileActivity, cameraLauncher)
            }

            btmAddPost.show()
        }
    }

    private fun logoutDialog() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Log Out")
        builder.setMessage("Do you want to log out?")
        builder.setPositiveButton(
            "Log out"
        ) { _, _ ->
            Toast.makeText(this@ViewProfileActivity, "You have been logged out", Toast.LENGTH_SHORT).show()

            this.mAuth.signOut()
            val intent = Intent(this@ViewProfileActivity, LogInActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP
            startActivity(intent)
        }
        builder.setNegativeButton(
            "Cancel"
        ) { _, _ -> }
        builder.create().show()
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
        permissionsResult(requestCode, grantResults, this@ViewProfileActivity, this)
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