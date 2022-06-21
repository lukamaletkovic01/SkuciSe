package com.example.skucise.LayoutActivities.CommentActivities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.fragment.app.FragmentManager
import androidx.viewpager2.widget.ViewPager2
import com.example.skucise.LayoutActivities.HomeScreenActivities.HomeScreenActivity
import com.example.skucise.R
import com.example.skucise.adapter.CommentsFragmentsAdapter
import com.google.android.material.tabs.TabLayout

class CommentsActivity : AppCompatActivity() {

    private lateinit var tabLayout: TabLayout
    private lateinit var pager2: ViewPager2
    var adapter: CommentsFragmentsAdapter? = null
    public fun RedirectNazad(view: View)
    {
        this.finish()
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_comments)
        tabLayout = findViewById(com.example.skucise.R.id.tabLayoutComments)
        pager2 = findViewById(com.example.skucise.R.id.viewPagerComments)

        val fm: FragmentManager = supportFragmentManager
        adapter = CommentsFragmentsAdapter(fm, lifecycle)
        pager2.adapter = adapter

        tabLayout.addTab(tabLayout.newTab().setText("Primljene"))
        tabLayout.addTab(tabLayout.newTab().setText("Poslate"))

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
    }
}