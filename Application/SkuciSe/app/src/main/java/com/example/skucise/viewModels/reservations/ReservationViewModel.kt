package com.example.skucise.viewModels.reservations

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.skucise.models.HelperModels.Status
import com.example.skucise.models.Reservation
import com.example.skucise.models.User
import com.example.skucise.repository.ReservationRepository
import com.example.skucise.repository.UserRepository
import kotlinx.coroutines.launch
import org.joda.time.DateTime
import retrofit2.Response

class ReservationViewModel (private val reservationRepository: ReservationRepository) : ViewModel() {

    val createResponse: MutableLiveData<Response<Boolean>> = MutableLiveData()
    val checkAdvertReservationStatusForMaps: MutableLiveData<Response<Boolean>> = MutableLiveData()
    val reservedTerms: MutableLiveData<Response<List<String>>> = MutableLiveData()
    val reservations: MutableLiveData<Response<List<Reservation>>> = MutableLiveData()
    val checkIfReviewedResponse: MutableLiveData<Response<Reservation>> = MutableLiveData()
    val leaveCommentForReservationResponse: MutableLiveData<Response<Boolean>> = MutableLiveData()
    val cancelReservationResponse: MutableLiveData<Response<Boolean>> = MutableLiveData()

    fun createReservation(reservation: Reservation) {
        viewModelScope.launch {
            val response: Response<Boolean> = reservationRepository.createReservation(reservation)
            createResponse.value = response
        }
    }

    fun isAdvertReserved(advertId: Long,UserId: Long) {
        viewModelScope.launch {
            val response: Response<Boolean> = reservationRepository.isAdvertReserved(advertId,UserId)
            checkAdvertReservationStatusForMaps.value = response
        }
    }

    fun cancelReservation(reservationId: Long){
        viewModelScope.launch {
            val response: Response<Boolean> = reservationRepository.cancelReservation(reservationId)
            cancelReservationResponse.value = response
        }
    }

    fun getReservedTerms(userId: Long) {
        viewModelScope.launch {
            val response: Response<List<String>> = reservationRepository.getReservedTerms(userId)
            reservedTerms.value = response
        }
    }
    fun getReservations(userId: Long) {
        viewModelScope.launch {
            val response: Response<List<Reservation>> = reservationRepository.getReservations(userId)
            reservations.value = response
        }
    }

    fun getSentReservations(userId: Long, status : Status) {
        viewModelScope.launch {
            val response: Response<List<Reservation>> = reservationRepository.getSentReservations(userId, status)
            reservations.value = response
        }
    }
    fun getReceivedReservations(userId: Long, status : Status) {
        viewModelScope.launch {
            val response: Response<List<Reservation>> = reservationRepository.getReceivedReservations(userId, status)
            reservations.value = response
        }
    }

    fun checkIfReviewed(ownerId: Long, advertId: Long, userId: Long){
        viewModelScope.launch {
            val response: Response<Reservation> = reservationRepository.checkIfReviewed(ownerId, advertId, userId)
            checkIfReviewedResponse.value = response
        }
    }

    fun leaveCommentForReservation(reservation: Reservation){
        viewModelScope.launch {
            val response: Response<Boolean> = reservationRepository.leaveCommentForReservation(reservation)
            leaveCommentForReservationResponse.value = response
        }
    }
}