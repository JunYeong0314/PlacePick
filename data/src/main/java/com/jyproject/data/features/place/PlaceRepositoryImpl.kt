package com.jyproject.data.features.place

import android.util.Log
import com.jyproject.data.mappers.PlaceDataMapper
import com.jyproject.data.remote.service.place.GetPlaceInfoService
import com.jyproject.data.remote.service.place.SearchPlaceService
import com.jyproject.domain.features.place.PlaceRepository
import com.jyproject.domain.models.Place
import com.jyproject.domain.models.PlaceInfo
import javax.inject.Inject

class PlaceRepositoryImpl @Inject constructor(
    private val searchPlaceService: SearchPlaceService,
    private val getPlaceInfoService: GetPlaceInfoService,
    private val placeDataMapper: PlaceDataMapper
): PlaceRepository {
    override suspend fun searchPlace(placeName: String): Result<List<Place>?> {
        return runCatching {
            val result = searchPlaceService.getSearchResult(placeName)
            result.body()?.let { response ->
                placeDataMapper.mapperToPlaces(response)
            }
        }
    }

    override suspend fun getPlaceInfo(place: String): Result<PlaceInfo?> {
        return runCatching {
            val result = getPlaceInfoService.getPlaceInfo(place)
            result.body()?.let { response->
                placeDataMapper.mapperToPlaceInfo(response.placeInfoData?.firstOrNull())
            }
        }
    }
}