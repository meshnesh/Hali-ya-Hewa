package com.munene.haliyahewa.di.module

import com.munene.haliyahewa.api.repositories.currentWeather.WeatherRepository
import com.munene.haliyahewa.api.repositories.currentWeather.WeatherRepositoryImpl
import com.munene.haliyahewa.api.repositories.favourites.FavoritesRepository
import com.munene.haliyahewa.api.repositories.favourites.FavoritesRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindWeatherRepository(weatherRepositoryImpl: WeatherRepositoryImpl): WeatherRepository

    @Binds
    @Singleton
    abstract fun bindFavoritesRepository(favoritesRepositoryImpl: FavoritesRepositoryImpl): FavoritesRepository
}
