package com.jyproject.data.db.entitiy

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "place_table")
data class PlaceEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "no")
    val no: Int = 0,
    @ColumnInfo(name = "place")
    val place: String
)