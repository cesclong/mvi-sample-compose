package com.cesc.weatherfeature.data.convert

import com.cesc.weatherfeature.data.model.WeatherResponse
import com.cesc.weatherfeature.domain.model.CityInfo
import com.cesc.weatherfeature.domain.model.Status
import com.cesc.weatherfeature.domain.model.Weather
import com.cesc.weatherfeature.domain.model.WeatherDomainModel

/**
 * <类说明 必填>
 *
 * @author shilong
 * @version [版本号]
 * @see [参考资料]
 * @since [历史 创建日期:2022/10/27]
 */

fun WeatherResponse.toDomainModel(): WeatherDomainModel {
    return WeatherDomainModel(
        status = Status(
            code = this.status,
            message = this.message,
            date = this.date,
            time = this.time
        ),
        cityInfo = CityInfo(
            city = this.cityInfo.city,
            cityCode = this.cityInfo.citykey,
            parent = this.cityInfo.parent,
            updateTime = this.cityInfo.updateTime
        ),
        shidu = this.data.shidu,
        pm10 = this.data.pm10,
        pm25 = this.data.pm25,
        quality = this.data.quality,
        wendu = this.data.wendu,
        ganmao = this.data.ganmao,
        weatherInfos = this.data.forecast.map {
            Weather(
                date = it.date,
                high = it.high,
                low = it.low,
                ymd = it.ymd,
                week = it.week,
                sunrise = it.sunrise,
                aqi = it.aqi,
                fx = it.fx,
                fl = it.fl,
                type = it.type,
                notice = it.notice
            )
        }
    )
}
