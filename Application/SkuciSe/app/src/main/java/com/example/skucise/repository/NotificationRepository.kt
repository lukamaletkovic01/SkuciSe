package com.example.skucise.repository

import com.example.skucise.api.RetrofitInstance
import com.example.skucise.models.HelperModels.ReservationMessage
import retrofit2.Response

class NotificationRepository {
    suspend fun sendReservationMessage(reservationMessage: ReservationMessage) : Response<Boolean> {
        return RetrofitInstance.notificationApi.sendReservationMessage(reservationMessage)
    }
}