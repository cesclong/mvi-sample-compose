package com.cn.featurewanandroidhome.domain

import com.cn.featurewanandroidhome.domain.model.QuestionModel

interface QuestionUseCase {

    suspend operator fun invoke(page: Int): QuestionModel
}


internal class QuestionUseCaseImpl(
    private val repository: QuestionRepository
) : QuestionUseCase {
    override suspend fun invoke(page: Int): QuestionModel {
        return repository.getQuestions(page)
    }

}
