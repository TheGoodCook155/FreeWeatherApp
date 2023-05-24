package com.weather.freeweatherapp.data.model.hourly


import com.google.gson.annotations.SerializedName

data class Hourly(
    @SerializedName("rain")
    val rain: List<Double>,
    @SerializedName("relativehumidity_2m")
    val relativehumidity2m: List<Int>,
    @SerializedName("snowfall")
    val snowfall: List<Double>,
    @SerializedName("temperature_2m")
    val temperature2m: List<Double>,
    @SerializedName("time")
    val time: List<String>,
    @SerializedName("winddirection_180m")
    val winddirection180m: List<Int>,
    @SerializedName("windspeed_10m")
    val windspeed10m: List<Double>,
    @SerializedName("windspeed_180m")
    val windspeed180m: List<Double>
)