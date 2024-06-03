package com.jyproject.domain.features.db.usecase

import com.jyproject.domain.features.db.repository.PlaceDataRepository
import javax.inject.Inject

class PlaceAddUseCase @Inject constructor(
    private val placeDataRepository: PlaceDataRepository
) {
    suspend operator fun invoke(
        place: String,
        placeArea: String
    ) = placeDataRepository.addPlace(place, placeArea)
}