package com.cesc.domain

import com.cesc.domain.model.WeatherDomainModel

/**
 * <类说明 必填>
 *
 * @author shilong
 * @version [版本号]
 * @see [参考资料]
 * @since [历史 创建日期:2022/10/26]
 */
interface Repository {
    suspend fun getWeather(cityCode: String): WeatherDomainModel
}
