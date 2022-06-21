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

class NewRealEstateTypeActivity : AppCompatActivity() {

    lateinit var nastaviDaljeDugme: AppCompatButton
    lateinit var textview: TextView
    lateinit var rbConstainer: RadioGroupPlus
    lateinit var radioButton: RadioButton
    private var radioId : Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_real_estate_type)
        nastaviDaljeDugme = findViewById(R.id.nastaviDaljeDugmeType)
        rbConstainer = findViewById(R.id.radio_buttons_container_type_screen_type)
    }

    public fun redirectNazad(view: View){

        val intent = Intent(this, NewRealEstateActivity::class.java)
        startActivity(intent)
    }

    public fun RedirectDalje(view: View){
        val intent = Intent(this, NewRealEstateSecondScreenActivity::class.java)
        intent.putExtra("type_first", radioButton.tag.toString())
        startActivity(intent)
    }

    public fun staSamSelektovao(view: View)
    {
        radioId = rbConstainer.checkedRadioButtonId
        radioButton = findViewById(radioId)
        toggleVisibilityAndBackround()
    }


    public fun toggleVisibilityAndBackround()
    {
        nastaviDaljeDugme.visibleOrInvisible(true)
    }

    private fun View.visibleOrInvisible(visible: Boolean) {
        visibility = if(visible) View.VISIBLE else View.GONE
    }
}