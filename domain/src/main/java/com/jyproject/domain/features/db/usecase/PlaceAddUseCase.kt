package com.jyproject.domain.features.db.usecase

import com.jyproject.domain.features.db.repository.PlaceDataRepository
import javax.inject.Inject

class PlaceAddUseCase @Inject constructor(
    private val placeDataRepository: PlaceDataRepository
) {
    suspend operator fun invoke(
        no: Int,
        place: String
    ) = placeDataRepository.addPlace(no, place)
}