package com.munene.haliyahewa.api.services

import com.munene.haliyahewa.api.model.CurrentForecast
import com.munene.haliyahewa.api.model.CurrentWeather
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherService {

    @GET("/data/2.5/weather")
    suspend fun getCurrentLocationWeather(
        @Query("lat") latitude: Double,
        @Query("lon") longitude: Double,
        @Query("appid") key: String,
        @Query("units") units: String = "metric",
    ): CurrentWeather

    @GET("/data/2.5/forecast")
    suspend fun getCurrentLocationForecast(
        @Query("lat") latitude: Double,
        @Query("lon") longitude: Double,
        @Query("appid") key: String,
        @Query("units") units: String = "metric",
    ): CurrentForecast
}