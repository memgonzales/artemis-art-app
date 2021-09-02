package com.mobdeve.gonzales.lee.ong.artemis

import android.content.Intent
import android.graphics.drawable.AnimationDrawable
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class MainActivity : AppCompatActivity() {
    private lateinit var logoAnimation: AnimationDrawable

    private lateinit var mAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initSplashScreen()
        initFirebase()
        launchLogIn()
    }

    private fun initFirebase(){
        this.mAuth = Firebase.auth
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
        logoAnimation.setExitFadeDuration(AnimationDuration.ANIMATION_FRAME_FADEOUT)
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