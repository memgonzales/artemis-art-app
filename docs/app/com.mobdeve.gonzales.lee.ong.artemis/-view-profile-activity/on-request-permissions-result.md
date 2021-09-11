//[app](../../../index.md)/[com.mobdeve.gonzales.lee.ong.artemis](../index.md)/[ViewProfileActivity](index.md)/[onRequestPermissionsResult](on-request-permissions-result.md)

# onRequestPermissionsResult

[androidJvm]\
open override fun [onRequestPermissionsResult](on-request-permissions-result.md)(requestCode: [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html), permissions: [Array](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-array/index.html)<out [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)>, grantResults: [IntArray](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int-array/index.html))

Callback for the result from requesting permissions.

## Parameters

androidJvm

| | |
|---|---|
| requestCode | The request code passed in <code>     ActivityCompat.requestPermissions(android.app.Activity, String[], int)</code> |
| permissions | The requested permissions. Never null |
| grantResults | The grant results for the corresponding permissions which is either <code>     PackageManager.PERMISSION_GRANTED</code> or <code>PackageManager.PERMISSION_DENIED</code>.     Never null |
