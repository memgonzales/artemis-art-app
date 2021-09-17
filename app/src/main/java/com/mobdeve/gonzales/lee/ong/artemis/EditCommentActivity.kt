package com.mobdeve.gonzales.lee.ong.artemis

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import de.hdodenhof.circleimageview.CircleImageView

/**
 * Class handling the functionalities related to editing a comment.
 *
 * @constructor Creates a class that handles the functionalities related to editing a comment.
 */
class EditCommentActivity : AppCompatActivity() {
    /**
     * Image view for the profile picture of the user who posted the comment.
     */
    private lateinit var civEditCommentProfilePic: CircleImageView

    /**
     * Text view for the username of the user who posted the comment.
     */
    private lateinit var tvEditCommentUsername: TextView

    /**
     * Input field for editing the comment.
     */
    private lateinit var etEditComment: EditText

    /**
     * Button for saving the edited comment.
     */
    private lateinit var btnEditCommentSave: Button

    /**
     * Progress bar to signal that data are being saved into the database.
     */
    private lateinit var pbEditComment: ProgressBar

    /**
     * Object instantiating the class containing helper methods for Firebase CRUD operations.
     */
    private lateinit var firebaseHelper: FirebaseHelper

    /**
     * Unique identifier of the comment.
     */
    private lateinit var commentId: String

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
        setContentView(R.layout.activity_edit_comment)

        initComponents()
    }

    /**
     * Initializes the components of the activity.
     */
    private fun initComponents() {
        setSupportActionBar(findViewById(R.id.toolbar_edit_comment))
        initActionBar()

        this.civEditCommentProfilePic = findViewById(R.id.civ_edit_comment_profile_pic)
        this.tvEditCommentUsername = findViewById(R.id.tv_edit_comment_username)
        this.etEditComment = findViewById(R.id.et_edit_comment)
        this.btnEditCommentSave = findViewById(R.id.btn_edit_comment_save)

        this.pbEditComment = findViewById(R.id.pb_edit_comment)

        initIntent()

        btnEditCommentSave.setOnClickListener {
            val commentBody = etEditComment.text.toString().trim()

            if (!commentBody.isNullOrEmpty()){
                pbEditComment.visibility = View.VISIBLE
                this.firebaseHelper.editComment(commentId, commentBody)
                pbEditComment.visibility = View.GONE
                finish()
            }
            else{
                Toast.makeText(this, "Comments should not be blank", Toast.LENGTH_SHORT).show()
            }
        }
    }

    /**
     * Retrieves the data passed via intents and initializes the remote database helpers
     * required for editing.
     */
    private fun initIntent() {
        val intent: Intent = intent

        val cmtId = intent.getStringExtra(Keys.KEY_COMMENTID.name)
        if(!cmtId.isNullOrEmpty()){
            this.commentId = cmtId
        }

        this.firebaseHelper = FirebaseHelper(this@EditCommentActivity, commentId)

        val profilePicture = intent.getStringExtra(Keys.KEY_PROFILE_PICTURE.name)
        val username = intent.getStringExtra(Keys.KEY_USERNAME.name)
        val commentBody = intent.getStringExtra(Keys.KEY_COMMENT_BODY.name)

        if (!(this@EditCommentActivity as Activity).isFinishing) {
            Glide.with(this@EditCommentActivity)
                .load(profilePicture)
                .placeholder(R.drawable.chibi_artemis_hd)
                .error(R.drawable.chibi_artemis_hd)
                .into(this.civEditCommentProfilePic)
        }

        this.tvEditCommentUsername.text = username
        this.etEditComment.setText(commentBody)

    }

    /**
     * Adds a back button to the action bar.
     */
    private fun initActionBar() {
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
    }
}