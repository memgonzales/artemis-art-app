package com.mobdeve.gonzales.lee.ong.artemis

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ProgressBar
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

    private lateinit var title: String
    private lateinit var medium: String
    private lateinit var dimensions: String
    private lateinit var desc: String

    private lateinit var pbAddPost: ProgressBar

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

        initDetails()
        addTagsAndPost()
    }

    private fun initActionBar() {
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
    }

    override fun onBackPressed() {
        val i = Intent(Intent.ACTION_MAIN)
        i.addCategory(Intent.CATEGORY_HOME)
        //i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)

        i.setFlags(Intent.FLAG_ACTIVITY_PREVIOUS_IS_TOP)

        i.putExtra(Keys.KEY_TITLE.name, title)
        i.putExtra(Keys.KEY_MEDIUM.name, medium)
        i.putExtra(Keys.KEY_DIMENSIONS.name, dimensions)
        i.putExtra(Keys.KEY_DESCRIPTION.name, desc)

        startActivity(i)
    }

    private fun initDetails(){
        this.title = intent.getStringExtra(Keys.KEY_TITLE.name).toString()
        this.medium = intent.getStringExtra(Keys.KEY_MEDIUM.name).toString()
        this.dimensions = intent.getStringExtra(Keys.KEY_DIMENSIONS.name).toString()
        this.desc = intent.getStringExtra(Keys.KEY_DESCRIPTION.name).toString()
    }

    private fun addTagsAndPost(){
        this.tietTags = findViewById(R.id.tiet_post_add_tags_desc)
        this.btnAddTag = findViewById(R.id.btn_post_add_tags_save)

        this.pbAddPost = findViewById(R.id.pb_post_add_tags)

        this.btnAddTag.setOnClickListener {
            var tags: String = tietTags.text.toString().trim()

            if (!checkEmpty(tags)){
                var allTags: ArrayList<String> = tags.split(',').toCollection(ArrayList())
                var title: String = intent.getStringExtra(Keys.KEY_TITLE.name).toString()
                var medium: String = intent.getStringExtra(Keys.KEY_MEDIUM.name).toString()
                var dimensions: String = intent.getStringExtra(Keys.KEY_DIMENSIONS.name).toString()
                var desc: String = intent.getStringExtra(Keys.KEY_DESCRIPTION.name).toString()

                val post: Post = Post(R.drawable.tofu_chan, "Tobe", title, R.drawable.magia_record,
                    medium, dimensions, desc, allTags)

                this.pbAddPost.visibility = View.VISIBLE

                val postDB = this.db.child(Keys.KEY_DB_POSTS.name)
                val postKey = postDB.push().key!!

                postDB.child(postKey).setValue(post)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful){
                            this.pbAddPost.visibility = View.GONE
                            Toast.makeText(this, "Posted successfully", Toast.LENGTH_LONG).show()
                            val intent = Intent(this@PostAddTagsActivity, BrowseFeedActivity::class.java)
                            startActivity(intent)
                        }

                        else{
                            this.pbAddPost.visibility = View.GONE
                            Toast.makeText(this, "Failed to post", Toast.LENGTH_LONG).show()
                        }
                    }
            }
        }

    }

    private fun checkEmpty(tags: String): Boolean{
        var hasEmpty: Boolean = false

        if (tags.isEmpty()){
            this.tietTags.error = "There should be at least one tag"
            this.tietTags.requestFocus()
            hasEmpty = true
        }

        else{
            this.tietTags.error = null
        }

        return hasEmpty
    }
}