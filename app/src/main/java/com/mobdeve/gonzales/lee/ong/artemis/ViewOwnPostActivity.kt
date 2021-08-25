package com.mobdeve.gonzales.lee.ong.artemis

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import de.hdodenhof.circleimageview.CircleImageView

class ViewOwnPostActivity : AppCompatActivity() {
    private lateinit var civItemViewOwnPostProfilePic: CircleImageView
    private lateinit var tvItemViewOwnPostUsername: TextView
    private lateinit var ivItemViewOwnPostPostImg: ImageView
    private lateinit var tvItemViewOwnPostTitle: TextView
    private lateinit var tvItemViewOwnPostDatePosted: TextView
    private lateinit var tvItemViewOwnPostMedium: TextView
    private lateinit var tvItemViewOwnPostDimensions: TextView
    private lateinit var tvItemViewOwnPostDescription: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_own_post)

        civItemViewOwnPostProfilePic = findViewById(R.id.civ_item_view_own_post_profile_pic)
        tvItemViewOwnPostUsername = findViewById(R.id.tv_item_view_own_post_username)

        initComponents()
    }

    private fun initComponents() {
        setSupportActionBar(findViewById(R.id.toolbar_view_own_post))
        initActionBar()
    }

    private fun initActionBar() {
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
    }
}