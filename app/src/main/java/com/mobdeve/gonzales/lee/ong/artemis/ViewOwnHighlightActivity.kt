package com.mobdeve.gonzales.lee.ong.artemis

import android.content.Intent
import android.content.res.ColorStateList
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.core.widget.NestedScrollView
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.floatingactionbutton.FloatingActionButton
import de.hdodenhof.circleimageview.CircleImageView

class ViewOwnHighlightActivity : AppCompatActivity() {
    private lateinit var civItemViewOwnHighlightProfilePic: CircleImageView
    private lateinit var tvItemViewOwnHighlightUsername: TextView
    private lateinit var ivItemViewOwnHighlightPost: ImageView
    private lateinit var tvItemViewOwnHighlightTitle: TextView
    private lateinit var tvItemViewOwnHighlightDatePosted: TextView
    private lateinit var tvItemViewOwnHighlightMedium: TextView
    private lateinit var tvItemViewOwnHighlightDimensions: TextView
    private lateinit var tvItemViewOwnHighlightDescription: TextView
    private lateinit var ibItemViewOwnHighlightHighlight: ImageButton
    private lateinit var bnvViewOwnHighlightBottom: BottomNavigationView

    private lateinit var btmAddPost: BottomSheetDialog
    private lateinit var fabAddPost: FloatingActionButton
    private lateinit var clDialogPostArtworkGallery: ConstraintLayout
    private lateinit var clDialogPostArtworkPhoto: ConstraintLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_own_highlight)

        civItemViewOwnHighlightProfilePic = findViewById(R.id.civ_item_view_own_highlight_profile_pic)
        tvItemViewOwnHighlightUsername = findViewById(R.id.tv_item_view_own_highlight_username)
        ivItemViewOwnHighlightPost = findViewById(R.id.iv_item_view_own_highlight_post)
        tvItemViewOwnHighlightTitle = findViewById(R.id.tv_item_view_own_highlight_title)
        tvItemViewOwnHighlightDatePosted = findViewById(R.id.tv_item_view_own_highlight_date)
        tvItemViewOwnHighlightMedium = findViewById(R.id.tv_item_view_own_highlight_medium)
        tvItemViewOwnHighlightDimensions = findViewById(R.id.tv_item_view_own_highlight_dimen)
        tvItemViewOwnHighlightDescription = findViewById(R.id.tv_item_view_own_highlight_desc)
        ibItemViewOwnHighlightHighlight = findViewById(R.id.ib_item_view_own_highlight_highlight)

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
        var highlight = intent.getBooleanExtra(Keys.KEY_HIGHLIGHT.name, false)

        this.civItemViewOwnHighlightProfilePic.setImageResource(profilePicture)
        this.tvItemViewOwnHighlightUsername.text = username
        this.ivItemViewOwnHighlightPost.setImageResource(post)
        this.tvItemViewOwnHighlightTitle.text = title
        this.tvItemViewOwnHighlightDatePosted.text = datePosted
        this.tvItemViewOwnHighlightMedium.text = type
        this.tvItemViewOwnHighlightDimensions.text = dimensions
        this.tvItemViewOwnHighlightDescription.text = description

        updateHighlight(highlight)

        ibItemViewOwnHighlightHighlight.setOnClickListener(View.OnClickListener {
            if (highlight) {
                highlight = false
                updateHighlight(highlight)
            } else {
                highlight = true
                updateHighlight(highlight)
                Toast.makeText(this@ViewOwnHighlightActivity, "Added to your Highlights", Toast.LENGTH_SHORT).show()
            }
        })

        civItemViewOwnHighlightProfilePic.setOnClickListener(View.OnClickListener {
            val intent = Intent(this, ViewProfileActivity::class.java)
            startActivity(intent)
        })

        tvItemViewOwnHighlightUsername.setOnClickListener(View.OnClickListener {
            val intent = Intent(this, ViewProfileActivity::class.java)
            startActivity(intent)
        })
    }

    private fun initComponents() {
        setSupportActionBar(findViewById(R.id.toolbar_view_own_highlight))
        initActionBar()
    }

    private fun initBottom() {
        this.bnvViewOwnHighlightBottom = findViewById(R.id.nv_view_own_highlight_bottom)

        BottomMenuUtil.setFinishBottomMenuListeners(bnvViewOwnHighlightBottom, this,
            this@ViewOwnHighlightActivity)
    }

    private fun initActionBar() {
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
    }

    private fun updateHighlight(highlight: Boolean) {
        if(highlight) {
            this.ibItemViewOwnHighlightHighlight.setImageResource(R.drawable.baseline_star_24)
            this.ibItemViewOwnHighlightHighlight.imageTintList = ColorStateList.valueOf(
                ContextCompat.getColor(this.ibItemViewOwnHighlightHighlight.context, R.color.pinkish_purple)
            )
        } else {
            this.ibItemViewOwnHighlightHighlight.setImageResource(R.drawable.outline_star_border_24)
            this.ibItemViewOwnHighlightHighlight.imageTintList = ColorStateList.valueOf(
                ContextCompat.getColor(this.ibItemViewOwnHighlightHighlight.context, R.color.default_gray)
            )
        }
    }

    private fun addPost() {
        this.btmAddPost = BottomSheetDialog(this@ViewOwnHighlightActivity)
        this.fabAddPost = findViewById(R.id.fab_view_own_highlight_add)

        val view = LayoutInflater.from(this@ViewOwnHighlightActivity).inflate(R.layout.dialog_post_artwork, null)

        this.fabAddPost.setOnClickListener {
            btmAddPost.setContentView(view)

            this.clDialogPostArtworkGallery = btmAddPost.findViewById(R.id.cl_dialog_post_artwork_gallery)!!
            this.clDialogPostArtworkPhoto = btmAddPost.findViewById(R.id.cl_dialog_post_artwork_photo)!!

            clDialogPostArtworkGallery.setOnClickListener(View.OnClickListener {
                Toast.makeText(this@ViewOwnHighlightActivity, "Photo chosen from the gallery", Toast.LENGTH_SHORT).show()
                btmAddPost.dismiss()
                val intent = Intent(this@ViewOwnHighlightActivity, PostArtworkActivity::class.java)
                startActivity(intent)
            })

            clDialogPostArtworkPhoto.setOnClickListener(View.OnClickListener {
                Toast.makeText(this@ViewOwnHighlightActivity, "Photo taken with the device camera", Toast.LENGTH_SHORT).show()
                btmAddPost.dismiss()
                val intent = Intent(this@ViewOwnHighlightActivity, PostArtworkActivity::class.java)
                startActivity(intent)
            })

            btmAddPost.show()
        }
    }
}