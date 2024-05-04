package com.jyproject.data.di

import android.content.Context
import com.jyproject.data.features.auth.UserDataRepositoryImpl
import com.jyproject.domain.features.auth.repository.UserDataRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataStoreModule {
    @Singleton
    @Provides
    fun provideUserDataRepository(
        @ApplicationContext context: Context
    ): UserDataRepository {
        return UserDataRepositoryImpl(context)
    }
}