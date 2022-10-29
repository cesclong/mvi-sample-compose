package com.cesc

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

/**
 * <类说明 必填>
 *
 * @author shilong
 * @version [版本号]
 * @see [参考资料]
 * @since [历史 创建日期:2022/10/26]
 */
class WeatherViewModel() : ViewModel() {

    init {
    }

    fun onTriggerEvent(event: WeatherEvent) {
        viewModelScope.launch {
            when (event) {
                is WeatherEvent.Search -> {
                    searchWeather(event.cityCode)
                }
            }
        }
    }

    private suspend fun searchWeather(cityCode: String) {
    }
}
