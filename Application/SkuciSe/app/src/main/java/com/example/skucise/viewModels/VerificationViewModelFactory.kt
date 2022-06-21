package com.example.skucise.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.skucise.repository.NotificationRepository
import com.example.skucise.repository.VerificationRepository

class VerificationViewModelFactory (private val verificationRepository: VerificationRepository) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return VerificationViewModel(verificationRepository) as T
    }
}