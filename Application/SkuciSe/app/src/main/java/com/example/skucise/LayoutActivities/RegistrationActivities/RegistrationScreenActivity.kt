package com.example.skucise.LayoutActivities.RegistrationActivities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.skucise.LayoutActivities.BasicActivities.LoadingUtils
import com.example.skucise.LayoutActivities.LoginActivities.LoginScreenActivity
import com.example.skucise.R
import com.example.skucise.repository.UserRepository
import com.example.skucise.viewModels.UserViewModel
import com.example.skucise.viewModels.UserViewModelFactory
import com.google.android.material.textfield.TextInputLayout

class RegistrationScreenActivity : AppCompatActivity() {

    private lateinit var userViewModel : UserViewModel

    lateinit var emailEditText : EditText
    lateinit var passwordEditText : EditText
    lateinit var passwordSecondEditText : EditText

    lateinit var emailLayout: TextInputLayout
    lateinit var passwordLayout : TextInputLayout
    lateinit var password2Layout: TextInputLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registration_screen)

        emailEditText = findViewById<EditText>(R.id.email)
        passwordEditText = findViewById<EditText>(R.id.password)
        passwordSecondEditText = findViewById<EditText>(R.id.passwordSecond)

        emailLayout = findViewById<TextInputLayout>(R.id.emailInputLayout)
        passwordLayout = findViewById<TextInputLayout>(R.id.passwordInputLayout)
        password2Layout = findViewById<TextInputLayout>(R.id.password2InputLayout)


        val userRepository = UserRepository()
        val userViewModelFactory = UserViewModelFactory(userRepository)

        userViewModel = ViewModelProvider(this, userViewModelFactory).get(UserViewModel::class.java)

        userViewModel.emailExists.observe(this, Observer { response ->
            if (response.isSuccessful){
                LoadingUtils.hideDialog()
                if (response.body() == true){
                   emailLayout.setError("Nalog sa ovim email-om već postoji")
               }
                else{
                   val intent = Intent(this, RegistrationSecondActivity::class.java)
                   intent.putExtra("Email", emailEditText.text.toString())
                   intent.putExtra("Password", passwordEditText.text.toString())
                   startActivity(intent)
               }
            }
            else{
                Toast.makeText(this@RegistrationScreenActivity, "Došlo je do greške, pokušajte ponovo", Toast.LENGTH_SHORT).show()
                LoadingUtils.hideDialog()
            }

        })

    }
    public fun redirectRegistrationSecond(view: View){


        var emailCheck = validateEmail()
        var passwordCheck = validatePassword()

        if (emailCheck && passwordCheck){
            LoadingUtils.showDialog(this, false)
            userViewModel.checkEmail(emailEditText.text.toString())
        }
    }

    public fun validateEmail() : Boolean{

        var email : String = emailEditText.text.toString()

        if (email.isNullOrEmpty()) {

            emailLayout.setError("Polje ne sme biti prazno")
            return false
        }
        else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            emailLayout.setError("Email nije u ispravnom formatu")
            return false
        }
        else{
            emailLayout.setError(null)
            return true
        }

    }
    public fun validatePassword() : Boolean{

        var password : String = passwordEditText.text.toString()
        var password2 : String = passwordSecondEditText.text.toString()

        var passwordLayout = findViewById<TextInputLayout>(R.id.passwordInputLayout)

        if (password.isNullOrEmpty()) {
            passwordLayout.setError("Polje ne sme biti prazno")
            return false

        }
        else if (password.length < 6){
            passwordLayout.setError("Lozinka mora sadržati najmanje 6 karaktera")
            return false
        }
        else if (password2.isNullOrEmpty()){

            passwordLayout.setError(null)
            password2Layout.setError("Polje ne sme biti prazno")
            return false
        }
        else if (!password2.equals(password)){

            passwordLayout.setError(null)
            password2Layout.setError("Šifre se ne poklapaju")
            return false
        }
        else{
            password2Layout.setError(null)
            return true
        }

    }

    public fun redirectLogin(view: View){
        val intent = Intent(this, LoginScreenActivity::class.java)
        startActivity(intent)
    }
}