package com.cn.featurewanandroidhome

import com.cn.featurewanandroidhome.data.SquareRepositoryImpl
import com.cn.featurewanandroidhome.domain.SquareRepository
import com.cn.featurewanandroidhome.domain.SquareUseCase
import com.cn.featurewanandroidhome.domain.SquareUseCaseImpl
import com.cn.featurewanandroidhome.presentation.composable.SquareViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.module.Module
import org.koin.dsl.bind
import org.koin.dsl.module


fun setupFeatureWanAndroidHome(networkModule: Module) = module {
    includes(networkModule, loadDataModule(), loadDomainModule(), loadViewModelModule())
}

internal fun loadDataModule() = module {
    single { SquareRepositoryImpl(get()) } bind SquareRepository::class
}

internal fun loadDomainModule() = module {
    single { SquareUseCaseImpl(get()) } bind SquareUseCase::class
}

internal fun loadViewModelModule() = module {
    viewModel { SquareViewModel(get()) }
}