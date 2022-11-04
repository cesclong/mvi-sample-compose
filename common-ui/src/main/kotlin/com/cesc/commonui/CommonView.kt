package com.cesc.commonui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier


@Composable
fun ShowLoadingView(msg: String = "Loading...", isShow: Boolean = true) {
    if (isShow) {
        Box(modifier = Modifier.fillMaxSize()) {
            Column(
                modifier = Modifier
                    .wrapContentSize()
                    .align(Alignment.Center), horizontalAlignment = Alignment.CenterHorizontally
            ) {
                CircularProgressIndicator()
                Text(text = msg)
            }
        }
    }
}


@Composable
fun ShowErrorView(msg: String = "error", isShow: Boolean = true) {
    if (isShow) {
        Box(modifier = Modifier.fillMaxSize()) {
            Column(
                modifier = Modifier
                    .wrapContentSize()
                    .align(Alignment.Center), horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(text = msg)
            }
        }
    }
}
