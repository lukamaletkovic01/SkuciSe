package com.example.skucise.viewModels.reservations

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.skucise.repository.ReservationRepository
import com.example.skucise.repository.UserRepository
import com.example.skucise.viewModels.UserViewModel

class ReservationViewModelFactory(private val reservationRepository: ReservationRepository) : ViewModelProvider.Factory{

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return ReservationViewModel(reservationRepository) as T
    }

}