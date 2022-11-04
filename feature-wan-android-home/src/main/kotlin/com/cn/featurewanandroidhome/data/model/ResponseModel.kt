package com.cn.featurewanandroidhome.data.model

import com.cesc.commonmodel.Article
import com.cesc.commonmodel.IDataResponse
import com.google.gson.annotations.SerializedName


data class SquareDataResponse(
    @SerializedName("errorCode") val code: Int,
    @SerializedName("errorMsg") val message: String,
    val data: SquareData
)

data class SquareData(
    val curPage: Int,
    val offset: Int,
    val over: Boolean,
    val pageCount: Int,
    val size: Int,
    val total: Int,
    val datas: List<Article>
)


data class TrendDataResponse(
    @SerializedName("errorCode") val code: Int,
    @SerializedName("errorMsg") val message: String,
    val data : List<Article>
)
