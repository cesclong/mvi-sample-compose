package com.cn.featurewanandroidhome.presentation.composable

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController


@Composable
internal fun RecommendScreen(navController: NavController) {
    Column(modifier = Modifier.fillMaxSize()) {
        Text(text = "recommend")
    }
}