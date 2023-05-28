package com.weather.freeweatherapp.data.datasource

import com.weather.freeweatherapp.data.model.places.PlacesListItem

interface ResourcesDataSource {

   suspend fun providePlacesFromJson(): List<PlacesListItem>

}