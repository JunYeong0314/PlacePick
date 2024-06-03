package com.jyproject.domain.features.place.usecase

import com.jyproject.domain.features.place.PlaceRepository
import javax.inject.Inject

class SearchPlaceUseCase @Inject constructor(
    private val placeRepository: PlaceRepository
) {
    suspend operator fun invoke(
        placeName: String
    ) = placeRepository.searchPlace(placeName)
}