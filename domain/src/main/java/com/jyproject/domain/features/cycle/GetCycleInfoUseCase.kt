package com.jyproject.domain.features.cycle

import javax.inject.Inject

class GetCycleInfoUseCase @Inject constructor(
    private val cycleRepository: CycleRepository
) {
    suspend operator fun invoke(
        regionName: String
    ) = cycleRepository.getCycleInfo(regionName = regionName)
}