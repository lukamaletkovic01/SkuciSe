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
        cities = arrayOf("Ada", "Aleksandrovac", "Aleksinac", "Alibunar", "Apatin", "Aran??elovac", "Arilje", "Babu??nica", "Ba??", "Ba??ka Palanka", "Ba??ka Topola", "Ba??ki Petrovac", "Bajina Ba??ta", "Bato??ina", "Be??ej", "Bela Crkva", "Bela Palanka", "Beo??in", "Beograd", "Blace", "Bogati??", "Bojnik", "Boljevac", "Bor", "Bosilegrad", "Brus", "Bujanovac", "??a??ak", "??ajetina", "??i??evac", "??oka", "Crna Trava", "??uprija", "Despotovac", "Dimitrovgrad", "Doljevac", "Gad??in Han", "Gnjilane", "Golubac", "Gornji Milanovac", "In??ija", "Irig", "Istok", "Ivanjica", "Jagodina", "Kanji??a", "Kikinda", "Kladovo", "Kni??", "Knja??evac", "Koceljeva", "Kosjeri??", "Kosovo Polje", "Kosovska Kamenica", "Kosovska Mitrovica", "Kostolac", "Kova??ica", "Kovin", "Kragujevac", "Kraljevo", "Krupanj", "Kru??evac", "Ku??evo", "Kula", "Kur??umlija", "Lajkovac", "Lapovo", "Lebane", "Leposavi??", "Leskovac", "Lipljan", "Ljig", "Ljubovija", "Loznica", "Lu??ani", "Majdanpek", "Mali I??o??", "Mali Zvornik", "Malo Crni??e", "Medve??a", "Mero??ina", "Mionica", "Negotin", "Ni??", "Nova Crnja", "Nova Varo??", "Novi Be??ej", "Novi Kne??evac", "Novi Pazar", "Novi Sad", "Obili??", "Od??aci", "Opovo", "Ose??ina", "Pan??evo", "Para??in", "Pe??inci", "Petrovac na Mlavi", "Pirot", "Plandi??te", "Po??arevac", "Po??ega", "Pre??evo", "Priboj", "Prijepolje", "Pri??tina", "Prokuplje", "Ra??a (Kragujeva??ka)", "Ra??ka", "Ra??anj", "Rekovac", "Ruma", "??abac", "Se??anj", "Senta", "??id", "Sjenica", "Smederevo", "Smederevska Palanka", "Sokobanja", "Sombor", "Srbobran", "Sremska Mitrovica", "Sremski Karlovci", "Stara Pazova", "??trpce", "Subotica", "Surdulica", "Svilajnac", "Svrljig", "Temerin", "Titel", "Topola", "Trgovi??te", "Trstenik", "Tutin", "Ub", "U??ice", "Valjevo", "Varvarin", "Velika Plana", "Veliko Gradi??te", "Vitina", "Vladi??in Han", "Vladimirci", "Vlasotince", "Vranje", "Vrbas", "Vrnja??ka Banja", "Vr??ac", "Vu??itrn", "??abalj", "??abari", "??agubica", "Zaje??ar", "??iti??te", "??itora??a", "Zrenjanin", "Zubin Potok", "Zve??an")

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