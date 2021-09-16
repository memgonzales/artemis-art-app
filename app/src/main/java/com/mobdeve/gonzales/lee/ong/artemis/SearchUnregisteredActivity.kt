package com.mobdeve.gonzales.lee.ong.artemis

import android.content.Intent
import android.os.Bundle
import android.view.KeyEvent
import android.view.MenuItem
import android.view.inputmethod.EditorInfo
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.floatingactionbutton.FloatingActionButton

class SearchUnregisteredActivity : AppCompatActivity() {
    /**
     * Button for searching using the first search tag.
     */
    private lateinit var btnSearchTag1: Button

    /**
     * Button for searching using the second search tag.
     */
    private lateinit var btnSearchTag2: Button

    /**
     * Button for searching using the third search tag.
     */
    private lateinit var btnSearchTag3: Button

    /**
     * Button for searching using the fourth search tag.
     */
    private lateinit var btnSearchTag4: Button

    /**
     * Button for searching using the fifth search tag.
     */
    private lateinit var btnSearchTag5: Button

    /**
     * Bottom navigation view containing the menu items for Home, Followed, Bookmarks, and Profile.
     */
    private lateinit var bnvSearchBottom: BottomNavigationView

    /**
     * Floating action button for posting an artwork.
     */
    private lateinit var fabAddPost: FloatingActionButton

    /**
     * Input field for the user's search query.
     */
    private lateinit var etSearchBar: EditText

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
        setContentView(R.layout.activity_search_unregistered)

        initComponents()
        initBottom()
    }

    /**
     * Initializes the components of the activity.
     */
    private fun initComponents() {
        setSupportActionBar(findViewById(R.id.toolbar_search_unregistered))

        this.btnSearchTag1 = findViewById(R.id.btn_search_unregistered_tag1)
        this.btnSearchTag2 = findViewById(R.id.btn_search_unregistered_tag2)
        this.btnSearchTag3 = findViewById(R.id.btn_search_unregistered_tag3)
        this.btnSearchTag4 = findViewById(R.id.btn_search_unregistered_tag4)
        this.btnSearchTag5 = findViewById(R.id.btn_search_unregistered_tag5)

        this.etSearchBar = findViewById(R.id.et_search_unregistered_bar)

        btnSearchTag1.setOnClickListener {
            val intent = Intent(
                this@SearchUnregisteredActivity,
                SearchResultsUnregisteredActivity::class.java
            )
            intent.putExtra(
                Keys.KEY_SEARCH.name,
                btnSearchTag1.text.toString().trim()
            )
            startActivity(intent)
            finish()
        }

        btnSearchTag2.setOnClickListener {
            val intent = Intent(
                this@SearchUnregisteredActivity,
                SearchResultsUnregisteredActivity::class.java
            )
            intent.putExtra(
                Keys.KEY_SEARCH.name,
                btnSearchTag2.text.toString().trim()
            )
            startActivity(intent)
            finish()
        }

        btnSearchTag3.setOnClickListener {
            val intent = Intent(
                this@SearchUnregisteredActivity,
                SearchResultsUnregisteredActivity::class.java
            )
            intent.putExtra(
                Keys.KEY_SEARCH.name,
                btnSearchTag3.text.toString().trim()
            )
            startActivity(intent)
            finish()
        }

        btnSearchTag4.setOnClickListener {
            val intent = Intent(
                this@SearchUnregisteredActivity,
                SearchResultsUnregisteredActivity::class.java
            )
            intent.putExtra(
                Keys.KEY_SEARCH.name,
                btnSearchTag4.text.toString().trim()
            )
            startActivity(intent)
            finish()
        }

        btnSearchTag5.setOnClickListener {
            val intent = Intent(
                this@SearchUnregisteredActivity,
                SearchResultsUnregisteredActivity::class.java
            )
            intent.putExtra(
                Keys.KEY_SEARCH.name,
                btnSearchTag5.text.toString().trim()
            )
            startActivity(intent)
            finish()
        }

        etSearchBar.setOnEditorActionListener(TextView.OnEditorActionListener { _, actionId, event ->
            if ((event != null && (event.keyCode == KeyEvent.KEYCODE_ENTER)) || (actionId == EditorInfo.IME_ACTION_DONE)) {
                val intent = Intent(this@SearchUnregisteredActivity, SearchResultsUnregisteredActivity::class.java)
                intent.putExtra(
                    Keys.KEY_SEARCH.name,
                    etSearchBar.text.toString().trim()
                )
                startActivity(intent)
            }
            return@OnEditorActionListener false
        })

        initActionBar()
    }

    /**
     * Sets the listeners for the menu selection found in the bottom navigation view.
     */
    private fun initBottom() {
        this.bnvSearchBottom = findViewById(R.id.nv_search_unregistered_bottom)
        this.fabAddPost = findViewById(R.id.fab_search_unregistered_add)

        BottomMenuUtil.setBottomMenuListenersUnregistered(bnvSearchBottom,
            this,this@SearchUnregisteredActivity)

        fabAddPost.setOnClickListener {
            Toast.makeText(this@SearchUnregisteredActivity,"Log in or create an account to use this feature", Toast.LENGTH_SHORT).show()
        }
    }

    /**
     * Adds a back button to the action bar.
     */
    private fun initActionBar() {
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
    }

    /**
     * This hook is called whenever an item in your options menu is selected.
     *
     * @param item The menu item that was selected. This value cannot be <code>null</code>.
     * @return <code>false</code> to allow normal menu processing to proceed; <code>true</code>
     * to consume it here.
     */
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId) {
            android.R.id.home -> {
                onBackPressed()
                true
            } else -> {
                super.onOptionsItemSelected(item)
            }
        }
    }
}