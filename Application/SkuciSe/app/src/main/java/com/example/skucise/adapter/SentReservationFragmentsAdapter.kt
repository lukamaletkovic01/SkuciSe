package com.example.skucise.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.skucise.LayoutActivities.ReservationActivities.SentReservations.SentReservationConfirmedFragment
import com.example.skucise.LayoutActivities.ReservationActivities.SentReservations.SentReservationOnHoldFragment
import com.example.skucise.LayoutActivities.ReservationActivities.SentReservations.SentReservationRejectedFragment

class SentReservationFragmentsAdapter(fragmentManager: FragmentManager, lifecycle: Lifecycle) :
    FragmentStateAdapter(fragmentManager, lifecycle) {
    override fun createFragment(position: Int): Fragment {
        when (position) {
            1 -> return SentReservationConfirmedFragment()
            2 -> return SentReservationRejectedFragment()
        }
        return SentReservationOnHoldFragment()
    }

    override fun getItemCount(): Int {
        return 3
    }
}