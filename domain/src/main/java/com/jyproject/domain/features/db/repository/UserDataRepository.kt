package com.jyproject.domain.features.db.repository

import com.jyproject.domain.models.UserData

interface UserDataRepository {
    suspend fun getUserData(): UserData
    suspend fun setUserData(key: String, value: String)
}