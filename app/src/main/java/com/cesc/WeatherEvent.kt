package com.cesc

/**
 * <类说明 必填>
 *
 * @author shilong
 * @version [版本号]
 * @see [参考资料]
 * @since [历史 创建日期:2022/10/26]
 */
sealed class WeatherEvent {
    data class Search(val cityCode: String) : WeatherEvent()
}
