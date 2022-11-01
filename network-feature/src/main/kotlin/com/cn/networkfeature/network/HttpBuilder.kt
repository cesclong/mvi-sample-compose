package com.cn.networkfeature.network

import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit


internal fun createOkhttpClient() = OkHttpClient.Builder().apply {
    connectTimeout(30, TimeUnit.SECONDS)
    readTimeout(30, TimeUnit.SECONDS)
    writeTimeout(30, TimeUnit.SECONDS)
}.build()

internal fun createGson() = GsonBuilder().setLenient().create()

internal fun createRetrofit() = Retrofit.Builder().apply {
    client(createOkhttpClient())
    baseUrl("http://t.weather.itboy.net/")
    addConverterFactory(GsonConverterFactory.create(createGson()))
}.build()