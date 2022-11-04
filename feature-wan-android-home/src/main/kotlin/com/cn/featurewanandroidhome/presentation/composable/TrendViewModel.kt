package com.cn.featurewanandroidhome.presentation.composable

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cesc.commonmodel.Article
import com.cn.featurewanandroidhome.domain.TrendUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class TrendViewModel(
    private val useCase: TrendUseCase
) : ViewModel() {

    private val intentChannel = Channel<TrendIntent>(Channel.UNLIMITED)

    private val _uiState = MutableStateFlow(TrendUiState.init)
    val uiState = _uiState.asStateFlow()

    private val _eventChannel = Channel<TrendEvent>(Channel.UNLIMITED)
    val event = _eventChannel.receiveAsFlow()

    init {
        intentChannel
            .receiveAsFlow()
            .onEach(::handleIntent)
            .launchIn(viewModelScope)
    }

    private fun handleIntent(intent: TrendIntent) {
        when (intent) {
            is TrendIntent.Init -> fetchArticles()
        }
    }

    fun sendIntent(intent : TrendIntent){
        intentChannel.trySend(intent)
    }

    private fun fetchArticles() {
        Log.e("Trend", "fetch")
        reduceState {
            this.copy(isLoading = true)
        }
        viewModelScope.launch {
            val respond = withContext(Dispatchers.IO) {
                runCatching { useCase.getAllTopArticles() }
            }

            respond.fold(
                onSuccess = {
                    sendEvent(TrendEvent.ShowToast("Success"))
                    reduceState {
                        this.copy(
                            isLoading = false,
                            isError = false,
                            data = it.articles,
                            errorMessage = ""
                        )
                    }
                },
                onFailure = {
                    sendEvent(TrendEvent.ShowToast("Failed"))
                    reduceState {
                        this.copy(
                            isLoading = false,
                            isError = true,
                            errorMessage = it.message ?: ""
                        )
                    }
                }
            )
        }
    }

    private fun reduceState(reducer: TrendUiState.() -> TrendUiState) {
        _uiState.value = _uiState.value.reducer()
    }

    private fun sendEvent(event : TrendEvent){
        _eventChannel.trySend(event)
    }

}

sealed class TrendIntent {
    object Init : TrendIntent()
}

data class TrendUiState(
    val isLoading: Boolean = false,
    val isError: Boolean = false,
    val errorMessage: String = "",
    val data: List<Article> = emptyList()
) {
    companion object {
        val init = TrendUiState(isLoading = true)
    }
}

sealed class TrendEvent {
    data class ShowToast(val msg: String) : TrendEvent()
}