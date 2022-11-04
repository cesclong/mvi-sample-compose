package com.cn.featurewanandroidhome.data.convert

import com.cn.featurewanandroidhome.data.model.SquareDataResponse
import com.cn.featurewanandroidhome.data.model.TrendDataResponse
import com.cn.featurewanandroidhome.domain.model.SquareModel
import com.cn.featurewanandroidhome.domain.model.TrendModel


internal fun SquareDataResponse.toDomainModel() = SquareModel(
    articles = this.data.datas
)


internal fun TrendDataResponse.toDomainModel() = TrendModel(
    articles = this.data
)

