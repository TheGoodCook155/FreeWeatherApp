package com.weather.freeweatherapp.utils.networkstatus

sealed class ConnectionState {
    object Available : ConnectionState()
    object Unavailable : ConnectionState()
}
