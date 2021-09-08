package com.mobdeve.gonzales.lee.ong.artemis

import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class BrokenLinkActivity : AppCompatActivity() {
    private lateinit var tvBrokenLinkEmail: TextView

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
            emailIntent.putExtra(Intent.EXTRA_EMAIL, arrayOf(EmailUtil.EMAIL_ADDRESS));
            emailIntent.putExtra(Intent.EXTRA_SUBJECT, EmailUtil.SUBJECT_BROKEN_LINK);
            emailIntent.putExtra(Intent.EXTRA_TEXT, EmailUtil.TEXT_BROKEN_LINK);

            startActivity(Intent.createChooser(emailIntent, EmailUtil.TITLE_BROKEN_LINK));
        }
    }
}