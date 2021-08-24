package com.mobdeve.gonzales.lee.ong.artemis

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.floatingactionbutton.FloatingActionButton

class EditProfileActivity : AppCompatActivity() {
    private lateinit var fabEditProfilePicEdit: FloatingActionButton
    private lateinit var btmProfilePicture: BottomSheetDialog
    private lateinit var clDialogProfilePictureEdit: ConstraintLayout
    private lateinit var clDialogProfilePictureDelete: ConstraintLayout
    private lateinit var btnEditProfileSave: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_profile)

        initComponents()
    }

    private fun initComponents() {
        setSupportActionBar(findViewById(R.id.toolbar_edit_profile))
        initActionBar()

        this.fabEditProfilePicEdit = findViewById(R.id.fab_edit_profile_pic_edit)
        this.btmProfilePicture = BottomSheetDialog(this@EditProfileActivity)
        this.btnEditProfileSave = findViewById(R.id.btn_edit_profile_save)

        btnEditProfileSave.setOnClickListener(View.OnClickListener {
            Toast.makeText(this@EditProfileActivity, "Profile changes are saved.", Toast.LENGTH_SHORT).show()
            val intent = Intent(this@EditProfileActivity, ViewProfileActivity::class.java)
            startActivity(intent)
            finish()
        })

        launchDialog()
    }

    private fun launchDialog() {
        val view = LayoutInflater.from(this@EditProfileActivity).inflate(R.layout.dialog_profile_picture, null)

        this.fabEditProfilePicEdit.setOnClickListener {
            btmProfilePicture.setContentView(view)

            this.clDialogProfilePictureEdit = btmProfilePicture.findViewById(R.id.cl_dialog_profile_picture_edit)!!
            this.clDialogProfilePictureDelete = btmProfilePicture.findViewById(R.id.cl_dialog_profile_picture_delete)!!

            clDialogProfilePictureEdit.setOnClickListener(View.OnClickListener {
                Toast.makeText(this@EditProfileActivity, "Profile picture is edited.", Toast.LENGTH_SHORT).show()
                btmProfilePicture.dismiss()
            })

            clDialogProfilePictureDelete.setOnClickListener(View.OnClickListener {
                Toast.makeText(this@EditProfileActivity, "Profile picture is deleted.", Toast.LENGTH_SHORT).show()
                btmProfilePicture.dismiss()
            })

            btmProfilePicture.show()
        }
    }

    private fun initActionBar() {
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
    }
}