package com.mobdeve.gonzales.lee.ong.artemis

import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class BrokenLinkActivity : AppCompatActivity() {
    private lateinit var tvBrokenLinkEmail: TextView
    private val emailAddress: String = "artemis.appmaster@gmail.com"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_broken_link)

        launchEmail()
    }

    private fun launchEmail() {
        tvBrokenLinkEmail = findViewById(R.id.tv_broken_link_email)
        tvBrokenLinkEmail.setOnClickListener {
            val emailIntent = Intent(Intent.ACTION_SEND);

            emailIntent.type = "message/rfc822";
            emailIntent.putExtra(Intent.EXTRA_EMAIL, arrayOf(emailAddress));
            emailIntent.putExtra(Intent.EXTRA_SUBJECT, "[User Report] Persistent Broken Link");
            emailIntent.putExtra(Intent.EXTRA_TEXT, "Place your username, and describe " +
                    "what you were doing when this error occurred");

            startActivity(Intent.createChooser(emailIntent, "Send a report"));
        }
    }
}