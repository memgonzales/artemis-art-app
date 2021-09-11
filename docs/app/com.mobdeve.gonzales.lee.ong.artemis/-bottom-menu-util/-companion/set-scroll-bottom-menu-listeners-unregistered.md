//[app](../../../../index.md)/[com.mobdeve.gonzales.lee.ong.artemis](../../index.md)/[BottomMenuUtil](../index.md)/[Companion](index.md)/[setScrollBottomMenuListenersUnregistered](set-scroll-bottom-menu-listeners-unregistered.md)

# setScrollBottomMenuListenersUnregistered

[androidJvm]\
fun [setScrollBottomMenuListenersUnregistered](set-scroll-bottom-menu-listeners-unregistered.md)(bnv: BottomNavigationView, nsv: [NestedScrollView](https://developer.android.com/reference/kotlin/androidx/core/widget/NestedScrollView.html), packageContext: [Context](https://developer.android.com/reference/kotlin/android/content/Context.html))

Sets the listeners and defines the corresponding behaviors (limited to scrolling actions and starting activities) when the items in the given bottom navigation view for unregistered users are selected.

Since unregistered users are restricted to only their feed and this method does not involve passing intents, there is no need to pass the activity calling this method and the menu corresponding to the activity on which the scrolling action is executed.

## Parameters

androidJvm

| | |
|---|---|
| bnv | bottom navigation view for unregistered users |
| nsv | nested scroll view involved in the scrolling action |
| packageContext | context of the activity calling this method |
