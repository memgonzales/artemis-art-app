package com.mobdeve.gonzales.lee.ong.artemis

import android.content.Intent
import android.content.res.ColorStateList
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.*
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.core.widget.NestedScrollView
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.floatingactionbutton.FloatingActionButton
import de.hdodenhof.circleimageview.CircleImageView

class ViewBookmarkActivity : AppCompatActivity() {
    private lateinit var civItemViewBookmarkProfilePic: CircleImageView
    private lateinit var tvItemViewBookmarkUsername: TextView
    private lateinit var ivItemViewBookmarkPost: ImageView
    private lateinit var tvItemViewBookmarkTitle: TextView
    private lateinit var tvItemViewBookmarkDatePosted: TextView
    private lateinit var tvItemViewBookmarkMedium: TextView
    private lateinit var tvItemViewBookmarkDimensions: TextView
    private lateinit var tvItemViewBookmarkDescription: TextView
    private lateinit var ibItemViewBookmarkBookmark: ImageButton
    private lateinit var bnvViewBookmarkBottom: BottomNavigationView
    private lateinit var nsvViewBookmark: NestedScrollView

    private lateinit var btmAddPost: BottomSheetDialog
    private lateinit var fabAddPost: FloatingActionButton
    private lateinit var clDialogPostArtworkGallery: ConstraintLayout
    private lateinit var clDialogPostArtworkPhoto: ConstraintLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_bookmark)

        civItemViewBookmarkProfilePic = findViewById(R.id.civ_item_view_bookmark_profile_pic)
        tvItemViewBookmarkUsername = findViewById(R.id.tv_item_view_bookmark_username)
        ivItemViewBookmarkPost = findViewById(R.id.iv_item_view_bookmark_post)
        tvItemViewBookmarkTitle = findViewById(R.id.tv_item_view_bookmark_title)
        tvItemViewBookmarkDatePosted = findViewById(R.id.tv_item_view_bookmark_date)
        tvItemViewBookmarkMedium = findViewById(R.id.tv_item_view_bookmark_medium)
        tvItemViewBookmarkDimensions = findViewById(R.id.tv_item_view_bookmark_dimen)
        tvItemViewBookmarkDescription = findViewById(R.id.tv_item_view_bookmark_desc)
        ibItemViewBookmarkBookmark = findViewById(R.id.ib_item_view_bookmark_bookmark)

        initIntent()
        initComponents()
    }

    private fun initIntent() {
        val intent: Intent = intent

        val profilePicture = intent.getIntExtra(Keys.KEY_PROFILE_PICTURE.name, 0)
        val username = intent.getStringExtra(Keys.KEY_USERNAME.name)
        val post = intent.getIntExtra(Keys.KEY_POST.name, 0)
        val title = intent.getStringExtra(Keys.KEY_TITLE.name)
        val datePosted = intent.getStringExtra(Keys.KEY_DATE_POSTED.name)
        val type = intent.getStringExtra(Keys.KEY_MEDIUM.name)
        val dimensions = intent.getStringExtra(Keys.KEY_DIMENSIONS.name)
        val description = intent.getStringExtra(Keys.KEY_DESCRIPTION.name)
        var bookmark = intent.getBooleanExtra(Keys.KEY_BOOKMARK.name, false)

        this.civItemViewBookmarkProfilePic.setImageResource(profilePicture)
        this.tvItemViewBookmarkUsername.text = username
        this.ivItemViewBookmarkPost.setImageResource(post)
        this.tvItemViewBookmarkTitle.text = title
        this.tvItemViewBookmarkDatePosted.text = datePosted
        this.tvItemViewBookmarkMedium.text = type
        this.tvItemViewBookmarkDimensions.text = dimensions
        this.tvItemViewBookmarkDescription.text = description

        updateBookmark(bookmark)

        ibItemViewBookmarkBookmark.setOnClickListener(View.OnClickListener {
            bookmark = !bookmark
            updateBookmark(bookmark)
        })

        civItemViewBookmarkProfilePic.setOnClickListener(View.OnClickListener {
            val intent = Intent(this, ViewUserActivity::class.java)

            intent.putExtra(
                Keys.KEY_PROFILE_PICTURE.name,
                profilePicture
            )
            intent.putExtra(
                Keys.KEY_USERNAME.name,
                username
            )
            intent.putExtra(
                Keys.KEY_BIO.name,
                "Dummy bio"
            )

            startActivity(intent)
        })

        tvItemViewBookmarkUsername.setOnClickListener(View.OnClickListener {
            val intent = Intent(this, ViewUserActivity::class.java)

            intent.putExtra(
                Keys.KEY_PROFILE_PICTURE.name,
                profilePicture
            )
            intent.putExtra(
                Keys.KEY_USERNAME.name,
                username
            )
            intent.putExtra(
                Keys.KEY_BIO.name,
                "Dummy bio"
            )

            startActivity(intent)
        })
    }

    private fun initComponents() {
        setSupportActionBar(findViewById(R.id.toolbar_view_bookmark))
        initActionBar()
        initBottom()
        addPost()
    }

    private fun initBottom() {
        this.bnvViewBookmarkBottom = findViewById(R.id.nv_view_bookmark_bottom)
        this.nsvViewBookmark = findViewById(R.id.nsv_view_bookmark)

        BottomMenuUtil.setFinishBottomMenuListeners(bnvViewBookmarkBottom, this,
            this@ViewBookmarkActivity)
    }

    private fun initActionBar() {
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
    }

    private fun updateBookmark(bookmark: Boolean) {
        if(bookmark) {
            this.ibItemViewBookmarkBookmark.setImageResource(R.drawable.outline_bookmark_24)
            this.ibItemViewBookmarkBookmark.imageTintList = ColorStateList.valueOf(
                ContextCompat.getColor(this.ibItemViewBookmarkBookmark.context, R.color.pinkish_purple)
            )
        } else {
            this.ibItemViewBookmarkBookmark.setImageResource(R.drawable.outline_bookmark_border_24)
            this.ibItemViewBookmarkBookmark.imageTintList = ColorStateList.valueOf(
                ContextCompat.getColor(this.ibItemViewBookmarkBookmark.context, R.color.default_gray)
            )
        }
    }

    private fun addPost() {
        this.btmAddPost = BottomSheetDialog(this@ViewBookmarkActivity)
        this.fabAddPost = findViewById(R.id.fab_view_bookmark_add)

        val view = LayoutInflater.from(this@ViewBookmarkActivity).inflate(R.layout.dialog_post_artwork, null)

        this.fabAddPost.setOnClickListener {
            btmAddPost.setContentView(view)

            this.clDialogPostArtworkGallery = btmAddPost.findViewById(R.id.cl_dialog_post_artwork_gallery)!!
            this.clDialogPostArtworkPhoto = btmAddPost.findViewById(R.id.cl_dialog_post_artwork_photo)!!

            clDialogPostArtworkGallery.setOnClickListener(View.OnClickListener {
                Toast.makeText(this@ViewBookmarkActivity, "Photo chosen from the gallery", Toast.LENGTH_SHORT).show()
                btmAddPost.dismiss()
                val intent = Intent(this@ViewBookmarkActivity, PostArtworkActivity::class.java)
                startActivity(intent)
            })

            clDialogPostArtworkPhoto.setOnClickListener(View.OnClickListener {
                Toast.makeText(this@ViewBookmarkActivity, "Photo taken with the device camera", Toast.LENGTH_SHORT).show()
                btmAddPost.dismiss()
                val intent = Intent(this@ViewBookmarkActivity, PostArtworkActivity::class.java)
                startActivity(intent)
            })

            btmAddPost.show()
        }
    }
}