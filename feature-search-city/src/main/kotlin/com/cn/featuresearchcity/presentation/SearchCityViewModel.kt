package com.cn.featuresearchcity.presentation

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cn.featuresearchcity.domain.data.TestRepository
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/**
 * <类说明 必填>
 *
 * @author  shilong
 * @version  [版本号]
 * @see  [参考资料]
 * @since  [历史 创建日期:2022/11/2]
 */
abstract class SearchCityViewModel : ViewModel() {
    abstract val state: MutableState<String>
    abstract fun fetchData()
}


internal class SearchCityViewModelImpl(
    private val repository: TestRepository
) : SearchCityViewModel() {
    private val _state = mutableStateOf("")
    override val state: MutableState<String>
        get() = _state

    override fun fetchData() {
        viewModelScope.launch {
            val respond = withContext(Dispatchers.IO) {
                kotlin.runCatching {
                    repository.getWeather("101030100")
                }
            }

            respond.fold(
                onSuccess = {
                    Log.e("fetchData", Gson().toJson(it))
                    _state.value = Gson().toJson(it)
                },
                onFailure = {
                    _state.value = it.message ?: ""
                }
            )
        }
    }

}
