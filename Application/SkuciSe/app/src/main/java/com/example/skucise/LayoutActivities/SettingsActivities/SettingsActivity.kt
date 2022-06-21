package com.example.skucise.LayoutActivities.SettingsActivities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.skucise.LayoutActivities.HomeScreenActivities.HomeScreenActivity
import com.example.skucise.R

class SettingsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)
    }


    public fun redirectHome(view: View){

        val intent = Intent(this, HomeScreenActivity::class.java)
        startActivity(intent)
    }
    public fun redirectChangePassword(view: View){

        val intent = Intent(this, ChangePasswordActivity::class.java)
        startActivity(intent)
    }
    public fun redirectReportProblem(view: View){

        val intent = Intent(this, ReportProblemActivity::class.java)
        startActivity(intent)
    }

}