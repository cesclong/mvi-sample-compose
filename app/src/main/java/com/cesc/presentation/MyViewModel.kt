package com.cesc.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cesc.domain.UseCase
import com.cesc.domain.model.WeatherDomainModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/**
 * <类说明 必填>
 *
 * @author shilong
 * @version [版本号]
 * @see [参考资料]
 * @since [历史 创建日期:2022/10/29]
 */
class MyViewModel(
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO,
    private val useCase: UseCase
) : ViewModel() {
    private val channel = Channel<WeatherIntent>(Channel.UNLIMITED)

    private val _uiState = MutableStateFlow<WeatherUiState>(WeatherUiState.Init)
    val uiState: StateFlow<WeatherUiState>
        get() = _uiState

    init {
        handleIntent()
    }

    fun sendIntent(newIntent: WeatherIntent) {
        channel.trySend(newIntent)
    }

    private fun handleIntent() {
        viewModelScope.launch {
            channel.consumeAsFlow().collect {
                when (it) {
                    is WeatherIntent.Fetch -> fetchWeather()
                }
            }
        }
    }

    private fun fetchWeather() {
        viewModelScope.launch {
            _uiState.value = WeatherUiState.Loading
            val fold = withContext(ioDispatcher) {
                runCatching {
                    useCase.getWeather("101070201")
                }
            }
            fold
                .onSuccess {
                    _uiState.emit(WeatherUiState.ShowWeather(it))
                }.onFailure {
                    _uiState.emit(WeatherUiState.Error(it.message ?: ""))
                }
        }
    }
}

// view -> model
sealed class WeatherIntent {
    object Fetch : WeatherIntent()
}

// model -> view
sealed class WeatherUiState() {
    object Init : WeatherUiState()
    object Loading : WeatherUiState()
    data class ShowWeather(val model: WeatherDomainModel) : WeatherUiState()
    data class Error(val msg: String) : WeatherUiState()
}
