package com.jyproject.data.remote.service.auth

import com.jyproject.data.response.auth.CheckResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface CheckService {
    @GET("/api/auth/check")
    suspend fun getCheck(
        @Query("userNum") userNum: String
    ): Response<CheckResponse>
}