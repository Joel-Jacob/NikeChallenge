package com.example.nikechallenge.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.nikechallenge.network.UDRepository

class UDViewModelFactory(private val udRepository: UDRepository):ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return UDViewModel(udRepository) as T
    }

}