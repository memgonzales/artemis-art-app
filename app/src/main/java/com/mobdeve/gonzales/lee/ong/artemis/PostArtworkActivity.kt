package com.mobdeve.gonzales.lee.ong.artemis

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.google.android.material.textfield.TextInputEditText

class PostArtworkActivity : AppCompatActivity() {
    private lateinit var tietTitle: TextInputEditText
    private lateinit var tietMedium: TextInputEditText
    private lateinit var tietDimensions: TextInputEditText
    private lateinit var tietDescription: TextInputEditText

    private lateinit var btnDetails: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_post_artwork)

        initComponents()
    }

    private fun initComponents() {
        setSupportActionBar(findViewById(R.id.toolbar_post_artwork))
        initActionBar()

        addDetails()
    }

    private fun initActionBar() {
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
    }

    private fun addDetails(){
        this.tietTitle = findViewById(R.id.tiet_post_artwork_title)
        this.tietMedium = findViewById(R.id.tiet_post_artwork_medium)
        this.tietDimensions = findViewById(R.id.tiet_post_artwork_dimen)
        this.tietDescription = findViewById(R.id.tiet_post_artwork_desc)

        this.btnDetails = findViewById(R.id.btn_post_artwork_save)

        this.btnDetails.setOnClickListener { view ->
            var title = tietTitle.text.toString().trim()
            var medium = tietMedium.text.toString().trim()
            var dimensions = tietDimensions.text.toString().trim()
            var desc = tietDescription.text.toString().trim()

            val intent = Intent(this, PostAddTagsActivity::class.java)

            intent.putExtra(Keys.KEY_TITLE.name, title)
            intent.putExtra(Keys.KEY_MEDIUM.name, medium)
            intent.putExtra(Keys.KEY_DIMENSIONS.name, dimensions)
            intent.putExtra(Keys.KEY_DESCRIPTION.name, desc)

            view.context.startActivity(intent)
        }
    }
}