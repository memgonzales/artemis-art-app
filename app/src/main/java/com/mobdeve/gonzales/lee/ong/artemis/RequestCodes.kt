package com.mobdeve.gonzales.lee.ong.artemis

/**
 * Class containing the codes used in requesting permissions related to launching activities
 * related to the camera and Gallery of the device.
 *
 * Since the said operations require integers as codes, they are referenced using their positions
 * in this enumeration declaration, that is, via the property <code>ordinal</code>.
 */
enum class RequestCodes {
    REQUEST_CODE_POST_CAMERA,
    REQUEST_CODE_POST_GALLERY
}