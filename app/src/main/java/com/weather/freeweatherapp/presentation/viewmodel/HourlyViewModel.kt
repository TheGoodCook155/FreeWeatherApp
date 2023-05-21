package com.weather.freeweatherapp.presentation.viewmodel

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.weather.freeweatherapp.data.AssetsRepository
import com.weather.freeweatherapp.data.RemoteDataSourceRepository
import com.weather.freeweatherapp.data.model.PlacesListItem
import com.weather.freeweatherapp.data.wrapper.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HourlyViewModel @Inject constructor(
    private val repository: RemoteDataSourceRepository,
    private val assetsRepository: AssetsRepository
    ): ViewModel() {

    val data : MutableState<NetworkResult<Any,Boolean,Exception>> = mutableStateOf(NetworkResult(null,true,Exception()))
    val places : MutableState<List<PlacesListItem>> = mutableStateOf(emptyList())

    init {
        Log.d("retrieved_data", ": ")
        getHourlyWeather()
        getPlaces()
    }

    fun getHourlyWeather(){

            data.value.isLoading = true

        viewModelScope.launch(Dispatchers.IO) {

            try {
                val retrievedData = repository.retrieveHourlyWeather("52.52","13.41",2,"temperature_2m,windspeed_10m,windspeed_180m,relativehumidity_2m,snowfall,rain,winddirection_180m")
                data.value.data = retrievedData
            }catch (e: Exception){
                data.value.exception = e
            }
            Log.d("retrieved_data", "getHourlyWeather: Wrapper: ${data.value}\n")
            Log.d("retrieved_data", ": HourlyViewModel: instance: ${data.value.data}")
            Log.d("retrieved_data", ": HourlyViewModel: ${data.value.data}")
        }

        Log.d("retrieved_data", ": HourlyViewModel: ${data.value.data}")

    }

    private fun getPlaces(){
        viewModelScope.launch(Dispatchers.IO) {

            val data = assetsRepository.getAllPlaces()
            places.value = data
            Log.d("places_cities", "ViewModel | getPlaces | getFirstElement: ${places.value.get(0)}")

        }
    }
}