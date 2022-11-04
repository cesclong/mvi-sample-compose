package com.cn.featurewanandroidhome.presentation.composable

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cesc.commonmodel.Article
import com.cn.featurewanandroidhome.domain.QuestionUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

interface IReducer<State> {
    val uiState: StateFlow<State>
    fun reduceState(update: State.() -> State)
}

abstract class ReducerViewModel<State, Intent> : IReducer<State>, ViewModel() {
    private val channel: Channel<Intent> = Channel(Channel.UNLIMITED)

    init {
        channel.receiveAsFlow()
            .onEach(::handleIntent)
            .launchIn(viewModelScope)
    }

    abstract fun handleIntent(intent: Intent)

    fun sendIntent(intent: Intent) {
        channel.trySend(intent)
    }

    abstract val initState: State

    private val _uiState by lazy {
        MutableStateFlow(initState)
    }
    override val uiState: StateFlow<State>
        get() = _uiState

    override fun reduceState(update: State.() -> State) {
        _uiState.value = _uiState.value.update()
    }
}


class QuestionViewModel(
    private val useCase: QuestionUseCase
) : ReducerViewModel<QuestionUiState, QuestionIntent>() {

    override val initState: QuestionUiState
        get() = QuestionUiState.init


    private val channel = Channel<QuestionIntent>(Channel.UNLIMITED)
    override fun handleIntent(intent: QuestionIntent) {
        when (intent) {
            is QuestionIntent.Fetch -> fetch(intent.page)
        }
    }

    init {
        channel.receiveAsFlow()
            .onEach(::handleIntent)
            .launchIn(viewModelScope)
    }


    private fun fetch(page: Int) {
        reduceState {
            this.copy(isLoading = true, isError = false)
        }
        viewModelScope.launch {
            val respond = withContext(Dispatchers.IO) {
                runCatching {
                    useCase(page)
                }
            }

            respond.fold(
                onSuccess = {
                    reduceState {
                        this.copy(
                            isLoading = false,
                            isError = false,
                            list = it.datas
                        )
                    }
                },
                onFailure = {
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

}


sealed class QuestionIntent {
    data class Fetch(val page: Int) : QuestionIntent()
}

data class QuestionUiState(
    val isLoading: Boolean = false,
    val isError: Boolean = false,
    val errorMessage: String = "",
    val list: List<Article> = emptyList()
) {
    companion object {
        val init = QuestionUiState(
            isLoading = true
        )
    }
}