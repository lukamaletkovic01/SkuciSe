package com.example.skucise.fragments

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.skucise.models.Advert
import com.example.skucise.R
import com.example.skucise.adapter.HomeScreenAdapter
import android.widget.*
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.airbnb.lottie.LottieAnimationView
import com.example.skucise.LayoutActivities.BasicActivities.RecyclerItemClickListener
import com.example.skucise.LayoutActivities.CategoryActivities.RealtyTypeActivity
import com.example.skucise.LayoutActivities.ProductPageActivies.ProductPageActivity
import com.example.skucise.LayoutActivities.Search.SearchFiltersActivity
import com.example.skucise.adapter.SearchViewAdapter
import com.example.skucise.repository.AdvertRepository
import com.example.skucise.viewModels.adverts.*


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [SearchFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class SearchFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private lateinit var searchView: SearchView
    private lateinit var advertViewModel: AdvertViewModel
    private lateinit var recyclerViewAdvert: RecyclerView
    private lateinit var dataholderAdverts: ArrayList<Advert>
    private lateinit var numberOfAdverts: TextView
    private lateinit var loader: LottieAnimationView


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
        val view: View = inflater.inflate(R.layout.fragment_search, container, false)
        loader = view.findViewById(R.id.lt_javrvis)
        init(view)
        addListenerForSearchView()
        addListenerForAdverts()

        return view
    }

    private fun addListenerForAdverts() {
        advertViewModel.adverts.observe(viewLifecycleOwner, { response ->
            if (response.isSuccessful) {
                loader.visibility=View.GONE
                dataholderAdverts = response.body() as ArrayList<Advert>
                numberOfAdverts.text = "Pronasli smo " + dataholderAdverts.count()
                if(dataholderAdverts.count() == 0 || dataholderAdverts.count() == 1)
                    numberOfAdverts.text = numberOfAdverts.text.toString() + " rezultat pretrage"
                else
                    numberOfAdverts.text = numberOfAdverts.text.toString() + " rezultata pretrage"

                recyclerViewAdvert.adapter = SearchViewAdapter(dataholderAdverts)
//                addOnTouchListenerForAdvert()

            } else {
                Log.d("ResponseError", response.errorBody().toString())
            }
        })


    }

    private fun addOnTouchListenerForAdvert() {
        recyclerViewAdvert.addOnItemTouchListener(
            RecyclerItemClickListener(
                context,
                recyclerViewAdvert,
                object : RecyclerItemClickListener.OnItemClickListener {
                    override fun onItemClick(view: View?, position: Int) {
                        activity?.let {
                            val intent = Intent(it, ProductPageActivity::class.java)

                            intent.putExtra("advertId", dataholderAdverts[position].id)
                            it.startActivity(intent)
                        }
                    }

                    override fun onLongItemClick(view: View?, position: Int) {
                    }
                })
        )
    }

    private fun addListenerForSearchView() {
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextChange(newText: String?): Boolean {
                if(searchView.query.toString() != "") {
                    loader.visibility = View.VISIBLE
                    advertViewModel.getAdvertsByName(searchView.query.toString())
                }
                else
                    loader.visibility = View.GONE

                return false
            }

            override fun onQueryTextSubmit(query: String?): Boolean {
                if(searchView.query.toString() != "") {
                    loader.visibility = View.VISIBLE
                    advertViewModel.getAdvertsByName(searchView.query.toString())
                }
                else
                    loader.visibility = View.GONE

                return false
            }
        })
    }

    private fun init(view: View) {
        numberOfAdverts = view.findViewById(R.id.resultsNumber)
        searchView = view.findViewById(R.id.searchViewSearchFragment)
        dataholderAdverts = ArrayList();
        recyclerViewAdvert = view.findViewById(R.id.recyclerViewSearch)
        recyclerViewAdvert.layoutManager = LinearLayoutManager(context)
        recyclerViewAdvert.isNestedScrollingEnabled = false;

        val advertRepository = AdvertRepository()
        val advertViewModelFactory = AdvertViewModelFactory(advertRepository)
        advertViewModel =
            ViewModelProvider(this, advertViewModelFactory).get(AdvertViewModel::class.java)

        var filters = view.findViewById<ImageView>(R.id.filters)
        filters.setOnClickListener(View.OnClickListener {
            activity?.let {
                val intent = Intent(it, SearchFiltersActivity::class.java)
                it.startActivity(intent)
            }
        })

    }
    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment SearchFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            SearchFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}