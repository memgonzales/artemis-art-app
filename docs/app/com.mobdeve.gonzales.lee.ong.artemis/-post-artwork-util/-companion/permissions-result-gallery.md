//[app](../../../../index.md)/[com.mobdeve.gonzales.lee.ong.artemis](../../index.md)/[PostArtworkUtil](../index.md)/[Companion](index.md)/[permissionsResultGallery](permissions-result-gallery.md)

# permissionsResultGallery

[androidJvm]\
fun [permissionsResultGallery](permissions-result-gallery.md)(grantResults: [IntArray](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int-array/index.html), context: [Context](https://developer.android.com/reference/kotlin/android/content/Context.html), galleryLauncher: [ActivityResultLauncher](https://developer.android.com/reference/kotlin/androidx/activity/result/ActivityResultLauncher.html)<[Intent](https://developer.android.com/reference/kotlin/android/content/Intent.html)>)

Defines the behavior depending on whether the user granted (or denied) the necessary permissions for choosing a photo from the Gallery.

## Parameters

androidJvm

| | |
|---|---|
| grantResults | grant results for the corresponding permissions which is either <code>     PackageManager.PERMISSION_GRANTED</code> or <code>PackageManager.PERMISSION_DENIED</code>.     Never null |
| context | context tied to the activity calling this method |
| galleryLauncher | activity result launcher related to choosing photos from the Gallery |
