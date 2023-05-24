package com.weather.freeweatherapp.data.datasource

import com.weather.freeweatherapp.data.model.daily.Daily
import com.weather.freeweatherapp.data.model.hourly.WeatherAPIResponse
import retrofit2.Response

interface RemoteDataSource {

    suspend fun getDailyParametersByTheHour(
    latitude: String,
    longitude: String,
    forecastDays: Int,
    hourly: String
    ): Response<WeatherAPIResponse>

    suspend fun getDailyWeather(
        latitude: String,
        longitude: String,
        timeZone:String,
        daily: String
    ): Response<Daily>


}