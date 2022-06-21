package com.example.skucise.LayoutActivities.ReservationActivities.SentReservations

import android.content.Context
import android.content.ContextWrapper
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ScrollView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.skucise.LayoutActivities.LoginActivities.SessionManager
import com.example.skucise.R
import com.example.skucise.adapter.SentReservationAdapter
import com.example.skucise.models.HelperModels.Status
import com.example.skucise.models.Reservation
import com.example.skucise.repository.ReservationRepository
import com.example.skucise.utils.SpacingItemDecoration
import com.example.skucise.viewModels.reservations.ReservationViewModel
import com.example.skucise.viewModels.reservations.ReservationViewModelFactory
import org.joda.time.LocalDateTime


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [SentReservationOnHoldFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class SentReservationOnHoldFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private lateinit var recyclerViewSentReservation: RecyclerView
    private lateinit var dataholderReservation: ArrayList<Reservation>
    private lateinit var reservationViewModel: ReservationViewModel
    private lateinit var reservations: List<Reservation>
    private lateinit var scrollView: ScrollView
    private lateinit var noText: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }

        var session: SessionManager = SessionManager(requireActivity().applicationContext)
        var hash: HashMap<String, String> = session.getUserDetails()
        var id = hash[SessionManager.KEY_ID]

        val reservationRepository = ReservationRepository()
        val reservationViewModelFactory = ReservationViewModelFactory(reservationRepository)
        reservationViewModel = ViewModelProvider(this, reservationViewModelFactory).get(
            ReservationViewModel::class.java
        )

        id?.toLong()?.let { reservationViewModel.getSentReservations(it, Status.Pending) }

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

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        var view = inflater.inflate(R.layout.fragment_sent_reservation_on_hold, container, false)
//        scrollView = view.findViewById(R.id.scrollViewReservation)
//        scrollView.post(Runnable { scrollView.fullScroll(ScrollView.FOCUS_UP) })
        return view
    }

    private fun fillDates() {
        if (!reservations.isNullOrEmpty()) {
            for ((index, value) in reservations.withIndex()) {
                reservations[index].date =
                    LocalDateTime.parse(reservations[index].dateOfReservation)
            }
        }
    }

    private fun initReservations(view: View) {
        noText = view.findViewById(R.id.nemaOglasaTekst)

        recyclerViewSentReservation =
            view.findViewById(com.example.skucise.R.id.recyclerViewReceivedReservations)
        recyclerViewSentReservation.layoutManager = LinearLayoutManager(view.context)
        recyclerViewSentReservation.isNestedScrollingEnabled = false;

        //razdvajam iteme u recyclerView
        var itemDecoration = SpacingItemDecoration(20)
        recyclerViewSentReservation.addItemDecoration(itemDecoration)
        dataholderReservation = java.util.ArrayList()

        for (res in reservations) {
            dataholderReservation.add(res)
        }

        recyclerViewSentReservation.adapter = SentReservationAdapter(
            dataholderReservation,
            context?.lifecycleOwner(),
            reservationViewModel,
            id.toLong()
        )
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment SentReservationOnHoldFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            SentReservationOnHoldFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
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

}