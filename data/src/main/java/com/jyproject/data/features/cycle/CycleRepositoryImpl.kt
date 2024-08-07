package com.jyproject.data.features.cycle

import com.jyproject.data.mappers.CycleMapper
import com.jyproject.data.remote.service.cycle.GetCycleInfoService
import com.jyproject.domain.features.cycle.CycleRepository
import com.jyproject.domain.models.Cycle
import javax.inject.Inject

class CycleRepositoryImpl @Inject constructor(
    private val cycleInfoService: GetCycleInfoService,
    private val cycleInfoMapper: CycleMapper
): CycleRepository {
    override suspend fun getCycleInfo(regionName: String): Result<Cycle?> {
        return runCatching {
            val result = cycleInfoService.getCycleInfo(regionName)
            result.body()?.let { response ->
                cycleInfoMapper.mapperToCycleInfo(response)
            }
        }
    }
}