package com.jyproject.domain.features.place.usecase

import com.jyproject.domain.features.place.PlaceRepository
import javax.inject.Inject

class GetPlaceInfoUseCase @Inject constructor(
    private val placeRepository: PlaceRepository
) {
    suspend operator fun invoke(
        place: String
    ) = placeRepository.getPlaceInfo(place)
}