package com.cn.featurewanandroidhome.data.convert

import com.cn.featurewanandroidhome.data.model.SquareDataResponse
import com.cn.featurewanandroidhome.domain.model.SquareModel


fun SquareDataResponse.toDomainModel() =
    SquareModel(
        articles = this.data.datas
    )

