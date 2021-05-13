package com.kepler.module.model.room.eneity.update

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "UpdateInfo",
    indices = [Index("currentVersion"), Index("recordId", unique = true)]
)
internal class UpdateInfoEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val id: Int,
    @ColumnInfo(name = "recordId")
    val recordId: String,
    @ColumnInfo(name = "targetVersion")
    val targetVersion: String,
    @ColumnInfo(name = "url")
    val url: String,
    @ColumnInfo(name = "isForce")
    val isForce: Boolean,
    @ColumnInfo(name = "currentVersion")
    val currentVersion: String,
    @ColumnInfo(name = "percent")
    val percent: Float,
    @ColumnInfo(name = "path")
    val path: String?,
    @ColumnInfo(name = "isFailed")
    val isFailed: Boolean
) {
    companion object {
        const val unStartDownloadPercent = -1f
    }
}