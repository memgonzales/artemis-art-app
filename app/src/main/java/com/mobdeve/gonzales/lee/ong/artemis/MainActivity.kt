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

/**
 * Class handling the display of the splash screen and the loading of initial resources
 * needed during app startup.
 *
 * @constructor Creates a class for displaying the splash screen and loading initial resources
 * needed during app startup.
 */
class MainActivity : AppCompatActivity() {
    /**
     * Animated logo of the app.
     */
    private lateinit var logoAnimation: AnimationDrawable

    /**
     * Starting point for Firebase authentication SDK.
     */
    private lateinit var mAuth: FirebaseAuth

    /**
     * Called when the activity is starting.
     *
     * @param savedInstanceState  If the activity is being re-initialized after previously being
     * shut down then this Bundle contains the data it most recently supplied in
     * <code>onSaveInstanceState(Bundle)</code>. Note: Otherwise it is <code>null</code>.
     * This value may be <code>null</code>.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        /*
         * Prevent the app from entering night mode. This should be the first command
         * executed on create.
         */
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initSplashScreen()
        initFirebase()
        launchLogIn()
    }

    /**
     * Initializes the Firebase-related components.
     */
    private fun initFirebase(){
        this.mAuth = Firebase.auth
    }

    /**
     * Called when the current Window of the activity gains or loses focus.
     *
     * Since the splash screen features an animated logo, this function is overridden to keep track
     * of when the splash screen activity gains focus (that is, upon launching) and subsequently
     * trigger the animation of the logo.
     *
     * @param hasFocus Whether the window of this activity has focus.
     */
    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)
        animateSplashScreen()
    }

    /**
     * Initializes the components of the splash screen.
     */
    private fun initSplashScreen() {
        val ivLogo = findViewById<ImageView>(R.id.iv_splash_logo)
        ivLogo.setBackgroundResource(R.drawable.logo_animation)
        logoAnimation = ivLogo.background as AnimationDrawable
    }

    /**
     * Starts the animation of the logo featured on the splash screen.
     */
    private fun animateSplashScreen() {
        logoAnimation.setExitFadeDuration(AnimationDuration.ANIMATION_FRAME_FADEOUT)
        logoAnimation.start()
    }

    /**
     * Launches the activity immediately following the splash screen after a set duration.
     */
    private fun launchLogIn() {
        Handler(Looper.getMainLooper()).postDelayed({

            val i = Intent(this@MainActivity, LogInActivity::class.java)
            startActivity(i)
            finish()

        }, AnimationDuration.SPLASH_SCREEN_TIMEOUT.toLong())
    }
}