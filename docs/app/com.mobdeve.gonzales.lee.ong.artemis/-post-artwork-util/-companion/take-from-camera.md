//[app](../../../../index.md)/[com.mobdeve.gonzales.lee.ong.artemis](../../index.md)/[PostArtworkUtil](../index.md)/[Companion](index.md)/[takeFromCamera](take-from-camera.md)

# takeFromCamera

[androidJvm]\
fun [takeFromCamera](take-from-camera.md)(activity: [Activity](https://developer.android.com/reference/kotlin/android/app/Activity.html), context: [Context](https://developer.android.com/reference/kotlin/android/content/Context.html), cameraLauncher: [ActivityResultLauncher](https://developer.android.com/reference/kotlin/androidx/activity/result/ActivityResultLauncher.html)<[Intent](https://developer.android.com/reference/kotlin/android/content/Intent.html)>): [File](https://developer.android.com/reference/kotlin/java/io/File.html)

Obtains the necessary permissions for taking a photo using the device camera. If these permissions have already been granted beforehand, this method also defines the subsequent behavior.

To take a photo using the device camera and save a temporary high-fidelity copy of this photo in the device storage, the following permissions are requested: <code>CAMERA</code>, <code>READ_EXTERNAL_STORAGE</code>, and <code>WRITE_EXTERNAL_STORAGE</code>

#### Return

photo of the artwork taken using the device camera

## Parameters

androidJvm

| | |
|---|---|
| activity | activity calling this method |
| context | context tied to the activity calling this method |
| cameraLauncher | activity result launcher related to taking photos using the device camera |
