package com.mobdeve.gonzales.lee.ong.artemis

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.auth.ktx.userProfileChangeRequest
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class AddProfileBioActivity : AppCompatActivity() {
    private var btnAddBio: Button? = null

    private var tielBio: TextInputEditText? = null

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

    private fun initFirebase(){
        this.mAuth = Firebase.auth
        this.user = mAuth.currentUser!!
        this.db = Firebase.database.reference
    }

    private fun initComponents() {
        this.tielBio = findViewById(R.id.tiel_add_profile_bio)

        setSupportActionBar(findViewById(R.id.toolbar_add_profile_bio))
        initActionBar()

        this.btnAddBio = findViewById(R.id.btn_add_profile_bio_add)
        launchAddProfilePic()
    }

    private fun initActionBar() {
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
    }

    private fun launchAddProfilePic() {
        this.btnAddBio?.setOnClickListener {
            var bio: String = tielBio?.getText().toString().trim()

            this.db.child(Keys.users.name).child(this.user.uid).child(Keys.bio.name).setValue(bio)

            val i = Intent(this@AddProfileBioActivity, AddProfileSuccessActivity::class.java)
            startActivity(i)
        }
    }
}