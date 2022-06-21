package com.example.skucise.LayoutActivities.RecoveryActivities

import android.content.Intent
import android.graphics.Typeface
import android.os.Bundle
import android.text.SpannableString
import android.text.style.StyleSpan
import android.view.View
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.skucise.LayoutActivities.BasicActivities.LoadingUtils
import com.example.skucise.R
import com.example.skucise.repository.VerificationRepository
import com.example.skucise.viewModels.VerificationViewModel
import com.example.skucise.viewModels.VerificationViewModelFactory


class RecoveryCodeActivity : AppCompatActivity() {

    private lateinit var verificationViewModel : VerificationViewModel
    private lateinit var email : String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recovery_code)
        email = intent.getStringExtra("Email").toString()
        fillText()

        val verificationRepository = VerificationRepository()
        val verificationViewModelFactory = VerificationViewModelFactory(verificationRepository)

        verificationViewModel = ViewModelProvider(this, verificationViewModelFactory).get(VerificationViewModel::class.java)

        verificationViewModel.recoveryResponse.observe(this, Observer { response ->
            if (response.isSuccessful){
                LoadingUtils.hideDialog()
                var id : Long? = response.body()
                if (id != null) {
                    if (id.toInt() != -1){
                        val intent = Intent(this, RecoveryPasswordActivity::class.java)
                        intent.putExtra("Id", id.toLong())
                        intent.putExtra("Email", email)
                        intent.putExtra("Code", findViewById<EditText>(R.id.code).text.toString())
                        startActivity(intent)
                    }
                    else{
                        Toast.makeText(this@RecoveryCodeActivity, "Uneti kod nije ispravan ili je vreme za resetovanje isteklo (3h)", Toast.LENGTH_SHORT).show()
                    }

                }
                else{
                    Toast.makeText(this@RecoveryCodeActivity, "Došlo je do greške", Toast.LENGTH_SHORT).show()
                }

            }
            else{
                LoadingUtils.hideDialog()

                Toast.makeText(this@RecoveryCodeActivity, "Došlo je do greške", Toast.LENGTH_SHORT).show()
            }

        })
    }

    private fun fillText() {
        val tempString = "Na email \n$email\n Vam je poslat kod."
        val spanString = SpannableString(tempString)
        spanString.setSpan(StyleSpan(Typeface.BOLD), 10, 10 + email.length, 0)
        findViewById<TextView>(R.id.text).text =
            spanString
    }

    public fun recover(view: View){
        var code : String = findViewById<EditText>(R.id.code).text.toString()
        verificationViewModel.Recover(email, code)
    }
}