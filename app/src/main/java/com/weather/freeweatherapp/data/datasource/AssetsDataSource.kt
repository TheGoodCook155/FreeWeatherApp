package com.weather.freeweatherapp.data.datasource

import com.weather.freeweatherapp.data.model.PlacesListItem

interface AssetsDataSource {

   suspend fun providePlacesFromJson(): List<PlacesListItem>

}