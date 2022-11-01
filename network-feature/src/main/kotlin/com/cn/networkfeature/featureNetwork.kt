package com.cn.networkfeature

import com.cn.networkfeature.network.createRetrofit
import org.koin.dsl.module


fun setupFeatureNetwork() = module {
    includes(networkManagerModule)
}

internal val networkManagerModule = module {
    single { createRetrofit() }
}
