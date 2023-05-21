package com.weather.freeweatherapp.data.service

import com.weather.freeweatherapp.BuildConfig
import com.weather.freeweatherapp.data.model.WeatherAPIResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query


interface WeatherAPIService {

    //forecast?latitude=52.52&longitude=13.41&forecast_days=14&hourly=temperature_2m,windspeed_10m,windspeed_180m,relativehumidity_2m,snowfall,rain,winddirection_180m

    @GET("forecast?")
    suspend fun getHourlyWeatherResults(
        @Query("latitude")
        latitude: String,
        @Query("longitude")
        longitude: String,
        @Query("forecast_days")
        forecastDays: Int,
        @Query("hourly")
        hourlyParams: String
    ): Response<WeatherAPIResponse>


}