package com.mobdeve.gonzales.lee.ong.artemis

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.google.android.material.textfield.TextInputEditText

class PostArtworkActivity : AppCompatActivity() {
    private lateinit var tietTitle: TextInputEditText
    private lateinit var tietMedium: TextInputEditText
    private lateinit var tietDimension: TextInputEditText
    private lateinit var tietDescription: TextInputEditText

    private lateinit var btnDetailsNext: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_post_artwork)

        initComponents()
    }

    private fun initComponents() {
        setSupportActionBar(findViewById(R.id.toolbar_post_artwork))
        initActionBar()
    }

    private fun initActionBar() {
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
    }

    private fun addDetails(){
        this.tietTitle = findViewById(R.id.tiet_post_artwork_title)
        this.tietMedium = findViewById(R.id.tiet_post_artwork_medium)
        this.tietDimension = findViewById(R.id.tiet_post_artwork_dimen)
        this.tietDescription = findViewById(R.id.tiet_post_artwork_desc)

        this.btnDetailsNext = findViewById(R.id.btn_post_artwork_save)

        this.btnDetailsNext.setOnClickListener {
            var title = tietTitle.text.toString().trim()
            var medium = tietMedium.text.toString().trim()
            var dimension = tietDimension.text.toString().trim()
            var desc = tietDescription.text.toString().trim()


        }
    }
}