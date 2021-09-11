package com.mobdeve.gonzales.lee.ong.artemis

import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

/**
 * Class handling the functionalities related to notifying the user and preventing the app
 * from crashing when the Firebase server encounters an error that cannot be resolved with simple
 * toast displays (for example, the remote server is temporarily down or the user submitted a
 * bad request).
 */
class BrokenLinkActivity : AppCompatActivity() {
    /**
     * Clickable text view for opening the email client of the user.
     */
    private lateinit var tvBrokenLinkEmail: TextView

    /**
     * Called when the activity is starting.
     *
     * @param savedInstanceState  If the activity is being re-initialized after previously being
     * shut down then this Bundle contains the data it most recently supplied in
     * <code>onSaveInstanceState(Bundle)</code>. Note: Otherwise it is <code>null</code>.
     * This value may be <code>null</code>.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_broken_link)

        launchEmail()
    }

    /**
     * Opens the email client of the user, sets the title of the mail client chooser, and prefills
     * some details, such as the recipient email address, subject line, and suggested contents of
     * the message body.
     *
     * The recipient email address is automatically set to the email address of the developers:
     * artemis.appmaster@gmail.com
     */
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