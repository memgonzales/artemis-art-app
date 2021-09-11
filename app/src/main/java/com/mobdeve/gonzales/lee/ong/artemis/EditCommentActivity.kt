package com.mobdeve.gonzales.lee.ong.artemis

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import de.hdodenhof.circleimageview.CircleImageView

class EditCommentActivity : AppCompatActivity() {
    private lateinit var civEditCommentProfilePic: CircleImageView
    private lateinit var tvEditCommentUsername: TextView
    private lateinit var etEditComment: EditText
    private lateinit var btnEditCommentSave: Button

    private lateinit var mAuth: FirebaseAuth
    private lateinit var db: DatabaseReference

    private lateinit var user: FirebaseUser
    private lateinit var userId: String

    private lateinit var commentId: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_comment)

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
            val intent = Intent(this@EditCommentActivity, BrokenLinkActivity::class.java)
            startActivity(intent)
        }
    }

    private fun initComponents() {
        setSupportActionBar(findViewById(R.id.toolbar_edit_comment))
        initActionBar()

        this.civEditCommentProfilePic = findViewById(R.id.civ_edit_comment_profile_pic)
        this.tvEditCommentUsername = findViewById(R.id.tv_edit_comment_username)
        this.etEditComment = findViewById(R.id.et_edit_comment)
        this.btnEditCommentSave = findViewById(R.id.btn_edit_comment_save)

        btnEditCommentSave.setOnClickListener {
            var commentBody = etEditComment.text.toString().trim()

            if (!commentBody.isNullOrEmpty()){
                this.db.child(Keys.KEY_DB_COMMENTS.name).child(commentId).child(Keys.commentBody.name).setValue(commentBody)
                    .addOnSuccessListener {
                        Toast.makeText(this@EditCommentActivity, "Your comment has been updated", Toast.LENGTH_SHORT
                        ).show()
                        finish()
                    }
                    .addOnFailureListener {
                        Toast.makeText(this@EditCommentActivity, "Your comment failed to be updated", Toast.LENGTH_SHORT
                        ).show()
                        finish()
                    }
            }
            else{
                Toast.makeText(this, "Comments should not be blank", Toast.LENGTH_SHORT).show()
            }

        }

        initIntent()
    }

    private fun initIntent() {
        val intent: Intent = intent

        val cmtId = intent.getStringExtra(Keys.KEY_COMMENTID.name)
        if(!cmtId.isNullOrEmpty()){
            this.commentId = cmtId
        }
        val profilePicture = intent.getStringExtra(Keys.KEY_PROFILE_PICTURE.name)
        val username = intent.getStringExtra(Keys.KEY_USERNAME.name)
        val commentBody = intent.getStringExtra(Keys.KEY_COMMENT_BODY.name)

        Glide.with(this)
            .load(profilePicture)
            .placeholder(R.drawable.chibi_artemis_hd)
            .error(R.drawable.chibi_artemis_hd)
            .into(this.civEditCommentProfilePic)

        //this.civEditCommentProfilePic.setImageResource(profilePicture)

        this.tvEditCommentUsername.text = username
        this.etEditComment.setText(commentBody)

    }

    private fun initActionBar() {
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
    }
}