package com.cn.featurewanandroidhome.data

import com.cn.featurewanandroidhome.data.convert.toDomainModel
import com.cn.featurewanandroidhome.data.source.ApiService
import com.cn.featurewanandroidhome.domain.TrendRepository
import com.cn.featurewanandroidhome.domain.model.TrendModel
import retrofit2.Retrofit

class TrendRepositoryImpl(
    private val retrofit: Retrofit
) : TrendRepository {
    private val api by lazy {
        retrofit.create(ApiService::class.java)
    }

    override suspend fun getAllTopArticles(): TrendModel {
        return api.getAllTopArticles().toDomainModel()
    }
}