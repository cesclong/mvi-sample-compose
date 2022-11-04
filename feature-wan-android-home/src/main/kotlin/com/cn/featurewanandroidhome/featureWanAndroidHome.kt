package com.cn.featurewanandroidhome

import com.cn.featurewanandroidhome.data.SquareRepositoryImpl
import com.cn.featurewanandroidhome.data.TrendRepositoryImpl
import com.cn.featurewanandroidhome.domain.SquareRepository
import com.cn.featurewanandroidhome.domain.SquareUseCase
import com.cn.featurewanandroidhome.domain.SquareUseCaseImpl
import com.cn.featurewanandroidhome.domain.TrendRepository
import com.cn.featurewanandroidhome.domain.TrendUseCase
import com.cn.featurewanandroidhome.domain.TrendUseCaseImpl
import com.cn.featurewanandroidhome.presentation.composable.SquareViewModel
import com.cn.featurewanandroidhome.presentation.composable.TrendViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.module.Module
import org.koin.dsl.bind
import org.koin.dsl.module


fun setupFeatureWanAndroidHome(networkModule: Module) = module {
    includes(networkModule, loadDataModule(), loadDomainModule(), loadViewModelModule())
}

internal fun loadDataModule() = module {
    single { SquareRepositoryImpl(get()) } bind SquareRepository::class
    single { TrendRepositoryImpl(get()) } bind TrendRepository::class
}

internal fun loadDomainModule() = module {
    single { SquareUseCaseImpl(get()) } bind SquareUseCase::class
    single { TrendUseCaseImpl(get()) } bind TrendUseCase::class
}

internal fun loadViewModelModule() = module {
    viewModel { SquareViewModel(get()) }
    viewModel { TrendViewModel(get()) }
}