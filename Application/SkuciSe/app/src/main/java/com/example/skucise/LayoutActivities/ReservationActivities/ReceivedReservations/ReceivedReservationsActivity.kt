package com.example.skucise.LayoutActivities.ReservationActivities.ReceivedReservations

import ReservationFragmentsAdapter
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.skucise.models.Reservation
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import androidx.fragment.app.FragmentManager
import androidx.viewpager2.widget.ViewPager2.OnPageChangeCallback
import com.example.skucise.LayoutActivities.HomeScreenActivities.HomeScreenActivity
import com.example.skucise.LayoutActivities.ReservationActivities.ReservationWelcomeScreenActivity
import com.google.android.material.tabs.TabLayout.OnTabSelectedListener


class ReceivedReservationsActivity : AppCompatActivity() {

    private lateinit var tabLayout: TabLayout
    private lateinit var pager2: ViewPager2
    var adapter: ReservationFragmentsAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(com.example.skucise.R.layout.activity_received_reservations)

        tabLayout = findViewById(com.example.skucise.R.id.tabLayoutReservations)
        pager2 = findViewById(com.example.skucise.R.id.viewPagerReservations)

        val fm: FragmentManager = supportFragmentManager
        adapter = ReservationFragmentsAdapter(fm, lifecycle)
        pager2.adapter = adapter

        tabLayout.addTab(tabLayout.newTab().setText("Na cekanju"))
        tabLayout.addTab(tabLayout.newTab().setText("Prihvaceno"))
        tabLayout.addTab(tabLayout.newTab().setText("Odbijeno"))

        tabLayout.addOnTabSelectedListener(object : OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                pager2.currentItem = tab.position
            }

            override fun onTabUnselected(tab: TabLayout.Tab) {}
            override fun onTabReselected(tab: TabLayout.Tab) {}
        })


        pager2.registerOnPageChangeCallback(object : OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                tabLayout.selectTab(tabLayout.getTabAt(position))
            }
        })
    }

    fun redirectBack(view: View) {
        val intent = Intent(this, ReservationWelcomeScreenActivity::class.java)
        startActivity(intent)
    }
}
