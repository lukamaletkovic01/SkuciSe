package com.example.skucise.LayoutActivities.NewRealEstate

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import com.example.skucise.R
import com.google.android.material.textfield.TextInputLayout

class NewRealEstateForthScreenActivity : AppCompatActivity() {
    private lateinit var cities : Array<String>
    private lateinit var cene : Array<String>

    val list_of_checked_values = ArrayList<String>()
    private var type_first: String = ""
    private var type_second: String = ""

    private lateinit var naslov: EditText
    private lateinit var adresa: EditText
    private lateinit var cena: EditText
    private lateinit var opis: EditText
    private lateinit var kucniRed: EditText
    private lateinit var kvadratura: EditText
    private lateinit var spratnost: EditText
    private lateinit var brojSpavacihSoba: EditText

    private var grad: String = ""
    private var valuta: String = ""

    lateinit var addressLayout : TextInputLayout
    lateinit var titleLayout : TextInputLayout
    lateinit var priceLayout : TextInputLayout
    lateinit var descLayout : TextInputLayout
    lateinit var houseOrderLayout : TextInputLayout
    lateinit var M2Layout : TextInputLayout
    lateinit var NumberOfBedroomsLayout : TextInputLayout
    lateinit var flatNumberLayout : TextInputLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_real_estate_forth_screen)
        cities = arrayOf("Ada", "Aleksandrovac", "Aleksinac", "Alibunar", "Apatin", "Aranđelovac", "Arilje", "Babušnica", "Bač", "Bačka Palanka", "Bačka Topola", "Bački Petrovac", "Bajina Bašta", "Batočina", "Bečej", "Bela Crkva", "Bela Palanka", "Beočin", "Beograd", "Blace", "Bogatić", "Bojnik", "Boljevac", "Bor", "Bosilegrad", "Brus", "Bujanovac", "Čačak", "Čajetina", "Ćićevac", "Čoka", "Crna Trava", "Ćuprija", "Despotovac", "Dimitrovgrad", "Doljevac", "Gadžin Han", "Gnjilane", "Golubac", "Gornji Milanovac", "Inđija", "Irig", "Istok", "Ivanjica", "Jagodina", "Kanjiža", "Kikinda", "Kladovo", "Knić", "Knjaževac", "Koceljeva", "Kosjerić", "Kosovo Polje", "Kosovska Kamenica", "Kosovska Mitrovica", "Kostolac", "Kovačica", "Kovin", "Kragujevac", "Kraljevo", "Krupanj", "Kruševac", "Kučevo", "Kula", "Kuršumlija", "Lajkovac", "Lapovo", "Lebane", "Leposavić", "Leskovac", "Lipljan", "Ljig", "Ljubovija", "Loznica", "Lučani", "Majdanpek", "Mali Iđoš", "Mali Zvornik", "Malo Crniće", "Medveđa", "Merošina", "Mionica", "Negotin", "Niš", "Nova Crnja", "Nova Varoš", "Novi Bečej", "Novi Kneževac", "Novi Pazar", "Novi Sad", "Obilić", "Odžaci", "Opovo", "Osečina", "Pančevo", "Paraćin", "Pećinci", "Petrovac na Mlavi", "Pirot", "Plandište", "Požarevac", "Požega", "Preševo", "Priboj", "Prijepolje", "Priština", "Prokuplje", "Rača (Kragujevačka)", "Raška", "Ražanj", "Rekovac", "Ruma", "Šabac", "Sečanj", "Senta", "Šid", "Sjenica", "Smederevo", "Smederevska Palanka", "Sokobanja", "Sombor", "Srbobran", "Sremska Mitrovica", "Sremski Karlovci", "Stara Pazova", "Štrpce", "Subotica", "Surdulica", "Svilajnac", "Svrljig", "Temerin", "Titel", "Topola", "Trgovište", "Trstenik", "Tutin", "Ub", "Užice", "Valjevo", "Varvarin", "Velika Plana", "Veliko Gradište", "Vitina", "Vladičin Han", "Vladimirci", "Vlasotince", "Vranje", "Vrbas", "Vrnjačka Banja", "Vršac", "Vučitrn", "Žabalj", "Žabari", "Žagubica", "Zaječar", "Žitište", "Žitorađa", "Zrenjanin", "Zubin Potok", "Zvečan")
        cene = arrayOf("Euro","Srpski Dinar","Svajcarki Franak")
        addSpinner()

        addressLayout = findViewById(R.id.add_new_adresaLayout)
        titleLayout =findViewById(R.id.add_new_nazivLayout)
        priceLayout =findViewById(R.id.add_new_cenaLayout)
        descLayout =findViewById(R.id.add_new_opisLayout)
        houseOrderLayout=findViewById(R.id.add_new_kucniRedLayout)
        M2Layout=findViewById(R.id.add_new_kvadraturaLayout)
        NumberOfBedroomsLayout=findViewById(R.id.add_new_brojspavacihSobaLayout)
        flatNumberLayout=findViewById(R.id.add_new_spratnostLayout)


        naslov = findViewById(R.id.add_new_naslov)
        adresa = findViewById(R.id.add_new_adresa)
        cena = findViewById(R.id.add_new_cena)
        opis = findViewById(R.id.add_new_opis)
        kucniRed = findViewById(R.id.add_new_kucniRed)


        val extras = intent.extras
        if (extras != null) {
            type_first = extras.getString("type_first").toString()
            type_second = extras.getString("type_second").toString()
            if (extras.containsKey("dodatni_sadrzaji"))
                for(i in extras.getStringArrayList("dodatni_sadrzaji")!!)
                {
                    list_of_checked_values.add(i)
                }

            if(type_second == "3" || type_second == "4" || type_second == "5")
            {
                M2Layout.visibility = View.GONE
                NumberOfBedroomsLayout. visibility = View.GONE
                flatNumberLayout.visibility = View.GONE
            }
            else
            {
                kvadratura = findViewById(R.id.add_new_kvadratura)
                spratnost = findViewById(R.id.add_new_spratnost)
                brojSpavacihSoba = findViewById(R.id.add_new_brojSpavacihSoba)
            }
        }
    }

    public fun RedirectNazad(view: View){

        this.finish()
    }

    public fun RedirectDalje(view: View){

        var checkAdress = checkAdress()
        var checkTitle = checkTitle()
        var checkPrice = checkPrice()
        var checkDesc = checkDesc()
        var checkHouseOrder = checkHouseOrder()
        var checkFlatNumber = checkFlatNumber()
        var checkbadroomnumber = checkbadroomnumber()
        var checkM2 = checkM2()

        if(checkAdress && checkTitle && checkPrice && checkDesc && checkHouseOrder) {

            var spratnostText = "0"
            var kvadraturaText = "0"
            var brojSpavacihText = "0"

            if (type_second == "1" || type_second == "2") {
                if(checkFlatNumber && checkbadroomnumber && checkM2) {
                    spratnostText = spratnost.text.toString()
                    kvadraturaText = kvadratura.text.toString()
                    brojSpavacihText = brojSpavacihSoba.text.toString()

                    val intent = Intent(this, NewRealEstateGoogleMapsActivity::class.java)
                    intent.putExtra("type_first", type_first)
                    intent.putExtra("type_second", type_second)
                    intent.putExtra("naslov", naslov.text.toString())
                    intent.putExtra("adresa", adresa.text.toString())
                    intent.putExtra("cena", cena.text.toString())
                    intent.putExtra("opis", opis.text.toString())
                    intent.putExtra("kucniRed", kucniRed.text.toString())
                    intent.putExtra("valuta", "Evro")
                    intent.putExtra("grad", grad)
                    intent.putExtra("spratnost",spratnostText)
                    intent.putExtra("kvadratura",kvadraturaText)
                    intent.putExtra("brojspavacih",brojSpavacihText)

                    intent.putStringArrayListExtra("dodatni_sadrzaji", list_of_checked_values)

                    startActivity(intent)
                }
            }
            else
            {
                val intent = Intent(this, NewRealEstateGoogleMapsActivity::class.java)
                intent.putExtra("type_first", type_first)
                intent.putExtra("type_second", type_second)
                intent.putExtra("naslov", naslov.text.toString())
                intent.putExtra("adresa", adresa.text.toString())
                intent.putExtra("cena", cena.text.toString())
                intent.putExtra("opis", opis.text.toString())
                intent.putExtra("kucniRed", kucniRed.text.toString())
                intent.putExtra("valuta", "Evro")
                intent.putExtra("grad", grad)
                intent.putExtra("spratnost",spratnostText)
                intent.putExtra("kvadratura",kvadraturaText)
                intent.putExtra("brojspavacih",brojSpavacihText)

                intent.putStringArrayListExtra("dodatni_sadrzaji", list_of_checked_values)

                startActivity(intent)
            }
        }
    }

    private fun addSpinner()
    {
        //val types = arrayOf("Izdavanje", "Prodaja")
        val spinner = findViewById<Spinner>(R.id.spinnerGradovi)
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


    public fun checkAdress() : Boolean{
        var address: String = findViewById<EditText>(R.id.add_new_adresa).text.toString()

        if (address.isNullOrEmpty()){
            addressLayout.setError("Polje ne sme biti prazno")
            return false
        }
        else {
            addressLayout.setError(null)
            return true
        }
    }

    public fun checkTitle() : Boolean{
        var tittle: String = findViewById<EditText>(R.id.add_new_naslov).text.toString()

        if (tittle.isNullOrEmpty()){
            titleLayout.setError("Polje ne sme biti prazno")
            return false
        }
        else {
            titleLayout.setError(null)
            return true
        }
    }

    public fun checkPrice() : Boolean{
        var price: String = findViewById<EditText>(R.id.add_new_cena).text.toString()

        if (price.isNullOrEmpty()){
            priceLayout.setError("Polje ne sme biti prazno")
            return false
        }
        else {
            priceLayout.setError(null)
            return true
        }
    }

    public fun checkDesc() : Boolean{
        var desc: String = findViewById<EditText>(R.id.add_new_opis).text.toString()

        if (desc.isNullOrEmpty()){
            descLayout.setError("Polje ne sme biti prazno")
            return false
        }
        else {
            descLayout.setError(null)
            return true
        }
    }

    public fun checkHouseOrder() : Boolean{
        var kucniRed: String = findViewById<EditText>(R.id.add_new_kucniRed).text.toString()

        if (kucniRed.isNullOrEmpty()){
            houseOrderLayout.setError("Polje ne sme biti prazno")
            return false
        }
        else {
            houseOrderLayout.setError(null)
            return true
        }
    }

    public fun checkM2() : Boolean{
        var kvadratura: String = findViewById<EditText>(R.id.add_new_kvadratura).text.toString()

        if (kvadratura.isNullOrEmpty()){
            M2Layout.setError("Polje ne sme biti prazno")
            return false
        }
        else {
            M2Layout.setError(null)
            return true
        }
    }

    public fun checkFlatNumber() : Boolean{
        var spratnost: String = findViewById<EditText>(R.id.add_new_spratnost).text.toString()

        if (spratnost.isNullOrEmpty()){
            flatNumberLayout.setError("Polje ne sme biti prazno")
            return false
        }
        else {
            flatNumberLayout.setError(null)
            return true
        }
    }

    public fun checkbadroomnumber() : Boolean{
        var brojSoba: String = findViewById<EditText>(R.id.add_new_brojSpavacihSoba).text.toString()

        if (brojSoba.isNullOrEmpty()){
            NumberOfBedroomsLayout.setError("Polje ne sme biti prazno")
            return false
        }
        else {
            NumberOfBedroomsLayout.setError(null)
            return true
        }
    }
}