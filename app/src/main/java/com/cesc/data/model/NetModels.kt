package com.cesc.data.model

/**
 * <类说明 必填>
 *
 * @author shilong
 * @version [版本号]
 * @see [参考资料]
 * @since [历史 创建日期:2022/10/26]
 */

data class WeatherResponse(
    val message: String,
    val status: Int,
    val date: String,
    val time: String,
    val cityInfo: CityInfo
)

data class CityInfo(
    val city: String,
    val citykey: String,
    val parent: String,
    val updateTime: String
)
