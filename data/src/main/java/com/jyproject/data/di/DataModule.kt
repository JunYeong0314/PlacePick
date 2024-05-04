package com.jyproject.data.di

import com.jyproject.data.features.auth.AuthRepositoryImpl
import com.jyproject.data.features.auth.UserAuthRepositoryImpl
import com.jyproject.data.features.login.KakaoLoginRepositoryImpl
import com.jyproject.data.features.login.NaverLoginRepositoryImpl
import com.jyproject.domain.features.auth.repository.AuthRepository
import com.jyproject.domain.features.auth.UserAuthRepository
import com.jyproject.domain.features.login.repository.KakaoLoginRepository
import com.jyproject.domain.features.login.repository.NaverLoginRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class DataModule {
    // Naver Login
    @Binds
    abstract fun bindNaverLoginRepository(naverLoginRepositoryImpl: NaverLoginRepositoryImpl): NaverLoginRepository

    // Kakao Login
    @Binds
    abstract fun bindKakaoLoginRepository(kakaoLoginRepositoryImpl: KakaoLoginRepositoryImpl): KakaoLoginRepository

    // User Auth
    @Binds
    abstract fun bindUserAuthRepository(userAuthRepositoryImpl: UserAuthRepositoryImpl): UserAuthRepository

    // User Check
    @Binds
    abstract fun bindAuthRepository(authRepositoryImpl: AuthRepositoryImpl): AuthRepository
}