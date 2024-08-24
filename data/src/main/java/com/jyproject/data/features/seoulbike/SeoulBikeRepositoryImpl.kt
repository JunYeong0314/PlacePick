package com.jyproject.data.features.seoulbike

import com.jyproject.data.mappers.SeoulBikeMapper
import com.jyproject.data.remote.service.seoulbike.GetSeoulBikeInfoService
import com.jyproject.domain.features.seoulbike.SeoulBikeRepository
import com.jyproject.domain.models.SeoulBike
import javax.inject.Inject

class SeoulBikeRepositoryImpl @Inject constructor(
    private val seoulBikeInfoService: GetSeoulBikeInfoService,
    private val seoulBikeMapper: SeoulBikeMapper
): SeoulBikeRepository {
    override suspend fun getCycleInfo(regionName: String): Result<List<SeoulBike>?> {
        return runCatching {
            val result = seoulBikeInfoService.getSeoulBikeInfo(regionName).body()

            result?.let {
                seoulBikeMapper.mapperToSeoulBike(it)
            }
        }
    }
}