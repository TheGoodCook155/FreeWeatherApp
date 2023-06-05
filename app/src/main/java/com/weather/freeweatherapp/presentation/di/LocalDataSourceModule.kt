package com.weather.freeweatherapp.presentation.di

import com.weather.freeweatherapp.data.datasource.LocalDBDataSource
import com.weather.freeweatherapp.data.datasource.LocalDBDataSourceImpl
import com.weather.freeweatherapp.data.db.DefaultLocationDAO
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class LocalDataSourceModule {


    @Provides
    @Singleton
    fun providesLocalDataSource(defaultLocationDAO: DefaultLocationDAO): LocalDBDataSource{
        return LocalDBDataSourceImpl(defaultLocationDAO = defaultLocationDAO)
    }

}