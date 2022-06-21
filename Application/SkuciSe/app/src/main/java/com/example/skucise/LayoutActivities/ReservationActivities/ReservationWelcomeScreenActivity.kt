package com.example.skucise.LayoutActivities.ReservationActivities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.skucise.LayoutActivities.HomeScreenActivities.HomeScreenActivity
import com.example.skucise.LayoutActivities.NewRealEstate.NewRealEstatePicturesAddActivity
import com.example.skucise.LayoutActivities.ReservationActivities.ReceivedReservations.ReceivedReservationsActivity
import com.example.skucise.LayoutActivities.ReservationActivities.SentReservations.SentReservationsActivity
import com.example.skucise.R

class ReservationWelcomeScreenActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reservation_welcome_screen)
    }

    public fun redirectReceived(view: View)
    {
        val intent = Intent(this, ReceivedReservationsActivity::class.java)
        startActivity(intent)
    }

    public fun redirectSent(view: View)
    {
        val intent = Intent(this, SentReservationsActivity::class.java)
        startActivity(intent)
    }

    fun redirectBack(view: View) {
        val intent = Intent(this, HomeScreenActivity::class.java)
        startActivity(intent)
    }
}