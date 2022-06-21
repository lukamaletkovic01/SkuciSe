package com.example.skucise.LayoutActivities.CommentActivities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.example.skucise.Animations.SwipeToDeleteCallback
import com.example.skucise.LayoutActivities.LoginActivities.SessionManager
import com.example.skucise.R
import com.example.skucise.adapter.CommentsAdapter
import com.example.skucise.adapter.CommentsFragmentsAdapter
import com.example.skucise.adapter.FavScreenAdapter
import com.example.skucise.adapter.MyPropertyAdapter
import com.example.skucise.models.Advert
import com.example.skucise.models.Comment
import com.example.skucise.repository.AdvertRepository
import com.example.skucise.utils.SpacingItemDecoration
import com.example.skucise.viewModels.adverts.AdvertViewModel
import com.example.skucise.viewModels.adverts.AdvertViewModelFactory
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.tabs.TabLayout
import android.content.Intent




class AllCommentsActivity : AppCompatActivity() {

    private lateinit var recyclerViewComment: RecyclerView
    private lateinit var dataholderComment: ArrayList<Comment>
    private lateinit var commentsAdapter: CommentsAdapter
    private lateinit var advertViewModel: AdvertViewModel
    private lateinit var dataholderAdvert: ArrayList<Advert>
    private lateinit var mIntent : Intent



    var adapter: CommentsFragmentsAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_all_comments)
        mIntent = getIntent()


        var advertId = mIntent.getLongExtra("AdvertID" ,0)
        println("+++++++++++++" + advertId)
        getComments(advertId)



    }
    private fun getComments(advertId: Long) {
        recyclerViewComment = findViewById(R.id.viewComments)
        recyclerViewComment.layoutManager = LinearLayoutManager(this)
        recyclerViewComment.isNestedScrollingEnabled = true;

        //razdvajam iteme u recyclerView
        var itemDecoration = SpacingItemDecoration(20)
        recyclerViewComment.addItemDecoration(itemDecoration)

        dataholderComment = java.util.ArrayList()



        val advertRepository = AdvertRepository()
        val advertViewModelFactory = AdvertViewModelFactory(advertRepository)
        advertViewModel =
            ViewModelProvider(this, advertViewModelFactory).get(AdvertViewModel::class.java)

        advertViewModel.getCommentsResponse.observe(this, { response ->
            if (response.isSuccessful) {
                dataholderComment= response.body() as ArrayList<Comment>

                commentsAdapter = CommentsAdapter(dataholderComment)
                recyclerViewComment?.adapter = commentsAdapter
            } else {
                Log.d("ResponseError", response.errorBody().toString())
            }
        })


        advertViewModel.getComments(advertId)

    }
}