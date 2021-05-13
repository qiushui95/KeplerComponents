package com.kepler.component.update.model.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.kepler.component.update.model.room.entity.*
import com.kepler.component.update.model.room.entity.DownloadStatusEntity
import com.kepler.component.update.model.room.entity.IgnoreRecordEntity
import com.kepler.component.update.model.room.entity.InstallRecordEntity
import com.kepler.component.update.model.room.entity.UpdateLogEntity
import com.kepler.component.update.model.room.entity.VersionInfoEntity

@Database(
    entities = [
        VersionInfoEntity::class,
        IgnoreRecordEntity::class,
        InstallRecordEntity::class,
        UpdateLogEntity::class,
        DownloadStatusEntity::class,
    ],
    version = 1,
    exportSchema = true
)
internal abstract class UpdateDatabase : RoomDatabase() {

    abstract fun getUpdateDao(): UpdateDao
}