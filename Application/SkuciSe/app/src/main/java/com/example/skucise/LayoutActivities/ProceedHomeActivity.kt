package com.example.skucise.LayoutActivities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.skucise.LayoutActivities.BasicActivities.LoadingUtils
import com.example.skucise.LayoutActivities.HomeScreenActivities.HomeScreenActivity
import com.example.skucise.LayoutActivities.LoginActivities.SessionManager
import com.example.skucise.R
import com.example.skucise.models.HelperModels.UserLogin
import com.example.skucise.repository.LoginRepository
import com.example.skucise.services.SkuciSeFirebaseMessagingService
import com.example.skucise.viewModels.LoginViewModel
import com.example.skucise.viewModels.LoginViewModelFactory
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.messaging.FirebaseMessaging

class ProceedHomeActivity() : AppCompatActivity() {

    private lateinit var email : String
    private lateinit var password : String
    private lateinit var loginViewModel : LoginViewModel
    private lateinit var fcmToken: String
    private lateinit var text : String
    lateinit var session: SessionManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_proceed_home)

        getRegistrationToken()

        text = intent.getStringExtra("text").toString()
        email = intent.getStringExtra("email").toString()
        password = intent.getStringExtra("password").toString()
        findViewById<TextView>(R.id.text).text =
                text


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
                Toast.makeText(this@ProceedHomeActivity, "Došlo je do greške", Toast.LENGTH_SHORT).show()
            }
        })


    }

    public fun redirectHome(view: View){
        val myUserLogin = UserLogin(null, email, password, null, null, fcmToken)
        loginViewModel.login(myUserLogin)
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
}