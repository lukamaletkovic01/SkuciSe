package com.example.skucise.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.skucise.repository.NotificationRepository

class NotificationViewModelFactory(private val notificationRepository: NotificationRepository) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return NotificationViewModel(notificationRepository) as T
    }
}