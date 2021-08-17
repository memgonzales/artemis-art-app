package com.mobdeve.gonzales.lee.ong.artemis

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class LogInActivity : AppCompatActivity() {
    private var btnSignUp: Button? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_log_in)

        initComponents()
    }

    private fun initComponents() {
        this.btnSignUp = findViewById(R.id.btn_log_in_sign_up)
        this.launchSignUp()
    }

    private fun launchSignUp() {
        this.btnSignUp?.setOnClickListener {
            val i = Intent(this@LogInActivity, SignUpActivity::class.java)
            startActivity(i)
        }
    }
}