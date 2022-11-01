package com.cesc.features

import com.cesc.weatherfeature.setupFeatureWeather
import com.cn.featuresearchcity.setupFeatureSearchCity
import com.cn.networkfeature.setupFeatureNetwork
import org.koin.core.KoinApplication
import org.koin.core.module.Module


private fun KoinApplication.setupNetworkModule(): Module {
    return setupFeatureNetwork()
}


fun KoinApplication.setupFeatureWeatherModule(): Module{
    return setupFeatureWeather(setupNetworkModule())
}

fun KoinApplication.setupFeatureSearchCityModule(): Module{
    return setupFeatureSearchCity(setupFeatureNetwork())
}