package com.cn.featurewanandroidhome.data.source

import com.cesc.commonmodel.BaseResponse
import com.cn.featurewanandroidhome.data.model.SquareDataResponse
import retrofit2.http.GET
import retrofit2.http.Path

internal interface ApiService {

    @GET("user_article/list/{page}/json")
    suspend fun getSquareArticles(@Path("page") page: Int): BaseResponse<SquareDataResponse>

}