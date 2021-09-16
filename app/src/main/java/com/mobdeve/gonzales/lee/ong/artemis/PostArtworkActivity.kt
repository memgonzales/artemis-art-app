package com.mobdeve.gonzales.lee.ong.artemis

import android.content.Intent
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
import java.io.FileOutputStream

/**
 * Class for handling functionalities related to posting an artwork.
 *
 * @constructor Creates an activity for posting an artwork.
 */
class PostArtworkActivity : AppCompatActivity() {
    /**
     * Input field for the title of the artwork.
     */
    private lateinit var tietTitle: TextInputEditText

    /**
     * Input field for the medium of the artwork.
     */
    private lateinit var tietMedium: TextInputEditText

    /**
     * Input field for the dimensions of the artwork.
     */
    private lateinit var tietDimensions: TextInputEditText

    /**
     * Input field for the description of the artwork.
     */
    private lateinit var tietDescription: TextInputEditText

    /**
     * Button for saving the details about the artwork.
     */
    private lateinit var btnDetails: Button

    /**
     * Image view for the photo of the artwork to be posted.
     */
    private lateinit var ivPostArtworkArt: ImageView

    /**
     * Either <code>PostArtworkUtil.FROM_CAMERA</code> or <code>PostArtworkUtil.FROM_GALLERY</code>
     * depending on whether the photo was chosen from the Gallery or taken using the device camera.
     */
    private lateinit var photoSource: String

    /**
     * Path to the photo of the artwork.
     */
    private lateinit var photoPath: String

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
        setContentView(R.layout.activity_post_artwork)

        initComponents()

        fetchPhoto()
    }

    /**
     * Retrieves the path to the photo to be posted by the user, alongside data as to whether
     * this photo was chosen from the Gallery or taken using the device camera.
     */
    private fun fetchPhoto() {
        ivPostArtworkArt = findViewById(R.id.iv_post_artwork_art)

        photoSource = intent.getStringExtra(Keys.KEY_POST_FROM.name)!!
        photoPath = intent.getStringExtra(Keys.KEY_POST_ARTWORK.name)!!

        if (photoSource == PostArtworkUtil.FROM_CAMERA) {
            fetchFromCamera(photoPath)
        } else if (photoSource == PostArtworkUtil.FROM_GALLERY) {
            fetchFromGallery()
        }
    }

    /**
     * Displays the photo taken using the device camera in the proper orientation.
     *
     * This method addresses the issue of most device cameras setting the orientation of a captured
     * image to landscape. The code for rotation is a direct translation of the one found in
     * [https://www.py4u.net/discuss/611150](https://www.py4u.net/discuss/611150).
     *
     * @param photoPath Path to the photo to be rotated.
     */
    private fun fetchFromCamera(photoPath: String?) {
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

        /* Compress the photo */
        rotatedBitmap.compress(Bitmap.CompressFormat.JPEG, 60, FileOutputStream(photoPath))

        ivPostArtworkArt.setImageBitmap(rotatedBitmap)

    }

    /**
     * Displays the photo chosen from the Gallery.
     */
    private fun fetchFromGallery() {
        val photoPath: String? = intent.getStringExtra(Keys.KEY_POST_ARTWORK.name)
        ivPostArtworkArt.setImageURI(Uri.parse(photoPath!!))
    }

    /**
     * Initializes the components of the activity.
     */
    private fun initComponents() {
        setSupportActionBar(findViewById(R.id.toolbar_post_artwork))
        initActionBar()

        addDetails()
    }

    /**
     * Adds a back button to the action bar.
     */
    private fun initActionBar() {
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
    }

    /**
     * Stores the details entered about the artwork in intents to be passed to the next activity
     * (where the user is asked to enter tags related to their post).
     */
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

            intent.putExtra(Keys.KEY_POST_ARTWORK.name, photoPath)
            intent.putExtra(Keys.KEY_POST_FROM.name, photoSource)

            startActivity(intent)
        }
    }

    /**
     * This hook is called whenever an item in your options menu is selected.
     *
     * @param item The menu item that was selected. This value cannot be <code>null</code>.
     * @return <code>false</code> to allow normal menu processing to proceed; <code>true</code>
     * to consume it here.
     */
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

}