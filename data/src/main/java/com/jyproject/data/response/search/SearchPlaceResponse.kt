package com.jyproject.data.response.search


import com.squareup.moshi.Json

data class SearchResponse(
    @Json(name = "places")
    val places: List<Place?>?
)

data class Place(
    @Json(name = "AREA_RANGE")
    val placeArea: String?,
    @Json(name = "AREA_NM")
    val place: String?,
    @Json(name = "_id")
    val id: String?,
    @Json(name = "NO")
    val no: Int?
)