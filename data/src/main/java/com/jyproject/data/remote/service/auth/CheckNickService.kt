package com.jyproject.data.remote.service.auth

import com.jyproject.data.response.auth.CheckNickResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface CheckNickService {
    @GET("/api/v1/auth/check-nickname/{userNum}")
    suspend fun getCheckNick(
        @Path("nick") nick: String
    ): Response<CheckNickResponse>
}