package com.example.skucise.LayoutActivities.CategoryActivities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.airbnb.lottie.LottieAnimationView
import com.example.skucise.R
import com.example.skucise.adapter.HomeScreenAdapter
import com.example.skucise.adapter.RealtyScreenAdapter
import com.example.skucise.models.Advert
import com.example.skucise.repository.AdvertRepository
import com.example.skucise.viewModels.adverts.AdvertViewModel
import com.example.skucise.viewModels.adverts.AdvertViewModelFactory

class RealtyTypeActivity : AppCompatActivity() {

    private lateinit var advertViewModel: AdvertViewModel
    private lateinit var recyclerViewAdvert: RecyclerView
    private lateinit var dataholderAdverts: ArrayList<Advert>
    private lateinit var naslovStranice: TextView
    private lateinit var brojItema: TextView
    private lateinit var loader: LottieAnimationView
    private var id: Long = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_realty_type)
        init()
    }

    public fun redirectHome(view: View)
    {
        this.finish()
    }

    private fun init() {
        val extras = intent.extras;
        loader = findViewById(R.id.lt_javrvis)
        loader.visibility=View.VISIBLE
        if (extras != null) {
            naslovStranice = findViewById(R.id.naslovKategorija)
            brojItema = findViewById(R.id.brojItema)
            id= extras.getSerializable("RealtyId").toString().toLong() + 1.toLong()

            if(id == 1.toLong())
                naslovStranice.text = "Kuće"
            else if(id == 2.toLong())
                naslovStranice.text = "Stanovi"
            else if(id == 3.toLong())
                naslovStranice.text = "Garaže"
            else if(id == 4.toLong())
                naslovStranice.text = "Parking mesta"
            else if(id == 5.toLong())
                naslovStranice.text = "Poslovni prostori"
            else naslovStranice.text = "Kategorije"

            recyclerViewAdvert = findViewById(R.id.recyclerViewRealty)
            recyclerViewAdvert.layoutManager = LinearLayoutManager(this)
            recyclerViewAdvert.isNestedScrollingEnabled = false;
            dataholderAdverts = ArrayList();

            val advertRepository = AdvertRepository()
            val advertViewModelFactory = AdvertViewModelFactory(advertRepository)
            advertViewModel =
                ViewModelProvider(this, advertViewModelFactory).get(AdvertViewModel::class.java)

            advertViewModel.adverts.observe(this, { response ->
                if (response.isSuccessful) {
                    loader.visibility=View.GONE
                    dataholderAdverts = response.body() as ArrayList<Advert>
                    recyclerViewAdvert.adapter = RealtyScreenAdapter(dataholderAdverts)

                    brojItema.text = "Pronašli smo " + dataholderAdverts.count()
                    if(dataholderAdverts.count() == 0 || dataholderAdverts.count() == 1)
                        brojItema.text = brojItema.text.toString() + " rezultat pretrage"
                    else
                        brojItema.text = brojItema.text.toString() + " rezultata pretrage"
                   // addOnTouchListenerForAdvert()
                } else {
                    Log.d("ResponseError", response.errorBody().toString())
                }
            })

            advertViewModel.getAdvertsByRentProperty(id)
        }
    }
}