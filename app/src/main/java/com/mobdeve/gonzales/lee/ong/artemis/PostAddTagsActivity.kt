package com.mobdeve.gonzales.lee.ong.artemis

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.Toast
import com.google.android.material.textfield.TextInputEditText

class PostAddTagsActivity : AppCompatActivity() {
    private lateinit var tietTags: TextInputEditText
    private lateinit var btnAddTag: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_post_add_tags)

        initComponents()
    }

    private fun initComponents() {
        setSupportActionBar(findViewById(R.id.toolbar_post_add_tags))
        initActionBar()

        addTags()
    }

    private fun initActionBar() {
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
    }


    private fun addTags(){
        this.tietTags = findViewById(R.id.tiet_post_add_tags_desc)
        this.btnAddTag = findViewById(R.id.btn_post_add_tags_save)

        this.btnAddTag.setOnClickListener {
            var tag = tietTags.text.toString().trim().split(',')
            Toast.makeText(this, "Check: " + tag, Toast.LENGTH_SHORT).show()
        }
    }
}