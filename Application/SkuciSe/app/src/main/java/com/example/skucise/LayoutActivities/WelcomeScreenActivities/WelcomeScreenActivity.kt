package com.example.skucise.LayoutActivities.WelcomeScreenActivities

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.annotation.RequiresApi
import com.example.skucise.LayoutActivities.LoginActivities.LoginScreenActivity
import com.example.skucise.LayoutActivities.LoginActivities.SessionManager
import com.example.skucise.LayoutActivities.RegistrationActivities.RegistrationScreenActivity
import com.example.skucise.R

class WelcomeScreenActivity : AppCompatActivity() {

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_welcome_screen)

        lateinit var session: SessionManager
        session = SessionManager(applicationContext)

        session.checkLogin()
//        if (intent.hasExtra("pushnotification")) {
//            val intent = Intent(this, ReceivedReservationsActivity::class.java)
//            startActivity(intent)
//            finish()
//        }
    }

    //funkcija koja vrsi redirekt sa jedne stranice na drugu (sa pocetne na login)
    public fun redirectLogin(view: View)
    {

        val intent = Intent(this, LoginScreenActivity::class.java)
        startActivity(intent)

    }
    public fun redirectRegistration(view: View){

            val intent = Intent(this, RegistrationScreenActivity::class.java)
            startActivity(intent)

    }
}