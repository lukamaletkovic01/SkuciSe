package com.example.skucise.viewModels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.skucise.models.HelperModels.UserLogin
import com.example.skucise.models.User
import com.example.skucise.repository.UserRepository
import kotlinx.coroutines.launch
import retrofit2.Response

class UserViewModel(private val userRepository: UserRepository) : ViewModel() {

    val users: MutableLiveData<Response<List<User>>> = MutableLiveData()
    val myResponse: MutableLiveData<Response<Boolean>> = MutableLiveData()

    val emailExists: MutableLiveData<Response<Boolean>> = MutableLiveData()
    val user: MutableLiveData<Response<User>> = MutableLiveData()
    val updateUserResponse: MutableLiveData<Response<Void>> = MutableLiveData()
    val updateProfilePhotoResponse: MutableLiveData<Response<Boolean>> = MutableLiveData()
    val passwordChange : MutableLiveData<Response<Void>> = MutableLiveData()
    val passwordRecovery : MutableLiveData<Response<Void>> = MutableLiveData()

    val updateRegistrationTokenResponse: MutableLiveData<Response<Boolean>> = MutableLiveData()

    fun getUsers() {
        viewModelScope.launch {
            val response: Response<List<User>> = userRepository.getUsers()
            users.value = response
        }
    }

    fun getUserInfo(id: Long) {
        viewModelScope.launch {
            val response: Response<User> = userRepository.getUserInfo(id)
            user.value = response
        }
    }


    //registration
    fun createUser(user: User) {
        viewModelScope.launch {
            val response: Response<Boolean> = userRepository.createUser(user)
            myResponse.value = response
        }
    }

    fun checkEmail(email: String) {
        viewModelScope.launch {
            val response: Response<Boolean> = userRepository.checkEmail(email)
            emailExists.value = response
        }
    }

    fun updateUser(user: User) {
        viewModelScope.launch {
            val response: Response<Void> = userRepository.updateUser(user)
            updateUserResponse.value = response
        }
    }

    fun updateProfilePhoto(files: String?, userID: Long?) {
        viewModelScope.launch {
            val response: Response<Boolean>? =
                files?.let { userRepository.updateProfilePhoto(it, userID) }
            updateProfilePhotoResponse.value = response
        }
    }

    fun updateFcmToken(userLogin: UserLogin) {
        viewModelScope.launch {
            val response: Response<Boolean> = userRepository.updateFcmToken(userLogin)
            updateRegistrationTokenResponse.value = response
        }
    }
    fun updatePassword(userId : Long?, currentPassword : String, newPassword : String) {
        viewModelScope.launch {
            val response: Response<Void> = userRepository.updatePassword(userId, currentPassword, newPassword)
            passwordChange.value = response
        }
    }
    fun changePassword(userId : Long?, recoveryCode : String, newPassword : String) {
        viewModelScope.launch {
            val response: Response<Void> = userRepository.changePassword(userId, recoveryCode, newPassword)
            passwordRecovery.value = response
        }
    }

}