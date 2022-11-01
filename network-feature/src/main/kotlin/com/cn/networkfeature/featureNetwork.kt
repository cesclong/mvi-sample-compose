package com.cn.networkfeature

import com.cn.networkfeature.network.NetworkManager
import com.cn.networkfeature.network.createRetrofit
import org.koin.dsl.module


fun setupFeatureNetwork() = module {
    includes(networkManagerModule, retrofitModule)
}

internal val networkManagerModule = module {
    single { NetworkManager(get()) }
}

internal val retrofitModule = module {
    single { createRetrofit() }
}