package com.mobdeve.gonzales.lee.ong.artemis

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.android.material.textfield.TextInputEditText

class EditEmailActivity : AppCompatActivity() {
    private lateinit var tietEditEmail: TextInputEditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_email)

        initComponents()
    }

    private fun initComponents() {
        this.tietEditEmail = findViewById(R.id.tiet_edit_email)
        tietEditEmail.isFocusable = false

        setSupportActionBar(findViewById(R.id.toolbar_edit_email))
        initActionBar()
    }

    private fun initActionBar() {
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
    }
}