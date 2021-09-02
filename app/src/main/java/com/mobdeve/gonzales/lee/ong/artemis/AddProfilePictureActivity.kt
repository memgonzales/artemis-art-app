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
     *
     */
    private lateinit var fabAddProfilePicEdit: FloatingActionButton
    private lateinit var btmProfilePicture: BottomSheetDialog

    private lateinit var civUploadImg: CircleImageView
    private lateinit var ivCameraPic: ImageView

    private lateinit var tvSkipUpload: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_profile_picture)

        initComponents()
    }

    private fun initComponents() {
        this.civUploadImg = findViewById(R.id.civ_add_profile_profile_pic)
        this.ivCameraPic = findViewById(R.id.iv_add_profile_pic_camera)

        setSupportActionBar(findViewById(R.id.toolbar_add_profile_pic))

        this.btnAddProfilePic = findViewById(R.id.btn_add_profile_pic_add)
        launchAddBio()

        this.fabAddProfilePicEdit = findViewById(R.id.fab_add_profile_pic_edit)
        this.btmProfilePicture = BottomSheetDialog(this@AddProfilePictureActivity)
        launchDialog()

        this.tvSkipUpload = findViewById(R.id.tv_add_profile_pic_skip)
        onSkipUpload()
    }

    private fun launchAddBio() {
        this.btnAddProfilePic.setOnClickListener {
            val i = Intent(this@AddProfilePictureActivity, AddProfileBioActivity::class.java)
            startActivity(i)
        }
    }

    private fun launchDialog() {
        val view = LayoutInflater.from(this@AddProfilePictureActivity).inflate(R.layout.dialog_profile_picture, null)

        this.fabAddProfilePicEdit.setOnClickListener {
            btmProfilePicture.setContentView(view)
            btmProfilePicture.show()
        }
    }

    override fun onBackPressed() {
        val i = Intent(this@AddProfilePictureActivity, BrowseFeedActivity::class.java)
        Toast.makeText(this@AddProfilePictureActivity, "You may update your profile details through the account tab",
            Toast.LENGTH_SHORT).show()
        startActivity(i)
        finish()
    }

    private fun onSkipUpload(){
        this.tvSkipUpload.setOnClickListener {
            val i = Intent(this@AddProfilePictureActivity, AddProfileBioActivity::class.java)
            Toast.makeText(this@AddProfilePictureActivity, "You may update your profile details through the account tab",
                Toast.LENGTH_SHORT).show()
            startActivity(i)
            finish()
        }
    }
}