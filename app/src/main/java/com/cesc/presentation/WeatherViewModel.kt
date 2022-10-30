package com.cesc.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cesc.domain.UseCase
import com.cesc.domain.model.WeatherDomainModel
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
        handleAction()
    }

    fun sendAction(action: WeatherAction) {
        channel.trySend(action)
    }

    private fun handleAction() {
        viewModelScope.launch {
            channel.consumeAsFlow().collect {
                when (it) {
                    is WeatherAction.FetchWeather -> fetchWeather()
                }
            }
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

data class WeatherUiState(
    val isLoading: Boolean = false,
    val isError: Boolean = false,
    val weatherDomainModel: WeatherDomainModel? = null,
    val errorMessage: String = ""
)