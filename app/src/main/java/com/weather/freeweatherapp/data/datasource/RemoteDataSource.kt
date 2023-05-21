package com.weather.freeweatherapp.data.datasource

import com.weather.freeweatherapp.data.model.WeatherAPIResponse
import retrofit2.Response

interface RemoteDataSource {

    suspend fun getDailyParameters(
    latitude: String,
    longitude: String,
    forecastDays: Int,
    hourly: String
    ): Response<WeatherAPIResponse>
}