package com.kepler.component.update.model.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.kepler.module.model.room.eneity.update.UpdateIgnoreEntity
import com.kepler.module.model.room.eneity.update.UpdateInfoEntity
import com.kepler.module.model.room.eneity.update.UpdateInstallEntity
import com.kepler.module.model.room.eneity.update.UpdateLogEntity

@Database(
    entities = [
        UpdateInfoEntity::class,
        UpdateIgnoreEntity::class,
        UpdateInstallEntity::class,
        UpdateLogEntity::class
    ],
    version = 1,
    exportSchema = true
)
internal abstract class UpdateDatabase : RoomDatabase() {

    abstract fun getUpdateDao(): UpdateDao
}