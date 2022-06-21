package com.example.skucise.LayoutActivities.Favorites

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.skucise.Animations.SwipeToDeleteCallback
import com.example.skucise.LayoutActivities.BasicActivities.RecyclerItemClickListener
import com.example.skucise.LayoutActivities.HomeScreenActivities.HomeScreenActivity
import com.example.skucise.LayoutActivities.LoginActivities.SessionManager
import com.example.skucise.R
import com.example.skucise.adapter.FavScreenAdapter
import com.example.skucise.models.Advert
import com.example.skucise.repository.AdvertRepository
import com.example.skucise.utils.SpacingItemDecoration
import com.example.skucise.viewModels.adverts.AdvertViewModel
import com.example.skucise.viewModels.adverts.AdvertViewModelFactory
import com.google.android.material.snackbar.Snackbar


class FavoritesActivity : AppCompatActivity() {

    private lateinit var recyclerViewOglas: RecyclerView
    private lateinit var dataholderOglas: ArrayList<Advert>
    private lateinit var favScreenAdapter: FavScreenAdapter
    private lateinit var advertViewModel: AdvertViewModel
    private lateinit var dataholderAdvert: ArrayList<Advert>
    private lateinit var noAdvertText: TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_favorites)
        noAdvertText = findViewById(R.id.nemaOglasaTekstFavorites)
        dodajOmiljeneOglase()
    }



    private fun dodajOmiljeneOglase() {
        recyclerViewOglas = findViewById(R.id.recyclerViewFavorites)
        recyclerViewOglas.layoutManager = LinearLayoutManager(this)
        recyclerViewOglas.isNestedScrollingEnabled = false;

        //razdvajam iteme u recyclerView
        var itemDecoration = SpacingItemDecoration(20)
        recyclerViewOglas.addItemDecoration(itemDecoration)

        dataholderOglas = java.util.ArrayList()



        val advertRepository = AdvertRepository()
        val advertViewModelFactory = AdvertViewModelFactory(advertRepository)
        advertViewModel =
            ViewModelProvider(this, advertViewModelFactory).get(AdvertViewModel::class.java)
        var session : SessionManager = SessionManager(this)
        var hash : HashMap<String, String> = session.getUserDetails()
        var id = hash.get(SessionManager.KEY_ID)
        advertViewModel.getMyLikesResponse.observe(this, { response ->
            if (response.isSuccessful) {
                dataholderOglas = response.body() as ArrayList<Advert>

                favScreenAdapter = id?.let { FavScreenAdapter(dataholderOglas, it,advertViewModel) }!!
                recyclerViewOglas.adapter = favScreenAdapter

                if(dataholderOglas.count() == 0)
                    noAdvertText.visibility = View.VISIBLE

            } else {
                Log.d("ResponseError", response.errorBody().toString())
            }
        })

        advertViewModel.getMyLikes(id!!.toString().toLong())

        //sada radim swipe animaciju
        var swipeDelete = object : SwipeToDeleteCallback(this) {
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                var position = viewHolder.adapterPosition
                var obrisaniOglas = dataholderOglas[position]
                advertViewModel.postLike(id.toString().toLong(), dataholderOglas[position].id!!);
                favScreenAdapter.obrisiFavitem(position)
                Snackbar.make(recyclerViewOglas,"Uklonili ste " +  obrisaniOglas.name.toString() +  " iz omiljenih nekretnina.",Snackbar.LENGTH_LONG)
                    .setAction("Ponisti", View.OnClickListener {
                        dataholderOglas.add(position,obrisaniOglas)
                        advertViewModel.postLike(id.toString().toLong(), dataholderOglas[position].id!!);
                        favScreenAdapter.notifyDataSetChanged()
                    }).show()
            }
        }
        val touchHelper = ItemTouchHelper(swipeDelete)
        touchHelper.attachToRecyclerView(recyclerViewOglas)
       // addOnTouchListenerForFavItem()

    }

    /*private fun napraviOmiljeneOglase() {


        val advertRepository = AdvertRepository()
        val advertViewModelFactory = AdvertViewModelFactory(advertRepository)
        advertViewModel =
            ViewModelProvider(this, advertViewModelFactory).get(AdvertViewModel::class.java)

        advertViewModel.getMyLikesResponse.observe(viewLifecycleOwner, { response ->
            if (response.isSuccessful) {
                dataholderAdvert = response.body()!!
                dataholderOglas = response.body()!!

                recyclerViewFavorites.adapter = FavScreenAdapter(dataholderAdvert)
            } else {
                Log.d("ResponseError", response.errorBody().toString())
            }
        })

        // advertViewModel.getLikeIfExist(advertId!!);



    }*/

    private fun addOnTouchListenerForFavItem() {
        recyclerViewOglas.addOnItemTouchListener(
            RecyclerItemClickListener(
                this,
                recyclerViewOglas,
                object : RecyclerItemClickListener.OnItemClickListener {
                    override fun onItemClick(view: View?, position: Int) {
//                            var data = dataholderOglas[position]
//                            val intent: Intent = Intent(this@FavoritesActivity,
//                                ProductPageActivity::class.java)
//                            print(data.ocena)
//                            intent.putExtra("naslov", data.naslov)
//                            intent.putExtra("adresa", data.adresa)
//                            var cena = data.cena
//                            if(data.tipOglasa.equals("Izdavanje"))
//                                cena+="/mesec"
//                            else cena+="/m2"
//                            intent.putExtra("cena", cena)
//                            intent.putExtra("ocena", data.ocena)
//                            intent.putExtra("slika", data.slika)
                            startActivity(null)
                    }
                    override fun onLongItemClick(view: View?, position: Int) {
                    }
                })
        )
    }
    public fun redirectHome(view: View){

        val intent = Intent(this, HomeScreenActivity::class.java)
        startActivity(intent)
    }
}