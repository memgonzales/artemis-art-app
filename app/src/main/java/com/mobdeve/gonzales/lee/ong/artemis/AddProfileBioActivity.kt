package com.mobdeve.gonzales.lee.ong.artemis

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.google.android.material.textfield.TextInputEditText

class AddProfileBioActivity : AppCompatActivity() {
    private var btnAddBio: Button? = null

    private var tielBio: TextInputEditText? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_profile_bio)

        initComponents()
    }

    private fun initComponents() {
        this.tielBio = findViewById(R.id.tiel_add_profile_bio)

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

            var bio: String? = tielBio?.getText().toString().trim()

            val i = Intent(this@AddProfileBioActivity, AddProfileSuccessActivity::class.java)
            startActivity(i)
        }
    }
}