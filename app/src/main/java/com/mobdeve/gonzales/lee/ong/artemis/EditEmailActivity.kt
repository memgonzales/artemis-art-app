package com.mobdeve.gonzales.lee.ong.artemis

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.EmailAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class EditEmailActivity : AppCompatActivity() {
    private lateinit var tilEditEmail: TextInputLayout
    private lateinit var tietEditEmail: TextInputEditText

    private lateinit var tilNewEmail: TextInputLayout
    private lateinit var tietNewEmail: TextInputEditText

    private lateinit var btnEditEmail: Button

    private lateinit var pbEditEmail: ProgressBar

    //Firebase
    private lateinit var mAuth: FirebaseAuth
    private lateinit var db: DatabaseReference

    private lateinit var user: FirebaseUser
    private lateinit var userId: String
    private lateinit var email: String

    private lateinit var credentials: AuthCredential

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_email)

        initFirebase()
        initComponents()
    }

    private fun initFirebase(){
        this.mAuth = Firebase.auth
        this.db = Firebase.database.reference

        this.user = this.mAuth.currentUser!!
        this.userId = this.user.uid
        this.email = this.user.email!!
    }

    private fun initComponents() {
        this.tietEditEmail = findViewById(R.id.tiet_edit_email)
        tietEditEmail.isFocusable = false

        this.tietEditEmail.setText(email)

        setSupportActionBar(findViewById(R.id.toolbar_edit_email))
        initActionBar()

        this.tilNewEmail = findViewById(R.id.til_edit_email_new)
        this.tietNewEmail = findViewById(R.id.tiet_edit_email_new)
        this.btnEditEmail = findViewById(R.id.btn_edit_email_confirm)

        this.pbEditEmail = findViewById(R.id.pb_edit_email)

        this.btnEditEmail.setOnClickListener {
            val email: String = this.tietNewEmail.text.toString().trim()

            if(validEmail(email)){
                updateEmail(email.lowercase())
            }
        }
    }

    private fun initActionBar() {
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
    }

    private fun validEmail(email: String): Boolean{
        var isValid = true

        if (email.isEmpty()) {
            this.tilNewEmail.error = "Required"
            this.tietNewEmail.requestFocus()
            isValid = false
        }

        else if(!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            this.tilNewEmail.error = "Invalid Email"
            this.tietNewEmail.requestFocus()
            isValid = false
        }

        else {
            tilNewEmail.error = null
        }

        return isValid
    }

    private fun updateEmail(email: String){
        pbEditEmail.visibility = View.VISIBLE

        val userDB = this.db.child(Keys.KEY_DB_USERS.name).child(this.userId)

        userDB.addValueEventListener(object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {

                val e: String = snapshot.child(Keys.email.name).getValue().toString()
                val pw: String = snapshot.child(Keys.password.name).getValue().toString()

                credentials = EmailAuthProvider.getCredential(e, pw)

                user.reauthenticateAndRetrieveData(credentials)
                    .addOnSuccessListener {
                        checkEmail(email)
                    }
                    .addOnFailureListener {
                        updateFailed()
                    }

            }

            override fun onCancelled(error: DatabaseError) {
                pbEditEmail.visibility = View.GONE
                Toast.makeText(applicationContext, "Failed to Access User", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun updateSuccessfully(){
        pbEditEmail.visibility = View.GONE
        Toast.makeText(applicationContext, "Your email have been updated", Toast.LENGTH_SHORT).show()

        val intent = Intent(this@EditEmailActivity, EditProfileActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun updateFailed(){
        pbEditEmail.visibility = View.GONE
        Toast.makeText(applicationContext, "Failed to update your email", Toast.LENGTH_SHORT).show()
    }

    private fun checkEmail(email: String){

        val userDB = this.db.child(Keys.KEY_DB_USERS.name)

        userDB.orderByChild(Keys.email.name).equalTo(email)
            .addListenerForSingleValueEvent(object: ValueEventListener{
                override fun onDataChange(snapshot: DataSnapshot) {

                    if (snapshot.exists()){
                        emailExists()
                    }

                    else{
                        user.updateEmail(email)
                            .addOnSuccessListener {
                                userDB.child(Keys.email.name).setValue(email)
                                updateSuccessfully()

                            }
                            .addOnFailureListener {
                                updateFailed()
                            }
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    pbEditEmail.visibility = View.GONE
                    val intent = Intent(this@EditEmailActivity, BrokenLinkActivity::class.java)
                    startActivity(intent)
                }

            })
    }

    private fun emailExists(){
        this.tilNewEmail.error = "The email was registered before"
        this.tietNewEmail.requestFocus()
    }

}