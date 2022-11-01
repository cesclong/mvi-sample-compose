package com.cesc.features

import com.cn.networkfeature.setupFeatureNetwork
import org.koin.core.KoinApplication


fun KoinApplication.loadNetworkModule() {
    modules(setupFeatureNetwork())
}
