package com.mobdeve.gonzales.lee.ong.artemis

import android.content.Intent
import android.graphics.drawable.AnimationDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.ImageView

class MainActivity : AppCompatActivity() {
    private var logoAnimation: AnimationDrawable? = null

    companion object {
        private const val SPLASH_SCREEN_TIMEOUT = 3000
        private const val ANIMATION_FRAME_FADEOUT = 400
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val ivLogo = findViewById<ImageView>(R.id.iv_splash_logo)
        ivLogo.setBackgroundResource(R.drawable.logo_animation)
        logoAnimation = ivLogo.background as AnimationDrawable

        Handler(Looper.getMainLooper()).postDelayed({
            val i = Intent(this@MainActivity, LogInActivity::class.java)
            startActivity(i)
            finish()
        }, SPLASH_SCREEN_TIMEOUT.toLong())
    }

    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)

        logoAnimation!!.setExitFadeDuration(ANIMATION_FRAME_FADEOUT.toInt());
        logoAnimation!!.start()
    }
}