package com.cn.featuresearchcity.domain.data

import retrofit2.Retrofit
import retrofit2.http.GET
import retrofit2.http.Path

/**
 * <类说明 必填>
 *
 * @author  shilong
 * @version  [版本号]
 * @see  [参考资料]
 * @since  [历史 创建日期:2022/11/2]
 */
class TestRepository(
    private val retrofit: Retrofit
) {
    private val apiService by lazy {
        retrofit.create(TestApiService::class.java)
    }

    suspend fun getWeather(code: String): TestResponse {
        return apiService.getWeather(code)
    }
}

internal interface TestApiService {
    @GET("api/weather/city/{code}")
    suspend fun getWeather(@Path("code") cityCode: String): TestResponse
}

data class TestResponse(
    val message: String,
    val status: Int,
    val date: String,
    val time: String
)