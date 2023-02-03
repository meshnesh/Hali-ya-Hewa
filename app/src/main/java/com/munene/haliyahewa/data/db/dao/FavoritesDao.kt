package com.munene.haliyahewa.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.munene.haliyahewa.data.db.entities.Favorite
import kotlinx.coroutines.flow.Flow

@Dao
interface FavoritesDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveLocation(favorite: Favorite)

    @Query("SELECT * FROM favorites")
    fun getLocations(): Flow<List<Favorite>>
}
