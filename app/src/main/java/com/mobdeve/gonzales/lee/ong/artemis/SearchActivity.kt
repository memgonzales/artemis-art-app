package com.mobdeve.gonzales.lee.ong.artemis

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.floatingactionbutton.FloatingActionButton

class SearchActivity : AppCompatActivity() {
    private lateinit var btnSearchTag1: Button
    private lateinit var btnSearchTag2: Button
    private lateinit var btnSearchTag3: Button
    private lateinit var btnSearchTag4: Button
    private lateinit var btnSearchTag5: Button

    private lateinit var bnvSearchBottom: BottomNavigationView

    private lateinit var btmAddPost: BottomSheetDialog
    private lateinit var fabAddPost: FloatingActionButton
    private lateinit var clDialogPostArtworkGallery: ConstraintLayout
    private lateinit var clDialogPostArtworkPhoto: ConstraintLayout

    private lateinit var etSearchBar: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)

        initComponents()
    }

    private fun initComponents() {
        setSupportActionBar(findViewById(R.id.toolbar_search))

        this.btnSearchTag1 = findViewById(R.id.btn_search_tag1)
        this.btnSearchTag2 = findViewById(R.id.btn_search_tag2)
        this.btnSearchTag3 = findViewById(R.id.btn_search_tag3)
        this.btnSearchTag4 = findViewById(R.id.btn_search_tag4)
        this.btnSearchTag5 = findViewById(R.id.btn_search_tag5)

        this.etSearchBar = findViewById(R.id.et_search_bar)

        btnSearchTag1.setOnClickListener(View.OnClickListener {
            val intent = Intent(this@SearchActivity, SearchResultsActivity::class.java)
            startActivity(intent)
            finish()
        })

        btnSearchTag2.setOnClickListener(View.OnClickListener {
            val intent = Intent(this@SearchActivity, SearchResultsActivity::class.java)
            startActivity(intent)
            finish()
        })

        btnSearchTag3.setOnClickListener(View.OnClickListener {
            val intent = Intent(this@SearchActivity, SearchResultsActivity::class.java)
            startActivity(intent)
            finish()
        })

        btnSearchTag4.setOnClickListener(View.OnClickListener {
            val intent = Intent(this@SearchActivity, SearchResultsActivity::class.java)
            startActivity(intent)
            finish()
        })

        btnSearchTag5.setOnClickListener(View.OnClickListener {
            val intent = Intent(this@SearchActivity, SearchResultsActivity::class.java)
            startActivity(intent)
            finish()
        })

        etSearchBar.setOnEditorActionListener(TextView.OnEditorActionListener { v, actionId, event ->
            if ((event != null && (event.keyCode == KeyEvent.KEYCODE_ENTER)) || (actionId == EditorInfo.IME_ACTION_DONE)) {
                val intent = Intent(this@SearchActivity, SearchResultsActivity::class.java)
                startActivity(intent)
            }
            return@OnEditorActionListener false
        });

        initActionBar()
        initBottom()
        addPost()
    }

    private fun initActionBar() {
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId

        when(id) {
            android.R.id.home -> {
                onBackPressed()
                return true
            } else -> {
            return super.onOptionsItemSelected(item)
            }
        }

        return super.onOptionsItemSelected(item)
    }

    private fun initBottom() {
        this.bnvSearchBottom = findViewById(R.id.nv_search_bottom)

        bnvSearchBottom.setOnItemSelectedListener{ item ->
            when (item.itemId) {
                R.id.icon_home_search_bottom -> {
                    val intent = Intent(this@SearchActivity, BrowseFeedActivity::class.java)
                    startActivity(intent)
                    return@setOnItemSelectedListener true
                }
                R.id.icon_follow_search_bottom -> {
                    val intent = Intent(this@SearchActivity, BrowseFeedFollowedActivity::class.java)
                    startActivity(intent)
                    return@setOnItemSelectedListener true
                }
                R.id.icon_bookmark_search_bottom -> {
                    val intent = Intent(this@SearchActivity, BrowseBookmarksActivity::class.java)
                    startActivity(intent)
                    return@setOnItemSelectedListener true
                }
                R.id.icon_user_search_bottom -> {
                    val intent = Intent(this@SearchActivity, ViewProfileActivity::class.java)
                    startActivity(intent)
                    return@setOnItemSelectedListener true
                }
            }
            false
        }
    }

    private fun addPost() {
        this.btmAddPost = BottomSheetDialog(this@SearchActivity)
        this.fabAddPost = findViewById(R.id.fab_search_add)

        val view = LayoutInflater.from(this@SearchActivity).inflate(R.layout.dialog_post_artwork, null)

        this.fabAddPost.setOnClickListener {
            btmAddPost.setContentView(view)

            this.clDialogPostArtworkGallery = btmAddPost.findViewById(R.id.cl_dialog_post_artwork_gallery)!!
            this.clDialogPostArtworkPhoto = btmAddPost.findViewById(R.id.cl_dialog_post_artwork_photo)!!

            clDialogPostArtworkGallery.setOnClickListener(View.OnClickListener {
                Toast.makeText(this@SearchActivity, "Photo chosen from the gallery", Toast.LENGTH_SHORT).show()
                btmAddPost.dismiss()
                val intent = Intent(this@SearchActivity, PostArtworkActivity::class.java)
                startActivity(intent)
            })

            clDialogPostArtworkPhoto.setOnClickListener(View.OnClickListener {
                Toast.makeText(this@SearchActivity, "Photo taken with the device camera", Toast.LENGTH_SHORT).show()
                btmAddPost.dismiss()
                val intent = Intent(this@SearchActivity, PostArtworkActivity::class.java)
                startActivity(intent)
            })

            btmAddPost.show()
        }
    }
}