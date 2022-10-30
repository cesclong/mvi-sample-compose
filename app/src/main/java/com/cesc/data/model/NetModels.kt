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
    val cityInfo: CityInfo,
    val data: WeatherData
)

data class CityInfo(
    val city: String,
    val citykey: String,
    val parent: String,
    val updateTime: String
)

data class WeatherData(
    val shidu: String,
    val pm25: Int,
    val pm10: Int,
    val quality: String,
    val wendu: Int,
    val ganmao: String,
    val forecast: List<WeatherInfo>
)

data class WeatherInfo(
    val date: String,
    val high: String,
    val low: String,
    val ymd: String,
    val week: String,
    val sunrise: String,
    val aqi: Int,
    val fx: String,
    val fl: String,
    val type: String,
    val notice: String
)
