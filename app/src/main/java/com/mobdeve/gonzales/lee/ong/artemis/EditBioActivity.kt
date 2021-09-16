package com.mobdeve.gonzales.lee.ong.artemis

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class EditBioActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_bio)

        initComponents()
    }

    private fun initComponents() {
        setSupportActionBar(findViewById(R.id.toolbar_edit_bio))
        initActionBar()
    }

    /**
     * Adds a back button to the action bar.
     */
    private fun initActionBar() {
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
    }
}