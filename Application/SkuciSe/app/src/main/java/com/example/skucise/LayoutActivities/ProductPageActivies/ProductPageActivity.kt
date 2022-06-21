package com.example.skucise.LayoutActivities.ProductPageActivies

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.skucise.LayoutActivities.EditProductActivities.EditProductActivity
import com.example.skucise.LayoutActivities.HomeScreenActivities.HomeScreenActivity
import com.example.skucise.R
import com.example.skucise.adapter.ProductScreenReviewAdapter
import com.example.skucise.fragments.HomeFragment
import com.example.skucise.fragments.ProductPageFragment
import com.example.skucise.models.Advert
import com.example.skucise.models.Review
import com.example.skucise.repository.AdvertRepository
import com.example.skucise.utils.Constrants
import com.example.skucise.viewModels.adverts.AdvertViewModel
import com.example.skucise.viewModels.adverts.AdvertViewModelFactory
import com.skyhope.showmoretextview.ShowMoreTextView
import com.squareup.picasso.Picasso

class ProductPageActivity : AppCompatActivity() {
    private lateinit var productPageFragment: Fragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product_page)

        if (intent.extras != null) {
            productPageFragment = ProductPageFragment.newInstance(intent.getSerializableExtra("advertId") as Long)
            val transaction = supportFragmentManager.beginTransaction()

            transaction.replace(R.id.product_fragment_container, productPageFragment)
            transaction.commit()
        }
    }


}