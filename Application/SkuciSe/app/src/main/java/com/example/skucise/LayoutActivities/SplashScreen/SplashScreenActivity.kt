package com.example.skucise.LayoutActivities.SplashScreen

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.content.Intent
import android.os.Handler
import com.example.skucise.LayoutActivities.WelcomeScreenActivities.WelcomeScreenActivity
import com.example.skucise.R

class SplashScreenActivity : AppCompatActivity() {

    // This is the loading time of the splash screen
    private val SPLASH_TIME_OUT:Long = 3000 // 1 sec
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)


        Handler().postDelayed({
            // This method will be executed once the timer is over
            // Start your app main activity

            startActivity(Intent(this,WelcomeScreenActivity::class.java))

            // close this activity
            finish()
        }, SPLASH_TIME_OUT)
    }
}