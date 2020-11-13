package com.nhat.presentation.shop

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

@Suppress("UNCHECKED_CAST")
open class InfoShopViewModelFactory(
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(InfoShopViewModel::class.java)) {
            return InfoShopViewModel() as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}