package com.arun.statsapp.ui.main.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.arun.statsapp.model.StatDataSource

class StatViewModelFactory(private val repository:StatDataSource):ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return StatViewModel(repository) as T
    }
}