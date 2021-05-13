package com.kepler.module.model.room.eneity.update

import androidx.room.*

@Entity(
    tableName = "UpdateInstall",
    foreignKeys = [
        ForeignKey(
            entity = UpdateInfoEntity::class,
            parentColumns = ["recordId"],
            childColumns = ["updateId"],
            onDelete = ForeignKey.CASCADE,
            onUpdate = ForeignKey.CASCADE
        ),
    ], indices = [Index("updateId")]
)
internal data class UpdateInstallEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val id: Int,
    @ColumnInfo(name = "updateId")
    val updateId: Int,
    @ColumnInfo(name = "time")
    val time: Long,
    @ColumnInfo(name = "hasUpload", defaultValue = "0")
    val hasUpload: Boolean
)
