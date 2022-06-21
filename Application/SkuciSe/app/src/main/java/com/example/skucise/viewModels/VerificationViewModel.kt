package com.example.skucise.viewModels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.skucise.models.HelperModels.ReservationMessage
import com.example.skucise.repository.NotificationRepository
import com.example.skucise.repository.VerificationRepository
import kotlinx.coroutines.launch
import retrofit2.Response

class VerificationViewModel(private val verificationRepository: VerificationRepository) : ViewModel() {

    val sendVerificationResponse: MutableLiveData<Response<Boolean>> = MutableLiveData()
    val verifyResponse: MutableLiveData<Response<Boolean>> = MutableLiveData()
    val recoveryResponse: MutableLiveData<Response<Long>> = MutableLiveData()
    val sendRecoveryResponse: MutableLiveData<Response<Boolean>> = MutableLiveData()

    fun SendVerificationCode(email : String, name : String){
        viewModelScope.launch {
            val response: Response<Boolean> = verificationRepository.SendVerificationCode(email, name)
            sendVerificationResponse.value = response
        }
    }

    fun Verify(email : String, code : String){
        viewModelScope.launch {
            val response: Response<Boolean> = verificationRepository.Verify(email, code)
            verifyResponse.value = response
        }
    }
    fun SendRecoveryCode(email : String){
        viewModelScope.launch {
            val response: Response<Boolean> = verificationRepository.SendRecoveryCode(email)
            sendRecoveryResponse.value = response
        }
    }

    fun Recover(email : String, code : String){
        viewModelScope.launch {
            val response: Response<Long> = verificationRepository.Recover(email, code)
            recoveryResponse.value = response
        }
    }
}