package com.mobdeve.gonzales.lee.ong.artemis

import android.content.Intent
import android.os.Bundle
import android.text.method.LinkMovementMethod
import android.view.View
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import at.favre.lib.crypto.bcrypt.BCrypt
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
    private lateinit var tietConfirmPassword: TextInputEditText

    private lateinit var tilUsername: TextInputLayout
    private lateinit var tilEmail: TextInputLayout
    private lateinit var tilPassword: TextInputLayout
    private lateinit var tilConfirmPassword: TextInputLayout

    private lateinit var pbSignUp: ProgressBar

    private lateinit var tvSignUpPrivacy: TextView

    //Firebase - related
    private lateinit var mAuth: FirebaseAuth
    private lateinit var db: FirebaseDatabase

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
        setContentView(R.layout.activity_sign_up)

        initComponents()
        initFirebase()
        initLink()
    }

    private fun initLink() {
        tvSignUpPrivacy = findViewById(R.id.tv_sign_up_privacy)
        tvSignUpPrivacy.movementMethod = LinkMovementMethod.getInstance()
    }

    /**
     * Initializes the Firebase-related components.
     */
    private fun initFirebase() {
        this.mAuth = Firebase.auth
        this.db = Firebase.database
    }

    /**
     * Initializes the components of the activity.
     */
    private fun initComponents() {
        this.tietUsername = findViewById(R.id.tiet_sign_up_username)
        this.tietEmail = findViewById(R.id.tiet_sign_up_email)
        this.tietPassword = findViewById(R.id.tiet_sign_up_password)
        this.tietConfirmPassword = findViewById(R.id.tiet_sign_up_confirm_password)

        this.tilUsername = findViewById(R.id.til_sign_up_username)
        this.tilEmail = findViewById(R.id.til_sign_up_email)
        this.tilPassword = findViewById(R.id.til_sign_up_password)
        this.tilConfirmPassword = findViewById(R.id.til_sign_up_confirm_password)

        this.pbSignUp = findViewById(R.id.pb_sign_up)
        this.btnSignUp = findViewById(R.id.btn_sign_up)

        setSupportActionBar(findViewById(R.id.toolbar_sign_up))
        initActionBar()

        launchAddProfilePic()
    }

    /**
     * Adds a back button to the action bar.
     */
    private fun initActionBar() {
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
    }

    private fun launchAddProfilePic() {

        this.btnSignUp.setOnClickListener {

                val username: String = tietUsername.text.toString().trim()
                val email: String = tietEmail.text.toString().trim()
                val password: String = tietPassword.text.toString().trim()
                val confirmPw: String = tietConfirmPassword.text.toString().trim()

                if(validCredentials(username, email, password, confirmPw)){
                    signUp(username, email.lowercase(), password)
                }
        }
    }

    private fun validCredentials(username: String, email: String, password: String, confirmPw: String): Boolean{
        var isValid = true

        when {
            username.isEmpty() -> {
                this.tilUsername.error = "Required"
                this.tietUsername.requestFocus()
                isValid = false
            }
            username.length < 4 -> {
                this.tilUsername.error = "Username should have at least 4 characters"
                this.tietUsername.requestFocus()
                isValid = false
            }
            else -> {
                this.tilUsername.error = null
            }
        }



        if(email.isEmpty()) {
            this.tilEmail.error = "Required"
            this.tietEmail.requestFocus()
            isValid = false
        }

        else if(!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            this.tilEmail.error = "Invalid email address"
            this.tietEmail.requestFocus()
            isValid = false
        }

        else{
            this.tilEmail.error = null
        }



        when {
            password.isEmpty() -> {
                this.tilPassword.error = "Required"
                this.tietPassword.requestFocus()
                isValid = false
            }
            password.length < 6 -> {
                this.tilPassword.error = "Password should have at least 6 characters"
                this.tietPassword.requestFocus()
                isValid = false
            }
            else -> {
                this.tilPassword.error = null
            }
        }



        when {
            confirmPw.isEmpty() -> {
                this.tilConfirmPassword.error = "Required"
                this.tietConfirmPassword.requestFocus()
                isValid = false
            }
            password != confirmPw -> {
                this.tilConfirmPassword.error = "Passwords do not match"
                this.tietConfirmPassword.requestFocus()
                isValid = false
            }
            else -> {
                this.tilConfirmPassword.error = null
            }
        }


        return isValid
    }

    private fun signUp(username: String, email: String, password: String){

        val userDB = this.db.reference.child(Keys.KEY_DB_USERS.name)

        userDB.orderByChild(Keys.username.name).equalTo(username)
            .addListenerForSingleValueEvent(object: ValueEventListener{
                override fun onDataChange(snapshot: DataSnapshot) {

                    if (snapshot.exists()){
                        usernameExists()
                        checkEmail(true, email, username, password)
                        failedRegistration()
                    }

                    else{
                        checkEmail(false, email, username, password)
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    pbSignUp.visibility = View.GONE
                    val intent = Intent(this@SignUpActivity, BrokenLinkActivity::class.java)
                    startActivity(intent)}

            })
    }

    private fun checkEmail(userExists: Boolean, email: String, username: String, password: String){

        val userDB = this.db.reference.child(Keys.KEY_DB_USERS.name)

        userDB.orderByChild(Keys.email.name).equalTo(email)
            .addListenerForSingleValueEvent(object: ValueEventListener{
                override fun onDataChange(snapshot: DataSnapshot) {

                    if (snapshot.exists()){
                        emailExists()
                    }

                    else{
                        if (!userExists){
                            val user = User(username, email, password)
                            storeUser(user)
                        }
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    pbSignUp.visibility = View.GONE
                    val intent = Intent(this@SignUpActivity, BrokenLinkActivity::class.java)
                    startActivity(intent)
                }

            })
    }

    private fun usernameExists(){
        this.tilUsername.error = "Username has been already been taken"
        this.tietUsername.requestFocus()
    }

    private fun emailExists(){
        this.tilEmail.error = "The email was registered before"
        this.tietEmail.requestFocus()
    }

    private fun storeUser(user: User) {
        this.pbSignUp.visibility = View.VISIBLE

        this.mAuth.createUserWithEmailAndPassword(user.getEmail()!!, user.getPassword()!!)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    user.setUserId(mAuth.currentUser!!.uid)
                    val pwHash = BCrypt.withDefaults().hashToString(12, user.getPassword()?.toCharArray())
                    user.setPassword(pwHash)

                    db.getReference(Keys.KEY_DB_USERS.name)
                        .child(mAuth.currentUser!!.uid)
                        .setValue(user).addOnCompleteListener { task ->
                            if (task.isSuccessful) {
                                successfulRegistration()
                            }

                            else {
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