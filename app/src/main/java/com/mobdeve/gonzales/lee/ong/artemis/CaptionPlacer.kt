package com.mobdeve.gonzales.lee.ong.artemis

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import kotlin.math.sqrt

/**
 * Class containing a method (defined inside a companion object) to overlay a text on a
 * bitmap (image).
 *
 * This is intended to be used in adding attribution to an artwork that a user shares on
 * their Facebook account
 *
 * @constructor Creates a class that contains a method (defined inside a companion object)
 * to overlay a text on a bitmap (image).
 */
class CaptionPlacer {
    /**
     * Companion object containing a method to overlay a text on a bitmap (image).
     */
    companion object {
        /**
         * Overlays two lines of text on a solid background drawn at the bottom of a bitmap (image).
         *
         * @param picture Image on which the text is to be overlain.
         * @param firstLine First line of text to be placed.
         * @param secondLine Second line of text to be placed.
         *
         * @return Bitmap with the two lines of text overlain on the original image.
         */
        fun placeCaption(picture: Bitmap, firstLine: String, secondLine: String): Bitmap {
            val paintRectangle = Paint()
            val paintText = Paint()
            val paintTextSmall = Paint()

            paintRectangle.style = Paint.Style.FILL
            paintRectangle.color = Color.BLACK
            paintRectangle.alpha = 70

            paintText.style = Paint.Style.FILL
            paintText.color = Color.WHITE

            paintTextSmall.style = Paint.Style.FILL
            paintTextSmall.color = Color.WHITE

            val newPicture: Bitmap = picture.copy(Bitmap.Config.ARGB_8888, true)
            val canvas = Canvas(newPicture)

            /* Make the font size scale by computing it against the dimensions of the canvas */
            paintText.textSize = sqrt((canvas.width * canvas.height).toDouble()).toFloat() / 20
            paintTextSmall.textSize = sqrt((canvas.width * canvas.height).toDouble()).toFloat() / 25

            /* Draw the rectangular overlay */
            canvas.drawRect(0F,
                (newPicture.height * 8.25/10).toFloat(),
                newPicture.width.toFloat(), newPicture.height.toFloat(), paintRectangle)

            /* Draw the two lines of text */
            canvas.drawText(firstLine,
                (newPicture.width / 16).toFloat(), (newPicture.height * 8.9/10).toFloat(), paintText)
            canvas.drawText(secondLine,
                (newPicture.width / 16).toFloat(), (newPicture.height * 9.7/10).toFloat(), paintTextSmall)

            return newPicture
        }
    }
}