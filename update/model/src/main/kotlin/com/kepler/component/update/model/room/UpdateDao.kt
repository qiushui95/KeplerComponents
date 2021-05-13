package com.kepler.component.update.model.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.kepler.component.update.model.room.entity.DownloadStatusEntity
import com.kepler.component.update.model.room.entity.InstallRecordEntity
import com.kepler.component.update.model.room.entity.UpdateLogEntity
import com.kepler.component.update.model.room.entity.VersionInfoEntity
import kotlinx.coroutines.flow.Flow

@Dao
internal abstract class UpdateDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    protected abstract suspend fun insertUpdateInfo(versionInfo: VersionInfoEntity)

    @Query("select count(1) >0 from VersionInfo where updateId=:id;")
    protected abstract suspend fun isContainUpdateInfo(id: String): Boolean

    @Query("update VersionInfo set targetVersion=:targetVersion, url=:url,isForce=:isForce where isCallByUser=:id;")
    protected abstract suspend fun updateUpdateInfo(
        id: String,
        targetVersion: String,
        url: String,
        isForce: Boolean
    )


    suspend fun insertOrUpdateOrDelete(
        id: String,
        targetVersion: String,
        url: String,
        isForce: Boolean,
        currentVersion: String,
        isCallByUser: Boolean
    ) {

        when {
            isContainUpdateInfo(id) -> updateUpdateInfo(id, targetVersion, url, isForce)
            else ->
                insertUpdateInfo(
                    VersionInfoEntity(
                        0,
                        id,
                        url,
                        isForce,
                        currentVersion,
                        targetVersion,
                        isCallByUser
                    )
                )
        }
    }

    @Query("select * from DownloadStatus where updateId=:id limit 1;")
    abstract suspend fun getDownloadStatusById(id: String): DownloadStatusEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun updateDownloadStatus(status: DownloadStatusEntity)

    @Query("insert into IgnoreRecord(updateId,time) values( :id,:timestamp);")
    abstract suspend fun insertIgnoreRecord(id: String, timestamp: Long)

    @Query("insert into InstallRecord(updateId,currentVersion,targetVersion,time) values( :id,:currentVersion,:targetVersion,:timestamp);")
    abstract suspend fun insertInstallRecord(id: String,currentVersion: String,targetVersion: String, timestamp: Long)

    @Query("update InstallRecord set hasUpload=1 where id in (:ids);")
    abstract suspend fun updateInstallUploadStatus(ids: List<Int>)

    @Query("select * from InstallRecord where (currentVersion=:currentVersion or targetVersion=:currentVersion) and hasUpload=0;")
    abstract suspend fun getNotUploadInstallLog(currentVersion: String): List<InstallRecordEntity>

    @Insert
    abstract suspend fun insertUpdateLog(logs: List<UpdateLogEntity>)

    @Query("select * from UpdateLog")
    abstract suspend fun getAllUpdateLog(): List<UpdateLogEntity>

    @Query("delete from UpdateLog where id in (:ids);")
    abstract suspend fun deleteUploadLogInId(ids: List<Int>)

    @Query("select * from VersionInfo where updateId=:id;")
    abstract fun getUpdateInfoById(id: String): VersionInfoEntity?

    @Query("select * from VersionInfo where (isForce or updateId not in (select updateId from IgnoreRecord where time>=:start and time<=:end)) and currentVersion=:currentVersion and isCallByUser=0 order by id desc limit 1;")
    abstract fun getAutoVersionInfoFlow(
        start: Long,
        end: Long,
        currentVersion: String
    ): Flow<VersionInfoEntity?>

    @Query("select * from DownloadStatus where updateId=:id limit 1;")
    abstract fun getDownloadStatusFlow(id: String): Flow<DownloadStatusEntity?>
}