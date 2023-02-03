package com.munene.haliyahewa.di.module

import android.content.Context
import androidx.room.Room
import com.munene.haliyahewa.data.db.HaliYaHewaDatabase
import com.munene.haliyahewa.data.db.dao.FavoritesDao
import com.munene.haliyahewa.data.db.dao.WeatherDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext context: Context): HaliYaHewaDatabase {
        return Room.databaseBuilder(context, HaliYaHewaDatabase::class.java, "hali_ya_hewa.db")
            .build()
    }

    @Singleton
    @Provides
    fun provideWeatherDao(database: HaliYaHewaDatabase): WeatherDao {
        return database.weatherDao
    }

    @Singleton
    @Provides
    fun provideFavoritesDao(database: HaliYaHewaDatabase): FavoritesDao {
        return database.favoritesDao
    }
}
