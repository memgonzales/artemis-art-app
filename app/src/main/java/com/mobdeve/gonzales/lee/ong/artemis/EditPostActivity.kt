package com.mobdeve.gonzales.lee.ong.artemis

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class EditPostActivity : AppCompatActivity() {
    private lateinit var tietEditArtworkTitle: TextInputEditText
    private lateinit var tietEditArtworkMedium: TextInputEditText
    private lateinit var tietEditArtworkDimension: TextInputEditText
    private lateinit var tietEditArtworkDescription: TextInputEditText
    private lateinit var tietEditArtworkTags: TextInputEditText
    private lateinit var ivEditArtworkPost: ImageView
    private lateinit var btnEditArtworkSave: Button

    private lateinit var mAuth: FirebaseAuth
    private lateinit var db: DatabaseReference

    private lateinit var user: FirebaseUser
    private lateinit var userId: String

    private lateinit var postId: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_post)

        initFirebase()
        initComponents()
    }

    /**
     * Initializes the Firebase-related components.
     */
    private fun initFirebase(){
        this.mAuth = Firebase.auth
        this.db = Firebase.database.reference

        if (this.mAuth.currentUser != null){
            this.user = this.mAuth.currentUser!!
            this.userId = this.user.uid
        }

        else{
            val intent = Intent(this@EditPostActivity, BrokenLinkActivity::class.java)
            startActivity(intent)
        }
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

        initIntent()

        this.btnEditArtworkSave.setOnClickListener {
            var title = tietEditArtworkTitle.text.toString().trim()
            var medium = tietEditArtworkMedium.text.toString().trim()
            var dimension = tietEditArtworkDimension.text.toString().trim()
            var description = tietEditArtworkDescription.text.toString().trim()
            var tags = tietEditArtworkTags.text.toString().trim()

            if(!postId.isEmpty()){
                updatePostDB(postId, title, medium, dimension, description, tags.split(',').toCollection(ArrayList()))
            }

            else{
                Toast.makeText(this@EditPostActivity, "Unable to load data", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun initIntent() {
        val intent: Intent = intent

        this.postId = intent.getStringExtra(Keys.KEY_POSTID.name).toString()

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

        Glide.with(this)
            .load(postImg)
            .error(R.drawable.placeholder)
            .into(this.ivEditArtworkPost)


    }

    private fun initActionBar() {
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
    }

    private fun updatePostDB(postId: String, title: String, medium: String, dimensions: String, desc: String, tags: ArrayList<String>){
        val updates = hashMapOf<String, Any?>(
            "/${Keys.KEY_DB_POSTS.name}/$postId/${Keys.title.name}" to title,
            "/${Keys.KEY_DB_POSTS.name}/$postId/${Keys.medium.name}" to medium,
            "/${Keys.KEY_DB_POSTS.name}/$postId/${Keys.dimensions.name}" to dimensions,
            "/${Keys.KEY_DB_POSTS.name}/$postId/${Keys.description.name}" to desc,
            "/${Keys.KEY_DB_POSTS.name}/$postId/${Keys.tags.name}" to tags,
        )

        db.updateChildren(updates)
            .addOnSuccessListener {
                Toast.makeText(
                    this@EditPostActivity,
                    "Your post details have been updated",
                    Toast.LENGTH_SHORT
                ).show()

               // val intent = Intent(this@EditPostActivity, BrowseOwnPostsActivity::class.java)
               // startActivity(intent)
                finish()
            }
            .addOnFailureListener {
                Toast.makeText(
                    this@EditPostActivity,
                    "Your post details failed to be updated",
                    Toast.LENGTH_SHORT
                ).show()
              //  val intent = Intent(this@EditPostActivity, BrowseOwnPostsActivity::class.java)
               // startActivity(intent)
                finish()
            }
    }
}