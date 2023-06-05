package com.weather.freeweatherapp.data.db.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "default_location")
data class DefaultLocation(
    @PrimaryKey(autoGenerate = false)
    val id: Int,
    @ColumnInfo("place_name")
    val name: String,
    @ColumnInfo("place_latitude")
    val lat: String,
    @ColumnInfo("place_longitude")
    val lng: String
) {
}