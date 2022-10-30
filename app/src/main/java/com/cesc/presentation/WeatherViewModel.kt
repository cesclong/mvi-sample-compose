package com.cesc.presentation

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cesc.domain.UseCase
import com.cesc.domain.model.WeatherDomainModel
import com.google.gson.Gson
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class WeatherViewModel(
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO,
    private val useCase: UseCase
) : ViewModel() {

    private val channel = Channel<WeatherAction>(Channel.UNLIMITED)
    private val _uiState = MutableStateFlow(WeatherUiState())
    val uiState: StateFlow<WeatherUiState>
        get() = _uiState

    init {
        channel.receiveAsFlow()
            .onEach(::handleAction)
            .launchIn(viewModelScope)
    }

    fun sendAction(action: WeatherAction) {
        channel.trySend(action)
    }

    private fun handleAction(action: WeatherAction){
        when(action){
            is WeatherAction.FetchWeather -> fetchWeather()
        }
    }

    private fun fetchWeather() {
        viewModelScope.launch {
            updateState {
                it.copy(isLoading = true, isError = false)
            }
            val fold = withContext(ioDispatcher) {
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
//遵循SSOT原则，所有影响UI刷新的数据都定义在ViewState中
data class WeatherUiState(
    val isLoading: Boolean = false,
    val isError: Boolean = false,
    val weatherDomainModel: WeatherDomainModel? = null,
    val errorMessage: String = ""
)