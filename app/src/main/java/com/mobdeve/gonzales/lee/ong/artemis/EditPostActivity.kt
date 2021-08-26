package com.mobdeve.gonzales.lee.ong.artemis

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.textfield.TextInputEditText

class EditPostActivity : AppCompatActivity() {
    private lateinit var tietEditArtworkTitle: TextInputEditText
    private lateinit var tietEditArtworkMedium: TextInputEditText
    private lateinit var tietEditArtworkDimension: TextInputEditText
    private lateinit var tietEditArtworkDescription: TextInputEditText
    private lateinit var tietEditArtworkTags: TextInputEditText
    private lateinit var ivEditArtworkPost: ImageView
    private lateinit var btnEditArtworkSave: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_post)

        initComponents()
    }

    private fun initComponents() {
        setSupportActionBar(findViewById(R.id.toolbar_edit_artwork))
        initActionBar()

        this.tietEditArtworkTitle = findViewById(R.id.tiet_edit_artwork_title)
        this.tietEditArtworkMedium = findViewById(R.id.tiet_edit_artwork_medium)
        this.tietEditArtworkDimension = findViewById(R.id.tiet_edit_artwork_dimen)
        this.tietEditArtworkDescription = findViewById(R.id.tiet_edit_artwork_desc)
        this.tietEditArtworkTags = findViewById(R.id.tiet_edit_artwork_tags)
        this.ivEditArtworkPost = findViewById(R.id.iv_edit_artwork_art)
        this.btnEditArtworkSave = findViewById(R.id.btn_edit_artwork_save)

        btnEditArtworkSave.setOnClickListener(View.OnClickListener {
            Toast.makeText(this@EditPostActivity, "Your post details have been updated", Toast.LENGTH_SHORT).show()
            finish()
        })

        initIntent()
    }

    private fun initIntent() {
        val intent: Intent = intent

        val title = intent.getStringExtra(Keys.KEY_TITLE.name)
        val medium = intent.getStringExtra(Keys.KEY_MEDIUM.name)
        val dimension = intent.getStringExtra(Keys.KEY_DIMENSIONS.name)
        val description = intent.getStringExtra(Keys.KEY_DESCRIPTION.name)
        val tags = intent.getStringArrayListExtra(Keys.KEY_TAGS.name)
        val postImg = intent.getIntExtra(Keys.KEY_POST.name, 0)

        val tagsString = tags?.joinToString(", ")

        this.tietEditArtworkTitle.setText(title)
        this.tietEditArtworkMedium.setText(medium)
        this.tietEditArtworkDimension.setText(dimension)
        this.tietEditArtworkDescription.setText(description)
        this.tietEditArtworkTags.setText(tagsString)
        this.ivEditArtworkPost.setImageResource(postImg)
    }

    private fun initActionBar() {
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
    }
}