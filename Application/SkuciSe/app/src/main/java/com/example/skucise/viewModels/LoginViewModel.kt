package com.example.skucise.viewModels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.skucise.models.HelperModels.UserLogin
import com.example.skucise.repository.LoginRepository
import kotlinx.coroutines.launch
import retrofit2.Response

class LoginViewModel (private val loginRepository: LoginRepository) : ViewModel() {

    val myResponse : MutableLiveData<Response<UserLogin>> = MutableLiveData()

    fun login(userLogin : UserLogin){

        viewModelScope.launch {
            val response : Response<UserLogin> = loginRepository.login(userLogin)
            myResponse.value = response
        }
    }
}