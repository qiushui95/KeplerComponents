package com.kepler.component.update.model.api

import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.Query

internal interface UpdateApi {

    /**
     * 获取更新信息
     */
    @POST("core/api/system/APPVersion_compareAPPVersion.action")
    suspend fun loadUpdateInfo(
        @Query("accessToken") token: String?
    ): ResponseUpdateContainer


    /**
     * 上传更新记录
     */
    @POST("core/api/system/APPVersion_appUpdateLog.action")
    suspend fun uploadUpdateLog(
        @Body updateLog: ResponseUpdateLog
    )
}