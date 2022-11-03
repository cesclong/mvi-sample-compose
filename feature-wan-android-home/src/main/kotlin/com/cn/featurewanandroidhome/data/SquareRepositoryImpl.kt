package com.cn.featurewanandroidhome.data

import com.cn.featurewanandroidhome.data.convert.toDomainModel
import com.cn.featurewanandroidhome.data.source.ApiService
import com.cn.featurewanandroidhome.domain.SquareRepository
import com.cn.featurewanandroidhome.domain.model.SquareModel
import retrofit2.Retrofit

class SquareRepositoryImpl(
    private val retrofit: Retrofit
) : SquareRepository {
    private val apiService by lazy {
        retrofit.create(ApiService::class.java)
    }

    override suspend fun getArticles(index: Int): SquareModel {
        return apiService.getSquareArticles(index).data.toDomainModel()
    }
}