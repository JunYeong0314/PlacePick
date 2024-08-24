package com.jyproject.domain.features.auth.repository

interface AuthRepository {
    suspend fun checkMember(userNum: String): Result<Boolean?>
    suspend fun signUp(userNum: String, nick: String): Result<String?>
}