package com.example.skucise.LayoutActivities.RecoveryActivities

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
import com.example.skucise.LayoutActivities.HomeScreenActivities.HomeScreenActivity
import com.example.skucise.R
import com.example.skucise.repository.UserRepository
import com.example.skucise.repository.VerificationRepository
import com.example.skucise.viewModels.UserViewModel
import com.example.skucise.viewModels.UserViewModelFactory
import com.example.skucise.viewModels.VerificationViewModel
import com.example.skucise.viewModels.VerificationViewModelFactory
import com.google.android.material.textfield.TextInputLayout

class RecoveryEmailActivity : AppCompatActivity() {

    private lateinit var verificationViewModel : VerificationViewModel
    lateinit var emailEditText : EditText
    lateinit var emailLayout: TextInputLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recovery_email)

        emailEditText = findViewById<EditText>(R.id.email)
        emailLayout = findViewById<TextInputLayout>(R.id.emailInputLayout)
        val verificationRepository = VerificationRepository()
        val verificationViewModelFactory = VerificationViewModelFactory(verificationRepository)

        verificationViewModel = ViewModelProvider(this, verificationViewModelFactory).get(VerificationViewModel::class.java)

        verificationViewModel.sendRecoveryResponse.observe(this, Observer { response ->
            if (response.isSuccessful){
                LoadingUtils.hideDialog()
                val intent = Intent(this, RecoveryCodeActivity::class.java)
                intent.putExtra("Email", emailEditText.text.toString())
                startActivity(intent)
            }
            else{
                LoadingUtils.hideDialog()

                Toast.makeText(this@RecoveryEmailActivity, "Došlo je do greške, proverite uneti email", Toast.LENGTH_SHORT).show()
            }

        })
    }

    public fun sendCode(view: View){

        var emailCheck = validateEmail()

        if (emailCheck){
            LoadingUtils.showDialog(this, false)
            verificationViewModel.SendRecoveryCode(emailEditText.text.toString())
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
}