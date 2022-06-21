package com.example.skucise.viewModels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.skucise.models.HelperModels.ReservationMessage
import com.example.skucise.repository.NotificationRepository
import kotlinx.coroutines.launch
import retrofit2.Response

class NotificationViewModel(private val notificationRepository: NotificationRepository) : ViewModel() {
    val sendReservationMessageResponse: MutableLiveData<Response<Boolean>> = MutableLiveData()

    fun sendReservationMessage(reservationMessage: ReservationMessage){
        viewModelScope.launch {
            val response: Response<Boolean> = notificationRepository.sendReservationMessage(reservationMessage)
            sendReservationMessageResponse.value = response
        }
    }
}