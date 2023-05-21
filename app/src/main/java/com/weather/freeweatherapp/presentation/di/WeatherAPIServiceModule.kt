package com.weather.freeweatherapp.presentation.di

import com.weather.freeweatherapp.BuildConfig
import com.weather.freeweatherapp.data.service.WeatherAPIService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
class WeatherAPIServiceModule {

    @Singleton
    @Provides
    fun providesRetrofitInstance(): Retrofit{

        return Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Singleton
    @Provides
    fun providesWeatherAPIService(retrofit: Retrofit): WeatherAPIService{
        return retrofit.create(WeatherAPIService::class.java)
    }

}