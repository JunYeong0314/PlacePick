package com.jyproject.data.db.entitiy

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "place_table")
data class PlaceEntity(
    @PrimaryKey
    @ColumnInfo
    val no: Int,
    @ColumnInfo(name = "place")
    val place: String
)