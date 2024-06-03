package com.jyproject.data.remote.service.place

import com.jyproject.data.response.search.SearchResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface SearchPlaceService {
    @GET("/api/place/search")
    suspend fun getSearchResult(
        @Query("placeName") placeName: String
    ): Response<SearchResponse>
}