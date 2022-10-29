package com.cesc.data.network

import com.cesc.data.model.WeatherResponse
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path

/**
 * <类说明 必填>
 *
 * @author shilong
 * @version [版本号]
 * @see [参考资料]
 * @since [历史 创建日期:2022/10/26]
 */

interface ApiService {
    @GET("api/weather/city/{code}")
    suspend fun getWeather(@Path("code") cityCode: String): WeatherResponse
}

fun createApiService(): ApiService {
    return Retrofit.Builder()
        .apply {
            baseUrl("http://t.weather.itboy.net/")
            addConverterFactory(GsonConverterFactory.create())
        }
        .client(
            OkHttpClient.Builder().build()
        )
        .build()
        .create(ApiService::class.java)
}
