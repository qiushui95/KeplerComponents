package com.kepler.components

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.kepler.components.databinding.ActivityMainBinding
import com.kepler.components.http.ChuckerInterceptorDelegate
import com.kepler.components.http.CommonParamInterceptor
import com.kepler.components.http.OkHttpProfilerInterceptorDelegate
import com.kepler.components.json.BooleanJsonAdapter
import com.kepler.components.update.UpdateActivity
import com.localebro.okhttpprofiler.OkHttpProfilerInterceptor
import okhttp3.Interceptor
import org.koin.android.ext.android.getKoin
import org.koin.dsl.bind
import org.koin.dsl.module
import son.ysy.architecture.http.interceptor.HttpInterceptor
import son.ysy.architecture.http.json.adapter.HttpJsonAdapter
import son.ysy.architecture.http.json.annotations.CheckResponseCode
import son.ysy.architecture.http.json.annotations.IgnoreParents
import son.ysy.architecture.initializer.ArchitectureInitializer

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {

        ArchitectureInitializer(
            application,
            true,
            "http://dhs.keplerlab.cn/",
            startPageInfo = son.ysy.architecture.entity.PageInfo(page = 0, pageSize = 0),
            httpTimeout = 15,
            tokenGetter = { TokenGetterImpl() },
            versionGetter = { VersionGetterImpl() },
            defaultCheckResponseCode = {
                CheckResponseCode.createByReflect("code", "message", 100)
            },
            defaultIgnoreParents = {
                IgnoreParents.createByReflect("returnValue", priority = 1)
            },
            errorReport = {
                ErrorReporterImpl()
            }
        )

        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)

        setContentView(binding.root)

        binding.btnAboutUpdate.setOnClickListener {
            startActivity(Intent(this, UpdateActivity::class.java))
        }

        getKoin().loadModules(listOf(module {

            single {
                OkHttpProfilerInterceptorDelegate()
            } bind HttpInterceptor::class

            single {
                ChuckerInterceptorDelegate(this@MainActivity)
            } bind HttpInterceptor::class

            single {
                CommonParamInterceptor(get())
            } bind HttpInterceptor::class

            single {
                BooleanJsonAdapter()
            } bind HttpJsonAdapter::class
        }))
    }
}