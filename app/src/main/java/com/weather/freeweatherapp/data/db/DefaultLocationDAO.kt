package com.weather.freeweatherapp.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.weather.freeweatherapp.data.db.entity.DefaultLocation
import kotlinx.coroutines.flow.Flow


@Dao
interface DefaultLocationDAO {

    @Query("SELECT * FROM default_location")
    fun getDefaultLocation(): Flow<DefaultLocation>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertDefaultLocation(defaultLocation: DefaultLocation)



}