package com.cn.featurewanandroidhome.domain

import com.cn.featurewanandroidhome.domain.model.SquareModel

abstract class SquareUseCase {
    abstract suspend fun getSquareArticles(page: Int = 0): SquareModel
}

internal class SquareUseCaseImpl(
    private val repository: SquareRepository
) : SquareUseCase() {
    override suspend fun getSquareArticles(page: Int): SquareModel {
        return repository.getArticles(page)
    }

}