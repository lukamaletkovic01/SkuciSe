package com.example.skucise.LayoutActivities.ReservationActivities.SentReservations

import android.R.attr
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.FragmentManager
import androidx.viewpager2.widget.ViewPager2
import com.example.skucise.R
import com.example.skucise.adapter.SentReservationFragmentsAdapter
import com.google.android.material.tabs.TabLayout
import android.R.attr.defaultValue
import android.content.Intent
import android.util.Log
import android.view.View
import com.example.skucise.LayoutActivities.ReservationActivities.ReservationWelcomeScreenActivity


class SentReservationsActivity : AppCompatActivity() {
    private lateinit var tabLayout: TabLayout
    private lateinit var pager2: ViewPager2
    var adapter: SentReservationFragmentsAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(com.example.skucise.R.layout.activity_sent_reservations)

        tabLayout = findViewById(com.example.skucise.R.id.tabLayoutSentReservations)
        pager2 = findViewById(com.example.skucise.R.id.viewPagerSentReservations)

        val fm: FragmentManager = supportFragmentManager
        adapter = SentReservationFragmentsAdapter(fm, lifecycle)
        pager2.adapter = adapter
//        pager2.isUserInputEnabled = false

        tabLayout.addTab(tabLayout.newTab().setText("Na cekanju"))
        tabLayout.addTab(tabLayout.newTab().setText("Prihvaceno"))
        tabLayout.addTab(tabLayout.newTab().setText("Odbijeno"))

        tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                pager2.currentItem = tab.position
            }

            override fun onTabUnselected(tab: TabLayout.Tab) {}
            override fun onTabReselected(tab: TabLayout.Tab) {}
        })


        pager2.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                tabLayout.selectTab(tabLayout.getTabAt(position))
            }
        })

//        val answerFromNotification = intent.getStringExtra("answer")
//        if (answerFromNotification != null) {
//            if (answerFromNotification == "accepted") {
//                pager2.currentItem = 1
//            } else if (answerFromNotification == "rejected") {
//                pager2.currentItem = 2
//            }
//        }
    }

    fun redirectBack(view: View) {
        val intent = Intent(this, ReservationWelcomeScreenActivity::class.java)
        startActivity(intent)
    }
}
