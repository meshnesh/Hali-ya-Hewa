package com.munene.haliyahewa.api.repositories.currentWeather

import com.munene.haliyahewa.api.LiveResource
import com.munene.haliyahewa.data.db.entities.Forecast
import com.munene.haliyahewa.data.db.entities.Weather
import com.munene.haliyahewa.data.models.UserLocation
import kotlinx.coroutines.flow.Flow

interface WeatherRepository {
    suspend fun getCurrentLocationWeather(userLocation: UserLocation): Flow<LiveResource<Weather?>>
    fun getCurrentLocationForecast(userLocation: UserLocation): Flow<LiveResource<List<Forecast>?>>
}