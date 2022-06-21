package com.example.skucise.repository

import com.example.skucise.api.RetrofitInstance
import com.example.skucise.models.HelperModels.ReservationMessage
import retrofit2.Response

class VerificationRepository {

    suspend fun SendVerificationCode(email : String, name : String) : Response<Boolean> {
        return RetrofitInstance.verificationApi.SendVerificationCode(email,name)
    }
    suspend fun Verify(email : String, code : String) : Response<Boolean> {
        return RetrofitInstance.verificationApi.Verify(email, code)
    }

    suspend fun SendRecoveryCode(email : String) : Response<Boolean> {
        return RetrofitInstance.verificationApi.SendRecoveryCode(email)
    }
    suspend fun Recover(email : String, code : String) : Response<Long> {
        return RetrofitInstance.verificationApi.Recover(email, code)
    }

}