package com.weather.freeweatherapp.data.datasource

import com.weather.freeweatherapp.data.db.DefaultLocationDAO
import com.weather.freeweatherapp.data.db.entity.DefaultLocation
import kotlinx.coroutines.flow.Flow

class LocalDBDataSourceImpl(
    private val defaultLocationDAO: DefaultLocationDAO
): LocalDBDataSource {
    override fun getDefaultLocation(): Flow<DefaultLocation> {
        return defaultLocationDAO.getDefaultLocation()
    }

    override suspend fun insertDefaultLocation(defaultLocation: DefaultLocation) {
        defaultLocationDAO.insertDefaultLocation(defaultLocation = defaultLocation)
    }
}