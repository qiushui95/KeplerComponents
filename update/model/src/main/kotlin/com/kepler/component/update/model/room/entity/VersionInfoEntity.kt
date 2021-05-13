package com.kepler.component.update.model.room.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "VersionInfo",
    indices = [Index("currentVersion"), Index("updateId", "isCallByUser", unique = true)]
)
internal data class VersionInfoEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val id: Int,
    @ColumnInfo(name = "updateId")
    val updateId: String,
    @ColumnInfo(name = "url")
    val url: String,
    @ColumnInfo(name = "isForce")
    val isForce: Boolean,
    @ColumnInfo(name = "currentVersion")
    val currentVersion: String,
    @ColumnInfo(name = "targetVersion")
    val targetVersion: String,
    @ColumnInfo(name = "isCallByUser")
    val isCallByUser: Boolean,
)