package com.example.skucise.LayoutActivities.CommentActivities

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.view.animation.LayoutAnimationController
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.skucise.LayoutActivities.LoginActivities.SessionManager
import com.example.skucise.R
import com.example.skucise.adapter.ReceivedCommentsAdapter
import com.example.skucise.models.Comment
import com.example.skucise.repository.AdvertRepository
import com.example.skucise.viewModels.adverts.AdvertViewModel
import com.example.skucise.viewModels.adverts.AdvertViewModelFactory

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [SentCommentsFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class SentCommentsFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private lateinit var advertViewModel: AdvertViewModel
    private lateinit var recyclerViewComments: RecyclerView
    private lateinit var dataholderComments: ArrayList<Comment>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_sent_comments, container, false)
        var receivedText = view.findViewById<TextView>(R.id.poslateRecenzijeText)
//        receivedText.visibility = View.GONE
        recyclerViewComments = view.findViewById(R.id.recyclerViewSentComments)
        recyclerViewComments.layoutManager = LinearLayoutManager(context)
        recyclerViewComments.isNestedScrollingEnabled = false;
        val lac = LayoutAnimationController(AnimationUtils.loadAnimation(view.context,R.anim.fall_down))
        lac.delay = 0.20f
        lac.order = LayoutAnimationController.ORDER_NORMAL
        recyclerViewComments.layoutAnimation = lac
        // Inflate the layout for this fragment

        dataholderComments = ArrayList();
        var session : SessionManager = SessionManager(view.context)
        var hash : HashMap<String, String> = session.getUserDetails()
        var userId = hash.get(SessionManager.KEY_ID)
        var id = userId.toString().toLong()
        val advertRepository = AdvertRepository()
        val advertViewModelFactory = AdvertViewModelFactory(advertRepository)
        advertViewModel =
            ViewModelProvider(this, advertViewModelFactory).get(AdvertViewModel::class.java)

        advertViewModel.getCommentsResponse.observe(viewLifecycleOwner, { response ->
            if (response.isSuccessful) {
                dataholderComments = response.body() as ArrayList<Comment>
                recyclerViewComments.adapter = ReceivedCommentsAdapter(dataholderComments)



            } else {
                Log.d("ResponseError", response.errorBody().toString())
            }
        })

        advertViewModel.getSentComments(id)

        // Inflate the layout for this fragment
        return view
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment SentCommentsFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            SentCommentsFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}