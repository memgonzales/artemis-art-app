package com.mobdeve.gonzales.lee.ong.artemis

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class AddProfilePictureActivity : AppCompatActivity() {
    private var btnAddProfilePic: Button? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_profile_picture)

        initComponents()
    }

    private fun initComponents() {
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