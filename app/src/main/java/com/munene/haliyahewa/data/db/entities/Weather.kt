package com.munene.haliyahewa.data.db.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "current_weather")
data class Weather(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id") var id: Int? = null,
    @ColumnInfo(name = "weather_id") var weatherId: Int? = null,
    @ColumnInfo(name = "weather") var weather: String? = null,
    @ColumnInfo(name = "weather_icon") var weatherIcon: String? = null,
    @ColumnInfo(name = "address_name") var addressName: String? = null,
    @ColumnInfo(name = "country") var country: String? = null,
    @ColumnInfo(name = "lat") val lat: Double? = null,
    @ColumnInfo(name = "lon") val lon: Double? = null,
    @ColumnInfo(name = "temp") val temp: Double? = null,
    @ColumnInfo(name = "temp_min") val tempMin: Double? = null,
    @ColumnInfo(name = "temp_max") val tempMax: Double? = null,
    @ColumnInfo(name = "pressure") val pressure: Int? = null,
    @ColumnInfo(name = "humidity") val humidity: Int? = null,
    @ColumnInfo(name = "last_update") val lastUpdate: Long? = null,
)
