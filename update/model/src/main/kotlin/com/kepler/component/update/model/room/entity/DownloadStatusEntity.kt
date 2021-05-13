package com.kepler.component.update.model.room.entity

import androidx.room.*


@Entity(
    tableName = "DownloadStatus",
)
internal data class DownloadStatusEntity(
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "updateId")
    val updateId: String,
    @ColumnInfo(name = "percent")
    val percent: Float,
    @ColumnInfo(name = "isFailed")
    val isFailed: Boolean,
    @ColumnInfo(name = "path")
    val path: String?,
) {
    companion object {
        const val NOT_START_PERCENT = Float.MIN_VALUE
    }

}