package com.example.skucise.LayoutActivities.ProfilePageActivities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.skucise.LayoutActivities.LoginActivities.SessionManager
import com.example.skucise.R
import com.example.skucise.adapter.MyPropertyAdapter
import com.example.skucise.adapter.NotMyPropertyAdapter
import com.example.skucise.models.Advert
import com.example.skucise.repository.AdvertRepository
import com.example.skucise.utils.SpacingItemDecoration
import com.example.skucise.viewModels.adverts.AdvertViewModel
import com.example.skucise.viewModels.adverts.AdvertViewModelFactory

class AllProductsFromForeignerActivity : AppCompatActivity() {

    private lateinit var advertViewModel: AdvertViewModel
    private lateinit var recyclerViewOglas : RecyclerView
    private lateinit var dataholderOglas : ArrayList<Advert>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_all_products_from_foreigner)
        init()
    }

    private fun init()
    {
        var id:Long = 0
        var extras = intent.extras
        if(extras != null)
            id = extras.getString("id").toString().toLong()
        val advertRepository = AdvertRepository()
        val advertViewModelFactory = AdvertViewModelFactory(advertRepository)

        advertViewModel =
            ViewModelProvider(this, advertViewModelFactory).get(AdvertViewModel::class.java)

        recyclerViewOglas = findViewById(R.id.recyclerViewMyProducts)
        recyclerViewOglas.layoutManager = LinearLayoutManager(this)
        recyclerViewOglas.isNestedScrollingEnabled = false;
        var itemDecoration = SpacingItemDecoration(20)
        recyclerViewOglas.addItemDecoration(itemDecoration)
        dataholderOglas = java.util.ArrayList()

        advertViewModel.adverts.observe(this, { response ->
            if (response.isSuccessful) {
                dataholderOglas = response.body() as ArrayList<Advert>
                recyclerViewOglas.adapter = NotMyPropertyAdapter(dataholderOglas)
            } else {
                Log.d("ResponseError", response.errorBody().toString())
            }
        })
        advertViewModel.getAdvertsByOwnerId(id!!.toLong())
    }
}