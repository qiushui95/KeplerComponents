package com.kepler.component.update.model.room.entity

import androidx.room.*

@Entity(
    tableName = "IgnoreRecord",
    indices = [Index("updateId")]
)
internal data class IgnoreRecordEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val id: Int,
    @ColumnInfo(name = "updateId")
    val updateId: String,
    @ColumnInfo(name = "time")
    val time: Long
)
