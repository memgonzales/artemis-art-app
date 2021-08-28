package com.mobdeve.gonzales.lee.ong.artemis

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.*
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import de.hdodenhof.circleimageview.CircleImageView

class EditProfileActivity : AppCompatActivity() {
    private lateinit var fabEditProfilePicEdit: FloatingActionButton
    private lateinit var btmProfilePicture: BottomSheetDialog
    private lateinit var clDialogProfilePictureEdit: ConstraintLayout
    private lateinit var clDialogProfilePictureDelete: ConstraintLayout
    private lateinit var btnEditProfileSave: Button

    private lateinit var civEditProfilePic: CircleImageView
    private lateinit var tietEditProfileUsername: TextInputEditText
    private lateinit var tietEditProfileEmail: TextInputEditText
    private lateinit var tietEditProfilePassword: TextInputEditText
    private lateinit var tietEditProfileBio: TextInputEditText

    private lateinit var tilEditProfileEmail: TextInputLayout
    private lateinit var tilEditProfilePassword: TextInputLayout

    //Firebase
    private lateinit var mAuth: FirebaseAuth
    private lateinit var ref: DatabaseReference

    private lateinit var user: FirebaseUser
    private lateinit var userId: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_profile)

        initFirebase()
        initComponents()
    }

    private fun initFirebase(){
        this.mAuth = Firebase.auth
        this.ref = Firebase.database.reference

        this.user = this.mAuth.currentUser!!
        this.userId = this.user.uid
    }

    private fun initComponents() {
        setSupportActionBar(findViewById(R.id.toolbar_edit_profile))
        initActionBar()

        this.civEditProfilePic = findViewById(R.id.civ_edit_profile_pic)
        this.tietEditProfileUsername = findViewById(R.id.tiet_edit_profile_username)
        this.tietEditProfileEmail = findViewById(R.id.tiet_edit_profile_email)
        this.tietEditProfilePassword = findViewById(R.id.tiet_edit_profile_password)
        this.tietEditProfileBio = findViewById(R.id.tiet_edit_profile_bio)

        this.tilEditProfileEmail = findViewById(R.id.til_edit_profile_email)
        this.tilEditProfilePassword = findViewById(R.id.til_edit_profile_password)

        this.tietEditProfileUsername.isFocusable = false

        this.fabEditProfilePicEdit = findViewById(R.id.fab_edit_profile_pic_edit)
        this.btmProfilePicture = BottomSheetDialog(this@EditProfileActivity)
        this.btnEditProfileSave = findViewById(R.id.btn_edit_profile_save)

        btnEditProfileSave.setOnClickListener(View.OnClickListener {
            var profPic: Int = civEditProfilePic.id
            var email: String = tietEditProfileEmail.text.toString().trim()
            var password: String = tietEditProfilePassword.text.toString().trim()
            var bio: String = tietEditProfileBio.text.toString().trim()

            if(!checkEmpty(email, password)){
                updateProfile(profPic, email, password, bio)
            }

        })

        initContent()
        launchDialog()
    }

    private fun initContent(){
        this.ref.child(Keys.KEY_DB_USERS.name).child(this.userId).addValueEventListener(object: ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                var profPic: Int = snapshot.child(Keys.userImg.name).getValue().toString().toInt()
                var username: String = snapshot.child(Keys.username.name).getValue().toString()
                var email: String = snapshot.child(Keys.email.name).getValue().toString()
                var pw: String = snapshot.child(Keys.password.name).getValue().toString()
                var bio: String = snapshot.child(Keys.bio.name).getValue().toString()
                //var bio: String = snapshot.child("userPosts").getValue().toString()

                civEditProfilePic.setImageResource(profPic)
                tietEditProfileUsername.setText(username)
                tietEditProfileEmail.setText(email)
                tietEditProfilePassword.setText(pw)
                tietEditProfileBio.setText(bio)
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(applicationContext, "Failed to Access User", Toast.LENGTH_SHORT).show()
            }
        })
    }

    /*

       private fun initContent() {
           var dataProfile = DataHelper.loadProfileData()

           this.civEditProfilePic.setImageResource(dataProfile.getUserImg())
           this.tietEditProfileUsername.setText(dataProfile.getUsername())
           this.tietEditProfileEmail.setText(dataProfile.getEmail())
           this.tietEditProfilePassword.setText(dataProfile.getPassword())
           this.tietEditProfileBio.setText(dataProfile.getBio())
       }



     */

    private fun launchDialog() {
        val view = LayoutInflater.from(this@EditProfileActivity).inflate(R.layout.dialog_profile_picture, null)

        this.fabEditProfilePicEdit.setOnClickListener {
            btmProfilePicture.setContentView(view)

            this.clDialogProfilePictureEdit = btmProfilePicture.findViewById(R.id.cl_dialog_profile_picture_edit)!!
            this.clDialogProfilePictureDelete = btmProfilePicture.findViewById(R.id.cl_dialog_profile_picture_delete)!!

            clDialogProfilePictureEdit.setOnClickListener(View.OnClickListener {
                Toast.makeText(this@EditProfileActivity, "Profile picture is edited", Toast.LENGTH_SHORT).show()
                btmProfilePicture.dismiss()
            })

            clDialogProfilePictureDelete.setOnClickListener(View.OnClickListener {
                Toast.makeText(this@EditProfileActivity, "Profile picture is deleted", Toast.LENGTH_SHORT).show()
                btmProfilePicture.dismiss()
            })

            btmProfilePicture.show()
        }
    }

    private fun initActionBar() {
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
    }

    private fun checkEmpty(email: String, password: String): Boolean {
        var hasEmpty: Boolean = false

        if (email.isEmpty()) {
            tilEditProfileEmail.error = "Required"
            tilEditProfileEmail.requestFocus()
        }

        else {
            tilEditProfileEmail.error = null
        }

        if (password.isEmpty()) {
            tilEditProfilePassword.error = "Required"
            tilEditProfilePassword.requestFocus()
        }

        else {
            tilEditProfilePassword.error = null
        }

        return hasEmpty
    }

    private fun updateProfile(userImg: Int, email: String, password: String, bio: String){

        val userDB = this.ref.child(Keys.KEY_DB_USERS.name).child(userId)

        userDB.child(Keys.userImg.name).setValue(userImg)
            .addOnSuccessListener {
                userDB.child(Keys.email.name).setValue(email)
                    .addOnSuccessListener {
                        userDB.child(Keys.password.name).setValue(password)
                            .addOnSuccessListener {
                                userDB.child(Keys.bio.name).setValue(bio)
                                    .addOnSuccessListener {
                                        updateSuccessfully()
                                    }
                                    .addOnFailureListener{
                                        updateFailed()
                                    }
                            }
                            .addOnFailureListener{
                                updateFailed()
                            }
                    }
                    .addOnFailureListener{
                        updateFailed()
                    }
            }
            .addOnFailureListener{
                updateFailed()
            }
    }

    private fun updateSuccessfully(){
        Toast.makeText(this@EditProfileActivity, "Your profile details have been updated", Toast.LENGTH_SHORT).show()

        val intent = Intent(this@EditProfileActivity, ViewProfileActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun updateFailed(){
        Toast.makeText(this@EditProfileActivity, "Failed to update your profile details", Toast.LENGTH_SHORT).show()
    }
}