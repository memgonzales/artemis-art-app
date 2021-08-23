package com.mobdeve.gonzales.lee.ong.artemis

import android.os.Bundle
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity

class ViewCommentsUnregisteredActivity : AppCompatActivity() {
    private lateinit var etAddCommentUnregistered: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_comments_unregistered)
        initComponents()
    }

    private fun initComponents() {
        setSupportActionBar(findViewById(R.id.toolbar_view_comments_unregistered))
        initActionBar()

        this.etAddCommentUnregistered = findViewById(R.id.et_add_comment_unregistered)
        this.disableEditText();
    }

    private fun initActionBar() {
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
    }

    private fun disableEditText() {
        this.etAddCommentUnregistered.isEnabled = false
    }
}