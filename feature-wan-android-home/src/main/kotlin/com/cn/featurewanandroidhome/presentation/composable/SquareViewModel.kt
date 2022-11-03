package com.cn.featurewanandroidhome.presentation.composable

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cesc.commonmodel.Article
import com.cn.featurewanandroidhome.domain.SquareUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SquareViewModel(
    private val useCase: SquareUseCase
) : ViewModel() {
    private val channel = Channel<SquareIntent>(Channel.UNLIMITED)

    private val _uiState = MutableStateFlow(SquareUiState())

    val uiState: StateFlow<SquareUiState>
        get() = _uiState

    init {
        channel.receiveAsFlow()
            .onEach(::handleIntent)
            .launchIn(viewModelScope)

    }

    private fun handleIntent(intent: SquareIntent) {
        channel.trySend(intent)
    }

    fun sendIntent(intent: SquareIntent) {
        when (intent) {
            is SquareIntent.Fetch -> {
                fetchSquareData(intent.page)
            }
        }
    }

    private fun fetchSquareData(page: Int) {
        updateState {
            it.copy(isLoading = true)
        }
        viewModelScope.launch {
            val respond = withContext(Dispatchers.IO) {
                runCatching {
                    useCase.getSquareArticles(page)
                }
            }
            respond.fold(
                onSuccess = { model ->
                    updateState {
                        it.copy(isLoading = false, isError = false, datas = model.articles)
                    }
                },
                onFailure = {
                    updateState {
                        it.copy(isLoading = false, isError = true)
                    }

                }
            )

        }
    }

    private fun updateState(handler: (SquareUiState) -> SquareUiState) {
        _uiState.tryEmit(handler(_uiState.value))
    }
}

sealed class SquareIntent {
    data class Fetch(val page: Int) : SquareIntent()

}


data class SquareUiState(
    val isLoading: Boolean = false,
    val isError: Boolean = false,
    val datas: List<Article> = emptyList()
)