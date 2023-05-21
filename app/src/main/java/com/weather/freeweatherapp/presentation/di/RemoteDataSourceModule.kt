package com.weather.freeweatherapp.presentation.di

import com.weather.freeweatherapp.data.datasource.RemoteDataSource
import com.weather.freeweatherapp.data.datasource.RemoteDataSourceImpl
import com.weather.freeweatherapp.data.service.WeatherAPIService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
class RemoteDataSourceModule {


    @Singleton
    @Provides
    fun providesRemoteDataSource(weatherAPIService: WeatherAPIService): RemoteDataSource{
        return RemoteDataSourceImpl(weatherAPIService)
    }

}