package com.example.skucise.LayoutActivities.LoginActivities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.skucise.LayoutActivities.BasicActivities.LoadingUtils
import com.example.skucise.LayoutActivities.HomeScreenActivities.HomeScreenActivity
import com.example.skucise.LayoutActivities.RecoveryActivities.RecoveryEmailActivity
import com.example.skucise.R
import com.example.skucise.LayoutActivities.RegistrationActivities.RegistrationScreenActivity
import com.example.skucise.models.HelperModels.UserLogin
import com.example.skucise.repository.LoginRepository
import com.example.skucise.services.SkuciSeFirebaseMessagingService
import com.example.skucise.viewModels.LoginViewModel
import com.example.skucise.viewModels.LoginViewModelFactory
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.messaging.FirebaseMessaging

class LoginScreenActivity : AppCompatActivity() {

    private lateinit var loginViewModel : LoginViewModel
    private lateinit var fcmToken: String
    lateinit var session: SessionManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login_screen)

        var rec : TextView = findViewById<TextView>(R.id.recovery)
        rec.setOnClickListener{
            val intent = Intent(this, RecoveryEmailActivity::class.java)
            startActivity(intent)
        }

        getRegistrationToken()

        val loginRepository = LoginRepository()
        val loginViewModelFactory = LoginViewModelFactory(loginRepository)
        session = SessionManager(applicationContext)
        loginViewModel = ViewModelProvider(this, loginViewModelFactory).get(LoginViewModel::class.java)


        loginViewModel.myResponse.observe(this, Observer { response ->
            if (response.isSuccessful){
                LoadingUtils.hideDialog()
                var id = response.body()?.id

                Log.d("ResponseBody", response.body().toString())
                response.body()?.let { it.role?.let { it1 -> it.token?.let { it2 ->
                    session.createLoginSession(it.email, it1.toInt() ,
                        it2, id!!.toLong()
                    )
                } } }
                Log.d("Response", "Login je uspeo")
                Log.d("Sesija ", session.getUserDetails().toString())
                val intent = Intent(this, HomeScreenActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                startActivity(intent)
            }
            else{
                LoadingUtils.hideDialog()
                Log.d("Response", response.errorBody().toString())
                Toast.makeText(this@LoginScreenActivity, "Login nije uspeo", Toast.LENGTH_SHORT).show()
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

        val intent = Intent(this, HomeScreenActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
    }
    public fun redirectRegistration(view: View)
    {
        val intent = Intent(this, RegistrationScreenActivity::class.java)
        startActivity(intent)
    }

    public fun login(view: View){
        LoadingUtils.showDialog(this, false)

        val email = findViewById<EditText>(R.id.email)
        val password = findViewById<EditText>(R.id.password)

        val myUserLogin = UserLogin(null, email.text.toString(), password.text.toString(), null, null, fcmToken)
        loginViewModel.login(myUserLogin)
    }
}