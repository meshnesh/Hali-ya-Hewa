package com.munene.haliyahewa.data.mapper

import com.munene.haliyahewa.api.model.CurrentForecast
import com.munene.haliyahewa.data.db.entities.Forecast
import java.text.SimpleDateFormat
import java.util.*

class CurrentForecastMapper : BaseMapper<CurrentForecast, List<Forecast>> {

    override fun transformToDomain(type: CurrentForecast): List<Forecast> {
        val forecasts = arrayListOf<Forecast>()
        for (forecastDay in type.days) {
            val forecast = Forecast(
                country = type.city.country,
                lat = type.city.coord.lat,
                lon = type.city.coord.lon,
                weather = forecastDay.weather[0].main,
                weatherId = forecastDay.weather[0].id,
                weatherIcon = forecastDay.weather[0].icon,
                temp = forecastDay.info.feelsLike,
                tempMin = forecastDay.info.tempMin,
                tempMax = forecastDay.info.tempMax,
                pressure = forecastDay.info.pressure,
                humidity = forecastDay.info.humidity,
                date = forecastDay.date,
                day = getDayOfWeek(forecastDay.dateTimestamp)
            )
            forecasts.add(forecast)
        }
        return forecasts
    }

    private fun getDayOfWeek(dateTimestamp: Int): String? {
        return SimpleDateFormat("EEEE", Locale.ENGLISH).format(dateTimestamp * 1000)
    }

    override fun transformToDto(type: List<Forecast>): CurrentForecast = CurrentForecast()
}
