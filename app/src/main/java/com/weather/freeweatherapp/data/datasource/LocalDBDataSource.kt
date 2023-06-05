package com.weather.freeweatherapp.data.datasource

import com.weather.freeweatherapp.data.db.entity.DefaultLocation
import kotlinx.coroutines.flow.Flow

interface LocalDBDataSource {

    fun getDefaultLocation(): Flow<DefaultLocation>

    suspend fun insertDefaultLocation(defaultLocation: DefaultLocation)

}