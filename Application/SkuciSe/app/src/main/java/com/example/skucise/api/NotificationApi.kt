package com.example.skucise.api

import com.example.skucise.models.HelperModels.ReservationMessage
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface NotificationApi {
    @POST("Notification/SendReservationMessage")
    suspend fun sendReservationMessage(@Body reservationMessage: ReservationMessage) : Response<Boolean>
}