//[app](../../../index.md)/[com.mobdeve.gonzales.lee.ong.artemis](../index.md)/[EditProfileActivity](index.md)/[fetchFromCamera](fetch-from-camera.md)

# fetchFromCamera

[androidJvm]\
private fun [fetchFromCamera](fetch-from-camera.md)(photoPath: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)?)

Displays the photo taken using the device camera in the proper orientation.

This method addresses the issue of most device cameras setting the orientation of a captured image to landscape. The code for rotation is a direct translation of the one found in <a href = "https://www.py4u.net/discuss/611150">https://www.py4u.net/discuss/611150</a>

## Parameters

androidJvm

| | |
|---|---|
| photoPath | path to the photo to be rotated |
