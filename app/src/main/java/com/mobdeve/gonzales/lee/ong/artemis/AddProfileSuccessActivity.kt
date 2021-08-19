package com.mobdeve.gonzales.lee.ong.artemis

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class AddProfileSuccessActivity : AppCompatActivity() {
    private var btnStartBrowsing: Button? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_profile_success)

        initComponents()
    }

    private fun initComponents() {
        setSupportActionBar(findViewById(R.id.toolbar_add_profile_success))

        this.btnStartBrowsing = findViewById(R.id.btn_add_profile_browse)
        startBrowsing()
    }

    private fun startBrowsing() {
        this.btnStartBrowsing?.setOnClickListener {
            val i = Intent(this@AddProfileSuccessActivity, BrowseFeedActivity::class.java)
            startActivity(i)
            finish()
        }
    }
}