package com.jyproject.data.remote.service.auth

import com.jyproject.data.request.auth.RegisterRequest
import com.jyproject.data.response.auth.RegisterResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface RegisterService {
    @POST("/api/v1/auth/register")
    suspend fun register(
        @Body registerRequest: RegisterRequest
    ): Response<RegisterResponse>
}