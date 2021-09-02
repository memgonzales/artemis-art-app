package com.mobdeve.gonzales.lee.ong.artemis

import android.graphics.*
import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import kotlin.math.sqrt

/**
 * Class containing a method (defined inside a companion object) to overlay a text on a
 * bitmap (image).
 *
 * This is intended to be used in adding attribution to an artwork that a user shares on
 * their Facebook account
 */
class CaptionPlacer {
    /**
     * Companion object containing a method to overlay a text on a bitmap (image)
     */
    companion object {
        /**
         * Overlays two lines of text on a solid background drawn at the bottom of a bitmap (image)
         *
         * @param picture image on which the text is to be overlain
         * @param firstLine first line of text to be placed
         * @param secondLine second line of text to be placed
         *
         * @return bitmap with the two lines of text overlain on the original image
         */
        fun placeCaption(picture: Bitmap, firstLine: String, secondLine: String): Bitmap {
            val paintRectangle = Paint()
            val paintText = Paint()

            paintRectangle.style = Paint.Style.FILL
            paintRectangle.color = Color.BLACK
            paintRectangle.alpha = 70

            paintText.style = Paint.Style.FILL
            paintText.color = Color.WHITE

            val newPicture: Bitmap = picture.copy(Bitmap.Config.ARGB_8888, true)
            val canvas = Canvas(newPicture)
            paintText.textSize = sqrt((canvas.width * canvas.height).toDouble()).toFloat() / 20
            canvas.drawRect(0F,
                (newPicture.height * 8.25/10).toFloat(),
                newPicture.width.toFloat(), newPicture.height.toFloat(), paintRectangle)
            canvas.drawText(firstLine,
                (newPicture.width / 16).toFloat(), (newPicture.height * 9/10).toFloat(), paintText)
            canvas.drawText(secondLine,
                (newPicture.width / 16).toFloat(), (newPicture.height * 9.7/10).toFloat(), paintText)

            return newPicture
        }
    }
}