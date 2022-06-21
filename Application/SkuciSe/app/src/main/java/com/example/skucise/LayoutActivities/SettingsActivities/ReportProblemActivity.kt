package com.example.skucise.LayoutActivities.SettingsActivities

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import com.example.skucise.LayoutActivities.LoginActivities.SessionManager
import com.example.skucise.R

class ReportProblemActivity : AppCompatActivity() {

    private lateinit var opis: EditText

    private var naslov: String = ""
    private var email: String = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_report_problem)
        init()
    }

    private fun init()
    {
        opis = findViewById(R.id.opisProblema)
        addSpinner()

        var session = SessionManager(this)
        var hash : HashMap<String, String> = session.getUserDetails()

        email = hash[SessionManager.KEY_EMAIL].toString()
    }

    public fun RedirectNazad(view:View)
    {
        this.finish()
    }

    public fun RedirectDalje(view: View)
    {
        if(naslov.toString() != "" && opis.text.toString() != "" && email != "")
        {
            var intent: Intent = Intent(Intent.ACTION_SENDTO)
            intent.putExtra(Intent.EXTRA_EMAIL,email)
            intent.putExtra(Intent.EXTRA_SUBJECT,naslov)
            intent.putExtra(Intent.EXTRA_TEXT,opis.text.toString())
            intent.data = Uri.parse("mailto: skucise.podrska@gmail.com")

            startActivity(intent)


        }
    }

    private fun addSpinner()
    {
        var problems = arrayOf("Dodavanjem nekretnine","Izmenom nekretnine","Izmenom licnih podataka","Zakazivanjem razgledanja","Obavestenjima","Pisanjem recenzije","Drugi problem")
        var problemi = arrayOf("Problem - Dodavanje nekretnine","Problem - Izmena nekretnine","Problem - Izmena licnih podataka","Problem - Zakazivanje razgledanja","Problem - Obavestenja","Problem - Pisanje recenzije","Problem sa aplikacijom")
        val spinner = findViewById<Spinner>(R.id.problem)
        spinner?.adapter = applicationContext?.let { ArrayAdapter(it, android.R.layout.simple_spinner_dropdown_item, problems) } as SpinnerAdapter
        spinner?.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {
            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val type = parent?.getItemAtPosition(position).toString()
                naslov = problemi[position]
            }
        }
    }


}