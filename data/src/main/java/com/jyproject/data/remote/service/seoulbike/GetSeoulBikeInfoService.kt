package com.jyproject.data.remote.service.seoulbike

import com.jyproject.data.response.seoulbike.SeoulBikeInfoResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface GetSeoulBikeInfoService {
    @GET("/api/v1/seoulbike/search/{regionName}")
    suspend fun getSeoulBikeInfo(
        @Path("regionName") regionName: String
    ): Response<SeoulBikeInfoResponse>
}