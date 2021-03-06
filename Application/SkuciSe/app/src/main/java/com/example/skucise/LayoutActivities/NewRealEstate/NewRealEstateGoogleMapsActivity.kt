package com.example.skucise.LayoutActivities.NewRealEstate

import android.content.Intent
import android.location.Geocoder
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import com.example.skucise.R
import com.example.skucise.databinding.ActivityGoogleMapsBinding
import com.example.skucise.models.HelperModels.AdressCoordinates
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import java.util.*
import kotlin.collections.ArrayList

class NewRealEstateGoogleMapsActivity : AppCompatActivity() , OnMapReadyCallback {

    var currentMarker: Marker? = null
    val list_of_checked_values = ArrayList<String>()
    private var advertType: String = ""
    private var realtyType: String = ""
    private var naslov: String = ""
    private var adresa: String = ""
    private var cena: String = ""
    private var opis: String = ""
    private var kucniRed: String = ""
    private var grad: String = ""
    private var valuta: String = ""
    private var spratnost: String = ""
    private var kvadratura: String = ""
    private var brojspavacih: String = ""
    private var latitude: Double = 0.0
    private var longitude: Double = 0.0

    private lateinit var mMap: GoogleMap
    private lateinit var binding: ActivityGoogleMapsBinding
    private var cities : ArrayList<AdressCoordinates> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_real_estate_google_maps)
        initCities()
        getExtras()
        initMap()
    }

    private fun initMap() {

        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map1) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }
    public fun RedirectNazad(view: View){

        this.finish()
    }
    private fun getExtras() {
        val extras = intent.extras
        if (extras != null) {
            advertType = extras.getString("type_first").toString()
            realtyType = extras.getString("type_second").toString()
            naslov = extras.getString("naslov").toString()
            adresa = extras.getString("adresa").toString()
            cena = extras.getString("cena").toString()
            opis = extras.getString("opis").toString()
            kucniRed = extras.getString("kucniRed").toString()
            grad = extras.getString("grad").toString()
            valuta = extras.getString("valuta").toString()
            spratnost = extras.getString("spratnost").toString()
            Log.d("spratnost1", spratnost.toString() + " aaaaaaaaaaaaaaa")

            kvadratura = extras.getString("kvadratura").toString()
            brojspavacih = extras.getString("brojspavacih").toString()
            if (extras.containsKey("dodatni_sadrzaji"))
                for (i in extras.getStringArrayList("dodatni_sadrzaji")!!) {
                    list_of_checked_values.add(i)
                }
        }
    }

    override fun onMapReady(p0: GoogleMap) {
        mMap = p0
        var city = LatLng(cities[0].lat,cities[0].lng) //default je beograd
        for((i, i_city) in cities.withIndex()) {
            if (i_city.city == grad)
                city = LatLng(cities[i].lat, cities[i].lng)
        }
        drawMarker(city)

        mMap.setOnMarkerDragListener(object:GoogleMap.OnMarkerDragListener
        {
            override fun onMarkerDrag(p0: Marker) {

            }

            override fun onMarkerDragEnd(p0: Marker) {
                if(currentMarker != null)
                    currentMarker?.remove()

                val newLatLng = p0?.position?.let { LatLng(it.latitude, p0?.position!!.longitude) }
                if (newLatLng != null) {
                    latitude = newLatLng.latitude
                    longitude = newLatLng.longitude
                    drawMarker(newLatLng)

                }
            }

            override fun onMarkerDragStart(p0: Marker) {

            }

        })
    }

    private fun drawMarker(latlong: LatLng)
    {
        val markerOption = MarkerOptions().position(latlong).title("Ovde sam").snippet(getTheAddress(latlong.latitude,latlong.longitude)).draggable(true)

        mMap.animateCamera(CameraUpdateFactory.newLatLng(latlong))
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latlong,15f))
        currentMarker = mMap.addMarker(markerOption)
        currentMarker?.showInfoWindow()

    }

    private fun getTheAddress(latitude: Double, longitude: Double): String? {
        val geoCoder = Geocoder(this, Locale.getDefault())
        val addresses = geoCoder.getFromLocation(latitude,longitude,1)
        return addresses[0].getAddressLine(0).toString()
    }

    public fun moveNext(view: View)
    {

        val intent = Intent(this, NewRealEstatePicturesAddActivity::class.java)
        intent.putExtra("type_first", advertType)
        intent.putExtra("type_second", realtyType)
        intent.putExtra("naslov", naslov)
        intent.putExtra("adresa", adresa)
        intent.putExtra("cena", cena)
        intent.putExtra("opis", opis)
        intent.putExtra("kucniRed", kucniRed)
        intent.putExtra("valuta", valuta)
        intent.putExtra("grad", grad)
        intent.putStringArrayListExtra("dodatni_sadrzaji", list_of_checked_values)
        intent.putExtra("latitude",latitude.toString())
        intent.putExtra("longitude",longitude.toString())
        intent.putExtra("spratnost",spratnost)
        intent.putExtra("kvadratura",kvadratura)
        intent.putExtra("brojspavacih",brojspavacih)

        startActivity(intent)

    }


    private fun initCities() {
        cities.add(AdressCoordinates("Beograd",	44.8167	,20.4667))
        cities.add(AdressCoordinates("Novi Sad",	45.2644	,19.8317))
        cities.add(AdressCoordinates("Ni??",	43.3192,	21.8961))
        cities.add(AdressCoordinates("Zemun"	,44.8500	,20.4000))
        cities.add(AdressCoordinates("Kragujevac",	44.0142,	20.9394))
        cities.add(AdressCoordinates("Subotica"	,46.0983	,19.6700))
        cities.add(AdressCoordinates("Valjevo",	44.2667	,19.8833))
        cities.add(AdressCoordinates("Loznica"	,44.5333,	19.2258))
        cities.add(AdressCoordinates("Zrenjanin",	45.3778	,20.3861))
        cities.add(AdressCoordinates("Pan??evo",	44.8739	,20.6519))
        cities.add(AdressCoordinates("??a??ak"	,43.8914	,20.3497))
        cities.add(AdressCoordinates("Kraljevo",	43.7234,	20.6870))
        cities.add(AdressCoordinates("Novi Pazar",	43.1500	,20.5167))
        cities.add(AdressCoordinates("Leskovac"	,42.9981,	21.9461))
        cities.add(AdressCoordinates("U??ice"	,43.8500	,19.8500))
        cities.add(AdressCoordinates("Kru??evac"	,43.5833,	21.3267))
        cities.add(AdressCoordinates("Vranje",	42.5542,	21.8972))
        cities.add(AdressCoordinates("Sombor",	45.7800,	19.1200))
        cities.add(AdressCoordinates("Batajnica",	44.9022,	20.2814))
        cities.add(AdressCoordinates("Kikinda",	45.8244,	20.4592))
        cities.add(AdressCoordinates("Prijepolje",	43.5439,	19.6514))
        cities.add(AdressCoordinates("Vr??ac",	45.1206,	21.2986))
        cities.add(AdressCoordinates("Bor"	,44.1303,	22.1036))
        cities.add(AdressCoordinates("Ba??ka Palanka",	45.2506	,19.3886))
        cities.add(AdressCoordinates("Priboj",	43.5816	,19.5273))
        cities.add(AdressCoordinates("Bajina Ba??ta",	43.9731	,19.5597))
        cities.add(AdressCoordinates("Vrbas"	,45.5697	,19.6378))
        cities.add(AdressCoordinates("??uprija"	,43.9231,	21.3686))
        cities.add(AdressCoordinates("Senta"	,45.9314	,20.0900))
        cities.add(AdressCoordinates("Trstenik"	,43.6186	,20.9972))
        cities.add(AdressCoordinates("Ba??ka Topola",	45.8089,	19.6350))
        cities.add(AdressCoordinates("Sjenica"	,43.2667	,20.0000))
        cities.add(AdressCoordinates("Bela Crkva",44.8975,	21.4172))
        cities.add(AdressCoordinates("Ada"	,45.8014,	20.1222))
        cities.add(AdressCoordinates("Vrnja??ka Banja",	43.6167,	20.9000))
        cities.add(AdressCoordinates("Kanji??a"	,46.0667,	20.0500))
        cities.add(AdressCoordinates("??abalj"	,45.3667	,20.0667))
        cities.add(AdressCoordinates("Od??aci",	45.5167,	19.2667))
        cities.add(AdressCoordinates("Sremski Karlovci"	,45.2000,	19.9333))
        cities.add(AdressCoordinates("Beo??in"	,45.1922,	19.7203))
        cities.add(AdressCoordinates("Sokobanja"	,43.6394,	21.8694))
        cities.add(AdressCoordinates("Lapovo",	44.1842,	21.0973))
        cities.add(AdressCoordinates("Melenci",	45.5083,	20.3169))
        cities.add(AdressCoordinates("Ba??ki Petrovac",	45.3564,	19.5883))
        cities.add(AdressCoordinates("Bogati??",	44.8375	,19.4806))
        cities.add(AdressCoordinates("Horgo??"	,46.1556	,19.9725))
        cities.add(AdressCoordinates("Ba??"	,45.3886	,19.2353))
        cities.add(AdressCoordinates("Banatski Karlovac",	45.0472,	21.0161))
        cities.add(AdressCoordinates("Debelja??a"	,45.0711,	20.6000))
        cities.add(AdressCoordinates("Stara Moravica"	,45.8689,	19.4661))
        cities.add(AdressCoordinates("Irig"	,45.1011	,19.8583))
        cities.add(AdressCoordinates("??oka",	45.9389,	20.1394))
        cities.add(AdressCoordinates("Elemir"	,45.4425,	20.3000))
        cities.add(AdressCoordinates("E??ka"	,45.3178,	20.4389))
        cities.add(AdressCoordinates("Markovac"	,44.2333	,21.1000))
        cities.add(AdressCoordinates("Novi Slankamen"	,45.1253,	20.2394))
        cities.add(AdressCoordinates("Vladimirovac",	45.0331,	20.8639))
        cities.add(AdressCoordinates("Perlez"	,45.2047,20.3756))
        cities.add(AdressCoordinates("Kamenica"	,43.3758,21.9419))
        cities.add(AdressCoordinates("Ba??aid"	,45.6411,20.4142))
        cities.add(AdressCoordinates("Aradac"	,45.3764,20.3008))
        cities.add(AdressCoordinates("Alibunar"	,45.0808,20.9658))
        cities.add(AdressCoordinates("Vreoci"	,44.4387,20.2757))
        cities.add(AdressCoordinates("??enta"	,45.1050,20.3861))
        cities.add(AdressCoordinates("Klek"	,45.4197	,20.4747))
        cities.add(AdressCoordinates("Deliblato"	,44.8372,	21.0383))
        cities.add(AdressCoordinates("Padej",	45.8289,	20.1661))
        cities.add(AdressCoordinates("Lazarevo"	,45.3864,	20.5367))
        cities.add(AdressCoordinates("Novi ??ednik"	,45.9333,	19.6667))
        cities.add(AdressCoordinates("Crna Trava"	,42.8101,	22.2990))
        cities.add(AdressCoordinates("Jagodina",	43.9750,	21.2564))
        cities.add(AdressCoordinates("??abac"	,44.7558,	19.6939))
        cities.add(AdressCoordinates("Gornji Milanovac",	44.0212	,20.4560))
        cities.add(AdressCoordinates("Po??ega",	43.8500	,20.0500))
        cities.add(AdressCoordinates("Krupanj",	44.3656	,19.3619))
        cities.add(AdressCoordinates("Lebane"	,42.9167,	21.7333))
        cities.add(AdressCoordinates("Vladi??in Han"	,42.7000,	22.0667))
        cities.add(AdressCoordinates("Knja??evac"	,43.5000,	22.4333))
        cities.add(AdressCoordinates("Ljubovija"	,44.1869	,19.3728))
        cities.add(AdressCoordinates("Smederevska Palanka",	44.3655	,20.9587))
        cities.add(AdressCoordinates("Mali Zvornik"	,44.3992,	19.1214))
        cities.add(AdressCoordinates("Surdulica"	,42.6950,	22.1672))
        cities.add(AdressCoordinates("Po??arevac"	,44.6200	,21.1897))
        cities.add(AdressCoordinates("Zaje??ar"	,43.9042,	22.2847))
        cities.add(AdressCoordinates("Para??in"	,43.8667,	21.4167))
        cities.add(AdressCoordinates("Lu??ani"	,43.8667	,20.1333))
        cities.add(AdressCoordinates("Smederevo",	44.6633,20.9289))
        cities.add(AdressCoordinates("Aleksinac"	,43.5383,21.7047))
        cities.add(AdressCoordinates("Despotovac"	,44.0833,21.4333))
        cities.add(AdressCoordinates("Kladovo"	,44.6039,	22.6072))
        cities.add(AdressCoordinates("Pirot"	,43.1519	,22.5850))
        cities.add(AdressCoordinates("Vlasotince",	42.9667,	22.1333))
        cities.add(AdressCoordinates("Prokuplje"	,43.2339,	21.5861))
        cities.add(AdressCoordinates("Bato??ina"	,44.1500	,21.0833))
        cities.add(AdressCoordinates("Dimitrovgrad"	,43.0167,	22.7833))
        cities.add(AdressCoordinates("Doljevac"	,43.1968,	21.8334))
        cities.add(AdressCoordinates("Velika Plana"	,44.3333,	21.0833))
        cities.add(AdressCoordinates("Svilajnac"	,44.2167,	21.2000))
        cities.add(AdressCoordinates("Svrljig"	,43.4167	,22.1167))
        cities.add(AdressCoordinates("??id"	,45.1283,	19.2264))
        cities.add(AdressCoordinates("Negotin"	,44.2167,22.5167))
        cities.add(AdressCoordinates("Bojnik"	,43.0142,21.7180))
        cities.add(AdressCoordinates("Topola"	,44.2525,20.6761))
        cities.add(AdressCoordinates("Blace"	,43.2906,21.2847))
        cities.add(AdressCoordinates("Apatin"	,45.6667,18.9833))
        cities.add(AdressCoordinates("Arilje"	,43.7519,20.0906))
        cities.add(AdressCoordinates("Kovin"	,44.7475,20.9761))
        cities.add(AdressCoordinates("Boljevac"	,43.8247,21.9519))
        cities.add(AdressCoordinates("Kosjeri??"	,44.0000,19.9167))
        cities.add(AdressCoordinates("??i??evac"	,43.7167,21.4500))
        cities.add(AdressCoordinates("Plandi??te"	,45.2269	,21.1217))
        cities.add(AdressCoordinates("Varvarin"	,43.7167,	21.3667))
        cities.add(AdressCoordinates("Ra??anj"	,43.6667,	21.5500))
        cities.add(AdressCoordinates("Mionica"	,44.2500,	20.0833))
        cities.add(AdressCoordinates("Aran??elovac"	,44.3042	,20.5561))
        cities.add(AdressCoordinates("Kula",	45.6109	,19.5274))
        cities.add(AdressCoordinates("Novi Be??ej",	45.6000,	20.1167))
        cities.add(AdressCoordinates("Be??ej"	,45.6167	,20.0333))
        cities.add(AdressCoordinates("??itora??a",	43.1833,	21.7167))
        cities.add(AdressCoordinates("Titel"	,45.2000	,20.3000))
        cities.add(AdressCoordinates("Mero??ina"	,43.2833	,21.7167))
        cities.add(AdressCoordinates("Stara Pazova",	44.9833,	20.1667))
        cities.add(AdressCoordinates("Bosilegrad"	,42.5005	,22.4728))
        cities.add(AdressCoordinates("Kni??",	43.9167,	20.7167))
        cities.add(AdressCoordinates("Se??anj"	,45.3667	,20.7725))
        cities.add(AdressCoordinates("Kova??ica"	,45.1117	,20.6214))
        cities.add(AdressCoordinates("Medve??a"	,42.8333	,21.5833))
        cities.add(AdressCoordinates("??iti??te"	,45.4850	,20.5497))
        cities.add(AdressCoordinates("??ajetina"	,43.7500	,19.7167))
        cities.add(AdressCoordinates("Sremska Mitrovica"	,44.9833,	19.6167))
        cities.add(AdressCoordinates("Ivanjica"	,43.5811,	20.2297))
        cities.add(AdressCoordinates("In??ija"	,45.0492	,20.0792))
        cities.add(AdressCoordinates("??abari"	,44.3562,	21.2143))
        cities.add(AdressCoordinates("Petrovac na Mlavi",	44.3783,	21.4194))
        cities.add(AdressCoordinates("Mali I??o??",	45.7069,	19.6644))
        cities.add(AdressCoordinates("Ub"	,44.4500,	20.0667))
        cities.add(AdressCoordinates("Ra??a"	,44.2333,	20.9833))
        cities.add(AdressCoordinates("Srbobran"	,45.5522,	19.8017))
        cities.add(AdressCoordinates("Ljig"	,44.2266	,20.2394))
        cities.add(AdressCoordinates("Rekovac"	,43.8667,	21.1333))
        cities.add(AdressCoordinates("Novi Kne??evac",	46.0500,	20.1000))
        cities.add(AdressCoordinates("Lajkovac"	,44.3667,	20.1667))
        cities.add(AdressCoordinates("Aleksandrovac",	43.4553,	21.0514))
        cities.add(AdressCoordinates("Malo Crni??e"	,44.5667,	21.2833))
        cities.add(AdressCoordinates("Kur??umlija"	,43.1408	,21.2678))
        cities.add(AdressCoordinates("Ra??ka"	,43.2859,	20.6135))
        cities.add(AdressCoordinates("Golubac"	,44.6500	,21.6333))
        cities.add(AdressCoordinates("Pe??inci"	,44.9089	,19.9664))
        cities.add(AdressCoordinates("Nova Crnja"	,45.6667,20.6000))
        cities.add(AdressCoordinates("Majdanpek"	,44.4167,21.9333))
        cities.add(AdressCoordinates("Ruma",	45.0031,	19.8289))
        cities.add(AdressCoordinates("Vladimirci"	,44.6167	,19.7833))
        cities.add(AdressCoordinates("Tutin"	,42.9875,	20.3256))
        cities.add(AdressCoordinates("Babu??nica",	43.0680	,22.4115))
        cities.add(AdressCoordinates("Brus"	,43.3836,	21.0336))
        cities.add(AdressCoordinates("Ose??ina"	,44.3667,	19.6000))
        cities.add(AdressCoordinates("Veliko Gradi??te",	44.7500,	21.5167))
        cities.add(AdressCoordinates("Ku??evo",	44.4833,	21.6667))
        cities.add(AdressCoordinates("Koceljeva"	,44.4708	,19.8070))
        cities.add(AdressCoordinates("Temerin",	45.4053	,19.8869))
        cities.add(AdressCoordinates("Gad??in Han"	,43.2203	,22.0258))
        cities.add(AdressCoordinates("Opovo"	,45.0522,	20.4303))
        cities.add(AdressCoordinates("??agubica"	,44.1979,	21.7902))
        cities.add(AdressCoordinates("Trgovi??te"	,42.3514,	22.0921))
        cities.add(AdressCoordinates("Nova Varo??"	,43.4667,	19.8203))
        cities.add(AdressCoordinates("Bela Palanka"	,43.2178,	22.3067))
        cities.add(AdressCoordinates("Pre??evo"	,42.3067	,21.6500))
        cities.add(AdressCoordinates("Bujanovac"	,42.4667	,21.7667))
        cities.add(AdressCoordinates("Mladenovac"	,44.4418	,20.6970))
        cities.add(AdressCoordinates("Petrovaradin"	,45.2500	,19.8667))
        cities.add(AdressCoordinates("Obrenovac"	,44.6539	,20.2000))
        cities.add(AdressCoordinates("Grocka"	,44.6720,	20.7153))
        cities.add(AdressCoordinates("Sur??in"	,44.7944,	20.2781))
        cities.add(AdressCoordinates("Barajevo"	,44.5790,	20.4179))
        cities.add(AdressCoordinates("Ni??ka Banja",	43.2943,	22.0099))
        cities.add(AdressCoordinates("Lazarevac"	,44.3667,	20.2500))
        cities.add(AdressCoordinates("Sopot"	,44.5193,	20.5758))
        cities.add(AdressCoordinates("Kostolac"	,44.7167,	21.1667))
    }
}