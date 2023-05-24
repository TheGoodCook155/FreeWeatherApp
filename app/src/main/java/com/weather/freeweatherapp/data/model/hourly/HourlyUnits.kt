package com.weather.freeweatherapp.data.model.hourly


import com.google.gson.annotations.SerializedName

data class HourlyUnits(
    @SerializedName("rain")
    val rain: String,
    @SerializedName("relativehumidity_2m")
    val relativehumidity2m: String,
    @SerializedName("snowfall")
    val snowfall: String,
    @SerializedName("temperature_2m")
    val temperature2m: String,
    @SerializedName("time")
    val time: String,
    @SerializedName("winddirection_180m")
    val winddirection180m: String,
    @SerializedName("windspeed_10m")
    val windspeed10m: String,
    @SerializedName("windspeed_180m")
    val windspeed180m: String
)