package com.cn.featurewanandroidhome.presentation.composable

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class HomeScreenViewModel : ViewModel() {
    var uiStates by mutableStateOf(HomeViewState())
        private set

    init {
        uiStates = uiStates.copy(
            titles = listOf(
                TabTitle(101, "Square"),
                TabTitle(102, "Trend"),
                TabTitle(103, "Question")
            )
        )
    }

}

data class HomeViewState(val titles: List<TabTitle> = emptyList())

data class TabTitle(
    val id: Int,
    val text: String,
    var cachePosition: Int = 0,
    var selected: Boolean = false
)