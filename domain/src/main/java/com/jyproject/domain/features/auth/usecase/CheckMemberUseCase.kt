package com.jyproject.domain.features.auth.usecase

import com.jyproject.domain.features.auth.repository.AuthRepository
import javax.inject.Inject

class CheckMemberUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {
    suspend operator fun invoke(
        userNum: String
    ) = authRepository.checkMember(userNum)
}