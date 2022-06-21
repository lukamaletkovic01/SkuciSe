
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager

import androidx.lifecycle.Lifecycle

import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.skucise.LayoutActivities.ReservationActivities.ReceivedReservations.ReservationOnHoldFragment
import com.example.skucise.LayoutActivities.ReservationActivities.ReceivedReservations.ReservationsConfirmedFragment
import com.example.skucise.LayoutActivities.ReservationActivities.ReceivedReservations.ReservationsRejectedFragment


class ReservationFragmentsAdapter(fragmentManager: FragmentManager, lifecycle: Lifecycle) :
    FragmentStateAdapter(fragmentManager, lifecycle) {
    override fun createFragment(position: Int): Fragment {
        when (position) {
            1 -> return ReservationsConfirmedFragment()
            2 -> return ReservationsRejectedFragment()
        }
        return ReservationOnHoldFragment()
    }

    override fun getItemCount(): Int {
        return 3
    }
}