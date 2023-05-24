package com.weather.freeweatherapp.data.datasource

import com.weather.freeweatherapp.data.model.daily.Daily
import com.weather.freeweatherapp.data.model.hourly.WeatherAPIResponse
import com.weather.freeweatherapp.data.service.WeatherAPIService
import retrofit2.Response
import javax.inject.Inject

class RemoteDataSourceImpl @Inject constructor(
   private val weatherAPIService: WeatherAPIService
): RemoteDataSource {
    override suspend fun getDailyParametersByTheHour(
        latitude: String,
        longitude: String,
        forecastDays: Int,
        hourly: String
    ): Response<WeatherAPIResponse> {
        return weatherAPIService.getHourlyWeatherResults(latitude,longitude,forecastDays,hourly)
    }

    override suspend fun getDailyWeather(
        latitude: String,
        longitude: String,
        timeZone: String,
        daily: String
    ): Response<Daily> {
        return weatherAPIService.getDailyWeather(latitude,longitude,timeZone,daily)
    }


}