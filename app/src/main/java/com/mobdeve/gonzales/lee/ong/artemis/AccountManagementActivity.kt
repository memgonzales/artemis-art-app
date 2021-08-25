package com.mobdeve.gonzales.lee.ong.artemis

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import com.google.android.material.bottomnavigation.BottomNavigationView

class AccountManagementActivity : AppCompatActivity() {
    private lateinit var clAccountManagementDelete: ConstraintLayout
    private lateinit var bnvAccountManagementBottom: BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_account_management)

        initContent()
        initComponents()
    }

    private fun initContent() {
        this.clAccountManagementDelete = findViewById(R.id.cl_account_management_delete)
        this.bnvAccountManagementBottom = findViewById(R.id.nv_account_management_bottom)

        clAccountManagementDelete.setOnClickListener(View.OnClickListener {
            deleteDialog()
        })
    }

    private fun deleteDialog() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Delete Profile")
        builder.setMessage("Are you sure you want to delete your profile? This action cannot be reversed.")
        builder.setPositiveButton(
            "Yes"
        ) { dialog, which ->
            Toast.makeText(this@AccountManagementActivity, "Account deleted", Toast.LENGTH_SHORT).show()
            val intent = Intent(this@AccountManagementActivity, LogInActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
        }
        builder.setNegativeButton(
            "No"
        ) { dialog, which -> }
        builder.create().show()
    }

    private fun initComponents() {
        setSupportActionBar(findViewById(R.id.toolbar_account_management))
        initBottom()
        initActionBar()
    }

    private fun initBottom() {
        bnvAccountManagementBottom.setOnItemSelectedListener{ item ->
            when (item.itemId) {
                R.id.icon_home_profile -> {
                    val intent = Intent(this@AccountManagementActivity, BrowseFeedActivity::class.java)
                    startActivity(intent)
                    return@setOnItemSelectedListener true
                }
                R.id.icon_follow_profile -> {
                    val intent = Intent(this@AccountManagementActivity, BrowseFeedFollowedActivity::class.java)
                    startActivity(intent)
                    return@setOnItemSelectedListener true
                }
                R.id.icon_bookmark_profile -> {
                    val intent = Intent(this@AccountManagementActivity, BrowseBookmarksActivity::class.java)
                    startActivity(intent)
                    return@setOnItemSelectedListener true
                }
                R.id.icon_user_profile -> {
                    val intent = Intent(this@AccountManagementActivity, ViewProfileActivity::class.java)
                    startActivity(intent)
                    return@setOnItemSelectedListener true
                }
            }
            false
        }
    }

    private fun initActionBar() {
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
    }
}