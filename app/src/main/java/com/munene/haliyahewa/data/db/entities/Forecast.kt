package com.munene.haliyahewa.data.db.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "current_forecast")
data class Forecast(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id") var id: Int? = null,
    @ColumnInfo(name = "country") var country: String? = "",
    @ColumnInfo(name = "lat") val lat: Double? = 0.0,
    @ColumnInfo(name = "lon") val lon: Double? = 0.0,
    @ColumnInfo(name = "weather") val weather: String? = "",
    @ColumnInfo(name = "weather_id") val weatherId: Int? = 0,
    @ColumnInfo(name = "weather_icon") val weatherIcon: String? = "",
    @ColumnInfo(name = "temp") val temp: Double? = 0.0,
    @ColumnInfo(name = "temp_min") val tempMin: Double? = 0.0,
    @ColumnInfo(name = "temp_max") val tempMax: Double? = 0.0,
    @ColumnInfo(name = "pressure") val pressure: Int? = 0,
    @ColumnInfo(name = "humidity") val humidity: Int? = 0,
    @ColumnInfo(name = "date") val date: String? = "",
    @ColumnInfo(name = "day") val day: String? = "",
)
