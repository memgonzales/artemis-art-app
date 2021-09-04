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

/**
 * Class containing methods for handling the posting of an artwork in relation to choosing a photo
 * from the Gallery or taking a photo using the device camera.
 *
 * The methods are intended to be called in every activity that allows the user to post an artwork
 * (that is, every activity for registered users that features a bottom navigation view).
 *
 * @constructor Creates a class that contains methods for handling the posting of an artwork
 * in relation to choosing a photo from the Gallery or taking a photo using the device camera.
 */
class PostArtworkUtil {
    /**
     * Companion object containing methods for handling the posting of an artwork in relation
     * to choosing a photo from the Gallery or taking a photo using the device camera, alongside
     * constants associated with these operations.
     */
    companion object {
        /**
         * Default file name (excluding the file extension) of a photo taken using the device camera.
         */
        private const val PHOTO_DEFAULT_FILE_NAME = "photo"

        /**
         * Name of the package to which this class belongs.
         */
        private const val PACKAGE_NAME = "com.mobdeve.gonzales.lee.ong.artemis"

        /**
         * Denotes that the photo was taken using the device camera.
         */
        const val FROM_CAMERA = "camera"

        /**
         * Denotes that the photo was chosen the Gallery.
         */
        const val FROM_GALLERY = "gallery"

        /**
         * Obtains the necessary permissions for choosing a photo from the Gallery. If these
         * permissions have already been granted beforehand, this method also defines the subsequent
         * behavior.
         *
         * To access the gallery, a <code>READ_EXTERNAL_STORAGE</code> permission is requested
         * from the user.
         *
         * @param activity activity calling this method
         * @param galleryLauncher activity result launcher related to choosing photos from the Gallery
         */
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

        /**
         * Launches the Gallery should the user decide to choose a photo from it.
         *
         * @param galleryLauncher activity result launcher related to choosing photos from the Gallery
         */
        private fun chooseFromGalleryIntent(galleryLauncher: ActivityResultLauncher<Intent>) {
            val intent = Intent(Intent.ACTION_PICK)
            intent.type = "image/*"

            galleryLauncher.launch(intent)
        }

        /**
         * Defines the behavior depending on whether the user granted (or denied) the necessary
         * permissions for choosing a photo from the Gallery.
         *
         * @param grantResults grant results for the corresponding permissions which is either <code>
         *     PackageManager.PERMISSION_GRANTED</code> or <code>PackageManager.PERMISSION_DENIED</code>.
         *     Never null
         * @param context context tied to the activity calling this method
         * @param galleryLauncher activity result launcher related to choosing photos from the Gallery
         */
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

        /**
         * Obtains the necessary permissions for taking a photo using the device camera. If these
         * permissions have already been granted beforehand, this method also defines the subsequent
         * behavior.
         *
         * To take a photo using the device camera and save a temporary high-fidelity copy of this photo
         * in the device storage, the following permissions are requested: <code>CAMERA</code>,
         * <code>READ_EXTERNAL_STORAGE</code>, and <code>WRITE_EXTERNAL_STORAGE</code>
         *
         * @param activity activity calling this method
         * @param context context tied to the activity calling this method
         * @param cameraLauncher activity result launcher related to taking photos using the device camera
         *
         * @return photo of the artwork taken using the device camera
         */
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

        /**
         * Returns a temporary file given the specified file name and a fixed extension of ".jpg"
         * (associated with an image) and stores this in the standard directory for pictures.
         *
         * This method is intended to return the file corresponding to the photo taken by the user
         * via the device camera.
         *
         * @param context context tied to the activity calling this method
         * @param name file name of the temporary file
         *
         * @return temporary file given the specified file name and a fixed extension of ".jpg"
         */
        private fun getPhotoFile(context: Context, name: String): File {
            val storageDirectory = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
            return File.createTempFile(name, ".jpg", storageDirectory)
        }

        /**
         * Launches the activity result pertaining to the device camera should the user decide
         * to take a photo using it.
         *
         * @param intent intent associated with launching the device camera
         * @param cameraLauncher activity result launcher related to taking photos with the device camera
         * @param activity activity calling this method
         * @param context context tied to the activity calling this method
         */
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

        /**
         * Defines the behavior depending on whether the user granted (or denied) the necessary
         * permissions for taking a photo using the device camera.
         *
         * @param grantResults grant results for the corresponding permissions which is either <code>
         *     PackageManager.PERMISSION_GRANTED</code> or <code>PackageManager.PERMISSION_DENIED</code>.
         *     Never null
         * @param activity activity calling this method
         * @param context context tied to the activity calling this method
         * @param cameraLauncher activity result launcher related to taking photos using the device camera
         *
         * @return file corresponding to the photo taken using the device camera. If the user denied
         * the necessary permissions to complete the operation, <code>null</code> is returned.
         */
        fun permissionsResultCamera(grantResults: IntArray, activity: Activity, context: Context,
                                    cameraLauncher: ActivityResultLauncher<Intent>): File? {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED
                && grantResults[1] == PackageManager.PERMISSION_GRANTED
                && grantResults[2] == PackageManager.PERMISSION_GRANTED) {
                val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)

                val photoFile = getPhotoFile(context, PHOTO_DEFAULT_FILE_NAME)

                val fileProvider = FileProvider.getUriForFile(context, PACKAGE_NAME, photoFile)
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

            /* The user denied the necessary permissions. */
            return null
        }
    }
}