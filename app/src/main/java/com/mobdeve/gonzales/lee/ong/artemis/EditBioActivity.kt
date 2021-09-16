package com.mobdeve.gonzales.lee.ong.artemis

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase


class EditBioActivity : AppCompatActivity() {
    private lateinit var tietEditBio : TextInputEditText

    private lateinit var pbEditBio : ProgressBar

    private lateinit var btnEditBio: Button
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


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_bio)

        initFirebase()
        initComponents()
    }

    private fun initComponents() {
        setSupportActionBar(findViewById(R.id.toolbar_edit_bio))
        initActionBar()

        initContent()
    }

    /**
     * Initializes the Firebase-related components.
     */
    private fun initFirebase(){
        this.mAuth = Firebase.auth
        this.db = Firebase.database.reference

        if (this.mAuth.currentUser != null) {
            this.user = this.mAuth.currentUser!!
            this.userId = this.user.uid

        } else {
            val intent = Intent(this@EditBioActivity, BrokenLinkActivity::class.java)
            startActivity(intent)
        }
    }

    private fun initContent(){
        this.tietEditBio = findViewById(R.id.tiet_edit_bio)
        this.pbEditBio = findViewById(R.id.pb_edit_bio)
        this.btnEditBio = findViewById(R.id.btn_edit_bio_confirm)

        this.db.child(Keys.KEY_DB_USERS.name).child(this.userId)
            .addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val bio: String = snapshot.child(Keys.bio.name).getValue().toString()

                    tietEditBio.setText(bio)

                }

                override fun onCancelled(error: DatabaseError) {
                    val intent = Intent(this@EditBioActivity, BrokenLinkActivity::class.java)
                    startActivity(intent)
                }
            })


        this.btnEditBio.setOnClickListener {
            pbEditBio.visibility = View.VISIBLE

            var bio = tietEditBio.text.toString().trim()
            updateBio(bio)

        }
    }
    /**
     * Adds a back button to the action bar.
     */
    private fun initActionBar() {
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
    }

    private fun updateBio(bio: String){
        this.db.child(Keys.KEY_DB_USERS.name).child(userId).child(Keys.bio.name).setValue(bio)
            .addOnSuccessListener { updateProfileSuccessfully() }
            .addOnFailureListener { updateProfileFailed() }
    }

    /**
     * Defines the behavior when the attribute in the user's profile are successfully edited.
     */
    private fun updateProfileSuccessfully(){
        pbEditBio.visibility = View.GONE
        Toast.makeText(this@EditBioActivity, "Your profile details have been updated", Toast.LENGTH_SHORT).show()
    }

    /**
     * Defines the behavior when the attribute in the user's profile are not successfully edited.
     */
    private fun updateProfileFailed(){
        pbEditBio.visibility = View.GONE
        Toast.makeText(this@EditBioActivity, "Failed to update your profile details", Toast.LENGTH_SHORT).show()
    }
}