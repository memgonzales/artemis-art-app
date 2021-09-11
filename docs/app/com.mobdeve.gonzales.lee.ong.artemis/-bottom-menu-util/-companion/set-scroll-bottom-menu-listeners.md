//[app](../../../../index.md)/[com.mobdeve.gonzales.lee.ong.artemis](../../index.md)/[BottomMenuUtil](../index.md)/[Companion](index.md)/[setScrollBottomMenuListeners](set-scroll-bottom-menu-listeners.md)

# setScrollBottomMenuListeners

[androidJvm]\
fun [setScrollBottomMenuListeners](set-scroll-bottom-menu-listeners.md)(bnv: BottomNavigationView, nsv: [NestedScrollView](https://developer.android.com/reference/kotlin/androidx/core/widget/NestedScrollView.html), scroll: [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html), from: [Activity](https://developer.android.com/reference/kotlin/android/app/Activity.html), packageContext: [Context](https://developer.android.com/reference/kotlin/android/content/Context.html))

Sets the listeners and defines the corresponding behaviors (limited to passing intents, scrolling actions, and starting activities) when the items in the given bottom navigation view for registered users are selected.

## Parameters

androidJvm

| | |
|---|---|
| bnv | bottom navigation view for registered users |
| nsv | nested scroll view involved in the scrolling action |
| scroll | menu corresponding to the activity on which the scrolling action is executed |
| from | activity calling this method |
| packageContext | context of the activity calling this method |
