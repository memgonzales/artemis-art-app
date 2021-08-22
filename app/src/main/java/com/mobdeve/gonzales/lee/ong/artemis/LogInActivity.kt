package com.mobdeve.gonzales.lee.ong.artemis

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.app.AppCompatDelegate.MODE_NIGHT_NO
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class LogInActivity : AppCompatActivity() {
    private var btnSignUp: Button? = null
    private var btnLogIn: Button? = null
    private var btnTest: Button? = null

    private var tietUsername: TextInputEditText? = null
    private var tietPassword: TextInputEditText? = null

    private var tvGuest: TextView? = null

    //Firebase
    private lateinit var mAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_log_in)

        initFirebase()
        initComponents()
    }

    private fun initFirebase(){
        this.mAuth = Firebase.auth
    }

    private fun initComponents() {
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
        this.btnSignUp?.setOnClickListener {
            val i = Intent(this@LogInActivity, SignUpActivity::class.java)
            startActivity(i)
        }
    }

    private fun startBrowsing() {
        this.btnLogIn?.setOnClickListener {
            var username: String? = tietUsername?.getText().toString().trim()
            var password: String? = tietPassword?.getText().toString().trim()

            this.mAuth.signInWithCustomToken(it).addOnCompleteListener(this)
            /*
            auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        // Sign in success, update UI with the signed-in user's information
                        Log.d(TAG, "signInWithEmail:success")
                        val user = auth.currentUser
                        updateUI(user)
                    } else {
                        // If sign in fails, display a message to the user.
                        Log.w(TAG, "signInWithEmail:failure", task.exception)
                        Toast.makeText(baseContext, "Authentication failed.",
                            Toast.LENGTH_SHORT).show()
                        updateUI(null)
                    }
                }
*/
            val i = Intent(this@LogInActivity, BrowseFeedActivity::class.java)
            startActivity(i)
            finish()
        }
    }

    private fun startTesting() {
        this.btnTest?.setOnClickListener {
            val i = Intent(this@LogInActivity, ViewCommentsActivity::class.java)
            startActivity(i)
        }
    }

    private fun loginAsGuest(){
        this.tvGuest?.setOnClickListener {

           // this.mAuth.signInAnonymously()
            val i = Intent(this@LogInActivity, BrowseFeedActivity::class.java)
            startActivity(i)
            finish()
        }
    }
}