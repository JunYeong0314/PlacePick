package com.jyproject.domain.features.db.repository

import com.jyproject.domain.models.Place
import kotlinx.coroutines.flow.Flow

interface PlaceDataRepository {
    fun readPlace(): Flow<List<Place>>
    suspend fun addPlace(place: String)
    suspend fun deletePlace(place: String)
    suspend fun findPlace(place: String): Boolean
}