package com.mobdeve.gonzales.lee.ong.artemis

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.preference.PreferenceManager
import android.view.View
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.*
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class LogInActivity : AppCompatActivity() {
    private lateinit var btnSignUp: Button
    private lateinit var btnLogIn: Button
    private lateinit var btnTest: Button

    private lateinit var tietUsername: TextInputEditText
    private lateinit var tietPassword: TextInputEditText

    private lateinit var tilUsername: TextInputLayout
    private lateinit var tilPassword: TextInputLayout

    private lateinit var tvGuest: TextView

    private lateinit var pbLogin: ProgressBar

    //Firebase
    private lateinit var mAuth: FirebaseAuth
    private lateinit var db: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_log_in)

        initFirebase()
        initComponents()
    }

    private fun initFirebase(){
        this.mAuth = Firebase.auth
        this.db = Firebase.database.reference
    }

    private fun initComponents() {
        this.pbLogin = findViewById(R.id.pb_log_in)

        this.btnSignUp = findViewById(R.id.btn_log_in_sign_up)
        this.launchSignUp()

        this.btnLogIn = findViewById(R.id.btn_log_in)
        startBrowsing()

        this.btnTest = findViewById(R.id.btn_test)
        startTesting()

        this.tvGuest = findViewById(R.id.tv_log_in_guest)
        loginAsGuest()
    }

    private fun launchSignUp() {
        this.btnSignUp.setOnClickListener {
            val i = Intent(this@LogInActivity, SignUpActivity::class.java)
            startActivity(i)
        }
    }

    private fun startBrowsing() {
        this.btnLogIn.setOnClickListener {
            this.tietUsername = findViewById(R.id.tiet_log_in_username)
            this.tietPassword = findViewById(R.id.tiet_log_in_password)

            this.tilUsername = findViewById(R.id.til_log_in_username)
            this.tilPassword = findViewById(R.id.til_log_in_password)

            val username: String = tietUsername.text.toString().trim()
            val password: String = tietPassword.text.toString().trim()

            if (!checkEmpty(username, password)){
                this.pbLogin.visibility = View.VISIBLE

                if (android.util.Patterns.EMAIL_ADDRESS.matcher(username).matches()){
                    loginWithEmail(username, password)
                }

                else{
                    loginWithUsername(username, password)
                }
            }
        }
    }

    private fun checkEmpty(username: String, password: String): Boolean{
        var hasEmpty = false

        if (username.isEmpty()){
            this.tilUsername.error = "Required"
            this.tietUsername.requestFocus()
            hasEmpty = true
        }

        else{
            this.tilUsername.error = null
        }

        if(password.isEmpty()){
            this.tilPassword.error = "Required"
            this.tietPassword.requestFocus()
            hasEmpty = true
        }

        else{
            this.tilPassword.error = null
        }

        return hasEmpty
    }

    private fun loginWithEmail(email: String, password: String){
        this.mAuth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful){
                    loginSuccessfully()
                }

                else{
                    loginFailed()
                }
            }
    }

    private fun loginWithUsername(username: String, password: String){
        val userDB = this.db.child(Keys.KEY_DB_USERS.name)

        userDB.orderByChild(Keys.username.name).equalTo(username)
            .addListenerForSingleValueEvent(object: ValueEventListener{
                override fun onDataChange(dataSnapshot: DataSnapshot){

                    if(dataSnapshot.exists()) {
                        for (userSnap in dataSnapshot.children) {

                            val email = dataSnapshot.child(userSnap.key!!).child(Keys.email.name).getValue().toString()
                            loginWithEmail(email, password)

                        }
                    }

                    else{
                        loginFailed()
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    loginFailed()
                }
            })
    }

    private fun loginSuccessfully(){
        this.pbLogin.visibility = View.GONE
        Toast.makeText(this@LogInActivity, "Logged in successfully", Toast.LENGTH_SHORT).show()
        val i = Intent(this@LogInActivity, BrowseFeedActivity::class.java)
        startActivity(i)
        finish()
    }

    private fun loginFailed(){
        this.pbLogin.visibility = View.GONE
        Toast.makeText(this@LogInActivity, "Invalid credentials", Toast.LENGTH_SHORT).show()
    }

    private fun loginAsGuest(){
        this.tvGuest.setOnClickListener {
            this.pbLogin.visibility = View.VISIBLE

            this.mAuth.signInAnonymously().addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    this.pbLogin.visibility = View.GONE
                    Toast.makeText(this@LogInActivity, "Successfully signed in as guest", Toast.LENGTH_SHORT).show()

                    val i = Intent(this@LogInActivity, BrowseFeedUnregisteredActivity::class.java)
                    startActivity(i)
                }

                else {
                    this.pbLogin.visibility = View.GONE
                    Toast.makeText(this@LogInActivity, "Unable to sign in as guest", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    override fun onStart() {
        super.onStart()

        if(this.mAuth.currentUser != null){
            val i = Intent(this@LogInActivity, BrowseFeedActivity::class.java)
            startActivity(i)
            finish()

        }
    }

    private fun startTesting() {
        this.btnTest.setOnClickListener {
            val i = Intent(this@LogInActivity, AddProfilePictureActivity::class.java)
            startActivity(i)
        }
    }
}