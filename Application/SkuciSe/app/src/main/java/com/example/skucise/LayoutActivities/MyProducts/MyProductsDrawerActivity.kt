package com.example.skucise.LayoutActivities.MyProducts

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.skucise.LayoutActivities.LoginActivities.SessionManager
import com.example.skucise.R
import com.example.skucise.adapter.MyPropertyAdapter
import com.example.skucise.models.Advert
import com.example.skucise.repository.AdvertRepository
import com.example.skucise.utils.SpacingItemDecoration
import com.example.skucise.viewModels.adverts.AdvertViewModel
import com.example.skucise.viewModels.adverts.AdvertViewModelFactory

class MyProductsDrawerActivity : AppCompatActivity() {

    private lateinit var advertViewModel: AdvertViewModel
    private lateinit var recyclerViewOglas : RecyclerView
    private lateinit var dataholderOglas : ArrayList<Advert>
    private lateinit var noAdvertsText : TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_products_drawer)

        init()
    }

    private fun init()
    {
        var session : SessionManager = SessionManager(this)
        var hash : HashMap<String, String> = session.getUserDetails()
        var id = hash.get(SessionManager.KEY_ID)
        val advertRepository = AdvertRepository()
        val advertViewModelFactory = AdvertViewModelFactory(advertRepository)

        noAdvertsText = findViewById(R.id.nemaOglasaTekst)

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
                recyclerViewOglas.adapter = MyPropertyAdapter(dataholderOglas)
                if(dataholderOglas.count() == 0)
                    noAdvertsText.visibility = View.VISIBLE

            } else {
                Log.d("ResponseError", response.errorBody().toString())
            }
        })
        advertViewModel.getAdvertsByOwnerId(id!!.toLong())
    }

    public fun goBack(view:View)
    {
        this.finish()
    }
}