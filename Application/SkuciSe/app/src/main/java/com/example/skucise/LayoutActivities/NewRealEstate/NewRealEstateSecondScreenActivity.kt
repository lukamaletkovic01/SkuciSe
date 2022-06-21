package com.example.skucise.LayoutActivities.NewRealEstate

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.RadioButton
import android.widget.TextView
import androidx.appcompat.widget.AppCompatButton
import com.example.skucise.R
import worker8.com.github.radiogroupplus.RadioGroupPlus

class NewRealEstateSecondScreenActivity : AppCompatActivity() {

    lateinit var nastaviDaljeDugme: AppCompatButton
    lateinit var textview: TextView
    lateinit var rbConstainer: RadioGroupPlus
    lateinit var radioButton: RadioButton
    private var radioId : Int = 0
    private var type: String = ""

    private lateinit var textViewKuca: TextView
    private lateinit var textViewStan: TextView
    private lateinit var textViewGaraza: TextView
    private lateinit var textViewParking: TextView
    private lateinit var textViewPoslovniProstor: TextView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_real_estate_second_screen)
        nastaviDaljeDugme = findViewById(R.id.nastaviDaljeDugme)
        rbConstainer = findViewById(R.id.radio_buttons_container_second_screen)

        //kupim selektovanu vrednost sa proslog ekrana
        val extras = intent.extras
        if (extras != null) {
            type = extras.getString("type_first").toString()
        }

    }

    public fun staSamSelektovao(view: View)
    {
        radioId = rbConstainer.checkedRadioButtonId
        radioButton = findViewById(radioId)

        toggleVisibilityAndBackround()

    }

    public fun RedirectNazad(view: View){

        this.finish()
    }
    public fun RedirectDalje(view: View){

        val intent = Intent(this, NewRealEstateThirdScreenActivity::class.java)
        intent.putExtra("type_first", type)
        intent.putExtra("type_second", radioButton.tag.toString())
        startActivity(intent)
    }


    public fun toggleVisibilityAndBackround()
    {
        nastaviDaljeDugme.visibleOrInvisible(true)
    }

    private fun View.visibleOrInvisible(visible: Boolean) {
        visibility = if(visible) View.VISIBLE else View.GONE
    }

}