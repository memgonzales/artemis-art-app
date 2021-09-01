package com.mobdeve.gonzales.lee.ong.artemis

import android.graphics.*
import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import kotlin.math.sqrt

/**
 * This class contains a static method to place text on top of a bitmap (image),
 * for use in adding attribution to artworks shared on the Facebook account
 * of the user.
 *
 * CALLING THE METHOD: CaptionPlacer.placeCaption(picture, firstLine, secondLine)
 *
 * The private methods, alongside caption_placer.xml, show a sample usage.
 */
class CaptionPlacer : AppCompatActivity() {
    private lateinit var ivOldImage: ImageView
    private lateinit var ivNewImage: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.caption_placer)

        initComponents()
        addCaptionSample()
    }

    private fun initComponents() {
        ivOldImage = findViewById(R.id.iv_old_image)
        ivNewImage = findViewById(R.id.iv_new_image)
    }

    private fun addCaptionSample() {
        val oldImageBitmapDrawable = ivOldImage.drawable as BitmapDrawable
        val oldImageBitmap = oldImageBitmapDrawable.bitmap

        val newImageBitmap = placeCaption(oldImageBitmap, "@shibe", "On Artemis")
        ivNewImage.setImageBitmap(newImageBitmap)
    }

    companion object {
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