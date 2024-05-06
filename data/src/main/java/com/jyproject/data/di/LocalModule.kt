package com.jyproject.data.di

import android.content.Context
import androidx.room.Room
import com.jyproject.data.db.PlaceDAO
import com.jyproject.data.db.PlaceDB
import com.jyproject.data.features.db.PlaceDataRepositoryImpl
import com.jyproject.data.features.db.UserDataRepositoryImpl
import com.jyproject.data.mappers.RoomDataMapper
import com.jyproject.domain.features.db.repository.PlaceDataRepository
import com.jyproject.domain.features.db.repository.UserDataRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object LocalModule {
    @Singleton
    @Provides
    fun provideUserDataRepository(
        @ApplicationContext context: Context
    ): UserDataRepository {
        return UserDataRepositoryImpl(context)
    }

    @Provides
    @Singleton
    fun providePlaceDB(
        @ApplicationContext context: Context
    ): PlaceDB{
        return Room.databaseBuilder(
            context,
            PlaceDB::class.java,
            "place_db"
        ).build()
    }

    @Singleton
    @Provides
    fun providePlaceDAO(placeDB: PlaceDB): PlaceDAO{
        return placeDB.placeDAO()
    }
}