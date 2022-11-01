package com.cesc.weatherfeature.data

import com.cesc.weatherfeature.data.convert.toDomainModel
import com.cesc.weatherfeature.domain.data.WeatherRepository
import com.cesc.weatherfeature.domain.model.WeatherDomainModel
import retrofit2.Retrofit

/**
 * <类说明 必填>
 *
 * @author  shilong
 * @version  [版本号]
 * @see  [参考资料]
 * @since  [历史 创建日期:2022/11/1]
 */
internal class WeatherRepositoryImpl(
    private val retrofit: Retrofit
) : WeatherRepository {
    private val api by lazy {
        retrofit.create(ApiService::class.java)
    }

    override suspend fun getWeather(cityCode: String): WeatherDomainModel {
        return api.getWeather(cityCode).toDomainModel()
    }
}