package com.mobdeve.gonzales.lee.ong.artemis

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.annotation.NonNull
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class SignUpActivity : AppCompatActivity() {
    private var btnSignUp: Button? = null

    private var tietUsername: TextInputEditText? = null
    private var tietEmail: TextInputEditText? = null
    private var tietPassword: TextInputEditText? = null

    //Firebase - related
    //private lateinit var mAuth: FirebaseAuth
    //private lateinit var db: FirebaseDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        initComponents()
      //  initFirebase()
    }

    /*
    private fun initFirebase(){
        this.mAuth = Firebase.auth
        this.db = Firebase.database
    }
     */

    private fun initComponents() {
        this.tietUsername = findViewById(R.id.tiet_sign_up_username)
        this.tietEmail = findViewById(R.id.tiet_sign_up_email)
        this.tietPassword = findViewById(R.id.tiet_sign_up_password)

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

        this.btnSignUp?.setOnClickListener {
            this.btnSignUp?.setOnClickListener(View.OnClickListener {
                var username: String = tietUsername?.getText().toString().trim()
                var email: String = tietEmail?.getText().toString().trim()
                var password: String = tietPassword?.getText().toString().trim()

                if(!checkEmpty(username, email, password)){

                    //var user: User = User(username, email, password)
                    //storeUser(user)


                    val i = Intent(this@SignUpActivity, AddProfilePictureActivity::class.java)
                    startActivity(i)
                    finish()


                }
            })

        }
    }

    private fun checkEmpty(username: String, email: String, password: String): Boolean{
        var hasEmpty: Boolean = false;

        if(username.isEmpty()){
            this.tietUsername?.setError("Required Field")
            this.tietUsername?.requestFocus()
            hasEmpty = true
        }

        if(email.isEmpty()){
            this.tietEmail?.setError("Required Field")
            this.tietEmail?.requestFocus()
            hasEmpty = true
        }

        if(password.isEmpty()){
            this.tietPassword?.setError("Required Field")
            this.tietPassword?.requestFocus()
            hasEmpty = true
        }

        return hasEmpty
    }

    /*
    private fun storeUser(user: User){
        this.mAuth.createUserWithEmailAndPassword(user.getUsername(), user.getPassword()).addOnCompleteListener(
            this, OnCompleteListener { task -> if (task.isSuccessful){ successfulRegistration()} else{failedRegistration()} })
    }

    private fun successfulRegistration(){
        Toast.makeText(this, "Succesfully Registered", Toast.LENGTH_SHORT).show()
    }

    private fun failedRegistration(){
        Toast.makeText(this, "Registration failed", Toast.LENGTH_SHORT).show()
    }

     */
}