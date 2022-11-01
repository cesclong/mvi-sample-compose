package com.cesc.weatherfeature

import com.cesc.weatherfeature.data.WeatherRepositoryImpl
import com.cesc.weatherfeature.domain.data.WeatherRepository
import com.cesc.weatherfeature.domain.usecase.WeatherUseCase
import com.cesc.weatherfeature.presentation.viewModel.WeatherViewModel
import com.cesc.weatherfeature.presentation.viewModel.WeatherViewModelImpl
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.module.Module
import org.koin.dsl.bind
import org.koin.dsl.module


fun setupFeatureWeather(networkModule: Module) = module {
    includes(networkModule, loadDataModule(), loadDomainModule(), loadViewModelModule())
}

internal fun loadDomainModule() = module {
    single { WeatherUseCase(get()) }
}

internal fun loadDataModule() = module {
    single { WeatherRepositoryImpl(get()) } bind WeatherRepository::class
}

internal fun loadViewModelModule() = module {
    viewModel { WeatherViewModelImpl(get()) } bind WeatherViewModel::class
}