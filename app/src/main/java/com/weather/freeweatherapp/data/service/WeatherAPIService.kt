package com.weather.freeweatherapp.data.service

import com.weather.freeweatherapp.data.model.daily.Daily
import com.weather.freeweatherapp.data.model.hourly.WeatherAPIResponse
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

    //https://api.open-meteo.com/v1/forecast?latitude=52.52&longitude=13.41&timezone=Europe/Skopje&daily=temperature_2m_max,temperature_2m_min,rain_sum,showers_sum,precipitation_hours,windspeed_10m_max,winddirection_10m_dominant,uv_index_max
    @GET("forecast?")
    suspend fun getDailyWeather(
        @Query("latitude")
        latitude: String,
        @Query("longitude")
        longitude: String,
        @Query("timezone")
        timeZone: String,
        @Query("daily")
        daily: String
    ): Response<Daily>

}