package com.example.skucise.api

import com.example.skucise.models.HelperModels.Message
import com.example.skucise.models.HelperModels.UserLogin
import com.example.skucise.models.User
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.*

interface UserApi {

    @GET("User/GetUsers")
    suspend fun getUsers(): Response<List<User>>

    @POST("Login")
    suspend fun login(
        @Body login: UserLogin
    ) : Response<UserLogin>

    @POST("User/CreateUser")
    suspend fun createUser(
        @Body user: User
    ) : Response<Boolean>

    @GET("User/CheckEmail")
    suspend fun checkEmail(
        @Query("email") email : String
    ) : Response<Boolean>

    @GET("User/GetUserInfo")
    suspend fun  getUserInfo(
        @Query("id") id : Long
    ) : Response<User>

    @PUT("User/UpdateUser")
    suspend fun updateUser(
        @Body user: User
    ) : Response<Void>

    @Multipart
    @POST("Images/")
    suspend fun updateProfilePhoto(
        @Part files : MultipartBody.Part,
        @Part("userID") userID : RequestBody
    ) : Response<Boolean>

    @POST("User/UpdateFcmToken")
    suspend fun updateFcmToken(@Body userLogin: UserLogin) : Response<Boolean>

    @PUT("User/UpdatePassword")
    suspend fun updatePassword(
        @Query("userId") userId: Long?,
        @Query("currentPassword") currentPassword: String,
        @Query("newPassword") newPassword: String,
    ) : Response<Void>

    @PUT("User/ChangePassword")
    suspend fun changePassword(
        @Query("userId") userId: Long?,
        @Query("recoveryCode") recoveryCode: String,
        @Query("newPassword") newPassword: String,
    ) : Response<Void>
}