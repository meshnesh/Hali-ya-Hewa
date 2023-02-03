package com.munene.haliyahewa.api.repositories.currentWeather

import com.munene.haliyahewa.BuildConfig
import com.munene.haliyahewa.api.LiveResource
import com.munene.haliyahewa.api.NetworkBoundResource.networkBoundResource
import com.munene.haliyahewa.api.services.WeatherService
import com.munene.haliyahewa.data.db.dao.WeatherDao
import com.munene.haliyahewa.data.db.entities.Forecast
import com.munene.haliyahewa.data.db.entities.Weather
import com.munene.haliyahewa.data.mapper.CurrentForecastMapper
import com.munene.haliyahewa.data.mapper.CurrentWeatherMapper
import com.munene.haliyahewa.data.models.UserLocation
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class WeatherRepositoryImpl @Inject internal constructor(
    private val weatherService: WeatherService,
    private val weatherDao: WeatherDao
) : WeatherRepository {
    override suspend fun getCurrentLocationWeather(userLocation: UserLocation): Flow<LiveResource<Weather?>> {
        val weatherMapper = CurrentWeatherMapper()
        return networkBoundResource(
            query = {
                weatherDao.getLastUpdatedWeather()
            },
            fetch = {
                weatherService.getCurrentLocationWeather(
                    latitude = userLocation.latitude,
                    longitude = userLocation.longitude,
                    key = BuildConfig.OPEN_WEATHER_API_KEY
                )
            },
            saveFetchResponse = { response ->
                weatherDao.deleteWeatherData()
                weatherDao.saveCurrentLocationWeather(
                    weatherMapper.transformToDomain(response)
                )
            }
        )
    }

    override fun getCurrentLocationForecast(userLocation: UserLocation): Flow<LiveResource<List<Forecast>?>> {
        val weatherMapper = CurrentForecastMapper()
        return networkBoundResource(
            query = {
                weatherDao.getLastUpdatedForecast()
            },
            fetch = {
                weatherService.getCurrentLocationForecast(
                    latitude = userLocation.latitude,
                    longitude = userLocation.longitude,
                    key = BuildConfig.OPEN_WEATHER_API_KEY
                )
            },
            saveFetchResponse = { response ->
                weatherDao.deleteForecastData()
                weatherDao.saveCurrentLocationForecast(
                    weatherMapper.transformToDomain(response)
                )
            }
        )
    }
}