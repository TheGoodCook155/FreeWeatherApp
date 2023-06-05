package com.weather.freeweatherapp.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.weather.freeweatherapp.data.db.entity.DefaultLocation

@Database(
    entities = [DefaultLocation::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase: RoomDatabase() {

    abstract fun defaultLocationDAO(): DefaultLocationDAO

}