package com.munene.haliyahewa.api.usecases

import com.munene.haliyahewa.api.repositories.favourites.FavoritesRepository
import com.munene.haliyahewa.base.BaseUseCase
import com.munene.haliyahewa.data.db.entities.Favorite
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

typealias GetLocationsBaseUseCase = BaseUseCase<Any, Flow<List<Favorite>>>

class GetFavouritesUseCase @Inject constructor(
    private val favoritesRepository: FavoritesRepository
) : GetLocationsBaseUseCase {

    override suspend fun invoke(params: Any): Flow<List<Favorite>> =
        favoritesRepository.getLocations()
}
