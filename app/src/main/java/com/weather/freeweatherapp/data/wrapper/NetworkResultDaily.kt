package com.weather.freeweatherapp.data.wrapper

import com.weather.freeweatherapp.data.model.daily.Daily

data class NetworkResultDaily<T: Daily, loading: Boolean, e: Exception>(
    var data: T? = null,
    var isLoading: loading? = null,
    var exception: e? = null
)
