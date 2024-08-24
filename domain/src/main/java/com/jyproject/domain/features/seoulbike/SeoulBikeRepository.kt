package com.jyproject.domain.features.seoulbike

import com.jyproject.domain.models.SeoulBike

interface SeoulBikeRepository {
    suspend fun getCycleInfo(regionName: String): Result<List<SeoulBike>?>
}