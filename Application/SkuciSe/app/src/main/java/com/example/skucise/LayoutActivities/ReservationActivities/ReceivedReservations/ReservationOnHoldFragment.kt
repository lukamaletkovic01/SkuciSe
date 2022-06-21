package com.example.skucise.LayoutActivities.ReservationActivities.ReceivedReservations

import android.content.Context
import android.content.ContextWrapper
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.skucise.LayoutActivities.LoginActivities.SessionManager
import com.example.skucise.R
import com.example.skucise.adapter.ReceivedReservationsAdapter
import com.example.skucise.models.HelperModels.Status
import com.example.skucise.models.Reservation
import com.example.skucise.repository.NotificationRepository
import com.example.skucise.repository.ReservationRepository
import com.example.skucise.utils.SpacingItemDecoration
import com.example.skucise.viewModels.NotificationViewModel
import com.example.skucise.viewModels.NotificationViewModelFactory
import com.example.skucise.viewModels.reservations.ReservationViewModel
import com.example.skucise.viewModels.reservations.ReservationViewModelFactory
import org.joda.time.LocalDateTime

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [ReservationOnHoldFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ReservationOnHoldFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    private var userId: Long? = null

    private lateinit var notificationViewModel: NotificationViewModel
    private lateinit var recyclerViewReceivedReservation: RecyclerView
    private lateinit var dataholderReservation: ArrayList<Reservation>
    private lateinit var reservationViewModel: ReservationViewModel
    private lateinit var reservations: List<Reservation>
    private lateinit var noText: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }

        var session: SessionManager = SessionManager(requireActivity().applicationContext)
        var hash: HashMap<String, String> = session.getUserDetails()
        userId = (hash.get(SessionManager.KEY_ID))?.toLong()

        val reservationRepository = ReservationRepository()
        val reservationViewModelFactory = ReservationViewModelFactory(reservationRepository)
        reservationViewModel = ViewModelProvider(this, reservationViewModelFactory).get(
            ReservationViewModel::class.java
        )

        userId?.toLong()?.let { reservationViewModel.getReceivedReservations(it, Status.Pending) }

        reservationViewModel.reservations.observe(this, Observer { response ->
            if (response.isSuccessful) {
                reservations = response.body() as List<Reservation>
                fillDates()
                view?.let { initReservations(it) }
                if(reservations.count() == 0)
                {
                    noText.visibility = View.VISIBLE
                }
            } else {
                Log.d("GRESKA", "Nije uspelo")
            }

        })
    }

    private fun fillDates() {
        if (!reservations.isNullOrEmpty()) {
            for ((index, value) in reservations.withIndex()) {
                reservations[index].date =
                    LocalDateTime.parse(reservations[index].dateOfReservation)
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_reservation_on_hold, container, false)
    }

    private fun initReservations(view: View) {
        noText = view.findViewById(R.id.nemaOglasaTekst)

        val notificationRepository = NotificationRepository()
        val notificationViewModelFactory = NotificationViewModelFactory(notificationRepository)

        notificationViewModel = ViewModelProvider(
            this,
            notificationViewModelFactory
        ).get(NotificationViewModel::class.java)

        recyclerViewReceivedReservation =
            view.findViewById(com.example.skucise.R.id.recyclerViewReceivedReservations)
        recyclerViewReceivedReservation.layoutManager = LinearLayoutManager(view.context)
        recyclerViewReceivedReservation.isNestedScrollingEnabled = false;

        //razdvajam iteme u recyclerView
        var itemDecoration = SpacingItemDecoration(20)
        recyclerViewReceivedReservation.addItemDecoration(itemDecoration)
        dataholderReservation = java.util.ArrayList()

        for (res in reservations) {
            dataholderReservation.add(res)
        }

        recyclerViewReceivedReservation.adapter = ReceivedReservationsAdapter(
            dataholderReservation,
            context?.lifecycleOwner(), notificationViewModel, userId
        )
    }

    private fun init(view: View) {

    }

    private fun Context.lifecycleOwner(): LifecycleOwner? {
        var curContext = this
        var maxDepth = 20
        while (maxDepth-- > 0 && curContext !is LifecycleOwner) {
            curContext = (curContext as ContextWrapper).baseContext
        }
        return if (curContext is LifecycleOwner) {
            curContext
        } else {
            null
        }
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment ReservationOnHoldFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            ReservationOnHoldFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}