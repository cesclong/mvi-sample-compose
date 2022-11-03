package com.cesc.commonmodel

import com.google.gson.annotations.SerializedName


data class BaseResponse<T>(
    @SerializedName("errorCode") val code: Int,
    @SerializedName("errorMsg") val message: String,
    @SerializedName("data") val data: T
)


interface IDataResponse {
    val curPage: Int
    val offset: Int
    val over: Boolean
    val pageCount : Int
    val size : Int
    val total : Int
}

