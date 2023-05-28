package com.weather.freeweatherapp.data.datasource

import com.weather.freeweatherapp.data.model.places.PlacesListItem
import com.weather.freeweatherapp.data.service.LocationProvider
import javax.inject.Inject

class ResourcesDataSourceImpl @Inject constructor(
    private val locationProvider: LocationProvider,
) : ResourcesDataSource{

    override suspend fun providePlacesFromJson(): List<PlacesListItem> {
        return locationProvider.providePlacesFromJSON()
    }

}