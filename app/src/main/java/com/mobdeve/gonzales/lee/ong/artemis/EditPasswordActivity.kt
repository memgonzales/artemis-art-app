package com.mobdeve.gonzales.lee.ong.artemis

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ProgressBar
import android.widget.Toast
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

class EditPasswordActivity : AppCompatActivity() {

    private lateinit var tilEditPw: TextInputLayout
    private lateinit var tietEditPw: TextInputEditText

    private lateinit var tilNewPw: TextInputLayout
    private lateinit var tietNewPw: TextInputEditText

    private lateinit var tilConfirmPw: TextInputLayout
    private lateinit var tietConfirmPw: TextInputEditText

    private lateinit var btnEditPw: Button

    private lateinit var pbEditPw: ProgressBar

    //Firebase
    private lateinit var mAuth: FirebaseAuth
    private lateinit var db: DatabaseReference

    private lateinit var user: FirebaseUser
    private lateinit var userId: String

    private lateinit var credentials: AuthCredential

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_password)

        initFirebase()
        initComponents()
    }

    private fun initFirebase(){
        this.mAuth = Firebase.auth
        this.db = Firebase.database.reference

        this.user = this.mAuth.currentUser!!
        this.userId = this.user.uid
    }

    private fun initComponents() {
        setSupportActionBar(findViewById(R.id.toolbar_edit_password))
        initActionBar()

        this.tilEditPw = findViewById(R.id.til_edit_password)
        this.tietEditPw = findViewById(R.id.tiet_edit_password)

        this.tilNewPw = findViewById(R.id.til_edit_password_new)
        this.tietNewPw = findViewById(R.id.tiet_edit_password_new)

        this.tilConfirmPw = findViewById(R.id.til_edit_password_new_confirm)
        this.tietConfirmPw = findViewById(R.id.tiet_edit_password_new_confirm)

        this.btnEditPw = findViewById(R.id.btn_edit_password_confirm)

        this.pbEditPw = findViewById(R.id.pb_edit_password)

        this.btnEditPw.setOnClickListener {
            val pw: String = this.tietEditPw.text.toString().trim()
            val newPw: String = this.tietNewPw.text.toString().trim()
            val confirmPw: String = this.tietConfirmPw.text.toString().trim()

            if (validPassword(pw, newPw, confirmPw)){
                updatePassword(pw, newPw)
            }
        }
    }

    private fun initActionBar() {
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
    }

    private fun validPassword(pw: String, newPw: String, confirmPw: String): Boolean{
        var isValid = true

        when {
            pw.isEmpty() -> {
                this.tilEditPw.error = "Required"
                isValid = false
            }
            pw.length < 6 -> {
                this.tilEditPw.error = "Password should have at least 6 characters"
                this.tietEditPw.requestFocus()
                isValid = false
            }
            else -> {
                this.tilEditPw.error = null
            }
        }



        when {
            newPw.isEmpty() -> {
                this.tilNewPw.error = "Required"
                this.tietNewPw.requestFocus()
                isValid = false
            }
            newPw.length < 6 -> {
                this.tilNewPw.error = "Password should have at least 6 characters"
                this.tietNewPw.requestFocus()
                isValid = false
            }
            else -> {
                this.tilNewPw.error = null
            }
        }



        when {
            confirmPw.isEmpty() -> {
                this.tilConfirmPw.error = "Required"
                this.tietConfirmPw.requestFocus()
                isValid = false
            }
            newPw != confirmPw -> {
                this.tilConfirmPw.error = "Password do not match with your new password"
                this.tietConfirmPw.requestFocus()
                isValid = false
            }
            else -> {
                this.tilConfirmPw.error = null
            }
        }

        return isValid
    }

    private fun updatePassword(oldPw: String, newPw: String){
        pbEditPw.visibility = View.VISIBLE

        val userDB = this.db.child(Keys.KEY_DB_USERS.name).child(this.userId)

        userDB.addValueEventListener(object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {

                val e: String = snapshot.child(Keys.email.name).getValue().toString()
                val pw: String = snapshot.child(Keys.password.name).getValue().toString()

                credentials = EmailAuthProvider.getCredential(e, pw)

                if (!oldPw.equals(pw)){
                    invalidOldPw()
                }

                else{
                    user.reauthenticateAndRetrieveData(credentials)
                        .addOnSuccessListener {
                            user.updatePassword(newPw)
                                .addOnSuccessListener {
                                    userDB.child(Keys.password.name).setValue(newPw)
                                    updateSuccessfully()

                                }
                                .addOnFailureListener {
                                    updateFailed()
                                }
                        }
                        .addOnFailureListener {
                            updateFailed()
                        }
                }
            }

            override fun onCancelled(error: DatabaseError) {
                pbEditPw.visibility = View.GONE
                val intent = Intent(this@EditPasswordActivity, BrokenLinkActivity::class.java)
                startActivity(intent)
            }
        })
    }

    private fun updateSuccessfully(){
        pbEditPw.visibility = View.GONE
        Toast.makeText(applicationContext, "Your password have been updated", Toast.LENGTH_SHORT).show()

        val intent = Intent(this@EditPasswordActivity, EditProfileActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun updateFailed(){
        pbEditPw.visibility = View.GONE
        Toast.makeText(applicationContext, "Failed to update your password", Toast.LENGTH_SHORT).show()
    }

    private fun invalidOldPw(){
        pbEditPw.visibility = View.GONE
        Toast.makeText(this@EditPasswordActivity, "Invalid Old Password", Toast.LENGTH_SHORT).show()
    }

}