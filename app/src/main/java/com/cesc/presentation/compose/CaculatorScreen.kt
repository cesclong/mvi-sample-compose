package com.cesc.presentation.compose

import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.runningFold
import kotlinx.coroutines.flow.stateIn


@Composable
fun CaculatorScreen(viewModel: CaculatorViewModel = androidx.lifecycle.viewmodel.compose.viewModel()) {
    val uiState by viewModel.state.collectAsState()
    Column(modifier = Modifier.fillMaxSize()) {
        Button(onClick = {
            viewModel.handlEvent(Event.Increase)
        }) {
            Text(text = "Increase")
        }

        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .height(2.dp)
        )

        Button(onClick = {
            viewModel.handlEvent(Event.Decrease)
        }) {
            Text(text = "Decrease")
        }

        Text(text = "num:${uiState.counter}")
    }

}

class CaculatorViewModel() : ViewModel() {
    private val events = Channel<Event>()
    val state = events.receiveAsFlow()
        .runningFold(State(), ::reduceState)
        .stateIn(viewModelScope, SharingStarted.Eagerly, State())

    fun handlEvent(event: Event) {
        events.trySend(event)
    }

    private fun reduceState(currentState: State, event: Event): State {
        return when (event) {
            is Event.Increase -> currentState.copy(counter = currentState.counter + 1)
            is Event.Decrease -> currentState.copy(counter = currentState.counter - 1)
        }
    }

}

sealed class Event {
    object Increase : Event()
    object Decrease : Event()
}

data class State(
    val counter: Int = 0
)