package com.jyproject.data.features.auth

import android.util.Log
import com.jyproject.data.remote.service.auth.CheckService
import com.jyproject.data.remote.service.auth.SignUpService
import com.jyproject.data.request.auth.SignUpRequest
import com.jyproject.domain.features.auth.repository.AuthRepository
import com.jyproject.domain.features.db.repository.UserDataRepository
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val signUpService: SignUpService,
    private val checkService: CheckService,
    private val userDataRepository: UserDataRepository
): AuthRepository {
    override suspend fun checkMember(userNum: String): Result<Boolean?> {
        return runCatching {
            val result = checkService.getCheck(userNum).body()

            result?.let {
                val token = it.token
                if(!token.isNullOrBlank()) userDataRepository.setUserData("token", token) // 유효기간이 만료된 토큰은 자동갱신
                it.exists
            }
        }
    }

    override suspend fun signUp(userNum: String): Result<String?> {
        val num = SignUpRequest(userNum = userNum)

        return runCatching {
            signUpService.signUp(num).body()?.token
        }
    }
}