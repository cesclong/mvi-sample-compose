package com.cesc.weatherfeature.domain.usecase

import com.cesc.weatherfeature.domain.data.WeatherRepository
import com.cesc.weatherfeature.domain.model.WeatherDomainModel

/**
 * <类说明 必填>
 *
 * @author  shilong
 * @version  [版本号]
 * @see  [参考资料]
 * @since  [历史 创建日期:2022/11/1]
 */
class WeatherUseCase(
    private val repository: WeatherRepository
) {
    suspend fun getWeather(cityCode: String): WeatherDomainModel {
        return repository.getWeather(cityCode)
    }
}