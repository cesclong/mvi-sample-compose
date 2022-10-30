package com.cesc.presentation.compose

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import com.cesc.domain.model.WeatherDomainModel
import com.cesc.presentation.*
import org.koin.androidx.compose.koinViewModel

/**
 * <类说明 必填>
 *
 * @author shilong
 * @version [版本号]
 * @see [参考资料]
 * @since [历史 创建日期:2022/10/29]
 */

@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun WeatherScreen(viewModel: WeatherViewModel = koinViewModel()) {
    LaunchedEffect(key1 = Unit) {
        viewModel.sendAction(WeatherAction.FetchWeather)
    }
    val uiState by viewModel.uiState.collectAsState()

    when {
        uiState.isLoading -> LoadingView()
        uiState.isError -> ErrorView(msg = uiState.errorMessage)
        uiState.weatherDomainModel != null -> ShowWeather(model = uiState.weatherDomainModel!!)
    }

}

@Composable
fun ShowWeather(model: WeatherDomainModel) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        Text(text = "${model.status.code}", fontSize = 28.sp, textAlign = TextAlign.Center)
        Text(text = model.cityInfo.city, fontSize = 28.sp, textAlign = TextAlign.Center)
        Text(text = model.cityInfo.cityCode, fontSize = 28.sp, textAlign = TextAlign.Center)
        Text(text = model.cityInfo.parent, fontSize = 28.sp, textAlign = TextAlign.Center)
        Text(text = model.cityInfo.updateTime, fontSize = 28.sp, textAlign = TextAlign.Center)
    }

}

@Composable
fun LoadingView() {
    Box(modifier = Modifier.fillMaxSize()) {
        Text(text = "Loading...", fontSize = 28.sp, textAlign = TextAlign.Center)
    }
}

@Composable
fun ErrorView(msg: String) {
    Box(modifier = Modifier.fillMaxSize()) {
        Text(text = "Error:${msg}", fontSize = 28.sp, textAlign = TextAlign.Center)
    }
}
