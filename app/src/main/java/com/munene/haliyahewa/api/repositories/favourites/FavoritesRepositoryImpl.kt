package com.munene.haliyahewa.api.repositories.favourites

import com.munene.haliyahewa.data.db.dao.FavoritesDao
import com.munene.haliyahewa.data.db.entities.Favorite
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class FavoritesRepositoryImpl @Inject constructor(
    private val favoritesDao: FavoritesDao
) : FavoritesRepository {

    override fun getLocations(): Flow<List<Favorite>> {
        return favoritesDao.getLocations()
    }

    override suspend fun saveLocation(params: Favorite) {
        return favoritesDao.saveLocation(params)
    }
}
