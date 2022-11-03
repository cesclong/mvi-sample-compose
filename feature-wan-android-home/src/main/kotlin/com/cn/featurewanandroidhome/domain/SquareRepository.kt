package com.cn.featurewanandroidhome.domain

import com.cn.featurewanandroidhome.domain.model.SquareModel

interface SquareRepository {
    suspend fun getArticles(index : Int) : SquareModel
}