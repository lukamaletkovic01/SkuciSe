package com.example.skucise.LayoutActivities.ProfilePageActivities

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContract
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.skucise.LayoutActivities.LoginActivities.SessionManager
import com.example.skucise.R
import com.example.skucise.models.User
import com.example.skucise.repository.UserRepository
import com.example.skucise.utils.Constrants
import com.example.skucise.viewModels.UserViewModel
import com.example.skucise.viewModels.UserViewModelFactory
import com.google.android.material.textfield.TextInputLayout
import com.squareup.picasso.Picasso
import com.theartofdev.edmodo.cropper.CropImage
import java.util.regex.Pattern

class EditProfileActivity : AppCompatActivity() {
    private lateinit var cities : Array<String>
    var selectedImage : Uri? = null

    private lateinit var profile_pic: ImageView
    private lateinit var userViewModel : UserViewModel
    private var user : User? = null
    lateinit var userCity : String


    lateinit var firstnameLayout : TextInputLayout
    lateinit var lastnameLayout: TextInputLayout
    lateinit var phonenumberLayout : TextInputLayout
    lateinit var addressLayout : TextInputLayout

    private val PHONE_NUMBER : Pattern =
        Pattern.compile("^06[0-69][0-9]{6,7}$")

    private val cropActivityResultContract = object : ActivityResultContract<Any?, Uri?>(){
        override fun createIntent(context: Context, input: Any?): Intent {
            return CropImage.activity()
                .setAspectRatio(12,12)
                .setMinCropResultSize(1000, 1000)
                .getIntent(this@EditProfileActivity)
        }

        override fun parseResult(resultCode: Int, intent: Intent?): Uri? {
            selectedImage = CropImage.getActivityResult(intent).uri
            return CropImage.getActivityResult(intent).uri
        }


    }
    private lateinit var cropActivityResultLauncher: ActivityResultLauncher<Any?>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_profile)

        profile_pic = findViewById<ImageView>(R.id.slikaProfila)
        cropActivityResultLauncher = registerForActivityResult(cropActivityResultContract){
            it?.let{ uri ->
                    profile_pic.setImageURI(uri)
            }
        }
        cities = arrayOf("Ada", "Aleksandrovac", "Aleksinac", "Alibunar", "Apatin", "Aranđelovac", "Arilje", "Babušnica", "Bač", "Bačka Palanka", "Bačka Topola", "Bački Petrovac", "Bajina Bašta", "Batočina", "Bečej", "Bela Crkva", "Bela Palanka", "Beočin", "Beograd", "Blace", "Bogatić", "Bojnik", "Boljevac", "Bor", "Bosilegrad", "Brus", "Bujanovac", "Čačak", "Čajetina", "Ćićevac", "Čoka", "Crna Trava", "Ćuprija", "Despotovac", "Dimitrovgrad", "Doljevac", "Gadžin Han", "Gnjilane", "Golubac", "Gornji Milanovac", "Inđija", "Irig", "Istok", "Ivanjica", "Jagodina", "Kanjiža", "Kikinda", "Kladovo", "Knić", "Knjaževac", "Koceljeva", "Kosjerić", "Kosovo Polje", "Kosovska Kamenica", "Kosovska Mitrovica", "Kostolac", "Kovačica", "Kovin", "Kragujevac", "Kraljevo", "Krupanj", "Kruševac", "Kučevo", "Kula", "Kuršumlija", "Lajkovac", "Lapovo", "Lebane", "Leposavić", "Leskovac", "Lipljan", "Ljig", "Ljubovija", "Loznica", "Lučani", "Majdanpek", "Mali Iđoš", "Mali Zvornik", "Malo Crniće", "Medveđa", "Merošina", "Mionica", "Negotin", "Niš", "Nova Crnja", "Nova Varoš", "Novi Bečej", "Novi Kneževac", "Novi Pazar", "Novi Sad", "Obilić", "Odžaci", "Opovo", "Osečina", "Pančevo", "Paraćin", "Pećinci", "Petrovac na Mlavi", "Pirot", "Plandište", "Požarevac", "Požega", "Preševo", "Priboj", "Prijepolje", "Priština", "Prokuplje", "Rača (Kragujevačka)", "Raška", "Ražanj", "Rekovac", "Ruma", "Šabac", "Sečanj", "Senta", "Šid", "Sjenica", "Smederevo", "Smederevska Palanka", "Sokobanja", "Sombor", "Srbobran", "Sremska Mitrovica", "Sremski Karlovci", "Stara Pazova", "Štrpce", "Subotica", "Surdulica", "Svilajnac", "Svrljig", "Temerin", "Titel", "Topola", "Trgovište", "Trstenik", "Tutin", "Ub", "Užice", "Valjevo", "Varvarin", "Velika Plana", "Veliko Gradište", "Vitina", "Vladičin Han", "Vladimirci", "Vlasotince", "Vranje", "Vrbas", "Vrnjačka Banja", "Vršac", "Vučitrn", "Žabalj", "Žabari", "Žagubica", "Zaječar", "Žitište", "Žitorađa", "Zrenjanin", "Zubin Potok", "Zvečan")

        addSpinner()


        profile_pic.setOnClickListener{
            cropActivityResultLauncher.launch(null)

        }
        firstnameLayout = findViewById<TextInputLayout>(R.id.firstnameLayout)
        lastnameLayout = findViewById<TextInputLayout>(R.id.lastnameLayout)
        phonenumberLayout = findViewById<TextInputLayout>(R.id.phoneNumberLayout)
        addressLayout = findViewById<TextInputLayout>(R.id.addressLayout)

        val userRepository = UserRepository()
        val userViewModelFactory = UserViewModelFactory(userRepository)

        userViewModel = ViewModelProvider(this, userViewModelFactory).get(UserViewModel::class.java)

        var session : SessionManager = SessionManager(this)
        var hash : HashMap<String, String> = session.getUserDetails()
        var id = hash.get(SessionManager.KEY_ID)
        userViewModel.getUserInfo(id!!.toLong())

        userViewModel.user.observe(this, Observer { response ->
            if (response.isSuccessful){
                user = response.body()!!

                var photoUrl = response.body()?.profileImage

                findViewById<EditText>(R.id.firstname).setText(user!!.firstname)
                findViewById<EditText>(R.id.lastname).setText(user!!.lastname)
                findViewById<EditText>(R.id.phoneNumber).setText(user!!.phoneNumber)
                findViewById<EditText>(R.id.address).setText(user!!.address)
                setSpinnerValue(user!!.city)


                var profile_pic : ImageView = findViewById<ImageView>(R.id.slikaProfila)
                Picasso.get().load(Constrants.BASE_URL + "/Images/UserImages/$photoUrl").into(profile_pic)

                Log.d("USER", response.body().toString())
            }
            else{
                Log.d("GRESKA", "Nije uspelo")
            }

        })

        userViewModel.updateUserResponse.observe(this, Observer { response ->
            if (response.isSuccessful){

                if (selectedImage != null) {
                    userViewModel.updateProfilePhoto(selectedImage!!.path, user?.id)
                }
                else{
                    redirectSuccess()
                }
            }
            else{
                Toast.makeText(this@EditProfileActivity, "Izmena nije uspela, pokusajte ponovo", Toast.LENGTH_SHORT).show()
            }

        })

        userViewModel.updateProfilePhotoResponse.observe(this, Observer { response ->
            if (response.isSuccessful){
                response.body()?.let { Log.d("body", it.toString()) }
                redirectSuccess()
            }
            else{
                Toast.makeText(this@EditProfileActivity, "Izmena nije uspela, pokusajte ponovo", Toast.LENGTH_SHORT).show()
            }

        })


    }

    private fun redirectSuccess() {
        val intent = Intent(this, EditUspesanActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
    }

    private fun setSpinnerValue(city: String?) {

        var spinner = findViewById<Spinner>(R.id.spinner1)
        for (i in 0 until spinner.getCount()) {
            if (spinner.getItemAtPosition(i).equals(city)) {
                spinner.setSelection(i)
                break
            }
        }
    }

    public fun idiNazad(view: View){
        finish()
    }

    public fun potvrdi(view: View)
    {
        if (user != null) {
            var firstnameBoolean = checkFirstName()
            var lastnameBoolean = checkLastName()
            var phoneNumberBoolean = checkPhoneNumber()
            var addressBoolean = checkAddress()

            if (firstnameBoolean && lastnameBoolean && phoneNumberBoolean && addressBoolean) {

                user!!.firstname = findViewById<EditText>(R.id.firstname).text.toString()
                user!!.lastname = findViewById<EditText>(R.id.lastname).text.toString()
                user!!.phoneNumber = findViewById<EditText>(R.id.phoneNumber).text.toString()
                user!!.address = findViewById<EditText>(R.id.address).text.toString()
                user!!.city = userCity

                userViewModel.updateUser(user!!)
            }
        }
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