package com.mobdeve.gonzales.lee.ong.artemis

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.Toast
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class PostAddTagsActivity : AppCompatActivity() {
    private lateinit var tietTags: TextInputEditText
    private lateinit var btnAddTag: Button

    //Firebase
    private lateinit var mAuth: FirebaseAuth
    private lateinit var user: FirebaseUser
    private lateinit var userId: String
    private lateinit var db: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_post_add_tags)

        initComponents()
        initFirebase()
    }

    private fun initFirebase() {
        this.mAuth = Firebase.auth
        this.user = this.mAuth.currentUser!!
        this.userId = this.user.uid
        this.db = Firebase.database.reference
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
            var tags: ArrayList<String> = tietTags.text.toString().trim().split(',').toCollection(ArrayList())
            //var allTags: Array<String> = tag.toTypedArray()
            //var allTages: ArrayList<String> = ArrayList<String>()
            val title: String = intent.getStringExtra(Keys.KEY_TITLE.name).toString()
            val medium: String = intent.getStringExtra(Keys.KEY_MEDIUM.name).toString()
            val dimension: String = intent.getStringExtra(Keys.KEY_DIMENSIONS.name).toString()
            val desc: String = intent.getStringExtra(Keys.KEY_DESCRIPTION.name).toString()

            Toast.makeText(this, "title: " + title, Toast.LENGTH_SHORT).show()
           // Toast.makeText(this, "med: " + medium, Toast.LENGTH_SHORT).show()
            //Toast.makeText(this, "dimension: " + dimension, Toast.LENGTH_SHORT).show()
           // Toast.makeText(this, "desc: " + desc, Toast.LENGTH_SHORT).show()
            //Toast.makeText(this, "tags: " + allTags, Toast.LENGTH_SHORT).show()
            val post: Post = Post(R.drawable.chibi_circle, "bio2", title, R.drawable.magia_record,
                                medium, dimension, desc, tags)

            val postDB = this.db.child(Keys.KEY_DB_POSTS.name)
            val postKey = postDB.push().key!!

            postDB.child(postKey).setValue(post)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful){
                        Toast.makeText(this, "Posted Successfully", Toast.LENGTH_LONG).show()
                        val intent = Intent(this@PostAddTagsActivity, BrowseFeedActivity::class.java)
                        startActivity(intent)
                    }

                    else{
                        Toast.makeText(this, "Failed to Post", Toast.LENGTH_LONG).show()
                    }
                }
        }
    }
}