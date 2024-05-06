package com.jyproject.data.features.place

import com.jyproject.data.remote.service.place.SearchPlaceService
import com.jyproject.data.response.search.SearchResponse
import com.jyproject.domain.features.place.PlaceRepository
import com.jyproject.domain.models.Place
import javax.inject.Inject

class PlaceRepositoryImpl @Inject constructor(
    private val searchPlaceService: SearchPlaceService
): PlaceRepository {
    override suspend fun searchPlace(searchPlace: String): Result<List<Place>?> {
        return runCatching {
            val result = searchPlaceService.getSearchResult(searchPlace)
            result.body()?.let { response ->
                mapperToPlaces(response)
            }
        }
    }

    private fun mapperToPlaces(searchResponse: SearchResponse): List<Place>?{
        return searchResponse.places?.map {
            Place(
                no = it?.no,
                place = it?.place
            )
        }
    }
}