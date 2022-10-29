package com.cesc.data

import com.cesc.data.convert.toDomainModel
import com.cesc.data.network.ApiService
import com.cesc.domain.Repository
import com.cesc.domain.model.WeatherDomainModel

/**
 * <类说明 必填>
 *
 * @author shilong
 * @version [版本号]
 * @see [参考资料]
 * @since [历史 创建日期:2022/10/26]
 */
class RepositoryImpl(
    private val service: ApiService
) : Repository {

    override suspend fun getWeather(cityCode: String): WeatherDomainModel {
        return service.getWeather(cityCode).toDomainModel()
    }
}
