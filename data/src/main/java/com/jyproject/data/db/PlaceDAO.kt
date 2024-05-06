package com.jyproject.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.jyproject.data.db.entitiy.PlaceEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface PlaceDAO {
    @Query("SELECT * FROM place_table")
    fun readPlace(): Flow<List<PlaceEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addPlace(placeEntity: PlaceEntity)

    @Query("DELETE FROM place_table WHERE place = :place")
    suspend fun deletePlace(place: String)

    @Query("SELECT * FROM place_table WHERE place = :place")
    suspend fun findPlace(place: String): PlaceEntity?
}