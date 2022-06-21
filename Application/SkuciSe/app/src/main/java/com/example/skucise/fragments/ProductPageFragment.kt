package com.example.skucise.fragments

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Color
import android.location.Geocoder
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.widget.AppCompatButton
import androidx.appcompat.widget.AppCompatImageButton
import androidx.core.graphics.scale
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.denzcoskun.imageslider.ImageSlider
import com.denzcoskun.imageslider.constants.ScaleTypes
import com.denzcoskun.imageslider.models.SlideModel
import com.example.skucise.LayoutActivities.CommentActivities.AllCommentsActivity
import com.example.skucise.LayoutActivities.EditProductActivities.EditProductActivity
import com.example.skucise.LayoutActivities.HomeScreenActivities.HomeScreenActivity
import com.example.skucise.LayoutActivities.LoginActivities.SessionManager
import com.example.skucise.LayoutActivities.ProfilePageActivities.ToProfileFromAdvertActivity
import com.example.skucise.LayoutActivities.RatePropertyActivities.RatePropertyActivity
import com.example.skucise.LayoutActivities.ReservationActivities.ReservationActivity
import com.example.skucise.R
import com.example.skucise.adapter.CommentsAdapter
import com.example.skucise.adapter.DetailFieldsAdapter
import com.example.skucise.models.Advert
import com.example.skucise.models.AdvertDetails
import com.example.skucise.models.AdvertImage
import com.example.skucise.models.Reservation
import com.example.skucise.repository.AdvertRepository
import com.example.skucise.repository.ReservationRepository
import com.example.skucise.utils.Constrants
import com.example.skucise.viewModels.adverts.AdvertViewModel
import com.example.skucise.viewModels.adverts.AdvertViewModelFactory
import com.example.skucise.viewModels.reservations.ReservationViewModel
import com.example.skucise.viewModels.reservations.ReservationViewModelFactory
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.*
import com.skyhope.showmoretextview.ShowMoreTextView
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_product_page.*
import java.util.*


class ProductPageFragment : Fragment(), OnMapReadyCallback {

    private lateinit var callback: OnMapReadyCallback

    private lateinit var mMap: GoogleMap
    private lateinit var advertViewModel: AdvertViewModel
    private lateinit var reservationViewModel: ReservationViewModel
    private lateinit var more : ImageButton
    private lateinit var edit : ImageButton
    private lateinit var delete : ImageButton

    private var daLiJeLajkovano: Boolean = false
    private var userId: Long? = null
    private var advertId: Long? = null

    private lateinit var recyclerViewCategory: RecyclerView
    private lateinit var recyclerViewComments: RecyclerView
    private lateinit var recyclerViewDetailFields: RecyclerView
    private lateinit var dataholderAdvert: Advert
    private lateinit var dataholderReservation: Reservation
    private lateinit var sessionManager: SessionManager

    private var showLocation = -1

    companion object {
        const val ARG_NAME = "advertId"

        fun newInstance(advertId: Long): ProductPageFragment {
            val fragment = ProductPageFragment()

            val bundle = Bundle().apply {
                putLong(ARG_NAME, advertId)
            }
            fragment.arguments = bundle

            return fragment
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        advertId = arguments?.getLong(ARG_NAME)
        return inflater.inflate(R.layout.fragment_product_page, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init(view);

        more = view.findViewById(R.id.more)
        edit = view.findViewById(R.id.edit)
        delete = view.findViewById(R.id.delete)
        edit.visibility = View.GONE
        delete.visibility = View.GONE
    }

    private fun init(view: View) {
        getUserId(view);
        getRecyclerView(view)
        toggleVisibleOrGoneKucniRed(view)

        if (advertId != null) {
            dataholderAdvert = Advert();

            setObservables(view);

            advertViewModel.getAdvertById(advertId!!);
            advertViewModel.getLikeIfExist(userId.toString().toLong(), advertId!!)
        }
    }

    private fun getUserId(view: View) {
        val session = SessionManager(view.context)
        val hash: HashMap<String, String> = session.getUserDetails()

        userId = hash[SessionManager.KEY_ID].toString().toLong()
    }

    private fun setObservables(view: View) {
        val advertRepository = AdvertRepository()
        val reservationRepository = ReservationRepository()

        val advertViewModelFactory = AdvertViewModelFactory(advertRepository)
        val reservationViewModelFactory = ReservationViewModelFactory(reservationRepository)

        advertViewModel =
            ViewModelProvider(this, advertViewModelFactory).get(AdvertViewModel::class.java)
        reservationViewModel =
            ViewModelProvider(
                this,
                reservationViewModelFactory
            ).get(ReservationViewModel::class.java)

        advertViewModel.advert.observe(viewLifecycleOwner) { response ->
            if (response.isSuccessful) {
                dataholderAdvert = response.body() as Advert
                dataholderAdvert.id?.let { reservationViewModel.isAdvertReserved(it, userId!!) }

                reservationViewModel.checkAdvertReservationStatusForMaps.observe(viewLifecycleOwner) { response ->
                    if (response.isSuccessful) {
                        var reservations = response.body() as Boolean

                        if(reservations)
                            showLocation = 1
                        else showLocation = 0

                        callback = OnMapReadyCallback { googleMap ->

                            if (dataholderAdvert.advertDetails!!.latitude == 0.0 && dataholderAdvert.advertDetails!!.longitude == 0.0) {
                                dataholderAdvert.advertDetails!!.latitude = 44.8167
                                dataholderAdvert.advertDetails!!.longitude = 20.4667
                            }


                            var city = dataholderAdvert.advertDetails!!.latitude?.let {
                                dataholderAdvert.advertDetails!!.longitude?.let { it1 ->
                                    LatLng(
                                        it,
                                        it1
                                    )
                                }
                            } // default je beograd

                            if (dataholderAdvert.userId != userId && showLocation == 0) {
                                val circleOptions = CircleOptions()
                                if (city != null) {
                                    city = LatLng(city.latitude + 0.001, city.longitude + 0.0001)
                                    circleOptions.center(city)
                                }

                                circleOptions.radius(200.0)
                                circleOptions.fillColor(0x30ff0000)
                                circleOptions.strokeWidth(0.00001f)
                                googleMap.addCircle(circleOptions)

                                lateinit var locationMarker: Marker

                                city?.let {
                                    MarkerOptions().position(it).title(dataholderAdvert.name)
                                        .snippet("Tacnu lokaciju cete dobiti nakon rezervacije.")
                                }?.let {
                                    locationMarker = googleMap.addMarker(it)!!
                                    locationMarker.showInfoWindow()
                                }
                                city?.let { CameraUpdateFactory.newLatLngZoom(it, 15.0f) }?.let {
                                    googleMap.moveCamera(
                                        it
                                    )
                                }
                            }
                            else
                            {
                                lateinit var locationMarker: Marker

                                city?.let {
                                    MarkerOptions().position(it).title(dataholderAdvert.name)
                                        .snippet(getTheAddress(city.latitude,city.longitude))
                                }?.let {
                                    locationMarker = googleMap.addMarker(it)!!
                                    locationMarker.showInfoWindow()
                                }
                                city?.let { CameraUpdateFactory.newLatLngZoom(it, 15.0f) }?.let {
                                    googleMap.moveCamera(
                                        it
                                    )
                                }
                            }
                        }

                        val mapFragment = childFragmentManager.findFragmentById(R.id.mapProduct) as SupportMapFragment?
                        mapFragment?.getMapAsync(callback)
                    } else {
                        Log.d("ResponseError", response.errorBody().toString())
                        showLocation = 0
                    }
                }

                if (dataholderAdvert.advertDetails != null) {

                    if (dataholderAdvert.userId != userId) {
                        more.visibility = View.GONE
                    } else {
                        more.visibility = View.VISIBLE
                        more.setOnClickListener(View.OnClickListener {
                            showMore()
                        })
                        view.findViewById<AppCompatButton>(R.id.dugme_zakazi).visibility = View.GONE
                        var scroll = view.findViewById<ScrollView>(R.id.scroll)
                        var parameter = scroll.getLayoutParams() as RelativeLayout.LayoutParams
                        parameter.setMargins(
                            parameter.leftMargin,
                            parameter.topMargin,
                            parameter.rightMargin,
                            0
                        ) // left, top, right, bottom

                        scroll.layoutParams = parameter

                    }
                    setView(view);

                    val checkComments = dataholderAdvert.comments
                    if (checkComments.size > 2) {
                        recyclerViewComments.adapter =
                            CommentsAdapter(dataholderAdvert.comments.subList(0, 2))
                    } else {
                        recyclerViewComments.adapter =
                            CommentsAdapter(dataholderAdvert.comments)
                    }
                    val detailFields = getDetailFields(dataholderAdvert.advertDetails!!)
                    recyclerViewDetailFields.adapter = DetailFieldsAdapter(detailFields)

                    reservationViewModel.checkIfReviewed(
                        dataholderAdvert.userId!!,
                        advertId!!,
                        userId!!
                    )
                } else {
                    Log.d("NullError", "Advert details cannot be null.")
                }
            }
        }

        advertViewModel.likes.observe(viewLifecycleOwner) { response ->
            if (response.isSuccessful) {
                daLiJeLajkovano = response.body() as Boolean
                val fav = view.findViewById<ImageView>(R.id.favorites)

                if (daLiJeLajkovano) {
                    fav.setBackgroundResource(R.drawable.ic_baseline_favorite_border_24)

                } else {
                    fav.setBackgroundResource(R.drawable.ic_baseline_favorite_24)
                }

            } else {
                Log.d("ResponseError", response.errorBody().toString())
            }
        }

        advertViewModel.createLikesResponse.observe(viewLifecycleOwner) { response ->
            if (response.isSuccessful) {
                print("Klik")
                // daLiJeLajkovano = response.body() as Boolean
                val fav = view.findViewById<ImageView>(R.id.favorites)

                if (daLiJeLajkovano) {
                    daLiJeLajkovano = false
                    fav.setBackgroundResource(R.drawable.ic_baseline_favorite_24)
                } else {
                    daLiJeLajkovano = true
                    fav.setBackgroundResource(R.drawable.ic_baseline_favorite_border_24)
                }

            } else {
                Log.d("ResponseError", response.errorBody().toString())
            }
        }

        advertViewModel.createLikesResponse.observe(viewLifecycleOwner) { response ->
            if (response.isSuccessful) {
            } else {
                Log.d("ResponseError", response.errorBody().toString())
            }
        }

        advertViewModel.getCommentsResponse.observe(viewLifecycleOwner) { response ->
            if (response.isSuccessful) {
                recyclerViewComments.adapter = CommentsAdapter(dataholderAdvert.comments)

            } else {
                Log.d("ResponseError", response.errorBody().toString())
            }
        }

        reservationViewModel.checkIfReviewedResponse.observe(viewLifecycleOwner) { response ->
            if (response.isSuccessful) {
                Log.d("checkIfReviewedResponse", response.body().toString())
                if (response.body() == null) {
                    addComment.visibility = View.GONE
                } else {
                    dataholderReservation = response.body() as Reservation
                }
            } else {
                Log.d("checkIfReviewedResponse", response.errorBody().toString())
            }
        }

        var session: SessionManager = SessionManager(view.context)
        var hash: HashMap<String, String> = session.getUserDetails()
        var userId = hash[SessionManager.KEY_ID]
        advertViewModel.getLikeIfExist(userId.toString().toLong(), advertId!!);
    }

    private fun showMore() {

        if (edit.visibility == View.GONE) {
            more.setBackgroundResource(R.drawable.ic_baseline_arrow_drop_up_24)
            edit.visibility = View.VISIBLE
            delete.visibility = View.VISIBLE
        }
        else{
            more.setBackgroundResource(R.drawable.ic_baseline_arrow_drop_down_24)
            edit.visibility = View.GONE
            delete.visibility = View.GONE
        }

    }


    private fun getScaledBitmap(
        appcontext: Context,
        icon: Int,
        width: Int,
        height: Int
    ): BitmapDescriptor? {
        val iconBitmap: Bitmap = BitmapFactory.decodeResource(appcontext.resources, icon)
        val iconBitmapResized = iconBitmap.scale(width, height)
        return BitmapDescriptorFactory.fromBitmap(iconBitmapResized)
    }

    private fun drawCircle(point: LatLng) {
        // Instantiating CircleOptions to draw a circle around the marker
    }

    private fun getTheAddress(latitude: Double, longitude: Double): String? {
        val geoCoder = Geocoder(view?.context, Locale.getDefault())
        val addresses = geoCoder.getFromLocation(latitude, longitude, 1)
        return addresses[0].getAddressLine(0).toString()
    }

    private fun getDetailFields(advertDetails: AdvertDetails): ArrayList<String> {
        val detailFields: ArrayList<String> = arrayListOf()

        detailFields.add(advertDetails.squaredArea.toString() + "m kvadratna")
        if (advertDetails.floorNumber != 0) detailFields.add("Sprat " + advertDetails.floorNumber.toString())
        if (advertDetails.bedroomNumber != 0) detailFields.add(advertDetails.bedroomNumber.toString() + " spavaćih soba")
        if (advertDetails.kitchen) detailFields.add("Kuhinja")
        if (advertDetails.bathroom) detailFields.add("Kupatilo")
        if (advertDetails.terrace) detailFields.add("Terasa")
        if (advertDetails.parking) detailFields.add("Parking")
        if (advertDetails.wifi) detailFields.add("WIFI")
        if (advertDetails.tv) detailFields.add("TV")
        if (advertDetails.airCondition) detailFields.add("Klima uređaj")
        if (advertDetails.alarm) detailFields.add("Alarm")
        if (advertDetails.pool) detailFields.add("Bazen")
        if (advertDetails.firePlace) detailFields.add("Kamin")
        if (advertDetails.gym) detailFields.add("Teretana")

        return detailFields
    }

    private fun getRecyclerView(view: View) {
        val commentsLayoutManager =
            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        val detailFieldsLayoutManager =
            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)

        recyclerViewComments = view.findViewById(R.id.recyclerViewRecenzije)
        recyclerViewDetailFields = view.findViewById(R.id.recyclerViewDetailFields)

        recyclerViewComments.layoutManager = commentsLayoutManager
        recyclerViewDetailFields.layoutManager = detailFieldsLayoutManager

        recyclerViewComments.isNestedScrollingEnabled = false;
        recyclerViewDetailFields.isNestedScrollingEnabled = false;
    }

    private fun setView(view: View) {
        val ownerName = view.findViewById<TextView>(R.id.oglasavacJe)
        val description = view.findViewById<ShowMoreTextView>(R.id.opis_oglasa)
        val homeOrder = view.findViewById<TextView>(R.id.kucniRedInvisible)
        val name = view.findViewById<TextView>(R.id.naslovProizvoda)
        val township = view.findViewById<TextView>(R.id.adresaProizvoda)
        val price = view.findViewById<TextView>(R.id.cenaProizvoda)
        val rating = view.findViewById<TextView>(R.id.ocenaProizvoda)
        val ratingsCount = view.findViewById<TextView>(R.id.ocenaKorisnika2)
        val userRatingsCount = view.findViewById<TextView>(R.id.ocenaKorisnika)
        val editButton = view.findViewById<ImageButton>(R.id.edit)
        val deleteButton = view.findViewById<ImageButton>(R.id.delete)
        val favButton = view.findViewById<ImageButton>(R.id.favorites)
        val backButton = view.findViewById<ImageButton>(R.id.back)
        val buttonReserve = view.findViewById<AppCompatButton>(R.id.dugme_zakazi)
        val profile = view.findViewById<AppCompatImageButton>(R.id.profile)
        val allCommentsButton = view.findViewById<AppCompatButton>(R.id.showAllComments)
        val profileSecond = view.findViewById<AppCompatImageButton>(R.id.profileSecond)
//        val addComment = view.findViewById<AppCompatButton>(R.id.addComment)
        val pogledajCeoProfil =
            view.findViewById<AppCompatButton>(R.id.pogledajCeoProfil)

        if (dataholderAdvert.userId != userId) {
            pogledajCeoProfil.setOnClickListener(View.OnClickListener {

                if (dataholderAdvert.userId != userId) {
                    val intent =
                        Intent(activity, ToProfileFromAdvertActivity::class.java)
                    intent.putExtra("userId", dataholderAdvert.userId.toString())
                    startActivity(intent)
                }
            })
        } else
            pogledajCeoProfil.visibility = View.GONE

        description.setShowingLine(4)
        description.addShowMoreText("Prikazi više");
        description.addShowLessText("Prikazi manje");
        description.setShowMoreColor(Color.BLUE)
        description.setShowLessTextColor(Color.BLUE)

        val imageSlider = view.findViewById<ImageSlider>(R.id.slikaProizvoda)
        val imageList = ArrayList<SlideModel>()

        for (item: AdvertImage in dataholderAdvert.advertImages!!) {
            imageList.add(
                SlideModel(
                    Constrants.BASE_URL + "/Images/AdvertImages/${item.path}",
                    ""
                )
            )
        }

        imageSlider.setImageList(imageList, ScaleTypes.FIT)
        imageSlider.stopSliding()

        if (dataholderAdvert.userId != userId)
            ("Oglasavac je " + dataholderAdvert.user!!.firstname).also {
                ownerName.text = it
            }
        else
            ("Vaša nekretnina").also { ownerName.text = it }

        Picasso.get()
            .load(Constrants.BASE_URL + "/Images/UserImages/${dataholderAdvert.user!!.profileImage}")
            .into(profile)
        Picasso.get()
            .load(Constrants.BASE_URL + "/Images/UserImages/${dataholderAdvert.user!!.profileImage}")
            .into(profileSecond)
        description.text = dataholderAdvert.advertDetails!!.description
        homeOrder.text = dataholderAdvert.advertDetails!!.houseOrder
        name.text = dataholderAdvert.name
        township.text = dataholderAdvert.advertDetails!!.township
        (dataholderAdvert.price.toString()).also { price.text = it + "\u20ac" }
        rating.text =
            if (dataholderAdvert.advertAverageRating != 0.0) dataholderAdvert.advertAverageRating.toString() else "N/A"

        if (dataholderAdvert.ratingsCount != 0) {
            (dataholderAdvert.ratingsCount.toString() + " recenzija oglasa").also {
                ratingsCount.text = it
            }
        } else {
            ratingsCount.text = "Nema recenzija oglasa"
            val showAllComments =
                view.findViewById<AppCompatButton>(R.id.showAllComments)
            showAllComments.visibility = View.GONE;
        }

        userRatingsCount.text =
            if (dataholderAdvert.user!!.ratingsCount != 0) dataholderAdvert.user!!.ratingsCount.toString() + " recenzija korisnika" else "Korisnik nije ocenjen"
        Picasso.get()
            .load(Constrants.BASE_URL + "/Images/UserImages/${dataholderAdvert.user!!.profileImage}")
            .into(profile)
        Picasso.get()
            .load(Constrants.BASE_URL + "/Images/UserImages/${dataholderAdvert.user!!.profileImage}")
            .into(profileSecond)


        if (dataholderAdvert.userId == userId) {
            deleteButton.setOnClickListener(View.OnClickListener {
                val builder = AlertDialog.Builder(activity)
                builder.setMessage("Da li ste sigurni da želite da izbrišete oglas?")
                    .setCancelable(false)
                    .setPositiveButton("Da") { dialog, id ->
                        deleteProduct()
                    }
                    .setNegativeButton("Ne") { dialog, id ->
                        // Dismiss the dialog
                        dialog.dismiss()
                    }
                val alert = builder.create()
                alert.show()
            })
            editButton.setOnClickListener(View.OnClickListener {
                editProduct()
            })


        }

        favButton.setOnClickListener(View.OnClickListener {
            toggleFavButton(view)
        })

        backButton.setOnClickListener(View.OnClickListener {
            redirectHome()
        })
        allCommentsButton.setOnClickListener(View.OnClickListener {
            toggleAllCommentsButton(advertId)
        })

        buttonReserve.setOnClickListener(View.OnClickListener {
            redirectReservation()
        })

        addComment.setOnClickListener {
            val intent = Intent(activity, RatePropertyActivity::class.java)
            intent.putExtra("reservation", dataholderReservation)

            startActivity(intent)
        }
    }

    private fun redirectHome() {
        val intent = Intent(activity, HomeScreenActivity::class.java)
        startActivity(intent)
    }

    private fun editProduct() {
        val intent = Intent(activity, EditProductActivity::class.java)
        intent.putExtra("advertId", advertId)
        startActivity(intent)
    }

    private fun deleteProduct() {
        advertId?.let { advertViewModel.deleteAdvert(it) }
        val intent = Intent(activity, HomeScreenActivity::class.java)
        //intent.putExtra("advertId", advertId)
        startActivity(intent)
    }

    private fun redirectReservation() {
        val intent = Intent(activity, ReservationActivity::class.java)
        intent.putExtra("advertId", dataholderAdvert.id)
        intent.putExtra("ownerId", dataholderAdvert.user?.id)

        startActivity(intent)
    }

    private var i: Int = 0
    private fun toggleFavButton(view: View) {

        var session: SessionManager = SessionManager(view.context)
        var hash: HashMap<String, String> = session.getUserDetails()
        var userId = hash.get(SessionManager.KEY_ID)
        advertViewModel.postLike(userId.toString().toLong(), advertId!!);
    }

    private fun toggleAllCommentsButton(advertId: Long?) {

        val intent = Intent(activity , AllCommentsActivity::class.java)
        intent.putExtra("AdvertID", advertId)
        startActivity(intent)
    }



    private fun toggleVisibleOrGoneKucniRed(view: View) {
        val textViewKucniRed: TextView = view.findViewById(R.id.kucniRed)
        val textViewKucniRedInvisible: TextView =
            view.findViewById(R.id.kucniRedInvisible)

        textViewKucniRed.setOnClickListener(View.OnClickListener {

            if (textViewKucniRedInvisible.isVisible)
                textViewKucniRedInvisible.visibleOrInvisible(false)
            else textViewKucniRedInvisible.visibleOrInvisible(true)

        })
    }

    private fun View.visibleOrInvisible(visible: Boolean) {
        visibility = if (visible) View.VISIBLE else View.GONE
    }

    private fun View.toggleVisibility() {
        visibility = if (visibility == View.VISIBLE) {
            View.INVISIBLE
        } else {
            View.VISIBLE
        }
    }

    override fun onMapReady(p0: GoogleMap) {
        TODO("Not yet implemented")
    }
}
