package com.example.skucise.LayoutActivities.EditProductActivities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.skucise.LayoutActivities.NewRealEstate.AkcijaUspesnaActivity
import com.example.skucise.LayoutActivities.NewRealEstate.NewRealEstatePicturesAddActivity
import com.example.skucise.R
import com.example.skucise.models.Advert
import com.example.skucise.repository.AdvertRepository
import com.example.skucise.viewModels.adverts.AdvertViewModel
import com.example.skucise.viewModels.adverts.AdvertViewModelFactory
import kotlinx.android.synthetic.main.activity_edit_product.*
import kotlinx.android.synthetic.main.fragment_profile.*

class EditProductActivity : AppCompatActivity() {
    private lateinit var dataholderAdvert: Advert
    private var id: Long = 0
    private lateinit var advertViewModel : AdvertViewModel
    private lateinit var cities : Array<String>
    private lateinit var cene : Array<String>
    private lateinit var title: EditText
    private lateinit var price: EditText
    private lateinit var desc: EditText
    private lateinit var adress: EditText
    private lateinit var houseOrder: EditText

    val list_of_checked_values = ArrayList<String>()
    private lateinit var advertObject: Advert

    private var grad: String = ""
    private var valuta: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        init()
        setContentView(R.layout.activity_edit_product)
        title = findViewById(R.id.edit_naslov)
        price = findViewById(R.id.edit_cena)
        desc = findViewById(R.id.edit_opis)
        houseOrder = findViewById(R.id.edit_kucniRed)
        adress = findViewById(R.id.edit_adresa)

        cities = arrayOf("Ada", "Aleksandrovac", "Aleksinac", "Alibunar", "Apatin", "Aranđelovac", "Arilje", "Babušnica", "Bač", "Bačka Palanka", "Bačka Topola", "Bački Petrovac", "Bajina Bašta", "Batočina", "Bečej", "Bela Crkva", "Bela Palanka", "Beočin", "Beograd", "Blace", "Bogatić", "Bojnik", "Boljevac", "Bor", "Bosilegrad", "Brus", "Bujanovac", "Čačak", "Čajetina", "Ćićevac", "Čoka", "Crna Trava", "Ćuprija", "Despotovac", "Dimitrovgrad", "Doljevac", "Gadžin Han", "Gnjilane", "Golubac", "Gornji Milanovac", "Inđija", "Irig", "Istok", "Ivanjica", "Jagodina", "Kanjiža", "Kikinda", "Kladovo", "Knić", "Knjaževac", "Koceljeva", "Kosjerić", "Kosovo Polje", "Kosovska Kamenica", "Kosovska Mitrovica", "Kostolac", "Kovačica", "Kovin", "Kragujevac", "Kraljevo", "Krupanj", "Kruševac", "Kučevo", "Kula", "Kuršumlija", "Lajkovac", "Lapovo", "Lebane", "Leposavić", "Leskovac", "Lipljan", "Ljig", "Ljubovija", "Loznica", "Lučani", "Majdanpek", "Mali Iđoš", "Mali Zvornik", "Malo Crniće", "Medveđa", "Merošina", "Mionica", "Negotin", "Niš", "Nova Crnja", "Nova Varoš", "Novi Bečej", "Novi Kneževac", "Novi Pazar", "Novi Sad", "Obilić", "Odžaci", "Opovo", "Osečina", "Pančevo", "Paraćin", "Pećinci", "Petrovac na Mlavi", "Pirot", "Plandište", "Požarevac", "Požega", "Preševo", "Priboj", "Prijepolje", "Priština", "Prokuplje", "Rača (Kragujevačka)", "Raška", "Ražanj", "Rekovac", "Ruma", "Šabac", "Sečanj", "Senta", "Šid", "Sjenica", "Smederevo", "Smederevska Palanka", "Sokobanja", "Sombor", "Srbobran", "Sremska Mitrovica", "Sremski Karlovci", "Stara Pazova", "Štrpce", "Subotica", "Surdulica", "Svilajnac", "Svrljig", "Temerin", "Titel", "Topola", "Trgovište", "Trstenik", "Tutin", "Ub", "Užice", "Valjevo", "Varvarin", "Velika Plana", "Veliko Gradište", "Vitina", "Vladičin Han", "Vladimirci", "Vlasotince", "Vranje", "Vrbas", "Vrnjačka Banja", "Vršac", "Vučitrn", "Žabalj", "Žabari", "Žagubica", "Zaječar", "Žitište", "Žitorađa", "Zrenjanin", "Zubin Potok", "Zvečan")
        cene = arrayOf("Euro","Srpski Dinar","Svajcarki Franak")
        addSpinner()

        val advertRepository = AdvertRepository()
        val advertViewModelFactory = AdvertViewModelFactory(advertRepository)

        advertViewModel = ViewModelProvider(this, advertViewModelFactory).get(AdvertViewModel::class.java)

        advertViewModel.updateAdvertResponse.observe(this) { response ->
            if (response.isSuccessful) {
                Log.d("UpdateOglas", "Izmena oglasa je uspela")
                val intent = Intent(this, EditProductSucceedActivity::class.java)
                intent.putExtra("advertId", dataholderAdvert.id)
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                startActivity(intent)

                startActivity(intent)
            } else {
                Log.d("UpdateOglas", "Izmena oglasa nije uspela")
                Toast.makeText(this, "Izmena oglasa nije uspela", Toast.LENGTH_SHORT).show()
            }

        }


    }

    private fun init() {
        val extras = intent.extras;

        if (extras != null) {
            dataholderAdvert = Advert();
            id = intent.getSerializableExtra("advertId") as Long
            val advertRepository = AdvertRepository()
            val advertViewModelFactory = AdvertViewModelFactory(advertRepository)
            advertViewModel =
                ViewModelProvider(this, advertViewModelFactory).get(AdvertViewModel::class.java)

            advertViewModel.advert.observe(this) { response ->
                if (response.isSuccessful) {
                    dataholderAdvert = response.body() as Advert

                    title.setText(dataholderAdvert.name)
                    price.setText(dataholderAdvert.price.toString())
                    desc.setText(dataholderAdvert.advertDetails!!.description)
                    houseOrder.setText(dataholderAdvert.advertDetails!!.houseOrder)
                    adress.setText(dataholderAdvert.advertDetails!!.township)
                    setSpinnerValue(dataholderAdvert.advertDetails!!.city)

//                    var j: Int = 0
//                    for (city in cities) {
//                        if (city == dataholderAdvert.advertDetails!!.township) {
//                            findViewById<Spinner>(R.id.editSpinnerGradovi).setSelection(j)
//                        }
//                        j += 1
//                    }

                } else {
                    Log.d("ResponseError", response.errorBody().toString())
                }
            }

            advertViewModel.getAdvertById(id);
        }
    }

    private fun setSpinnerValue(city: String?) {
        val spinner = findViewById<Spinner>(R.id.editSpinnerGradovi)
        for (i in 0 until spinner.count) {
            if (spinner.getItemAtPosition(i).equals(city)) {
                spinner.setSelection(i)
                break
            }
        }
    }

    public fun idiNazad(view: View){
        finish()
    }

    public fun idiNapred(view: View)
    {
        dataholderAdvert.name = title.text.toString()
        dataholderAdvert.price = price.text.toString().toLong()
        dataholderAdvert.advertDetails!!.city = grad
        dataholderAdvert.advertDetails!!.township = adress.text.toString()
        dataholderAdvert.advertDetails!!.description = desc.text.toString()

        advertViewModel.updateAdvert(dataholderAdvert)
    }

    private fun addSpinner()
    {
        //val types = arrayOf("Izdavanje", "Prodaja")
        val spinner = findViewById<Spinner>(R.id.editSpinnerGradovi)
        spinner?.adapter = applicationContext?.let { ArrayAdapter(it, android.R.layout.simple_spinner_dropdown_item, cities) } as SpinnerAdapter
        spinner?.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {
            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val type = parent?.getItemAtPosition(position).toString()
                grad = type
                //Toast.makeText(this@RegistrationSecondActivity, userAddress, Toast.LENGTH_LONG).show()
            }
        }
    }

}