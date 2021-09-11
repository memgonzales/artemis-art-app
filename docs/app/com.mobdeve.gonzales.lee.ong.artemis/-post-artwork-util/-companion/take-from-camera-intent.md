//[app](../../../../index.md)/[com.mobdeve.gonzales.lee.ong.artemis](../../index.md)/[PostArtworkUtil](../index.md)/[Companion](index.md)/[takeFromCameraIntent](take-from-camera-intent.md)

# takeFromCameraIntent

[androidJvm]\
private fun [takeFromCameraIntent](take-from-camera-intent.md)(intent: [Intent](https://developer.android.com/reference/kotlin/android/content/Intent.html), cameraLauncher: [ActivityResultLauncher](https://developer.android.com/reference/kotlin/androidx/activity/result/ActivityResultLauncher.html)<[Intent](https://developer.android.com/reference/kotlin/android/content/Intent.html)>, activity: [Activity](https://developer.android.com/reference/kotlin/android/app/Activity.html), context: [Context](https://developer.android.com/reference/kotlin/android/content/Context.html))

Launches the activity result pertaining to the device camera should the user decide to take a photo using it.

## Parameters

androidJvm

| | |
|---|---|
| intent | intent associated with launching the device camera |
| cameraLauncher | activity result launcher related to taking photos with the device camera |
| activity | activity calling this method |
| context | context tied to the activity calling this method |
