package com.example.skucise.api

import com.example.skucise.models.HelperModels.UserLogin
import com.example.skucise.models.User
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface VerificationApi {

    @POST("Verification/SendVerificationCode")
    suspend fun SendVerificationCode(
        @Query("email") email: String,
        @Query("name") name: String
    ) : Response<Boolean>

    @POST("Verification/Verify")
    suspend fun Verify(
        @Query("email") email : String,
        @Query("code") code : String
    ) : Response<Boolean>
    @POST("Verification/SendRecoveryCode")
    suspend fun SendRecoveryCode(
        @Query("email") email: String
    ) : Response<Boolean>

    @POST("Verification/Recover")
    suspend fun Recover(
        @Query("email") email : String,
        @Query("code") code : String
    ) : Response<Long>

}