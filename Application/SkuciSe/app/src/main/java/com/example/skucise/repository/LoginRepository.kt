package com.example.skucise.repository

import com.example.skucise.models.HelperModels.UserLogin
import com.example.skucise.api.RetrofitInstance
import retrofit2.Response

class LoginRepository {

        suspend fun login(userLogin : UserLogin) : Response<UserLogin> {

            return RetrofitInstance.userApi.login(userLogin)
    }
}