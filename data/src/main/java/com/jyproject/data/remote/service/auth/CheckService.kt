package com.jyproject.data.remote.service.auth

import com.jyproject.data.response.auth.CheckResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface CheckService {
    @GET("/api/v1/auth/check-existUser/{userNum}")
    suspend fun getCheck(
        @Path("userNum") userNum: String
    ): Response<CheckResponse>
}