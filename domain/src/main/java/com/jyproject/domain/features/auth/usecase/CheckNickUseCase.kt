package com.jyproject.domain.features.auth.usecase

import com.jyproject.domain.features.auth.repository.AuthRepository
import javax.inject.Inject

class CheckNickUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {
    suspend operator fun invoke(
        nick: String
    ) = authRepository.checkNick(nick)
}