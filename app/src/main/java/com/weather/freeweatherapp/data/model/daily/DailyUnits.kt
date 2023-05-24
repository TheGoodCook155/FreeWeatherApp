package com.weather.freeweatherapp.data.model.daily


import com.google.gson.annotations.SerializedName

data class DailyUnits(
    @SerializedName("precipitation_hours")
    val precipitationHours: String,
    @SerializedName("rain_sum")
    val rainSum: String,
    @SerializedName("showers_sum")
    val showersSum: String,
    @SerializedName("temperature_2m_max")
    val temperature2mMax: String,
    @SerializedName("temperature_2m_min")
    val temperature2mMin: String,
    @SerializedName("time")
    val time: String,
    @SerializedName("uv_index_max")
    val uvIndexMax: String,
    @SerializedName("winddirection_10m_dominant")
    val winddirection10mDominant: String,
    @SerializedName("windspeed_10m_max")
    val windspeed10mMax: String
)