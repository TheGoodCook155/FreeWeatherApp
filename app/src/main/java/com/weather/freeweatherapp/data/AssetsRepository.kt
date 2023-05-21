package com.weather.freeweatherapp.data

import android.util.Log
import com.weather.freeweatherapp.data.datasource.AssetsDataSource
import com.weather.freeweatherapp.data.model.PlacesListItem
import javax.inject.Inject

class AssetsRepository @Inject constructor(
    private val assetsDataSource: AssetsDataSource
) {

   suspend fun getAllPlaces(): List<PlacesListItem>{

       val places = assetsDataSource.providePlacesFromJson()

       Log.d("places_cities", "AssetsRepository | getAllPlaces: ${places}")

        return places
    }

}