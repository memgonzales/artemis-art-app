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
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.*
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

/**
 * Class handling the functionalities related to logging in.
 *
 * @constructor Creates a class that handles the functionalities related to logging in.
 */
class LogInActivity : AppCompatActivity() {

    private lateinit var btnTest: Button

    /**
     * Button for creating a user account.
     */
    private lateinit var btnSignUp: Button

    /**
     * Button for logging in.
     */
    private lateinit var btnLogIn: Button

    /**
     * Input field for the username.
     */
    private lateinit var tietUsername: TextInputEditText

    /**
     * Input field for the password.
     */
    private lateinit var tietPassword: TextInputEditText

    /**
     * Text input layout for the username.
     */
    private lateinit var tilUsername: TextInputLayout

    /**
     * Text input layout for the password.
     */
    private lateinit var tilPassword: TextInputLayout

    /**
     * Clickable text view for entering the app as an unregistered user (that is, via a guest account).
     */
    private lateinit var tvGuest: TextView

    /**
     * Progress bar to signal that data are being fetched from the database.
     */
    private lateinit var pbLogin: ProgressBar

    /**
     * Starting point for Firebase authentication SDK.
     */
    private lateinit var mAuth: FirebaseAuth

    /**
     * Starting point for all database-related operations.
     */
    private lateinit var db: DatabaseReference

    /**
     * Called when the activity is starting.
     *
     * @param savedInstanceState  If the activity is being re-initialized after previously being
     * shut down then this Bundle contains the data it most recently supplied in
     * <code>onSaveInstanceState(Bundle)</code>. Note: Otherwise it is <code>null</code>.
     * This value may be <code>null</code>.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_log_in)

        initFirebase()
        initComponents()
    }

    /**
     * Initializes the Firebase-related components.
     */
    private fun initFirebase(){
        this.mAuth = Firebase.auth
        this.db = Firebase.database.reference
    }

    /**
     * Initializes the components of the activity.
     */
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

    /**
     * Redirects the user to the activity for signing up.
     */
    private fun launchSignUp() {
        this.btnSignUp.setOnClickListener {
            val i = Intent(this@LogInActivity, SignUpActivity::class.java)
            startActivity(i)
        }
    }

    /**
     * Redirects the user to the activity for browsing the feed, provided that they
     * entered valid log-in credentials (namely, the username or email address
     * and the password).
     */
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

    /**
     * Checks if the user entered both a username (or email address) and a password.
     *
     * @param username value entered in the input field for the username (or email address)
     * @param password value entered in the input field for the password.
     *
     * @return <code>false</code> if the user entered both a username (or email address) and
     * a password; <code>true</code>, otherwise
     */
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

    /**
     * Verifies the log-in credentials given the email address and password the user entered.
     *
     * This function uses the built-in email and password authentication method of Firebase.
     *
     * @param email email address that the user entered
     * @param password password that the user entered
     */
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

    /**
     * Verifies the log-in credentials given the username and password the user entered.
     *
     * @param username username that the user entered
     * @param password password that the user entered
     */
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
                    val intent = Intent(this@LogInActivity, BrokenLinkActivity::class.java)
                    startActivity(intent)
                }
            })
    }

    /**
     * Defines the behavior when the user is successfully logged in, that is, by entering valid
     * credentials.
     */
    private fun loginSuccessfully(){
        this.pbLogin.visibility = View.GONE
        Toast.makeText(this@LogInActivity, "Logged in successfully", Toast.LENGTH_SHORT).show()
        val i = Intent(this@LogInActivity, BrowseFeedActivity::class.java)
        startActivity(i)
        finish()
    }

    /**
     * Defines the behavior when the user inputs invalid credentials.
     */
    private fun loginFailed(){
        this.pbLogin.visibility = View.GONE
        Toast.makeText(this@LogInActivity, "Invalid credentials", Toast.LENGTH_SHORT).show()
    }

    /**
     * Defines the behavior when the user chooses to enter the app as an unregistered user
     * (that is, via a guest account).
     */
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

    /**
     * Called when the activity is becoming visible to the user.
     */
    override fun onStart() {
        super.onStart()

        if(this.mAuth.currentUser != null){

            if (!this.mAuth.currentUser!!.email.isNullOrEmpty()){
                val i = Intent(this@LogInActivity, BrowseFeedActivity::class.java)
                startActivity(i)
                finish()
            }

        }
    }


    private fun startTesting() {
        this.btnTest.setOnClickListener {
            val i = Intent(this@LogInActivity, BrokenLinkActivity::class.java)
            startActivity(i)
        }
    }
}