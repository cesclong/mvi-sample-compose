package com.cn.featurewanandroidhome.domain

import com.cn.featurewanandroidhome.domain.model.QuestionModel

interface QuestionRepository {
    suspend fun getQuestions(page : Int) : QuestionModel
}