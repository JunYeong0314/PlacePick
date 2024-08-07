package com.jyproject.data.response.cycle


import com.squareup.moshi.Json

data class CycleInfoResponse(
    @Json(name = "latitude")
    val latitude: Double?,
    @Json(name = "longitude")
    val longitude: Double?,
    @Json(name = "regionDetail")
    val regionDetail: String?,
    @Json(name = "region")
    val region: String?,
    @Json(name = "rentalName")
    val rentalName: String?,
    @Json(name = "way")
    val way: String?
)