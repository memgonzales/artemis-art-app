package com.mobdeve.gonzales.lee.ong.artemis

import android.content.Intent
import android.graphics.drawable.AnimationDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.ImageView
import androidx.appcompat.app.AppCompatDelegate

class MainActivity : AppCompatActivity() {
    private lateinit var logoAnimation: AnimationDrawable

    override fun onCreate(savedInstanceState: Bundle?) {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initSplashScreen()
        launchLogIn()
    }

    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)
        animateSplashScreen()
    }

    private fun initSplashScreen() {
        val ivLogo = findViewById<ImageView>(R.id.iv_splash_logo)
        ivLogo.setBackgroundResource(R.drawable.logo_animation)
        logoAnimation = ivLogo.background as AnimationDrawable
    }

    private fun animateSplashScreen() {
        logoAnimation.setExitFadeDuration(AnimationDuration.ANIMATION_FRAME_FADEOUT.toInt());
        logoAnimation.start()
    }

    private fun launchLogIn() {
        Handler(Looper.getMainLooper()).postDelayed({
            val i = Intent(this@MainActivity, LogInActivity::class.java)
            startActivity(i)
            finish()
        }, AnimationDuration.SPLASH_SCREEN_TIMEOUT.toLong())
    }
}