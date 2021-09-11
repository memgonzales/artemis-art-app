//[app](../../../index.md)/[com.mobdeve.gonzales.lee.ong.artemis](../index.md)/[ViewOwnPostActivity](index.md)/[permissionsResult](permissions-result.md)

# permissionsResult

[androidJvm]\
private fun [permissionsResult](permissions-result.md)(requestCode: [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html), grantResults: [IntArray](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int-array/index.html), context: [Context](https://developer.android.com/reference/kotlin/android/content/Context.html), activity: [Activity](https://developer.android.com/reference/kotlin/android/app/Activity.html))

Defines the behavior related to choosing a photo from the Gallery or taking a photo using the device camera based on the permissions granted by the user.

## Parameters

androidJvm

| | |
|---|---|
| requestCode | the request code passed in <code>     ActivityCompat.requestPermissions(android.app.Activity, String[], int)</code> |
| grantResults | the grant results for the corresponding permissions which is either <code>     PackageManager.PERMISSION_GRANTED</code> or <code>PackageManager.PERMISSION_DENIED</code>.     Never null |
| context | context tied to this activity |
| activity | this activity |
