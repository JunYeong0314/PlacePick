package com.jyproject.domain.features.db.usecase

import com.jyproject.domain.features.db.repository.PlaceDataRepository
import javax.inject.Inject

class PlaceReadUseCase @Inject constructor(
    private val placeDataRepository: PlaceDataRepository
) {
    operator fun invoke() = placeDataRepository.readPlace()
}