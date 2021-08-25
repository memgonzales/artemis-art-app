package com.mobdeve.gonzales.lee.ong.artemis

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.KeyEvent
import android.view.MenuItem
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.Button
import android.widget.EditText
import android.widget.TextView

class SearchActivity : AppCompatActivity() {
    private lateinit var btnSearchTag1: Button
    private lateinit var btnSearchTag2: Button
    private lateinit var btnSearchTag3: Button
    private lateinit var btnSearchTag4: Button
    private lateinit var btnSearchTag5: Button

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
        })

        etSearchBar.setOnEditorActionListener(TextView.OnEditorActionListener { v, actionId, event ->
            if ((event != null && (event.keyCode == KeyEvent.KEYCODE_ENTER)) || (actionId == EditorInfo.IME_ACTION_DONE)) {
                val intent = Intent(this@SearchActivity, SearchResultsActivity::class.java)
                startActivity(intent)
            }
            return@OnEditorActionListener false
        });

        initActionBar()
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
}