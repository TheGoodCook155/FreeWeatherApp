package com.weather.freeweatherapp.data.wrapper

import com.weather.freeweatherapp.data.model.hourly.WeatherAPIResponse

data class NetworkResult<T: WeatherAPIResponse, loading: Boolean, e: Exception>(
    var data: T? = null,
    var isLoading: loading? = null,
    var exception: e? = null
) {


}