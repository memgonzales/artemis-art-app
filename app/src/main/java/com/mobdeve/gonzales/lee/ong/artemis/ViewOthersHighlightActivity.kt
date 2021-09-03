package com.mobdeve.gonzales.lee.ong.artemis

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.floatingactionbutton.FloatingActionButton
import de.hdodenhof.circleimageview.CircleImageView

class ViewOthersHighlightActivity : AppCompatActivity() {
    private lateinit var civItemViewOthersHighlightProfilePic: CircleImageView
    private lateinit var tvItemViewOthersHighlightUsername: TextView
    private lateinit var ivItemViewOthersHighlightPost: ImageView
    private lateinit var tvItemViewOthersHighlightTitle: TextView
    private lateinit var tvItemViewOthersHighlightDatePosted: TextView
    private lateinit var tvItemViewOthersHighlightMedium: TextView
    private lateinit var tvItemViewOthersHighlightDimensions: TextView
    private lateinit var tvItemViewOthersHighlightDescription: TextView
    private lateinit var bnvViewOthersHighlightBottom: BottomNavigationView

    private lateinit var btmAddPost: BottomSheetDialog
    private lateinit var fabAddPost: FloatingActionButton
    private lateinit var clDialogPostArtworkGallery: ConstraintLayout
    private lateinit var clDialogPostArtworkPhoto: ConstraintLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_others_highlight)

        civItemViewOthersHighlightProfilePic = findViewById(R.id.civ_item_view_others_highlight_profile_pic)
        tvItemViewOthersHighlightUsername = findViewById(R.id.tv_item_view_others_highlight_username)
        ivItemViewOthersHighlightPost = findViewById(R.id.iv_item_view_others_highlight_post)
        tvItemViewOthersHighlightTitle = findViewById(R.id.tv_item_view_others_highlight_title)
        tvItemViewOthersHighlightDatePosted = findViewById(R.id.tv_item_view_others_highlight_date)
        tvItemViewOthersHighlightMedium = findViewById(R.id.tv_item_view_others_highlight_medium)
        tvItemViewOthersHighlightDimensions = findViewById(R.id.tv_item_view_others_highlight_dimen)
        tvItemViewOthersHighlightDescription = findViewById(R.id.tv_item_view_others_highlight_desc)

        initIntent()
        initComponents()
        initBottom()
        addPost()
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

        this.civItemViewOthersHighlightProfilePic.setImageResource(profilePicture)
        this.tvItemViewOthersHighlightUsername.text = username
        this.ivItemViewOthersHighlightPost.setImageResource(post)
        this.tvItemViewOthersHighlightTitle.text = title
        this.tvItemViewOthersHighlightDatePosted.text = datePosted
        this.tvItemViewOthersHighlightMedium.text = type
        this.tvItemViewOthersHighlightDimensions.text = dimensions
        this.tvItemViewOthersHighlightDescription.text = description

        civItemViewOthersHighlightProfilePic.setOnClickListener {
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
        }

        tvItemViewOthersHighlightUsername.setOnClickListener {
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
        }
    }

    private fun initComponents() {
        setSupportActionBar(findViewById(R.id.toolbar_view_others_highlight))
        initActionBar()
    }

    private fun initBottom() {
        this.bnvViewOthersHighlightBottom = findViewById(R.id.nv_view_others_highlight_bottom)

        BottomMenuUtil.setFinishBottomMenuListeners(bnvViewOthersHighlightBottom, this,
            this@ViewOthersHighlightActivity)
    }

    private fun initActionBar() {
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
    }

    private fun addPost() {
        this.btmAddPost = BottomSheetDialog(this@ViewOthersHighlightActivity)
        this.fabAddPost = findViewById(R.id.fab_view_others_highlight_add)

        val view = LayoutInflater.from(this@ViewOthersHighlightActivity).inflate(R.layout.dialog_post_artwork, null)

        this.fabAddPost.setOnClickListener {
            btmAddPost.setContentView(view)

            this.clDialogPostArtworkGallery = btmAddPost.findViewById(R.id.cl_dialog_post_artwork_gallery)!!
            this.clDialogPostArtworkPhoto = btmAddPost.findViewById(R.id.cl_dialog_post_artwork_photo)!!

            clDialogPostArtworkGallery.setOnClickListener {
                Toast.makeText(
                    this@ViewOthersHighlightActivity,
                    "Photo chosen from the gallery",
                    Toast.LENGTH_SHORT
                ).show()
                btmAddPost.dismiss()
                val intent =
                    Intent(this@ViewOthersHighlightActivity, PostArtworkActivity::class.java)
                startActivity(intent)
            }

            clDialogPostArtworkPhoto.setOnClickListener {
                Toast.makeText(
                    this@ViewOthersHighlightActivity,
                    "Photo taken with the device camera",
                    Toast.LENGTH_SHORT
                ).show()
                btmAddPost.dismiss()
                val intent =
                    Intent(this@ViewOthersHighlightActivity, PostArtworkActivity::class.java)
                startActivity(intent)
            }

            btmAddPost.show()
        }
    }
}