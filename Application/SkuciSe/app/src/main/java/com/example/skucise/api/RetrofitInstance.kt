package com.example.skucise.api

import com.example.skucise.utils.Constrants
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {


    private val retrofit by lazy{

        Retrofit.Builder()
            .baseUrl(Constrants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val userApi: UserApi by lazy{
        retrofit.create(UserApi::class.java)
    }

    val advertApi: AdvertApi by lazy {
        retrofit.create(AdvertApi::class.java)
    }

    val notificationApi: NotificationApi by lazy {
        retrofit.create(NotificationApi::class.java)
    }
    val verificationApi: VerificationApi by lazy {
        retrofit.create(VerificationApi::class.java)
    }

    val reservationApi : ReservationApi by lazy{
        retrofit.create(ReservationApi::class.java)
    }
}