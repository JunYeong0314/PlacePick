package com.jyproject.data.features.auth

import com.jyproject.data.remote.service.auth.CheckNickService
import com.jyproject.data.remote.service.auth.CheckService
import com.jyproject.data.remote.service.auth.RegisterService
import com.jyproject.data.request.auth.RegisterRequest
import com.jyproject.domain.features.auth.repository.AuthRepository
import com.jyproject.domain.features.db.repository.UserDataRepository
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val signUpService: RegisterService,
    private val checkService: CheckService,
    private val checkNickService: CheckNickService,
    private val userDataRepository: UserDataRepository
): AuthRepository {
    override suspend fun checkMember(userNum: String): Result<Boolean?> {
        return runCatching {
            val result = checkService.getCheck(userNum = userNum).body()?.response

            result?.let {
                val token = it.token
                if (!token.isNullOrBlank()) {
                    userDataRepository.setUserData("token", token)
                }
                it.exists
            }
        }
    }

    override suspend fun register(userNum: String, nick: String): Result<String?> {
        val num = RegisterRequest(userNum = userNum, nick = nick)

        return runCatching {
            signUpService.register(num).body()?.token
        }
    }

    override suspend fun checkNick(nick: String): Result<Int?> {
        return runCatching {
            checkNickService.getCheckNick(nick).code()
        }
    }
}