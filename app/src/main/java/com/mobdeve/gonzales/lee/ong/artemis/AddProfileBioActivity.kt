package com.mobdeve.gonzales.lee.ong.artemis

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class AddProfileBioActivity : AppCompatActivity() {
    private var btnAddBio: Button? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_profile_bio)

        initComponents()
    }

    private fun initComponents() {
        setSupportActionBar(findViewById(R.id.toolbar_add_profile_bio))
        initActionBar()

        this.btnAddBio = findViewById(R.id.btn_add_profile_bio_add)
        launchAddProfilePic()
    }

    private fun initActionBar() {
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
    }

    private fun launchAddProfilePic() {
        this.btnAddBio?.setOnClickListener {
            val i = Intent(this@AddProfileBioActivity, AddProfilePictureActivity::class.java)
            startActivity(i)
            finish()
        }
    }
}