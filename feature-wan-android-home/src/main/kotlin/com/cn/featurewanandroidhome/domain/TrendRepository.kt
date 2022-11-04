package com.cn.featurewanandroidhome.domain

import com.cn.featurewanandroidhome.domain.model.TrendModel

interface TrendRepository {

    suspend fun getAllTopArticles(): TrendModel

}