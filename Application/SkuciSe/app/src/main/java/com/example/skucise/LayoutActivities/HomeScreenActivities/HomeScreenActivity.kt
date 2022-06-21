package com.example.skucise.LayoutActivities.HomeScreenActivities

import android.content.Intent
import android.os.Bundle
import android.provider.CalendarContract
import android.util.Log
import android.view.Gravity
import android.view.MenuItem
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.skucise.LayoutActivities.CommentActivities.CommentsActivity
import com.example.skucise.LayoutActivities.Favorites.FavoritesActivity
import com.example.skucise.LayoutActivities.LoginActivities.SessionManager
import com.example.skucise.LayoutActivities.MyProducts.MyProductsDrawerActivity
import com.example.skucise.LayoutActivities.NewRealEstate.NewRealEstateActivity
import com.example.skucise.LayoutActivities.ReservationActivities.ReservationWelcomeScreenActivity
import com.example.skucise.LayoutActivities.WelcomeScreenActivities.WelcomeScreenActivity
import com.example.skucise.R
import com.example.skucise.fragments.*
import com.example.skucise.repository.UserRepository
import com.example.skucise.utils.Constrants
import com.example.skucise.viewModels.UserViewModel
import com.example.skucise.viewModels.UserViewModelFactory
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationView
import com.squareup.picasso.Picasso
import android.content.ContentUris
import android.net.Uri
import com.example.skucise.LayoutActivities.SettingsActivities.SettingsActivity


class HomeScreenActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    private var homeFragment = HomeFragment()
    private var menuFragment = MenuFragment()
    private var searchFragment = SearchFragment()
    private var profileFragment = ProfileFragment()

    private lateinit var session : SessionManager
    lateinit var bottomNavigationView: BottomNavigationView
    lateinit var drawerLayout: DrawerLayout
    lateinit var navigationDrawerView: NavigationView

    private lateinit var userViewModel : UserViewModel



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home_screen)
        val userRepository = UserRepository()
        val userViewModelFactory = UserViewModelFactory(userRepository)

        userViewModel = ViewModelProvider(this, userViewModelFactory).get(UserViewModel::class.java)

        bottomNavigationView = findViewById(R.id.bottom_navigation_bar)
        navigationDrawerView = findViewById(R.id.drawer)
        drawerLayout = findViewById(R.id.drawer_layout)

        var hView : View =  navigationDrawerView.getHeaderView(0)
        var nav_user : TextView = hView.findViewById(R.id.usernameDrawer)
        var nav_img : ImageView = hView.findViewById(R.id.imageDrawer)

        val intentFragment = intent.getStringExtra("frgToLoad")


            when (intentFragment.toString()) {
                "Profile" -> {
                    replaceFragment(profileFragment)
                }
                "Search" -> {
                    replaceFragment(searchFragment)
                }

                else -> {
                    replaceFragment(homeFragment)
                }
            }

        session = SessionManager(this)
        var hash : HashMap<String, String> = session.getUserDetails()
        var id = hash.get(SessionManager.KEY_ID)
        userViewModel.getUserInfo(id!!.toLong())

        userViewModel.user.observe(this, Observer { response ->
            if (response.isSuccessful){

                var name =  response.body()?.firstname + " " + response.body()?.lastname
                var photoUrl = response.body()?.profileImage

                nav_user.setText(name);
                Picasso.get().load(Constrants.BASE_URL + "/Images/UserImages/$photoUrl").into(nav_img)

                Log.d("USER", response.body().toString())
            }
            else{
                Log.d("GRESKA", "Nije uspelo")
            }

        })

        bottomNavigationView.setOnNavigationItemSelectedListener {
            when(it.itemId)
            {
                R.id.ic_home -> replaceFragment(homeFragment)
                R.id.ic_search -> replaceFragment(searchFragment)
                R.id.ic_menu -> openMyDrawer(drawerLayout)
                R.id.ic_profile -> replaceFragment(profileFragment)
            }
            true
        }

        navigationDrawerView.setNavigationItemSelectedListener(this)
    }

    public fun openMyDrawer(mDrawerLayout :DrawerLayout)
    {
        mDrawerLayout.openDrawer(Gravity.RIGHT);
    }

    private fun replaceFragment(fragment :Fragment)
    {
        if(fragment != null)
        {
            val transaction = supportFragmentManager.beginTransaction()
            transaction.replace(R.id.fragment_container,fragment)
            transaction.commit()
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when(item.itemId)
        {
            R.id.oglasiNekretninu -> {
                var intent: Intent = Intent(this, NewRealEstateActivity::class.java)
                startActivity(intent)
            }
            R.id.omiljeneNekretnine -> {
                var intent: Intent = Intent(this, FavoritesActivity::class.java)
                startActivity(intent)
            }
            R.id.zahteviZaGledanje ->{
                var intent: Intent = Intent(this, ReservationWelcomeScreenActivity::class.java)
                startActivity(intent)
            }
            R.id.logout ->{
                session.LogoutUser()
                var intent: Intent = Intent(this, WelcomeScreenActivity::class.java)
                startActivity(intent)
            }
            R.id.mojeNekretnine ->{
                var intent: Intent = Intent(this, MyProductsDrawerActivity::class.java)
                startActivity(intent)
            }
            R.id.napisaneRecenzije->{
                var intent: Intent = Intent(this, CommentsActivity::class.java)
                startActivity(intent)
            }
            R.id.kalendarAktivnosti-> {
                val startMillis = System.currentTimeMillis()
                val builder: Uri.Builder = CalendarContract.CONTENT_URI.buildUpon()
                builder.appendPath("time")
                ContentUris.appendId(builder, startMillis)
                intent = Intent(Intent.ACTION_VIEW).setData(builder.build())
                startActivity(intent)
            }
            R.id.podesavanja -> {
                var intent: Intent = Intent(this, SettingsActivity::class.java)
                startActivity(intent)
            }

        }
        return true
    }
}
