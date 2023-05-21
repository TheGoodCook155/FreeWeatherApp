package com.weather.freeweatherapp.data.model


import com.google.gson.annotations.SerializedName

data class PlacesListItem(
    @SerializedName("country")
    val country: String,
    @SerializedName("lat")
    val lat: String,
    @SerializedName("lng")
    val lng: String,
    @SerializedName("name")
    val name: String
)