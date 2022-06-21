package com.example.skucise.fragments

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.view.animation.LayoutAnimationController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.skucise.models.Advert
import com.example.skucise.R
import com.example.skucise.adapter.HomeScreenAdapter
import com.example.skucise.LayoutActivities.BasicActivities.RecyclerItemClickListener
import android.widget.*
import androidx.activity.addCallback
import androidx.lifecycle.ViewModelProvider
import com.airbnb.lottie.LottieAnimationView
import com.example.skucise.LayoutActivities.BasicActivities.LoadingUtils
import com.example.skucise.LayoutActivities.CategoryActivities.RealtyTypeActivity
import com.example.skucise.LayoutActivities.ProductPageActivies.ProductPageActivity
import com.example.skucise.adapter.HomeScreenCategoryAdapter
import com.example.skucise.models.Category
import com.example.skucise.repository.AdvertRepository
import com.example.skucise.services.SkuciSeFirebaseMessagingService
import com.example.skucise.viewModels.adverts.*

class HomeFragment : Fragment() {
    private lateinit var advertViewModel: AdvertViewModel

    private lateinit var recyclerViewAdvert: RecyclerView
    private lateinit var recyclerViewCategory: RecyclerView

    private lateinit var dataholderAdverts: ArrayList<Advert>
    private lateinit var dataholderCategory: ArrayList<Category>
    private lateinit var loader: LottieAnimationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        init()
    }

    private fun init() {
        dataholderAdverts = ArrayList();

        val advertRepository = AdvertRepository()
        val advertViewModelFactory = AdvertViewModelFactory(advertRepository)
        advertViewModel =
            ViewModelProvider(this, advertViewModelFactory).get(AdvertViewModel::class.java)

        advertViewModel.adverts.observe(this) { response ->
            if (response.isSuccessful) {
                loader.visibility = View.GONE
                dataholderAdverts = response.body() as ArrayList<Advert>
                recyclerViewAdvert.adapter = HomeScreenAdapter(dataholderAdverts)
//                recyclerViewAdvert.startLayoutAnimation()
                addOnTouchListenerForAdvert()
            } else {
                Log.d("ResponseError", response.errorBody().toString())
            }
        }

        advertViewModel.getAdverts(1)
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view: View = inflater.inflate(R.layout.fragment_home, container, false)
        loader = view.findViewById(R.id.lt_javrvis)
        loader.visibility=View.VISIBLE
        init(view)
        setDropdown(view)
        dodajKategorije(view)

        return view
    }

    private fun init(view: View) {
        recyclerViewAdvert = view.findViewById(R.id.recyclerView1)
        recyclerViewAdvert.layoutManager = LinearLayoutManager(context)
        recyclerViewAdvert.isNestedScrollingEnabled = false;
        val lac = LayoutAnimationController(AnimationUtils.loadAnimation(view.context,R.anim.fall_down))
        lac.delay = 0.20f
        lac.order = LayoutAnimationController.ORDER_NORMAL
        recyclerViewAdvert.layoutAnimation = lac
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

    private fun addOnTouchListenerForCategory() {
        recyclerViewCategory.addOnItemTouchListener(
            RecyclerItemClickListener(
                context,
                recyclerViewCategory,
                object : RecyclerItemClickListener.OnItemClickListener {
                    override fun onItemClick(view: View?, position: Int) {
                        activity?.let {
                            val intent = Intent(it, RealtyTypeActivity::class.java)
                            intent.putExtra("RealtyId", position)
                            it.startActivity(intent)
                        }
                    }

                    override fun onLongItemClick(view: View?, position: Int) {
                    }
                })
        )
    }

    private fun setDropdown(view: View) {
        val types = arrayOf("Izdavanje", "Prodaja")
        val spinner = view.findViewById<Spinner>(R.id.spinner1)

        spinner?.adapter = activity?.applicationContext?.let {
            ArrayAdapter(
                it,
                android.R.layout.simple_spinner_dropdown_item,
                types
            )
        } as SpinnerAdapter
        spinner?.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {
            }

            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                advertViewModel.getAdverts(position + 1)
            }
        }
    }

    private fun dodajKategorije(view: View) {
        //pristupam rec view za category
        recyclerViewCategory = view.findViewById(R.id.recyclerViewCategory)
        recyclerViewCategory.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        recyclerViewCategory.isNestedScrollingEnabled = false;

        dataholderCategory = java.util.ArrayList()
        dataholderCategory = napraviKategorije()
        recyclerViewCategory.adapter = HomeScreenCategoryAdapter(dataholderCategory)
        addOnTouchListenerForCategory()
    }

    private fun napraviKategorije(): ArrayList<Category> {
        val cat1 = Category(R.drawable.category_house, "Kuće")
        val cat2 = Category(R.drawable.oglas3, "Stanovi")
        val cat3 = Category(R.drawable.category_garrage, "Garaže")
        val cat4 = Category(R.drawable.category_parking_lott, "Parking mesta")
        val cat5 = Category(R.drawable.category_business_place, "Poslovni prostori")
        dataholderCategory.add(cat1)
        dataholderCategory.add(cat2)
        dataholderCategory.add(cat3)
        dataholderCategory.add(cat4)
        dataholderCategory.add(cat5)

        return dataholderCategory
    }
}
