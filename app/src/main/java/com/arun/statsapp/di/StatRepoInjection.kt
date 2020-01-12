package com.arun.statsapp.di

import com.arun.statsapp.model.StatDataSource
import com.arun.statsapp.model.StatRepository

object StatRepoInjection {

    fun provideStatRepository() : StatDataSource {
        return StatRepository()
    }
}