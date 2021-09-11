package com.mobdeve.gonzales.lee.ong.artemis

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.widget.ScrollView
import android.widget.Toast
import androidx.core.widget.NestedScrollView
import com.google.android.material.bottomnavigation.BottomNavigationView

/**
 * Class containing methods for setting the listeners and the corresponding behaviors
 * when the menu items in the bottom navigation view are selected.
 *
 * The methods are intended to be called in every activity whose layout features
 * the bottom navigation view with menu items Home, Followed, Bookmarks, and Profile.
 *
 * @constructor Creates a class that contains methods for setting the listeners and the corresponding
 * behaviors when the menu items in the bottom navigation view are selected.
 */
class BottomMenuUtil {
    /**
     * Companion object containing the methods for setting the listeners and the corresponding
     * behaviors when the menu items in the bottom navigation view are selected, alongside
     * constants referring to these menu items.
     */
    companion object {
        /**
         * Constant referring to the Home menu in the bottom navigation view.
         */
        const val HOME: Int = 1

        /**
         * Constant referring to the Followed menu in the bottom navigation view.
         */
        const val FOLLOW: Int = 2

        /**
         * Constant referring to the Bookmarks menu in the bottom navigation view.
         */
        const val BOOKMARK: Int = 3

        /**
         * Constant referring to the Profile menu in the bottom navigation view.
         */
        const val USER: Int = 4

        /**
         * Constant referring to an invalid option in the bottom navigation view.
         */
        private const val INVALID: Int = -1

        /**
         * Maps the ID of the icon in the bottom navigation view to the menu it represents
         * (technically, the constant referring to that menu).
         *
         * @param itemId ID of the icon in the bottom navigation view.
         * @return Menu represented by the icon in the bottom navigation view.
         */
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

        /**
         * Sets the listeners and defines the corresponding behaviors (limited to passing intents
         * and starting activities) when the items in the given bottom navigation view for registered
         * users are selected.
         *
         * @param bnv Bottom navigation view for registered users.
         * @param from Activity calling this method.
         * @param packageContext Context of the activity calling this method.
         */
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

        /**
         * Sets the listeners and defines the corresponding behaviors (limited to passing intents,
         * destroying current activities, and starting activities) when the items in the given
         * bottom navigation view for registered users are selected.
         *
         * @param bnv Bottom navigation view for registered users.
         * @param from Activity calling this method.
         * @param packageContext Context of the activity calling this method.
         */
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

        /**
         * Sets the listeners and defines the corresponding behaviors (limited to passing intents,
         * scrolling actions, and starting activities) when the items in the given
         * bottom navigation view for registered users are selected.
         *
         * @param bnv Bottom navigation view for registered users.
         * @param nsv Nested scroll view involved in the scrolling action.
         * @param scroll Menu corresponding to the activity on which the scrolling action is executed.
         * @param from Activity calling this method.
         * @param packageContext Context of the activity calling this method.
         */
        fun setScrollBottomMenuListeners(bnv: BottomNavigationView, nsv: NestedScrollView, scroll: Int, from: Activity, packageContext: Context) {
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

        /**
         * Sets the listeners and defines the corresponding behaviors (limited to scrolling actions
         * and starting activities) when the items in the given bottom navigation view for unregistered
         * users are selected.
         *
         * Since unregistered users are restricted to only their feed and this method does not involve
         * passing intents, there is no need to pass the activity calling this method and the menu
         * corresponding to the activity on which the scrolling action is executed.
         *
         * @param bnv Bottom navigation view for unregistered users.
         * @param nsv Nested scroll view involved in the scrolling action.
         * @param packageContext Context of the activity calling this method.
         */
        fun setScrollBottomMenuListenersUnregistered(bnv: BottomNavigationView, nsv: NestedScrollView, packageContext: Context) {
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

        /**
         * Sets the listeners and defines the corresponding behaviors (limited to passing intents,
         * and starting activities) when the items in the given bottom navigation view for unregistered
         * users are selected.
         *
         * @param bnv Bottom navigation view for unregistered users.
         * @param from Nested scroll view involved in the scrolling action.
         * @param packageContext Context of the activity calling this method.
         */
        fun setBottomMenuListenersUnregistered(bnv: BottomNavigationView, from: Activity, packageContext: Context) {
            bnv.setOnItemSelectedListener { item ->
                when (getPosition(item.itemId)) {
                    HOME -> {
                        val intent = Intent(packageContext, BrowseFeedUnregisteredActivity::class.java)
                        from.startActivity(intent)
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

        /**
         * Sets the listeners and defines the corresponding behaviors (limited to passing intents,
         * destroying current activities, and starting activities) when the items in the given bottom
         * navigation view for unregistered users are selected.
         *
         * @param bnv Bottom navigation view for unregistered users.
         * @param from Nested scroll view involved in the scrolling action.
         * @param packageContext Context of the activity calling this method.
         */
        fun setFinishBottomMenuListenersUnregistered(bnv: BottomNavigationView, from: Activity, packageContext: Context) {
            bnv.setOnItemSelectedListener { item ->
                when (getPosition(item.itemId)) {
                    HOME -> {
                        val intent = Intent(packageContext, BrowseFeedUnregisteredActivity::class.java)
                        from.startActivity(intent)
                        from.finish()
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