package com.munene.haliyahewa.ui.weather

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.munene.haliyahewa.api.LiveResource
import com.munene.haliyahewa.api.usecases.LocationForecastUseCase
import com.munene.haliyahewa.api.usecases.LocationWeatherUseCase
import com.munene.haliyahewa.base.BaseViewModel
import com.munene.haliyahewa.data.db.entities.Forecast
import com.munene.haliyahewa.data.db.entities.Weather
import com.munene.haliyahewa.data.models.UserLocation
import com.munene.haliyahewa.util.LocationLiveData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WeatherViewModel @Inject constructor(
    private val locationWeatherUseCase: LocationWeatherUseCase,
    private val locationForecastUseCase: LocationForecastUseCase
) : BaseViewModel() {

    @Inject
    lateinit var locationLiveData: LocationLiveData
    fun fetchLocationLiveData() = locationLiveData

    private val _currentWeather = MutableLiveData<Weather?>()
    val currentWeather: LiveData<Weather?> = _currentWeather

    private val _forecast = MutableLiveData<List<Forecast>?>()
    val forecast: LiveData<List<Forecast>?> = _forecast

    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> = _loading

    fun getCurrentLocationWeather(currentLocation: UserLocation) {
        viewModelScope.launch {
            locationWeatherUseCase.invoke(currentLocation).collect { state ->
                when (state) {
                    is LiveResource.Loading -> {
                        _loading.value = true
                        if (state.data != null) {
                            _currentWeather.value = state.data
                        }
                    }
                    is LiveResource.Success -> {
                        _loading.value = false
                        if (state.data != null) {
                            _currentWeather.value = state.data
                        }
                    }
                    is LiveResource.Error -> {
                        _loading.value = false
                    }
                }
            }
        }
        getCurrentLocationForecast(currentLocation)
    }

    fun getCurrentLocationForecast(currentLocation: UserLocation) {
        viewModelScope.launch {
            locationForecastUseCase.invoke(currentLocation).collect { state ->
                when (state) {
                    is LiveResource.Loading -> {
                        _loading.value = true
                        if (state.data != null) {
                            _forecast.value = state.data
                        }
                    }
                    is LiveResource.Success -> {
                        _loading.value = false
                        if (state.data != null) {
                            _forecast.value = state.data
                        } else {
                            _forecast.value = listOf()
                        }
                    }
                    is LiveResource.Error -> {
                        _loading.value = false
                    }
                }
            }
        }
    }

    suspend fun getFavoriteLocationWeather(savedLocation: UserLocation): Flow<LiveResource<Weather?>> {
        return locationWeatherUseCase.invoke(savedLocation)
    }
}
