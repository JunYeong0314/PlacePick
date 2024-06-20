package com.jyproject.data.features.db

import android.util.Log
import com.jyproject.data.db.PlaceDAO
import com.jyproject.data.db.entitiy.PlaceEntity
import com.jyproject.data.mappers.RoomDataMapper
import com.jyproject.domain.features.db.repository.PlaceDataRepository
import com.jyproject.domain.models.Place
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import javax.inject.Inject

class PlaceDataRepositoryImpl @Inject constructor(
    private val placeDAO: PlaceDAO,
    private val mapper: RoomDataMapper
): PlaceDataRepository {
    override fun readPlace(): Flow<List<Place>> {
        return placeDAO.readPlace()
            .flowOn(Dispatchers.IO)
            .map {
                mapper.mapPlaceEntity(it)
            }
    }

    override suspend fun addPlace(place: String, placeArea: String) {
        withContext(Dispatchers.IO){
            placeDAO.addPlace(PlaceEntity(place = place, placeArea = placeArea))
        }
    }

    override suspend fun deletePlace(place: String) {
        withContext(Dispatchers.IO){
            placeDAO.deletePlace(place)
        }
    }

    override suspend fun findPlace(place: String): Boolean {
        return withContext(Dispatchers.IO){
            placeDAO.findPlace(place) != null
        }
    }

    override suspend fun getPlaceInfo(place: String): Place? {
        return withContext(Dispatchers.IO){
            placeDAO.findPlace(place)?.let { placeEntity ->
                Place(no = null, place = placeEntity.place, placeArea = placeEntity.placeArea)
            }
        }
    }
}