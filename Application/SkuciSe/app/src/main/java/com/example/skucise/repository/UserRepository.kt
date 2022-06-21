package com.example.skucise.repository

import com.example.skucise.models.User
import com.example.skucise.api.RetrofitInstance
import com.example.skucise.models.HelperModels.Message
import com.example.skucise.models.HelperModels.UserLogin
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.Part
import java.io.File

class UserRepository {
    suspend fun getUsers() : Response<List<User>> {
        return RetrofitInstance.userApi.getUsers()
    }
    suspend fun createUser(user : User) : Response<Boolean>{

        return RetrofitInstance.userApi.createUser(user)
    }

    suspend fun checkEmail(email : String) : Response<Boolean>{

        return RetrofitInstance.userApi.checkEmail(email)
    }

    suspend fun getUserInfo(id : Long) : Response<User>{

        return RetrofitInstance.userApi.getUserInfo(id)
    }

    suspend fun updateUser(user : User) : Response<Void>{

        return RetrofitInstance.userApi.updateUser(user)
    }

    suspend fun updateProfilePhoto(realPath: String, userID: Long?) : Response<Boolean>{

        var file: File = File(realPath)
        var requestFile: RequestBody =
            RequestBody.create("image/*".toMediaTypeOrNull(), file)
        var body: MultipartBody.Part =
            MultipartBody.Part.createFormData("files", file.name, requestFile)
        var id: RequestBody =
            RequestBody.create("text/plain".toMediaTypeOrNull(), userID.toString())
        return RetrofitInstance.userApi.updateProfilePhoto(body, id)
    }

    suspend fun updateFcmToken(userLogin: UserLogin) : Response<Boolean>{
        return RetrofitInstance.userApi.updateFcmToken(userLogin);
    }

    suspend fun updatePassword(userId : Long?, currentPassword : String, newPassword : String) : Response<Void>{
        return RetrofitInstance.userApi.updatePassword(userId, currentPassword, newPassword);
    }
    suspend fun changePassword(userId : Long?, recoveryCode : String, newPassword : String) : Response<Void>{
        return RetrofitInstance.userApi.changePassword(userId, recoveryCode, newPassword);
    }
}