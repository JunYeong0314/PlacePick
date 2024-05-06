package com.jyproject.data.mappers

import com.jyproject.data.db.entitiy.PlaceEntity
import com.jyproject.domain.models.Place
import javax.inject.Inject

class RoomDataMapper @Inject constructor() {
    fun mapPlaceEntity(placeList: List<PlaceEntity>): List<Place>{
        return placeList.map { entity->
            Place(
                no = entity.no,
                place = entity.place
            )
        }
    }
}