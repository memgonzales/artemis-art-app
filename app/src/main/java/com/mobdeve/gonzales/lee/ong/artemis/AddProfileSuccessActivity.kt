package com.mobdeve.gonzales.lee.ong.artemis

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

/**
 * Class handling the functionalities related to directing the user to start browsing the feed
 * after successfully completing user profile customization.
 *
 * @constructor Creates an activity for directing the user to start browsing the feed after
 * successfully completing user profile customization.
 */
class AddProfileSuccessActivity : AppCompatActivity() {
    /**
     * Button to direct the user to start browsing the feed after successful sign-up and user
     * profile customization.
     */
    private lateinit var btnStartBrowsing: Button

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
        setContentView(R.layout.activity_add_profile_success)

        initComponents()
    }

    /**
     * Initializes the components of the activity.
     */
    private fun initComponents() {
        setSupportActionBar(findViewById(R.id.toolbar_add_profile_success))

        this.btnStartBrowsing = findViewById(R.id.btn_add_profile_browse)
        startBrowsing()
    }

    /**
     * Directs the user to start browsing the feed after successful sign-up and user profile customization.
     */
    private fun startBrowsing() {
        this.btnStartBrowsing.setOnClickListener {
            val i = Intent(this@AddProfileSuccessActivity, BrowseFeedActivity::class.java)
            startActivity(i)
            finish()
        }
    }

    /**
     * Called when the activity has detected the user's press of the back key.
     */
    override fun onBackPressed() {
        val i = Intent(this@AddProfileSuccessActivity, BrowseFeedActivity::class.java)
        startActivity(i)
        finish()
    }
}