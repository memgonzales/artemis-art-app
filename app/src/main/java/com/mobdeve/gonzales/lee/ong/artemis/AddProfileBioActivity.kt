package com.mobdeve.gonzales.lee.ong.artemis

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

/**
 * Class handling the functionalities related to adding a short bio as part of user profile
 * customization after signing up.
 *
 * @constructor Creates an activity for adding a short bio as part of user profile customization
 * after signing up.
 */
class AddProfileBioActivity : AppCompatActivity() {
    /**
     * Button for adding the short bio.
     */
    private lateinit var btnAddBio: Button

    /**
     * Field for entering the short bio.
     */
    private lateinit var tielBio: TextInputEditText

    /**
     * Clickable text view for skipping the addition of a short bio.
     */
    private lateinit var tvBioSkip: TextView

    /**
     * Progress bar to signal that data are being fetched from the database.
     */
    private lateinit var pbAddBio: ProgressBar

    /**
     * Represents a user profile's information in the Firebase user database.
     */
    private lateinit var user: FirebaseUser

    /**
     * Starting point for Firebase authentication SDK.
     */
    private lateinit var mAuth: FirebaseAuth

    /**
     * Starting point for all database-related operations.
     */
    private lateinit var db: DatabaseReference

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
        setContentView(R.layout.activity_add_profile_bio)

        initFirebase()
        initComponents()
    }

    /**
     * Initializes the Firebase-related components.
     */
    private fun initFirebase() {
        this.mAuth = Firebase.auth
        this.db = Firebase.database.reference

        if(mAuth.currentUser != null){
            this.user = mAuth.currentUser!!
        }

        else{
            val intent = Intent(this@AddProfileBioActivity, BrokenLinkActivity::class.java)
            startActivity(intent)
        }

    }

    /**
     * Initializes the components of the activity.
     */
    private fun initComponents() {
        setSupportActionBar(findViewById(R.id.toolbar_add_profile_bio))
        initActionBar()

        this.tielBio = findViewById(R.id.tiet_add_profile_bio)

        this.btnAddBio = findViewById(R.id.btn_add_profile_bio_add)
        launchProfileSuccess()

        this.tvBioSkip = findViewById(R.id.tv_add_profile_bio_skip)
        skipAddProfilePic()
    }

    /**
     * Adds a back button to the action bar.
     */
    private fun initActionBar() {
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
    }

    /**
     * Defines the behavior when the button for adding a short bio is clicked, that is,
     * the short bio has been successfully added and the user is notified that profile
     * customization has been completed.
     */
    private fun launchProfileSuccess() {
        this.pbAddBio = findViewById(R.id.pb_add_profile_bio)

        this.btnAddBio.setOnClickListener {
            pbAddBio.visibility = View.VISIBLE

            val bio: String = tielBio.text.toString().trim()

            if (!bio.isEmpty()){
                this.db.child(Keys.KEY_DB_USERS.name).child(this.user.uid).child(Keys.bio.name).setValue(bio)
                    .addOnSuccessListener {
                        pbAddBio.visibility = View.GONE
                        Toast.makeText(this@AddProfileBioActivity, "Successfully added your bio", Toast.LENGTH_SHORT).show()
                        val i = Intent(this@AddProfileBioActivity, AddProfileSuccessActivity::class.java)
                        startActivity(i)
                        finish()
                    }
                    .addOnFailureListener{
                        pbAddBio.visibility = View.GONE
                        Toast.makeText(this@AddProfileBioActivity, "Unable to add your bio right now. Please try again later", Toast.LENGTH_SHORT).show()
                    }
            }

            else{
                pbAddBio.visibility = View.GONE
                Toast.makeText(this@AddProfileBioActivity, "Please add a short bio about yourself", Toast.LENGTH_SHORT).show()
            }


        }
    }

    /**
     * Defines the behavior when the user chooses to skip adding a short bio.
     */
    private fun skipAddProfilePic() {
        this.tvBioSkip.setOnClickListener {
            val i = Intent(this@AddProfileBioActivity, AddProfileSuccessActivity::class.java)
            Toast.makeText(this@AddProfileBioActivity, "You may update your profile details through the account tab",
                Toast.LENGTH_SHORT).show()
            startActivity(i)
            finish()
        }
    }

    /**
     * This hook is called whenever an item in your options menu is selected.
     *
     * @param item The menu item that was selected. This value cannot be <code>null</code>.
     * @return Return false to allow normal menu processing to proceed, true to consume it here.
     */
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId) {
            android.R.id.home -> {
                onBackPressed()
                true
            } else -> {
                super.onOptionsItemSelected(item)
            }
        }
    }

    /**
     * Called when the activity has detected the user's press of the back key.
     */
    override fun onBackPressed() {
        val i = Intent(this@AddProfileBioActivity, AddProfilePictureActivity::class.java)
        startActivity(i)
        finish()
    }
}