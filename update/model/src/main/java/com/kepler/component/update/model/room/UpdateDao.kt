package com.kepler.component.update.model.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.kepler.module.model.room.eneity.update.UpdateInfoEntity
import com.kepler.module.model.room.eneity.update.UpdateInstallLogEntity
import com.kepler.module.model.room.eneity.update.UpdateLogEntity
import kotlinx.coroutines.flow.Flow

@Dao
internal abstract class UpdateDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    protected abstract suspend fun insertUpdateInfo(updateInfo: UpdateInfoEntity)

    @Query("select count(1) >0 from UpdateInfo where recordId=:id;")
    protected abstract suspend fun isContainUpdateInfo(id: String): Boolean

    @Query("update UpdateInfo set targetVersion=:targetVersion, url=:url,isForce=:isForce where recordId=:id;")
    protected abstract suspend fun updateUpdateInfo(
        id: String,
        targetVersion: String,
        url: String,
        isForce: Boolean
    )

    @Query("delete from UpdateInfo where recordId=:id;")
    protected abstract suspend fun deleteUpdateInfo(id: String)

    suspend fun insertOrUpdateOrDelete(
        id: String,
        targetVersion: String,
        url: String,
        isForce: Boolean,
        currentVersion: String
    ) {

        when {
            isContainUpdateInfo(id) -> updateUpdateInfo(id, targetVersion, url, isForce)
            else ->
                insertUpdateInfo(
                    UpdateInfoEntity(
                        0,
                        id,
                        targetVersion,
                        url,
                        isForce,
                        currentVersion,
                        UpdateInfoEntity.unStartDownloadPercent,
                        null,
                        false
                    )
                )
        }
    }

    @Query("update UpdateInfo set percent=:percent ,isFailed=0 where recordId=:id; ")
    abstract suspend fun updateDownloadPercent(id: String, percent: Float)

    @Query("update UpdateInfo set isFailed=1 where recordId=:id; ")
    abstract suspend fun updateDownloadFailed(id: String)

    @Query("update UpdateInfo set isFailed=0,path=:path,percent=100 where recordId=:id; ")
    abstract suspend fun updateDownloadSuccess(id: String, path: String)

    @Query("insert into UpdateIgnore(updateId,time) values( :id,:timestamp);")
    abstract suspend fun insertIgnoreRecord(id: String, timestamp: Long)

    @Query("insert into UpdateInstall(updateId,time) values( :id,:timestamp);")
    abstract suspend fun insertInstallRecord(id: String, timestamp: Long)

    @Query("update UpdateInstall set hasUpload=1 where id in (:ids);")
    abstract suspend fun updateInstallUploadStatus(ids: List<Int>)

    @Query("select UpdateInstall.id,UpdateInfo.recordId,UpdateInfo.currentVersion,UpdateInfo.targetVersion from UpdateInstall left join UpdateInfo on UpdateInstall.updateId=UpdateInfo.recordId where (currentVersion=:currentVersion or targetVersion=:currentVersion) and hasUpload=0;")
    abstract suspend fun getNotUploadInstallLog(currentVersion: String): List<UpdateInstallLogEntity>

    @Insert
    abstract suspend fun insertUpdateLog(logs: List<UpdateLogEntity>)

    @Query("select * from UpdateLog")
    abstract suspend fun getAllUpdateLog(): List<UpdateLogEntity>

    @Query("delete from UpdateLog where id in (:ids);")
    abstract suspend fun deleteUploadLogInId(ids: List<Int>)

    @Query("select * from UpdateInfo where recordId=:id;")
    abstract fun getUpdateInfoById(id: String): UpdateInfoEntity?

    @Query("select * from UpdateInfo where (isForce or recordId not in (select updateId from UpdateIgnore where time>=:start and time<=:end)) and currentVersion=:currentVersion order by id desc limit 1;")
    abstract fun getUpdateInfoFlow(
        start: Long,
        end: Long,
        currentVersion: String
    ): Flow<UpdateInfoEntity?>
}