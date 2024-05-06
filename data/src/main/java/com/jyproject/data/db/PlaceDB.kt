package com.jyproject.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.jyproject.data.db.entitiy.PlaceEntity

@Database(entities = [PlaceEntity::class], version = 1, exportSchema = false)
abstract class PlaceDB: RoomDatabase() {
    abstract fun placeDAO(): PlaceDAO
}