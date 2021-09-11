//[app](../../../../index.md)/[com.mobdeve.gonzales.lee.ong.artemis](../../index.md)/[PostArtworkUtil](../index.md)/[Companion](index.md)/[getPhotoFile](get-photo-file.md)

# getPhotoFile

[androidJvm]\
private fun [getPhotoFile](get-photo-file.md)(context: [Context](https://developer.android.com/reference/kotlin/android/content/Context.html), name: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)): [File](https://developer.android.com/reference/kotlin/java/io/File.html)

Returns a temporary file given the specified file name and a fixed extension of ".jpg" (associated with an image) and stores this in the standard directory for pictures.

This method is intended to return the file corresponding to the photo taken by the user via the device camera.

#### Return

temporary file given the specified file name and a fixed extension of ".jpg"

## Parameters

androidJvm

| | |
|---|---|
| context | context tied to the activity calling this method |
| name | file name of the temporary file |
