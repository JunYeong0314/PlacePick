package com.jyproject.data.remote.service.auth

import com.jyproject.data.request.auth.SignUpRequest
import com.jyproject.data.response.auth.SignUpResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface SignUpService {
    @POST("/api/auth/signup")
    suspend fun signUp(
        @Body signUpRequest: SignUpRequest
    ): Response<SignUpResponse>
}