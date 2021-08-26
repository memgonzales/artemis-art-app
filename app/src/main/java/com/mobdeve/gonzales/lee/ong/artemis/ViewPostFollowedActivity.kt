package com.mobdeve.gonzales.lee.ong.artemis

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout

class ViewPostFollowedActivity : AppCompatActivity() {
    private lateinit var srlViewPostFollowed: SwipeRefreshLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_post_followed)

        initComponents()
    }

    private fun initComponents() {
        setSupportActionBar(findViewById(R.id.toolbar_view_post_followed))
        initActionBar()
        initSwipeRefresh()
    }

    private fun initSwipeRefresh() {
        this.srlViewPostFollowed = findViewById(R.id.srl_view_post_followed)
        srlViewPostFollowed.setOnRefreshListener {
            onRefresh();
        }

        srlViewPostFollowed.setColorSchemeResources(R.color.purple_main,
            R.color.pinkish_purple,
            R.color.purple_pics_lighter,
            R.color.pinkish_purple_lighter);
    }

    private fun onRefresh() {
        Handler(Looper.getMainLooper()).postDelayed({
            srlViewPostFollowed.isRefreshing = false
        }, AnimationDuration.REFRESH_TIMEOUT.toLong())
    }

    private fun initActionBar() {
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
    }
}