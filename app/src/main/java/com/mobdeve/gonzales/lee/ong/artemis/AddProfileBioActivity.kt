package com.mobdeve.gonzales.lee.ong.artemis

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class AddProfileBioActivity : AppCompatActivity() {
    private lateinit var btnAddBio: Button

    private lateinit var tielBio: TextInputEditText
    private lateinit var tvBioSkip: TextView

    //Firebase
    private lateinit var user: FirebaseUser
    private lateinit var mAuth: FirebaseAuth
    private lateinit var db: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_profile_bio)

        initFirebase()
        initComponents()
    }

    private fun initFirebase() {
        this.mAuth = Firebase.auth
        this.user = mAuth.currentUser!!
        this.db = Firebase.database.reference
    }

    private fun initComponents() {
        setSupportActionBar(findViewById(R.id.toolbar_add_profile_bio))
        initActionBar()

        this.tielBio = findViewById(R.id.tiel_add_profile_bio)

        this.btnAddBio = findViewById(R.id.btn_add_profile_bio_add)
        launchAddProfilePic()

        this.tvBioSkip = findViewById(R.id.tv_add_profile_bio_skip)
        skipAddProfilePic()
    }

    private fun initActionBar() {
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
    }

    private fun launchAddProfilePic() {
        this.btnAddBio.setOnClickListener {
            val bio: String = tielBio.text.toString().trim()

            this.db.child(Keys.KEY_DB_USERS.name).child(this.user.uid).child(Keys.KEY_DB_BIO.name).setValue(bio)

            val i = Intent(this@AddProfileBioActivity, AddProfileSuccessActivity::class.java)
            startActivity(i)
            finish()
        }
    }

    private fun skipAddProfilePic() {
        this.tvBioSkip.setOnClickListener {
            val i = Intent(this@AddProfileBioActivity, AddProfileSuccessActivity::class.java)
            Toast.makeText(this@AddProfileBioActivity, "You may update your profile details through the account tab.",
                Toast.LENGTH_SHORT).show()
            startActivity(i)
            finish()
        }
    }

    override fun onBackPressed() {
        val i = Intent(this@AddProfileBioActivity, BrowseFeedActivity::class.java)
        startActivity(i)
        finish()
    }
}