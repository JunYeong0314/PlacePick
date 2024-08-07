package com.jyproject.domain.features.cycle

import com.jyproject.domain.models.Cycle

interface CycleRepository {
    suspend fun getCycleInfo(regionName: String): Result<Cycle?>
}