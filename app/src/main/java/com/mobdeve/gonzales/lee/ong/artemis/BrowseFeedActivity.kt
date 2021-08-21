package com.mobdeve.gonzales.lee.ong.artemis

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.widget.ActionMenuView
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class BrowseFeedActivity : AppCompatActivity() {

    private lateinit var dataPosts: ArrayList<Post>
    private lateinit var rvFeed: RecyclerView
    private lateinit var feedAdapter: FeedAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_browse_feed)

        initComponents()
        initRecyclerView()
    }

    private fun initComponents() {
        setSupportActionBar(findViewById(R.id.toolbar_feed))
    }

    private fun initRecyclerView() {
        this.dataPosts = DataHelper.loadPostData();

        this.rvFeed = findViewById(R.id.rv_feed);
        this.rvFeed.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);

        this.feedAdapter = FeedAdapter(this.dataPosts);
        this.rvFeed.adapter = feedAdapter;
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.menu_feed, menu)

        return true
    }
}