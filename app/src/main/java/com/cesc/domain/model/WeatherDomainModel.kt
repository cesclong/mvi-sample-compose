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
    val cityInfo: CityInfo,
    val shidu: String,
    val pm25: Int,
    val pm10: Int,
    val quality: String,
    val wendu: Int,
    val ganmao: String,
    val weatherInfos: List<Weather>
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

data class Weather(
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

/**
 *     "date": "30",
"high": "高温 16℃",
"low": "低温 8℃",
"ymd": "2022-10-30",
"week": "星期日",
"sunrise": "06:19",
"sunset": "16:55",
"aqi": 42,
"fx": "南风",
"fl": "2级",
"type": "阴",
"notice": "不要被阴云遮挡住好心情"
 */