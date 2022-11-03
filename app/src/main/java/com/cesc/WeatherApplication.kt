package com.cesc

import android.app.Application
import android.content.Context
import com.cesc.features.setupFeatureSearchCityModule
import com.cesc.features.setupFeatureWeatherModule
import com.cesc.features.setupWanAndroidHomeFeatureModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

/**
 * <类说明 必填>
 *
 * @author shilong
 * @version [版本号]
 * @see [参考资料]
 * @since [历史 创建日期:2022/10/29]
 */
class WeatherApplication : Application() {
    companion object {
        lateinit var CONTEXT: Context
    }

    override fun onCreate() {
        super.onCreate()
        CONTEXT = this

        startKoin {
            androidContext(this@WeatherApplication)
            androidLogger()

            val moduleList = listOf(
                setupFeatureWeatherModule(),
                setupFeatureSearchCityModule(),
                setupWanAndroidHomeFeatureModule()
            )
            modules(moduleList)
        }
    }
}
