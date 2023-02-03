package com.munene.haliyahewa.api.usecases

import com.munene.haliyahewa.api.repositories.favourites.FavoritesRepository
import com.munene.haliyahewa.base.BaseUseCase
import com.munene.haliyahewa.data.db.entities.Favorite
import javax.inject.Inject

typealias SaveLocationBaseUseCase = BaseUseCase<Favorite, Any>

class SaveFavouriteUseCase @Inject constructor(
    private val favoritesRepository: FavoritesRepository
) : SaveLocationBaseUseCase {

    override suspend fun invoke(params: Favorite) = favoritesRepository.saveLocation(params)
}
