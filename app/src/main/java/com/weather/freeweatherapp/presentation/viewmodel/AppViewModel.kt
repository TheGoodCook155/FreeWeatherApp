package com.weather.freeweatherapp.presentation.viewmodel

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.weather.freeweatherapp.data.ResourceDataSourceRepository
import com.weather.freeweatherapp.data.RemoteDataSourceRepository
import com.weather.freeweatherapp.data.constants.Constants
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
    private val resourceDataSourceRepository: ResourceDataSourceRepository
    ): ViewModel() {

    val dataHourly : MutableState<NetworkResult<WeatherAPIResponse,Boolean,Exception>> = mutableStateOf(NetworkResult(null,true,Exception()))

    val dataDaily: MutableState<NetworkResultDaily<Daily,Boolean,Exception>> = mutableStateOf(NetworkResultDaily(null,true,Exception()))

    val places : MutableState<List<PlacesListItem>> = mutableStateOf(emptyList())



    private val daysToShow = 1// todo

    init {
        Log.d("retrieved_data", ":ViewModel init ")
        getHourlyWeather("52.52","13.41",daysToShow,Constants.HOURLY_PARAM)
        getDailyWeather("52.52","13.41",Constants.DAILY_PARAM)
        getPlaces()
    }

    fun getHourlyWeather(latitude: String, longitude: String, days: Int, params: String?){

            dataHourly.value.isLoading = true

        viewModelScope.launch(Dispatchers.IO) {

            try {
                val retrievedData = repository.retrieveHourlyWeather(latitude,longitude,days,params!!)
                dataHourly.value.data = retrievedData.data
            }catch (e: Exception){
                dataHourly.value.exception = e
            }
            if (dataHourly.value.data?.hourly?.time?.isNotEmpty() == true) {
                dataHourly.value.isLoading = false
            }
            Log.d("retrieved_data", "getHourlyWeather: Wrapper: ${dataHourly.value}\n")
            Log.d("retrieved_data", ": HourlyViewModel: instance: ${dataHourly.value.data}")
            Log.d("retrieved_data", ": HourlyViewModel: ${dataHourly.value.data}")
        }

        Log.d("retrieved_data", ": HourlyViewModel: ${dataHourly.value.data}")

    }

    fun getDailyWeather(latitude: String,longitude: String, daily: String?){

        dataDaily.value.isLoading = true

        viewModelScope.launch(Dispatchers.IO) {

            try {
                val retrievedData = repository.retrieveDailyWeather(latitude, longitude,"Europe/Skopje", Constants.DAILY_PARAM)
                dataDaily.value.data = retrievedData.data
                Log.d("retrieved_data_daily", "getHourlyWeather: Wrapper: ${dataDaily.value.data}\n")
            }catch (e: Exception){
                dataDaily.value.exception = e
            }

            dataDaily.value.isLoading = false
        }
        Log.d("retrieved_data_daily", ": HourlyViewModel: Exception ${dataDaily.value.exception.toString()}")

    }

    private fun getPlaces(){

        viewModelScope.launch(Dispatchers.IO) {

            val data = resourceDataSourceRepository.getAllPlaces()
            places.value = data
            Log.d("places_cities", "ViewModel | getPlaces | getFirstElement: ${places.value.get(0)}")

        }
    }
}