package com.jyproject.data.remote.service.place

import com.jyproject.data.response.search.SearchResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface SearchPlaceService {
    @GET("/api/v1/place/search/{placeName}")
    suspend fun getSearchResult(
        @Path("placeName") placeName: String
    ): Response<SearchResponse>
}