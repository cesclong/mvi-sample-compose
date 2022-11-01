package com.cn.networkfeature.network

import com.google.gson.Gson
import okhttp3.OkHttpClient
import retrofit2.Retrofit

class NetworkManager(
    private val retrofit: Retrofit
) {
    fun <T> createService(clazz: Class<T>): T {
        return retrofit.create(clazz)
    }
}