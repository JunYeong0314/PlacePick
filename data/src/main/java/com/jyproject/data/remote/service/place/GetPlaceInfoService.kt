package com.jyproject.data.remote.service.place

import com.jyproject.data.BuildConfig
import com.jyproject.data.response.place.PlaceInfoResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

private const val API_KEY = BuildConfig.SEOUL_API_KEY

interface GetPlaceInfoService {
    @GET("/$API_KEY/json/citydata_ppltn/1/10/{place}")
    suspend fun getPlaceInfo(
        @Path("place") place: String
    ): Response<PlaceInfoResponse>
}