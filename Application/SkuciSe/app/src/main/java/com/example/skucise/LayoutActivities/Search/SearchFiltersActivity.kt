package com.example.skucise.LayoutActivities.Search

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import androidx.appcompat.widget.AppCompatButton
import androidx.lifecycle.ViewModelProvider
import com.example.skucise.LayoutActivities.BasicActivities.LoadingUtils
import com.example.skucise.LayoutActivities.EditProductActivities.EditProductSucceedActivity
import com.example.skucise.LayoutActivities.HomeScreenActivities.HomeScreenActivity
import com.example.skucise.R
import com.example.skucise.fragments.SearchFragment
import com.example.skucise.models.Advert
import com.example.skucise.models.Filters
import com.example.skucise.repository.AdvertRepository
import com.example.skucise.viewModels.adverts.AdvertViewModel
import com.example.skucise.viewModels.adverts.AdvertViewModelFactory
import com.google.android.material.slider.RangeSlider
import java.text.NumberFormat
import java.util.*

class SearchFiltersActivity : AppCompatActivity() {

    private lateinit var rangeSlider: RangeSlider
    private lateinit var rasponCena: TextView
    private lateinit var cities : Array<String>
    private lateinit var primeniFiltere: AppCompatButton
    private lateinit var dataholderAdvert: ArrayList<Advert>
    var grad: String = ""


    private lateinit var cb_wifi :CheckBox
    private lateinit var cb_tv :CheckBox
    private lateinit var cb_terasa :CheckBox
    private lateinit var cb_klima :CheckBox
    private lateinit var cb_kuhinja :CheckBox
    private lateinit var cb_parking :CheckBox
    private lateinit var cb_kupatilo :CheckBox
    private lateinit var cb_alarm :CheckBox
    private lateinit var cb_bazen :CheckBox
    private lateinit var cb_rostilj :CheckBox
    private lateinit var cb_kamin :CheckBox
    private lateinit var cb_teretana :CheckBox
    private lateinit var cb_izdavanje :CheckBox
    private lateinit var cb_prodaja :CheckBox
    private lateinit var cb_kuca :CheckBox
    private lateinit var cb_stan :CheckBox
    private lateinit var cb_garaza :CheckBox
    private lateinit var cb_parkingMesto :CheckBox
    private lateinit var cb_poslovni :CheckBox

    private lateinit var advertViewModel : AdvertViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search_filters)
        init()
        addSpinner()


        val advertRepository = AdvertRepository()
        val advertViewModelFactory = AdvertViewModelFactory(advertRepository)

        advertViewModel = ViewModelProvider(this, advertViewModelFactory).get(AdvertViewModel::class.java)

        advertViewModel.adverts.observe(this, { response ->
            if (response.isSuccessful){
                LoadingUtils.hideDialog()
                dataholderAdvert = response.body() as ArrayList<Advert>
                Log.d("filters", "Primena filtera je uspela " + dataholderAdvert.count().toString())
                val intent = Intent(this, SearchResultsActivity::class.java)
                val args = Bundle()
                args.putSerializable("ARRAYLIST", dataholderAdvert)
                intent.putExtra("BUNDLE", args)
                startActivity(intent)
            }
            else{
                Log.d("filters", "Primena filtera nije uspela")
                Toast.makeText(this, "Primena filtera nije uspela", Toast.LENGTH_SHORT).show()
            }

        })

    }

    private fun init()
    {
        cb_wifi = findViewById(R.id.cbFilter_wifi)
        cb_tv = findViewById(R.id.cbFilter_tv)
        cb_terasa = findViewById(R.id.cbFilter_terasa)
        cb_klima = findViewById(R.id.cbFilter_klima)
        cb_kuhinja = findViewById(R.id.cbFilter_kuhinja)
        cb_parking = findViewById(R.id.cbFilter_parking)
        cb_kupatilo = findViewById(R.id.cbFilter_kupatilo)
        cb_alarm = findViewById(R.id.cbFilter_alarm)
        cb_bazen = findViewById(R.id.cbFilter_bazen)
        cb_rostilj = findViewById(R.id.cbFilter_rostilj)
        cb_kamin = findViewById(R.id.cbFilter_kamin)
        cb_teretana = findViewById(R.id.cbFilter_teretana)
        cb_izdavanje = findViewById(R.id.cbFilter_izdavanje)
        cb_prodaja = findViewById(R.id.cbFilter_prodaja)
        cb_kuca = findViewById(R.id.cbFilter_kuca)
        cb_stan = findViewById(R.id.cbFilter_stan)
        cb_garaza = findViewById(R.id.cbFilter_garaza)
        cb_parkingMesto = findViewById(R.id.cbFilter_parkingLot)
        cb_poslovni = findViewById(R.id.cbFilter_poslovni)


        rangeSlider = findViewById<RangeSlider>(R.id.rangeSlider)
        rasponCena = findViewById(R.id.rasponCena)
        primeniFiltere = findViewById(R.id.primeniFiltere)

        rangeSlider.setLabelFormatter { value: Float ->
            val format = NumberFormat.getCurrencyInstance()
            format.maximumFractionDigits = 0
            format.currency = Currency.getInstance("EUR")
            format.format(value.toDouble())
        }


        rangeSlider.addOnSliderTouchListener(object : RangeSlider.OnSliderTouchListener {
            @SuppressLint("SetTextI18n")
            override fun onStartTrackingTouch(slider: RangeSlider) {
                rasponCena.text = rangeSlider.values[0].toString() + "€ - " + rangeSlider.values[1].toString() + "€"
            }

            override fun onStopTrackingTouch(slider: RangeSlider) {
                rasponCena.text = rangeSlider.values[0].toLong().toString() + "€ - " + rangeSlider.values[1].toLong().toString() + "€"
            }
        })

        primeniFiltere.setOnClickListener(View.OnClickListener {
            LoadingUtils.showDialog(this, false)
            filtersFunction()
        })

    }
    public fun redirectHome(view: View)
    {
        this.finish()
    }
    private fun filtersFunction()
    {
        var wifi :Boolean = false
        var tv :Boolean = false
        var terasa :Boolean = false
        var klima :Boolean = false
        var kuhinja :Boolean = false
        var parking :Boolean = false
        var kupatilo :Boolean = false
        var alarm :Boolean = false
        var bazen :Boolean = false
        var rostilj :Boolean = false
        var kamin :Boolean = false
        var teretana :Boolean = false
        var izdavanje :Boolean = false
        var prodaja :Boolean = false
        var kuca :Boolean = false
        var stan :Boolean = false
        var garaza :Boolean = false
        var parkingMesto :Boolean = false
        var poslovni :Boolean = false

        if(cb_wifi.isChecked)
            wifi = true
        if(cb_tv.isChecked)
            tv = true
        if(cb_terasa.isChecked)
           terasa = true
        if(cb_klima.isChecked)
            klima = true
        if(cb_kuhinja.isChecked)
            kuhinja = true
        if(cb_parking.isChecked)
            parking = true
        if(cb_kupatilo.isChecked)
            kupatilo = true
        if(cb_alarm.isChecked)
            alarm = true
        if(cb_bazen.isChecked)
            bazen = true
        if(cb_rostilj.isChecked)
            rostilj = true
        if(cb_kamin.isChecked)
            kamin = true
        if(cb_teretana.isChecked)
            teretana = true
        if(cb_izdavanje.isChecked)
            izdavanje = true
        if(cb_prodaja.isChecked)
            prodaja = true
        if(cb_kuca.isChecked)
            kuca = true
        if(cb_stan.isChecked)
            stan = true
        if(cb_garaza.isChecked)
            garaza = true
        if(cb_parkingMesto.isChecked)
            parkingMesto = true
        if(cb_poslovni.isChecked)
            poslovni = true

        var filter: Filters = Filters(grad,terasa,parking,wifi,tv,klima,kuhinja,kupatilo,alarm,bazen,rostilj,kamin,teretana,izdavanje,prodaja,kuca,stan,garaza,parkingMesto,poslovni,rangeSlider.values[0].toLong(),rangeSlider.values[1].toLong())

        advertViewModel.getAdvertsByFilter(filter)
    }

    private fun addSpinner()
    {
        cities = arrayOf("Izaberite grad","Ada", "Aleksandrovac", "Aleksinac", "Alibunar", "Apatin", "Aranđelovac", "Arilje", "Babušnica", "Bač", "Bačka Palanka", "Bačka Topola", "Bački Petrovac", "Bajina Bašta", "Batočina", "Bečej", "Bela Crkva", "Bela Palanka", "Beočin", "Beograd", "Blace", "Bogatić", "Bojnik", "Boljevac", "Bor", "Bosilegrad", "Brus", "Bujanovac", "Čačak", "Čajetina", "Ćićevac", "Čoka", "Crna Trava", "Ćuprija", "Despotovac", "Dimitrovgrad", "Doljevac", "Gadžin Han", "Gnjilane", "Golubac", "Gornji Milanovac", "Inđija", "Irig", "Istok", "Ivanjica", "Jagodina", "Kanjiža", "Kikinda", "Kladovo", "Knić", "Knjaževac", "Koceljeva", "Kosjerić", "Kosovo Polje", "Kosovska Kamenica", "Kosovska Mitrovica", "Kostolac", "Kovačica", "Kovin", "Kragujevac", "Kraljevo", "Krupanj", "Kruševac", "Kučevo", "Kula", "Kuršumlija", "Lajkovac", "Lapovo", "Lebane", "Leposavić", "Leskovac", "Lipljan", "Ljig", "Ljubovija", "Loznica", "Lučani", "Majdanpek", "Mali Iđoš", "Mali Zvornik", "Malo Crniće", "Medveđa", "Merošina", "Mionica", "Negotin", "Niš", "Nova Crnja", "Nova Varoš", "Novi Bečej", "Novi Kneževac", "Novi Pazar", "Novi Sad", "Obilić", "Odžaci", "Opovo", "Osečina", "Pančevo", "Paraćin", "Pećinci", "Petrovac na Mlavi", "Pirot", "Plandište", "Požarevac", "Požega", "Preševo", "Priboj", "Prijepolje", "Priština", "Prokuplje", "Rača (Kragujevačka)", "Raška", "Ražanj", "Rekovac", "Ruma", "Šabac", "Sečanj", "Senta", "Šid", "Sjenica", "Smederevo", "Smederevska Palanka", "Sokobanja", "Sombor", "Srbobran", "Sremska Mitrovica", "Sremski Karlovci", "Stara Pazova", "Štrpce", "Subotica", "Surdulica", "Svilajnac", "Svrljig", "Temerin", "Titel", "Topola", "Trgovište", "Trstenik", "Tutin", "Ub", "Užice", "Valjevo", "Varvarin", "Velika Plana", "Veliko Gradište", "Vitina", "Vladičin Han", "Vladimirci", "Vlasotince", "Vranje", "Vrbas", "Vrnjačka Banja", "Vršac", "Vučitrn", "Žabalj", "Žabari", "Žagubica", "Zaječar", "Žitište", "Žitorađa", "Zrenjanin", "Zubin Potok", "Zvečan")
        val spinner = findViewById<Spinner>(R.id.SpinnerGradovi)
        spinner?.adapter = applicationContext?.let { ArrayAdapter(it, android.R.layout.simple_spinner_dropdown_item, cities) } as SpinnerAdapter
        spinner?.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {
            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val type = parent?.getItemAtPosition(position).toString()
                grad = type
            }
        }
    }
}