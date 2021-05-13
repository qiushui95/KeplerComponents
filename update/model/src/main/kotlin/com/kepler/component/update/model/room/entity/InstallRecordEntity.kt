package com.kepler.component.update.model.room.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "InstallRecord",
    indices = [Index("updateId")]
)
internal data class InstallRecordEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val id: Int,
    @ColumnInfo(name = "updateId")
    val updateId: String,
    @ColumnInfo(name = "time")
    val time: Long,
    @ColumnInfo(name = "currentVersion")
    val currentVersion: String,
    @ColumnInfo(name = "targetVersion")
    val targetVersion: String,
    @ColumnInfo(name = "hasUpload", defaultValue = "0")
    val hasUpload: Boolean
)
