package com.example.skucise.repository

import com.example.skucise.api.RetrofitInstance
import com.example.skucise.models.Advert
import com.example.skucise.models.HelperModels.Status
import com.example.skucise.models.Reservation
import org.joda.time.DateTime
import retrofit2.Response
import retrofit2.http.Path

class ReservationRepository {

    suspend fun createReservation(reservation: Reservation): Response<Boolean> {
        return RetrofitInstance.reservationApi.createReservation(reservation);
    }

    suspend fun getReservedTerms(userId: Long): Response<List<String>> {
        return RetrofitInstance.reservationApi.getReservedTerms(userId);
    }

    suspend fun getReservations(userId: Long): Response<List<Reservation>> {
        return RetrofitInstance.reservationApi.getReservations(userId);
    }

    suspend fun getSentReservations(userId: Long, status: Status): Response<List<Reservation>> {
        return RetrofitInstance.reservationApi.getSentReservations(userId, status);
    }

    suspend fun getReceivedReservations(userId: Long, status: Status): Response<List<Reservation>> {
        return RetrofitInstance.reservationApi.getReceivedReservations(userId, status);
    }

    suspend fun checkIfReviewed(ownerId: Long, advertId: Long, userId: Long
    ): Response<Reservation> {
        return RetrofitInstance.reservationApi.checkIfReviewed(ownerId, advertId, userId)
    }

    suspend fun leaveCommentForReservation(reservation: Reservation): Response<Boolean>{
        return RetrofitInstance.reservationApi.leaveCommentForReservation(reservation)
    }

    suspend fun isAdvertReserved(advertId: Long, userId: Long): Response<Boolean> {
        return RetrofitInstance.reservationApi.isAdvertReserved(advertId, userId);
    }

    suspend fun cancelReservation(reservationId: Long): Response<Boolean> {
        return RetrofitInstance.reservationApi.cancelReservation(reservationId)
    }
}
