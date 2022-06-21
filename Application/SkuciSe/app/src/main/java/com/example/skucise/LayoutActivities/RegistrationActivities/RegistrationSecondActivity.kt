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
        cities = arrayOf("Ada", "Aleksandrovac", "Aleksinac", "Alibunar", "Apatin", "Aranđelovac", "Arilje", "Babušnica", "Bač", "Bačka Palanka", "Bačka Topola", "Bački Petrovac", "Bajina Bašta", "Batočina", "Bečej", "Bela Crkva", "Bela Palanka", "Beočin", "Beograd", "Blace", "Bogatić", "Bojnik", "Boljevac", "Bor", "Bosilegrad", "Brus", "Bujanovac", "Čačak", "Čajetina", "Ćićevac", "Čoka", "Crna Trava", "Ćuprija", "Despotovac", "Dimitrovgrad", "Doljevac", "Gadžin Han", "Gnjilane", "Golubac", "Gornji Milanovac", "Inđija", "Irig", "Istok", "Ivanjica", "Jagodina", "Kanjiža", "Kikinda", "Kladovo", "Knić", "Knjaževac", "Koceljeva", "Kosjerić", "Kosovo Polje", "Kosovska Kamenica", "Kosovska Mitrovica", "Kostolac", "Kovačica", "Kovin", "Kragujevac", "Kraljevo", "Krupanj", "Kruševac", "Kučevo", "Kula", "Kuršumlija", "Lajkovac", "Lapovo", "Lebane", "Leposavić", "Leskovac", "Lipljan", "Ljig", "Ljubovija", "Loznica", "Lučani", "Majdanpek", "Mali Iđoš", "Mali Zvornik", "Malo Crniće", "Medveđa", "Merošina", "Mionica", "Negotin", "Niš", "Nova Crnja", "Nova Varoš", "Novi Bečej", "Novi Kneževac", "Novi Pazar", "Novi Sad", "Obilić", "Odžaci", "Opovo", "Osečina", "Pančevo", "Paraćin", "Pećinci", "Petrovac na Mlavi", "Pirot", "Plandište", "Požarevac", "Požega", "Preševo", "Priboj", "Prijepolje", "Priština", "Prokuplje", "Rača (Kragujevačka)", "Raška", "Ražanj", "Rekovac", "Ruma", "Šabac", "Sečanj", "Senta", "Šid", "Sjenica", "Smederevo", "Smederevska Palanka", "Sokobanja", "Sombor", "Srbobran", "Sremska Mitrovica", "Sremski Karlovci", "Stara Pazova", "Štrpce", "Subotica", "Surdulica", "Svilajnac", "Svrljig", "Temerin", "Titel", "Topola", "Trgovište", "Trstenik", "Tutin", "Ub", "Užice", "Valjevo", "Varvarin", "Velika Plana", "Veliko Gradište", "Vitina", "Vladičin Han", "Vladimirci", "Vlasotince", "Vranje", "Vrbas", "Vrnjačka Banja", "Vršac", "Vučitrn", "Žabalj", "Žabari", "Žagubica", "Zaječar", "Žitište", "Žitorađa", "Zrenjanin", "Zubin Potok", "Zvečan")

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

                Toast.makeText(this@RegistrationSecondActivity, "Došlo je do greške", Toast.LENGTH_SHORT).show()
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