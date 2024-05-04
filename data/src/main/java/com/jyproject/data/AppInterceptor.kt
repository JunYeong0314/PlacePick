package com.jyproject.data

import com.jyproject.domain.features.auth.repository.UserDataRepository
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import javax.inject.Inject

class AppInterceptor @Inject constructor(
    private val userDataRepository: UserDataRepository
): Interceptor {
    companion object {
        private const val AUTHORIZATION_HEADER = "Authorization"
        private const val BEARER_TOKEN_PREFIX = "Bearer "
    }

    @Throws
    override fun intercept(chain: Interceptor.Chain): Response {
        val authToken = runBlocking {
            BEARER_TOKEN_PREFIX + userDataRepository.getUserData().token
        }
        val request = chain.request().putTokenHeader(authToken)

        return chain.proceed(request)
    }

    private fun Request.putTokenHeader(token: String): Request {
        return this.newBuilder()
            .addHeader(AUTHORIZATION_HEADER, token)
            .build()
    }
}