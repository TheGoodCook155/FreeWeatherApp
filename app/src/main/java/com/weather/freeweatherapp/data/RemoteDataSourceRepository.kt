package com.weather.freeweatherapp.data

import android.util.Log
import com.weather.freeweatherapp.data.datasource.RemoteDataSource
import com.weather.freeweatherapp.data.model.WeatherAPIResponse
import com.weather.freeweatherapp.data.wrapper.NetworkResult
import javax.inject.Inject

class RemoteDataSourceRepository @Inject constructor(
    private val remoteDataSource: RemoteDataSource
) {

    suspend fun retrieveHourlyWeather(
        latitude: String,
        longitude: String,
        forecastDays: Int,
        hourly: String
    ): NetworkResult<Any,Boolean,Exception> {

        return convertResponseToWrapperOfWeatherAPIResponse(latitude,longitude, forecastDays,hourly)

    }

    //convert retrofit response to a wrapper class

    private suspend fun convertResponseToWrapperOfWeatherAPIResponse(
        latitude: String,
        longitude: String,
        forecastDays: Int,
        hourly: String
    ): NetworkResult<Any,Boolean,Exception> {

        val response = remoteDataSource.getDailyParameters(latitude,longitude, forecastDays,hourly)

        if (response.isSuccessful){
            response.body()?.let {result ->
                Log.d("retrieved_data", "convertResponseToWrapperOfWeatherAPIResponse: ${result}")
                return NetworkResult(result,false,null)
            }
        }
        return NetworkResult(null,false, Exception(response.message().toString()))

    }

}