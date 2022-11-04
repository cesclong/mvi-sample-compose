package com.cn.featurewanandroidhome.data

import com.cn.featurewanandroidhome.data.convert.toDomainModel
import com.cn.featurewanandroidhome.data.source.ApiService
import com.cn.featurewanandroidhome.domain.QuestionRepository
import com.cn.featurewanandroidhome.domain.model.QuestionModel
import retrofit2.Retrofit

class QuestionRepositoryImpl(
    private val retrofit: Retrofit
) : QuestionRepository {
    private val apiService by lazy {
        retrofit.create(ApiService::class.java)
    }

    override suspend fun getQuestions(page: Int): QuestionModel {
        return apiService.getQuestions(page).toDomainModel()
    }
}