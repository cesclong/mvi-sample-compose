package com.cn.featurewanandroidhome.data.model

import com.cesc.commonmodel.Article
import com.cesc.commonmodel.IDataResponse


data class SquareDataResponse(
    override val curPage: Int,
    override val offset: Int,
    override val over: Boolean,
    override val pageCount: Int,
    override val size: Int,
    override val total: Int,
    val datas: List<Article>
) : IDataResponse