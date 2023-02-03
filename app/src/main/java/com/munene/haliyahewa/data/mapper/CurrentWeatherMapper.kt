package com.munene.haliyahewa.data.mapper

import com.munene.haliyahewa.api.model.CurrentWeather
import com.munene.haliyahewa.data.db.entities.Weather

class CurrentWeatherMapper : BaseMapper<CurrentWeather, Weather> {

    override fun transformToDomain(type: CurrentWeather): Weather {
        return Weather(
            weather = type.weather.firstOrNull()?.main,
            weatherIcon = type.weather.firstOrNull()?.icon,
            weatherId = type.weather.firstOrNull()?.id,
            addressName = type.name,
            country = type.sys.country,
            lat = type.coordinates.lat,
            lon = type.coordinates.lon,
            temp = type.info.feelsLike,
            tempMin = type.info.tempMin,
            tempMax = type.info.tempMax,
            pressure = type.info.pressure,
            humidity = type.info.humidity,
            lastUpdate = System.currentTimeMillis()
        )
    }

    override fun transformToDto(type: Weather) = CurrentWeather()
}
