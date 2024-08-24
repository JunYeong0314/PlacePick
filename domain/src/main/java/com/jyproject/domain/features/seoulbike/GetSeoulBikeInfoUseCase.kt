package com.jyproject.domain.features.seoulbike

import javax.inject.Inject

class GetSeoulBikeInfoUseCase @Inject constructor(
    private val seoulBikeRepository: SeoulBikeRepository
) {
    suspend operator fun invoke(
        regionName: String
    ) = seoulBikeRepository.getCycleInfo(regionName = regionName)
}