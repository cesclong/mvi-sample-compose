package com.cn.featurewanandroidhome.data.source

import com.cn.featurewanandroidhome.data.model.SquareDataResponse
import com.cn.featurewanandroidhome.data.model.TrendDataResponse
import retrofit2.http.GET
import retrofit2.http.Path

internal interface ApiService {

    @GET("user_article/list/{page}/json")
    suspend fun getSquareArticles(@Path("page") page: Int): SquareDataResponse

    @GET("article/top/json")
    suspend fun getAllTopArticles(): TrendDataResponse

}