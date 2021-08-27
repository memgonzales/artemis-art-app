package com.mobdeve.gonzales.lee.ong.artemis

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.Button
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import com.google.android.material.textfield.TextInputEditText

class PostArtworkActivity : AppCompatActivity() {
    private lateinit var tietTitle: TextInputEditText
    private lateinit var tietMedium: TextInputEditText
    private lateinit var tietDimensions: TextInputEditText
    private lateinit var tietDescription: TextInputEditText

    private lateinit var btnDetails: Button

    /*
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if(resultCode == RESULT_OK){

        }
    }

     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_post_artwork)

        initComponents()
    }

    private fun initComponents() {
        setSupportActionBar(findViewById(R.id.toolbar_post_artwork))
        initActionBar()

        initDetails()
        addDetails()
    }

    private fun initActionBar() {
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
    }

    private fun initDetails(){
        this.tietTitle = findViewById(R.id.tiet_post_artwork_title)
        this.tietMedium = findViewById(R.id.tiet_post_artwork_medium)
        this.tietDimensions = findViewById(R.id.tiet_post_artwork_dimen)
        this.tietDescription = findViewById(R.id.tiet_post_artwork_desc)

        if (intent.extras != null){
            var title: String = intent.getStringExtra(Keys.KEY_TITLE.name).toString()
            var medium: String = intent.getStringExtra(Keys.KEY_MEDIUM.name).toString()
            var dimensions: String = intent.getStringExtra(Keys.KEY_DIMENSIONS.name).toString()
            var desc: String = intent.getStringExtra(Keys.KEY_DESCRIPTION.name).toString()

            this.tietTitle.setText(title)
            this.tietMedium.setText(medium)
            this.tietDimensions.setText(dimensions)
            this.tietDescription.setText(desc)
        }

    }

    private fun addDetails(){

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

            startActivity(intent)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId

        when(id) {
            android.R.id.home -> {
                onBackPressed()
                return true
            } else -> {
            return super.onOptionsItemSelected(item)
            }
        }

        return super.onOptionsItemSelected(item)
    }
}