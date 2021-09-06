package com.mobdeve.gonzales.lee.ong.artemis

import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Matrix
import android.media.ExifInterface
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
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
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.ktx.storage
import java.io.ByteArrayOutputStream

class PostAddTagsActivity : AppCompatActivity() {
    private lateinit var tietTags: TextInputEditText
    private lateinit var btnAddTag: Button

    private lateinit var ivPostAddTagsArt: ImageView

    //Firebase
    private lateinit var mAuth: FirebaseAuth
    private lateinit var user: FirebaseUser
    private lateinit var userId: String
    private lateinit var db: DatabaseReference

    private lateinit var storage: FirebaseStorage
    private lateinit var storageRef: StorageReference

    private lateinit var title: String
    private lateinit var medium: String
    private lateinit var dimensions: String
    private lateinit var desc: String

    private lateinit var pbAddPost: ProgressBar

    private lateinit var sp: SharedPreferences
    private lateinit var spEditor: SharedPreferences.Editor

    private lateinit var photoSource: String
    private lateinit var photoPath: String

    private var cameraTaken: Boolean = false
    private lateinit var photoByte: ByteArray
    private lateinit var photoUri: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_post_add_tags)

        initComponents()
        initFirebase()

        fetchPhoto()
    }

    private fun fetchPhoto() {
        ivPostAddTagsArt = findViewById(R.id.iv_post_add_tags_art)

        photoSource = intent.getStringExtra(Keys.KEY_POST_FROM.name)!!
        photoPath = intent.getStringExtra(Keys.KEY_POST_ARTWORK.name)!!

        if (photoSource == PostArtworkUtil.FROM_CAMERA) {
            fetchFromCamera(photoPath)
            cameraTaken = true
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

        ivPostAddTagsArt.setImageBitmap(rotatedBitmap)


        val outputStream = ByteArrayOutputStream()
        rotatedBitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream)
        this.photoByte = outputStream.toByteArray()

    }

    private fun fetchFromGallery() {
        val photoPath: String? = intent.getStringExtra(Keys.KEY_POST_ARTWORK.name)
        ivPostAddTagsArt.setImageURI(Uri.parse(photoPath!!))

        this.photoUri = photoPath

    }

    private fun initFirebase() {
        this.mAuth = Firebase.auth
        this.user = this.mAuth.currentUser!!
        this.userId = this.user.uid
        this.db = Firebase.database.reference

        this.storage = Firebase.storage
        this.storageRef = this.storage.reference
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

    /*
    override fun onBackPressed() {
        val i = Intent(Intent.ACTION_MAIN)
        i.addCategory(Intent.CATEGORY_HOME)
        //i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        i.setFlags(Intent.FLAG_ACTIVITY_PREVIOUS_IS_TOP)
        /*
        i.setFlags(Intent.FLAG_ACTIVITY_PREVIOUS_IS_TOP)
        i.putExtra(Keys.KEY_TITLE.name, title)
        i.putExtra(Keys.KEY_MEDIUM.name, medium)
        i.putExtra(Keys.KEY_DIMENSIONS.name, dimensions)
        i.putExtra(Keys.KEY_DESCRIPTION.name, desc)
         */
        startActivity(i)
    }
     */



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
            val tags: String = tietTags.text.toString().trim()

            if (!checkEmpty(tags)){
                val allTags: ArrayList<String> = tags.split(',').toCollection(ArrayList())

                val title: String = intent.getStringExtra(Keys.KEY_TITLE.name).toString()
                val medium: String = intent.getStringExtra(Keys.KEY_MEDIUM.name).toString()
                val dimensions: String = intent.getStringExtra(Keys.KEY_DIMENSIONS.name).toString()
                val desc: String = intent.getStringExtra(Keys.KEY_DESCRIPTION.name).toString()

                this.pbAddPost.visibility = View.VISIBLE

                val postDB = this.db.child(Keys.KEY_DB_POSTS.name)
                val postKey = postDB.push().key!!

                val url = this.storageRef.child(postKey)

                if(cameraTaken){
                    url.putBytes(this.photoByte)
                        .addOnSuccessListener {
                            url.downloadUrl
                                .addOnSuccessListener {

                                    val post = Post(postKey, title, it.toString(), medium, dimensions, desc, allTags)
                                    storePost(postKey, post)
                                }

                                .addOnFailureListener {
                                    postFailed()
                                }
                        }
                        .addOnFailureListener{
                            postFailed()
                        }

                }

                else{
                    url.putFile(Uri.parse(this.photoUri))
                        .addOnSuccessListener {
                            url.downloadUrl
                                .addOnSuccessListener {

                                    val post = Post(postKey, title, it.toString(), medium, dimensions, desc, allTags)
                                    storePost(postKey, post)
                                }

                                .addOnFailureListener {
                                    postFailed()
                                }
                        }
                        .addOnFailureListener{
                            postFailed()
                        }
                }
            }
        }

    }

    private fun storePost(postKey: String, post: Post){
        val userDB = this.db.child(Keys.KEY_DB_USERS.name).child(this.userId)

        userDB.addValueEventListener(object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val userImg: String = snapshot.child(Keys.userImg.name).getValue().toString()
                val username: String = snapshot.child(Keys.username.name).getValue().toString()

                post.setProfilePicture(userImg)
                post.setUsername(username)

                val updates = hashMapOf<String, Any>(
                    "/${Keys.KEY_DB_POSTS}/$postKey" to post,
                    "/${Keys.KEY_DB_USERS}/$userId/${Keys.userPosts.name}/$postKey" to postKey
                )


                db.updateChildren(updates)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful){
                            postSuccessfully()
                        }

                        else{
                            postFailed()
                        }
                    }
            }

            override fun onCancelled(error: DatabaseError) {
                postFailed()
            }

        })
    }

    private fun postSuccessfully(){
        pbAddPost.visibility = View.GONE
        Toast.makeText(applicationContext, "Posted successfully", Toast.LENGTH_LONG).show()
        val intent = Intent(this@PostAddTagsActivity, BrowseFeedActivity::class.java)
        startActivity(intent)
    }

    private fun postFailed(){
        pbAddPost.visibility = View.GONE
        Toast.makeText(applicationContext, "Failed to post", Toast.LENGTH_LONG).show()
    }

    private fun checkEmpty(tags: String): Boolean{
        var hasEmpty = false

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

    private fun getList(str: String): ArrayList<String>{
        return str.substring(1, str.length-1).split(",").toCollection(ArrayList())
    }

    /*
    override fun onPause() {
        super.onPause()
    }
     */
}