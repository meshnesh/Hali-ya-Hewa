package com.munene.haliyahewa.api.usecases

import com.munene.haliyahewa.api.LiveResource
import com.munene.haliyahewa.api.repositories.currentWeather.WeatherRepository
import com.munene.haliyahewa.base.BaseUseCase
import com.munene.haliyahewa.data.db.entities.Weather
import com.munene.haliyahewa.data.models.UserLocation
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

typealias LocationWeatherBaseUseCase = BaseUseCase<UserLocation, Flow<LiveResource<Weather?>>>

class LocationWeatherUseCase @Inject constructor(
    private val weatherRepository: WeatherRepository
) : LocationWeatherBaseUseCase {

    override suspend fun invoke(params: UserLocation) =
        weatherRepository.getCurrentLocationWeather(params)
}