//[app](../../../index.md)/[com.mobdeve.gonzales.lee.ong.artemis](../index.md)/[PostArtworkActivity](index.md)/[fetchFromCamera](fetch-from-camera.md)

# fetchFromCamera

[androidJvm]\
private fun [fetchFromCamera](fetch-from-camera.md)(photoPath: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)?)

Displays the photo taken using the device camera in the proper orientation.

This method addresses the issue of most device cameras setting the orientation of a captured image to landscape. The code for rotation is a direct translation of the one found in https://www.py4u.net/discuss/611150

## Parameters

androidJvm

| | |
|---|---|
| photoPath | path to the photo to be rotated |
