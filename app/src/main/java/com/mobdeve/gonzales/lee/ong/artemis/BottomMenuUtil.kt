package com.mobdeve.gonzales.lee.ong.artemis

import android.app.Activity
import android.content.Context
import android.content.Intent
import com.google.android.material.bottomnavigation.BottomNavigationView

class BottomMenuUtil {
    companion object {
        fun setBottomMenuListeners(bnvAccountManagementBottom: BottomNavigationView, from: Activity, packageContext: Context) {
            bnvAccountManagementBottom.setOnItemSelectedListener{ item ->
                when (item.itemId) {
                    R.id.icon_home_profile -> {
                        val intent = Intent(packageContext, BrowseFeedActivity::class.java)
                        from.startActivity(intent)
                        return@setOnItemSelectedListener true
                    }
                    R.id.icon_follow_profile -> {
                        val intent = Intent(packageContext, BrowseFeedFollowedActivity::class.java)
                        from.startActivity(intent)
                        return@setOnItemSelectedListener true
                    }
                    R.id.icon_bookmark_profile -> {
                        val intent = Intent(packageContext, BrowseBookmarksActivity::class.java)
                        from.startActivity(intent)
                        return@setOnItemSelectedListener true
                    }
                    R.id.icon_user_profile -> {
                        val intent = Intent(packageContext, ViewProfileActivity::class.java)
                        from.startActivity(intent)
                        return@setOnItemSelectedListener true
                    }
                }
                false
            }
        }
    }
}