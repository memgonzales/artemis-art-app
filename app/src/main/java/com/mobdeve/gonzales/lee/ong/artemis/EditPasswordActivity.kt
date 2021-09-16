package com.mobdeve.gonzales.lee.ong.artemis

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import at.favre.lib.crypto.bcrypt.BCrypt
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
 * Class handling the functionalities related to editing the user's password.
 *
 * @constructor Creates a class that handles the functionalities related to editing the user's
 * password.
 */
class EditPasswordActivity : AppCompatActivity() {
    /**
     * Text input layout for the old password.
     */
    private lateinit var tilEditPw: TextInputLayout

    /**
     * Input field for the old password.
     */
    private lateinit var tietEditPw: TextInputEditText

    /**
     * Text input layout for the new password.
     */
    private lateinit var tilNewPw: TextInputLayout

    /**
     * Input field for the new password.
     */
    private lateinit var tietNewPw: TextInputEditText

    /**
     * Text input layout for confirming the new password.
     */
    private lateinit var tilConfirmPw: TextInputLayout

    /**
     * Input field for confirming the new password.
     */
    private lateinit var tietConfirmPw: TextInputEditText

    /**
     * Button for saving the new password.
     */
    private lateinit var btnEditPw: Button

    /**
     * Progress bar to signal that data are being saved into the database.
     */
    private lateinit var pbEditPw: ProgressBar

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
     * Credential that the Firebase Authentication server can use to authenticate the user.
     */
    private lateinit var credentials: AuthCredential

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
        setContentView(R.layout.activity_edit_password)

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
        }

        else{
            val intent = Intent(this@EditPasswordActivity, BrokenLinkActivity::class.java)
            startActivity(intent)
        }
    }

    /**
     * Initializes the components of the activity.
     */
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

    /**
     * Adds a back button to the action bar.
     */
    private fun initActionBar() {
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
    }

    /**
     * Checks whether the user entered an old password (although verifying if the input is correct
     * is delegated to a separate method) and whether the new password and its confirmation match.
     *
     * @param pw Old password of the user.
     * @param newPw New password of the user.
     * @param confirmPw Confirmation of the new password of the user.
     */
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

    /**
     * Saves the new password in the remote database, effectively replacing the old password
     * of the user.
     *
     * @param oldPw Old password of the user.
     * @param newPw New password of the user.
     */
    private fun updatePassword(oldPw: String, newPw: String){
        pbEditPw.visibility = View.VISIBLE

        val userDB = this.db.child(Keys.KEY_DB_USERS.name).child(this.userId)

        userDB.addValueEventListener(object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {

                val e: String = snapshot.child(Keys.email.name).getValue().toString()
                val hash: String = snapshot.child(Keys.password.name).getValue().toString()

                val result = BCrypt.verifyer().verify(oldPw.toCharArray(), hash)

                if (result.verified){

                    credentials = EmailAuthProvider.getCredential(e, oldPw)
                    user.reauthenticateAndRetrieveData(credentials)
                        .addOnSuccessListener {
                            val pwHash = BCrypt.withDefaults().hashToString(12, newPw.toCharArray())
                            userDB.child(Keys.password.name).setValue(pwHash)
                                .addOnSuccessListener {

                                    user.updatePassword(newPw)
                                        .addOnSuccessListener { updateSuccessfully() }
                                        .addOnFailureListener { updateFailed() }

                                }
                                .addOnFailureListener {
                                    updateFailed()
                                }
                        }
                        .addOnFailureListener {
                            updateFailed()
                        }
                }

                else{
                    invalidOldPw()
                }
            }

            override fun onCancelled(error: DatabaseError) {
                pbEditPw.visibility = View.GONE
                val intent = Intent(this@EditPasswordActivity, BrokenLinkActivity::class.java)
                startActivity(intent)
            }
        })
    }

    /**
     * Defines the behavior when the password is successfully edited.
     */
    private fun updateSuccessfully(){
        pbEditPw.visibility = View.GONE
        Toast.makeText(applicationContext, "Your password have been updated", Toast.LENGTH_SHORT).show()

        val intent = Intent(this@EditPasswordActivity, EditProfileActivity::class.java)
        startActivity(intent)
        finish()
    }

    /**
     * Defines the behavior when the password is not successfully edited.
     */
    private fun updateFailed(){
        pbEditPw.visibility = View.GONE
        Toast.makeText(applicationContext, "Failed to update your password", Toast.LENGTH_SHORT).show()
    }

    /**
     * Defines the behavior when the user entered an incorrect old password.
     */
    private fun invalidOldPw(){
        pbEditPw.visibility = View.GONE
        Toast.makeText(this@EditPasswordActivity, "Invalid Old Password", Toast.LENGTH_SHORT).show()
    }

}