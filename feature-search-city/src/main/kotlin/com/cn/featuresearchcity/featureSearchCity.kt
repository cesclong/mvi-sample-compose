package com.cn.featuresearchcity

import com.cn.featuresearchcity.domain.data.TestRepository
import com.cn.featuresearchcity.presentation.SearchCityViewModel
import com.cn.featuresearchcity.presentation.SearchCityViewModelImpl
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.module.Module
import org.koin.dsl.bind
import org.koin.dsl.module

/**
 * <类说明 必填>
 *
 * @author  shilong
 * @version  [版本号]
 * @see  [参考资料]
 * @since  [历史 创建日期:2022/11/2]
 */

fun setupFeatureSearchCity(networkModule: Module) = module {
    includes(networkModule, loadDomainModule(), loadViewModelModule())
}

internal fun loadDomainModule() = module {
    single { TestRepository(get()) }
}

internal fun loadViewModelModule() = module {
    viewModel { SearchCityViewModelImpl(get()) } bind SearchCityViewModel::class
}