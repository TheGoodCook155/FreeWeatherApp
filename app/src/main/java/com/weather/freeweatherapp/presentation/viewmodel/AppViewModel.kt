package com.weather.freeweatherapp.presentation.viewmodel

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.weather.freeweatherapp.data.AssetsRepository
import com.weather.freeweatherapp.data.RemoteDataSourceRepository
import com.weather.freeweatherapp.data.model.daily.Daily
import com.weather.freeweatherapp.data.model.places.PlacesListItem
import com.weather.freeweatherapp.data.model.hourly.WeatherAPIResponse
import com.weather.freeweatherapp.data.wrapper.NetworkResult
import com.weather.freeweatherapp.data.wrapper.NetworkResultDaily
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AppViewModel @Inject constructor(
    private val repository: RemoteDataSourceRepository,
    private val assetsRepository: AssetsRepository
    ): ViewModel() {

    val data : MutableState<NetworkResult<WeatherAPIResponse,Boolean,Exception>> = mutableStateOf(NetworkResult(null,true,Exception()))
    val dataDaily: MutableState<NetworkResultDaily<Daily,Boolean,Exception>> = mutableStateOf(NetworkResultDaily(null,true,Exception()))
    val places : MutableState<List<PlacesListItem>> = mutableStateOf(emptyList())
    private val daysToShow = 7

    init {
        Log.d("retrieved_data", ":ViewModel init ")
        getHourlyWeather("52.52","13.41",1,"temperature_2m,windspeed_10m,windspeed_180m,relativehumidity_2m,snowfall,rain,winddirection_180m")
        getDailyWeather("52.52","13.41","temperature_2m_max,temperature_2m_min,rain_sum,showers_sum,precipitation_hours,windspeed_10m_max,winddirection_10m_dominant,uv_index_max")
        getPlaces()
    }

    fun getHourlyWeather(latitude: String, longitude: String, days: Int, params: String?){

            data.value.isLoading = true

        viewModelScope.launch(Dispatchers.IO) {

            try {
                val retrievedData = repository.retrieveHourlyWeather(latitude,longitude,daysToShow,"temperature_2m,windspeed_10m,windspeed_180m,relativehumidity_2m,snowfall,rain,winddirection_180m")
                data.value.data = retrievedData.data
            }catch (e: Exception){
                data.value.exception = e
            }
            Log.d("retrieved_data", "getHourlyWeather: Wrapper: ${data.value}\n")
            Log.d("retrieved_data", ": HourlyViewModel: instance: ${data.value.data}")
            Log.d("retrieved_data", ": HourlyViewModel: ${data.value.data}")
        }

        Log.d("retrieved_data", ": HourlyViewModel: ${data.value.data}")

    }

    fun getDailyWeather(latitude: String,longitude: String,daily: String){

        dataDaily.value.isLoading = true

        viewModelScope.launch(Dispatchers.IO) {

            try {
                val retrievedData = repository.retrieveDailyWeather(latitude, longitude,"Europe/Skopje", "temperature_2m_max,temperature_2m_min,rain_sum,showers_sum,precipitation_hours,windspeed_10m_max,winddirection_10m_dominant,uv_index_max")
                dataDaily.value.data = retrievedData.data
                Log.d("retrieved_data_daily", "getHourlyWeather: Wrapper: ${dataDaily.value.data}\n")
            }catch (e: Exception){
                dataDaily.value.exception = e
            }

        }

        Log.d("retrieved_data_daily", ": HourlyViewModel: Exception ${dataDaily.value.exception.toString()}")

    }

    private fun getPlaces(){

        viewModelScope.launch(Dispatchers.IO) {

            val data = assetsRepository.getAllPlaces()
            places.value = data
            Log.d("places_cities", "ViewModel | getPlaces | getFirstElement: ${places.value.get(0)}")

        }
    }
}