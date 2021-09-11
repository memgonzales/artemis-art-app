//[app](../../../../index.md)/[com.mobdeve.gonzales.lee.ong.artemis](../../index.md)/[PostArtworkUtil](../index.md)/[Companion](index.md)/[chooseFromGallery](choose-from-gallery.md)

# chooseFromGallery

[androidJvm]\
fun [chooseFromGallery](choose-from-gallery.md)(activity: [Activity](https://developer.android.com/reference/kotlin/android/app/Activity.html), galleryLauncher: [ActivityResultLauncher](https://developer.android.com/reference/kotlin/androidx/activity/result/ActivityResultLauncher.html)<[Intent](https://developer.android.com/reference/kotlin/android/content/Intent.html)>)

Obtains the necessary permissions for choosing a photo from the Gallery. If these permissions have already been granted beforehand, this method also defines the subsequent behavior.

To access the gallery, a <code>READ_EXTERNAL_STORAGE</code> permission is requested from the user.

## Parameters

androidJvm

| | |
|---|---|
| activity | activity calling this method |
| galleryLauncher | activity result launcher related to choosing photos from the Gallery |
