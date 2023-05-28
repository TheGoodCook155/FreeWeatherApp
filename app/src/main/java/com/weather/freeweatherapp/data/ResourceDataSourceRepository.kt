package com.weather.freeweatherapp.data

import android.util.Log
import com.weather.freeweatherapp.data.datasource.ResourcesDataSource
import com.weather.freeweatherapp.data.model.places.PlacesListItem
import javax.inject.Inject

class ResourceDataSourceRepository @Inject constructor(
    private val resourcesDataSource: ResourcesDataSource
) {

   suspend fun getAllPlaces(): List<PlacesListItem>{

       val places = resourcesDataSource.providePlacesFromJson()

       Log.d("places_cities", "AssetsRepository | getAllPlaces: ${places}")

        return places
    }

}