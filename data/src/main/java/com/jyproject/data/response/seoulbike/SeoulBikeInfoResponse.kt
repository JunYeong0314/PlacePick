package com.jyproject.data.response.seoulbike


import com.squareup.moshi.Json

data class SeoulBikeInfoResponse(
    @Json(name = "regions")
    val regions: List<Region?>?
)

data class Region(
    @Json(name = "_id")
    val id: String?,
    @Json(name = "latitude")
    val latitude: Double?,
    @Json(name = "longitude")
    val longitude: Double?,
    @Json(name = "region")
    val region: String?,
    @Json(name = "regionDetail")
    val regionDetail: String?,
    @Json(name = "rentalName")
    val rentalName: String?,
    @Json(name = "way")
    val way: String?
)