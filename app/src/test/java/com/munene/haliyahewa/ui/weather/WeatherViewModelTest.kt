package com.munene.haliyahewa.ui.weather

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.dvttest.hiweather.testutils.getOrAwaitValue
import com.google.common.truth.Truth.assertThat
import com.munene.haliyahewa.api.LiveResource
import com.munene.haliyahewa.api.usecases.LocationForecastUseCase
import com.munene.haliyahewa.api.usecases.LocationWeatherUseCase
import com.munene.haliyahewa.data.db.entities.Forecast
import com.munene.haliyahewa.data.db.entities.Weather
import com.munene.haliyahewa.fakeForecast
import com.munene.haliyahewa.fakeUserLocation
import com.munene.haliyahewa.fakeWeather
import com.munene.haliyahewa.testutils.CoroutinesTestRule
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runBlockingTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
internal class WeatherViewModelTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    var coroutinesTestRule = CoroutinesTestRule()

    private val currentWeatherObserver = mockk<Observer<Weather?>>(relaxed = true)
    private val forecastObserver = mockk<Observer<List<Forecast>?>>(relaxed = true)

    private lateinit var locationWeatherUseCase: LocationWeatherUseCase
    private lateinit var locationForecastUseCase: LocationForecastUseCase
    private lateinit var viewModel: WeatherViewModel
    private lateinit var fakeForecastList :ArrayList<Forecast>

    @Before
    fun setUp() = coroutinesTestRule.testDispatcher.runBlockingTest {

        locationWeatherUseCase = mockk()
        coEvery { locationWeatherUseCase.invoke(any()) } returns flow {
            emit(LiveResource.loading(null))
            emit(LiveResource.success(fakeWeather))
        }

        fakeForecastList = arrayListOf()
        fakeForecastList.add(fakeForecast)
        fakeForecastList.add(fakeForecast)
        fakeForecastList.add(fakeForecast)
        fakeForecastList.add(fakeForecast)
        fakeForecastList.add(fakeForecast)

        locationForecastUseCase = mockk()
        coEvery { locationForecastUseCase.invoke(any()) } returns flow {
            emit(LiveResource.loading(null))
            emit(LiveResource.success(fakeForecastList))
        }

        viewModel = WeatherViewModel(
            locationWeatherUseCase,
            locationForecastUseCase
        )
    }

    @Test
    fun `assert getCurrentLocationWeather returns location weather from repository successfully`() {
        viewModel.currentWeather.observeForever(currentWeatherObserver)
        viewModel.getCurrentLocationWeather(fakeUserLocation)

        coVerify(exactly = 1) { locationWeatherUseCase.invoke(fakeUserLocation) }

        assertThat(viewModel.currentWeather.getOrAwaitValue()).isEqualTo(fakeWeather)
        assertThat(viewModel.loading.getOrAwaitValue()).isEqualTo(false)
    }

    @Test
    fun `assert getCurrentLocationForecast returns location forecast list of 5 days from repository`() {
        viewModel.forecast.observeForever(forecastObserver)
        viewModel.getCurrentLocationForecast(fakeUserLocation)

        coVerify(exactly = 1) { locationForecastUseCase.invoke(fakeUserLocation) }

        assertThat(viewModel.forecast.getOrAwaitValue()).isEqualTo(fakeForecastList)
        assertThat(viewModel.forecast.value?.size).isEqualTo(5)
        assertThat(viewModel.loading.getOrAwaitValue()).isEqualTo(false)
    }

    @After
    fun tearDown() {
        viewModel.currentWeather.removeObserver(currentWeatherObserver)
        viewModel.forecast.removeObserver(forecastObserver)
    }
}