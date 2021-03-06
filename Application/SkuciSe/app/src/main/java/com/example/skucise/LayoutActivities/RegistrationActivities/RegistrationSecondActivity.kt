package com.example.skucise.LayoutActivities.RegistrationActivities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.skucise.LayoutActivities.BasicActivities.LoadingUtils
import com.example.skucise.LayoutActivities.HomeScreenActivities.HomeScreenActivity
import com.example.skucise.LayoutActivities.LoginActivities.LoginScreenActivity
import com.example.skucise.R
import com.example.skucise.models.User
import com.example.skucise.repository.UserRepository
import com.example.skucise.repository.VerificationRepository
import com.example.skucise.services.SkuciSeFirebaseMessagingService
import com.example.skucise.viewModels.UserViewModel
import com.example.skucise.viewModels.UserViewModelFactory
import com.example.skucise.viewModels.VerificationViewModel
import com.example.skucise.viewModels.VerificationViewModelFactory
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.messaging.FirebaseMessaging
import java.util.*
import java.util.regex.Pattern

class RegistrationSecondActivity : AppCompatActivity() {

    private lateinit var verificationViewModel : VerificationViewModel
    lateinit var user : User
    private lateinit var fcmToken: String

    lateinit var userEmail : String
    lateinit var userPassword : String
    lateinit var userCity : String

    lateinit var firstnameLayout : TextInputLayout
    lateinit var lastnameLayout: TextInputLayout
    lateinit var phonenumberLayout : TextInputLayout
    lateinit var addressLayout : TextInputLayout

    private val PHONE_NUMBER : Pattern =
        Pattern.compile("^06[0-69][0-9]{6,7}$")
    private lateinit var cities : Array<String>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registration_second)

        getRegistrationToken()

        // list of cities used for dropdown button
        cities = arrayOf("Ada", "Aleksandrovac", "Aleksinac", "Alibunar", "Apatin", "Aran??elovac", "Arilje", "Babu??nica", "Ba??", "Ba??ka Palanka", "Ba??ka Topola", "Ba??ki Petrovac", "Bajina Ba??ta", "Bato??ina", "Be??ej", "Bela Crkva", "Bela Palanka", "Beo??in", "Beograd", "Blace", "Bogati??", "Bojnik", "Boljevac", "Bor", "Bosilegrad", "Brus", "Bujanovac", "??a??ak", "??ajetina", "??i??evac", "??oka", "Crna Trava", "??uprija", "Despotovac", "Dimitrovgrad", "Doljevac", "Gad??in Han", "Gnjilane", "Golubac", "Gornji Milanovac", "In??ija", "Irig", "Istok", "Ivanjica", "Jagodina", "Kanji??a", "Kikinda", "Kladovo", "Kni??", "Knja??evac", "Koceljeva", "Kosjeri??", "Kosovo Polje", "Kosovska Kamenica", "Kosovska Mitrovica", "Kostolac", "Kova??ica", "Kovin", "Kragujevac", "Kraljevo", "Krupanj", "Kru??evac", "Ku??evo", "Kula", "Kur??umlija", "Lajkovac", "Lapovo", "Lebane", "Leposavi??", "Leskovac", "Lipljan", "Ljig", "Ljubovija", "Loznica", "Lu??ani", "Majdanpek", "Mali I??o??", "Mali Zvornik", "Malo Crni??e", "Medve??a", "Mero??ina", "Mionica", "Negotin", "Ni??", "Nova Crnja", "Nova Varo??", "Novi Be??ej", "Novi Kne??evac", "Novi Pazar", "Novi Sad", "Obili??", "Od??aci", "Opovo", "Ose??ina", "Pan??evo", "Para??in", "Pe??inci", "Petrovac na Mlavi", "Pirot", "Plandi??te", "Po??arevac", "Po??ega", "Pre??evo", "Priboj", "Prijepolje", "Pri??tina", "Prokuplje", "Ra??a (Kragujeva??ka)", "Ra??ka", "Ra??anj", "Rekovac", "Ruma", "??abac", "Se??anj", "Senta", "??id", "Sjenica", "Smederevo", "Smederevska Palanka", "Sokobanja", "Sombor", "Srbobran", "Sremska Mitrovica", "Sremski Karlovci", "Stara Pazova", "??trpce", "Subotica", "Surdulica", "Svilajnac", "Svrljig", "Temerin", "Titel", "Topola", "Trgovi??te", "Trstenik", "Tutin", "Ub", "U??ice", "Valjevo", "Varvarin", "Velika Plana", "Veliko Gradi??te", "Vitina", "Vladi??in Han", "Vladimirci", "Vlasotince", "Vranje", "Vrbas", "Vrnja??ka Banja", "Vr??ac", "Vu??itrn", "??abalj", "??abari", "??agubica", "Zaje??ar", "??iti??te", "??itora??a", "Zrenjanin", "Zubin Potok", "Zve??an")

        addSpinner()

        firstnameLayout = findViewById<TextInputLayout>(R.id.firstnameLayout)
        lastnameLayout = findViewById<TextInputLayout>(R.id.lastnameLayout)
        phonenumberLayout = findViewById<TextInputLayout>(R.id.phoneNumberLayout)
        addressLayout = findViewById<TextInputLayout>(R.id.addressLayout)

        userEmail = intent.getStringExtra("Email").toString()
        userPassword = intent.getStringExtra("Password").toString()
        Log.d("email", userEmail)
        Log.d("password", userPassword)

        val verificationRepository = VerificationRepository()
        val verificationViewModelFactory = VerificationViewModelFactory(verificationRepository)

        verificationViewModel = ViewModelProvider(this, verificationViewModelFactory).get(VerificationViewModel::class.java)

        verificationViewModel.sendVerificationResponse.observe(this, Observer { response ->
            if (response.isSuccessful){
                LoadingUtils.hideDialog()
                val intent = Intent(this, VerificationActivity::class.java)
                val args = Bundle()
                args.putSerializable("USER", user)
                intent.putExtra("BUNDLE", args)
                startActivity(intent)
            }
            else{
                Log.d("registracija", response.errorBody().toString())
                LoadingUtils.hideDialog()

                Toast.makeText(this@RegistrationSecondActivity, "Do??lo je do gre??ke", Toast.LENGTH_SHORT).show()
            }

        })

    }

    private fun getRegistrationToken() {
        FirebaseMessaging.getInstance().token.addOnCompleteListener(OnCompleteListener { task ->
            if (!task.isSuccessful) {
                Log.w(SkuciSeFirebaseMessagingService.TAG, "Fetching FCM registration token failed", task.exception)
                return@OnCompleteListener
            }

            initializeFcmToken(task.result)
            return@OnCompleteListener
        })
    }

    private fun initializeFcmToken(result: String?) {
        if (result != null) {
            fcmToken = result
        }
    }

    public fun redirectHome(view: View){

        var firstnameBoolean = checkFirstName()
        var lastnameBoolean = checkLastName()
        var phoneNumberBoolean = checkPhoneNumber()
        var addressBoolean = checkAddress()

        if (firstnameBoolean && lastnameBoolean && phoneNumberBoolean && addressBoolean) {
            LoadingUtils.showDialog(this, false)
            var firstname: String = findViewById<EditText>(R.id.firstname).text.toString()
            var lastname: String = findViewById<EditText>(R.id.lastname).text.toString()
            var phoneNumber: String = findViewById<EditText>(R.id.phoneNumber).text.toString()
            var address: String = findViewById<EditText>(R.id.address).text.toString()

            user = User(
                null,
                userEmail,
                firstname,
                lastname,
                userPassword,
                "default.jpg",
                phoneNumber,
                1,
                fcmToken = fcmToken,
                userCity,
                address
            )
            Log.d("USER", user.toString())
            user.firstname?.let { verificationViewModel.SendVerificationCode(user.email, it) }
        }
    }
    public fun redirectLogin(view: View){
        val intent = Intent(this, LoginScreenActivity::class.java)
        startActivity(intent)
    }

    public fun checkFirstName() : Boolean{

        var firstname: String = findViewById<EditText>(R.id.firstname).text.toString()

        if (firstname.isNullOrEmpty()){
            firstnameLayout.setError("Polje ne sme biti prazno")
            return false
        }
        else{
            firstnameLayout.setError(null)
            return true
        }
    }
    public fun checkLastName() : Boolean{
        var lastname: String = findViewById<EditText>(R.id.lastname).text.toString()

        if (lastname.isNullOrEmpty()){
            lastnameLayout.setError("Polje ne sme biti prazno")
            return false
        }
        else{
            lastnameLayout.setError(null)
            return true
        }
    }

    public fun checkPhoneNumber() : Boolean{

        var phoneNumber: String = findViewById<EditText>(R.id.phoneNumber).text.toString()

        if (phoneNumber.isNullOrEmpty()){
            phonenumberLayout.setError("Polje ne sme biti prazno")
            return false
        }
        else if (!PHONE_NUMBER.matcher(phoneNumber).matches()){
            phonenumberLayout.setError("Broj telefona nije u ispravnom formatu (Format: 06XYYYYYYY)")
            return false
        }
        else {
            phonenumberLayout.setError(null)
            return true
        }


    }

    public fun checkAddress() : Boolean{
        var address: String = findViewById<EditText>(R.id.address).text.toString()

        if (address.isNullOrEmpty()){
            addressLayout.setError("Polje ne sme biti prazno")
            return false
        }
        else {
            addressLayout.setError(null)
            return true
        }


    }

    private fun addSpinner()
    {
        //val types = arrayOf("Izdavanje", "Prodaja")
        val spinner = findViewById<Spinner>(R.id.spinner1)
        spinner?.adapter = applicationContext?.let { ArrayAdapter(it, android.R.layout.simple_spinner_dropdown_item, cities) } as SpinnerAdapter
        spinner?.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {
            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val type = parent?.getItemAtPosition(position).toString()
                userCity = type
                //Toast.makeText(this@RegistrationSecondActivity, userAddress, Toast.LENGTH_LONG).show()
            }
        }
    }

}