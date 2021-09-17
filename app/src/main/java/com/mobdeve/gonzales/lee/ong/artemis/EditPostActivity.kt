package com.mobdeve.gonzales.lee.ong.artemis

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout

/**
 * Class handling the functionalities related to editing a post.
 *
 * @constructor Creates a class that handles the functionalities related to editing a post.
 */
class EditPostActivity : AppCompatActivity() {
    /**
     * Input field for editing the title of the post.
     */
    private lateinit var tietEditArtworkTitle: TextInputEditText

    /**
     * Input field for editing the medium of the artwork.
     */
    private lateinit var tietEditArtworkMedium: TextInputEditText

    /**
     * Input field for editing the dimensions of the artwork.
     */
    private lateinit var tietEditArtworkDimension: TextInputEditText

    /**
     * Input field for editing the description of the artwork.
     */
    private lateinit var tietEditArtworkDescription: TextInputEditText

    private lateinit var tilEditArtworkTags: TextInputLayout
    /**
     * Input field for editing the tags of an artwork.
     */
    private lateinit var tietEditArtworkTags: TextInputEditText

    /**
     * Image view for the photo of the artwork posted.
     */
    private lateinit var ivEditArtworkPost: ImageView

    /**
     * Button for saving the edits to the details of the artwork posted.
     */
    private lateinit var btnEditArtworkSave: Button

    /**
     * Object instantiating the class containing helper methods for Firebase CRUD operations.
     */
    private lateinit var firebaseHelper: FirebaseHelper

    /**
     * Unique identifier of the post.
     */
    private lateinit var postId: String

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
        setContentView(R.layout.activity_edit_post)

        initComponents()
    }

    /**
     * Initializes the components of the activity.
     */
    private fun initComponents() {
        setSupportActionBar(findViewById(R.id.toolbar_edit_artwork))
        initActionBar()

        this.tilEditArtworkTags = findViewById(R.id.til_edit_artwork_tags)

        this.tietEditArtworkTitle = findViewById(R.id.tiet_edit_artwork_title)
        this.tietEditArtworkMedium = findViewById(R.id.tiet_edit_artwork_medium)
        this.tietEditArtworkDimension = findViewById(R.id.tiet_edit_artwork_dimen)
        this.tietEditArtworkDescription = findViewById(R.id.tiet_edit_artwork_desc)
        this.tietEditArtworkTags = findViewById(R.id.tiet_edit_artwork_tags)
        this.ivEditArtworkPost = findViewById(R.id.iv_edit_artwork_art)
        this.btnEditArtworkSave = findViewById(R.id.btn_edit_artwork_save)

        initIntent()

        this.btnEditArtworkSave.setOnClickListener {
            var title = tietEditArtworkTitle.text.toString().trim()
            var medium = tietEditArtworkMedium.text.toString().trim()
            var dimension = tietEditArtworkDimension.text.toString().trim()
            var description = tietEditArtworkDescription.text.toString().trim()
            var tags = tietEditArtworkTags.text.toString().trim()

            if(!postId.isEmpty() && !tags.isNullOrEmpty()){
                tilEditArtworkTags.error = null
                this.firebaseHelper.editPost(postId, title, medium, dimension, description, tags.split(',').toCollection(ArrayList()))
                finish()
            }

            else if (tags.isNullOrEmpty()){
                tilEditArtworkTags.error = "Required"
                tietEditArtworkTags.requestFocus()
            }
            else{
                Toast.makeText(this@EditPostActivity, "Unable to load data", Toast.LENGTH_SHORT).show()
            }
        }
    }

    /**
     * Retrieves the data passed via intents and initializes the remote database helpers
     * required for editing.
     */
    private fun initIntent() {
        val intent: Intent = intent

        this.postId = intent.getStringExtra(Keys.KEY_POSTID.name).toString()
        this.firebaseHelper = FirebaseHelper(this@EditPostActivity, postId)

        val title = intent.getStringExtra(Keys.KEY_TITLE.name).toString()
        val medium = intent.getStringExtra(Keys.KEY_MEDIUM.name).toString()
        val dimension = intent.getStringExtra(Keys.KEY_DIMENSIONS.name).toString()
        val description = intent.getStringExtra(Keys.KEY_DESCRIPTION.name).toString()
        val tags = intent.getStringExtra(Keys.KEY_TAGS.name).toString()

        val postImg = intent.getStringExtra(Keys.KEY_POST.name).toString()


        this.tietEditArtworkTitle.setText(title)
        this.tietEditArtworkMedium.setText(medium)
        this.tietEditArtworkDimension.setText(dimension)
        this.tietEditArtworkDescription.setText(description)
        this.tietEditArtworkTags.setText(tags)

        if (!(this@EditPostActivity as Activity).isFinishing) {
            Glide.with(this@EditPostActivity)
                .load(postImg)
                .placeholder(R.drawable.placeholder)
                .error(R.drawable.placeholder)
                .into(this.ivEditArtworkPost)
        }


    }

    /**
     * Adds a back button to the action bar.
     */
    private fun initActionBar() {
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
    }
}