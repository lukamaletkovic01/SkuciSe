package com.example.skucise.LayoutActivities.ProfilePageActivities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.skucise.LayoutActivities.LoginActivities.SessionManager
import com.example.skucise.LayoutActivities.MyProducts.MyProductsDrawerActivity
import com.example.skucise.R
import com.example.skucise.adapter.MyPropertyAdapter
import com.example.skucise.adapter.NotMyPropertyAdapter
import com.example.skucise.models.Advert
import com.example.skucise.models.User
import com.example.skucise.repository.AdvertRepository
import com.example.skucise.repository.UserRepository
import com.example.skucise.utils.Constrants
import com.example.skucise.utils.SpacingItemDecoration
import com.example.skucise.viewModels.UserViewModel
import com.example.skucise.viewModels.UserViewModelFactory
import com.example.skucise.viewModels.adverts.AdvertViewModel
import com.example.skucise.viewModels.adverts.AdvertViewModelFactory
import com.squareup.picasso.Picasso

class ToProfileFromAdvertActivity : AppCompatActivity() {

    private lateinit var recyclerViewOglas : RecyclerView
    private lateinit var dataholderOglas : ArrayList<Advert>
    private lateinit var showmoreButton: TextView
    private lateinit var advertViewModel: AdvertViewModel
    private lateinit var userViewModel : UserViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_to_profile_from_advert)

        var id: Long = 0

        val extras = intent.extras
        if (extras != null) {
            id = extras.getString("userId").toString().toLong()
        }
        val userRepository = UserRepository()
        val userViewModelFactory = UserViewModelFactory(userRepository)

        userViewModel = ViewModelProvider(this, userViewModelFactory).get(UserViewModel::class.java)

        val advertRepository = AdvertRepository()
        val advertViewModelFactory = AdvertViewModelFactory(advertRepository)

        advertViewModel =
            ViewModelProvider(this, advertViewModelFactory).get(AdvertViewModel::class.java)

        userViewModel.getUserInfo(id)

        userViewModel.user.observe(this, Observer { response ->
            if (response.isSuccessful){

                var user = response.body() as User
                var name =  response.body()?.firstname + " " + response.body()?.lastname
                var phoneNumber = response.body()?.phoneNumber
                var address = response.body()?.address
                var city = response.body()?.city
                var photoUrl = response.body()?.profileImage
                var rating = user.userAverageRating
                var numComments = response.body()?.ratingsCount
                findViewById<TextView>(R.id.name).text = name
                findViewById<TextView>(R.id.brojTelefona).text = "Broj telefona: " + phoneNumber
                findViewById<TextView>(R.id.adresaKorisnika).text = "Adresa: " + address
                findViewById<TextView>(R.id.gradKorisnika).text = "Grad: " + city

                findViewById<TextView>(R.id.brojRecenzija).text = numComments.toString()
                findViewById<TextView>(R.id.prosecnaOcena).text = rating.toString()


                var profile_pic : ImageView = findViewById<ImageView>(R.id.slikaProfila)
                Picasso.get().load(Constrants.BASE_URL + "/Images/UserImages/$photoUrl").into(profile_pic)

                Log.d("USER", response.body().toString())
            }
            else{
                Log.d("GRESKA", "Nije uspelo")
            }

        })
        advertViewModel.getAdvertsByOwnerId(id)

        recyclerViewOglas = findViewById(R.id.recyclerViewTudjiOglasi)
        recyclerViewOglas.layoutManager = LinearLayoutManager(this)
        recyclerViewOglas.isNestedScrollingEnabled = false;
        var itemDecoration = SpacingItemDecoration(20)
        recyclerViewOglas.addItemDecoration(itemDecoration)
        dataholderOglas = java.util.ArrayList()

        showmoreButton = findViewById(R.id.showmoreButton)
        showmoreButton.setOnClickListener(View.OnClickListener {
            val intent = Intent(this, AllProductsFromForeignerActivity::class.java)
            intent.putExtra("id",id.toString())
            startActivity(intent)
        })


        advertViewModel.adverts.observe(this, { response ->
            if (response.isSuccessful) {
                dataholderOglas = response.body() as ArrayList<Advert>
                recyclerViewOglas.adapter = NotMyPropertyAdapter(dataholderOglas)
                var numRealties  = dataholderOglas.count().toString()
                findViewById<TextView>(R.id.brojNekretnina).text = numRealties.toString()
            } else {
                Log.d("ResponseError", response.errorBody().toString())
            }
        })


    }
}