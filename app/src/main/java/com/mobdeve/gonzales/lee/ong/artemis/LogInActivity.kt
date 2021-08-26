package com.mobdeve.gonzales.lee.ong.artemis

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class LogInActivity : AppCompatActivity() {
    private lateinit var btnSignUp: Button
    private lateinit var btnLogIn: Button
    private lateinit var btnTest: Button

    private lateinit var tietUsername: TextInputEditText
    private lateinit var tietPassword: TextInputEditText

    private lateinit var tvGuest: TextView

    private lateinit var pbLogin: ProgressBar

    //Firebase
    private lateinit var mAuth: FirebaseAuth
    private lateinit var db: DatabaseReference
    //private var customToken: String? = null

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

            var username: String? = tietUsername.text.toString().trim()
            var password: String? = tietPassword.text.toString().trim()

            //Toast.makeText(this, "Working", Toast.LENGTH_SHORT).show()
            /*
            val userDB = this.db.child(Keys.KEY_DB_USERS.name)

            userDB.orderByChild(Keys.KEY_DB_USERNAMES.name).equalTo(username).get()
                .addOnCompleteListener { dataSnapshot ->

                }


             */

            //Toast.makeText(this, "Check: " + usernameLogin, Toast.LENGTH_SHORT).show()
            /*
            customToken?.let{
                this.mAuth.signInWithCustomToken(it).addOnCompleteListener(this){ task ->
                    if (task.isSuccessful){
                        Toast.makeText(this@LogInActivity, "Hurrah", Toast.LENGTH_SHORT).show()
                    }

                    else{
                        Toast.makeText(this@LogInActivity, "Awes", Toast.LENGTH_SHORT).show()
                    }


                }
            }

             */
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

    private fun startTesting() {
        this.btnTest.setOnClickListener {
            val i = Intent(this@LogInActivity, SearchResultsUnregisteredActivity::class.java)
            startActivity(i)
        }
    }
}