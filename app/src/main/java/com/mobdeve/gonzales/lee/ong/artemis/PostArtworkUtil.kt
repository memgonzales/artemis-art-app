package com.mobdeve.gonzales.lee.ong.artemis

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Environment
import android.provider.MediaStore
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import java.io.File

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

        fun permissionsResultGallery(grantResults: IntArray, context: Context,
                                     galleryLauncher: ActivityResultLauncher<Intent>) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                chooseFromGalleryIntent(galleryLauncher)
            } else {
                Toast.makeText(
                    context,
                    "Insufficient permissions to access your gallery",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

        fun takeFromCamera(activity: Activity, context: Context,
                           cameraLauncher: ActivityResultLauncher<Intent>) : File {
            val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            val permissions = arrayOf(
                android.Manifest.permission.CAMERA,
                android.Manifest.permission.READ_EXTERNAL_STORAGE,
                android.Manifest.permission.WRITE_EXTERNAL_STORAGE
            )

            val photoFile = getPhotoFile(context, PHOTO_DEFAULT_FILE_NAME)

            val fileProvider =
                FileProvider.getUriForFile(context, PACKAGE_NAME, photoFile)
            intent.putExtra(MediaStore.EXTRA_OUTPUT, fileProvider)

            if (ContextCompat.checkSelfPermission(
                    activity.applicationContext,
                    permissions[0]
                ) != PackageManager.PERMISSION_GRANTED
                || ContextCompat.checkSelfPermission(
                    activity.applicationContext,
                    permissions[1]
                ) != PackageManager.PERMISSION_GRANTED
                || ContextCompat.checkSelfPermission(
                    activity.applicationContext,
                    permissions[2]
                ) != PackageManager.PERMISSION_GRANTED
            ) {

                ActivityCompat.requestPermissions(
                    activity,
                    permissions,
                    RequestCodes.REQUEST_CODE_POST_CAMERA.ordinal
                )

            } else {
                takeFromCameraIntent(intent, cameraLauncher, activity, context)
            }

            return photoFile
        }

        private fun getPhotoFile(context: Context, name: String): File {
            val storageDirectory = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
            return File.createTempFile(name, ".jpg", storageDirectory)
        }

        private fun takeFromCameraIntent(intent: Intent, cameraLauncher: ActivityResultLauncher<Intent>,
                                         activity: Activity, context: Context) {
            if (intent.resolveActivity(activity.packageManager) != null) {
                cameraLauncher.launch(intent)
            } else {
                Toast.makeText(
                    context,
                    "Camera app cannot be found",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

        fun permissionsResultCamera(grantResults: IntArray, activity: Activity, context: Context,
                                    cameraLauncher: ActivityResultLauncher<Intent>): File? {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED
                && grantResults[1] == PackageManager.PERMISSION_GRANTED
                && grantResults[2] == PackageManager.PERMISSION_GRANTED) {
                val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)

                val photoFile = PostArtworkUtil.getPhotoFile(context, PostArtworkUtil.PHOTO_DEFAULT_FILE_NAME)

                val fileProvider = FileProvider.getUriForFile(context, PostArtworkUtil.PACKAGE_NAME, photoFile)
                intent.putExtra(MediaStore.EXTRA_OUTPUT, fileProvider)

                if (intent.resolveActivity(activity.packageManager) != null) {
                    cameraLauncher.launch(intent)
                } else {
                    Toast.makeText(
                        context,
                        "Camera app cannot be found",
                        Toast.LENGTH_SHORT
                    ).show()
                }

                return photoFile
            } else {
                Toast.makeText(
                    context,
                    "Insufficient permissions to take a photo",
                    Toast.LENGTH_SHORT
                ).show()
            }

            return null
        }
    }
}