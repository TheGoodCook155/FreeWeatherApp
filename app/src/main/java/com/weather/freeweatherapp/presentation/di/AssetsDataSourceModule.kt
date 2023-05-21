package com.weather.freeweatherapp.presentation.di

import android.app.Application
import com.weather.freeweatherapp.data.datasource.AssetsDataSource
import com.weather.freeweatherapp.data.datasource.AssetsDataSourceImpl
import com.weather.freeweatherapp.data.service.LocationProvider
import com.weather.freeweatherapp.data.service.LocationProviderImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AssetsDataSourceModule {

    @Provides
    @Singleton
    fun providesAssetsDataSource(locationProvider: LocationProvider): AssetsDataSource{
        return AssetsDataSourceImpl(locationProvider)
    }

    @Provides
    @Singleton
    fun providesLocationProvider():LocationProvider{
        return LocationProviderImpl()
    }

}