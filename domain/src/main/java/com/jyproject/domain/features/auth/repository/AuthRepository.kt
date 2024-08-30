package com.jyproject.domain.features.auth.repository

interface AuthRepository {
    suspend fun checkMember(userNum: String): Result<Boolean?>
    suspend fun register(userNum: String, nick: String): Result<String?>
    suspend fun checkNick(nick: String): Result<Int?>
}