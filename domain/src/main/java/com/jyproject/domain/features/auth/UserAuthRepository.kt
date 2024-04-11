package com.jyproject.domain.features.auth

import com.jyproject.domain.models.LoginState

interface UserAuthRepository {
    fun checkUser(userId: String, loginState: (LoginState) -> Unit)
}