package com.example.skucise.api


import com.example.skucise.models.Comment
import com.example.skucise.models.HelperModels.Status
import com.example.skucise.models.Reservation
import com.example.skucise.models.User
import org.joda.time.DateTime
import retrofit2.Response
import retrofit2.http.*

interface ReservationApi {


    @POST("Reservation/CreateReservation")
    suspend fun createReservation(
        @Body reservation: Reservation
    ): Response<Boolean>


    @GET("Reservation/GetReservedTerms")
    suspend fun getReservedTerms(
        @Query("userId") userId: Long
    ): Response<List<String>>

    @GET("Reservation/GetReservations")
    suspend fun getReservations(
        @Query("userId") userId: Long
    ): Response<List<Reservation>>

    @GET("Reservation/GetSentReservations")
    suspend fun getSentReservations(
        @Query("userId") userId: Long,
        @Query("status") status: Status
    ): Response<List<Reservation>>

    @GET("Reservation/GetReceivedReservations")
    suspend fun getReceivedReservations(
        @Query("userId") userId: Long,
        @Query("status") status: Status
    ): Response<List<Reservation>>

    @GET("Reservation/CheckIfReviewed/{ownerId}/{advertId}/{userId}")
    suspend fun checkIfReviewed(
        @Path("ownerId") ownerId: Long,
        @Path("advertId") advertId: Long,
        @Path("userId") userId: Long
    ): Response<Reservation>

    @POST("Reservation/LeaveCommentForReservation")
    suspend fun leaveCommentForReservation(
        @Body reservation: Reservation
    ): Response<Boolean>

    @GET("Reservation/isAdvertReserved")
    suspend fun isAdvertReserved(
        @Query("advertId") advertId: Long,
        @Query("userId") userId: Long
    ): Response<Boolean>

    @GET("Reservation/CancelReservation/{reservationId}")
    suspend fun cancelReservation(
        @Path("reservationId") reservationId: Long
    ): Response<Boolean>
}
