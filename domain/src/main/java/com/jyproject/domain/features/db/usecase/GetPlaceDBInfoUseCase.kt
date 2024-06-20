package com.jyproject.domain.features.db.usecase

import com.jyproject.domain.features.db.repository.PlaceDataRepository
import com.jyproject.domain.models.Place
import javax.inject.Inject

class GetPlaceDBInfoUseCase @Inject constructor(
    private val placeDataRepository: PlaceDataRepository
) {
    suspend operator fun invoke(
        place: String
    ): Place? = placeDataRepository.getPlaceInfo(place)
}