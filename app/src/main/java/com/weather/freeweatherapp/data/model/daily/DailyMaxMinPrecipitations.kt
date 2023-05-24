package com.weather.freeweatherapp.data.model.daily


import com.google.gson.annotations.SerializedName

data class DailyMaxMinPrecipitations(
    @SerializedName("precipitation_hours")
    val precipitationHours: List<Double>,
    @SerializedName("rain_sum")
    val rainSum: List<Double>,
    @SerializedName("showers_sum")
    val showersSum: List<Double>,
    @SerializedName("temperature_2m_max")
    val temperature2mMax: List<Double>,
    @SerializedName("temperature_2m_min")
    val temperature2mMin: List<Double>,
    @SerializedName("time")
    val time: List<String>,
    @SerializedName("uv_index_max")
    val uvIndexMax: List<Double>,
    @SerializedName("winddirection_10m_dominant")
    val winddirection10mDominant: List<Int>,
    @SerializedName("windspeed_10m_max")
    val windspeed10mMax: List<Double>
)