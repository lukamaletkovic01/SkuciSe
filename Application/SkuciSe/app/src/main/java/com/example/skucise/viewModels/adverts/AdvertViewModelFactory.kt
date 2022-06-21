package com.example.skucise.viewModels.adverts

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.skucise.repository.AdvertRepository

class AdvertViewModelFactory (private val advertRepository: AdvertRepository) : ViewModelProvider.Factory{
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return AdvertViewModel(advertRepository) as T
    }
}