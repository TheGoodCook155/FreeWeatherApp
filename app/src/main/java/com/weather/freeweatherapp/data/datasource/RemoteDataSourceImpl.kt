package com.weather.freeweatherapp.data.datasource

import com.weather.freeweatherapp.data.model.WeatherAPIResponse
import com.weather.freeweatherapp.data.service.WeatherAPIService
import retrofit2.Response
import javax.inject.Inject

class RemoteDataSourceImpl @Inject constructor(
   private val weatherAPIService: WeatherAPIService
): RemoteDataSource {
    override suspend fun getDailyParameters(
        latitude: String,
        longitude: String,
        forecastDays: Int,
        hourly: String
    ): Response<WeatherAPIResponse> {
        return weatherAPIService.getHourlyWeatherResults(latitude,longitude,forecastDays,hourly)
    }
}