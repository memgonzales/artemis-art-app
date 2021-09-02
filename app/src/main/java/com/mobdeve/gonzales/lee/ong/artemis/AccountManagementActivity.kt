package com.mobdeve.gonzales.lee.ong.artemis

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

/**
 * Class handling the functionalities related to account management.
 *
 * @constructor Creates an activity for account management
 */
class AccountManagementActivity : AppCompatActivity() {
    private lateinit var clAccountManagementDelete: ConstraintLayout
    private lateinit var bnvAccountManagementBottom: BottomNavigationView

    private lateinit var btmAddPost: BottomSheetDialog
    private lateinit var fabAddPost: FloatingActionButton
    private lateinit var clDialogPostArtworkGallery: ConstraintLayout
    private lateinit var clDialogPostArtworkPhoto: ConstraintLayout

    private lateinit var mAuth: FirebaseAuth
    private lateinit var ref: DatabaseReference
    private lateinit var user: FirebaseUser
    private lateinit var userId: String

    /**
     * Called when the activity is starting.
     *
     * @param savedInstanceState  If the activity is being re-initialized after previously being
     * shut down then this Bundle contains the data it most recently supplied in
     * <code>onSaveInstanceState(Bundle)</code>. Note: Otherwise it is null. This value may be null.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_account_management)

        initFirebase()
        initDeleteDialog()
        initBottom()
        initComponents()
    }

    /**
     * Initializes the Firebase-related components.
     */
    private fun initFirebase(){
        this.mAuth = Firebase.auth
        this.ref = Firebase.database.reference
        this.user = this.mAuth.currentUser!!
        this.userId = this.user.uid
    }

    /**
     * Launches a confirmation dialog when the Delete Account button is clicked.
     */
    private fun initDeleteDialog() {
        this.clAccountManagementDelete = findViewById(R.id.cl_account_management_delete)

        clAccountManagementDelete.setOnClickListener(View.OnClickListener {
            deleteDialog()
        })
    }

    /**
     * Creates the confirmation dialog when the Delete Account button is clicked.
     */
    private fun deleteDialog() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Delete Profile")
        builder.setMessage("Are you sure you want to delete your profile? This action cannot be reversed.")
        builder.setPositiveButton(
            "Delete"
        ) { _, _ ->

            this.user.delete()
                .addOnCompleteListener { task ->
                    if(task.isSuccessful){
                        ref.child(Keys.KEY_DB_USERS.name).child(this.userId).removeValue()
                        deleteSuccessfully()
                    }

                    else{
                        deleteFailed()
                    }
                }
        }

        builder.setNegativeButton(
            "Cancel"
        ) { _, _ -> }
        builder.create().show()
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
        Toast.makeText(applicationContext, "Failed to delete account", Toast.LENGTH_SHORT).show()
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

        bnvAccountManagementBottom.setOnItemSelectedListener{ item ->
            when (item.itemId) {
                R.id.icon_home_profile -> {
                    val intent = Intent(this@AccountManagementActivity, BrowseFeedActivity::class.java)
                    startActivity(intent)
                    return@setOnItemSelectedListener true
                }
                R.id.icon_follow_profile -> {
                    val intent = Intent(this@AccountManagementActivity, BrowseFeedFollowedActivity::class.java)
                    startActivity(intent)
                    return@setOnItemSelectedListener true
                }
                R.id.icon_bookmark_profile -> {
                    val intent = Intent(this@AccountManagementActivity, BrowseBookmarksActivity::class.java)
                    startActivity(intent)
                    return@setOnItemSelectedListener true
                }
                R.id.icon_user_profile -> {
                    val intent = Intent(this@AccountManagementActivity, ViewProfileActivity::class.java)
                    startActivity(intent)
                    return@setOnItemSelectedListener true
                }
            }
            false
        }
    }

    /**
     * Adds a back button to the action bar.
     */
    private fun initActionBar() {
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
    }

    private fun addPost() {
        this.btmAddPost = BottomSheetDialog(this@AccountManagementActivity)
        this.fabAddPost = findViewById(R.id.fab_account_management_add)

        val view = LayoutInflater.from(this@AccountManagementActivity).inflate(R.layout.dialog_post_artwork, null)

        this.fabAddPost.setOnClickListener {
            btmAddPost.setContentView(view)

            this.clDialogPostArtworkGallery = btmAddPost.findViewById(R.id.cl_dialog_post_artwork_gallery)!!
            this.clDialogPostArtworkPhoto = btmAddPost.findViewById(R.id.cl_dialog_post_artwork_photo)!!

            clDialogPostArtworkGallery.setOnClickListener(View.OnClickListener {
                Toast.makeText(this@AccountManagementActivity, "Photo chosen from the gallery", Toast.LENGTH_SHORT).show()
                btmAddPost.dismiss()
                val intent = Intent(this@AccountManagementActivity, PostArtworkActivity::class.java)
                startActivity(intent)
            })

            clDialogPostArtworkPhoto.setOnClickListener(View.OnClickListener {
                Toast.makeText(this@AccountManagementActivity, "Photo taken with the device camera", Toast.LENGTH_SHORT).show()
                btmAddPost.dismiss()
                val intent = Intent(this@AccountManagementActivity, PostArtworkActivity::class.java)
                startActivity(intent)
            })

            btmAddPost.show()
        }
    }
}