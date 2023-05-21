package com.weather.freeweatherapp.data.wrapper

data class NetworkResult<T, loading: Boolean, e: Exception>(
    var data: T? = null,
    var isLoading: loading? = null,
    var exception: e? = null
) {


}