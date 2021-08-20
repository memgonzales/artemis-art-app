package com.mobdeve.gonzales.lee.ong.artemis

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.app.AppCompatDelegate.MODE_NIGHT_NO

class LogInActivity : AppCompatActivity() {
    private var btnSignUp: Button? = null
    private var btnLogIn: Button? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_log_in)

        initComponents()
    }

    private fun initComponents() {
        this.btnSignUp = findViewById(R.id.btn_log_in_sign_up)
        this.launchSignUp()

        this.btnLogIn = findViewById(R.id.btn_log_in)
        startBrowsing()
    }

    private fun launchSignUp() {
        this.btnSignUp?.setOnClickListener {
            val i = Intent(this@LogInActivity, SignUpActivity::class.java)
            startActivity(i)
        }
    }

    private fun startBrowsing() {
        this.btnLogIn?.setOnClickListener {
            val i = Intent(this@LogInActivity, ViewPostActivity::class.java)
            startActivity(i)
            finish()
        }
    }
}