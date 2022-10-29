package com.cesc.data.convert

import com.cesc.data.model.WeatherResponse
import com.cesc.domain.model.CityInfo
import com.cesc.domain.model.Status
import com.cesc.domain.model.WeatherDomainModel

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
        )
    )
}
