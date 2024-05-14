package com.jyproject.domain.features.db.usecase

import com.jyproject.domain.features.db.repository.PlaceDataRepository
import javax.inject.Inject

class PlaceFindUseCase @Inject constructor(
    private val placeDataRepository: PlaceDataRepository
) {
    suspend operator fun invoke(
        place: String
    ): Boolean = placeDataRepository.findPlace(place)
}