package com.cesc.weatherfeature.data

import com.cesc.weatherfeature.data.model.WeatherResponse
import retrofit2.http.GET
import retrofit2.http.Path

/**
 * <类说明 必填>
 *
 * @author  shilong
 * @version  [版本号]
 * @see  [参考资料]
 * @since  [历史 创建日期:2022/11/1]
 */
interface ApiService {
    @GET("api/weather/city/{code}")
    suspend fun getWeather(@Path("code") cityCode: String): WeatherResponse
}