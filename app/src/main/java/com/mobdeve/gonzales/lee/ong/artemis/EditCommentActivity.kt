package com.mobdeve.gonzales.lee.ong.artemis

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import de.hdodenhof.circleimageview.CircleImageView

class EditCommentActivity : AppCompatActivity() {
    private lateinit var civEditCommentProfilePic: CircleImageView
    private lateinit var tvEditCommentUsername: TextView
    private lateinit var etEditComment: EditText
    private lateinit var btnEditCommentSave: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_comment)

        initComponents()
    }

    private fun initComponents() {
        setSupportActionBar(findViewById(R.id.toolbar_edit_comment))
        initActionBar()

        this.civEditCommentProfilePic = findViewById(R.id.civ_edit_comment_profile_pic)
        this.tvEditCommentUsername = findViewById(R.id.tv_edit_comment_username)
        this.etEditComment = findViewById(R.id.et_edit_comment)
        this.btnEditCommentSave = findViewById(R.id.btn_edit_comment_save)

        btnEditCommentSave.setOnClickListener {
            Toast.makeText(
                this@EditCommentActivity,
                "Your comment has been updated",
                Toast.LENGTH_SHORT
            ).show()
            finish()
        }

        initIntent()
    }

    private fun initIntent() {
        val intent: Intent = intent

        val profilePicture = intent.getIntExtra(Keys.KEY_PROFILE_PICTURE.name, 0)
        val username = intent.getStringExtra(Keys.KEY_USERNAME.name)
        val commentBody = intent.getStringExtra(Keys.KEY_COMMENT_BODY.name)

        this.civEditCommentProfilePic.setImageResource(profilePicture)
        this.tvEditCommentUsername.text = username
        this.etEditComment.setText(commentBody)

    }

    private fun initActionBar() {
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
    }
}