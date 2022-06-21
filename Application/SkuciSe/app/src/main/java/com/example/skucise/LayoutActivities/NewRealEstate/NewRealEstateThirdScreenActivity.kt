package com.example.skucise.LayoutActivities.NewRealEstate

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.CheckBox
import com.example.skucise.R

class NewRealEstateThirdScreenActivity : AppCompatActivity() {

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


    val list_of_checked_values = ArrayList<String>()

    private var type_first: String = ""
    private var type_second: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_real_estate_third_screen)
        cb_wifi = findViewById(R.id.cb_wifi)
        cb_tv = findViewById(R.id.cb_tv)
        cb_terasa = findViewById(R.id.cb_terasa)
        cb_klima = findViewById(R.id.cb_klima)
        cb_kuhinja = findViewById(R.id.cb_kuhinja)
        cb_parking = findViewById(R.id.cb_parking)
        cb_kupatilo = findViewById(R.id.cb_kupatilo)
        cb_alarm = findViewById(R.id.cb_alarm)
        cb_bazen = findViewById(R.id.cb_bazen)
        cb_rostilj = findViewById(R.id.cb_rostilj)
        cb_kamin = findViewById(R.id.cb_kamin)
        cb_teretana = findViewById(R.id.cb_teretana)


        //kupim selektovanu vrednost sa proslog ekrana
        val extras = intent.extras
        if (extras != null) {
            type_first = extras.getString("type_first").toString()
            type_second = extras.getString("type_second").toString()
        }
    }
    public fun RedirectNazad(view: View){

        this.finish()
    }

    public fun RedirectDalje(view: View){

        if(cb_wifi.isChecked)
            list_of_checked_values.add("wifi")
        if(cb_tv.isChecked)
            list_of_checked_values.add("tv")
        if(cb_terasa.isChecked)
            list_of_checked_values.add("terasa")
        if(cb_klima.isChecked)
            list_of_checked_values.add("klima")
        if(cb_kuhinja.isChecked)
            list_of_checked_values.add("kuhinja")
        if(cb_parking.isChecked)
            list_of_checked_values.add("parking")
        if(cb_kupatilo.isChecked)
            list_of_checked_values.add("kupatilo")
        if(cb_alarm.isChecked)
            list_of_checked_values.add("alarm")
        if(cb_bazen.isChecked)
            list_of_checked_values.add("bazen")
        if(cb_rostilj.isChecked)
            list_of_checked_values.add("rostilj")
        if(cb_kamin.isChecked)
            list_of_checked_values.add("kamin")
        if(cb_teretana.isChecked)
            list_of_checked_values.add("teretana")

        val intent = Intent(this, NewRealEstateForthScreenActivity::class.java)
        intent.putExtra("type_first", type_first)
        intent.putExtra("type_second", type_second)

        if(list_of_checked_values.isNotEmpty()){
            intent.putStringArrayListExtra("dodatni_sadrzaji",list_of_checked_values)
        }

        startActivity(intent)
    }

}