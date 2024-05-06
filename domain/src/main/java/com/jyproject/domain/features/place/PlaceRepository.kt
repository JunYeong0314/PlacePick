package com.jyproject.domain.features.place

import com.jyproject.domain.models.Place

interface PlaceRepository {
    suspend fun searchPlace(searchPlace: String): Result<List<Place>?>
}