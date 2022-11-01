package com.cesc

import android.app.Application
import com.cesc.data.RepositoryImpl
import com.cesc.data.network.ApiService
import com.cesc.data.network.createApiService
import com.cesc.domain.Repository
import com.cesc.domain.UseCase
import com.cesc.features.loadNetworkModule
import com.cesc.presentation.WeatherViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.startKoin
import org.koin.core.module.Module
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.core.qualifier.named
import org.koin.dsl.module

/**
 * <类说明 必填>
 *
 * @author shilong
 * @version [版本号]
 * @see [参考资料]
 * @since [历史 创建日期:2022/10/29]
 */
class WeatherApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@WeatherApplication)
            androidLogger()
            modules(createDIModules())
            loadNetworkModule()
        }
    }
}

internal fun createDIModules(): List<Module> {
    return listOf(
        module {
            singleOf(::RepositoryImpl) { bind<Repository>() }
            single<ApiService> { createApiService() }

            single<CoroutineDispatcher>(named("io")) { Dispatchers.IO }

            singleOf(::UseCase)

            viewModel { WeatherViewModel(get(named("io")), get()) }
        }
    )
}
