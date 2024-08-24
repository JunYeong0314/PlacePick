package com.jyproject.data.mappers

import com.jyproject.data.response.seoulbike.SeoulBikeInfoResponse
import com.jyproject.domain.models.SeoulBike
import javax.inject.Inject

class SeoulBikeMapper @Inject constructor() {
    fun mapperToSeoulBike(seoulBikeInfoResponse: SeoulBikeInfoResponse): List<SeoulBike>? {
        return seoulBikeInfoResponse.regions?.map {
            SeoulBike(
                rentalName = it?.rentalName,
                region = it?.region,
                regionDetail = it?.regionDetail,
                latitude = it?.latitude,
                longitude = it?.longitude,
                way = it?.way
            )
        }
    }
}