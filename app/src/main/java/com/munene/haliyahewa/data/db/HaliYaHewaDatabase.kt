package com.munene.haliyahewa.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.munene.haliyahewa.data.db.dao.FavoritesDao
import com.munene.haliyahewa.data.db.dao.WeatherDao
import com.munene.haliyahewa.data.db.entities.Favorite
import com.munene.haliyahewa.data.db.entities.Forecast
import com.munene.haliyahewa.data.db.entities.Weather

@Database(
    entities = [Weather::class, Forecast::class, Favorite::class],
    version = 1,
    exportSchema = true,
    autoMigrations = []
)
abstract class HaliYaHewaDatabase : RoomDatabase() {
    abstract val weatherDao: WeatherDao
    abstract val favoritesDao: FavoritesDao
}