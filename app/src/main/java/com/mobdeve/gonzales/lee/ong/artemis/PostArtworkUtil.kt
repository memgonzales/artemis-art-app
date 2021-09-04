package com.mobdeve.gonzales.lee.ong.artemis

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

class PostArtworkUtil {
    companion object {
        const val PHOTO_DEFAULT_FILE_NAME = "photo"
        const val PACKAGE_NAME = "com.mobdeve.gonzales.lee.ong.artemis"

        const val FROM_CAMERA = "camera"
        const val FROM_GALLERY = "gallery"

        fun chooseFromGallery(activity: Activity, galleryLauncher: ActivityResultLauncher<Intent>) {
            val permissions = arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE)

            if (ContextCompat.checkSelfPermission(
                    activity.applicationContext,
                    permissions[0]
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                ActivityCompat.requestPermissions(
                    activity,
                    permissions,
                    RequestCodes.REQUEST_CODE_POST_GALLERY.ordinal
                )
            } else {
                chooseFromGalleryIntent(galleryLauncher)
            }
        }

        private fun chooseFromGalleryIntent(galleryLauncher: ActivityResultLauncher<Intent>) {
            val intent = Intent(Intent.ACTION_PICK)
            intent.type = "image/*"

            galleryLauncher.launch(intent)
        }

        fun permissionsResultGallery(grantResults: IntArray, context: Context, galleryLauncher: ActivityResultLauncher<Intent>) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                chooseFromGalleryIntent(galleryLauncher)
            } else {
                Toast.makeText(
                    context,
                    "Insufficient permissions to access your gallery",
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    }
}