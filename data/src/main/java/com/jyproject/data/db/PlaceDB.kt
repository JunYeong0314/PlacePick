package com.jyproject.data.db

import androidx.room.AutoMigration
import androidx.room.Database
import androidx.room.RoomDatabase
import com.jyproject.data.db.entitiy.PlaceEntity

@Database(
    entities = [PlaceEntity::class],
    version = 3,
    exportSchema = true,
    autoMigrations = [AutoMigration (from = 2, to = 3)]
)
abstract class PlaceDB: RoomDatabase() {
    abstract fun placeDAO(): PlaceDAO
}