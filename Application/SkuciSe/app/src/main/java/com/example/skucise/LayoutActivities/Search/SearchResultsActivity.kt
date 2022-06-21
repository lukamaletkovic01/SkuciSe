package com.example.skucise.LayoutActivities.Search

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.SearchView
import android.widget.TextView
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.skucise.LayoutActivities.BasicActivities.RecyclerItemClickListener
import com.example.skucise.LayoutActivities.ProductPageActivies.ProductPageActivity
import com.example.skucise.R
import com.example.skucise.adapter.SearchViewAdapter
import com.example.skucise.models.Advert
import com.example.skucise.viewModels.adverts.AdvertViewModel

class SearchResultsActivity : AppCompatActivity() {

    private lateinit var searchView: SearchView
    private lateinit var advertViewModel: AdvertViewModel
    private lateinit var recyclerViewAdvert: RecyclerView
    private lateinit var dataholderAdverts: ArrayList<Advert>
    private lateinit var numberOfAdverts: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search_results)

        val extras = intent.extras

        if (extras != null) {
            dataholderAdverts = ArrayList();
            val args = intent.getBundleExtra("BUNDLE")
            dataholderAdverts = args!!.getSerializable("ARRAYLIST") as ArrayList<Advert>
            Log.d("filters", "Primenaaaa filtera je uspela " + dataholderAdverts.count().toString())


//            if (extras.containsKey("dodatni_sadrzaji"))
//                for(i in extras.getSerializable("adverts")!!)
//                {
//                    list_of_checked_values.add(i)
//                }

            recyclerViewAdvert = findViewById(R.id.recyclerViewSearchResults)
            recyclerViewAdvert.layoutManager = LinearLayoutManager(this)
            recyclerViewAdvert.isNestedScrollingEnabled = false;
            recyclerViewAdvert.adapter = SearchViewAdapter(dataholderAdverts)
        }


    }

}