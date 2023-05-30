package com.weather.freeweatherapp.data

import android.util.Log
import com.weather.freeweatherapp.data.datasource.RemoteDataSource
import com.weather.freeweatherapp.data.model.daily.Daily
import com.weather.freeweatherapp.data.model.hourly.WeatherAPIResponse
import com.weather.freeweatherapp.data.wrapper.NetworkResult
import com.weather.freeweatherapp.data.wrapper.NetworkResultDaily
import retrofit2.Response
import javax.inject.Inject

class RemoteDataSourceRepository @Inject constructor(
    private val remoteDataSource: RemoteDataSource
) {

    suspend fun retrieveHourlyWeather(
        latitude: String,
        longitude: String,
        forecastDays: Int,
        hourly: String
    ): NetworkResult<WeatherAPIResponse,Boolean,Exception> {

        return convertResponseToWrapperOfWeatherAPIResponse(latitude,longitude, forecastDays,hourly)

    }

    suspend fun retrieveDailyWeather(
        latitude: String,
        longitude: String,
        timeZone: String,
        daily: String
    ): NetworkResultDaily<Daily,Boolean,Exception>{
        return convertResponseToWrapperOfWeatherAPIResponseDaily(latitude, longitude,timeZone, daily)
    }

    //convert retrofit response to a wrapper class

    private suspend fun convertResponseToWrapperOfWeatherAPIResponse(
        latitude: String,
        longitude: String,
        forecastDays: Int,
        hourly: String
    ): NetworkResult<WeatherAPIResponse,Boolean,Exception> {

        val response = remoteDataSource.getDailyParametersByTheHour(latitude,longitude, forecastDays,hourly)



        if (response.isSuccessful){
            response.body()?.let {result ->
                Log.d("retrieved_data", "convertResponseToWrapperOfWeatherAPIResponse: ${result}")
                return NetworkResult(result,false,null)
            }
        }
        return NetworkResult(null,false, Exception(response.message().toString()))

    }

    private suspend fun convertResponseToWrapperOfWeatherAPIResponseDaily(
        latitude: String,
        longitude: String,
        timeZone: String,
        daily: String
    ): NetworkResultDaily<Daily,Boolean,Exception> {

        val response = remoteDataSource.getDailyWeather(latitude, longitude,timeZone, daily)
        Log.d("retrieved_data_daily", "convertResponseToWrapperOfWeatherAPIResponseDaily: response_code: ${response.code()}")



        if (response.isSuccessful){
            response.body()?.let {result ->
                Log.d("retrieved_data_daily", "convertResponseToWrapperOfWeatherAPIResponseDaily: ${result}")
                return NetworkResultDaily(result,false,null)
            }
        }else{
            response.errorBody().let { result->
                Log.d("retrieved_data_daily", "convertResponseToWrapperOfWeatherAPIResponseDaily: Exception")
                return NetworkResultDaily(null,false, Exception(result.toString()))
            }
        }

        return NetworkResultDaily(null,false, Exception(response.message().toString()))
    }

}