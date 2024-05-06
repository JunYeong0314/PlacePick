package com.jyproject.domain.features.db.usecase

import com.jyproject.domain.features.db.repository.PlaceDataRepository
import javax.inject.Inject

class PlaceDeleteUseCase @Inject constructor(
    private val placeDataRepository: PlaceDataRepository
) {
    suspend operator fun invoke(
        place: String
    ) = placeDataRepository.deletePlace(place)
}