package com.example.skucise.LayoutActivities.SettingsActivities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.skucise.LayoutActivities.BasicActivities.LoadingUtils
import com.example.skucise.LayoutActivities.LoginActivities.SessionManager
import com.example.skucise.R
import com.example.skucise.repository.UserRepository
import com.example.skucise.viewModels.UserViewModel
import com.example.skucise.viewModels.UserViewModelFactory
import com.google.android.material.textfield.TextInputLayout

class ChangePasswordActivity : AppCompatActivity() {

    private lateinit var userViewModel : UserViewModel
    lateinit var currentPasswordEditText : EditText
    lateinit var passwordEditText : EditText
    lateinit var passwordSecondEditText : EditText

    lateinit var currentPasswordLayout: TextInputLayout
    lateinit var passwordLayout : TextInputLayout
    lateinit var password2Layout: TextInputLayout

    lateinit var session : SessionManager
    lateinit var hash : HashMap<String, String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_change_password)

        session = SessionManager(this)
        hash = session.getUserDetails()

        currentPasswordEditText = findViewById<EditText>(R.id.currentPassword)
        passwordEditText = findViewById<EditText>(R.id.password)
        passwordSecondEditText = findViewById<EditText>(R.id.passwordSecond)

        currentPasswordLayout = findViewById<TextInputLayout>(R.id.currentPasswordInputLayout)
        passwordLayout = findViewById<TextInputLayout>(R.id.passwordInputLayout)
        password2Layout = findViewById<TextInputLayout>(R.id.password2InputLayout)

        val userRepository = UserRepository()
        val userViewModelFactory = UserViewModelFactory(userRepository)

        userViewModel = ViewModelProvider(this, userViewModelFactory).get(UserViewModel::class.java)

        userViewModel.passwordChange.observe(this, Observer { response ->
            if (response.isSuccessful){
                LoadingUtils.hideDialog()
                Toast.makeText(this@ChangePasswordActivity, "Uspešno ste promenili šifru", Toast.LENGTH_SHORT).show()
                this.finish()
            }
            else{
                Toast.makeText(this@ChangePasswordActivity, "Došlo je do greške, proverite trenutnu lozinku", Toast.LENGTH_SHORT).show()
                LoadingUtils.hideDialog()
            }

        })
    }

    public fun redirectSettings(view: View){

        val intent = Intent(this, SettingsActivity::class.java)
        startActivity(intent)
    }

    public fun changePassword(view: View){


        var currentPasswordCheck = currentPasswordCheck()
        var passwordCheck = validatePassword()

        if (currentPasswordCheck && passwordCheck){
            var id = hash.get(SessionManager.KEY_ID)
            LoadingUtils.showDialog(this, false)
            userViewModel.updatePassword(id?.toLong(), currentPasswordEditText.text.toString(), passwordEditText.text.toString())
        }

    }

    public fun currentPasswordCheck() : Boolean{

        var email : String = currentPasswordEditText.text.toString()

        if (email.isNullOrEmpty()) {

            currentPasswordLayout.setError("Polje ne sme biti prazno")
            return false
        }
        else{
            currentPasswordLayout.setError(null)
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
}