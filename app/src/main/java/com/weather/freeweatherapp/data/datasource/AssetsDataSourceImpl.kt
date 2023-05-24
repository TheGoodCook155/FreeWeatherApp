package com.weather.freeweatherapp.data.datasource

import com.weather.freeweatherapp.data.model.places.PlacesListItem
import com.weather.freeweatherapp.data.service.LocationProvider
import javax.inject.Inject

class AssetsDataSourceImpl @Inject constructor(
    private val locationProvider: LocationProvider,
) : AssetsDataSource{

    override suspend fun providePlacesFromJson(): List<PlacesListItem> {
        return locationProvider.providePlacesFromJSON()
    }

}