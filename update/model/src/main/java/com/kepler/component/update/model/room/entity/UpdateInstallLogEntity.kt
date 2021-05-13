package com.kepler.module.model.room.eneity.update

import androidx.room.ColumnInfo

internal data class UpdateInstallLogEntity(
    @ColumnInfo(name = "id")
    val id:Int,
    @ColumnInfo(name = "recordId")
    val recordId:String,
    @ColumnInfo(name = "currentVersion")
    val currentVersion:String,
    @ColumnInfo(name = "targetVersion")
    val targetVersion:String,
)
