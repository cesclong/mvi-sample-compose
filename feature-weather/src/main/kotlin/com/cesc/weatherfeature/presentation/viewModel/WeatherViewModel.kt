package com.cesc.weatherfeature.presentation.viewModel

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cesc.weatherfeature.domain.model.WeatherDomainModel
import com.cesc.weatherfeature.domain.usecase.WeatherUseCase
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


/**
 * <类说明 必填>
 *
 * @author  shilong
 * @version  1.0.0
 * @since  [历史 创建日期:2022/11/1]
 */
abstract class WeatherViewModel(
) : ViewModel() {

    abstract val uiState: StateFlow<WeatherUiState>

    abstract fun sendAction(action: WeatherAction)
}


internal class WeatherViewModelImpl(
    private val useCase: WeatherUseCase
) : WeatherViewModel() {
    private val channel = Channel<WeatherAction>(Channel.UNLIMITED)
    private val _uiState = MutableStateFlow(WeatherUiState())

    override val uiState: StateFlow<WeatherUiState>
        get() = _uiState

    init {
        channel.receiveAsFlow()
            .onEach(::handleAction)
            .launchIn(viewModelScope)
    }

    override fun sendAction(action: WeatherAction) {
        channel.trySend(action)
    }

    private fun handleAction(action: WeatherAction) {
        when (action) {
            is WeatherAction.FetchWeather -> fetchWeather()
        }
    }

    private fun fetchWeather() {
        viewModelScope.launch {
            updateState {
                it.copy(isLoading = true, isError = false)
            }
            val fold = withContext(Dispatchers.IO) {
                kotlin.runCatching {
                    useCase.getWeather("101070201")
                }
            }

            fold.onSuccess { model ->
                Log.e("Model", Gson().toJson(model))
                updateState {
                    it.copy(isLoading = false, isError = false, weatherDomainModel = model)
                }
            }.onFailure { throwable ->
                updateState {
                    it.copy(
                        isLoading = false,
                        isError = true,
                        errorMessage = throwable.message ?: ""
                    )
                }
            }
        }
    }

    private suspend fun updateState(handler: suspend (state: WeatherUiState) -> WeatherUiState) {
        _uiState.emit(handler(_uiState.value))

    }
}


sealed class WeatherAction {
    object FetchWeather : WeatherAction()
}

// 遵循SSOT原则，所有影响UI刷新的数据都定义在ViewState中
data class WeatherUiState(
    val isLoading: Boolean = false,
    val isError: Boolean = false,
    val weatherDomainModel: WeatherDomainModel? = null,
    val errorMessage: String = ""
)
