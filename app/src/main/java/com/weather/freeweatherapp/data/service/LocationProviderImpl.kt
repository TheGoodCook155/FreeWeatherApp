package com.weather.freeweatherapp.data.service

import android.util.Log
import com.google.gson.Gson
import com.weather.freeweatherapp.data.model.places.PlacesList
import com.weather.freeweatherapp.data.model.places.PlacesListItem
import java.io.IOException

class LocationProviderImpl: LocationProvider {

    private lateinit var list: PlacesList

    override suspend fun providePlacesFromJSON(): List<PlacesListItem> {

        val gson = Gson()
        try {

            val inputStream = javaClass.classLoader!!
                .getResourceAsStream("cities.json")

            val json = inputStream.bufferedReader().use {
                it.readText()
            }

            list = gson.fromJson(json, PlacesList::class.java)

            inputStream.close()

        } catch (e: IOException) {
            e.printStackTrace()
        }
        Log.d("places_cities", "LocationProviderImpl | list size: ${list.size}, placeItemsFirst: ${list.get(0).toString()}")

        return list
    }

}