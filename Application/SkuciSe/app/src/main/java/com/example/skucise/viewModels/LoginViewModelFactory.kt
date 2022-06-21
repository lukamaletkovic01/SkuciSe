package com.example.skucise.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.skucise.repository.LoginRepository
import com.example.skucise.repository.UserRepository



    class LoginViewModelFactory(private val loginRepository: LoginRepository) : ViewModelProvider.Factory{
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return LoginViewModel(loginRepository) as T
        }

    }