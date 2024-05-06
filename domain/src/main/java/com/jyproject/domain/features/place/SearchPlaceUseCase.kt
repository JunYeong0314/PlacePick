package com.jyproject.domain.features.place

import javax.inject.Inject

class SearchPlaceUseCase @Inject constructor(
    private val placeRepository: PlaceRepository
) {
    suspend operator fun invoke(
        searchPlace: String
    ) = placeRepository.searchPlace(searchPlace)
}