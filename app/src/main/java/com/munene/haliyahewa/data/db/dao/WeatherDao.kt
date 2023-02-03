package com.munene.haliyahewa.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.munene.haliyahewa.data.db.entities.Forecast
import com.munene.haliyahewa.data.db.entities.Weather
import kotlinx.coroutines.flow.Flow

@Dao
interface WeatherDao {

    @Query("SELECT * FROM current_weather ORDER BY id DESC LIMIT 1")
    fun getLastUpdatedWeather(): Flow<Weather>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveCurrentLocationWeather(weather: Weather)

    @Query("DELETE FROM current_weather")
    suspend fun deleteWeatherData()

    @Query("SELECT * FROM current_forecast GROUP BY day ORDER BY id ASC")
    fun getLastUpdatedForecast(): Flow<List<Forecast>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveCurrentLocationForecast(forecasts: List<Forecast>)

    @Query("DELETE FROM current_forecast")
    suspend fun deleteForecastData()
}
