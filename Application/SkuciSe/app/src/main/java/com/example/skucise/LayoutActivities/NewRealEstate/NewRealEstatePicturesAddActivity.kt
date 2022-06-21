package com.example.skucise.LayoutActivities.NewRealEstate

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.skucise.LayoutActivities.LoginActivities.SessionManager
import com.example.skucise.R
import com.example.skucise.models.Advert
import com.example.skucise.models.AdvertDetails
import com.example.skucise.repository.AdvertRepository
import com.example.skucise.viewModels.adverts.AdvertViewModel
import com.example.skucise.viewModels.adverts.AdvertViewModelFactory
import kotlinx.android.synthetic.main.activity_new_real_estate_pictures_add.*
import java.io.InputStream


class NewRealEstatePicturesAddActivity : AppCompatActivity() {

    private lateinit var advertViewModel : AdvertViewModel
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
    private var latitute : Double? = 0.0
    private var longitude : Double? = 0.0
    private var spratnost: Int = 0
    private var kvadratura: Int = 0
    private var brojspavacih: Int = 0
    private lateinit var fotoaparat: ImageView
    private lateinit var samoDaVidimJelRadi :TextView
    private lateinit var textIspodAparata: TextView

    private var images : ArrayList<Uri?>? = null
    private var position = 0
    private val PICK_IMAGES_CODE = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_real_estate_pictures_add)
        fotoaparat = findViewById(com.example.skucise.R.id.fotoaparat)
        textIspodAparata = findViewById(R.id.textIspodAparata)
        fotoaparat.visibility = View.VISIBLE
        textIspodAparata.visibility = View.VISIBLE


        //OD OVE LINIJE KUPIMO SVE UNETE VREDNOSTI SA PRETHODNOG EKRANA
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
            latitute = extras.getString("latitude").toString().toDouble()
            longitude = extras.getString("longitude").toString().toDouble()
            spratnost = extras.getString("spratnost").toString().toInt()
            kvadratura = extras.getString("kvadratura").toString().toInt()
            Log.d("spratnost2", spratnost.toString() + " aaaaaaaaaaaaaaa")

            brojspavacih = extras.getString("brojspavacih").toString().toInt()

            if (extras.containsKey("dodatni_sadrzaji"))
                for(i in extras.getStringArrayList("dodatni_sadrzaji")!!)
                {
                    list_of_checked_values.add(i)
                }

            var nextBtn = findViewById<AppCompatButton>(R.id.nextBtn)
            var previousBtn = findViewById<AppCompatButton>(R.id.previousBtn)
            var imageSwitcher = findViewById<ImageSwitcher>(R.id.imageSwitcher)

            images = ArrayList()

            imageSwitcher.setFactory { ImageView(applicationContext) }
            imageSwitcher.setOnClickListener {
                pickImagesIntent()
            }

            nextBtn.setOnClickListener {
                if (position < images!!.size-1){
                    position++
                    imageSwitcher.setImageURI(images!![position])
                }
                else{
                    Toast.makeText(this, "Nema vise slika...", Toast.LENGTH_SHORT).show()

                }
            }
            previousBtn.setOnClickListener {
                if (position > 0){
                    position--
                    imageSwitcher.setImageURI(images!![position])
                }
                else{
                    Toast.makeText(this, "Nema vise slika...", Toast.LENGTH_SHORT).show()

                }
            }
        }

        //OD OVE LINIJE IMAMO SVE VREDNOSTI

        val advertRepository = AdvertRepository()
        val advertViewModelFactory = AdvertViewModelFactory(advertRepository)

        advertViewModel = ViewModelProvider(this, advertViewModelFactory).get(AdvertViewModel::class.java)

        advertViewModel.newAdvertResponse.observe(this, Observer { response ->
            if (response.isSuccessful){
                Log.d("NoviOglas", "Dodavanje oglasa je uspelo")
                val intent = Intent(this, AkcijaUspesnaActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                intent.putExtra("advertId", response.body())

                startActivity(intent)
            }
            else{
                Log.d("body", response.body().toString())
                Toast.makeText(this, "Dodavanje oglasa nije uspelo", Toast.LENGTH_SHORT).show()
            }

        })
    }
    private fun pickImagesIntent(){
        images!!.clear()
        val intent = Intent()
        intent.type = "image/*"
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true)
        intent.action = Intent.ACTION_GET_CONTENT
        startActivityForResult(Intent.createChooser(intent, "Izaberite slike"), PICK_IMAGES_CODE)

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        fotoaparat.visibility = View.GONE
        textIspodAparata.visibility = View.GONE
        if (requestCode == PICK_IMAGES_CODE){
            if (resultCode == Activity.RESULT_OK){

                if (data!!.clipData != null){

                    val count = data.clipData!!.itemCount
                    for (i in 0 until count){
                        val imageUri = data.clipData!!.getItemAt(i).uri
                        images!!.add(imageUri)
                    }


                    imageSwitcher.setImageURI(images!![0])
                    position = 0
                }
                else{
                    val imageUri = data.data

                    imageSwitcher.setImageURI(imageUri)
                    position = 0
                }
            }
        }
    }

    public fun RedirectNazad(view: View){

        this.finish()
    }

    public fun RedirectDalje(view: View){
        var wifi: Boolean = false
        var tv: Boolean = false
        var terasa: Boolean = false
        var klima: Boolean = false
        var kuhinja: Boolean = false
        var parking: Boolean = false
        var kupatilo: Boolean = false
        var alarm: Boolean = false
        var bazen: Boolean = false
        var rostilj: Boolean = false
        var kamin: Boolean = false
        var teretana: Boolean = false

        if (list_of_checked_values.contains("wifi"))
            wifi = true
        else if (list_of_checked_values.contains("tv"))
            tv = true
        if (list_of_checked_values.contains("terasa"))
            terasa = true
        if (list_of_checked_values.contains("klima"))
            klima = true
        if (list_of_checked_values.contains("kuhinja"))
            kuhinja = true
        if (list_of_checked_values.contains("parking"))
            parking = true
        if (list_of_checked_values.contains("kupatilo"))
            kupatilo = true
        if (list_of_checked_values.contains("alarm"))
            alarm = true
        if (list_of_checked_values.contains("bazen"))
            bazen = true
        if (list_of_checked_values.contains("rostilj"))
            rostilj = true
        if (list_of_checked_values.contains("kamin"))
            kamin = true
        if (list_of_checked_values.contains("teretana"))
            teretana = true


        var ad: AdvertDetails = AdvertDetails(
            0,
            "2021-11-11",
            adresa,
            grad,
            kvadratura,
            brojspavacih,
            terasa,
            parking,
            wifi,
            tv,
            klima,
            kuhinja,
            kupatilo,
            alarm,
            bazen,
            rostilj,
            kamin,
            teretana,
            opis,
            kucniRed,
            spratnost,
            1,
            1,
            null,
            1, null,
            1,
            null,
            latitute,
            longitude
        )

        var session : SessionManager = SessionManager(view.context)
        var hash : HashMap<String, String> = session.getUserDetails()
        var userId = hash.get(SessionManager.KEY_ID)

        if (images?.size!! > 0) {
            var advert = Advert(
                null,
                naslov,
                cena.toLong(),
                0,
                0.0,
                userId.toString().toLong(),
                null,
                null,
                ad,
                realtyType.toLong(),
                advertType.toLong(),
                null,
                null,
                listOf()
            )

            var realPaths: MutableList<InputStream> = ArrayList()
            for (uri in images!!) {

                var stream = contentResolver.openInputStream(uri!!)


                if (stream != null) {
                    realPaths.add(stream)
                }

            }
            advertViewModel.createAdvert(realPaths, advert)
        } else {
            Toast.makeText(this, "Dodajte slike", Toast.LENGTH_SHORT).show()
        }
    }
}