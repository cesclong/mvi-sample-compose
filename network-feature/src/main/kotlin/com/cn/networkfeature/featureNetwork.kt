package com.cn.networkfeature

import com.cn.networkfeature.network.createRetrofit
import com.cn.networkfeature.network.createWanAndroidRetrofit
import org.koin.dsl.module


fun setupFeatureNetwork() = module {
    includes(networkManagerModule)
}

internal val networkManagerModule = module {
//    single { createRetrofit() }
    single { createWanAndroidRetrofit() }
}

