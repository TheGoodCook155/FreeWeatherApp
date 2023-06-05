package com.weather.freeweatherapp.data

import com.weather.freeweatherapp.data.datasource.LocalDBDataSource
import com.weather.freeweatherapp.data.db.entity.DefaultLocation
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class DBRepository @Inject constructor(
    private val localDBDataSource: LocalDBDataSource
    ) {


    fun getDefaultLocation(): Flow<DefaultLocation>{
        return localDBDataSource.getDefaultLocation()
    }

    suspend fun insertDefaultLocation(defaultLocation: DefaultLocation){
        localDBDataSource.insertDefaultLocation(defaultLocation = defaultLocation)
    }



}