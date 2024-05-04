package com.jyproject.domain.features.auth.repository

import com.jyproject.domain.models.Platform

interface AuthRepository {
    suspend fun checkMember(userNum: String): Result<Boolean?>
    suspend fun signUp(userNum: String): Result<String?>
}