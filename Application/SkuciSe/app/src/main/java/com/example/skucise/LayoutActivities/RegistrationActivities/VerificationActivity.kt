package com.example.skucise.LayoutActivities.RegistrationActivities

import android.content.Intent
import android.graphics.Typeface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.SpannableString
import android.text.style.StyleSpan
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.skucise.LayoutActivities.BasicActivities.LoadingUtils
import com.example.skucise.LayoutActivities.HomeScreenActivities.HomeScreenActivity
import com.example.skucise.LayoutActivities.LoginActivities.LoginScreenActivity
import com.example.skucise.LayoutActivities.LoginActivities.SessionManager
import com.example.skucise.LayoutActivities.ProceedHomeActivity
import com.example.skucise.R
import com.example.skucise.models.Advert
import com.example.skucise.models.HelperModels.UserLogin
import com.example.skucise.models.User
import com.example.skucise.repository.LoginRepository
import com.example.skucise.repository.UserRepository
import com.example.skucise.repository.VerificationRepository
import com.example.skucise.services.SkuciSeFirebaseMessagingService
import com.example.skucise.viewModels.*
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.messaging.FirebaseMessaging

class VerificationActivity : AppCompatActivity() {

    private lateinit var user : User
    private lateinit var userViewModel : UserViewModel
    private lateinit var verificationViewModel : VerificationViewModel

    lateinit var session: SessionManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_verification)


        val extras = intent.extras

        if (extras != null) {
            val args = intent.getBundleExtra("BUNDLE")
            user = args!!.getSerializable("USER") as User
        }
        fillText()
        val verificationRepository = VerificationRepository()
        val verificationViewModelFactory = VerificationViewModelFactory(verificationRepository)

        verificationViewModel = ViewModelProvider(this, verificationViewModelFactory).get(VerificationViewModel::class.java)

        val userRepository = UserRepository()
        val userViewModelFactory = UserViewModelFactory(userRepository)

        userViewModel = ViewModelProvider(this, userViewModelFactory).get(UserViewModel::class.java)

        userViewModel.myResponse.observe(this, Observer { response ->
            if (response.isSuccessful){
                LoadingUtils.hideDialog()
                Log.d("Registracije", "Registracije je uspela")
                val intent = Intent(this, ProceedHomeActivity::class.java)
                intent.putExtra("email", user.email)
                intent.putExtra("password", user.password)
                intent.putExtra("text", "Uspešno ste se registrovali")
                startActivity(intent)
            }
            else{
                LoadingUtils.hideDialog()
                Log.d("Registracije", "Registracije nije uspela")
                Toast.makeText(this@VerificationActivity, "Registracija nije uspela", Toast.LENGTH_SHORT).show()
            }

        })
        verificationViewModel.verifyResponse.observe(this, Observer { response ->
            if (response.isSuccessful){
                var bl : Boolean = response.body() as Boolean
                LoadingUtils.hideDialog()
                println("USERRRRR " + user.toString())
                if (bl){
                    userViewModel.createUser(user)
                }
                else{
                    Toast.makeText(this@VerificationActivity, "Uneti kod nije ispravan", Toast.LENGTH_SHORT).show()
                }

            }
            else{
                LoadingUtils.hideDialog()

                Toast.makeText(this@VerificationActivity, "Došlo je do greške", Toast.LENGTH_SHORT).show()
            }

        })
    }

    fun verify(view: View) {

        var code : String = findViewById<EditText>(R.id.code).text.toString()
        verificationViewModel.Verify(user.email, code)


    }

    private fun fillText() {
        val tempString = "Na email \n${user.email}\n Vam je poslat kod."
        val spanString = SpannableString(tempString)
        spanString.setSpan(StyleSpan(Typeface.BOLD), 10, 10 + user.email.length, 0)
        findViewById<TextView>(R.id.text).text =
            spanString
    }

    public fun redirectLogin(view: View){
        val intent = Intent(this, LoginScreenActivity::class.java)
        startActivity(intent)
    }
}