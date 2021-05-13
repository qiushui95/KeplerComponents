package com.kepler.module.model.room.eneity.update

import androidx.room.*

@Entity(tableName = "UpdateLog")
internal data class UpdateLogEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val id: Int = 0,
    @ColumnInfo(name = "updateId")
    val updateId: String,
    @ColumnInfo(name = "desc")
    val desc: String,
    @ColumnInfo(name = "currentVersion")
    val currentVersion: String,
    @ColumnInfo(name = "targetVersion")
    val targetVersion: String,
    @ColumnInfo(name = "status")
    val status: String
)
