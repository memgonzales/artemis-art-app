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

/**
 * Class handling the functionalities related to editing the user's email address.
 *
 * @constructor Creates a class that handles the functionalities related to editing the user's
 * email address.
 */
class EditEmailActivity : AppCompatActivity(), DialogWithInput.DialogWithInputListener {
    /**
     * Text input layout for the old email address.
     */
    private lateinit var tilEditEmail: TextInputLayout

    /**
     * Input field for the old email address.
     */
    private lateinit var tietEditEmail: TextInputEditText

    /**
     * Text input layout for the new email address.
     */
    private lateinit var tilNewEmail: TextInputLayout

    /**
     * Input field for the new email address.
     */
    private lateinit var tietNewEmail: TextInputEditText

    /**
     * Button for saving the new email address.
     */
    private lateinit var btnEditEmail: Button

    /**
     * Progress bar to signal that data are being saved into the database.
     */
    private lateinit var pbEditEmail: ProgressBar

    /**
     * Starting point for Firebase authentication SDK.
     */
    private lateinit var mAuth: FirebaseAuth

    /**
     * Starting point for all database-related operations.
     */
    private lateinit var db: DatabaseReference

    /**
     * Represents a user profile's information in the Firebase user database.
     */
    private lateinit var user: FirebaseUser

    /**
     * Unique identifier of the user.
     */
    private lateinit var userId: String

    /**
     * Email address of the user.
     */
    private lateinit var email: String

    /**
     * New email address entered by the user.
     */
    private lateinit var newEmail: String

    /**
     * Credential that the Firebase Authentication server can use to authenticate the user.
     */
    private lateinit var credentials: AuthCredential

    /**
     * Text view for fetching the password entered by the user in the confirmatory dialog.
     */
    private lateinit var tvEditEmailInputPassword: TextView

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
        setContentView(R.layout.activity_edit_email)

        initFirebase()
        initComponents()
    }

    /**
     * Initializes the Firebase-related components.
     */
    private fun initFirebase(){
        this.mAuth = Firebase.auth
        this.db = Firebase.database.reference

        if (this.mAuth.currentUser != null){
            this.user = this.mAuth.currentUser!!
            this.userId = this.user.uid
            this.email = this.user.email!!
        }

        else{
            val intent = Intent(this@EditEmailActivity, BrokenLinkActivity::class.java)
            startActivity(intent)
        }
    }

    /**
     * Initializes the components of the activity.
     */
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
            this.newEmail = this.tietNewEmail.text.toString().trim()

            if(validEmail(newEmail)){
                editDialog()
                // updateEmail(email.lowercase())
            }
        }
    }

    /**
     * Creates the confirmation dialog when the edit button is clicked.
     */
    private fun editDialog() {
        val passwordDialog = DialogWithInput()
        passwordDialog.show(supportFragmentManager, "Dialog")
    }

    /**
     * Retrieves and returns the password entered by the user in the confirmation dialog.
     *
     * @param password Password entered by the user in the confirmation dialog.
     * @return Password entered by the user, casted as a string.
     */
    override fun fetchPassword(password: String): String {
        tvEditEmailInputPassword = findViewById(R.id.tv_edit_email_input_password)
        tvEditEmailInputPassword.text = password

        if (!password.isNullOrEmpty()){
            updateEmail(newEmail.lowercase(), password)
        }

        else{
            Toast.makeText(applicationContext, "Please input your password", Toast.LENGTH_SHORT).show()
        }


        return tvEditEmailInputPassword.text as String
    }

    /**
     * Adds a back button to the action bar.
     */
    private fun initActionBar() {
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
    }

    /**
     * Checks if the given string is a valid email address.
     *
     * @param email string to be checked for validity as an email address
     * @return <code>true</code> if the given string is a valid email address; <code>false</code>,
     * otherwise.
     */
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

    /**
     * Saves the new email address in the remote database, effectively replacing the old email
     * address of the user.
     *
     * @param email New email address of the user.
     */
    private fun updateEmail(email: String, pw: String){
        pbEditEmail.visibility = View.VISIBLE

        credentials = EmailAuthProvider.getCredential(this.email, pw)

        user.reauthenticate(credentials)
            .addOnSuccessListener {
                checkEmail(email)
            }
            .addOnFailureListener {
                updateFailed()
            }
    }

    /**
     * Defines the behavior when the email address is successfully edited.
     */
    private fun updateSuccessfully(){
        pbEditEmail.visibility = View.GONE
        Toast.makeText(applicationContext, "Your email has been updated", Toast.LENGTH_SHORT).show()

        val intent = Intent(this@EditEmailActivity, EditProfileActivity::class.java)
        startActivity(intent)
        finish()
    }

    /**
     * Defines the behavior when the email address is not successfully edited.
     */
    private fun updateFailed(){
        pbEditEmail.visibility = View.GONE
        Toast.makeText(applicationContext, "Failed to update your email", Toast.LENGTH_SHORT).show()
    }

    /**
     * Checks if the given email address is already registered in the remote database.
     *
     * @param email Email address to be checked.
     */
    private fun checkEmail(email: String){
        val userDB = this.db.child(Keys.KEY_DB_USERS.name)

        userDB.orderByChild(Keys.email.name).equalTo(email)
            .addListenerForSingleValueEvent(object: ValueEventListener{
                override fun onDataChange(snapshot: DataSnapshot) {

                    if (snapshot.exists()){
                        emailExists()
                        updateFailed()
                    }

                    else{
                        userDB.child(userId).child(Keys.email.name).setValue(email)
                            .addOnSuccessListener {
                                user.updateEmail(email)
                                    .addOnSuccessListener { updateSuccessfully() }
                                    .addOnFailureListener { updateFailed() }
                            }
                            .addOnFailureListener {
                                Toast.makeText(applicationContext, "Invalid password", Toast.LENGTH_SHORT).show()
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

    /**
     * Defines the behavior when the user entered an already-existing email address.
     */
    private fun emailExists(){
        this.tilNewEmail.error = "The email was registered before"
        this.tietNewEmail.requestFocus()
    }
}