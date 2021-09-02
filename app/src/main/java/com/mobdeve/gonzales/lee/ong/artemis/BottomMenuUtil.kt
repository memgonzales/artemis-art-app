package com.mobdeve.gonzales.lee.ong.artemis

import android.app.Activity
import android.content.Context
import android.content.Intent
import com.google.android.material.bottomnavigation.BottomNavigationView

class BottomMenuUtil {
    companion object {
        private const val HOME: Int = 1
        private const val FOLLOW: Int = 2
        private const val BOOKMARK: Int = 3
        private const val USER: Int = 4
        private const val INVALID: Int = -1

        private fun getPosition(itemId: Int): Int {
            when (itemId) {
                R.id.icon_home_profile, R.id.icon_home_bookmarks, R.id.icon_home_feed,
                R.id.icon_home_feed_followed, R.id.icon_home_search_bottom -> {
                    return HOME
                }

                R.id.icon_follow_profile, R.id.icon_follow_bookmarks, R.id.icon_follow_feed,
                R.id.icon_follow_feed_followed, R.id.icon_follow_search_bottom -> {
                    return FOLLOW
                }

                R.id.icon_bookmark_profile, R.id.icon_bookmark_bookmarks, R.id.icon_bookmark_feed,
                R.id.icon_bookmark_feed_followed, R.id.icon_bookmark_search_bottom -> {
                    return BOOKMARK
                }

                R.id.icon_user_profile, R.id.icon_user_bookmarks, R.id.icon_user_feed,
                R.id.icon_user_feed_followed, R.id.icon_user_search_bottom -> {
                    return USER
                }
            }

            return INVALID
        }

        fun setBottomMenuListeners(bnvAccountManagementBottom: BottomNavigationView, from: Activity, packageContext: Context) {
            bnvAccountManagementBottom.setOnItemSelectedListener{ item ->
                when (getPosition(item.itemId)) {
                    HOME -> {
                        val intent = Intent(packageContext, BrowseFeedActivity::class.java)
                        from.startActivity(intent)
                        return@setOnItemSelectedListener true
                    }
                    FOLLOW -> {
                        val intent = Intent(packageContext, BrowseFeedFollowedActivity::class.java)
                        from.startActivity(intent)
                        return@setOnItemSelectedListener true
                    }
                    BOOKMARK -> {
                        val intent = Intent(packageContext, BrowseBookmarksActivity::class.java)
                        from.startActivity(intent)
                        return@setOnItemSelectedListener true
                    }
                    USER -> {
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