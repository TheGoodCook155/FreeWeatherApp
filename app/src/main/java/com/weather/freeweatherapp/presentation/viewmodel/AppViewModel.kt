package com.weather.freeweatherapp.presentation.viewmodel

import android.content.res.Configuration
import android.util.Log
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalConfiguration
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.weather.freeweatherapp.data.DBRepository
import com.weather.freeweatherapp.data.ResourceDataSourceRepository
import com.weather.freeweatherapp.data.RemoteDataSourceRepository
import com.weather.freeweatherapp.data.constants.Constants
import com.weather.freeweatherapp.data.db.entity.DefaultLocation
import com.weather.freeweatherapp.data.model.daily.Daily
import com.weather.freeweatherapp.data.model.places.PlacesListItem
import com.weather.freeweatherapp.data.model.hourly.WeatherAPIResponse
import com.weather.freeweatherapp.data.wrapper.NetworkResult
import com.weather.freeweatherapp.data.wrapper.NetworkResultDaily
import com.weather.freeweatherapp.utils.networkstatus.ConnectionState
import com.weather.freeweatherapp.utils.networkstatus.connectivityState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class AppViewModel @Inject constructor(
    private val repository: RemoteDataSourceRepository,
    private val resourceDataSourceRepository: ResourceDataSourceRepository,
    private val dbRepository: DBRepository
    ): ViewModel() {

    val dataHourly : MutableState<NetworkResult<WeatherAPIResponse,Boolean,Exception>> = mutableStateOf(NetworkResult(null,true,Exception()))

    val dataDaily: MutableState<NetworkResultDaily<Daily,Boolean,Exception>> = mutableStateOf(NetworkResultDaily(null,true,Exception()))

    val places : MutableState<List<PlacesListItem>> = mutableStateOf(emptyList())

    val defaultLocation: MutableState<DefaultLocation> = mutableStateOf(DefaultLocation(0,"Skopje","52.52","13.41"))

    val isConnected: MutableState<Boolean> = mutableStateOf(false)

    private val daysToShow = 1// todo

    init {

        Log.d("viewModelInit", ":ViewModel init ")
        Log.d("default_location", ": defaultLocation: ${defaultLocation.value}")
        getDefaultLocation()
//        getHourlyWeather("52.52","13.41",daysToShow,Constants.HOURLY_PARAM)
//        getDailyWeather("52.52","13.41",Constants.DAILY_PARAM)

        getHourlyWeather(defaultLocation.value.lat,defaultLocation.value.lng,1,Constants.HOURLY_PARAM)
        getDailyWeather(defaultLocation.value.lat,defaultLocation.value.lng,Constants.DAILY_PARAM)
        getPlaces()
    }

    fun getHourlyWeather(latitude: String, longitude: String, days: Int, params: String?){

        dataHourly.value.isLoading = true

        viewModelScope.launch(Dispatchers.IO) {

            try {
                val retrievedData = repository.retrieveHourlyWeather(latitude,longitude,days,params!!)

                dataHourly.value.data = retrievedData.data

            }catch (e: Exception){
                dataDaily.value.exception = Exception(e.message.toString())
            }
                dataHourly.value.isLoading = false

            Log.d("retrieved_data_hourly", ": HourlyViewModel: ${dataHourly.value.data}")
        }

    }

    fun getDailyWeather(latitude: String,longitude: String, daily: String?){

        dataDaily.value.isLoading = true

        viewModelScope.launch(Dispatchers.IO) {

            try {
                val retrievedData = repository.retrieveDailyWeather(latitude, longitude,"Europe/Skopje", Constants.DAILY_PARAM)
                dataDaily.value.data = retrievedData.data
                Log.d("retrieved_data_daily", "getHourlyWeather: Wrapper: ${dataDaily.value.data}\n")
            }catch (e: Exception){
                dataDaily.value.exception = Exception(e.message.toString())
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

    fun getDefaultLocation(): DefaultLocation{


        Log.d("default_location", "getDefaultLocation: STARTED")
        viewModelScope.launch {

          dbRepository.getDefaultLocation().collect { location ->
              Log.d("default_location", "AppViewModel | getDefaultLocation collector: ${location} ")
              val defaultWhenNull = DefaultLocation(0, "Skopje", "52.52", "13.41")
              if (location == null) {
                  defaultLocation.value = defaultWhenNull
                  Log.d(
                      "default_location",
                      "AppViewModel | defaultLocation.value == null, switched: ${defaultLocation.value.id}, ${defaultLocation.value.name}"
                  )
              } else {
                  defaultLocation.value = location
              }
          }


            Log.d("default_location", "AppViewModel | getDefaultLocation: NULL: ${defaultLocation.value.id}, ${defaultLocation.value.name}")
        }


        Log.d("default_location", "AppViewModel | getDefaultLocation: ENDS")

               return defaultLocation.value
    }

    fun insertDefaultLocation(defaultLocation: DefaultLocation){

        viewModelScope.launch {
            dbRepository.insertDefaultLocation(defaultLocation = defaultLocation)
        }

    }

}