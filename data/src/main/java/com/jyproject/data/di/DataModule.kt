package com.jyproject.data.di

import com.jyproject.data.features.auth.AuthRepositoryImpl
import com.jyproject.data.features.db.PlaceDataRepositoryImpl
import com.jyproject.data.features.login.KakaoLoginRepositoryImpl
import com.jyproject.data.features.login.NaverLoginRepositoryImpl
import com.jyproject.data.features.place.PlaceRepositoryImpl
import com.jyproject.data.features.seoulbike.SeoulBikeRepositoryImpl
import com.jyproject.domain.features.auth.repository.AuthRepository
import com.jyproject.domain.features.db.repository.PlaceDataRepository
import com.jyproject.domain.features.login.repository.KakaoLoginRepository
import com.jyproject.domain.features.login.repository.NaverLoginRepository
import com.jyproject.domain.features.place.PlaceRepository
import com.jyproject.domain.features.seoulbike.SeoulBikeRepository
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

    // User Check
    @Binds
    abstract fun bindAuthRepository(authRepositoryImpl: AuthRepositoryImpl): AuthRepository

    // Search Place
    @Binds
    abstract fun bindPlaceRepository(placeRepositoryImpl: PlaceRepositoryImpl): PlaceRepository

    // Place RoomDB
    @Binds
    abstract fun bindPlaceDataRepository(placeDataRepositoryImpl: PlaceDataRepositoryImpl): PlaceDataRepository

    // SeoulBike
    @Binds
    abstract fun bindSeoulBikeRepository(seoulBikeRepositoryImpl: SeoulBikeRepositoryImpl): SeoulBikeRepository
}