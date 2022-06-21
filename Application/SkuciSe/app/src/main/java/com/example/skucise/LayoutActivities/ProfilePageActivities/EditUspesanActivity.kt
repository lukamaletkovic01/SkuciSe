package com.example.skucise.LayoutActivities.ProfilePageActivities

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.skucise.LayoutActivities.HomeScreenActivities.HomeScreenActivity
import com.example.skucise.R
import com.example.skucise.fragments.ProfileFragment

class EditUspesanActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_uspesan)
    }

    public fun redirectHome(view: View){

        val intent = Intent(this, HomeScreenActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
    }

    public fun redirectProfile(view: View){
        val intent = Intent(this, HomeScreenActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        intent.putExtra("frgToLoad", "Profile")
        startActivity(intent)
    }
}