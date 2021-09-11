package com.mobdeve.gonzales.lee.ong.artemis

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import de.hdodenhof.circleimageview.CircleImageView

class ViewOthersHighlightUnregisteredActivity : AppCompatActivity() {
    private lateinit var civItemViewOthersHighlightUnregisteredProfilePic: CircleImageView
    private lateinit var tvItemViewOthersHighlightUnregisteredUsername: TextView
    private lateinit var ivItemViewOthersHighlightUnregisteredPost: ImageView
    private lateinit var tvItemViewOthersHighlightUnregisteredTitle: TextView
    private lateinit var tvItemViewOthersHighlightUnregisteredDatePosted: TextView
    private lateinit var tvItemViewOthersHighlightUnregisteredMedium: TextView
    private lateinit var tvItemViewOthersHighlightUnregisteredDimensions: TextView
    private lateinit var tvItemViewOthersHighlightUnregisteredDescription: TextView

    private lateinit var bnvViewOthersHighlightUnregisteredBottom: BottomNavigationView
    private lateinit var fabAddPost: FloatingActionButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_others_highlight_unregistered)

        civItemViewOthersHighlightUnregisteredProfilePic = findViewById(R.id.civ_item_view_others_highlight_unregistered_profile_pic)
        tvItemViewOthersHighlightUnregisteredUsername = findViewById(R.id.tv_item_view_others_highlight_unregistered_username)
        ivItemViewOthersHighlightUnregisteredPost = findViewById(R.id.iv_item_view_others_highlight_unregistered_post)
        tvItemViewOthersHighlightUnregisteredTitle = findViewById(R.id.tv_item_view_others_highlight_unregistered_title)
        tvItemViewOthersHighlightUnregisteredDatePosted = findViewById(R.id.tv_item_view_others_highlight_unregistered_date)
        tvItemViewOthersHighlightUnregisteredMedium = findViewById(R.id.tv_item_view_others_highlight_unregistered_medium)
        tvItemViewOthersHighlightUnregisteredDimensions = findViewById(R.id.tv_item_view_others_highlight_unregistered_dimen)
        tvItemViewOthersHighlightUnregisteredDescription = findViewById(R.id.tv_item_view_others_highlight_unregistered_desc)

        initIntent()
        initComponents()
        initBottom()
    }

    private fun initIntent() {
        val intent: Intent = intent

        val profilePicture = intent.getStringExtra(Keys.KEY_PROFILE_PICTURE.name)
        val username = intent.getStringExtra(Keys.KEY_USERNAME.name)

        val postImg = intent.getStringExtra(Keys.KEY_POST.name)
        val title = intent.getStringExtra(Keys.KEY_TITLE.name)
        val datePosted = intent.getStringExtra(Keys.KEY_DATE_POSTED.name)
        val medium = intent.getStringExtra(Keys.KEY_MEDIUM.name)
        val dimensions = intent.getStringExtra(Keys.KEY_DIMENSIONS.name)
        val description = intent.getStringExtra(Keys.KEY_DESCRIPTION.name)

        //this.civItemViewOthersHighlightUnregisteredProfilePic.setImageResource(profilePicture)

        Glide.with(this@ViewOthersHighlightUnregisteredActivity)
            .load(profilePicture)
            .placeholder(R.drawable.chibi_artemis_hd)
            .error(R.drawable.chibi_artemis_hd)
            .into(this.civItemViewOthersHighlightUnregisteredProfilePic)

        this.tvItemViewOthersHighlightUnregisteredUsername.text = username
        //this.ivItemViewOthersHighlightUnregisteredPost.setImageResource(post)
        Glide.with(this@ViewOthersHighlightUnregisteredActivity)
            .load(postImg)
            .placeholder(R.drawable.placeholder)
            .error(R.drawable.placeholder)
            .into(this.ivItemViewOthersHighlightUnregisteredPost)

        if (!title.isNullOrEmpty()){
            this.tvItemViewOthersHighlightUnregisteredTitle.visibility = View.VISIBLE
            this.tvItemViewOthersHighlightUnregisteredTitle.text = title
        }
        else{
            this.tvItemViewOthersHighlightUnregisteredTitle.visibility = View.INVISIBLE
        }

        this.tvItemViewOthersHighlightUnregisteredDatePosted.text = datePosted

        if(!medium.isNullOrEmpty()){
            this.tvItemViewOthersHighlightUnregisteredMedium.visibility = View.VISIBLE
            this.tvItemViewOthersHighlightUnregisteredMedium.text = medium
        }
        else{
            this.tvItemViewOthersHighlightUnregisteredMedium.visibility = View.GONE
        }

        if(!dimensions.isNullOrEmpty()){
            this.tvItemViewOthersHighlightUnregisteredDimensions.visibility = View.VISIBLE
            this.tvItemViewOthersHighlightUnregisteredDimensions.text = dimensions
        }
        else{
            this.tvItemViewOthersHighlightUnregisteredDimensions.visibility = View.GONE
        }

        if(!description.isNullOrEmpty()){
            this.tvItemViewOthersHighlightUnregisteredDescription.visibility = View.VISIBLE
            this.tvItemViewOthersHighlightUnregisteredDescription.text = description
        }
        else{
            this.tvItemViewOthersHighlightUnregisteredDescription.visibility = View.GONE
        }

        civItemViewOthersHighlightUnregisteredProfilePic.setOnClickListener {
            val intent = Intent(this, ViewUserUnregisteredActivity::class.java)

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

        tvItemViewOthersHighlightUnregisteredUsername.setOnClickListener {
            val intent = Intent(this, ViewUserUnregisteredActivity::class.java)

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
        setSupportActionBar(findViewById(R.id.toolbar_view_others_highlight_unregistered))
        initActionBar()
    }

    private fun initBottom() {
        this.bnvViewOthersHighlightUnregisteredBottom = findViewById(R.id.nv_view_others_highlight_unregistered_bottom)
        this.fabAddPost = findViewById(R.id.fab_view_others_highlight_unregistered_add)

        BottomMenuUtil.setFinishBottomMenuListenersUnregistered(bnvViewOthersHighlightUnregisteredBottom, this,
            this@ViewOthersHighlightUnregisteredActivity)

        fabAddPost.setOnClickListener {
            Toast.makeText(
                this@ViewOthersHighlightUnregisteredActivity,
                "Log in or create an account to use this feature",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    private fun initActionBar() {
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
    }
}