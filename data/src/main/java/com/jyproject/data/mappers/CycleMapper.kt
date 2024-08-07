package com.jyproject.data.mappers

import com.jyproject.data.response.cycle.CycleInfoResponse
import com.jyproject.domain.models.Cycle
import javax.inject.Inject

class CycleMapper @Inject constructor() {
    fun mapperToCycleInfo(cycleInfoResponse: CycleInfoResponse): Cycle {
        return Cycle(
            rentalName = cycleInfoResponse.rentalName,
            region = cycleInfoResponse.region,
            regionDetail = cycleInfoResponse.regionDetail,
            latitude = cycleInfoResponse.latitude,
            longitude = cycleInfoResponse.longitude,
            way = cycleInfoResponse.way
        )
    }
}