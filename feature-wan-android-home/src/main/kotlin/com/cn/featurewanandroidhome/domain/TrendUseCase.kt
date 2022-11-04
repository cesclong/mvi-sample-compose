package com.cn.featurewanandroidhome.domain

import com.cn.featurewanandroidhome.domain.model.TrendModel

abstract class TrendUseCase {
    abstract suspend fun getAllTopArticles(): TrendModel
}

internal class TrendUseCaseImpl(
    private val repository: TrendRepository
) : TrendUseCase() {
    override suspend fun getAllTopArticles(): TrendModel {
        return repository.getAllTopArticles()
    }

}