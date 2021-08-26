package com.mobdeve.gonzales.lee.ong.artemis

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.Button
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class SignUpActivity : AppCompatActivity() {
    private lateinit var btnSignUp: Button

    private lateinit var tietUsername: TextInputEditText
    private lateinit var tietEmail: TextInputEditText
    private lateinit var tietPassword: TextInputEditText

    private lateinit var tilUsername: TextInputLayout
    private lateinit var tilEmail: TextInputLayout
    private lateinit var tilPassword: TextInputLayout

    private lateinit var pbSignUp: ProgressBar

    //Firebase - related
    private lateinit var mAuth: FirebaseAuth
    private lateinit var db: FirebaseDatabase

    private var usernameExists: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        initComponents()
        initFirebase()
    }

    private fun initFirebase() {
        this.mAuth = Firebase.auth
        this.db = Firebase.database
    }


    private fun initComponents() {
        this.tietUsername = findViewById(R.id.tiet_sign_up_username)
        this.tietEmail = findViewById(R.id.tiet_sign_up_email)
        this.tietPassword = findViewById(R.id.tiet_sign_up_password)

        this.tilUsername = findViewById(R.id.til_sign_up_username)
        this.tilEmail = findViewById(R.id.til_sign_up_email)
        this.tilPassword = findViewById(R.id.til_sign_up_password)

        this.pbSignUp = findViewById(R.id.pb_sign_up)
        this.btnSignUp = findViewById(R.id.btn_sign_up)

        setSupportActionBar(findViewById(R.id.toolbar_sign_up))
        initActionBar()

        launchAddProfilePic()
    }

    private fun initActionBar() {
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
    }

    private fun launchAddProfilePic() {

        this.btnSignUp.setOnClickListener {

                val username: String = tietUsername.text.toString().trim()
                val email: String = tietEmail.text.toString().trim()
                val password: String = tietPassword.text.toString().trim()

                if(validCredentials(username, email, password)){
                    checkUser(username)
                    //var user: User = User(username, email, password)
                    //storeUser(user)
                }
        }
    }

    private fun validCredentials(username: String, email: String, password: String): Boolean {
        var isValid: Boolean = true;

        if(username.isEmpty()) {
            this.tilUsername.error = "Required"
            this.tietUsername.requestFocus()
            isValid = false
        }

        if (!username.isEmpty() && username.length < 4){
            this.tilUsername.error = "Username should have at least 4 characters"
            this.tietUsername.requestFocus()
            isValid = false
        }


        /*
        if(!username.isEmpty() ){
            usernameExists(username)
            this.tilUsername.error = "Username has been already been taken"
            this.tietUsername.requestFocus()
            isValid = false
        }

         */

        if(email.isEmpty()) {
            this.tilEmail.error = "Required"
            this.tietEmail.requestFocus()
            isValid = false
        }

        if(!email.isEmpty() && !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            this.tilEmail.error = "Invalid email address"
            this.tietEmail.requestFocus()
            isValid = false
        }

        if(password.isEmpty()) {
            this.tilPassword.error = "Required"
            this.tietPassword.requestFocus()
            isValid = false
        }

        if(!password.isEmpty() && password.length < 6){
            this.tilPassword.error = "Password should have at least 6 characters"
            this.tietPassword.requestFocus()
            isValid = false
        }

        return isValid
    }

    private fun checkUser(username: String){

        var userDB = this.db.reference.child(Keys.KEY_DB_USERS.name)

        userDB.orderByChild(Keys.username.name).equalTo(username)
            .addListenerForSingleValueEvent(object: ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {

                if (snapshot.childrenCount  > 0){
                    usernameExists = true
                }

                else{

                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })

    }

    private fun storeUser(user: User) {
        this.pbSignUp.visibility = View.VISIBLE

        this.mAuth.createUserWithEmailAndPassword(user.getEmail(), user.getPassword())
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    db.getReference(Keys.KEY_DB_USERS.name)
                        .child(mAuth.currentUser!!.uid)
                        .setValue(user).addOnCompleteListener { task ->
                            if (task.isSuccessful) {
                                successfulRegistration()
                            } else {
                                failedRegistration()
                            }
                        }

                }

                else {
                    failedRegistration()
                }
            }
    }

    private fun successfulRegistration() {
        this.pbSignUp.visibility = View.GONE
        Toast.makeText(this, "Successfully registered", Toast.LENGTH_SHORT).show()

        val i = Intent(this@SignUpActivity, AddProfilePictureActivity::class.java)
        startActivity(i)
        finish()
    }

    private fun failedRegistration() {
        this.pbSignUp.visibility = View.GONE
        Toast.makeText(this, "Registration failed", Toast.LENGTH_SHORT).show()
    }
}