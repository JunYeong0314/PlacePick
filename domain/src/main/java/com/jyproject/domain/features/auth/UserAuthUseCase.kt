package com.jyproject.domain.features.auth

import com.jyproject.domain.models.LoginState
import javax.inject.Inject

class UserAuthUseCase @Inject constructor(
    private val userAuthRepository: UserAuthRepository
) {
    operator fun invoke(
        userId: String,
        loginState: (LoginState) -> Unit
    ) = userAuthRepository.checkUser(userId = userId, loginState = loginState)
}