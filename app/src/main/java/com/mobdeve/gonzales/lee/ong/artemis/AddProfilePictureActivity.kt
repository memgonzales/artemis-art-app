package com.mobdeve.gonzales.lee.ong.artemis

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import de.hdodenhof.circleimageview.CircleImageView

class AddProfilePictureActivity : AppCompatActivity() {
    private var btnAddProfilePic: Button? = null

    private var civUploadImg: CircleImageView? = null
    private var ivCameraPic: ImageView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_profile_picture)

        initComponents()
    }

    private fun initComponents() {
        this.civUploadImg = findViewById(R.id.civ_item_feed_profile_pic)
        this.ivCameraPic = findViewById(R.id.iv_add_profile_pic_camera)

        setSupportActionBar(findViewById(R.id.toolbar_add_profile_pic))

        this.btnAddProfilePic = findViewById(R.id.btn_add_profile_pic_add)
        launchAddBio()
    }

    private fun launchAddBio() {
        this.btnAddProfilePic?.setOnClickListener {


            val i = Intent(this@AddProfilePictureActivity, AddProfileBioActivity::class.java)
            startActivity(i)
        }
    }
}