package com.cn.architecture

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.repeatOnLifecycle
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


interface UiState

interface UiEvent

interface UiEffect

abstract class BaseViewModel<S : UiState, E : UiEvent, F : UiEffect> : ViewModel() {
    private val eventChannel = Channel<E>(Channel.UNLIMITED)

    abstract fun initUiState(): S

    private val _uiState by lazy { MutableStateFlow(initUiState()) }
    val uiState: StateFlow<S> = _uiState.asStateFlow()

    private val effectChannel = Channel<F>(Channel.UNLIMITED)
    val effectFlow: Flow<F> = effectChannel.receiveAsFlow()

    init {
        eventChannel.receiveAsFlow()
            .onEach(::collectEvents)
            .launchIn(viewModelScope)
    }

    fun sendEvent(event: E) {
        eventChannel.trySend(event)
    }

    private fun collectEvents(event: E) = handleEvents(event)

    abstract fun handleEvents(event: E)

    protected fun reduceState(reducer: S.() -> S) {
        _uiState.value = _uiState.value.reducer()
    }

    protected fun <R> fetchRemote(
        block: suspend () -> R,
        onSuccess: (R) -> Unit,
        onFailed: (Throwable?) -> Unit
    ) {
        viewModelScope.launch {
            val respond = withContext(Dispatchers.IO) {
                runCatching { block.invoke() }
            }
            respond.fold(
                onSuccess = onSuccess,
                onFailure = onFailed
            )
        }
    }

}

@Composable
fun <S : UiState, E : UiEvent, F : UiEffect> BaseViewModel<S, E, F>.collectSideEffect(
    lifecycleState: Lifecycle.State = Lifecycle.State.STARTED,
    sideEffect: (suspend (sideEffect: F) -> Unit),
) {
    val sideEffectFlow = this.effectFlow
    val lifecycleOwner = LocalLifecycleOwner.current

    LaunchedEffect(sideEffectFlow, lifecycleOwner) {
        lifecycleOwner.lifecycle.repeatOnLifecycle(lifecycleState) {
            sideEffectFlow.collect {
                sideEffect(it)
            }
        }
    }
}