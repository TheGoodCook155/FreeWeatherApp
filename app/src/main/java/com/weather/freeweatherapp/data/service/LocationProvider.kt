package com.weather.freeweatherapp.data.service

import com.weather.freeweatherapp.data.model.PlacesListItem

interface LocationProvider {

   suspend fun providePlacesFromJSON(): List<PlacesListItem>

}