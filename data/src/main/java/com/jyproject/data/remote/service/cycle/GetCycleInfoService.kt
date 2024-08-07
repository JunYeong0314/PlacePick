package com.jyproject.data.remote.service.cycle

import com.jyproject.data.response.cycle.CycleInfoResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface GetCycleInfoService {
    @GET("/api/cycle/search")
    suspend fun getCycleInfo(
        @Query("regionName") regionName: String
    ): Response<CycleInfoResponse>
}