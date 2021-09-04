package com.mobdeve.gonzales.lee.ong.artemis

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.floatingactionbutton.FloatingActionButton
import de.hdodenhof.circleimageview.CircleImageView

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
     * Profile picture uploaded the user (or the placeholder if the user has not yet uploaded).
     */
    private lateinit var civUploadImg: CircleImageView

    /**
     * Clickable  text view for skipping the addition of a profile picture.
     */
    private lateinit var tvSkipUpload: TextView

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
}