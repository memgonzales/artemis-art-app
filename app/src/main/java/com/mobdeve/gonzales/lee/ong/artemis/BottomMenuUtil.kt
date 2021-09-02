package com.mobdeve.gonzales.lee.ong.artemis

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.widget.ScrollView
import android.widget.Toast
import androidx.core.widget.NestedScrollView
import com.google.android.material.bottomnavigation.BottomNavigationView

class BottomMenuUtil {
    companion object {
        const val HOME: Int = 1
        const val FOLLOW: Int = 2
        const val BOOKMARK: Int = 3
        const val USER: Int = 4

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

        fun setBottomMenuListeners(bnv: BottomNavigationView, from: Activity, packageContext: Context) {
            bnv.setOnItemSelectedListener{ item ->
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

        fun setFinishBottomMenuListeners(bnv: BottomNavigationView, from: Activity, packageContext: Context) {
            bnv.setOnItemSelectedListener{ item ->
                when (getPosition(item.itemId)) {
                    HOME -> {
                        val intent = Intent(packageContext, BrowseFeedActivity::class.java)
                        from.startActivity(intent)
                        from.finish()
                        return@setOnItemSelectedListener true
                    }
                    FOLLOW -> {
                        val intent = Intent(packageContext, BrowseFeedFollowedActivity::class.java)
                        from.startActivity(intent)
                        from.finish()
                        return@setOnItemSelectedListener true
                    }
                    BOOKMARK -> {
                        val intent = Intent(packageContext, BrowseBookmarksActivity::class.java)
                        from.startActivity(intent)
                        from.finish()
                        return@setOnItemSelectedListener true
                    }
                    USER -> {
                        val intent = Intent(packageContext, ViewProfileActivity::class.java)
                        from.startActivity(intent)
                        from.finish()
                        return@setOnItemSelectedListener true
                    }
                }
                false
            }
        }

        fun setScrollBottomBottomMenuListeners(bnv: BottomNavigationView, nsv: NestedScrollView, scroll: Int, from: Activity, packageContext: Context) {
            bnv.setOnItemSelectedListener { item ->
                when (getPosition(item.itemId)) {
                    HOME -> {
                        if (scroll == HOME) {
                            nsv.fullScroll(ScrollView.FOCUS_UP)
                        } else {
                            val intent = Intent(packageContext, BrowseFeedActivity::class.java)
                            from.startActivity(intent)
                        }

                        return@setOnItemSelectedListener true
                    }
                    FOLLOW -> {
                        if (scroll == FOLLOW) {
                            nsv.fullScroll(ScrollView.FOCUS_UP)
                        } else {
                            val intent =
                                Intent(packageContext, BrowseFeedFollowedActivity::class.java)
                            from.startActivity(intent)
                        }
                        return@setOnItemSelectedListener true
                    }
                    BOOKMARK -> {
                        if (scroll == BOOKMARK) {
                            nsv.fullScroll(ScrollView.FOCUS_UP)
                        } else {
                            val intent = Intent(packageContext, BrowseBookmarksActivity::class.java)
                            from.startActivity(intent)
                        }
                        return@setOnItemSelectedListener true
                    }
                    USER -> {
                        if (scroll == USER) {
                            nsv.fullScroll(ScrollView.FOCUS_UP)
                        } else {
                            val intent = Intent(packageContext, ViewProfileActivity::class.java)
                            from.startActivity(intent)
                        }
                        return@setOnItemSelectedListener true
                    }
                }
                false
            }
        }

        fun setScrollBottomBottomMenuListenersUnregistered(bnv: BottomNavigationView, nsv: NestedScrollView, packageContext: Context) {
            bnv.setOnItemSelectedListener { item ->
                when (getPosition(item.itemId)) {
                    HOME -> {
                        nsv.fullScroll(ScrollView.FOCUS_UP)
                        return@setOnItemSelectedListener true
                    }
                    FOLLOW, BOOKMARK, USER -> {
                        Toast.makeText(packageContext,
                            "Log in or create an account to use this feature",
                            Toast.LENGTH_SHORT).show()
                    }

                }
                false
            }
        }
    }
}