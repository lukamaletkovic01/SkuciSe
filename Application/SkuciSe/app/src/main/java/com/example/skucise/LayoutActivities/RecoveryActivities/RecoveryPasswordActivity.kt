package com.example.skucise.LayoutActivities.RecoveryActivities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.skucise.LayoutActivities.BasicActivities.LoadingUtils
import com.example.skucise.LayoutActivities.HomeScreenActivities.HomeScreenActivity
import com.example.skucise.LayoutActivities.LoginActivities.SessionManager
import com.example.skucise.LayoutActivities.ProceedHomeActivity
import com.example.skucise.R
import com.example.skucise.models.HelperModels.UserLogin
import com.example.skucise.repository.LoginRepository
import com.example.skucise.repository.UserRepository
import com.example.skucise.services.SkuciSeFirebaseMessagingService
import com.example.skucise.viewModels.LoginViewModel
import com.example.skucise.viewModels.LoginViewModelFactory
import com.example.skucise.viewModels.UserViewModel
import com.example.skucise.viewModels.UserViewModelFactory
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.messaging.FirebaseMessaging
import kotlin.properties.Delegates

class RecoveryPasswordActivity : AppCompatActivity() {

    private var userId by Delegates.notNull<Long>()
    private lateinit var code : String
    private lateinit var email : String
    private lateinit var userViewModel : UserViewModel
    lateinit var passwordLayout : TextInputLayout
    lateinit var password2Layout: TextInputLayout
    lateinit var passwordEditText : EditText
    lateinit var passwordSecondEditText : EditText


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recovery_password)

        email = intent.getStringExtra("Email").toString()
        userId = intent.getLongExtra("Id", -1)
        code = intent.getStringExtra("Code").toString()

        val userRepository = UserRepository()
        val userViewModelFactory = UserViewModelFactory(userRepository)

        userViewModel = ViewModelProvider(this, userViewModelFactory).get(UserViewModel::class.java)

        passwordEditText = findViewById<EditText>(R.id.password)
        passwordSecondEditText = findViewById<EditText>(R.id.passwordSecond)

        passwordLayout  = findViewById<TextInputLayout>(R.id.passwordInputLayout)
        password2Layout = findViewById<TextInputLayout>(R.id.password2InputLayout)

        userViewModel.passwordRecovery.observe(this, Observer { response ->
            if (response.isSuccessful){
                LoadingUtils.hideDialog()
                val intent = Intent(this, ProceedHomeActivity::class.java)
                intent.putExtra("email", email)
                intent.putExtra("password", passwordEditText.text.toString())
                intent.putExtra("text", "Uspešno ste se izmenili lozinku")
                startActivity(intent)

            }
            else{
                LoadingUtils.hideDialog()
                Toast.makeText(this@RecoveryPasswordActivity, "Došlo je do greške, pokušajte ponovo", Toast.LENGTH_SHORT).show()
            }

        })

    }

    public fun changePassword(view: View){
        var passwordCheck = validatePassword()
        if (passwordCheck){
            userViewModel.changePassword(userId, code, passwordEditText.text.toString())
        }

    }


    public fun validatePassword() : Boolean{

        var password : String = passwordEditText.text.toString()
        var password2 : String = passwordSecondEditText.text.toString()

        var passwordLayout = findViewById<TextInputLayout>(R.id.passwordInputLayout)

        if (password.isNullOrEmpty()) {
            passwordLayout.error = "Polje ne sme biti prazno"
            return false

        }
        else if (password.length < 6){
            passwordLayout.error = "Šifra mora sadržati najmanje 6 karaktera"
            return false
        }
        else if (password2.isNullOrEmpty()){

            passwordLayout.error = null
            password2Layout.error = "Polje ne sme biti prazno"
            return false
        }
        else if (!password2.equals(password)){

            passwordLayout.error = null
            password2Layout.error = "Šifre se ne poklapaju"
            return false
        }
        else{
            password2Layout.error = null
            return true
        }

    }
}