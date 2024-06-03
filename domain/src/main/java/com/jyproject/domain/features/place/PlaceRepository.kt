package com.jyproject.domain.features.place

import com.jyproject.domain.models.Place
import com.jyproject.domain.models.PlaceInfo

interface PlaceRepository {
    suspend fun searchPlace(placeName: String): Result<List<Place>?>

    suspend fun getPlaceInfo(place: String): Result<PlaceInfo?>

}