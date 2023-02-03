package com.munene.haliyahewa.api.usecases

import com.munene.haliyahewa.api.LiveResource
import com.munene.haliyahewa.api.repositories.currentWeather.WeatherRepository
import com.munene.haliyahewa.base.BaseUseCase
import com.munene.haliyahewa.data.db.entities.Forecast
import com.munene.haliyahewa.data.models.UserLocation
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

typealias LocationForecastBaseUseCase = BaseUseCase<UserLocation, Flow<LiveResource<List<Forecast>?>>>

class LocationForecastUseCase @Inject constructor(
    private val weatherRepository: WeatherRepository
) : LocationForecastBaseUseCase {

    override suspend fun invoke(params: UserLocation) =
        weatherRepository.getCurrentLocationForecast(params)
}
