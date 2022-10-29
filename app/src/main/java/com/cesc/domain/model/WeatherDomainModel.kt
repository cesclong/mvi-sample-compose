package com.cesc.domain.model

/**
 * <类说明 必填>
 *
 * @author shilong
 * @version [版本号]
 * @see [参考资料]
 * @since [历史 创建日期:2022/10/26]
 */

data class WeatherDomainModel(
    val status: Status,
    val cityInfo: CityInfo

)

data class CityInfo(
    val city: String,
    val cityCode: String,
    val parent: String,
    val updateTime: String
)

data class Status(
    val code: Int,
    val message: String,
    val date: String,
    val time: String
)
