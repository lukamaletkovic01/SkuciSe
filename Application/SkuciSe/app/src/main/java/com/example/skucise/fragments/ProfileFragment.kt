package com.example.skucise.fragments

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.skucise.LayoutActivities.LoginActivities.SessionManager
import com.example.skucise.LayoutActivities.LoginActivities.SessionManager.Companion.KEY_ID
import com.example.skucise.LayoutActivities.MyProducts.MyProductsDrawerActivity
import com.example.skucise.LayoutActivities.ProfilePageActivities.EditProfileActivity
import com.example.skucise.R
import com.example.skucise.adapter.MyPropertyAdapter
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

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class ProfileFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    private lateinit var recyclerViewOglas : RecyclerView
    private lateinit var dataholderOglas : ArrayList<Advert>
    private lateinit var settingsButton: ImageView
    private lateinit var advertViewModel: AdvertViewModel
    private lateinit var userViewModel : UserViewModel
    private lateinit var showMoreButton: TextView
    private lateinit var noAdvertsText: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }

        val userRepository = UserRepository()
        val userViewModelFactory = UserViewModelFactory(userRepository)

        userViewModel = ViewModelProvider(this, userViewModelFactory).get(UserViewModel::class.java)

        val advertRepository = AdvertRepository()
        val advertViewModelFactory = AdvertViewModelFactory(advertRepository)

        advertViewModel =
            ViewModelProvider(this, advertViewModelFactory).get(AdvertViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view: View = inflater.inflate(R.layout.fragment_profile, container, false)

        noAdvertsText = view.findViewById(R.id.nemaOglasaTekst)
        showMoreButton = view.findViewById(R.id.mojiShowmore)
        showMoreButton.setOnClickListener(View.OnClickListener {
            activity?.let{
                val intent = Intent (it, MyProductsDrawerActivity::class.java)
                it.startActivity(intent)
            }
        })

        settingsButton = view.findViewById(R.id.settings)
        settingsButton.setOnClickListener(View.OnClickListener {
            activity?.let{
                val intent = Intent (it, EditProfileActivity::class.java)
                it.startActivity(intent)
            }
        })
        var session : SessionManager = SessionManager(view.context)
        var hash : HashMap<String, String> = session.getUserDetails()
        var id = hash.get(KEY_ID)
        userViewModel.getUserInfo(id!!.toLong())

        userViewModel.user.observe(viewLifecycleOwner, Observer { response ->
            if (response.isSuccessful){

                advertViewModel.getAdvertsByOwnerId(id!!.toLong())

                recyclerViewOglas = view.findViewById(R.id.recyclerViewMojiOglasi)
                recyclerViewOglas.layoutManager = LinearLayoutManager(context)
                recyclerViewOglas.isNestedScrollingEnabled = false;
                var itemDecoration = SpacingItemDecoration(20)
                recyclerViewOglas.addItemDecoration(itemDecoration)
                dataholderOglas = java.util.ArrayList()

                advertViewModel.adverts.observe(viewLifecycleOwner) { response1 ->
                    if (response1.isSuccessful) {
                        dataholderOglas = response1.body() as ArrayList<Advert>

                        var numRealties = dataholderOglas.count().toString()
                        if (numRealties.toInt() > 3)
                            recyclerViewOglas.adapter = MyPropertyAdapter(ArrayList(dataholderOglas.subList(0, 3)))
                        else
                            recyclerViewOglas.adapter = MyPropertyAdapter(ArrayList(dataholderOglas.subList(0, numRealties.toInt())))
                        view.findViewById<TextView>(R.id.brojNekretnina).text =
                            numRealties.toString()
                    if(dataholderOglas.count() == 0)
                        noAdvertsText.visibility = View.VISIBLE

                    } else {

                        Log.d("ResponseError", response.errorBody().toString())
                    }
                }
                var user = response.body() as User
                var name =  response.body()?.firstname + " " + response.body()?.lastname
                var phoneNumber = response.body()?.phoneNumber
                var address = response.body()?.address
                var city = response.body()?.city
                var photoUrl = response.body()?.profileImage
                var rating = user.userAverageRating
                var numComments = response.body()?.ratingsCount


                view.findViewById<TextView>(R.id.name).text = name
                view.findViewById<TextView>(R.id.brojTelefona).text = "Broj telefona: " + phoneNumber
                view.findViewById<TextView>(R.id.adresaKorisnika).text = "Adresa: " + address
                view.findViewById<TextView>(R.id.gradKorisnika).text = "Grad: " + city

                view.findViewById<TextView>(R.id.brojRecenzija).text = numComments.toString()
                view.findViewById<TextView>(R.id.prosecnaOcena).text = rating.toString()

                var profile_pic : ImageView = view.findViewById<ImageView>(R.id.slikaProfila)
                Picasso.get().load(Constrants.BASE_URL + "/Images/UserImages/$photoUrl").into(profile_pic)

                Log.d("USER", response.body().toString())
            }
            else{
                Log.d("GRESKA", "Nije uspelo")
            }

        })



        // Inflate the layout for this fragment
        return view
    }


}