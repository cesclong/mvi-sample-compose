package com.cn.featurewanandroidhome.presentation.composable

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cesc.commonmodel.Article
import com.cn.architecture.BaseViewModel
import com.cn.architecture.UiEffect
import com.cn.architecture.UiEvent
import com.cn.architecture.UiState
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
) : BaseViewModel<TrendUiState, TrendUiEvent, TrendUiEffect>() {
    override fun initUiState(): TrendUiState = TrendUiState.init

    override fun handleEvents(event: TrendUiEvent) {
        when (event) {
            is TrendUiEvent.Init -> {
                fetchArticles()
            }
        }
    }

    private fun fetchArticles() {
        reduceState {
            this.copy(isLoading = true, isError = false, data = emptyList())
        }

        fetchRemote(
            block = {
                useCase.getAllTopArticles()
            },
            onSuccess = {
                reduceState {
                    this.copy(isLoading = false, isError = false, data = it.articles)
                }
            },
            onFailed = {
                reduceState {
                    this.copy(
                        isLoading = false,
                        isError = false,
                        errorMessage = it?.message ?: ""
                    )
                }
            }
        )
    }


}//end class


sealed class TrendUiEvent : UiEvent {
    object Init : TrendUiEvent()
}


data class TrendUiState(
    val isLoading: Boolean = false,
    val isError: Boolean = false,
    val errorMessage: String = "",
    val data: List<Article> = emptyList()
) : UiState {
    companion object {
        val init = TrendUiState(isLoading = true)
    }
}


sealed class TrendUiEffect : UiEffect {
    data class ShowToast(val msg: String) : TrendUiEffect()
}
