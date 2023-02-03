package com.munene.haliyahewa.api.repositories.favourites

import com.munene.haliyahewa.data.db.entities.Favorite
import kotlinx.coroutines.flow.Flow

interface FavoritesRepository {
    fun getLocations(): Flow<List<Favorite>>
    suspend fun saveLocation(params: Favorite)
}
