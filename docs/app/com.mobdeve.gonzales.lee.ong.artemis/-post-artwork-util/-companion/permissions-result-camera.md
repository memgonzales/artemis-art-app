//[app](../../../../index.md)/[com.mobdeve.gonzales.lee.ong.artemis](../../index.md)/[PostArtworkUtil](../index.md)/[Companion](index.md)/[permissionsResultCamera](permissions-result-camera.md)

# permissionsResultCamera

[androidJvm]\
fun [permissionsResultCamera](permissions-result-camera.md)(grantResults: [IntArray](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int-array/index.html), activity: [Activity](https://developer.android.com/reference/kotlin/android/app/Activity.html), context: [Context](https://developer.android.com/reference/kotlin/android/content/Context.html), cameraLauncher: [ActivityResultLauncher](https://developer.android.com/reference/kotlin/androidx/activity/result/ActivityResultLauncher.html)<[Intent](https://developer.android.com/reference/kotlin/android/content/Intent.html)>): [File](https://developer.android.com/reference/kotlin/java/io/File.html)?

Defines the behavior depending on whether the user granted (or denied) the necessary permissions for taking a photo using the device camera.

#### Return

file corresponding to the photo taken using the device camera. If the user denied the necessary permissions to complete the operation, <code>null</code> is returned.

## Parameters

androidJvm

| | |
|---|---|
| grantResults | grant results for the corresponding permissions which is either <code>     PackageManager.PERMISSION_GRANTED</code> or <code>PackageManager.PERMISSION_DENIED</code>.     Never null |
| activity | activity calling this method |
| context | context tied to the activity calling this method |
| cameraLauncher | activity result launcher related to taking photos using the device camera |
