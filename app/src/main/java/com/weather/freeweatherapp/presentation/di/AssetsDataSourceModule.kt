package com.weather.freeweatherapp.presentation.di

import com.weather.freeweatherapp.data.datasource.ResourcesDataSource
import com.weather.freeweatherapp.data.datasource.ResourcesDataSourceImpl
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
    fun providesAssetsDataSource(locationProvider: LocationProvider): ResourcesDataSource{
        return ResourcesDataSourceImpl(locationProvider)
    }

    @Provides
    @Singleton
    fun providesLocationProvider():LocationProvider{
        return LocationProviderImpl()
    }

}