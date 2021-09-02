package com.mobdeve.gonzales.lee.ong.artemis

import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Matrix
import android.media.ExifInterface
import android.net.Uri
import android.os.Bundle
import android.view.MenuItem
import android.widget.Button
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.textfield.TextInputEditText


class PostArtworkActivity : AppCompatActivity() {
    private lateinit var tietTitle: TextInputEditText
    private lateinit var tietMedium: TextInputEditText
    private lateinit var tietDimensions: TextInputEditText
    private lateinit var tietDescription: TextInputEditText

    private lateinit var btnDetails: Button
    private lateinit var ivPostArtworkArt: ImageView

    private lateinit var sp: SharedPreferences
    private lateinit var spEditor: SharedPreferences.Editor

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_post_artwork)

        initComponents()

        fetchPhoto()
    }

    private fun fetchPhoto() {
        ivPostArtworkArt = findViewById(R.id.iv_post_artwork_art)

        val photoSource: String? = intent.getStringExtra(Keys.KEY_POST_FROM.name)
        val photoPath: String? = intent.getStringExtra(Keys.KEY_POST_ARTWORK.name)

        if (photoSource == PostArtworkUtil.FROM_CAMERA) {
            fetchFromCamera(photoPath)
        } else if (photoSource == PostArtworkUtil.FROM_GALLERY) {
            fetchFromGallery()
        }
    }

    private fun fetchFromCamera(photoPath: String?) {
        /* Taken from https://www.py4u.net/discuss/611150 */
        val bounds = BitmapFactory.Options()
        bounds.inJustDecodeBounds = true
        BitmapFactory.decodeFile(photoPath, bounds)

        val opts = BitmapFactory.Options()
        val bm = BitmapFactory.decodeFile(photoPath, opts)
        val exif = ExifInterface(photoPath!!)
        val orientString: String? = exif.getAttribute(ExifInterface.TAG_ORIENTATION)
        val orientation = orientString?.toInt() ?: ExifInterface.ORIENTATION_NORMAL

        var rotationAngle = 0
        if (orientation == ExifInterface.ORIENTATION_ROTATE_90) rotationAngle = 90
        if (orientation == ExifInterface.ORIENTATION_ROTATE_180) rotationAngle = 180
        if (orientation == ExifInterface.ORIENTATION_ROTATE_270) rotationAngle = 270

        val matrix = Matrix()
        matrix.setRotate(rotationAngle.toFloat(), bm.width.toFloat() / 2, bm.height.toFloat() / 2)
        val rotatedBitmap =
            Bitmap.createBitmap(bm, 0, 0, bounds.outWidth, bounds.outHeight, matrix, true)

        ivPostArtworkArt.setImageBitmap(rotatedBitmap)
    }

    private fun fetchFromGallery() {
        val photoPath: String? = intent.getStringExtra(Keys.KEY_POST_ARTWORK.name)
        ivPostArtworkArt.setImageURI(Uri.parse(photoPath!!))
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


        /*
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

         */

    }

    private fun addDetails(){
        this.tietTitle = findViewById(R.id.tiet_post_artwork_title)
        this.tietMedium = findViewById(R.id.tiet_post_artwork_medium)
        this.tietDimensions = findViewById(R.id.tiet_post_artwork_dimen)
        this.tietDescription = findViewById(R.id.tiet_post_artwork_desc)

        this.btnDetails = findViewById(R.id.btn_post_artwork_save)

        this.btnDetails.setOnClickListener { view ->

            val title = tietTitle.text.toString().trim()
            val medium = tietMedium.text.toString().trim()
            val dimensions = tietDimensions.text.toString().trim()
            val desc = tietDescription.text.toString().trim()


            val intent = Intent(this, PostAddTagsActivity::class.java)

            intent.putExtra(Keys.KEY_TITLE.name, title)
            intent.putExtra(Keys.KEY_MEDIUM.name, medium)
            intent.putExtra(Keys.KEY_DIMENSIONS.name, dimensions)
            intent.putExtra(Keys.KEY_DESCRIPTION.name, desc)

            startActivity(intent)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId) {
            android.R.id.home -> {
                onBackPressed()
                true
            } else -> {
                super.onOptionsItemSelected(item)
            }
        }
    }

    /*
    override fun onResume() {
        super.onResume()


    }

     */
}